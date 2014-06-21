/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feedgeneratorui;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author securonix
 */
public class authenticate extends HttpServlet {

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
    
    private boolean authenticate(String username, String password){
        MySqlDatabase mydb = new MySqlDatabase();
        return mydb.checkUserInDb(username, password);
    }
    
    private String getOrderId(){
        MySqlDatabase mydb = new MySqlDatabase();
        return mydb.currentOrderId();
    }
    
    private String getUserNumber(String username){
        MySqlDatabase mydb = new MySqlDatabase();
        return mydb.currentUserNumber(username);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            if(authenticate(username, password)){
                String orderid = getOrderId();
                String usernumber = getUserNumber(username);
                Cookie loginCookie = new Cookie("user", username);
                Cookie orderIdCookie = new Cookie("orderid", orderid);
                Cookie userNumberCookie = new Cookie("usernumber", usernumber);
                loginCookie.setMaxAge(30*60);
                orderIdCookie.setMaxAge(30*60);
                response.addCookie(loginCookie);
                response.addCookie(orderIdCookie);
                response.addCookie(userNumberCookie);
                out.print("loggedin");
            }else{
                out.println("failed");
            }
        } finally {            
            out.close();
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
