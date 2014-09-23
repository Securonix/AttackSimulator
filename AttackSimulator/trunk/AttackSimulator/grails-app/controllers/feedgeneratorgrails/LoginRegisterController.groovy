package feedgeneratorgrails

class LoginRegisterController {
    
    def springSecurityService;
    
    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
                redirect action: 'auth', params: params
        }
        //render(view:'/loginRegister/LoginRegister')
    }
}
