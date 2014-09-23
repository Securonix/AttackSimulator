/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package templatingsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.OperationNotSupportedException;
import templatingsystem.MySQLDBClass;

/**
 *
 * @author securonix
 */
public final class TemplatingSystem {
    private String transactionFile;
    private ArrayList<String> transactions;
    private HashMap<String, ValueGeneratorType> vgtMap;
    private volatile boolean running;
    
    public TemplatingSystem(){
        transactionFile = "";
        transactions = new ArrayList<>();
        vgtMap = new HashMap<>();
        running = true;
    }
    
    public TemplatingSystem(String feedtype){
        transactionFile = getTransactionFile(feedtype);
        transactions = new ArrayList<>();
        bufferAllTransactionLines();
        vgtMap = new HashMap<>();
        running = true;
    }
    
    public String getTransactionFile(String feedtype){
        try {
            return (new MySQLDBClass()).getTransactionFile(feedtype);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void bufferAllTransactionLines(){
        BufferedReader br = null;
        try{
            transactionFile = "transactionFile.txt";
            br = new BufferedReader(new FileReader(transactionFile));
            String lineRead;
            
            while((lineRead = br.readLine()) != null){
                if(transactions == null){
                    transactions = new ArrayList<>();
                }
                transactions.add(lineRead);
                //find all the values in the {{ .* }} 
                String pattern = "\\{\\{([\\w\\.]+)\\}\\}";
                Pattern pat = Pattern.compile(pattern);
                Matcher matcher = pat.matcher(lineRead);
                while(matcher.find()){
                    String variable = matcher.group(1);
                    //System.out.println(lineRead.replace(matcher.group(), matcher.group(1)));
                    //check if this variable exists in the HashMap
                    //the variable could have a point(.) before it which would mean that 
                    if(variable.contains(".")){
                        variable = variable.split("\\.")[0];
                    }
                    
                    if(vgtMap != null && !vgtMap.containsKey(variable)){
                        //get the row from the templatemaster table;
                        try{
                            HashMap<String, Object> resultSet = (new MySQLDBClass()).getTemplateMasterValues(variable);
                            String valueGen = (String)resultSet.get("variablegenerator");
                            ArrayList<String> params = (ArrayList<String>)resultSet.get("parameters");
                            ValueGeneratorType temp = null;
                            switch(valueGen){
                                case "tablevaluegenerator" :
                                     temp = new TableValueGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "datevaluegenerator" :
                                    temp = new DateValueGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "randomvaluegenerator" :
                                    temp = new RandomValueGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "sequentialvaluegenerator":
                                    temp = new SequentialGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                            }
                        }catch(NullPointerException ex){
                            ex.getMessage();
                        }
                    }
                }
            }
        }catch(FileNotFoundException ex){
            Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(br != null){
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void generateFeed(Integer userid, String destinationip, String destionationport, String frequency){
        Random random = new Random();
        
        while(running){
            /*
            * Here we have to put in the intelligence to first look at the configuration of the feed and then generate only the feeds
            * in the sequence that was specified. For testing purpose this has not been implemented yet.
            * @TODO
            */
            int index = random.nextInt(transactions.size());
            String currentTransaction = transactions.get(index);
            
            String pattern = "\\{\\{([\\w\\.]+)\\}\\}";
            Pattern pat = Pattern.compile(pattern);
            Matcher matcher = pat.matcher(currentTransaction);
            HashSet<String> variablesFound = new HashSet<>();
            while(matcher.find()){
                String var = matcher.group(1);
                if(var.contains(".")){
                    var = var.split("\\.")[0];
                    HashMap<String, String> res = null;
                    try {
                        res = vgtMap.get(var).getValue();
                        String pattempstr = "\\{\\{("+var+"\\.\\w+)\\}\\}";
                        Pattern pattemp = Pattern.compile(pattempstr);
                        Matcher matchertemp = pattemp.matcher(currentTransaction);
                        while(matchertemp.find()){
                            currentTransaction = currentTransaction.replace(matchertemp.group(), res.get(matchertemp.group(1)));
                        }
                    } catch (OperationNotSupportedException ex) {
                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                }else{
                    try {          
                        currentTransaction = currentTransaction.replace(matcher.group(), vgtMap.get(matcher.group(1)).getValue().get(matcher.group(1)));
                    } catch (OperationNotSupportedException ex) {
                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            System.out.println(currentTransaction);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stopGeneratingFeed(){
        running = false;
    }
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TemplatingSystem ts = new TemplatingSystem();
        ts.bufferAllTransactionLines();
        ts.generateFeed(null, null, null, null);
    }
}
