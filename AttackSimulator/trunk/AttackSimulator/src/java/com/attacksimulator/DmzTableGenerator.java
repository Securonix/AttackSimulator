/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attacksimulator;

import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author securonix
 */
public class DmzTableGenerator extends ValueGeneratorType{
    private String query;
    private MySQLDBClass mydb;
    private String secuserid;
    
    public DmzTableGenerator(String variableName, ArrayList<String> params) {
        super(variableName, params);
        secuserid = params.get(params.size()-1);
        mydb = new MySQLDBClass();
    }

    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException, NumberFormatException {
        String countQuery = "select count(*) from dmzusermapping where secuserid="+secuserid+";";
        query = "select * from dmzusermapping where secuserid="+secuserid+";";
        HashMap<String, String> temp = mydb.executeQuery(query, countQuery, variableName);        
        return temp;
    }
}
