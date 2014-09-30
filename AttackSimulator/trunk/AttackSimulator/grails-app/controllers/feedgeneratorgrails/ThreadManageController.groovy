package feedgeneratorgrails

import grails.plugins.springsecurity.Secured
import org.feedgeneratorgrails.Orders;
import com.attacksimulator.RunSysLogFeeds;

class ThreadManageController {
    
  def springSecurityService;
    
    def index() { 
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
            def destinationIp = order?.destinationip;
            def destinationPort = order?.destinationport;
            def frequency = order?.frequency;
            def feedtype = order?.feedtype;
            def userid = order?.userid;
            
            //per orderid we have only one thread.. we have made sure of it right now.. 
            //we have all the parameters that we need to start the thread 
            RunSysLogFeeds th = new RunSysLogFeeds(userid, destinationIp, destinationPort, frequency, feedtype, Integer.parseInt(orderid));
            th.start();
            order.threadid = th.getId();
            order.save(flush: true);
            render "threadstarted";
            return;
        }else if(!(order==null) && operation.equalsIgnoreCase("stopthread")){
            def threadid = order.threadid;
            if(threadid != -1){
                //stop the thread by making it -1 in the db and find it in the running java threads and killing it
                order.threadid = -1;
                order.save(flush: true);
                
                Thread currentThread = Thread.currentThread();
                ThreadGroup threadGroup = getThreadGroup(currentThread);
                int allActiveThreads = threadGroup.activeCount();
                Thread[] allThreads = new Thread[allActiveThreads];
                threadGroup.enumerate(allThreads);
                
                for(int i=0; i < allThreads.length; i++){
                    Thread thread = allThreads[i];
                    if(thread.getId() == threadid){
                        ((RunSysLogFeeds)thread).shutdown();
                    }
                }
                
                render "threadstopped";
                return;
            }else{
                //thread is already stopped
            }
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
