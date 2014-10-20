package feedgeneratorgrails

import grails.plugins.springsecurity.Secured
import org.feedgeneratorgrails.SecUser;
import org.feedgeneratorgrails.SecUserSecRole;
import org.feedgeneratorgrails.SecRole;

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
        
        render "success";
    }
    
    def approve(){
        def userid = params.get("userid");
        SecUser user = SecUser.get(userid);
        if(user != null){
            user.enabled = true;
            user.save(flush: true);
            render "success";
        }else{
            render "failure";
        }
    }
    
    def disapprove(){
        def userid = params.get("userid");
        SecUser user = SecUser.get(userid);
        if(user != null){
            user.enabled = false;
            user.save(flush: true);
            render "success";
        }else{
            render "failure";
        }
        
    }
    
    def delete(){
        def userid = params.get("userid");
        SecUser user = SecUser.get(userid);
        if(user != null){
            user.delete(flush: true);
            render "success";
        }else{
            render "failure";
        }
    }
    
    def setNewPassword(){
        def userid = params.get("userid");
        def password = params.get("password");
        SecUser user = SecUser.get(userid);
        if(user !=null){
            user.password = password;
            user.save(flush: true);
            render "success";
        }else{
            render "failure";
        }
    }
    
    def makeAdmin(){
        def userid = params.get("userid");
        def adminRole = SecRole.findAllByAuthority("ROLE_ADMIN").get(0);
        //System.out.println("The adminRoleId class: "+adminRoleid.getClass());
        SecUser user = SecUser.get(userid);
        if(user != null){
            SecUserSecRole.create(user, adminRole, true);
            render "success"
        }else{
            render "failure";
        }
    }
}
