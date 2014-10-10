package feedgeneratorgrails

import attacksimulator.Extusermapping
import com.attacksimulator.*;
import com.attacksimulator.MySQLDBClass
import com.attacksimulator.UserImport;
import org.feedgeneratorgrails.Dmzusermapping
import org.feedgeneratorgrails.Ipcountry;
import org.feedgeneratorgrails.Sysipusermapping;
import org.feedgeneratorgrails.Users;
import org.feedgeneratorgrails.Usermaster;

class EnvironmentController {
    
    def springSecurityService;
    
    def index() {
        if(springSecurityService.isLoggedIn()){
            def getOnlyCountry = Ipcountry.createCriteria();
            def countries = getOnlyCountry.list(){
                projections {
                    distinct("country")
                }
            }
            
            def sysipusers = Sysipusermapping.findAllBySecuserid(springSecurityService.currentUser.id);
            System.out.println("Class type of sysipuser: "+sysipusers.getClass());
            
            ArrayList<Usermaster> users = new ArrayList<>();
            ArrayList<String> ipaddress1 = new ArrayList<>();
            ArrayList<String> ipaddress2 = new ArrayList<>();
            ArrayList<String> ipaddress3 = new ArrayList<>();
            
            for(Sysipusermapping sysipuser: sysipusers){
                def user = Usermaster.findById(sysipuser.userid);
                System.out.println("Sysipuser firstname:" + user.firstname);
                System.out.println("Sysipuser lastname:" + user.lastname);
                System.out.println("Sysipuser department:" + user.department);
                users.add(user);
                ipaddress1.add(sysipuser.ipaddress1);
                ipaddress2.add(sysipuser.ipaddress2);
                ipaddress3.add(sysipuser.ipaddress3);
            }
            
            def dmzaddress = Dmzusermapping.findAllBySecuserid(springSecurityService.currentUser.id);
            def extcountry = Extusermapping.findAllBySecuserid(springSecurityService.currentUser.id);
            
           render(view:'/environment/EnvironmentDetails.gsp', model: [user: springSecurityService.currentUser.username, countries: countries, users: users, ipaddress1: ipaddress1, ipaddress2: ipaddress2, ipaddress3: ipaddress3, dmzaddress:dmzaddress, countryByUser: extcountry]);
           //render countries;
        }else{
            redirect(controller:"login", action:"auth");
        }
    }
    
    def createUserTables(){
        //external ips and dmzrange will be implemented in 3.0 have no time to do this.
        def numofusers = params.get("numusers");
         String [] countries = params.list("country[]"); //not saving
        def internalrange = params.get("internal");
        def dmzrange = params.get("dmzrange"); //not saving
        def secuserid = springSecurityService.currentUser.id;
        
        //sysipusermapping
        def useripmapping = getUserIpMapping(internalrange, Integer.parseInt(numofusers), secuserid);
        for(Map.Entry<Integer, ArrayList<String>> userip : useripmapping.entrySet()){
            Integer userid = userip.getKey();
            ArrayList<String> ips = userip.getValue();
            String ipaddress = ips[0]+","+ips[1]+","+ips[2];
            Sysipusermapping systable = new Sysipusermapping(id: 1, secuserid: secuserid, userid: userid, ipaddress1: ips[0], ipaddress2: ips[1], ipaddress3: ips[2]);
            systable.save(flush: true);
        }
        
        //dmzusermapping
         Integer randomDmzSize = (new Random()).nextInt(50);
        for(int i=0; i<randomDmzSize; i++){
            String dmzaddress = getDMZRange(dmzrange);
            Dmzusermapping dmzuser = new Dmzusermapping(id:1, secuserid: secuserid, dmzaddress: dmzaddress);
            dmzuser.save(flush: true);
        }
        
        //extusermapping
        for(int i=0; i<countries.length; i++){
            Extusermapping extcountry = new Extusermapping(id: 1, secuserid: secuserid, country: countries[i]);
            extcountry.save(flush: true);
        }
        
        render "success"
    }
    
