package attacksimulator;

import org.feedgeneratorgrails.Feedmaster;

class UielementsController {

    def index() {
    
    }
    
    def getFeedtypes(){
        def feedtypes = Feedmaster.getAll();
        render(template:'orderlist', model:[feeds:feedtypes]);
    }
}
