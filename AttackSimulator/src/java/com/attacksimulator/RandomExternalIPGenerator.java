/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import static groovy.sql.Sql.resultSet;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.naming.OperationNotSupportedException;

/**
 *
 * @author securonix
 */
public class RandomExternalIPGenerator extends ValueGeneratorType {
    
    private String query;
    private MySQLDBClass mydb;
    private String secuserid;
    private ArrayList<String> list;

    /*
    * Key for the params for Table Value Generator:
     */
    public RandomExternalIPGenerator(String variableName, ArrayList<String> params) {
        super(variableName, params);
        if (params == null || params.isEmpty()) {
            throw new UnsupportedOperationException("Table value generator needs the table name");
        }
        mydb = new MySQLDBClass();
        secuserid = params.get(params.size() - 1);
        System.out.println("RandomExternalIPGenerator params : ");
        for (String p : params) {
            System.out.println(p);
        }
        
        list = null;
    }
    
    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException {
        /*
        * Depending on the number of parameters that are there in the arraylist we will interpret what needs to be done with the query.
        * 1. when 1 param present, it considers it to be securserid
        * 2. When 2 parameters, the first parameter is country (matches to select country from ipcountry), 2nd param is secuserid
         */
        String country = null;
        HashMap<String, String> temp = new HashMap<>();
        
        
        if(params.size() > 1) {
            country = params.get(0);
            System.out.println("Getting country from params "+ country);
        }
            
        
        if(country == null) {
            String countQuery = "select id from extusermapping";
            query = "select country from extusermapping";
            if (params.size() > 1) {
                countQuery += " where ";
                int counter = 0;
                
                for (String param : params) {
                    if (++counter <= params.size() - 1) {
                        countQuery += param;
                    }
                }
                countQuery += " and secuserid=" + secuserid + ";";
            } else {
                countQuery += " where secuserid=" + secuserid + ";";
            }
            
            System.out.println("CountQuery- " + countQuery);
            if (list == null) {
                list = new ArrayList<String>();
                list = mydb.executeCountQuery(countQuery);
            }
            
            int randomIndex = randomValueGenerate(list.size());
            System.out.println("RandomIndex=" + randomIndex);
            
            if (params.size() > 1) {
                query += " where ";
                int counter = 0;
                for (String param : params) {
                    if (++counter <= params.size() - 1) {
                        query += param;
                    }
                }
                query += " and id=" + list.get(randomIndex);
            } else {
                query += " where id=" + list.get(randomIndex);
            }
            
            query += " and secuserid=" + secuserid + ";";
            
            System.out.println("Query:" + query);
            country = mydb.executeQueryReturnString(query);
            
        }
        
        System.out.println("Country Selected "+ country);
        
        if (country != null) {
            
            String countQuery = "select id from ipcountry ";
            query = "select * from ipcountry ";
            if (params.size() > 1) {
                countQuery += " where ";
                int counter = 0;
                
                for (String param : params) {
                    if (++counter <= params.size() - 1) {
                        countQuery += param;
                    }
                }
                countQuery += " and country='" + country + "';";
            } else {
                countQuery += " where country='" + country + "';";
            }
            
            System.out.println("CountQuery- " + countQuery);
            if (list == null) {
                list = new ArrayList<String>();
                list = mydb.executeCountQuery(countQuery);
            }
            
            int randomIndex = randomValueGenerate(list.size());
            System.out.println("RandomIndex=" + randomIndex);
            
            if (params.size() > 1) {
                query += " where ";
                int counter = 0;
                for (String param : params) {
                    if (++counter <= params.size() - 1) {
                        query += param;
                    }
                }
                query += " and id=" + list.get(randomIndex);
            } else {
                query += " where id=" + list.get(randomIndex);
            }
//            query += " and country='" + secuserid + "';";
            
//            System.out.println("Query:" + query);
//            ResultSet resultSet = mydb.executeQueryReturnResultSet(query);
//            
//            
//            String iprangebegin = null;
//            String iprangeend = null;
////
//            try {
//                    temp = new HashMap<>();
//                    resultSet.next();
//                    iprangebegin = resultSet.getString("iprangebegin");
//                    iprangeend = resultSet.getString("iprangeend");
//                    Long iprangebeginLong = ipToLong(iprangebegin);
//                    Long iprangeendLong = ipToLong(iprangeend);
//                    int randomIndex2 = randomValueGenerate(iprangeendLong.intValue()-iprangebeginLong.intValue());
//                    iprangebeginLong = iprangebeginLong+randomIndex2;
//                    String FinalIP = longToIp(iprangebeginLong);
//                    System.out.println("Final Ip selected for country "+ country+ ": "+ FinalIP);
//                    temp.put(variableName, FinalIP);
//                
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }


            ArrayList<String> iplist =  mydb.executeCountQuery2(query);
            System.out.println("iplistrange "+ iplist);
            String iprangebegin = iplist.get(0);
            String iprangeend = iplist.get(1);;

            Long iprangebeginLong = ipToLong(iprangebegin);
            Long iprangeendLong = ipToLong(iprangeend);
            int randomIndex2 = randomValueGenerate(iprangeendLong.intValue()-iprangebeginLong.intValue());
            iprangebeginLong = iprangebeginLong+randomIndex2;
            String FinalIP = longToIp(iprangebeginLong);
            System.out.println("Final Ip selected for country "+ country+ ": "+ FinalIP);
            temp.put(variableName, FinalIP);
            
            
            
        }
        
        return temp;
    }
    
    private int randomValueGenerate(int maxVal) {
        Random random = new Random();
        int index = random.nextInt(maxVal);
        return index;
    }
    
    
    public static long ipToLong(String ipAddress) {
        long result = 0;
        String[] atoms = ipAddress.split("\\.");

        for (int i = 3; i >= 0; i--) {
            result |= (Long.parseLong(atoms[3 - i]) << (i * 8));
        }

        return result & 0xFFFFFFFF;
    }

    public static String longToIp(long ip) {

        final StringBuilder sb = new StringBuilder(15);
        for (int i = 0; i < 4; i++) {
            sb.insert(0, Long.toString(ip & 0xff));
            if (i < 3) {
                sb.insert(0, '.');
            }
            ip >>= 8;
        }

        return sb.toString();
    }
    
    
    
}
