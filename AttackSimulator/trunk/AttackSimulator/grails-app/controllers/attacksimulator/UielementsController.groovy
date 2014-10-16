package attacksimulator;

import org.feedgeneratorgrails.Feedmaster;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.SimpleScheduleBuilder;


class UielementsController {
	
	def quartzScheduler;

    def index() {
		System.out.println("The type of the scheduler is : "+ quartzScheduler.getClass());
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
		
    }
    
    def getFeedtypes(){
        def feedtypes = Feedmaster.getAll();
        render(template:'orderlist', model:[feeds:feedtypes]);
    }
}
