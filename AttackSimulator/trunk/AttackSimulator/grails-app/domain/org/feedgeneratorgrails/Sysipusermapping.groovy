package org.feedgeneratorgrails

class Sysipusermapping {
    
    Integer id;
    Integer secuserid;
    Integer userid;
    String ipaddress;
    
    static constraints = {
        id blank: false
        secuserid blank: false
        userid blank: false
        ipaddress blank: false
    }
}
