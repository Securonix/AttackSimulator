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
public class RunSyslogAttacks extends Thread{
    private final String secuserid;
    private final String transactionfile;
    private final String attackerid;
    private final String frequency;
    private final String attackid;
    private final TemplatingSystem tmpsys;
    private final String destinationip;
    private final String destinationport;
    private final String feedtype;
    
    public RunSyslogAttacks(String secuserid, String transactionfile, String attackerid, String frequency, String attackid, String destinationip, String destinationport, String feedtype) {
        this.secuserid = secuserid;
        this.transactionfile = transactionfile;
        this.attackerid = attackerid;
        this.frequency = frequency;
        this.attackid = attackid;
        this.destinationip = destinationip;
        this.destinationport = destinationport;
        this.feedtype = feedtype;
       tmpsys = new TemplatingSystem(transactionfile, attackerid, secuserid);
    }

    @Override
    public void run() {
        tmpsys.generateAttack(feedtype, attackid, destinationip, destinationport, frequency);
    }

    public String getAttackerid() {
        return attackerid;
    }

    public String getSecuserid() {
        return secuserid;
    }

    public String getTransactionfile() {
        return transactionfile;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getAttackid() {
        return attackid;
    }

    public TemplatingSystem getTmpsys() {
        return tmpsys;
    }

    public String getDestinationip() {
        return destinationip;
    }

    public String getDestinationport() {
        return destinationport;
    }

    public String getFeedtype() {
        return feedtype;
    }
}
