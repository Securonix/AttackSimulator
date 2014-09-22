/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package templatingsystem;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author securonix
 */

public class MySQLDBClass {
    /*
     * This class will handle all connections to the Database and querying.
     */
    private Connection myconnection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Properties props = null;
    private String databaseName = null;
    private String dbUsername = null;
    private String dbPassword = null;
    private String dbUrl = null;
    private String dbPort = null;
    
    MySQLDBClass(){
        String path = "conf/jdbc.properties";
        databaseName = new String();
        dbUsername = new String();
        dbPassword = new String();
        dbUrl = new String();
        dbPort = new String();
        
        setProperties(path);
        
        dbUrl = "jdbc:mysql://localhost:"+dbPort+"/" + databaseName + "?user=" + dbUsername + "&password=" + dbPassword;
        //System.out.println(dbUrl);
    }
    
    private void createConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            myconnection = DriverManager.getConnection(dbUrl);    
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e){
            Logger.getLogger("MySql Driver not loaded").log(Level.SEVERE, null, e);
        }
    }
    
    private void closeConnection(){
        try {
            if(myconnection.isValid(0)) {
                myconnection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setProperties(String path){
        try {
            /*
            InputStream input = new FileInputStream(path);
            props = new Properties();
            props.load(input);
            
            dbUsername = props.getProperty("username");
            dbPassword = props.getProperty("password");
            databaseName = props.getProperty("database");
            dbPort = props.getProperty("port");
            */
            dbUsername = "root";//props.getProperty("username");
            dbPassword = "open24X7";//props.getProperty("password");
            databaseName = "attacksimdev";//props.getProperty("database");
            dbPort = "3306";//props.getProperty("port");
        } catch (Exception ex) {
                //
        }//catch (FileNotFoundException ex) {
            //System.out.println("File not found exception");
            //Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (IOException ex) {
        //    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        //} 
    }

    ArrayList<String> fillParams() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    HashMap<String, String> executeQuery(String query, String countQuery, String variableName) {
         HashMap<String, String> temp = null;
        try{
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            Statement countStatement = myconnection.createStatement();
            ResultSet countResultSet = countStatement.executeQuery(countQuery);
            countResultSet.next();
            if(!resultSet.isBeforeFirst()){
                //no data
            }else{
                temp = new HashMap<>();
                int rowCount = countResultSet.getInt(1);
                //generate a random number between 1 and the row count
                Random rand =new Random();
                int random = rand.nextInt(rowCount);
                if(random == 0) random++;
                resultSet.relative(random);
                int colNum = rsmd.getColumnCount();
                for(int i=0; i<colNum; i++){
                    temp.put(variableName+"."+rsmd.getColumnName(i+1), resultSet.getString(i+1));
                }
            }
        }catch(SQLException ex){
            System.out.println("Exception occured in executeQuery function in MySQLDBClass");
            ex.printStackTrace();
        }finally{
             try {
                 if(resultSet != null && !resultSet.isClosed()){
                     resultSet.close();
                 }
                 
                 if(statement != null && !statement.isClosed()){
                     statement.close();
                 }
                 
                 closeConnection();
             } catch (SQLException ex) {
                 Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
        return temp;
    }

    String getTransactionFile(String feedtype) throws FileNotFoundException{
        String filepath = "";
        try {
            String query = "select transactionfilepath from feedmaster;";
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            if(!resultSet.isBeforeFirst()){
                //no transactionfilepath entry for this feedtype
                throw new FileNotFoundException();
            }else{
                resultSet.next();
                filepath = resultSet.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                 if(resultSet != null && !resultSet.isClosed()){
                     resultSet.close();
                 }
                 
                 if(statement != null && !statement.isClosed()){
                     statement.close();
                 }
                 
                 closeConnection();
             } catch (SQLException ex) {
                 Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
        return filepath;
    }

    HashMap<String, Object> getTemplateMasterValues(String variable) {
        HashMap<String, Object> rs = new HashMap<>();
        try {
            String query = "select * from variablemaster where variablename = \"" + variable + "\";";
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            if(!resultSet.isBeforeFirst()){
                throw new NullPointerException("No variable entry found");
            }else{
                resultSet.next();
                ArrayList<String> params = new ArrayList<>();
                String variableGenerator = resultSet.getString("variablegenerator");   
                for(int i=1; i < 6; i++){
                    String value = resultSet.getString("params"+i);
                    if(!resultSet.wasNull()){
                        params.add(value);
                    }else{
                        break;
                    }
                }
                rs.put("variablegenerator", variableGenerator);
                rs.put("parameters", params);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                 if(resultSet != null && !resultSet.isClosed()){
                     resultSet.close();
                 }
                 
                 if(statement != null && !statement.isClosed()){
                     statement.close();
                 }
                 
                 closeConnection();
             } catch (SQLException ex) {
                 Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        return rs;
    }
}
