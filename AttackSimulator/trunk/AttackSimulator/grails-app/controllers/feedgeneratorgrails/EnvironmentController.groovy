package feedgeneratorgrails

import com.attacksimulator.*;
import com.attacksimulator.UserImport;
import org.feedgeneratorgrails.Ipcountry;
import org.feedgeneratorgrails.Sysipusermapping;
import org.feedgeneratorgrails.Users;

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
           render(view:'/environment/EnvironmentDetails.gsp', model: [user: springSecurityService.currentUser.username, countries: countries]);
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
        
        def useripmapping = getUserIpMapping(internalrange, Integer.parseInt(numofusers), secuserid);
        
        for(Map.Entry<Integer, ArrayList<String>> userip : useripmapping.entrySet()){
            Integer userid = userip.getKey();
            ArrayList<String> ips = userip.getValue();
            String ipaddress = ips[0]+","+ips[1]+","+ips[2];
            Sysipusermapping systable = new Sysipusermapping(id: 1, secuserid: secuserid, userid: userid, ipaddress: ipaddress);
            systable.save(flush: true);
        }
        
        render "success"
    }
    
    HashMap<Integer, ArrayList<String>> getUserIpMapping(String internalrange, Integer numofusers, Integer secuserid){
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
    
}
