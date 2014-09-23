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
public class SequentialGenerator extends ValueGeneratorType{
    private Long currentSeqVal;
    
    public SequentialGenerator(String variableName, ArrayList<String> params) {
        super(variableName, params);
        if(params == null || params.isEmpty()){
            currentSeqVal = 0L;
        }else{
            currentSeqVal = Long.parseLong(params.get(0));
        }
    }

    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException, NumberFormatException {
        HashMap<String, String> temp = new HashMap<>();
        temp.put(variableName, currentSeqVal.toString());
        currentSeqVal++;
        return temp;
    }
}
