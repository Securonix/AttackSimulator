/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    PopulateCache populatecache = new PopulateCache();
    public static HashMap<Integer,HashMap<Integer, HashMap<String, String>>> allCachedTables = new HashMap<>();

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
	HashMap<Integer, HashMap<String, String>> cachetable = null;
	String tablename=params.get(0);	
	int hashcode = tablename.hashCode();
	HashMap<String, String> allvalues ;

	if(hashcode<0)
	    hashcode=hashcode*-1;
	

        query = "select * from " + tablename;
      
        if(params.size() > 1){
            query +=" where ";
             int counter=0;
            for (String param : params){
                if (counter++ > 0)
                query += param;
            }
        }
        	

	if(!allCachedTables.containsKey(hashcode))
	{
            	System.out.println("tablename : hashcode - " + tablename +" : "+hashcode);
	    System.out.println("Creating Cache for table : " + tablename);
	    if(!allCachedTables.containsKey(hashcode))
	    {
                System.out.println("tablename : query - " + tablename +" : "+query);
                cachetable = populatecache.createHashtable(query);
		allCachedTables.put(hashcode, cachetable);
	    }
	  System.out.println("Cache created for table : " + tablename);

	}
        else
        {
          cachetable=allCachedTables.get(hashcode);
        }
	
	//System.out.println("cachetable retrived with size : " + cachetable.size());

	allvalues = new HashMap<>();
//	cachetable=cachetable.get
	int randomId = randomValueGenerate(cachetable.size());
	
	allvalues=cachetable.get(randomId);
	String columnname="";
	String columnvalue="";
	HashMap<String, String> temp2=new HashMap<>();
	
	temp2.put(variableName + "." + "id", randomId+"");
//        System.out.println("variableName"+ variableName + "." + "id - " + randomId );

//        System.out.println("com.attacksimulator.TableValueGenerator.getValue() : allvalues " + allvalues.size());
	for (Iterator<String> iterator = allvalues.keySet().iterator(); iterator.hasNext();)
	{
	    columnname = iterator.next();
	    columnvalue=allvalues.get(columnname);
	    temp2.put(variableName + "." + columnname, columnvalue);
//            System.out.println("variableName"+ variableName + "." + "columnname - " + columnname + " : columnvalue -"+ columnvalue);

	}
	
//        String countQuery = "select id from " + params.get(0);
//        System.out.println("com.attacksimulator.TableValueGenerator.getValue() creating old query");
//        query = "select * from " + params.get(0);

        // params.get(0) = dmzusermapping
        // params.get(1) = dmzhostname = 'VPNConcentrator'
//
//        if(params.size() > 1){
//            countQuery += " where ";
//            int counter=0;
//
//            for (String param : params){
//                if (counter++ > 0)
//                    countQuery += param;
//
//            }
//        }
//        countQuery += ";";
//
//        System.out.println("CountQuery- " + countQuery);
//        if(list == null){
//            list = new ArrayList<>();
//            list =mydb.executeCountQuery(countQuery);
//        }
        
//        if(params.size() > 1){
//            query +=" where ";
//             int counter=0;
//            for (String param : params){
//                if (counter++ > 0)
//                query += param;
//            }
//            query += " and id=" + list.get(randomValueGenerate(list.size()));
//        }else{
//            query += " where id=" + list.get(randomValueGenerate(list.size()));
//        }
//
//        query += ";";
//        System.out.println("Query:" + query);
        
        
//        HashMap<String, String> temp = mydb.executeQuery(query, variableName);
//        System.out.println("com.attacksimulator.TableValueGenerator.getValue() : temp" + temp);
//                System.out.println("com.attacksimulator.TableValueGenerator.getValue() : temp2" + temp2);

        return temp2;
    }

    private int randomValueGenerate(int maxVal){
        int index = random.nextInt(maxVal);
	if(index==0)
	    index=1;
        return index;
    }
}