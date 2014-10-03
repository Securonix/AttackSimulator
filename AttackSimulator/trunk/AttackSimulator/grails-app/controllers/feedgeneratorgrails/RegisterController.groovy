package feedgeneratorgrails

import grails.converters.JSON
import org.feedgeneratorgrails.SecUser
import com.attacksimulator.EmailUtility;

class RegisterController {
    def springSecurityService;
    
    private ArrayList<String> errorMessage = new ArrayList<>();
    private hostname="http://localhost:8081/AttackSimulator/";
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
            SecUser secuser = new SecUser(id: 1, name: params.get("name"), username: params.get("username"), password: params.get("password"), 
            enabled: "0", accountExpired: "0", accountLocked: "0", passwordExpired: "0", company: "Dummy Company", workemail: params.get("email"),
            phone: "Dummy Phone", businessuser: businessuser);
            secuser.save(flush: true);
            
           //make entry into the sec_user_sec_role table
            System.out.println("Seeing  if I can find the user id of the saved user by just doing a id retreival from the object: "+secuser.id); //Yes, I was able to get the id
            ArrayList<String> toEmails = new ArrayList<>();
            toEmails.add(params.get("email"));
            EmailUtility emailUtility = new EmailUtility("anujva@gmail.com", toEmails);
            //send out an email to the user telling him that his account has been registered and is waiting approval
            String subject1 = "You have been registered";
            String body1 = "Hi "+params.get("username")+",\n"+
                    "Your details have been saved and is awaiting approval from the Attack Simulator Admin.\n"+
                    "\n"+
                    "Thank you for your interest in Securonix.\n"+
                    "Regards,\n"+
                    "Securonix Team.";
            
            String subject2 = "A new user registered";
            String body2 = "Hi Tanuj,\n"+
                    "A new user has just registered. Please approve this by clicking on this link.\n"+hostname+"Register/registerApproval?approved=1&userid="+secuser.id;
                    "Regards,\n"+
                    "Securonix Team.";
            emailUtility.sendMail(body1, subject1, true, false, true);
            
            //send an approval to an administrator to approve the account.
            emailUtility.sendMail(body2, subject2, true, false, true);
            
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
    
    def registerApproval(){
        def userid = params.get("userid");
        def approved = params.get("approved");
        
        if(Integer.parseInt(approved) == 1){
            SecUser secuser = SecUser.get(Integer.parseInt(userid));
            secuser.enabled = "1";
            secuser.save(flush: true);
            
            String subject = "You have been approved.";
            String body = "Hi "+secuser.username + "\n\n"+"You have been approved to use the Attack Simulator. \n\nThanks,\nSecuronix Team";
            //send an email to the user saying that you have been approved.
            EmailUtility emailutility = new EmailUtility("anujva@gmail.com", secuser.workemail);
            emailutility.sendMail(body, subject, true, false, true);
        }
        
        
        render "success";
    }
}
