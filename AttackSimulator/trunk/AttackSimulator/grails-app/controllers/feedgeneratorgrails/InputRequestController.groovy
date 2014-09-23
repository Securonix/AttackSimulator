package feedgeneratorgrails

class InputRequestController {
    
    def springSecurityService;
    
    def inputRequest() {
        if (springSecurityService.isLoggedIn()) {
            //redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
            def username = springSecurityService.currentUser.username;
            render(view:'InputRequestParameters', model: [user: username])
        }
        else {
                redirect (controller:"login", action: 'auth', params: params)
        }
    }
}
