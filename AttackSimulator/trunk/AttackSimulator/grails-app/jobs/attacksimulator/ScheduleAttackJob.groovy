package attacksimulator

import com.attacksimulator.RunSysLogFeeds;
import org.feedgeneratorgrails.Orders;
import com.attacksimulator.FileFeedGenerator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


class ScheduleAttackJob {
    static triggers = {}

    def execute(context) {
        // execute job
        def orderid = Integer.parseInt(context.mergedJobDataMap.get('orderid'));
        if(orderid == null){
            System.out.println("Orderid is null.. quitting execution of job!!");
            return;
        }
        def order = Orders.get(orderid);
        def secuserid = order.userid;
        def destinationip = order.destinationip;
        def destinationport = order.destinationport;
        def frequency = order.frequency;
        def feedtype = order.feedtype;
        def factorString = order.weekendfactor;
        def startdate = order.startdate;
        def enddate = order.enddate;
        
        if(!destinationport .trim().equalsIgnoreCase("-1")){
            RunSysLogFeeds th = new RunSysLogFeeds(secuserid, destinationip, destinationport, frequency, feedtype, orderid, factorString);
            th.start();
            order.threadid = th.getId();
            order.save(flush: true);
        }else{
            //this is historical order.. needs to be written to a file.
            FileFeedGenerator ffg = new FileFeedGenerator();
            //public void generateFeed(String feedtype, int userid, String outputPrefix, String freq, Date startdate, Date enddate)
            ffg.generateFeed(feedtype, secuserid, destinationip, frequency, startdate, enddate, factorString);
        }
    }
}
