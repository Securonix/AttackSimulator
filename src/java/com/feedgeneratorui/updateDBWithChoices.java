/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feedgeneratorui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author securonix
 */
@WebServlet(name = "updateDBWithChoices", urlPatterns = {"/updateDBWithChoices"})
public class updateDBWithChoices extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private PrintWriter out = null;
    private Map<String, Object> testParametersServerSide(String username, String contentPeriod1, String contentPeriod2, String frequency, String destinationIp,
        String destinationPort, String selectedLogFileType){

      Map<String, Object> parameters = new HashMap<String, Object>();

      if(contentPeriod1.length() == 0 || contentPeriod1 == null){
        out.print("contentPeriod1false");
        return null;
      }

      if(contentPeriod2.length() == 0 || contentPeriod2 == null){
        out.print("contentPeriod2false");
        return null;
      }
      
      try{
        String splitDate1[] = contentPeriod1.split("/");
        int month1 = Integer.parseInt(splitDate1[0].trim());
        int day1 = Integer.parseInt(splitDate1[1].trim());
        int year1 = Integer.parseInt(splitDate1[2].trim());

        if(month1 < 1 || month1 > 12){
            out.print("month1false");
            return null;
        }

        if(day1 < 1 || day1 > 31){
            out.print("day1false");
            return null;
        }
        
        String splitDate2[] = contentPeriod2.split("/");
        int month2 = Integer.parseInt(splitDate2[0].trim());
        int day2 = Integer.parseInt(splitDate2[1].trim());
        int year2 = Integer.parseInt(splitDate2[2].trim());

        //we have the dates in month year and days now.. 
        int freq = Integer.parseInt(frequency.trim());
        parameters.put("month1", month1);
        parameters.put("day1", day1);
        parameters.put("year1", year1);
        parameters.put("month2", month2);
        parameters.put("day2", day2);
        parameters.put("year2", year2);
        parameters.put("frequency", freq);
        parameters.put("destinationIP", destinationIp.trim());
        parameters.put("destinationPort", destinationPort.trim());
        parameters.put("feedtype", selectedLogFileType);
        parameters.put("username", username);

      }catch(Exception e){
        out.print("failure");
        e.printStackTrace();
        return null;
      }
      return parameters;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/plain;charset=UTF-8");
            out = response.getWriter();
            out.println("success");
        
            String username = request.getParameter("username");
            String contentPeriod1 = request.getParameter("contentPeriod1");
            String contentPeriod2 = request.getParameter("contentPeriod2");
            String frequency = request.getParameter("frequency");
            String destinationIp = request.getParameter("destinationIp");
            String destinationPort = request.getParameter("destinationPort");
            String selectedLogFileType = request.getParameter("selectedLogFileType");

            //do checks server side to find out if the data that has reached here is okay or not.
            response.setContentType("text/plain;charset=UTF-8");
            out = response.getWriter();
            Map<String, Object> testParameters = testParametersServerSide(username, contentPeriod1, contentPeriod2, frequency, destinationIp, destinationPort,
                selectedLogFileType);
            
            if(testParameters == null){
                out.print("failed");
            }else{
                MySqlDatabase mydb = new MySqlDatabase();
                mydb.insertIntoDB(testParameters);
                /*SendMail sm = new SendMail("avarma@securonix.com", "A new user is seeking feeds", username + " just " +
                        "requested a feed from securonix, please click here to goto the admin panel http://107.20.142.115:8085/feedgeneratorui/admin.jsp to allow feed generation.", username);
                sm.sendMail();*/
                out.print("success");
            }
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Anuj's Work";
    }// </editor-fold>
}
