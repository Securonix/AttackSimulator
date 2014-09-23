package feedgeneratorgrails

import com.attacksimulator.MySQLDBClass;
import org.feedgeneratorgrails.Orders;
import org.feedgeneratorgrails.Feedconfiguration;
import org.feedgeneratorgrails.Attackdefinition;

class AttackController {
    
    def springSecurityService;
    
    def index() {
        if(springSecurityService.isLoggedIn()){
            def orderbysecuserid = Orders.findAllByUserid(springSecurityService.currentUser.id);
            HashSet<String> uniqueFeedTypes = new HashSet<>();
            
            for(Orders order: orderbysecuserid){
                uniqueFeedTypes.add(order.feedtype);
            }
            
//            MySQLDBClass mysqldbclass = new MySQLDBClass();
//            ArrayList<String> users = mysqldbclass.getAllUsersForSecUser(springSecurityService.currentUser.id);
//            System.out.println("The number of users for this secuserid: "+users.size());
               render(view:'/attack/attack.gsp', model: [user: springSecurityService.currentUser.username, uniqueFeedTypes: uniqueFeedTypes, orders: orderbysecuserid]);
            return;
        }
        
        redirect(controller:'login', action: 'auth');
    }
    
    def getFeedConfig() {
        // I need to first get the configuration of the feedtype that was sent.. feedtype will usually be a single request.
        String feedtype = params.get("feedtype");
        feedtype = feedtype.toLowerCase();
        
        Integer orderid = Integer.parseInt(params.get("orderid"));
        //I need to get the table heading from the feedconfiguration tables. I need to know what the input parameter should be taken as.
        //right now what I will do is just give them a text box to enter the value and then save it. This should ideally be a choice for some 
        //set of values
        def feedconfig = Feedconfiguration.findAllByFeedtype(feedtype);
        //The Feedconfigoptions will have what the input type is going to be like and what values will be used to populate the choice fields
        //currently I won't be implementing this.
        //def feedconfigoptions = 
        
        //from the feedconfig I need to find the values of the configfields that have been used.. and put it in the arraylist.
        ArrayList<String> tableHeaders = new ArrayList<>();
        for(int i=1; i<21; i++){
            String val = feedconfig.("configfield"+i);
            if(val.equalsIgnoreCase("[null]")){
                break;
            }else{
                tableHeaders.add(val.subSequence(1, val.length()-1));
            }
        }
        
        for(int i=0; i < tableHeaders.size(); i++){
            System.out.println("Value of tableHeader: "+tableHeaders.get(i));
        }
        
       String feedconfigid = feedconfig.(id);
        feedconfigid = feedconfigid.subSequence(1, feedconfigid.length()-1);
        render(template: 'attacktemplate', model: [feedconfig: feedconfig, tableHeaders: tableHeaders, orderid: orderid, feedtype: feedtype, feedconfigid: feedconfigid]);
    }
    
    def saveAttackVector(){
        String[] attackvectors = params.get("attackvectors[]");
        Integer orderid = Integer.parseInt(params.get("orderid"));
        Integer feedconfigid = Integer.parseInt(params.get("feedconfigid"));
        String feedtype = params.get("feedtype");
        
        for(int i=0; i<attackvectors.length; i++){
            System.out.println("Attack Vector "+(i+1)+" :"+attackvectors[i]);
        }
        
        System.out.println("Order id: "+ orderid);
        Integer userid = springSecurityService.currentUser.id;
        
//        def attackdefinition = Attackdefinition.findAllByOrderid(orderid);
//        
//        System.out.println(attackdefinition);
//        def userid = springSecurityService.currentUser.id;
//        
//        if(attackdefinition == null || attackdefinition.isEmpty()){
//            System.out.println("attackdefinition was empty");
//            attackdefinition = new Attackdefinition(id:1, feedtype: feedtype, orderid: orderid, feedconfigid: feedconfigid, userid: userid, step: 0);
//            for(int i=0; attackvectors.length; i++){
//                attackdefinition["attackelement"+(i+1)] = attackvectors[i];
//            }
//            attackdefinition.save(flush: true);
//        }else{
//            for(int i=0; attackvectors.length; i++){
//                attackdefinition["attackelement"+(i+1)] = attackvectors[i];
//            }
//            attackdefinition.save(flush: true);
//        }
        
        MySQLDBClass tempObj = new MySQLDBClass();
        tempObj.saveAttackDefinition(feedtype, orderid, feedconfigid, userid, attackvectors);
        
        render "success";
    }
    
    def savedVectors(){
        def attackdefinition = Attackdefinition.findAllByUserid(springSecurityService.currentUser.id);
        
        if(attackdefinition == null || attackdefinition.isEmpty()){
            render "failed";
        }else{
            ArrayList<String> attackelements = new ArrayList<>();
            for(def attackdef : attackdefinition){
                String attackelems = "";
                for(int i=1; i < 21; i++){
                    if(attackdef.("attackelement"+i) == null || attackdef.("attackelement"+i).equalsIgnoreCase("null")){
                        break;
                    }
                    System.out.println("The values of the attackdefinition we have created are: "+attackdef.("attackelement"+i));
                    attackelems += attackdef.("attackelement"+i) + " | ";
                }
                attackelems = attackelems.substring(0, attackelems.length() - 3);
                attackelements.add(attackelems);
            }
            
            render(template: "sequenceattack", model: [attackdefinition: attackdefinition, attackelements: attackelements]);
        }
        
        return;
    }
}