    String getDMZRange(String dmzrange){
        /*
        *   Class A range: 0.0.0.0   -------    127.255.255.255
        *   Class B range: 128.0.0.0 -----    191.255.  255.255
        *   Class C range: 192.0.0.0 ------   223.255.255.255  
        */
       
        Integer firstquart;
        Integer secondquart;
        Integer thirdquart;
        Integer lastquart;
       
        switch(dmzrange){
            case "classa" :
                firstquart = getRandomInRange(0, 127);
                secondquart = getRandomInRange(0, 255);
                thirdquart = getRandomInRange(0,255);
                lastquart = getRandomInRange(0,255);
                break;
                
            case "classb" :
                firstquart = getRandomInRange(128, 191);
                secondquart = getRandomInRange(0, 255);
                thirdquart = getRandomInRange(0,255);
                lastquart = getRandomInRange(0,255);
                break;
                
            case "classc" :
                firstquart = getRandomInRange(192, 223);
                secondquart = getRandomInRange(0, 255);
                thirdquart = getRandomInRange(0,255);
                lastquart = getRandomInRange(0,255);
                break;
                
            default:
                throw new NumberFormatException();
        }
        
        return firstquart+"."+secondquart+"."+thirdquart+"."+lastquart;
    }
    
    HashMap< Integer, ArrayList<String> > getUserIpMapping(String internalrange, Integer numofusers, Integer secuserid){
        /*
        *   Class A range: 0.0.0.0   -------    127.255.255.255
        *   Class B range: 128.0.0.0 -----    191.255.  255.255
        *   Class C range: 192.0.0.0 ------   223.255.255.255  
        */
       
        ArrayList<Integer> userIds = getListOfUsers(numofusers);
        HashSet<String> usedUpIps = new HashSet<>();
        
        HashMap<Integer, ArrayList<String>> resultSet = new HashMap<>();
        
        if (internalrange != null && internalrange.equalsIgnoreCase("classa")){
            //we need to generate a random ip address for class a networks
            //0-127
            
            for(int i=0; i<numofusers; i++){
                //generating three random ip addresses, since I am seeing that for each user the userip file
                //generally has three addresses.
                ArrayList<String> threeUniqueIp = new ArrayList<>();
                
                for(int j=0; j<3; j++){
                    Integer firstquart = getRandomInRange(0, 127);
                    Integer secondquart = getRandomInRange(0, 255);
                    Integer thirdquart = getRandomInRange(0,255);
                    Integer lastquart = getRandomInRange(0,255);
                    System.out.println(firstquart+" : First Quart");
                    System.out.println(secondquart+" : Second Quart")
                    System.out.println(thirdquart+" : Third Quart")
                    System.out.println(lastquart+" : Fourth Quart")
                    
                    String ipaddress = firstquart.toString()+"."+secondquart.toString()+"."+thirdquart.toString()+"."+lastquart.toString();
                    if(usedUpIps.contains(ipaddress)){
                        j=j-1;
                    }else{
                        threeUniqueIp.add(ipaddress);
                        usedUpIps.add(ipaddress);
                        if(threeUniqueIp.size() == 3)
                            break;
                    }
                }
                
                
                //Unique Ip Addresses found to associate with the users now. The users are also random.
                //nothing need to be done.
                
                resultSet.put(userIds.get(i), threeUniqueIp);
             }
        }else if(internalrange != null && internalrange.equalsIgnoreCase("classb")){
            for(int i=0; i<numofusers; i++){
                //generating three random ip addresses, since I am seeing that for each user the userip file
                //generally has three addresses.
                ArrayList<String> threeUniqueIp = new ArrayList<>();
                
                for(int j=0; j<3; j++){
                    Integer firstquart = getRandomInRange(128, 191);
                    Integer secondquart = getRandomInRange(0, 255);
                    Integer thirdquart = getRandomInRange(0,255);
                    Integer lastquart = getRandomInRange(0,255);
                    System.out.println(firstquart+" : First Quart");
                    System.out.println(secondquart+" : Second Quart")
                    System.out.println(thirdquart+" : Third Quart")
                    System.out.println(lastquart+" : Fourth Quart")
                    
                    String ipaddress = firstquart.toString()+"."+secondquart.toString()+"."+thirdquart.toString()+"."+lastquart.toString();
                    if(usedUpIps.contains(ipaddress)){
                        j=j-1;
                    }else{
                        threeUniqueIp.add(ipaddress);
                        usedUpIps.add(ipaddress);
                        if(threeUniqueIp.size() == 3)
                            break;
                    }
                }
                
                
                //Unique Ip Addresses found to associate with the users now. The users are also random.
                //nothing need to be done.
                
                resultSet.put(userIds.get(i), threeUniqueIp);
             }
        }else if(internalrange != null && internalrange.equalsIgnoreCase("classc")){
            for(int i=0; i<numofusers; i++){
                //generating three random ip addresses, since I am seeing that for each user the userip file
                //generally has three addresses.
                ArrayList<String> threeUniqueIp = new ArrayList<>();
                
                for(int j=0; j<3; j++){
                    Integer firstquart = getRandomInRange(192, 223);
                    Integer secondquart = getRandomInRange(0, 255);
                    Integer thirdquart = getRandomInRange(0,255);
                    Integer lastquart = getRandomInRange(0,255);
                    System.out.println(firstquart+" : First Quart");
                    System.out.println(secondquart+" : Second Quart")
                    System.out.println(thirdquart+" : Third Quart")
                    System.out.println(lastquart+" : Fourth Quart")
                    
                    String ipaddress = firstquart.toString()+"."+secondquart.toString()+"."+thirdquart.toString()+"."+lastquart.toString();
                    if(usedUpIps.contains(ipaddress)){
                        j=j-1;
                    }else{
                        threeUniqueIp.add(ipaddress);
                        usedUpIps.add(ipaddress);
                        if(threeUniqueIp.size() == 3)
                            break;
                    }
                }
                
                
                //Unique Ip Addresses found to associate with the users now. The users are also random.
                //nothing need to be done.
                
                resultSet.put(userIds.get(i), threeUniqueIp);
             }
        }
        
        return resultSet;
    }
    
