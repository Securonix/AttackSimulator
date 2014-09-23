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

public class SyslogSharepointFeedGenerator extends FeedGeneratorInterface{

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
    private final List<SyslogBean> syslogBeans = new ArrayList<>();
    private final Random generator2 = new Random(Calendar.getInstance().getTimeInMillis());
    private int interval;
    SyslogConfigIF config;
    
    public SyslogSharepointFeedGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency){
        config = new UDPNetSyslogConfig(destinationIp, Integer.parseInt(destinationPort));
        try{
            Syslog.createInstance("Sharepoint"+orderId, config);
        }catch(Exception e){
            System.out.println("Already created instance");
        }
    }

    public static void main(String[] args) {
        String syslogConfigFile = "conf/SharepointFeedgenerator.properties";//args[1]
        //syslogProxyGenerator(syslogConfigFile);
    }

    @Override
    public void syslogProxyGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency) {
        running = true;
        // TODO code application logic here
        Calendar baseDate = Calendar.getInstance();
        
        loadSyslogConfProperties(syslogConfigFile, destinationIp, destinationPort, ((Integer)orderId).toString());
        try {
            
            /*
             * Create the userset
             */
            HashMap<String, ArrayList<String>> usersIps;
            ArrayList<String> users;
            ArrayList<String> operations;
            ArrayList<String> domains;
            ArrayList<String> files;
            for (SyslogBean syslogBean : syslogBeans) {

                usersIps = new HashMap<String, ArrayList<String>>();
                users = new ArrayList<String>();
                operations = new ArrayList<String>();
                domains = new ArrayList<String>();
                files = new ArrayList<String>();

                String fileline;
                BufferedReader FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Sharepoint/user-IPAddress.csv"));
                FileRead.readLine();
                /*
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
                */
                FileRead.close();
                
                MySQLDBClass mysqldbclass = new MySQLDBClass();
                ArrayList<Object> userIpMappingsFromDb = mysqldbclass.getUserIPForSecUser(userid);
                users = (ArrayList<String>)userIpMappingsFromDb.get(2);
                usersIps = (HashMap<String, ArrayList<String>>)userIpMappingsFromDb.get(0);
                
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Sharepoint/operations.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    operations.add(fileline);
                }
                FileRead.close();
                // Read domains
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Sharepoint/domains.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    domains.add(fileline);
                }
                FileRead.close();
                //Read files
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/Sharepoint/files.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    files.add(fileline);
                }
                FileRead.close();
                syslogBean.setUsers(users);
                syslogBean.setUsersIps(usersIps);
                syslogBean.setOperations(operations);
                syslogBean.setDomains(domains);
                syslogBean.setFilenames(files);

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
                operations = syslogBean.getOperations();
                String operation = operations.get(generator2.nextInt(operations.size()));
                domains = syslogBean.getDomains();
                String domain = domains.get(generator2.nextInt(domains.size()));
                files = syslogBean.getFilenames();
                String file = files.get(generator2.nextInt(files.size()));
                
                baseDate = Calendar.getInstance();
                String timeString = baseDate.get(Calendar.HOUR) + ":" + baseDate.get(Calendar.MINUTE) + ":" + baseDate.get(Calendar.SECOND);
                
                System.out.println(domain + "," + userName + "," + dateString + "," + timeString + "," + operation +  "," + file + "," + "type=Sharepoint" + " -- orderId: " + orderId  + " IpAddress: " + destinationIp + "\n");
                publish("," + domain + "," + userName + "," + operation +  "," + file  + "," + "type=Sharepoint"  + "\n");
                
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
                    attackString = resultSetHMap.get("test1") + "," + resultSetHMap.get("test2") + "," + "87654654768" + "," + dateString + "," + timeString + "," + "87.23.44.3"  + "," + "type=SHAREPOINTSSSSS" + " -- orderId: " + orderId + "\n";
                                        
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
            Logger.getLogger(SyslogSharepointFeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
        String operationFile;
        String fileListFile;
        String domainFile;
        for (int i = 1; i <= 10; i++) {
            userIpFile = props.getProperty("HRDataFileBase");
            operationFile = props.getProperty("Operations");
            fileListFile = props.getProperty("files");
            domainFile = props.getProperty("domains");
            SyslogBean syslogBean = new SyslogBean();
            syslogBean.setUserIpFile(userIpFile);
            syslogBean.setOperationFile(operationFile);
            syslogBean.setFileListFile(fileListFile);
            syslogBean.setDomainFile(domainFile);
            syslogBeans.add(syslogBean);
        }

        interval = Integer.parseInt(props.getProperty("interval"));
        
        SyslogIF syslog;
        SyslogConfigIF config;
        String HOST = destinationIp;
        String PORT = destinationPort;
        
        try {
            
            syslog = Syslog.getInstance("Sharepoint"+orderId);
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