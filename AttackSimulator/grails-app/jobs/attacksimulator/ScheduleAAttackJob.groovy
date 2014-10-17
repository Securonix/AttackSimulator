package attacksimulator

import com.attacksimulator.RunSyslogAttacks;
import attacksimulator.Attackorders;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


class ScheduleAAttackJob {
    static triggers = {}

    def execute(context) {
        // execute job
        def orderid = context.mergedJobDataMap.get('orderid');
        def feedtype = context.mergedJobDataMap.get('feedtype');
        
        if(orderid == null){
            System.out.println("Orderid is null.. quitting execution of job!!");
            return;
        }
        
        def order = Attackorders.get(orderid);
        def secuserid = order.secuserid;
        def attackid = order.attackid;
        def attackerid = order.attackerid;
        def destinationip = order.destinationip;
        def destinationport = order.destinationport;
        def transactionfile = order.transactionfile;
        def frequency = order.frequency;
        
        // public RunSyslogAttacks(String secuserid, String transactionfile, String attackerid, Date dayofattack, String timeofattack, 
        // String frequency, String attackid, String destinationip, String destinationport, String feedtype)
        RunSyslogAttacks th = new RunSyslogAttacks(secuserid.toString(), transactionfile, attackerid.toString(), frequency, attackid.toString(), destinationip, destinationport, feedtype);
        th.start();
        order.threadid = th.getId();
        order.save(flush: true);
    }
}
