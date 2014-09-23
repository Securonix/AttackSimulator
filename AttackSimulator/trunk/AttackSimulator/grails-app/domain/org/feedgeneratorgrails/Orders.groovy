package org.feedgeneratorgrails

class Orders {
    Integer id;
    Integer userid;
    String feedtype;
    String frequency;
    Date startdate;
    Date enddate;
    String destinationip;
    String destinationport;
    Integer approved;
    Integer threadid;
    
    static constraints = {
        userid blank: false
        feedtype blank:false
        frequency blank: false
        startdate blank:false
        destinationip: false
        destinationport: false
        approved blank: false
        threadid blank: false
    }
}
