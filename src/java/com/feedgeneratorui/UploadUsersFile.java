/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.feedgeneratorui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author securonix
 */
@WebServlet(name = "UploadUsersFile", urlPatterns = {"/UploadUsersFile"})
@MultipartConfig(fileSizeThreshold=1024*1024*5, // 5GB
                 maxFileSize=1024*1024*10,      // 10GB
                 maxRequestSize=1024*1024*50, // 50GB
                 location="/Users/securonix/temp")   
public class UploadUsersFile extends HttpServlet {

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
        if(request.getContentType().toLowerCase().contains("multipart/form-data")){
            //file upload request
            Part filePart = request.getPart("file");
            String filename = getFileName(filePart);
            
            //get the current user who is placing the order
            String username = getValue(request.getPart("user"));
            String userNumber = getValue(request.getPart("usernumber"));
            
            // constructs path of the directory to save uploaded file
            String savePath = "/tmp";

            // creates the save directory if it does not exists
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            
            String filepath = fileSaveDir+ File.separator + username + "_" + userNumber + "_" + filename;
            File saveFile = new File(filepath);
            
            if(saveFile.exists()){
                saveFile.delete();
            }
            
            FileOutputStream fos = new FileOutputStream(fileSaveDir+ File.separator + username + "_" + userNumber + "_" + filename);
            
            BufferedInputStream bis = new BufferedInputStream(filePart.getInputStream());
            byte[] buffer = new byte[1024*1024];
            int length;
            while((length = bis.read(buffer)) != -1){
                fos.write(buffer, 0, length);
            }
            
            boolean testForCorrectness = testCsvFile(filepath);
            
            PrintWriter out = response.getWriter();
            response.setHeader("Content-type", "text-plain");
            out.print("success");
        }else{
            PrintWriter out = response.getWriter();
            response.setHeader("Content-type", "text-plain");
            out.print("failure");
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

    private String getFileName(Part filePart) {
        for (String cd : filePart.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
    
    private String getValue(Part filePart) {
        try {
            Scanner scanner = new Scanner(filePart.getInputStream());
            String username = scanner.nextLine();
            return username;
        } catch (IOException ex) {
            Logger.getLogger(UploadUsersFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    private boolean testCsvFile(String filepath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
