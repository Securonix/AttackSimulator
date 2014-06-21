package com.feedgeneratorui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 *
 * @author securonix
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class register extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param username
     * @return 
     */
    
    protected boolean testUsername(String username){
        
        return true;
    }
    
    protected boolean testPassword(String password){
        
        return true;
    }
    
    protected boolean testCompany(String company){
        
        return true;
    }
    
    protected boolean testFullName(String fullname){
        
        return true;
    }
    
    protected boolean testEmail(String email){
        
        return true;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String company = request.getParameter("company");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        
        boolean result = testUsername(username) && testPassword(password) && testFullName(fullname) && testCompany(company)
                && testEmail(email);
        
        HashMap<String, String> dataValues;
        dataValues = new HashMap<String, String>();
        String returnValue = null;
        PrintWriter out = response.getWriter();
        response.setHeader("Content-type", "text/plain");
        if(result){
            dataValues.put("name", fullname);
            dataValues.put("company", company);
            dataValues.put("emailid", email);
            dataValues.put("username", username);
            dataValues.put("password", password);
            dataValues.put("contact", contact);
            MySqlDatabase mydb = new MySqlDatabase();
            returnValue = mydb.registerUserToDB(dataValues);
        }
        
        if(returnValue.compareTo("success") == 0){
            out.print("success");
        }else{
            out.print("failure");
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
        return "Short description";
    }// </editor-fold>
}
