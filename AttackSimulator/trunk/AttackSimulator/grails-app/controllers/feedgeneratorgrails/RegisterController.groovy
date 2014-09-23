package feedgeneratorgrails

import grails.converters.JSON
import org.feedgeneratorgrails.SecUser

class RegisterController {
    def springSecurityService;
    
    /*
                          String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
                          String company;
                          String workemail;
                          String phone;
    */
    
    def addSecUser(){
        //System.out.println(params.get("username"))
        
        Boolean check = checkAllParams(params)
        
        if(check){
            SecUser secuser = new SecUser(id: 1, username: params.get("username"), password: params.get("password"), 
            enabled: "1", accountExpired: "0", accountLocked: "0", passwordExpired: "0", company: params.get("company"), workemail: params.get("email"),
            phone: params.get("contact"))
            secuser.save(flush: true);
            
            render "success" as String
        }else{
            render "failure" as String
        }
    }
    
    def checkAllParams(param){
        def username = param.get("username");
        def password = param.get("password");
        def company = param.get("company");
        def workemail = param.get("email");
        def phone = param.get("contact");
        
        if(username?.trim()){
            //username is fine
        }else{
            return false;
        }
        
        if(password?.trim()){
            //password is fine
        }else{
            return false;
        }
        
        if(company?.trim()){
            //company is fine
        }else{
            return false;
        }
        
        if(workemail?.trim()){
            //workemail is fine
        }else{
            return false;
        }
        
        if(phone?.trim()){
            //phone is fine
        }else{
            return false;
        }
        
        return true;
    }
}
