package feedgeneratorgrails

import grails.converters.JSON
import org.feedgeneratorgrails.SecUser

class RegisterController {
    def springSecurityService;
    
    private ArrayList<String> errorMessage = new ArrayList<>();
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
            enabled: "0", accountExpired: "0", accountLocked: "0", passwordExpired: "0", company: "Dummy Company", workemail: params.get("email"),
            phone: "Dummy Phone", businessuser: businessuser);
            secuser.save(flush: true);
            
           //make entry into the sec_user_sec_role table
            System.out.println("Seeing  if I can find the user id of the saved user by just doing a id retreival from the object: "+secuser.id);
            
            //send out an email to the user telling him that his account has been registered and is waiting approval
            
            //send an approval to an administrator to approve the account.
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
            errorMessage.push("Problem with the username");
            return false;
        }
        
        if(password?.trim()){
            //password is fine
        }else{
            errorMessage.push("Problem with the password");
            return false;
        }
        
        if(workemail?.trim()){
            //workemail is fine
        }else{
            errorMessage.push("Problem with the workemail");
            return false;
        }
        
        //check if the user already exists in the db.
        def userExists = SecUser.findAllByUsername(username);
        
        if(!userExists.isEmpty()){
            errorMessage.push("User already exists in the database");
            return false;
        }
        
        return true;
    }
}
