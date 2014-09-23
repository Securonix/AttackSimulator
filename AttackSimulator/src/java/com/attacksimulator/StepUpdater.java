/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author securonix
 */

public class StepUpdater implements Runnable{
    
    private final int orderid;
    private final long stopforthislong;
    
    public StepUpdater(int orderid, long stopforthislong) {
        this.orderid = orderid;
        this.stopforthislong = stopforthislong;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(stopforthislong);
        } catch (InterruptedException ex) {
            Logger.getLogger(StepUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MySQLDBClass tempObj = new MySQLDBClass();
        tempObj.updateStepForNextOrderId(orderid);
    }
    
}
