package org.feedgeneratorgrails

class Dmzusermapping {
    Integer id;
    Integer secUserid;
    String dmzaddress;
    
    static constraints = {
        id blank:false
        secUserid blank: false
        dmzaddress blank: false
    }
}
