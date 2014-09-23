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

public class SyslogVontuFeedGenerator extends FeedGeneratorInterface{

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
    
    public SyslogVontuFeedGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency){
        config = new UDPNetSyslogConfig(destinationIp, Integer.parseInt(destinationPort));
        try{
            Syslog.createInstance("Vontu"+orderId, config);
        }catch(Exception e){
            System.out.println("Already defined");
        }
    }

    public static void main(String[] args) {
        String syslogConfigFile = "conf/VontuFeedgenerator.properties";//args[1]
        //syslogProxyGenerator(syslogConfigFile);
        
    }

    @Override
    public void syslogProxyGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency) {
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
        loadSyslogConfProperties(syslogConfigFile, destinationIp, destinationPort, ((Integer)orderId).toString());
        // TODO code application logic here
        running = true;
        BytesGenerator getBytes = new BytesGenerator(400, 500);
        getBytes.addRange(4000, 4030);
        getBytes.addRange(4000000,4000004);
        BytesGenerator getMatches = new BytesGenerator(0, 15);
        Calendar baseDate = Calendar.getInstance();
        
        try {
            
            /*
             * Create the userset
             */
            HashMap<String, ArrayList<String>> usersIps;
            ArrayList<String> users;
            ArrayList<String> recipients;
            ArrayList<String> subjects;
            ArrayList<String> classifications;
            for (SyslogBean syslogBean : syslogBeans) {

                usersIps = new HashMap<String, ArrayList<String>>();
                users = new ArrayList<String>();
                recipients = new ArrayList<String>();
                subjects = new ArrayList<String>();
                classifications = new ArrayList<String>();

                String fileline;
                BufferedReader FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/VontuDLP/user-IPAddress.csv"));
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
                MySQLDBClass mysqldbclass = new MySQLDBClass();
                ArrayList<Object> userIpMappingsFromDb = mysqldbclass.getUserIPForSecUser(userid);
                users = (ArrayList<String>)userIpMappingsFromDb.get(2);
                
                usersIps = (HashMap<String, ArrayList<String>>)userIpMappingsFromDb.get(0);
                
                FileRead.close();
                
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/VontuDLP/recipients.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    recipients.add(fileline);
                }
                FileRead.close();
                // Read domains
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/VontuDLP/subjects.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    subjects.add(fileline);
                }
                FileRead.close();
                //Read files
                FileRead = new BufferedReader(new FileReader("/home/securonix/NetBeansProjects/FeedGenerator/VontuDLP/classification.csv"));
                while ((fileline = FileRead.readLine()) != null) {
                    fileline = fileline.trim();
                    classifications.add(fileline);
                }
                FileRead.close();
                syslogBean.setUsers(users);
                syslogBean.setUsersIps(usersIps);
                syslogBean.setRecipients(recipients);
                syslogBean.setSubjects(subjects);
                syslogBean.setClassifications(classifications);

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
                recipients = syslogBean.getRecipients();
                String recipient = recipients.get(generator2.nextInt(recipients.size()));
                subjects = syslogBean.getSubjects();
                String subject = subjects.get(generator2.nextInt(subjects.size()));
                classifications = syslogBean.getClassifications();
                String classification = classifications.get(generator2.nextInt(classifications.size()));
                
                baseDate = Calendar.getInstance();
                String timeString = baseDate.get(Calendar.HOUR) + ":" + baseDate.get(Calendar.MINUTE) + ":" + baseDate.get(Calendar.SECOND);
                
                System.out.println(userName + "," + recipient + "," + "Email/SMTP,Passed," + classification + "," + dateString + "," + timeString + "," + getBytes.getRandom() +  "," + subject  +  "," + getMatches.getRandom() +  ",Open,yes" + "," + " -- orderId: " + orderId + " Running: " + running +", type=Vontu\n");
                publish("," + userName + "," + recipient + "," + "Email/SMTP,Passed," + classification + "," + getBytes.getRandom() +  "," + subject  +  "," + getMatches.getRandom() +  ",Open,yes" + "," + "type=Vontu" +"\n");
                
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
                    attackString = resultSetHMap.get("account") + "," + resultSetHMap.get("recipientaddress") + "," + "87654654768" + "," + dateString + "," + timeString + "," + "87.23.44.3"  + "," + "type=VONTUUUUUUU" + " -- orderId: " + orderId + "\n";
                                        
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
            Logger.getLogger(SyslogVontuFeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
        String recipientFile;
        String classificationFile;
        String subjectFile;
        for (int i = 1; i <= 10; i++) {
            userIpFile = props.getProperty("HRDataFileBase");
            recipientFile = props.getProperty("recipientList");
            classificationFile = props.getProperty("classificationList");
            subjectFile = props.getProperty("subjectList");
            SyslogBean syslogBean = new SyslogBean();
            syslogBean.setUserIpFile(userIpFile);
            syslogBean.setRecipientFile(recipientFile);
            syslogBean.setClassificationFile(classificationFile);
            syslogBean.setSubjectFile(subjectFile);
            syslogBeans.add(syslogBean);
        }

        interval = Integer.parseInt(props.getProperty("interval"));

        SyslogIF syslog;
        SyslogConfigIF config;

        String PROTOCOL = "protocol_1";
        String HOST = destinationIp;
        String PORT = destinationPort;

        if (props.getProperty(PROTOCOL) == null || props.getProperty(PROTOCOL).isEmpty()) {
            //do nothing
            return;
        }

        try {
            
            syslog = Syslog.getInstance("Vontu"+orderId);
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