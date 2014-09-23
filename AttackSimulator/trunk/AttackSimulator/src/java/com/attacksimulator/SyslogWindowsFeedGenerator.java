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

public class SyslogWindowsFeedGenerator extends FeedGeneratorInterface{

    /**
     * @param args the command line arguments
     */
     Properties props;

    private enum LEVEL {

        alert, critical, debug, emergency, error, info, notice, warn
    }
    private final Map<LEVEL, Pattern> linePatterns = new EnumMap<LEVEL, Pattern>(LEVEL.class);
    private final Map<LEVEL, String> facility = new EnumMap<LEVEL, String>(LEVEL.class);
    private final List<SyslogIF> syslogArray = new ArrayList<SyslogIF>();
    private final List<SyslogBean> syslogBeans = new ArrayList<SyslogBean>();
    private final Random generator2 = new Random(Calendar.getInstance().getTimeInMillis());
    private int interval;
    SyslogConfigIF config;
    
    public SyslogWindowsFeedGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency){
        config = new UDPNetSyslogConfig(destinationIp, Integer.parseInt(destinationPort));
        try{
            Syslog.createInstance("Windows"+orderId, config);
        }catch(Exception e){
            System.out.println("Already created instance");
        }
    }

    public static void main(String[] args) {
        String syslogConfigFile = "conf/WindowsFeedgenerator.properties";//args[1]
        //syslogProxyGenerator(syslogConfigFile);
        
    }

    @Override
    public void syslogProxyGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency) {
        loadSyslogConfProperties(syslogConfigFile, destinationIp, destinationPort, ((Integer)orderId).toString());
        // TODO code application logic here
        running = true;
        Calendar baseDate = Calendar.getInstance();
        
        try {
            /*
             * Create the userset
             */
            ArrayList<String> ipaddresses;
            ArrayList<String> users;
            ArrayList<String> regulartransactions;
            ArrayList<String> servicetransactions;
            
            for (SyslogBean syslogBean : syslogBeans) {

                ipaddresses = new ArrayList<>();
                users = new ArrayList<>();
                regulartransactions = new ArrayList<>();
                servicetransactions = new ArrayList<>();
               
                String fileline;
                BufferedReader FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Windows/users.csv"));
                FileRead.readLine();
                /*
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    users.add(fileline);
                }
                */
                FileRead.close();
                
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Windows/ipaddresses.csv"));
                /*
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    ipaddresses.add(fileline);
                }
                */
                FileRead.close();
                MySQLDBClass mysqldbclass = new MySQLDBClass();
                ArrayList<Object> userIpMappingsFromDb = mysqldbclass.getUserIPForSecUser(userid);
                users = (ArrayList<String>)userIpMappingsFromDb.get(2);
                //usersIps = (HashMap<String, ArrayList<String>>)userIpMappingsFromDb.get(0);
                ipaddresses = (ArrayList<String>)userIpMappingsFromDb.get(1);
                
                // Read domains
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Windows/regulartransactions.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    regulartransactions.add(fileline);
                }
                FileRead.close();
                //Read files
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Windows/servicetransactions.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    servicetransactions.add(fileline);
                }
                FileRead.close();
                
                
                syslogBean.setUsers(users);
                syslogBean.setIPAddresses(ipaddresses);
                syslogBean.setRegularTransactions(regulartransactions);
                syslogBean.setServiceTransactions(servicetransactions);
               

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
                ipaddresses = syslogBean.getIPAddresses();
                String IPAddress = ipaddresses.get(generator2.nextInt(ipaddresses.size()));;
                
                String outline;
                baseDate = Calendar.getInstance();
                String timeString = baseDate.get(Calendar.HOUR) + ":" + baseDate.get(Calendar.MINUTE) + ":" + baseDate.get(Calendar.SECOND);
                
                
                if(userName.contains("SVC"))
                {
                    servicetransactions = syslogBean.getServiceTransactions();
                    String servicetransaction = servicetransactions.get(generator2.nextInt(servicetransactions.size()));
                    outline =  userName + "," + IPAddress + "," + servicetransaction;
                }
                else
                {
                    regulartransactions = syslogBean.getRegularTransactions();
                    String regulartransaction = regulartransactions.get(generator2.nextInt(regulartransactions.size()));
                    outline = userName + "," + IPAddress + "," + regulartransaction;
                }
                     
                
                
               outline = outline   + "," + "type=Windows";
                
                System.out.println(outline +" -- orderId: " + orderId + " Running: " + running);
                publish("," + outline  + "\n");
                
                //ATTACKER LOGIC
                
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
                    attackString = resultSetHMap.get("test1") + "," + resultSetHMap.get("test2") + "," + "87654654768" + "," + dateString + "," + timeString + "," + "87.23.44.3"  + "," + "type=WINDOws" + " -- orderId: " + orderId + "\n";
                                        
                    //publish the attack
                    System.out.println(attackString);
                    //update the current orderid showing that it has already been run
                    tempObj.updateStepForOrderId(orderId);
                    
                    //set the next attack in a separate thread with a timer for it to go off after whatever time it was specified
                    new Thread(new StepUpdater(orderId, 5000)).start();
                }
                
                Thread.sleep(Integer.parseInt(frequency));
            }
        } catch (IOException e) {
            System.out.println("Exception" + e.toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(SyslogWindowsFeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
        String usersFile;
        String IPAddressesFile;
        String regulartransactionFile;
        String servicetransactionFile;
        for (int i = 1; i <= 10; i++) {
            usersFile = props.getProperty("HRDataFileBase");
            IPAddressesFile = props.getProperty("IPAddressesBase");
            regulartransactionFile = props.getProperty("regulartransactions");
            servicetransactionFile = props.getProperty("servicetransactions");
            SyslogBean syslogBean = new SyslogBean();
            syslogBean.setUserIpFile(usersFile);
            syslogBean.setIPAddressFile(IPAddressesFile);
            syslogBean.setRegularTransactionFile(regulartransactionFile);
            syslogBean.setServiceTransactionFile(servicetransactionFile);
            syslogBeans.add(syslogBean);
        }

        interval = Integer.parseInt(props.getProperty("interval"));

        SyslogIF syslog;
        SyslogConfigIF config;

        String PROTOCOL = "protocol_1";
        String HOST = destinationIp;
        String PORT = destinationPort;

        try {
            
            syslog = Syslog.getInstance("Windows"+orderId);
            config = syslog.getConfig();
            config.setHost(HOST);
            config.setPort(Integer.parseInt(PORT));
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