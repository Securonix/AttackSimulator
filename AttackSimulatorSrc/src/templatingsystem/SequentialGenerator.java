/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package templatingsystem;

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
        if(params == null || params.size() == 0){
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public HashMap<String, String> getValue() throws OperationNotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
