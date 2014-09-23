package feedgeneratorgrails

import org.feedgeneratorgrails.Orders;
import org.codehaus.groovy.grails.validation.routines.InetAddressValidator;

class OrderController {

    def springSecurityService;
    
    def saveOrder() {
        
        //check received params server side and then save it in the database
        def destinationip = params.get("destinationip");
        def destinationport = params.get("destinationport");
        String[] feedtype = params.list("feedtype[]");
        log.debug 'Value of feedtype'+feedtype;
        String[] startdates = params.list("startdates[]");
        String[] enddates = params.list("enddates[]");
        String[] frequencies = params.list("frequencies[]");
        def userid = springSecurityService.currentUser.id;
        
        //make some checks for empty strings wrong values etc on all the variable here to be done
        if(!testIP(destinationip)){
            render "ipfailed";
            return;
        }
        
        if(!testPort(destinationport)){
            render "portfailed";
            return;
        }
        
        for(int i=0; i<startdates.length; i++){
            if(!testDate(startdates[i], enddates[i])){
                render "dateswrong";
                return;
            }
        }
       
        for(int i=0; i<feedtype.length; i++){
            Orders order = new Orders(id: 1, userid: userid, feedtype: feedtype[i], startdate: Date.parse("MM/dd/yyyy", startdates[i]), enddate: Date.parse("MM/dd/yyyy", enddates[i]), frequency: frequencies[i], destinationip: destinationip, destinationport: destinationport, approved: 0, threadid: -1);
            order.save(flush: true);
        }
        
        render "success";
    }
    
    def testIP(String ipaddress){
        if(InetAddressValidator.getInstance().isValidInet4Address(ipaddress)){
            return true;
        }else{
            return false;
        }
    }
    
    def testPort(String port){
        def portNum = Integer.parseInt(port);
        if(portNum < 0 || portNum > 65535){
            return false;
        }
        return true;
    }
    
    def testDate(String date1, String date2){
        def today  = new Date();
        def stdate = Date.parse("MM/dd/yyyy", date1);
        def eddate = Date.parse("MM/dd/yyyy", date2);
        
        if(today.compareTo(stdate) > 0){
            return false;
        }
        
        if(stdate.compareTo(eddate) > 0){
            return false;
        }
        
        return true;
    }
    
    def testOrder() {
        
        Orders order = new Orders(id: 1, userid: 24, feedtype: "Vontu", startdate: Date.parse("MM/dd/yy", "10/10/98"), enddate: Date.parse("MM/dd/yy", "10/10/98"), frequency: "54", destinationip: ".1.1.1.1", destinationport: "87", approved: 0, threadid: -1);
        order.save(flush: true);
        
        render "Hello"
    }
    
    def deleteOrder(){
        String[] allyesids = params.list("allyesids[]");
        String[] allnoids = params.list("allnoids[]");
        
        
        //for all the yes.. update the orderids and change approved to 1. For all the no's delete the entries.
        for(int i=0; i<allyesids.length; i++){
            def order = Orders.get(allyesids[i]);
            order.approved = 1;
            order.save(flush: true);
        }
        
        for(int i=0; i<allnoids.length; i++){
            def order = Orders.get(allnoids[i]);
            order.delete(flush: true);
        }
        
        render "success"
    }
}
