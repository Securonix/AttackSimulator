/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.feedgeneratorui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author securonix
 */
public class PreviewUsersFile extends HttpServlet {

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
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(new URL("http://107.20.142.115:8085/FeedGeneratorUI/Resources/HRData665.csv").openStream()));
        String firstLine = reader.readLine();
        String[] headers = firstLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        String tableString = "<table id=\"previewUsersTable\" border=\"solid\"><col style=\"width: 200px\">";
        tableString += "<thead ><tr>";
        for (String header : headers) {
            tableString += "<th>" + header + "</th>";
        }
        tableString += "</tr></thead>";
        int count = 0; String lines;
        tableString += "<tbody>";
        while (count < 10 && (lines = reader.readLine()) != null){
            String [] values = lines.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            tableString += "<tr>";
            for(String value : values){
                tableString += "<td>" + value + "</td>";
            }
            if(values.length < headers.length){
                tableString += "<td></td>";
            }
            tableString += "</tr>";
            count++;
        }
        tableString += "</tbody></table>";
        response.setHeader("Content-type", "text/plain");
        PrintWriter out = response.getWriter();
        out.print(tableString);
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
        processRequest(request, response);
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
