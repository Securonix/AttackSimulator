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
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author anjan
 */
public class RandomStringGenerator extends ValueGeneratorType {
    
    public RandomStringGenerator(String variableName, ArrayList<String> params) {
        super(variableName, params);
    }
    
    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException, NumberFormatException{
        
        /*
        * Here the parameters would mean the bounds in which the random number needs to be generated.
        * 1. If the size of params is 1, that is the length of the string to be returned. 
        * If there are no params.. just generate a string of size 10.
        */
        
        HashMap<String, String> temp = new HashMap<>();
        Random rand = new Random();
        
        if(params == null || params.isEmpty()){
            //just generate a random number and send it.
            temp.put(variableName, RandomStringUtils.randomAlphanumeric(10));
            return temp;
        } else if(params.size() == 1){
            temp.put(variableName, RandomStringUtils.randomAlphanumeric(Integer.parseInt(params.get(1))));
            return temp;
        } else{
            throw new UnsupportedOperationException("We support only two parameters for this variable.");
        }
        
        
    }
    
}
