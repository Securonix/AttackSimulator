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
    
    /*
    * Key for the params for Table Value Generator:
    * 1. Table Name
    * 2. Column Name
    * 3. This is the first where clause , ResultSet only for a particular value (Select columnName from TableName where "userid=value")
    * 4. Onwards this will be anded and will all be part of the where clause;
    */

    public TableValueGenerator(String variableName, ArrayList<String> params){
        super(variableName, params);
        mydb = new MySQLDBClass();
    }

    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException{
        /*
        * Depending on the number of parameters that are there in the arraylist we will interpret what needs to be done with the query.
        * 1. Table Name
        * 2. Where conditions.From 2 onwards whatever we have is just part of the where condition of the table query.
        */
        
        String countQuery = "select count(*) from " + params.get(0);
        query = "select * from "+ params.get(0) + "";
        
        if(params.size() > 1){
            query +=" where ";
            countQuery += " where ";
            for (String param : params){
                query += param;
                countQuery += param;
            }
        }
        
        query += ";";
        countQuery += ";";
        
        HashMap<String, String> temp = mydb.executeQuery(query, countQuery, variableName);
        
        return temp;
    }
}
