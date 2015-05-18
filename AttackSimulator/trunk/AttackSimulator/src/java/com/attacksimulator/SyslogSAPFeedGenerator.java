/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
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

/**
 *
 * @author securonix
 */

public class SyslogSAPFeedGenerator extends FeedGeneratorInterface {
    String DATAFILE = "proxy/sapData";
    public static void main(String[] args) {
        
    }
    
    static Properties props;

    private static enum LEVEL {

        alert, critical, debug, emergency, error, info, notice, warn
    }
    private final Map<LEVEL, Pattern> linePatterns = new EnumMap<LEVEL, Pattern>(LEVEL.class);
    private final Map<LEVEL, String> facility = new EnumMap<LEVEL, String>(LEVEL.class);
    private final List<SyslogIF> syslogArray = new ArrayList<SyslogIF>();
    private final List<SyslogBean> syslogBeans = new ArrayList<SyslogBean>();
    private final Random generator2 = new Random(Calendar.getInstance().getTimeInMillis());
    private int interval;
    
    @Override
    public void syslogProxyGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency) {
        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        running  = true;
        loadSyslogConfProperties(syslogConfigFile, destinationIp, destinationPort);
        // TODO code application logic here
        BytesGenerator randombytes = new BytesGenerator(0, 500);
        Calendar baseDate = Calendar.getInstance();
         try {
            File sapDataDir = new File("proxy/sapData");
            if(sapDataDir.isDirectory()) {
                File[] dataFiles = sapDataDir.listFiles();
                for(File dataFile : dataFiles) {
                    BufferedReader reader = new BufferedReader(new FileReader(dataFile));
                    int i=0;
                    String line;
                    while((line = reader.readLine())!=null) {
                        if(i++ < 2) 
                         continue;
                        String[] cols = line.split("\t");
                        ArrayList<String> lineA = new ArrayList<>(); 
                        for(int j=0;j<cols.length;j++) {
                            if(j==3 || j==16) {
                                lineA.add("$LOGDATE");
                            }
                            else if(j==4 || j==17) {
                                lineA.add("$LOGTIME");
                            }                            
                            else {
                                lineA.add(cols[j]);
                            }
                        }
                        lines.add(lineA);
                    }
                    reader.close();
                }
            }
             SimpleDateFormat logdateFormat = new SimpleDateFormat("yyyyMMdd");
             SimpleDateFormat logTimeFormat = new SimpleDateFormat("HHmmss");
            while (running) {
                baseDate = Calendar.getInstance();                
                int randomLine = generator2.nextInt(lines.size());
                ArrayList<String> randomLineCols = lines.get(randomLine);
                StringBuilder line = new StringBuilder();
                for(String col : randomLineCols) {
                    line.append(col);
                    line.append("\t");
                }
                String finalizedLine = line.substring(0, line.length()-1).replaceAll("$LOGDATE", logdateFormat.format(baseDate.getTime()));
                finalizedLine = finalizedLine.replaceAll("$LOGTIME", logTimeFormat.format(baseDate.getTime()));
                System.out.print(finalizedLine+"\n");
                //publish(finalizedLine+"\n");
                
                
                
                Thread.sleep(interval);
            }
         
        } catch (IOException e) {
            System.out.println("Exception" + e.toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(SyslogProxyFeedGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadSyslogConfProperties(String filepath, String destinationIp, String destinationPort) {
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
        SyslogConfigIF config;

        String PROTOCOL = "protocol_1";
        String HOST = destinationIp;
        String PORT = destinationPort;


        try {
            syslog = Syslog.getInstance(props.getProperty(PROTOCOL));
            config = syslog.getConfig();
            config.setHost(HOST);
            config.setPort(Integer.parseInt(PORT));

            String pattern;
            for (SyslogSAPFeedGenerator.LEVEL level : SyslogSAPFeedGenerator.LEVEL.values()) {
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
                SyslogSAPFeedGenerator.LEVEL level = SyslogSAPFeedGenerator.LEVEL.values()[generator2.nextInt(SyslogSAPFeedGenerator.LEVEL.values().length)];
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
