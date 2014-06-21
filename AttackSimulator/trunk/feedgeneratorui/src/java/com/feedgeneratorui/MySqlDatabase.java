/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feedgeneratorui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author securonix
 */
public class MySqlDatabase {
    private Connection myconnection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Properties props = null;
    private String databaseName = null;
    private String dbUsername = null;
    private String dbPassword = null;
    private String dbUrl = null;
    
    public MySqlDatabase(){
        String path = "/home/ec2-user/Attacksimulator/tomcat-7.0.securonix/conf/jdbc.properties";
        databaseName = new String();
        dbUsername = new String();
        dbPassword = new String();
        dbUrl = new String();
        
        setProperties(path);
        
        dbUrl = "jdbc:mysql://localhost:3306/" + databaseName + "?user=" + dbUsername + "&password=" + dbPassword;
    }
    
    private void setProperties(String path){
        try {
            InputStream input = new FileInputStream(path);
            props = new Properties();
            props.load(input);
            
            dbUsername = props.getProperty("username");
            dbPassword = props.getProperty("password");
            databaseName = props.getProperty("database");
            
        } catch (FileNotFoundException ex) {
            System.out.println("File not found exception");
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void createConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            myconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/securonix?"
                  + "user=root&password=open24X7");    
        } catch (SQLException ex) {
            closeConnection();
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e){
            closeConnection();
            Logger.getLogger("MySql Driver not loaded").log(Level.SEVERE, null, e);
        }
    }
    
    public void closeConnection(){
        try {
            if(myconnection.isValid(0)) {
                myconnection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String registerUserToDB(Map<String, String> dataValues){
        String value = "failure";
        try {
            createConnection();
            //check if already registered before doing this
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select count(*) from SECURONIX.USERS_FEEDGEN where username = \"" + dataValues.get("username") + "\";");
            resultSet.next();
            if(resultSet.getInt(1) != 0){
                //username already in use
                value = "usernameInUse";
            }else{
                preparedStatement = myconnection.prepareStatement("insert into SECURONIX.USERS_FEEDGEN (name, company, emailid, contact, username, password) values (?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, dataValues.get("name"));
                preparedStatement.setString(2, dataValues.get("company"));
                preparedStatement.setString(3, dataValues.get("emailid"));
                preparedStatement.setString(4, dataValues.get("contact"));
                preparedStatement.setString(5, dataValues.get("username"));
                preparedStatement.setString(6, dataValues.get("password"));
                preparedStatement.executeUpdate();
                value = "success";
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeConnection();
        }
        
        return value;
    }

    public boolean checkUserInDb(String username, String password) {
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select count(*) from SECURONIX.USERS_FEEDGEN where username=\"" + 
                    username + "\" AND password=\"" + password + "\";");
            
            resultSet.next();
            if(resultSet.getInt(1) >= 1){
                return true;
            }
        }catch(SQLException ex){
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeConnection();
        }
        
        return false;
    }

    public boolean checkUsernameInDb(String username) {
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select count(*) from SECURONIX.USERS_FEEDGEN where username=\"" + 
                    username + "\";");
            resultSet.next();
            if(resultSet.getInt(1) >= 1){
                return true;
            }
        }catch(SQLException ex){
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeConnection();
        }
        
        return false;
    }

    public boolean checkForNullEntries(String username){
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select month1, day1, year1, month2, day2, year2, frequency, destinationip, destinationport, feedtype "
                    + "from USERS_FEEDGEN where username=\""+ username +"\";");
            
            resultSet.next();
            int month1 = resultSet.getInt(1);
            if(resultSet.wasNull()){
                closeConnection();
                return true;
            }
        }catch(SQLException ex){
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeConnection();
        }
        return false;
    }
    
    /*
    public ResultSet genericSelectStatement(Map<String, ArrayList<String> > inputQueryParams){
        String query = "select ";
        ArrayList<String> selectValues = inputQueryParams.get("select");
        for(int i=0; i<selectValues.size(); i++){
            query += selectValues.get(i) + ", ";
        }
    }
    */

    public void insertIntoDB(Map<String, Object> testParameters) {
        String value = "failure";
        try {
            createConnection();
            
            preparedStatement = myconnection.prepareStatement("insert into USERS_FEEDGEN_ORDER(month1, day1, year1, month2, day2, year2, frequency, destinationIP, destinationPort,"
                    + "feedtype, username) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?);");
            preparedStatement.setString(1, testParameters.get("month1").toString());
            preparedStatement.setString(2, testParameters.get("day1").toString());
            preparedStatement.setString(3, testParameters.get("year1").toString());
            preparedStatement.setString(4, testParameters.get("month2").toString());
            preparedStatement.setString(5, testParameters.get("day2").toString());
            preparedStatement.setString(6, testParameters.get("year2").toString());
            preparedStatement.setString(7, testParameters.get("frequency").toString());
            preparedStatement.setString(8, testParameters.get("destinationIP").toString());
            preparedStatement.setString(9, testParameters.get("destinationPort").toString());
            preparedStatement.setString(10, testParameters.get("feedtype").toString());
            preparedStatement.setString(11, testParameters.get("username").toString());
            preparedStatement.executeUpdate();
            
            value = "success";
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeConnection();
        }
    }

    public boolean checkAdminInDb(String username, String password) {
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select count(*) from SECURONIX.USERS_FEEDGEN_ADMIN where username=\"" + 
                    username + "\" AND password=\"" + password + "\";");
            resultSet.next();
            if(resultSet.getInt(1) == 1){
                return true;
            }
        }catch(SQLException ex){
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeConnection();
        }
        
        return false;
    }

    public ResultSet getAllUnapprovedUsers() {
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select month1, day1, year1, month2, day2, year2, frequency, destinationip, destinationport, feedtype, transid, username from SECURONIX.USERS_FEEDGEN_ORDER where approved=\"0\";");
            if(!resultSet.isBeforeFirst()){
                return null;
            }else{
                return resultSet;
            }
        }catch(SQLException ex){
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateApproval(int tranid){
        try {
            createConnection();
            
            preparedStatement = myconnection.prepareStatement("update USERS_FEEDGEN_ORDER SET approved=\"1\" where transid=?;");
            preparedStatement.setInt(1, tranid);
            preparedStatement.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            closeConnection();
        }
    }

    public boolean deleteOrder(int tranid) {
        try {
            createConnection();
            
            preparedStatement = myconnection.prepareStatement("delete from USERS_FEEDGEN_ORDER where transid=?;");
            preparedStatement.setInt(1, tranid);
            preparedStatement.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            closeConnection();
        }
    }

    public ResultSet getAllApprovedUsers() {
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select month1, day1, year1, month2, day2, year2, frequency, destinationip, destinationport, feedtype, transid, username from SECURONIX.USERS_FEEDGEN_ORDER where approved=\"1\";");
            if(!resultSet.isBeforeFirst()){
                return null;
            }else{
                return resultSet;
            }
        }catch(SQLException ex){
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet getOrderId(int orderId) {
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select month1, day1, year1, month2, day2, year2, frequency, destinationip, destinationport, feedtype, transid, username from SECURONIX.USERS_FEEDGEN_ORDER where transid="+ orderId + ";");
            if(!resultSet.isBeforeFirst()){
                return null;
            }else{
                return resultSet;
            }
        }catch(SQLException ex){
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean getAlreadyStarted(int orderId) {
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select * from SECURONIX.USERS_FEEDGEN_THREAD where orderid="+ orderId + ";");
            
            if(!resultSet.isBeforeFirst()){
                return false;
            }else{
                return true;
            }
        }catch(SQLException ex){
            closeConnection();
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            closeConnection();
        }
    }

    public boolean insertThreadDetails(long threadid, int orderId) {
        try {
            createConnection();
            
            preparedStatement = myconnection.prepareStatement("INSERT INTO USERS_FEEDGEN_THREAD(threadid, orderid) VALUES(?,?);");
            preparedStatement.setLong(1, threadid);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            closeConnection();
        }
    }

    public ArrayList<Long> getAllThreads(int orderId) {
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select * from SECURONIX.USERS_FEEDGEN_THREAD where orderid="+ orderId + ";");
            ArrayList<Long> result;
            result = new ArrayList<>();
            if(!resultSet.isBeforeFirst()){
                return null;
            }else{
                while(resultSet.next()){
                    long id = resultSet.getLong(2);
                    result.add(id);
                }
                
                return result;
            }
        }catch(SQLException ex){
            closeConnection();
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            closeConnection();
        }
    }

    public boolean  deleteThreadOrder(int orderId) {
        try {
            createConnection();
            
            preparedStatement = myconnection.prepareStatement("delete from USERS_FEEDGEN_THREAD where orderid=?;");
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            closeConnection();
        }
    }

    String currentOrderId() {
        try {
            createConnection();
            
            preparedStatement = myconnection.prepareStatement("select MAX(transid) from USERS_FEEDGEN_ORDER");
            
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String result = resultSet.getString(1);
            
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            closeConnection();
        }
    }

    String currentUserNumber(String username) {
        try {
            createConnection();
            
            preparedStatement = myconnection.prepareStatement("select id from USERS_FEEDGEN where username=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            
            String usernumber = resultSet.getString(1);
            closeConnection();
            
            return usernumber;
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }finally{
            closeConnection();
        }
    }
}
