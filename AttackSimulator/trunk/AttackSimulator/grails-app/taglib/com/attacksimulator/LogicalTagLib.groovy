/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attacksimulator
import org.feedgeneratorgrails.Sysipusermapping;
import org.feedgeneratorgrails.Orders;

/**
 *
 * @author securonix
 */
class LogicalTagLib {
  
    def springSecurityService;
      
    def userloggedin = { attrs, body ->
        if(springSecurityService.isLoggedIn()){
            out << body();
        }
    }
      
    def userenvironmentknown = { attrs, body ->
        def userid = springSecurityService.currentUser.id;
        def sysipmappingentries = Sysipusermapping.findAllBySecuserid(userid);
        System.out.println("Object of type: " + sysipmappingentries.getClass());   
        if(!sysipmappingentries.isEmpty()){
            out << body();
        }
    }
      
    def userhasorders = { attrs, body ->
        def userid = springSecurityService.currentUser.id;
        def order = Orders.findAllByUserid(userid);
        System.out.println("Object of type: " + order.getClass());
        System.out.println("Order size: " + order.size());
        if(!order.isEmpty()){
            out << body();
        }
    }
}

