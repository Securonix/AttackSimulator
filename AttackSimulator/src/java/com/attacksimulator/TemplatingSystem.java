/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.OperationNotSupportedException;

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
        vgtMap = new HashMap<>();
        running = true;
        bufferAllTransactionLines();
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
            if(transactionFile.isEmpty()){
                throw new FileNotFoundException("Couldn't find the transaction file.. Maybe the entry in the db is wrong or there is none!");
            }
            System.out.println("The transaction file path is : "+transactionFile);
            
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
            Long currentFrequency = getCurrentFrequencyTest(frequency);
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
            
            //before outputting this transaction we will have to split it by the double pipe symbol and output each one in a different line.
            String [] splitTransactions = currentTransaction.split("\\|\\|");
            for(String transaction : splitTransactions){
                System.out.println(transaction);
            }
            
            try {
                Thread.sleep(currentFrequency);
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

    private Long getCurrentFrequency(String frequency) {
        String [] frequencies = frequency.split(",");
        ArrayList<Long> frequencyLong = new ArrayList<>();
        for(String freq : frequencies){
            frequencyLong.add(Long.parseLong(freq));
        }
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        int currenttime = Integer.parseInt(sdf.format(cal.getTime()));
        if(currenttime >= 0 && currenttime<9){
            return frequencyLong.get(0);
        }else if(currenttime >= 9 && currenttime < 15){
            return frequencyLong.get(1);
        }else if(currenttime >= 15 && currenttime < 18){
            return frequencyLong.get(2);
        }else if(currenttime >= 18 && currenttime < 21){
            return frequencyLong.get(3);
        }else if(currenttime >= 21 && currenttime < 23){
            return frequencyLong.get(4);
        }
        
        return 0L;
    }
    
    private Long getCurrentFrequencyTest(String frequency) {
        String [] frequencies = frequency.split(",");
        ArrayList<Long> frequencyLong = new ArrayList<>();
        for(String freq : frequencies){
            frequencyLong.add(Long.parseLong(freq));
        }
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        int currenttime = Integer.parseInt(sdf.format(cal.getTime()));
        if(currenttime >= 0 && currenttime<15){
            return frequencyLong.get(0);
        }else if(currenttime >= 15 && currenttime < 25){
            return frequencyLong.get(1);
        }else if(currenttime >= 25 && currenttime < 35){
            return frequencyLong.get(2);
        }else if(currenttime >= 35 && currenttime < 45){
            return frequencyLong.get(3);
        }else if(currenttime >= 45 && currenttime < 60){
            return frequencyLong.get(4);
        }
        
        return 0L;
    }
}
