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

public class SyslogNetflowFeedGenerator extends FeedGeneratorInterface {  
    static Properties props;
    
    private static enum LEVEL {

        alert, critical, debug, emergency, error, info, notice, warn
    }
    private final Map<LEVEL, Pattern> linePatterns = new EnumMap<LEVEL, Pattern>(LEVEL.class);
    private final Map<LEVEL, String> facility = new EnumMap<LEVEL, String>(LEVEL.class);
    private final List<SyslogIF> syslogArray = new ArrayList<SyslogIF>();
    private final List<SyslogBean> syslogBeans = new ArrayList<SyslogBean>();
    private final Random generator2 = new Random(Calendar.getInstance().getTimeInMillis());
    private String sylogInstance;
    SyslogConfigIF config;
    
    public SyslogNetflowFeedGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency){
        config = new UDPNetSyslogConfig(destinationIp, Integer.parseInt(destinationPort));
        try{
            Syslog.createInstance("NetFlow"+orderId, config);
        }catch(Exception e){
            System.out.println("Already created this instance");
        }
    }
    
    @Override
    public void syslogProxyGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency) {
        running  = true;
        loadSyslogConfProperties(syslogConfigFile, destinationIp, destinationPort);
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

                usersIps = new HashMap<String, ArrayList<String>>();
                users = new ArrayList<String>();
                transactions = new ArrayList<String>();

                String fileline;
                BufferedReader FileRead = null;
                /*new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Proxy/user-IPAddress.csv"));
                FileRead.readLine();

                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();

                    String[] split = fileline.split("\\,");

                    for (int i = 0; i < split.length; i++) {
                        split[i] = split[i].trim().replaceAll("\"", "");
                    }

                    ArrayList<String> ips = new ArrayList<String>();

                    ips.add(split[1]);
                    ips.add(split[2]);
                    ips.add(split[3]);

                    usersIps.put(split[0], ips);
                    users.add(split[0]);
                }
                
                FileRead.close();
                */
                
                MySQLDBClass mysqldbclass = new MySQLDBClass();
                ArrayList<Object> userIpMappingsFromDb = mysqldbclass.getUserIPForSecUser(userid);
                users = (ArrayList<String>)userIpMappingsFromDb.get(2);
                usersIps = (HashMap<String, ArrayList<String>>)userIpMappingsFromDb.get(0);
                ArrayList<String> ipaddresses = (ArrayList<String>)userIpMappingsFromDb.get(1);
                syslogBean.setUsers(users);
                syslogBean.setUsersIps(usersIps); 
                syslogBean.setIPAddresses(ipaddresses);
                
                FileRead = new BufferedReader(new FileReader("/home/securonix/Downloads/FeedGen_DB_FW_SAP/newflow_transactions.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    transactions.add(fileline);
                }
                FileRead.close();
                syslogBean.setTransactions(transactions);
            }
            
            SyslogBean syslogBean;
            String dateString = (baseDate.get(Calendar.MONTH) + 1) + "/" + baseDate.get(Calendar.DATE) + "/" + baseDate.get(Calendar.YEAR);
            while (running) {
                syslogBean = syslogBeans.get(generator2.nextInt(syslogBeans.size()));
                users = syslogBean.getUsers();
                String userName = users.get(generator2.nextInt(users.size()));
                usersIps = syslogBean.getUsersIps();
                String IPAddress1 = usersIps.get(userName).get(generator2.nextInt(usersIps.get(userName).size()));
                String IPAddress2 = usersIps.get(userName).get(generator2.nextInt(usersIps.get(userName).size()));
                transactions = syslogBean.getTransactions();
                String trans = transactions.get(generator2.nextInt(transactions.size()));
                baseDate = Calendar.getInstance();
                String timeString = baseDate.get(Calendar.HOUR) + ":" + baseDate.get(Calendar.MINUTE) + ":" + baseDate.get(Calendar.SECOND);
                if(trans.contains("FTP")) {
                    System.out.println(IPAddress1 + "," + IPAddress2 + "," + dateString + "," + timeString + "," + generator2.nextInt(999999999)  + "," + trans  + ", port/code:0,"+generator2.nextInt(40)+"T\n");
                    //publish(IPAddress1 + "," + IPAddress2 + "," + dateString + "," + timeString + "," + generator2.nextInt(999999999)  + "," + trans  + ", port/code:0,"+generator2.nextInt(40)+"T\n");
                }
                else if(trans.contains("ICMP")) {
                    System.out.println(IPAddress1 + "," + IPAddress2 + "," + dateString + "," + timeString + ",80287" + trans  + ", port/code:0,"+generator2.nextInt(40)+"T\n");
                    //publish(IPAddress1 + "," + IPAddress2 + "," + dateString + "," + timeString + ",80287" + trans  + ", port/code:0,"+generator2.nextInt(40)+"T\n");
                }
                else {
                    System.out.println(IPAddress1 + "," + IPAddress2 + "," + dateString + "," + timeString + "," + generator2.nextInt(9999999)  + "," + trans  + ", port/code:0,"+generator2.nextInt(40)+"T\n");
                    //publish(IPAddress1 + "," + IPAddress2 + "," + dateString + "," + timeString + "," + generator2.nextInt(9999999)  + "," + trans  + ", port/code:0,"+generator2.nextInt(40)+"T\n");
                }
                //publish("," + userName + "," + trans + ",0," + dateString + "," + timeString + "," + IPAddress  + "," + "type=Proxy"  + "\n");                
                
                MySQLDBClass tempObj = new MySQLDBClass();
                boolean shouldFire = tempObj.getAttackVectorsAndStep(orderId);
                 System.out.println("Value of should fire variable: "+shouldFire);

                if(shouldFire){
                    //build proxy Attack based on the parameters saved from the UI
                    HashMap<String, String> resultSetHMap = (tempObj).getAttackDefinition(orderId);
                    //Integer configid = Integer.parseInt(resultSetHMap.get("feedconfigid"));
                    //ArrayList<String> keyList = (tempObject).getKeyList(configid);
                    
                    //create the String to be published.
                    String attackString;
                    attackString = resultSetHMap.get("test1") + "," + resultSetHMap.get("test2") + "," + "87654654768" + "," + dateString + "," + timeString + "," + "87.23.44.3"  + "," + "type=SyslogNetflow" + " -- orderId: " + orderId + "\n";
                                        
                    //publish the attack
                    System.out.println(attackString);
                    //update the current orderid showing that it has already been run
                    tempObj.updateStepForOrderId(orderId);
                    
                    //set the next attack in a separate thread with a timer for it to go off after whatever time it was specified
                    new Thread(new StepUpdater(orderId, 5000)).start();
                }
                
                Thread.sleep(Long.parseLong(frequency));
            }
         
        } catch (IOException e) {
            System.out.println("Exception" + e.toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(SyslogNetflowFeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadSyslogConfProperties(String filepath, String destinationIp, String destinationPort) {
        //Generate Properties from Syslog Config file

        props = new Properties();
        try {
            props.load(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/conf/ProxyFeedGenerator.properties"));
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

        
        SyslogIF syslog;
        
        String PROTOCOL = "protocol_1";
        String HOST = destinationIp;
        String PORT = destinationPort;


        try {
             syslog = Syslog.getInstance("NetFlow"+orderId);
//            config = syslog.getConfig();
//            config.setHost(HOST);
//            config.setPort(Integer.parseInt(PORT));

            String pattern;
            for (SyslogNetflowFeedGenerator.LEVEL level : SyslogNetflowFeedGenerator.LEVEL.values()) {
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
                SyslogNetflowFeedGenerator.LEVEL level = SyslogNetflowFeedGenerator.LEVEL.values()[generator2.nextInt(SyslogNetflowFeedGenerator.LEVEL.values().length)];
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
    