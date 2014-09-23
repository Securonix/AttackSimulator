
import org.feedgeneratorgrails.SecUser

class BootStrap {
    
    def springSecurityService
    
    def init = { servletContext ->
        SecUser user = new SecUser(id: 1, username: "anuj", password: springSecurityService.encodePassword("test"), 
                                    enabled: "1", accountExpired: "0", accountLocked:"0", passwordExpired: 0, company: "Securonix Solutions", 
                                    workemail: "avarma@securonix.com",
                                    phone: "213-321-9359");
        user.save();
    }
    
    def destroy = {
    
    }
}
