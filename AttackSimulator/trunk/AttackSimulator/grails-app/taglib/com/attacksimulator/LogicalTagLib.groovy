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
    
    def userenvironmentunknown = { attrs, body ->
        def userid = springSecurityService.currentUser.id;
        def sysipmappingentries = Sysipusermapping.findAllBySecuserid(userid);
        System.out.println("Object of type: " + sysipmappingentries.getClass());   
        if(sysipmappingentries.isEmpty()){
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
    
    def currentUrl = { attrs, body ->
        def location = request.forwardURI;
        if(location.contains("Environment")){
            out << "Environment";
        }else if(location.contains("Request")){
            out << "Place an Order";
        }else if(location.contains("Thread")){
            out << "Your Orders";
        }else if(ocation.contains("Attack")){
            out << "Attack Management";
        }
    }
    
    def hasattackorders = { attrs, body->
        def secuserid = springSecurityService.currentUser.id;
        def attackorders = Attackorders.findAllBySecuserid(secuserid);
        if(!attackorders.isEmpty()){
            out << body();
        }
    }
}

