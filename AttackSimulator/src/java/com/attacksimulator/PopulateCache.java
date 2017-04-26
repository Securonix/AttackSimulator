/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prafulilamkar
 */
public class PopulateCache
{

    private MySQLDBClass mydb;

    public static HashMap<String, String> cachedTables;
//    public static HashMap<String, String> tablestobecached;

    HashMap<String, HashMap<String, String>> cachetable1;

//    public PopulateCache()
//    {
//	tablestobecached = new ArrayList<>();
//    }
//    public void getAllTableNames()
//    {
//	String query = "select id,tablename from populatecache";
//	ResultSet resultSet = mydb.executeQueryReturnResultSet(query);
//	ResultSetMetaData rsmd = null;
//	try
//	{
//	    rsmd = resultSet.getMetaData();
//	}
//	catch (SQLException ex)
//	{
//	    Logger.getLogger(PopulateCache.class.getName()).log(Level.SEVERE, null, ex);
//	}
//
//	try
//	{
//	    if (!resultSet.isBeforeFirst())
//	    {
//		//no data
//	    }
//	    else
//	    {
//		tablestobecached = new HashMap<>();
//		resultSet.next();
//		int colNum = rsmd.getColumnCount();
//
//		if (!tablestobecached.containsKey(resultSet.getString(0)))
//		{
//		    System.out.println(resultSet.getString(0) + " - " + resultSet.getString(1));
//		    tablestobecached.put(resultSet.getString(0), resultSet.getString(1));
//		}
//		if (resultSet.wasNull())
//		{
//		}
//		    //System.out.println("Column Value: " +  rsmd.getColumnName(i+1) + "Value: " + resultSet.getString(i+1) );
//	    }
//	}
//	catch (SQLException ex)
//	{
//	    Logger.getLogger(PopulateCache.class.getName()).log(Level.SEVERE, null, ex);
//	}
//
//    }
    public HashMap<Integer, HashMap<String, String>> createHashtable(String query)
    {
	mydb = new MySQLDBClass();
//	System.out.println("Creating Hashtable");
	HashMap<Integer, HashMap<String, String>> hashtable = new HashMap<>();
//	ArrayList<String> allvalues = new ArrayList<>();
        System.out.println("com.attacksimulator.PopulateCache.createHashtable() - new query");
	

	System.out.println("query - " + query);
	hashtable = mydb.executeQueryForCache(query);

	return hashtable;
    }

}