    Integer getRandomInRange(Integer lowerbound, Integer upperbound){
        if(upperbound - lowerbound <= 0){
            return 0;
        }
        
        Random rand = new Random();
        
        Integer randomNumber = rand.nextInt(upperbound - lowerbound + 1)+lowerbound;
        
        return randomNumber;
    }
    
    ArrayList<Integer> getListOfUsers(Integer numofusers ){
        //This list of users have to be random.
        ArrayList<Integer> randomIndexs = new ArrayList<>();
        Random rand = new Random();
        //System.out.println("This function is being called so this will be printed for sure");
        for(int i=1; i<677+1; i++){
            randomIndexs.add(i);
        }
        
        Collections.shuffle(randomIndexs, new Random(System.nanoTime()));
        
        for(int i=0; i<numofusers; i++){
            System.out.println(randomIndexs.get(i));
        }
        
        ArrayList<Integer> resultSet = new ArrayList<>();
        
        for(int i=0; i < numofusers; i++){
            resultSet.add(randomIndexs.get(i));
        }
        
        return resultSet;
    }
    
    def modifyEnvironment(){
        def secuserid = springSecurityService.currentUser.id;
        deleteEnvironForUser(secuserid);
        
        render "success";
    }
    
    void deleteEnvironForUser(Integer secuserid){
        //Have to delete the entries from secusermapping table
        def sysipusers = Sysipusermapping.findAllBySecuserid(secuserid);
        for(Sysipusermapping sysipuser: sysipusers){
            sysipuser.delete(flush: true);
        }
        
        def dmzaddresses = Dmzusermapping.findAllBySecuserid(secuserid);
        for(Dmzusermapping dmzaddress: dmzaddresses){
            dmzaddress.delete(flush: true);
        }
        
        def countries = Extusermapping.findAllBySecuserid(secuserid);
        for(Extusermapping country : countries){
            country.delete(flush: true);
        }
    }
    
    def getDownloadLink(){
        MySQLDBClass mydb = new MySQLDBClass();
        String basepath = request.getSession().getServletContext().getRealPath("/");
        String hreflink = mydb.getUserDownloadLink(springSecurityService.currentUser.id, basepath);
        hreflink = "<a href=\"http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+hreflink+"\" download>Download user data</a>";
        render hreflink as String;
    }
    
    def deleteFile(){
        String filename = params.get("filename");
        String basepath = request.getSession().getServletContext().getRealPath("/");
        File file = new File(basepath+"/downloads/"+filename);
        
    }
}
