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
        Boolean businessuser;
        
        if(params.get("businessuser").equalsIgnoreCase("true")){
            businessuser = "1";
        }else{
            businessuser = "0";
        }
        
        if(check){
            SecUser secuser = new SecUser(id: 1, username: params.get("username"), password: params.get("password"), 
            enabled: "1", accountExpired: "0", accountLocked: "0", passwordExpired: "0", company: "Dummy Company", workemail: params.get("email"),
            phone: "Dummy Phone", businessuser: businessuser);
            secuser.save(flush: true);
            
            render "success" as String
        }else{
            render "failure" as String
        }
    }
    
    def checkAllParams(param){
        def username = param.get("username");
        def password = param.get("password");
        def workemail = param.get("email");
        
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
        
        if(workemail?.trim()){
            //workemail is fine
        }else{
            return false;
        }
        
        return true;
    }
}
