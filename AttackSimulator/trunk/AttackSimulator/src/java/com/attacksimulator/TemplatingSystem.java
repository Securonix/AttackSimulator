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
import java.util.Date;
import java.util.HashMap;
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

    private final String transactionFile;
    private ArrayList<String> transactions;
    private final HashMap<String, ValueGeneratorType> vgtMap;
    private volatile boolean running;
    private final ArrayList<Integer> transactionWeights;
    private final ArrayList<Integer> weightedTransactionIds;
    private final Random rand;
    private SyslogUtility syslogUtility;
    private FileWriterUtility fileWriterUtility;

    public TemplatingSystem() {
        System.out.println("Hello 1");
        transactionFile = "";
        transactions = new ArrayList<>();
        vgtMap = new HashMap<>();
        running = true;
        rand = new Random();
        transactionWeights = new ArrayList<>();
        weightedTransactionIds = new ArrayList<>();
        fileWriterUtility = new FileWriterUtility();
    }

    public TemplatingSystem(String feedtype, Integer userid) {
        System.out.println("Hello 2");
        transactionFile = getTransactionFile(feedtype);
        transactions = new ArrayList<>();
        vgtMap = new HashMap<>();
        running = true;
        transactionWeights = new ArrayList<>();
        weightedTransactionIds = new ArrayList<>();
        bufferAllTransactionLines(userid);
        rand = new Random();
        fileWriterUtility = new FileWriterUtility();
    }

    /*
     *   Use this constructor for attacks
     */
    public TemplatingSystem(String transactionfile, String attackerid, String secuserid) {
        System.out.println("Hello 3");
        transactionFile = transactionfile;
        transactions = new ArrayList<>();
        vgtMap = new HashMap<>();
        running = true;
        transactionWeights = new ArrayList<>();
        weightedTransactionIds = new ArrayList<>();
        bufferAllTransactionLines(Integer.parseInt(secuserid));
        rand = new Random();
        fileWriterUtility = new FileWriterUtility();
    }

    public String getTransactionFile(String feedtype) {
        try {
            return (new MySQLDBClass()).getTransactionFile(feedtype);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void bufferAllTransactionLines(Integer userid) {
        //This function buffers the transaction lines and builds the array that will be used to generate the feed with weights.
        System.out.println("===== Enter bufferAllTransactionLines =====");
        BufferedReader br = null;
        try {
            if (transactionFile.isEmpty()) {
                throw new FileNotFoundException("Couldn't find the transaction file.. Maybe the entry in the db is wrong or there is none!");
            }
            System.out.println("The transaction file path is : " + transactionFile);

            br = new BufferedReader(new FileReader(transactionFile));
            String lineRead;

            while ((lineRead = br.readLine()) != null) {
                if (transactions == null) {
                    transactions = new ArrayList<>();
                }

                //lines that have a # in the beginning need to not be read as transactions.
                //lines that are empty need to be discarded.
                lineRead = lineRead.trim();
                if (lineRead.contains("####")) {
                    //this isa line that has to either completely be ignored or ignore part of it.
                    int hashIndex = lineRead.indexOf("####");
                    if (hashIndex == 0) {
                        continue; //ignoring the entire line.
                    } else if (hashIndex > 0) {
                        lineRead = lineRead.substring(0, hashIndex);
                    }
                }

                if (lineRead.trim().isEmpty()) {
                    continue;
                }

                String[] splitLine = lineRead.split("<<");

                transactions.add(splitLine[0].trim());
//                try{
                transactionWeights.add(Integer.parseInt(splitLine[1].trim()));
//                }catch(ArrayIndexOutOfBoundsException ex){
//                	System.out.println("The offending line: " + lineRead);
//                }
                //find all the values in the {{ .* }} 
                String pattern = "\\{\\{([\\w\\.]+)\\}\\}";
                Pattern pat = Pattern.compile(pattern);
                Matcher matcher = pat.matcher(lineRead);
                while (matcher.find()) {
                    String variable = matcher.group(1);
                    //System.out.println(lineRead.replace(matcher.group(), matcher.group(1)));
                    //check if this variable exists in the HashMap
                    //the variable could have a point(.) before it which would mean that 
                    if (variable.contains(".")) {
                        variable = variable.split("\\.")[0];
                    }

                    System.out.println("For variable " + variable);
                    if (vgtMap != null && !vgtMap.containsKey(variable)) {
                        //get the row from the templatemaster table;
                        try {
                            HashMap<String, Object> resultSet = (new MySQLDBClass()).getTemplateMasterValues(variable);
                            String valueGen = (String) resultSet.get("variablegenerator");
                            ArrayList<String> params = (ArrayList<String>) resultSet.get("parameters");
                            ValueGeneratorType temp = null;
                            switch (valueGen) {
                                case "tablevaluegenerator":
                                    temp = new TableValueGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "datevaluegenerator":
                                    temp = new DateValueGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "randomvaluegenerator":
                                    temp = new RandomValueGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "randomstringgenerator":
                                    temp = new RandomStringGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "sequentialvaluegenerator":
                                    temp = new SequentialGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "usermastertablegenerator":
                                    if (params != null) {
                                        params.add(userid.toString());
                                    } else {
                                        params = new ArrayList<>();
                                        params.add(userid.toString());
                                    }
                                    temp = new UsermasterTableGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    System.out.println("\tAdding " + params);
                                    break;
                                case "dmztablegenerator":
                                    if (params != null) {
                                        params.add(userid.toString());
                                    } else {
                                        params = new ArrayList<>();
                                        params.add(userid.toString());
                                    }
                                    temp = new DmzTableGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "randomexternalipgenerator":
                                    if (params != null) {
                                        params.add(userid.toString());
                                    } else {
                                        params = new ArrayList<>();
                                        params.add(userid.toString());
                                    }
                                    temp = new RandomExternalIPGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                                case "sysipusermappinggenerator":
                                    if (params != null) {
                                        params.add(userid.toString());
                                    } else {
                                        params = new ArrayList<>();
                                        params.add(userid.toString());
                                    }
                                    temp = new SysipUserMappingGenerator(variable, params);
                                    vgtMap.put(variable, temp);
                                    break;
                            }
                        } catch (NullPointerException ex) {
                            ex.getMessage();
                        }
                    }
                }
            }

//            System.out.println("vgtMap size " + vgtMap.size());
            //first sum up all the weights that we got from the transaction file.
            int sumOfWeights = 0;
            for (Integer weight : transactionWeights) {
                sumOfWeights += weight;
            }

            //Find the factor that is needed to get to 1000.
            double factor = 1000.0 / sumOfWeights;

            //multiply this factor to all the weights.
            ArrayList<Integer> weights1000 = new ArrayList<>();
            int sumWhilePuttingItIn = 0;
            for (Integer weight : transactionWeights) {
                sumWhilePuttingItIn += (int) (Math.floor(weight * factor));
                weights1000.add((int) (Math.ceil(weight * factor)));
            }

            if (sumWhilePuttingItIn != 1000) {
                int diff = 1000 - sumWhilePuttingItIn;
                int value = weights1000.get(weights1000.size() - 1);
                weights1000.set(weights1000.size() - 1, value + diff);
            }

            for (int i = 0; i < weights1000.size(); i++) {
                int index = weights1000.get(i);
                for (int j = 0; j < index; j++) {
                    weightedTransactionIds.add(i);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        System.out.println("===== Exit bufferAllTransactionLines =====");
    }

    public void generateFeed(Integer userid, String destinationip, String destionationport, String frequency, String feedtype, Integer orderid, String factorString) {
        syslogUtility = new SyslogUtility(feedtype + orderid, destinationip, destionationport);
        HashMap<String, HashMap<String, String>> varResultBuffer;
        String[] forwardDataTableinfo = {};
        try {
            forwardDataTableinfo = (new MySQLDBClass()).getTablenametoforwarddata(feedtype); //forward data to this table, assumption that it already exists
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (running) {
            /*
             * Here we have to put in the intelligence to first look at the configuration of the feed and then generate only the feeds
             * in the sequence that was specified. For testing purpose this has not been implemented yet.
             * @TODO
             */
            //int index = random.nextInt(transactions.size());

            int index = getRandomizedIndex();
            varResultBuffer = new HashMap<String , HashMap<String, String>>();
            String currentTransaction = transactions.get(index);
            Long currentFrequency = getCurrentFrequencyTest(frequency);
            String pattern = "\\{\\{([\\w\\.]+)\\}\\}";
            Pattern pat = Pattern.compile(pattern);
            Matcher matcher = pat.matcher(currentTransaction);
            while (matcher.find()) {
                String var = matcher.group(1);
                if (var.contains(".")) {
                    var = var.split("\\.")[0];
//                    System.out.println("vgtMap size " + vgtMap.size());
//                    System.out.println("var " + var);
//                    System.out.println("Contains? " + vgtMap.containsKey(var));

                    HashMap<String, String> res = null;
                    try {
                        if (varResultBuffer.containsKey(var)) {
                            res = varResultBuffer.get(var);
                        } else {
                            res = vgtMap.get(var).getValue();
                            varResultBuffer.put(var, res);
                        }
                        String pattempstr = "\\{\\{(" + var + "\\.\\w+)\\}\\}";
                        Pattern pattemp = Pattern.compile(pattempstr);
                        Matcher matchertemp = pattemp.matcher(currentTransaction);
                        while (matchertemp.find()) {
                            //System.out.println("matcher group " + matchertemp.group() + "-- Resource get " + res.get(matchertemp.group(1)) + "-- matcher temp group -- " + matchertemp.group(1));
                            if ("null".equals(res.get(matchertemp.group(1))) || res.get(matchertemp.group(1)) == null) {
                                currentTransaction = currentTransaction.replace(matchertemp.group(), "");
                            } else {
                                currentTransaction = currentTransaction.replace(matchertemp.group(), res.get(matchertemp.group(1)));
                            }
//                            currentTransaction = currentTransaction.replace(matchertemp.group(), res.get(matchertemp.group(1)));

                        }
                    } catch (OperationNotSupportedException ex) {
                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        currentTransaction = currentTransaction.replace(matcher.group(), vgtMap.get(matcher.group(1)).getValue().get(matcher.group(1)));
                    } catch (OperationNotSupportedException ex) {
                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            //before outputting this transaction we will have to split it by the double pipe symbol and output each one in a different line.
            String[] splitTransactions = currentTransaction.split("\\|\\|");
            for (String transaction : splitTransactions) {
//                System.out.println(transaction.trim());
                syslogUtility.publishString(transaction.trim());
                if(forwardDataTableinfo != null && forwardDataTableinfo.length>0 ){
                    String temp= "'"+transaction.replaceAll("\\|"," ' , ' ")+" '";
                    
                    (new MySQLDBClass()).saveForwardedData(forwardDataTableinfo,temp); //save to table 
                    
                }
                //fileWriterUtility.writeString(transaction.trim());
            }
            //Find the current day of the week.
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            Long factor = Long.parseLong(factorString);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1 || dayOfWeek == 7) {
                currentFrequency = currentFrequency * factor;
            }
            try {
                Thread.sleep(currentFrequency);
            } catch (InterruptedException ex) {
                Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void generateAttack(String feedtype, String attackid, String destinationip, String destinationport, String frequency) {
        syslogUtility = new SyslogUtility(feedtype + attackid, destinationip, destinationport);
        HashMap<String , HashMap<String, String>> varResultBuffer;
        while (running) {
            int index = getRandomizedIndex();
            varResultBuffer = new HashMap<String , HashMap<String, String>>();
            String currentTransaction = transactions.get(index);
            Long currentFrequency = Long.parseLong(frequency);
            String pattern = "\\{\\{([\\w\\.]+)\\}\\}";
            Pattern pat = Pattern.compile(pattern);
            Matcher matcher = pat.matcher(currentTransaction);
            while (matcher.find()) {
                String var = matcher.group(1);
                if (var.contains(".")) {
                    var = var.split("\\.")[0];
                    HashMap<String, String> res = null;
                    try {
                        if (varResultBuffer.containsKey(var)) {
                            res = varResultBuffer.get(var);
                        } else {
                            res = vgtMap.get(var).getValue();
                            varResultBuffer.put(var, res);
                        }
                        String pattempstr = "\\{\\{(" + var + "\\.\\w+)\\}\\}";
                        Pattern pattemp = Pattern.compile(pattempstr);
                        Matcher matchertemp = pattemp.matcher(currentTransaction);
                        while (matchertemp.find()) {
                            currentTransaction = currentTransaction.replace(matchertemp.group(), res.get(matchertemp.group(1)));
                        }
                    } catch (OperationNotSupportedException ex) {
                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        currentTransaction = currentTransaction.replace(matcher.group(), vgtMap.get(matcher.group(1)).getValue().get(matcher.group(1)));
                    } catch (OperationNotSupportedException ex) {
                        Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            //before outputting this transaction we will have to split it by the double pipe symbol and output each one in a different line.
            String[] splitTransactions = currentTransaction.split("\\|\\|");
            for (String transaction : splitTransactions) {
//                System.out.println(transaction.trim());
                syslogUtility.publishString(transaction.trim());
            }

            try {
                Thread.sleep(currentFrequency);
            } catch (InterruptedException ex) {
                Logger.getLogger(TemplatingSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stopGeneratingFeed() {
        running = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TemplatingSystem ts = new TemplatingSystem();
        //ts.bufferAllTransactionLines();
        //ts.generateFeed(Integer.SIZE, null, null, null, null, Integer.SIZE);
    }

    private Long getCurrentFrequency(String frequency) {
        String[] frequencies = frequency.split(",");
        ArrayList<Long> frequencyLong = new ArrayList<>();
        for (String freq : frequencies) {
            frequencyLong.add(Long.parseLong(freq));
        }
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        int currenttime = Integer.parseInt(sdf.format(cal.getTime()));
        if (currenttime >= 0 && currenttime < 9) {
            return frequencyLong.get(0);
        } else if (currenttime >= 9 && currenttime < 15) {
            return frequencyLong.get(1);
        } else if (currenttime >= 15 && currenttime < 18) {
            return frequencyLong.get(2);
        } else if (currenttime >= 18 && currenttime < 21) {
            return frequencyLong.get(3);
        } else if (currenttime >= 21 && currenttime < 24) {
            return frequencyLong.get(4);
        }

        return 0L;
    }

    private Long getCurrentFrequencyTest(String frequency) {
        String[] frequencies = frequency.split(",");
        ArrayList<Long> frequencyLong = new ArrayList<>();
        for (String freq : frequencies) {
            frequencyLong.add(Long.parseLong(freq));
        }
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("ss");
        int currenttime = Integer.parseInt(sdf.format(cal.getTime()));
        if (currenttime >= 0 && currenttime < 15) {
            return frequencyLong.get(0);
        } else if (currenttime >= 15 && currenttime < 25) {
            return frequencyLong.get(1);
        } else if (currenttime >= 25 && currenttime < 35) {
            return frequencyLong.get(2);
        } else if (currenttime >= 35 && currenttime < 45) {
            return frequencyLong.get(3);
        } else if (currenttime >= 45 && currenttime < 60) {
            return frequencyLong.get(4);
        }

        return 0L;
    }

    public int getRandomizedIndex() {
        int index = rand.nextInt(1000);
        return weightedTransactionIds.get(index);
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public HashMap<String, ValueGeneratorType> getVgetMap() {
        return vgtMap;
    }
}
