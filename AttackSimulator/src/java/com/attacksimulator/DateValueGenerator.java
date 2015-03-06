/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author securonix
 */

public class DateValueGenerator extends ValueGeneratorType{
    
    public DateValueGenerator(String variableName, ArrayList<String> params){
        super(variableName, params);
    }
    
    @Override
    public HashMap<String, String> getValue() {
        String date = "";
        if(params == null || params.isEmpty()){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            date = sdf.format(now);
        }else if(params.size() == 1){
            if (params.get(0).equalsIgnoreCase("Epoch")) {
                date = (System.currentTimeMillis() / 1000L) + "";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(params.get(0));
                Date now = new Date();
                date = sdf.format(now);
            }
        }else if(params.size() > 1){
            throw new UnsupportedOperationException();
        }
        
        HashMap<String, String> temp = new HashMap<>();
        temp.put(variableName, date);
        return temp;
    }
}
