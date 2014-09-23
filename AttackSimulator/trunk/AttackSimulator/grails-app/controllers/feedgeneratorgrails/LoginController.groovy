package feedgeneratorgrails

import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.feedgeneratorgrails.Sysipusermapping;

class LoginController {

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

    /**
     * Dependency injection for the springSecurityService.
     */
    def springSecurityService

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def index = {
        if (springSecurityService.isLoggedIn()) {
            if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
                println('Inside Authentication Test ROLE_ADMIN');
                redirect uri: '/AdminView';
                return;
            }else if(SpringSecurityUtils.ifAllGranted("ROLE_USER")){
                println('Inside Authentication Test ROLE_USER')
                //test if the user has already put in his environment details.If he has then redirect him to orders page
                def userid = springSecurityService.currentUser.id;
                def testUserInSysMap = Sysipusermapping.findAllBySecuserid(userid);
                
                if(testUserInSysMap.size() == 0 || testUserInSysMap == null){
                        redirect uri:'/Environment';
                }else{
                    redirect uri: '/inputRequest/inputRequest';
                }
            }else{
                //test if the user has already put in his environment details.If he has then redirect him to orders page
                def userid = springSecurityService.currentUser.id;
                def testUserInSysMap = Sysipusermapping.findAllBySecuserid(userid);
                
                if(testUserInSysMap.size() == 0 || testUserInSysMap == null){
                        redirect uri:'/Environment';
                }else{
                    redirect uri: '/inputRequest/inputRequest';
                }
            }
            
            return;
        }
        else {
            redirect action: 'auth', params: params
        }
    }

    /**
     * Show the login page.
     */
    def auth = {

        def config = SpringSecurityUtils.securityConfig
           println('Inside Authentication Test');
           println('SpringSecurityService Status: '+ springSecurityService.isLoggedIn());
           
        if (springSecurityService.isLoggedIn()) {
            if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
                println('Inside Authentication Test ROLE_ADMIN');
                redirect uri: '/AdminView';
                return;
            }else if(SpringSecurityUtils.ifAllGranted("ROLE_USER")){
                println('Inside Authentication Test ROLE_USER')
                //test if the user has already put in his environment details.If he has then redirect him to orders page
                def userid = springSecurityService.currentUser.id;
                def testUserInSysMap = Sysipusermapping.findAllBySecuserid(userid);
                
                if(testUserInSysMap.size() == 0 || testUserInSysMap == null){
                        redirect uri:'/Environment';
                }else{
                    redirect uri: '/inputRequest/inputRequest';
                }
            }else{
                //test if the user has already put in his environment details.If he has then redirect him to orders page
                def userid = springSecurityService.currentUser.id;
                def testUserInSysMap = Sysipusermapping.findAllBySecuserid(userid);
                
                if(testUserInSysMap.size() == 0 || testUserInSysMap == null){
                        redirect uri:'/Environment';
                }else{
                    redirect uri: '/inputRequest/inputRequest';
                }
            }
            
            return;
        }

        String view = '/loginRegister/LoginRegister.gsp';
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
            rememberMeParameter: config.rememberMe.parameter]
    }

    /**
     * The redirect action for Ajax requests.
     */
    def authAjax = {
        println('Inside Authentication Test');
        println('SpringSecurityService Status: '+ springSecurityService.isLoggedIn());
        
        if (springSecurityService.isLoggedIn()) {
            if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")){
                println('Inside Authentication Test ROLE_ADMIN');
                redirect uri: '/AdminView';
                return;
            }else if(SpringSecurityUtils.ifAllGranted("ROLE_USER")){
                println('Inside Authentication Test ROLE_USER')
                //test if the user has already put in his environment details.If he has then redirect him to orders page
                def userid = springSecurityService.currentUser.id;
                def testUserInSysMap = Sysipusermapping.findAllBySecuserid(userid);
                
                if(testUserInSysMap.size() == 0 || testUserInSysMap == null){
                        redirect uri:'/Environment';
                }else{
                    redirect uri: '/inputRequest/inputRequest';
                }
            }else{
                //test if the user has already put in his environment details.If he has then redirect him to orders page
                def userid = springSecurityService.currentUser.id;
                def testUserInSysMap = Sysipusermapping.findAllBySecuserid(userid);
                
                if(testUserInSysMap.size() == 0 || testUserInSysMap == null){
                        redirect uri:'/Environment';
                }else{
                    redirect uri: '/inputRequest/inputRequest';
                }
            }
            return;
        }
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    /**
     * Show denied page.
     */
    def denied = {
        if (springSecurityService.isLoggedIn() &&
            authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full = {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
        model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
            postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail = {

        def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.expired")
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.passwordExpired")
            }
            else if (exception instanceof DisabledException) {
                msg = g.message(code: "springSecurity.errors.login.disabled")
            }
            else if (exception instanceof LockedException) {
                msg = g.message(code: "springSecurity.errors.login.locked")
            }
            else {
                msg = g.message(code: "springSecurity.errors.login.fail")
                log.debug "$actionName EXCEPTION - "+ msg
            }
        }
        
        log.debug "$actionName EXCEPTION - "+ msg

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
            flash.message = msg
            redirect action: 'auth', params: params
        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess = {
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied = {
        render([error: 'access denied'] as JSON)
    }
}
