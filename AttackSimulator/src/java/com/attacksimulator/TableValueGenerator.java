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

/**
 *
 * @author securonix
 */

public class TableValueGenerator extends ValueGeneratorType{
    private String query;
    private MySQLDBClass mydb;
    private Random random;
    private ArrayList<String> list;
    /*
    * Key for the params for Table Value Generator:
    * 1. Table Name
    * 2. Column Name
    * 3. This is the first where clause , ResultSet only for a particular value (Select columnName from TableName where "userid=value")
    * 4. Onwards this will be anded and will all be part of the where clause;
    */

    public TableValueGenerator(String variableName, ArrayList<String> params){
        super(variableName, params);
        if (params == null || params.isEmpty()){
            throw new UnsupportedOperationException("Table value generator needs the table name");
        }
        mydb = new MySQLDBClass();
        random = new Random();
        list = null;
    }

    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException{
        /*
        * Depending on the number of parameters that are there in the arraylist we will interpret what needs to be done with the query.
        * 1. Table Name
        * 2. Where conditions.From 2 onwards whatever we have is just part of the where condition of the table query.
        */

        String countQuery = "select id from " + params.get(0);

        query = "select * from " + params.get(0);

        // params.get(0) = dmzusermapping
        // params.get(1) = dmzhostname = 'VPNConcentrator'

        if(params.size() > 1){
            countQuery += " where ";
            int counter=0;

            for (String param : params){
                if (counter++ > 0)
                    countQuery += param;

            }
        }
        countQuery += ";";

        System.out.println("CountQuery- " + countQuery);
        if(list == null){
            list = new ArrayList<>();
            list =mydb.executeCountQuery(countQuery);
        }
        
        if(params.size() > 1){
            query +=" where ";
             int counter=0;
            for (String param : params){
                if (counter++ > 0)
                query += param;
            }
            query += " and id=" + list.get(randomValueGenerate(list.size()));
        }else{
            query += " where id=" + list.get(randomValueGenerate(list.size()));
        }

        query += ";";
        System.out.println("Query:" + query);
        HashMap<String, String> temp = mydb.executeQuery(query, variableName);

        return temp;
    }

    private int randomValueGenerate(int maxVal){
        int index = random.nextInt(maxVal);
        return index;
    }
}