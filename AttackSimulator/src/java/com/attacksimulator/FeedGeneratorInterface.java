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

class FeedGeneratorInterface {
    protected volatile boolean running;
    protected int orderId;
    protected String syslogConfigFile;
    protected Integer userid;
    protected String destinationIp;
    protected String destinationPort;
    protected String frequency;
    
    FeedGeneratorInterface(){
        running = true;
    }
    
    FeedGeneratorInterface(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency){
        this.orderId = orderId;
        this.syslogConfigFile = syslogConfigFile;
        this.userid = userid;
        this.destinationIp = destinationIp;
        this.destinationPort = destinationPort;
        this.frequency = frequency;
    }
    
    public void syslogProxyGenerator(String syslogConfigFile, Integer userid, String destinationIp, String destinationPort, int orderId, String frequency) {
        //implement this method in the various classes.
    }
    
    public void shutdown(){
        running = false;
    }
}