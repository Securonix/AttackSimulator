/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feedgeneratorui;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author securonix
 */
@WebServlet(name = "authAdmin", urlPatterns = {"/authAdmin"})
public class authAdmin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        MySqlDatabase mydb = new MySqlDatabase();
        boolean result = mydb.checkAdminInDb(username, password);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String json = new String();
        String table = new String();
        String unapprovedUSers = new String();
        unapprovedUSers = "[ ";
        if (result) {
            try {
                /* TODO output your page here. You may use following sample code. */
                Cookie loginCookie = new Cookie("admin", username);
                loginCookie.setMaxAge(30 * 60);
                response.addCookie(loginCookie);
                ResultSet rs = mydb.getAllUnapprovedUsers();
                if (!rs.isBeforeFirst()) {
                    response.setContentType("text/plain;charset=UTF-8");
                    out.print("nousers");
                } else {
                    table += "\"<table border=\\\"1\\\" style=\\\"text-align: center\\\">";
                    table += "<tr>";
                    table += "<th>";
                    table += "Name";
                    table += "</th>";
                    table += "<th>";
                    table += "Start Date";
                    table += "</th>";
                    table += "<th>";
                    table += "End Date";
                    table += "</th>";
                    table += "<th>";
                    table += "Frequency";
                    table += "</th>";
                    table += "<th>";
                    table += "Destination IP";
                    table += "</th>";
                    table += "<th>";
                    table += "Destination Port";
                    table += "</th>";
                    table += "<th>";
                    table += "Log type";
                    table += "</th>";
                    table += "<th colspan=2>";
                    table += "Allow";
                    table += "</th>";
                    table += "</tr>";

                    while (rs.next()) {
                        table += "<tr>";
                        table += "<td>";
                        table += rs.getString(12);
                        table += "</td>";
                        table += "<td>";
                        table += rs.getInt(1);
                        table += "/";
                        table += rs.getInt(2);
                        table += "/";
                        table += rs.getInt(3);
                        table += "</td>";
                        table += "<td>";
                        table += rs.getInt(4);
                        table += "/";
                        table += rs.getInt(5);
                        table += "/";
                        table += rs.getInt(6);
                        table += "</td>";
                        table += "<td>";
                        table += rs.getInt(7);
                        table += "</td>";
                        table += "<td>";
                        table += rs.getString(8);
                        table += "</td>";
                        table += "<td>";
                        table += rs.getString(9);
                        table += "</td>";
                        table += "<td>";
                        table += rs.getString(10);
                        table += "</td>";
                        table += "<td style=\\\"cursor: pointer;\\\" id=\\\"" + rs.getString(11) + "yes\\\">";
                        table += "Yes";
                        table += "</td>";
                        table += "<td style=\\\"cursor: pointer;\\\" id=\\\"" + rs.getString(11) + "no\\\">";
                        table += "No";
                        table += "</td>";
                        table += "</tr>";
                        unapprovedUSers += rs.getString(11) + ", ";
                    }

                    table += "</table>\"";
                    unapprovedUSers = unapprovedUSers.trim();
                    if (unapprovedUSers.length() > 0 && unapprovedUSers.charAt(unapprovedUSers.length() - 1) == ',') {
                        unapprovedUSers = unapprovedUSers.substring(0, unapprovedUSers.length() - 1);
                    }
                    unapprovedUSers += " ]";

                    json = "{ \"table\": " + table + ", \"users\": " + unapprovedUSers + " , \"threadButton\": \"http://107.20.142.115:8085/FeedGeneratorUI/threadManage.jsp\"}";
                    out.print(json);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex){
                response.setContentType("text/plain;charset=UTF-8");
                out.print("nousers");
            }finally {
                mydb.closeConnection();
                out.close();
            }
        } else {
            response.setContentType("text/plain;charset=UTF-8");
            out.print("failedauthenticate");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
