/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package templatingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author securonix
 */
public class RandomValueGenerator extends ValueGeneratorType{

    public RandomValueGenerator(String variableName, ArrayList<String> params) {
        super(variableName, params);
    }

    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException, NumberFormatException{
        /*
        * Here the parameters would mean the bounds in which the random number needs to be generated.
        * 1. If the size of params is 1 this number will be treated as the upper bound of the random number generation.
        * 2. If the size of the params list is 2, the first param is the lower limit and the second param is the upper limit.
        * If there are no params.. just generate a number and send it.
        */
        
        HashMap<String, String> temp = new HashMap<>();
        Random rand = new Random();
        
        if(params == null || params.isEmpty()){
            //just generate a random number and send it.
            temp.put(variableName, ((Integer)(rand.nextInt())).toString());
            return temp;
        }else if(params.size() == 1){
            temp.put(variableName, ((Integer)(rand.nextInt(Integer.parseInt(params.get(0))))).toString());
            return temp;
        }else if(params.size() == 2){
            Integer randomNumber = rand.nextInt(Integer.parseInt(params.get(1)) - Integer.parseInt(params.get(0)) + 1) + Integer.parseInt(params.get(0));
            temp.put(variableName, randomNumber.toString());
            return temp;
        }else{
            throw new UnsupportedOperationException("We support only two parameters for this variable.");
        }
    }
}
