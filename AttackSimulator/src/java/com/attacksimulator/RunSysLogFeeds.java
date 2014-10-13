/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

/**
 *
 * @author securonix
 */
public class RunSysLogFeeds extends Thread{

     private final String destinationIp;
     private final String destinationPort;
     private final String frequency;
     private final String feedtype;
     private volatile boolean running;
     private FeedGeneratorInterface fgi;
     private int orderId;
     private Integer userid;
     private TemplatingSystem templateSys;
     private String factorString;

     public RunSysLogFeeds(Integer userid, String desIp, String desPort, String freq, String ftype, int orderId, String factorString){
        destinationIp = desIp;
        destinationPort = desPort;
        frequency = freq;
        feedtype = ftype;
        running = true;
        this.orderId = orderId;
        this.userid = userid;
        fgi = null;
        templateSys = null;
        this.factorString = factorString;
     }

    @Override
    public void run() {
        /*
        if(feedtype.equalsIgnoreCase("sharepoint")){
            String syslogConfigFile = "/home/securonix/NetBeansProjects/FeedGenerator/conf/SharepointFeedGenerator.properties";
            fgi = new SyslogSharepointFeedGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
            fgi.syslogProxyGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
        }else if(feedtype.equalsIgnoreCase("proxy")){
            String syslogConfigFile = "/home/securonix/NetBeansProjects/FeedGenerator/conf/ProxyFeedGenerator.properties";
            fgi = new SyslogProxyFeedGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
            fgi.syslogProxyGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
        }else if(feedtype.equalsIgnoreCase("Vontu")){
            String syslogConfigFile = "/home/securonix/NetBeansProjects/FeedGenerator/conf/VontuFeedGenerator.properties";
            fgi = new SyslogVontuFeedGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
            fgi.syslogProxyGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
        }else if(feedtype.equalsIgnoreCase("Windows")){
            String syslogConfigFile = "/home/securonix/NetBeansProjects/FeedGenerator/conf/WindowsFeedGenerator.properties";
            fgi = new SyslogWindowsFeedGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
            fgi.syslogProxyGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
        }else if(feedtype.equalsIgnoreCase("database")){
            String syslogConfigFile = "/home/securonix/NetBeansProjects/FeedGenerator/conf/ProxyFeedGenerator.properties";
            fgi = new SyslogDatabaseFeedGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
            fgi.syslogProxyGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
        }else if(feedtype.equalsIgnoreCase("netflow")){
            String syslogConfigFile = "/home/securonix/NetBeansProjects/FeedGenerator/conf/ProxyFeedGenerator.properties";
            fgi = new SyslogNetflowFeedGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
            fgi.syslogProxyGenerator(syslogConfigFile, userid, destinationIp, destinationPort, orderId, frequency);
        }
        */
        templateSys = new TemplatingSystem(feedtype, userid);
        templateSys.generateFeed(userid, destinationIp, destinationPort, frequency, feedtype, orderId, factorString);
    }
    
    public void shutdown(){
        if(fgi != null){
            fgi.shutdown();
        }
        
        if(templateSys != null){
            templateSys.stopGeneratingFeed();
        }
        
        running = false;
    }
    
}
