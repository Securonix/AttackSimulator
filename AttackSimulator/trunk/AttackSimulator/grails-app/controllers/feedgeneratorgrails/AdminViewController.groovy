package feedgeneratorgrails

import grails.plugins.springsecurity.Secured
import org.feedgeneratorgrails.SecUser;

class AdminViewController {
    
    @Secured(['ROLE_ADMIN'])
    def index() {
        def allun = SecUser.findAllByEnabled(false);
        def allapp = SecUser.findAllByEnabled(true);
        //def log = Logger.getLogger.
        render(view: 'admin.gsp', model: [usersunappr: allun, usersappr: allapp]);
    }
    
    
    def getTotalOrderCount() {
        def all = Orders.list();
        render all.size();
    }
    
}
