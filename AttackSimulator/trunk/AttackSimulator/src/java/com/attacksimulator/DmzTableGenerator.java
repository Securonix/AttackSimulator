/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author securonix
 */

public class DmzTableGenerator extends ValueGeneratorType{
    private String query;
    private MySQLDBClass mydb;
    private String secuserid;
    private Random random;
    /*
    * Key for the params for Table Value Generator:
    * 1. Table Name
    * 2. Column Name
    * 3. This is the first where clause , ResultSet only for a particular value (Select columnName from TableName where "userid=value")
    * 4. Onwards this will be anded and will all be part of the where clause;
    */

    public DmzTableGenerator(String variableName, ArrayList<String> params){
        super(variableName, params);
        if (params == null || params.isEmpty()){
            throw new UnsupportedOperationException("Table value generator needs the table name");
        }
        mydb = new MySQLDBClass();
        secuserid = params.get(params.size()-1);
        System.out.println("DmzTableGenerator params : " );
        for(String p : params) {
            System.out.println(p);
        }
        random = new Random();
    }

    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException{
        /*
        * Depending on the number of parameters that are there in the arraylist we will interpret what needs to be done with the query.
        * 1. Table Name
        * 2. Where conditions.From 2 onwards whatever we have is just part of the where condition of the table query.
        */

        String countQuery = "select id from dmzusermapping";

        query = "select * from dmzusermapping";

        // params.get(0) = dmzusermapping
        // params.get(1) = dmzhostname = 'VPNConcentrator'

        if(params.size() > 1){
            countQuery += " where ";
            int counter=0;

            for (String param : params){
                if (++counter <= params.size()-1)
                    countQuery += param;
            }
            countQuery += " and secuserid=" + secuserid + ";";
        }else
        {
          countQuery += " where secuserid=" + secuserid + ";";
        }
   
        System.out.println("CountQuery- " + countQuery);
        ArrayList<String> list = mydb.executeCountQuery(countQuery);
        
        int randomIndex = randomValueGenerate(list.size());
        System.out.println("RandomIndex=" + randomIndex);

        if(params.size() > 1){
            query +=" where ";
             int counter=0;
            for (String param : params){
                if (++counter <= params.size()-1)
                
                query += param;
            }
            query += " and id=" + list.get(randomIndex);
        }else{
            query += " where id=" + list.get(randomIndex);
        }

        query += " and secuserid=" + secuserid + ";";
        System.out.println("Query:" + query);
        HashMap<String, String> temp = mydb.executeQuery(query, variableName);

        return temp;
    }

    private int randomValueGenerate(int maxVal){
        int index = random.nextInt(maxVal);
        return index;
    }
}