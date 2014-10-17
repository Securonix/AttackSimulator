package attacksimulator

import com.attacksimulator.MySQLDBClass;
import com.attacksimulator.AttackOrders;
import attacksimulator.Attackorders;
import org.feedgeneratorgrails.Orders;
import java.sql.Time;
import java.text.DateFormat
import java.text.SimpleDateFormat

class AttackOrderController {
    
    def springSecurityService;
    
    def index() {
        if(springSecurityService.isLoggedIn()){
            //get all the attacks that the user previously ordered.
            //for each fieldset we need to find the number of options that we have for the attacks.
            def secuserid = springSecurityService.currentUser.id;
            def listOfAvailableAttacks = (new MySQLDBClass()).getListofAvailableAttacks(secuserid);
            def listOfUsers = (new MySQLDBClass()).getListOfUsers(secuserid);
            def attackorders = Attackorders.findAllBySecuserid(secuserid);
            
            render(view: '/attackOrder/AttackOrder.gsp', model: [attackorders : listOfAvailableAttacks, listofusers : listOfUsers, userattacks: attackorders]);
        }else{
            redirect(controller:"login", action:"auth");
        }
    }
    
    def saveAttackOrder() {
        if(springSecurityService.isLoggedIn()){
            def secuserid = springSecurityService.currentUser.id;
            def startdate = params.get("startdate");
            def time = params.get("time");
            def typeofattackid = params.get("typeofattackid");
            def transactionfile = params.get("transactionfile");
            def frequency = params.get("frequency");
            def attackerid = params.get("attackerid");
            def username = params.get("username");
            def feedtype = params.get("feedtype");
            def feedorder = Orders.findAllByUseridAndFeedtype(secuserid, feedtype).get(0);
            System.out.println("The feedorder is : " + feedorder);
            def destinationip = feedorder.destinationip;
            def destinationport = feedorder.destinationport;
            def order = Attackorders.findAllByAttackidAndSecuserid(typeofattackid, secuserid);
//            System.out.println("The destinationip is : " + destinationip);
//            System.out.println("The destinationport is : " + destinationport);
//            System.out.println("The startdate is : " + startdate);
//            System.out.println("The time is : " + time);
//            System.out.println("The typeofattackid is : " + typeofattackid);
//            System.out.println("The frequency is : " + frequency);
//            System.out.println("The attackerid is : " + attackerid);
//            System.out.println("The username is : " + username);
//            System.out.println("The feedtype is : " + feedtype);
//            System.out.println("The transactionfile is : " + transactionfile);
            
            //DateFormat formatter = new SimpleDateFormat("HH:mm");
            //java.sql.Time sqlTime = new java.sql.Time(formatter.parse(time).getTime());
            
            if(order.isEmpty()){
                //everything is fine.. this is a new order.. just save it to the DB.
                Attackorders neworder = new Attackorders(id: 1, attackid: typeofattackid, attackerid: attackerid, username: username, secuserid: secuserid, 
                transactionfile: transactionfile, destinationip: destinationip, destinationport: destinationport, dayofattack: Date.parse("MM/dd/yyyy", startdate), timeofattack: time,
                frequency: frequency, threadid: -1);
                neworder.save(flush: true);
                
                Date scheduleDate = Date.parse("MM/dd/yyyy HH:mm", startdate+" "+time);
                
                //schedule attack jobs.
                ScheduleAAttackJob.schedule(scheduleDate, [orderid: neworder.id, feedtype: feedtype]);
                
            }else{
                //this seems to be an order that was already there in the db.. update the params of this order and let the 
                //user know that his order params have been updated
                if(order.size() == 1){
                    Attackorders oldorder = order.get(0);
                    oldorder.dayofattack = Date.parse("MM/dd/yyyy", startdate);
                    oldorder.timeofattack = time;
                    oldorder.attackid = typeofattackid;
                    oldorder.frequency = frequency;
                    oldorder.attackerid = attackerid;
                    oldorder.username = username;
                    oldorder.save(flush: true);
                }else{
                    render "failureordernumwrong";
                }
                
                render "success";
            }
        }else{
            render "failurelogin";
        }
        
        render "success";
    }
}
