package org.feedgeneratorgrails

class Dmzusermapping {
    Integer id;
    Integer secuserid;
    String dmzaddress;
    
    static constraints = {
        id blank:false
        secuserid blank: false
        dmzaddress blank: false
    }
}
