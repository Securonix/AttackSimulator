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
public abstract class ValueGeneratorType {
    protected ArrayList<String> params;
    protected String variableName;
    
    public ValueGeneratorType(String variableName, ArrayList<String> params){
        this.variableName = variableName;
        this.params = params;
    }
    
    public abstract HashMap<String, String> getValue() throws OperationNotSupportedException, NumberFormatException;

}
