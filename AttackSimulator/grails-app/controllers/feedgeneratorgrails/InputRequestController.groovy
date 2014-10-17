package feedgeneratorgrails

import org.feedgeneratorgrails.Orders;

class InputRequestController {
    
    def springSecurityService;
    
    def inputRequest() {
        if (springSecurityService.isLoggedIn()) {
            //redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
            def userid = springSecurityService.currentUser.id;
            def username = springSecurityService.currentUser.username;
            def orders = Orders.findAllByApprovedAndUserid("1", userid);
            render(view:'InputRequestParameters', model: [user: username, orders: orders])
        }
        else {
                redirect (controller:"login", action: 'auth', params: params)
        }
    }
}
