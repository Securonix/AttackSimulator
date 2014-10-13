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
public class AttackOrders {

    public String description;
    public String attackid;

    public AttackOrders(String des, String atkid) {
        description = des;
        attackid = atkid;
    }
}
