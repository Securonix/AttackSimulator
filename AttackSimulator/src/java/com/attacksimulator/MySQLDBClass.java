/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.UUID;
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
    private String dbserverName = null;

    MySQLDBClass() {
        String path = "jdbc.properties";
        databaseName = new String();
        dbUsername = new String();
        dbPassword = new String();
        dbUrl = new String();
        dbPort = new String();

        setProperties(path);

        dbUrl = "jdbc:mysql://" + dbserverName + ":" + dbPort + "/" + databaseName + "?user=" + dbUsername + "&password=" + dbPassword;
        // System.out.println(dbUrl);
    }

    private void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(dbUrl);
            myconnection = DriverManager.getConnection(dbUrl);
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            Logger.getLogger("MySql Driver not loaded").log(Level.SEVERE, null, e);
        }
    }

    private void closeConnection() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }

            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }

            if (myconnection.isValid(0)) {
                myconnection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveUsersToDb(UserImport ui, String[] splitLines) {
        String query = "INSERT INTO `attacksimdev`.`users`\n"
                + "(`employeeid`,\n"
                + "`firstname`,\n"
                + "`middlename`,\n"
                + "`lastname`,\n"
                + "`department`,\n"
                + "`division`,\n"
                + "`location`,\n"
                + "`manageremployeeid`,\n"
                + "`workemail`,\n"
                + "`workphone`,\n"
                + "`title`,\n"
                + "`employeetype`,\n"
                + "`status`,\n"
                + "`uniquecode`,\n"
                + "`riskscore`,\n"
                + "`promoted`,\n"
                + "`createdate`,\n"
                + "`usergroup`,\n"
                + "`street`,\n"
                + "`city`,\n"
                + "`province`,\n"
                + "`zipcode`,\n"
                + "`userstate`,\n"
                + "`region`,\n"
                + "`country`,\n"
                + "`approveremployeeid`,\n"
                + "`delegateemployeeid`,\n"
                + "`technicalapproverid`,\n"
                + "`extension`,\n"
                + "`fax`,\n"
                + "`mobile`,\n"
                + "`pager`,\n"
                + "`jobcode`,\n"
                + "`comments`,\n"
                + "`createdby`,\n"
                + "`costcentername`,\n"
                + "`costcentercode`,\n"
                + "`enabledate`,\n"
                + "`disabledate`,\n"
                + "`deletedate`,\n"
                + "`updatedate`,\n"
                + "`sunrisedate`,\n"
                + "`sunsetdate`,\n"
                + "`criticality`,\n"
                + "`domintlin`,\n"
                + "`nameprefix`,\n"
                + "`namesuffix`,\n"
                + "`preferredname`,\n"
                + "`secondaryphone`,\n"
                + "`statusdescription`,\n"
                + "`vacationstart`,\n"
                + "`vacationend`,\n"
                + "`networkid`,\n"
                + "`workpager`,\n"
                + "`workextensionnumber`,\n"
                + "`workfax`,\n"
                + "`employeestatuscode`,\n"
                + "`locationcode`,\n"
                + "`locationname`,\n"
                + "`mailcode`,\n"
                + "`hiredate`,\n"
                + "`rehiredate`,\n"
                + "`recenthiredate`,\n"
                + "`terminationdate`,\n"
                + "`lastdayworked`,\n"
                + "`contractstartdate`,\n"
                + "`contractenddate`,\n"
                + "`employeetypedescription`,\n"
                + "`regtempin`,\n"
                + "`fulltimeparttimein`,\n"
                + "`managerfirstname`,\n"
                + "`managerlastname`,\n"
                + "`managermiddlename`,\n"
                + "`orgunitnumber`,\n"
                + "`companycode`,\n"
                + "`companynumber`,\n"
                + "`hierarchy`,\n"
                + "`lastperformancereviewdate`,\n"
                + "`lastperformancereviewresult`,\n"
                + "`standardhours`,\n"
                + "`shiftcode`,\n"
                + "`shiftname`,\n"
                + "`lanid`,\n"
                + "`userid`,\n"
                + "`transferreddate`,\n"
                + "`datasourceid`,\n"
                + "`timezoneoffset`,\n"
                + "VALUES\n"
                + "<{employeeid: }>,\n"
                + "<{firstname: }>,\n"
                + "<{middlename: }>,\n"
                + "<{lastname: }>,\n"
                + "<{department: }>,\n"
                + "<{division: }>,\n"
                + "<{location: }>,\n"
                + "<{manageremployeeid: }>,\n"
                + "<{workemail: }>,\n"
                + "<{workphone: }>,\n"
                + "<{title: }>,\n"
                + "<{employeetype: }>,\n"
                + "<{status: }>,\n"
                + "<{uniquecode: }>,\n"
                + "<{riskscore: }>,\n"
                + "<{promoted: }>,\n"
                + "<{createdate: }>,\n"
                + "<{usergroup: }>,\n"
                + "<{street: }>,\n"
                + "<{city: }>,\n"
                + "<{province: }>,\n"
                + "<{zipcode: }>,\n"
                + "<{userstate: }>,\n"
                + "<{region: }>,\n"
                + "<{country: }>,\n"
                + "<{approveremployeeid: }>,\n"
                + "<{delegateemployeeid: }>,\n"
                + "<{technicalapproverid: }>,\n"
                + "<{extension: }>,\n"
                + "<{fax: }>,\n"
                + "<{mobile: }>,\n"
                + "<{pager: }>,\n"
                + "<{jobcode: }>,\n"
                + "<{comments: }>,\n"
                + "<{createdby: }>,\n"
                + "<{costcentername: }>,\n"
                + "<{costcentercode: }>,\n"
                + "<{enabledate: }>,\n"
                + "<{disabledate: }>,\n"
                + "<{deletedate: }>,\n"
                + "<{updatedate: }>,\n"
                + "<{sunrisedate: }>,\n"
                + "<{sunsetdate: }>,\n"
                + "<{criticality: Low}>,\n"
                + "<{domintlin: }>,\n"
                + "<{nameprefix: }>,\n"
                + "<{namesuffix: }>,\n"
                + "<{preferredname: }>,\n"
                + "<{secondaryphone: }>,\n"
                + "<{statusdescription: }>,\n"
                + "<{vacationstart: }>,\n"
                + "<{vacationend: }>,\n"
                + "<{networkid: }>,\n"
                + "<{workpager: }>,\n"
                + "<{workextensionnumber: }>,\n"
                + "<{workfax: }>,\n"
                + "<{employeestatuscode: }>,\n"
                + "<{locationcode: }>,\n"
                + "<{locationname: }>,\n"
                + "<{mailcode: }>,\n"
                + "<{hiredate: }>,\n"
                + "<{rehiredate: }>,\n"
                + "<{recenthiredate: }>,\n"
                + "<{terminationdate: }>,\n"
                + "<{lastdayworked: }>,\n"
                + "<{contractstartdate: }>,\n"
                + "<{contractenddate: }>,\n"
                + "<{employeetypedescription: }>,\n"
                + "<{regtempin: }>,\n"
                + "<{fulltimeparttimein: }>,\n"
                + "<{managerfirstname: }>,\n"
                + "<{managerlastname: }>,\n"
                + "<{managermiddlename: }>,\n"
                + "<{orgunitnumber: }>,\n"
                + "<{companycode: }>,\n"
                + "<{companynumber: }>,\n"
                + "<{hierarchy: }>,\n"
                + "<{lastperformancereviewdate: }>,\n"
                + "<{lastperformancereviewresult: }>,\n"
                + "<{standardhours: }>,\n"
                + "<{shiftcode: }>,\n"
                + "<{shiftname: }>,\n"
                + "<{lanid: }>,\n"
                + "<{userid: }>,\n"
                + "<{transferreddate: }>,\n"
                + "<{datasourceid: 0}>,\n"
                + "<{timezoneoffset: }>);";
    }

    public ArrayList<Object> getUserIPForSecUser(Integer secUserId) {
        createConnection();
        HashMap<String, ArrayList<String>> rs = new HashMap<>();
        ArrayList<String> ips = null;
        ArrayList<String> accounts = new ArrayList<>();
        ArrayList<Integer> userids = new ArrayList<>();
        ArrayList<Object> returnentity = new ArrayList<>();
        ArrayList<ArrayList<String>> ipsInOrder = new ArrayList<>();

        if (myconnection != null) {
            try {
                String query = "select * from sysipusermapping where secuserid=" + secUserId;
                //System.out.println("The secuserid that I see in mysqldb class is : "+ secUserId);
                statement = myconnection.createStatement();
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Integer userid = resultSet.getInt("userid");
                    //System.out.println("UserId from MySqldb class: "+userid);
                    String commasepIps = resultSet.getString("ipaddress");
                    //System.out.println("UserId from MySqldb class: "+userid);
                    String[] splitIps = commasepIps.split("\\,");
                    ips = new ArrayList<>();
                    ips.add(splitIps[0]);
                    ips.add(splitIps[1]);
                    ips.add(splitIps[2]);
                    //rs.put(userid.toString(), ips);
                    ipsInOrder.add(ips);
                    userids.add(userid);
                }

                resultSet.close();
                statement.close();
                statement = myconnection.createStatement();

                String query2 = "select Account1 from users where id=";
                int index = 0;
                for (Integer ids : userids) {
                    String actquery = query2 + ids;
                    resultSet = statement.executeQuery(actquery);
                    resultSet.next();
                    String acc = resultSet.getString(1);
                    accounts.add(acc);
                    rs.put(acc, ipsInOrder.get(index++));
                }
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        returnentity.add(rs);
        returnentity.add(ips);
        returnentity.add(accounts);

        closeConnection();
        return returnentity;
    }

    public int getMaxOfUsers() {
        createConnection();
        createConnection();
        try {
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery("select max(id) from users");
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return -1;
    }

    public ArrayList<String> getAllUsersForSecUser(int secuserid) {
        String query = "select userid, firstname, lastname from users where id in (select userid from sysipusermapping where secuserid=" + secuserid
                + ");";

        ArrayList<String> users = new ArrayList<>();
        createConnection();
        try {
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);

            if (!resultSet.isBeforeFirst()) {
                //no users in the sysipusermapping table. This means that this user has somehow managed to get to the attacker screen before setting up
                //his environment.
            } else {
                //there are values in the sysipusermapping table against this secuserid. This is the list of users.
                while (resultSet.next()) {
                    users.add(resultSet.getString("userid"));
                    users.add(resultSet.getString("firstname"));
                    users.add(resultSet.getString("lastname"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return users;
    }

    private void setProperties(String path) {
        try {
            InputStream input = MySQLDBClass.class.getResourceAsStream(path);
            props = new Properties();
            props.load(input);

            dbUsername = props.getProperty("username");
            dbPassword = props.getProperty("password");
            databaseName = props.getProperty("database");
            dbPort = props.getProperty("port");

            if (props.getProperty("servername") == null || props.getProperty("servername").isEmpty()) {
                dbserverName = "localhost";
            } else {
                dbserverName = props.getProperty("servername");
            }

            /*
             dbUsername = "root";//props.getProperty("username");
             dbPassword = "open24X7";//props.getProperty("password");
             databaseName = "attacksimdev";//props.getProperty("database");
             dbPort = "3306";//props.getProperty("port");
             */
        } catch (Exception ex) {
            //
        }//catch (FileNotFoundException ex) {
        //System.out.println("File not found exception");
        //Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (IOException ex) {
        //    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        //} 
    }

    boolean getAttackVectorsAndStep(int orderid) {
        try {
            createConnection();
            String query = "select count(*), step from attackdefinition where orderid = " + orderid;
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count == 0) {
                //there are no attacks yet with this order 
                return false;
            } else {
                int step = resultSet.getInt(2);
                if (step == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            closeConnection();
        }

        return false;
    }

    HashMap<String, String> getAttackDefinition(int orderId) {
        HashMap<String, String> resultMap = new HashMap<>();
        Statement tempstatement = null;
        ResultSet tempresultSet = null;
        try {
            createConnection();
            String query = "select * from attackdefinition where orderid=" + orderId;
            statement = myconnection.createStatement();
            //this should give only one result. Ideally this shouldn't be called if the orderid doesn't have a definition at all.
            resultSet = statement.executeQuery(query);
            //what is the feedconfigid so that we can find the keys to use in the map?
            resultSet.next();
            Integer feedconfigid = resultSet.getInt("feedconfigid");

            //we have the attack definition in this resultset.
            tempstatement = myconnection.createStatement();
            String query2 = "select * from feedconfiguration where id=" + feedconfigid;
            tempresultSet = tempstatement.executeQuery(query2);
            tempresultSet.next();
            //this should give only one result.
            for (int i = 1; i < 21; i++) {
                resultMap.put(tempresultSet.getString("configfield" + i), resultSet.getString("attackelement" + i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (tempstatement != null) {
                try {
                    tempstatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (tempresultSet != null) {
                try {
                    tempresultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            closeConnection();
        }

        return resultMap;
    }

    ArrayList<String> getKeyList(Integer configid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void updateStepForOrderId(int orderId) {
        String query = "update attackdefinition set step=0 where orderid=" + orderId;
        try {
            createConnection();
            statement = myconnection.createStatement();
            int res = statement.executeUpdate(query);
            //we have updated 
            if (res == 0) {
                System.out.println("Something went wrong with the update for the orderid " + orderId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            closeConnection();
        }
    }

    void setStepForOrderId(int orderId) {
        String query = "update attackdefinition set step=1 where orderid=" + orderId;
        try {
            createConnection();
            statement = myconnection.createStatement();
            int res = statement.executeUpdate(query);
            //we have updated 
            if (res == 0) {
                System.out.println("Something went wrong with the update for the orderid " + orderId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            closeConnection();
        }
    }

    void updateStepForNextOrderId(int orderid) {
        String query = "select nextorderid from attackdetails where orderid = " + orderid;
        HashMap<String, String> steps = new HashMap<>();
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);

            //I am supposed to get only one row.. currently we are not supporting multiple attacks.
            if (!resultSet.isBeforeFirst()) {
                return;
            }

            resultSet.next();
            int nextOrderid = resultSet.getInt(1);
            setStepForOrderId(nextOrderid);

        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean saveAttackDefinition(String feedtype, Integer orderid, Integer feedconfigid, Integer userid, String[] attackvectors) {
        try {
            createConnection();
            String query1 = "select count(*) from attackdefinition where orderid=" + orderid;
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query1);
            resultSet.next();
            int count = resultSet.getInt(1);

            //insert into attackdefinition (feedtype, orderid, feedconfigid, userid, attackelement1attackelement2attackelement3attackelement4attackelement5attackelement6) values ("vontu, 1, 1, 33, 2425, 4546, 2435, 2454, 23435, 234);
            if (count == 0) {
                //there is no entry and now we need to put in a new query.
                String query2 = "insert into attackdefinition (feedtype, orderid, feedconfigid, userid ";
                String query2Secondpart = "";
                for (int i = 0; i < attackvectors.length; i++) {
                    query2 += ", attackelement" + (i + 1);
                    query2Secondpart += ", \"" + attackvectors[i] + "\"";
                }
                query2 += ") values (\"" + feedtype + "\", " + orderid + ", " + feedconfigid + ", " + userid;
                query2 += query2Secondpart + ");";
                //System.out.println(query2);
                statement = myconnection.createStatement();
                statement.executeUpdate(query2);
            } else {
                String query2 = "update attackdefinition set ";
                for (int i = 0; i < attackvectors.length; i++) {
                    query2 += "attackelement" + (i + 1) + " = \"" + attackvectors[i] + "\", ";
                }
                query2 = query2.substring(0, query2.length() - 2);
                query2 += " where feedconfigid=" + feedconfigid + " and orderid=" + orderid + " and userid=" + userid + ";";
                //System.out.println(query2);
                statement = myconnection.createStatement();
                statement.executeUpdate(query2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (resultSet != null && !resultSet.isClosed()) {
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    HashMap<String, String> executeQuery(String query, String variableName) {
        HashMap<String, String> temp = null;
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();

            if (!resultSet.isBeforeFirst()) {
                //no data
            } else {
                temp = new HashMap<>();
                resultSet.next();
                int colNum = rsmd.getColumnCount();
                for (int i = 0; i < colNum; i++) {
                    temp.put(variableName + "." + rsmd.getColumnName(i + 1), resultSet.getString(i + 1));
                    if (resultSet.wasNull()) {
                        temp.put(variableName + "." + rsmd.getColumnName(i + 1), "");
                    }
                    System.out.println("Column Value: " + rsmd.getColumnName(i + 1) + "Value: " + resultSet.getString(i + 1));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in executeQuery function in MySQLDBClass");
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return temp;
    }

    HashMap<Integer, HashMap<String, String>> executeQueryForCache(String query) {
        System.out.println("Inside executeQueryForCache");
        HashMap<Integer, HashMap<String, String>> temp = null;
        HashMap<String, String> allvalues = new HashMap<>();
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            String columnname = "";
            String columnvalue = "";
            temp = new HashMap<>();
            int keyno = 1;
            int count = 0;
            while (resultSet.next()) {
                allvalues = new HashMap<>();
//		    resultSet.next();
                int colNum = rsmd.getColumnCount();
                int id = -1;
                for (int i = 1; i <= colNum; i++) {
                    columnname = rsmd.getColumnName(i);
                    columnvalue = resultSet.getString(i);
//		    System.out.println(columnname + " - " + columnvalue);

                    if (columnname.equals("id")) {
                        id = keyno++;
                    } else if (!allvalues.containsKey(columnname)) {
//			System.out.println("Added to allvalues :" + columnname + " - " + columnvalue);
                        allvalues.put(columnname, columnvalue);
                    }
                }
                if (id != -1) {
                    if (!temp.containsKey(id)) {
                        System.out.println("Putting values in hash table for id : " + id + "-" + allvalues);
                        count++;
                        temp.put(id, allvalues);
                        id = -1;
                    }
                }
            }
            System.out.println("Populated hashtable with count : " + count);

        } catch (SQLException ex) {
            System.out.println("Exception occured in executeQuery function in MySQLDBClass");
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return temp;
    }

    ResultSet executeQueryReturnResultSet(String query) {

        ResultSet temp = null;
        try {
            createConnection();
            statement = myconnection.createStatement();
            temp = statement.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Exception occured in executeQuery function in MySQLDBClass");
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return temp;
    }

    String executeQueryReturnString(String query) {
        String temp = null;
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();

            if (!resultSet.isBeforeFirst()) {
                //no data
            } else {
                resultSet.next();
                int colNum = rsmd.getColumnCount();
                for (int i = 0; i < colNum; i++) {
                    temp = resultSet.getString(i + 1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in executeQuery function in MySQLDBClass");
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return temp;
    }

    ArrayList<String> executeCountQuery(String query) {
        ArrayList<String> temp = null;
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                //no data
            } else {
                temp = new ArrayList<>();
                while (resultSet.next()) {
                    temp.add(resultSet.getString(1));
                    if (resultSet.wasNull()) {
                        temp.add("");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in executeQuery function in MySQLDBClass");
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return temp;
    }

    ArrayList<String> executeCountQuery2(String query) {
        ArrayList<String> temp = null;
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                //no data
            } else {
                temp = new ArrayList<>();
                while (resultSet.next()) {

                    String iprangebegin = null;
                    String iprangeend = null;
                    iprangebegin = resultSet.getString("iprangebegin");
                    iprangeend = resultSet.getString("iprangeend");
                    
                    temp.add(iprangebegin);
                    temp.add(iprangeend);

//                    temp.add(resultSet.getString(1));
//                    if (resultSet.wasNull()) {
//                        temp.add("");
//                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in executeQuery function in MySQLDBClass");
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return temp;
    }

    HashMap<String, String> executeQuery(String query, String countQuery, String variableName) {
        HashMap<String, String> temp = null;
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            Statement countStatement = myconnection.createStatement();
            ResultSet countResultSet = countStatement.executeQuery(countQuery);
            countResultSet.next();
            if (!resultSet.isBeforeFirst()) {
                //no data
            } else {
                temp = new HashMap<>();
                int rowCount = countResultSet.getInt(1);
                //generate a random number between 1 and the row count
                Random rand = new Random();
                int random = rand.nextInt(rowCount);
                if (random == 0) {
                    random++;
                }
                resultSet.relative(random);
                int colNum = rsmd.getColumnCount();
                for (int i = 0; i < colNum; i++) {
                    temp.put(variableName + "." + rsmd.getColumnName(i + 1), resultSet.getString(i + 1));
                    if (resultSet.wasNull()) {
                        temp.put(variableName + "." + rsmd.getColumnName(i + 1), "");
                    }
                    //System.out.println("Column Value: " + rsmd.getColumnName(i+1) + "Value: " + resultSet.getString(i+1) );
                }
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in executeQuery function in MySQLDBClass");
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return temp;
    }

    int executeQuery(String query) {
        int rowCount = 0;
        ResultSet countResultSet = null;
        Statement countStatement = null;
        try {
            createConnection();
            countStatement = myconnection.createStatement();
            countResultSet = countStatement.executeQuery(query);
            countResultSet.next();
            rowCount = countResultSet.getInt(1);
        } catch (SQLException ex) {
            System.out.println("Exception occured in executeQuery(String query) function in MySQLDBClass");
            ex.printStackTrace();
        } finally {
            if (countResultSet != null) {
                try {
                    countResultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (countStatement != null) {
                try {
                    countStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            closeConnection();
        }
        return rowCount;
    }

    String getTransactionFile(String feedtype) throws FileNotFoundException {
        String filepath = "";
        try {
            String query = "select transactionfilepath from feedmaster where feedtype=\"" + feedtype + "\";";
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                //no transactionfilepath entry for this feedtype
                throw new FileNotFoundException();
            } else {
                resultSet.next();
                filepath = resultSet.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
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
            if (!resultSet.isBeforeFirst()) {
                throw new NullPointerException("No variable entry found");
            } else {
                resultSet.next();
                ArrayList<String> params = new ArrayList<>();
                String variableGenerator = resultSet.getString("variablegenerator");
                for (int i = 1; i < 6; i++) {
                    String value = resultSet.getString("params" + i);
                    if (!resultSet.wasNull()) {
                        params.add(value);
                    } else {
                        break;
                    }
                }
                rs.put("variablegenerator", variableGenerator);
                rs.put("parameters", params);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    String getUserDownloadLink(Integer secuserid, String basepath) {
        String filename = null;
        try {
            String query = "select * from users where id in (select userid from sysipusermapping where secuserid=" + secuserid + ");";
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            filename = UUID.randomUUID().toString().replaceAll("-", "");
            BufferedWriter out = new BufferedWriter(new FileWriter(basepath + "/downloads/" + filename + ".csv"));
            int columnCount = rsmd.getColumnCount();
            if (!resultSet.isBeforeFirst()) {
                throw new NullPointerException("No variable entry found");
            } else {
                while (resultSet.next()) {
                    String writeToFile = resultSet.getString(1);
                    for (int i = 2; i < columnCount + 1; i++) {
                        String tofile = resultSet.getString(i);
                        if (tofile == null || tofile.equalsIgnoreCase("null")) {
                            writeToFile += ",";
                        } else {
                            writeToFile += "," + tofile;
                        }
                    }
                    out.write(writeToFile);
                    out.newLine();
                }
                out.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }

                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }

                closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "/downloads/" + filename + ".csv";
    }

    public HashMap<String, ArrayList<AttackOrders>> getListofAvailableAttacks(Integer secuserid) {
        //get the list of available attacks for a particular user.
        String query = "select * from attackconfiguration where feedtype in (select distinct feedtype from orders where userid=" + secuserid + ");";
        HashMap<String, ArrayList<AttackOrders>> returnResult = null;
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            returnResult = new HashMap<>();

            if (!resultSet.isBeforeFirst()) {
                return null;
            } else {
                while (resultSet.next()) {
                    if (returnResult.containsKey(resultSet.getString("feedtype"))) {
                        returnResult.get(resultSet.getString("feedtype")).add(new AttackOrders(resultSet.getString("description"), resultSet.getString("id"), resultSet.getString("transactionfile")));
                    } else {
                        returnResult.put(resultSet.getString("feedtype"), new ArrayList<AttackOrders>());
                        returnResult.get(resultSet.getString("feedtype")).add(new AttackOrders(resultSet.getString("description"), resultSet.getString("id"), resultSet.getString("transactionfile")));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }

        return returnResult;
    }

    public ArrayList<Users> getListOfUsers(Integer secuserid) {
        String query = "select * from usermaster where id in (select userid from sysipusermapping where secuserid=" + secuserid + ");";
        ArrayList<Users> listofusers = null;
        try {
            createConnection();
            statement = myconnection.createStatement();
            resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            if (!resultSet.isBeforeFirst()) {
                //no data here..
            } else {
                listofusers = new ArrayList<>();
                while (resultSet.next()) {
                    Users user = new Users(resultSet.getString("id"), resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("account1"));
                    listofusers.add(user);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }

        return listofusers;
    }
}
