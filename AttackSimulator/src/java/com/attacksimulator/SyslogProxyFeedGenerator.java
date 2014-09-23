/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.net.udp.UDPNetSyslogConfig;

/**
 *
 * @author securonix
 */

public class SyslogProxyFeedGenerator extends FeedGeneratorInterface{

    /**
     * @param args the command line arguments
     */
     Properties props;

    private enum LEVEL {

        alert, critical, debug, emergency, error, info, notice, warn
    }
    private final Map<LEVEL, Pattern> linePatterns = new EnumMap<>(LEVEL.class);
    private final Map<LEVEL, String> facility = new EnumMap<>(LEVEL.class);
    private final List<SyslogIF> syslogArray = new ArrayList<>();
    private final List<SyslogBean> syslogBeans = new ArrayList<>();
    private final Random generator2 = new Random(Calendar.getInstance().getTimeInMillis());
    private int interval;
    private String sylogInstance;
    SyslogConfigIF config;
    
    public static void main(String[] args) {
        //String syslogConfigFile = "conf/ProxyFeedGenerator.properties";//args[1]
        //(new SyslogProxyFeedGenerator(syslogConfigFile, "12.12.12.12", "909", 23, "234")).syslogProxyGenerator(syslogConfigFile, "12.12.12.12", "909", 23, "234");
        
    }
    
