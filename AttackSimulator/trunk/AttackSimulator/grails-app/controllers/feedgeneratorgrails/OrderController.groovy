package feedgeneratorgrails

import org.feedgeneratorgrails.Orders;
import groovy.json.JsonSlurper
import org.codehaus.groovy.grails.validation.routines.InetAddressValidator;
import attacksimulator.Usersyslogdetails;

class OrderController {

    def springSecurityService;
    
    def saveOrder() {
        
        if(!springSecurityService.isLoggedIn()){
            redirect action: 'auth', params: params;
        }
        
        //check received params server side and then save it in the database
        def userid = springSecurityService.currentUser.id;
        def userdeets = Usersyslogdetails.findAllBySecuserid(userid);
        def destinationip = userdeets.get(0).destinationip;
        def destinationport = userdeets.get(0).destinationport;
        String[] feedtype = params.list("feedtype[]");
        String[] startdates = params.list("startdates[]");
        String[] enddates = params.list("enddates[]");
        String[] frequencies = params.list("frequencies[]");
        
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
            def prevOrder = Orders.find("from Orders where feedtype='"+ feedtype[i]+"' and destinationip='" +destinationip+"' and userid="+userid);
            
            if(prevOrder == null){
                Orders order = new Orders(id: 1, userid: userid, feedtype: feedtype[i], startdate: Date.parse("MM/dd/yyyy", startdates[i]), enddate: Date.parse("MM/dd/yyyy", enddates[i]), frequency: frequencies[i], destinationip: destinationip, destinationport: destinationport, approved: 1, threadid: -1, weekendfactor: 10);
                order.save(flush: true);
            }else{
                prevOrder.frequency = frequencies[i];
                prevOrder.destinationport = destinationport;
                prevOrder.startdate = Date.parse("MM/dd/yyyy", startdates[i]);
                prevOrder.enddate = Date.parse("MM/dd/yyyy", enddates[i]);
                prevOrder.save(flush: true);
            }
        }
        
        render "success";
    }
    
    def testIP(String ipaddress){
//        if(InetAddressValidator.getInstance().isValidInet4Address(ipaddress)){
//            return true;
//        }else{
//            return true;
//        }
          return true;
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
        today.clearTime();
        
        //System.out.println("Todays date: "+ today.toString());
        //System.out.println("Start date: "+ stdate.toString());
        //System.out.println("End date: "+ eddate.toString());
        
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
    
    def updateOrder(){
        System.out.println(params.get("order"));
        def slurper = new JsonSlurper();
        def orderParams = slurper.parseText(params.get("order"));
        System.out.println("The class of orderParams: "+ orderParams.getClass());
        
        def order = Orders.get(orderParams.id);
        
        if(orderParams.containsKey("frequency")){
            order.frequency = orderParams.frequency;
        }
        
        if(orderParams.containsKey("startdate")){
            order.startdate = orderParams.startdate;
        }
        
        if(orderParams.containsKey("enddate")){
            order.enddate = orderParams.enddate;
        }
        
        if(orderParams.containsKey("destinationip")){
            order.destinationip = orderParams.destinationip;
        }
        
        if(orderParams.containsKey("destinationport")){
            order.destinationport = orderParams.destinationport;
        }
        
        order.save(flush: true);
        
        render "success";
    }
}
