package org.feedgeneratorgrails

class Dmzusermapping {
    Integer id;
    Integer secuserid;
    String dmzaddress;
    String dmzhostname;
    
    static constraints = {
        id blank:false
        secuserid blank: false
        dmzaddress blank: false
        dmzhostname blank: false
    }
}
