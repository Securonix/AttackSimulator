package org.feedgeneratorgrails

class Usermaster {
    Integer id;
    String userid;
    String firstname;
    String middlename;
    String lastname;
    String workemail;
    String lanid;
    String networkid;
    String manageremployeeid;
    String department;
    String internalip;
    String desktopipaddress;
    String desktopname;
    
    static constraints = {
        id blank: false
        userid blank: false
        firstname blank: false
        lastname blank: false
        workemail blank: false
    }
}
