package feedgeneratorgrails

import grails.plugins.springsecurity.Secured
import org.feedgeneratorgrails.Orders;

class AdminViewController {
    
    @Secured(['ROLE_ADMIN'])
    def index() {
        def all = Orders.findAllByApproved("0");
        def orders;
        if(params.containsKey("offset"))
            orders = Orders.findAllByApproved("0", [offset:params.offset, max:params.max]);
        else
            orders = Orders.findAllByApproved("0", [offset: 0, max: 10]);
        //def log = Logger.getLogger.
        render(view: 'admin.gsp', model: [orders: orders, count: all.size()]);
    }
    
    
    def getTotalOrderCount() {
        def all = Orders.list();
        render all.size();
    }
    
}
