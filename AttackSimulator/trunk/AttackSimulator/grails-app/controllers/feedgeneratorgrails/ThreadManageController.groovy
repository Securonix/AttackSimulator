package feedgeneratorgrails

import grails.plugins.springsecurity.Secured
import org.feedgeneratorgrails.Orders;
import com.attacksimulator.RunSysLogFeeds;
import attacksimulator.ScheduleAttackJob;
import attacksimulator.DeScheduleAttackJob;

class ThreadManageController {
    
  def springSecurityService;
    
    def index() { 
        if(!springSecurityService.isLoggedIn()){
            redirect(controller:"login", action:"auth");
        }
        def userid = springSecurityService.currentUser.id;
        def orders = Orders.findAllByApprovedAndUserid("1", userid);
        def user = springSecurityService.currentUser.username;
        
        render(view: '/threadManage/threadManage.gsp', model:[orders: orders, user: user])
    }
    
    def startStop(){
        def orderid = params.orderid;
        def operation = params.operation;
        
        def order = Orders.findById(orderid);
        
        if(!(order == null) && operation.equalsIgnoreCase("startthread")){
            ScheduleAttackJob.triggerNow([orderid: orderid]);
            render "threadstarted";
            return;
        }else if(!(order==null) && operation.equalsIgnoreCase("stopthread")){
            DeScheduleAttackJob.triggerNow([orderid: orderid]);
            render "threadstopped"
            return;
        }
        
        render "wrongoperation";
        
        return;
    }
    
    ThreadGroup getThreadGroup(Thread thread){
        ThreadGroup rootGroup = thread.getThreadGroup();
        while(true){
            ThreadGroup parentGroup = rootGroup.getParent();
            if(parentGroup == null){
                break;
            }
            rootGroup = parentGroup;
         }
        
        return rootGroup;
    }
}
