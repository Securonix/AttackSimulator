/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.feedgeneratorui;

import feedgenerator.RunSysLogFeeds;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author securonix
 */
public class ThreadManagement extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String operation = request.getParameter("operation");
        int orderId = Integer.parseInt(request.getParameter("orderid"));
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            MySqlDatabase mydb = new MySqlDatabase();
            if(operation.equalsIgnoreCase("startthread")){
                boolean alreadyStarted = mydb.getAlreadyStarted(orderId);
                out.print(alreadyStarted);
                if(!alreadyStarted){
                    ResultSet resultSet = mydb.getOrderId(orderId); // this should return only one result
                    try {
                        while(resultSet.next()){
                            String destinationIp = resultSet.getString(8);
                            String frequency = resultSet.getString(7);
                            String destinationPort = resultSet.getString(9);
                            String feedtype = resultSet.getString(10);
                            out.println(destinationIp);
                            out.println(destinationPort);
                            out.println(frequency);
                            out.println(feedtype);
                            String[] feedtypeArray = feedtype.split("-");
                            for (String feedtypeArray1 : feedtypeArray) {
                                out.println("Here");
                                System.out.println(System.getProperty("user.dir"));
                                RunSysLogFeeds th = new RunSysLogFeeds(destinationIp, destinationPort, frequency, feedtypeArray1, orderId);
                                out.println("Here too");
                                th.start();
                                out.println("Here three");
                                long threadid = th.getId();
                                out.println(threadid);
                                if(mydb.insertThreadDetails(threadid, orderId)){
                                    System.out.println("Inserted Values");
                                    response.setHeader("Content-type", "text/plain");
                                    out.write("started");
                                }else{
                                    System.out.println("Error in inserting threadId");
                                    response.setHeader("Content-type", "text/plain");
                                    out.write("error");
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        out.write("failed");
                        Logger.getLogger(ThreadManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }finally{
                        mydb.closeConnection();
                    }
                }else{
                    out.write("already started");
                }
            }else if(operation.equalsIgnoreCase("stopthread")){
                ArrayList<Long> threadIds = mydb.getAllThreads(orderId);
                //find all these thread with their Ids and shut them down
                Thread currentThread = Thread.currentThread();
                ThreadGroup threadGroup = getThreadGroup(currentThread);
                int allActiveThreads = threadGroup.activeCount();
                Thread[] allThreads = new Thread[allActiveThreads];
                threadGroup.enumerate(allThreads);
                
                for(int i=0; i < allThreads.length; i++){
                    Thread thread = allThreads[i];
                    for(int j=0; j < threadIds.size(); j++){
                        if(thread.getId() == threadIds.get(j)){
                            ((RunSysLogFeeds)thread).shutdown();
                        }
                    }
                }
                
                //delete entries from the table
                mydb.deleteThreadOrder(orderId);
            }else{
                response.setHeader("Content-type", "text/plain");
                out.println("Parameter Not recognized");
            }
        } finally {
            out.close();
        }
    }
    
    private ThreadGroup getThreadGroup(Thread thread){
        ThreadGroup rootGroup = thread.getThreadGroup();
        while(true){
            ThreadGroup parentGroup = rootGroup.getParent();
            if(parentGroup == null){
                break;
            }
            rootGroup = parentGroup;
        }
        
        return rootGroup;
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
