package attacksimulator;

import org.feedgeneratorgrails.Feedmaster;

class UielementsController {

    def quartzScheduler;

    def index() {
        System.out.println("The type of the scheduler is : "+ quartzScheduler.getClass());	
    }
    
    def scheduleAttackOrder(){
        def attackorderid = params.get("id");
        System.out.println("The attack order id: "+ attackorderid);
        
        ScheduleAttackJob.triggerNow([orderid: attackorderid]);
      
        render "success";
    }
    
     def deScheduleAttackOrder(){
        def attackorderid = params.get("id");
        System.out.println("The attack order id: "+ attackorderid);
        
        DeScheduleAttackJob.triggerNow([orderid: attackorderid]);
      
        render "success";
    }
    
    def getFeedtypes(){
        def feedtypes = Feedmaster.getAll();
        render(template:'orderlist', model:[feeds:feedtypes]);
    }
    
    def getFeedtypesFile(){
        def feedtypes = Feedmaster.getAll();
        render(template:'fileexport', model:[feeds:feedtypes]);
    }
}
