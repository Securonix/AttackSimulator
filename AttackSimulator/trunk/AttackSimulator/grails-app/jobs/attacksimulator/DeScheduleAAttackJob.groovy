package attacksimulator

import com.attacksimulator.RunSyslogAttacks;
import attacksimulator.Attackorders;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


class DeScheduleAAttackJob {
    static triggers = {}

    def execute(context) {
        // execute job
        def orderid = Integer.parseInt(context.mergedJobDataMap.get('orderid'));
        def order = Attackorders.get(orderid);
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
                    RunSyslogAttacks th = (RunSyslogAttacks)thread;
                    if(orderid == th.getOrderId())
                        ((RunSyslogAttacks)thread).shutdown();
                }
            }
        }
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
