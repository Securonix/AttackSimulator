package attacksimulator

import com.attacksimulator.MySQLDBClass;
import com.attacksimulator.AttackOrders;
import attacksimulator.Attackorders;
import org.feedgeneratorgrails.Orders;

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
            def feedorder = Orders.findAllByUseridAndFeedtype(secuserid, feedtype).get(0)
            def destinationip = feedorder.get(0).destinationip;
            def destinationport = feedorder.get(0).destinationport;
            def order = Attackorders.findAllByAttackid(typeofattackid);
            if(order.isEmpty()){
                //everything is fine.. this is a new order.. just save it to the DB.
                Attackorders neworder = new Attackorders(id: 1, attackid: typeofattackid, attackerid: attackerid, username: username, secuserid: secuserid, 
                transactionfile: transactionfile, destinationip: destinationip, destinationport: destinationport, dayofattack: startdate, timeofattack: time,
                frequency: frequency);
                neworder.save(flush: true);
            }else{
                //this seems to be an order that was already there in the db.. update the params of this order and let the 
                //user know that his order params have been updated
                if(order.size() == 1){
                    Attackorders oldorder = order.get(0);
                    oldorder.dayofattack = startdate;
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
    }
}
