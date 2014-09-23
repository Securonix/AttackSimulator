package org.feedgeneratorgrails

class Ipcountry {
    
    String iprangebegin;
    Integer id;
    String iprangeend;
    String countrycode;
    String country;
    
    static constraints = {
        iprangebegin blank: false
        id blank: false
        iprangeend blank: false;
        countrycode blank: false;
        country blank: false
    }
}