    public SyslogProxyFeedGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency){
        config = new UDPNetSyslogConfig(destinationIp, Integer.parseInt(destinationPort));
        try{
            Syslog.createInstance("Proxy"+orderId, config);
        }catch(Exception e){
            System.out.println("Already created this instance");
        }
    }

    @Override
    public void syslogProxyGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency) {
        //running  = true;
        loadSyslogConfProperties(syslogConfigFile, destinationIp, destinationPort, ((Integer)orderId).toString());
        // TODO code application logic here
        BytesGenerator randombytes = new BytesGenerator(0, 500);
        Calendar baseDate = Calendar.getInstance();
        
        try {
            /*
             * Create the userset
             */
            HashMap<String, ArrayList<String>> usersIps;
            ArrayList<String> users;
            ArrayList<String> transactions;
            for (SyslogBean syslogBean : syslogBeans) {

                usersIps = new HashMap<>();
                users = new ArrayList<>();
                transactions = new ArrayList<>();

                String fileline;
                BufferedReader FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Proxy/user-IPAddress.csv"));
                FileRead.readLine();
                /*
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();

                    String[] split = fileline.split("\\,");

                    for (int i = 0; i < split.length; i++) {
                        split[i] = split[i].trim().replaceAll("\"", "");
                    }

                    ArrayList<String> ips = new ArrayList<>();

                    ips.add(split[1]);
                    ips.add(split[2]);
                    ips.add(split[3]);

                    usersIps.put(split[0], ips);
                    users.add(split[0]);
                }
                */
                MySQLDBClass mysqldbclass = new MySQLDBClass();
                ArrayList<Object> userIpMappingsFromDb = mysqldbclass.getUserIPForSecUser(userid);
                users = (ArrayList<String>)userIpMappingsFromDb.get(2);
                usersIps = (HashMap<String, ArrayList<String>>)userIpMappingsFromDb.get(0);
                
                FileRead.close();
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Proxy/transactions.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    transactions.add(fileline);
                }
                FileRead.close();
                syslogBean.setUsers(users);
                syslogBean.setUsersIps(usersIps);
                syslogBean.setTransactions(transactions);

            }

            String dateString = (baseDate.get(Calendar.MONTH) + 1) + "/" + baseDate.get(Calendar.DATE) + "/" + baseDate.get(Calendar.YEAR);
            /*
             * THis is for iterations
             */
            SyslogBean syslogBean;
            while (running) {
                syslogBean = syslogBeans.get(generator2.nextInt(syslogBeans.size()));
                users = syslogBean.getUsers();
                String userName = users.get(generator2.nextInt(users.size()));
                usersIps = syslogBean.getUsersIps();
                String IPAddress = usersIps.get(userName).get(generator2.nextInt(usersIps.get(userName).size()));
                transactions = syslogBean.getTransactions();
                String trans = transactions.get(generator2.nextInt(transactions.size()));
                baseDate = Calendar.getInstance();
                String timeString = baseDate.get(Calendar.HOUR) + ":" + baseDate.get(Calendar.MINUTE) + ":" + baseDate.get(Calendar.SECOND);

                if(trans.contains("File Storage"))
                {
                    randombytes.addRange(50000, 50010);
                    randombytes.addRange(90000, 90010);
                    System.out.println(userName + "," + trans + "," + randombytes.getRandom() + "," + dateString + "," + timeString + "," + IPAddress  + "," + "type=Proxy" + " -- orderId: " + orderId + "\n");
                    publish("," + userName + "," + trans + "," + randombytes.getRandom() + "," + IPAddress  + "," + "type=Proxy"  + "\n");
                }
                else if(trans.contains("Social Network"))
                {
                    System.out.println(userName + "," + trans + "," + randombytes.getRandom() + "," + dateString + "," + timeString + "," + IPAddress  + "," + "type=Proxy"  + " -- orderId: " + orderId + "\n");
                    publish("," + userName + "," + trans + "," + randombytes.getRandom() + "," + IPAddress  + "," + "type=Proxy"  + "\n");
                }
                else{
                    System.out.println(userName + "," + trans + ",0," + dateString + "," + timeString + "," + IPAddress  + "," + "type=Proxy"  + " -- orderId: " + orderId + " IPaddress: "+ destinationIp +"\n");
                    publish("," + userName + "," + trans + ",0," + IPAddress  + "," + "type=Proxy"  + "\n");
                    //publish("," + userName + "," + trans + "," + IPAddress  + "," + "type=Proxy"   + "\n");
                }
                
                //ATTACKER LOGIC
                MySQLDBClass tempObject = new MySQLDBClass();
                boolean shouldFire = (tempObject).getAttackVectorsAndStep(orderId);
                 System.out.println("Value of should fire variable: "+shouldFire);
           
                if(shouldFire){
                    //build proxy Attack based on the parameters saved from the UI
                    HashMap<String, String> resultSetHMap = (tempObject).getAttackDefinition(orderId);
                    //Integer configid = Integer.parseInt(resultSetHMap.get("feedconfigid"));
                    //ArrayList<String> keyList = (tempObject).getKeyList(configid);
                    
                    //create the String to be published.
                    String attackString;
                    attackString = resultSetHMap.get("test") + "," + "MailSentReceieved and what not" + "," + "87654654768" + "," + dateString + "," + timeString + "," + "87.23.44.3"  + "," + "type=Proxy" + " -- orderId: " + orderId + "\n";
                                        
                    //publish the attack
                    System.out.println(attackString);
                    //update the current orderid showing that it has already been run
                    tempObject.updateStepForOrderId(orderId);
                    
                    //set the next attack in a separate thread with a timer for it to go off after whatever time it was specified
                    new Thread(new StepUpdater(orderId, 5000)).start();
                }
                
                Thread.sleep(Integer.parseInt(frequency));
            }

        } catch (IOException e) {
            System.out.println("Exception" + e.toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(SyslogProxyFeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadSyslogConfProperties(String filepath, String destinationIp, String destinationPort, String orderId) {
        //Generate Properties from Syslog Config file

        props = new Properties();
        try {
            props.load(new FileReader(filepath));
            //System.out.println("Configuration loaded!");
        } catch (IOException ex) {
            System.err.println("Error loading properties file - " + ex.getMessage());
            return;
        }
        String userIpFile;
        String transactionFile;
        for (int i = 1; i <= 10; i++) {
            userIpFile = props.getProperty("HRDataFileBase");
            transactionFile = props.getProperty("transactionFile");
            SyslogBean syslogBean = new SyslogBean();
            syslogBean.setUserIpFile(userIpFile);
            syslogBean.setTransactionFile(transactionFile);
            syslogBeans.add(syslogBean);
        }

        interval = Integer.parseInt(props.getProperty("interval"));

        
        SyslogIF syslog;

        String PROTOCOL = "protocol_1";
        String HOST = destinationIp;
        String PORT = destinationPort;


        try {
            
            syslog = Syslog.getInstance("Proxy"+orderId);
            String pattern;
            for (LEVEL level : LEVEL.values()) {
                pattern = props.getProperty(level.name() + ".pattern");
                if (pattern != null) {
                    linePatterns.put(level, Pattern.compile(pattern));
                    facility.put(level, props.getProperty(level.name() + ".facility"));
                }
            }
            syslogArray.add(syslog);

        } catch (SyslogRuntimeException ex) {
            System.err.println("Error reading file - " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.err.println("Error reading file - " + ex.getMessage());
        }
    }

    private void publish(String line) {
        if (line.trim().length() > 0) {
            Pattern pattern;

            for (SyslogIF syslog : syslogArray) {
                LEVEL level = LEVEL.values()[generator2.nextInt(LEVEL.values().length)];
                pattern = linePatterns.get(level);
                if (pattern.matcher(line).matches()) {
                    syslog.getConfig().setFacility(facility.get(level));
                }
                switch (level) {
                    case alert:
                        syslog.alert(line);
                        break;
                    case critical:
                        syslog.critical(line);
                        break;
                    case debug:
                        syslog.debug(line);
                        break;
                    case emergency:
                        syslog.emergency(line);
                        break;
                    case error:
                        syslog.error(line);
                        break;
                    case info:
                        syslog.info(line);
                        break;
                    case notice:
                        syslog.notice(line);
                        break;
                    case warn:
                        syslog.warn(line);
                        break;
                }
            }
        }
    }

    @Override
    public void shutdown(){
        running = false;
    }
}
