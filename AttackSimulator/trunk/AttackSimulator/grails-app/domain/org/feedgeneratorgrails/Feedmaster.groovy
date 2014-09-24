package org.feedgeneratorgrails

class Feedmaster {
    Integer id;
    String feedtype;
    String transactionfilepath;
    
    static constraints = {
        id blank: false;
        feedtype blank:false;
        transactionfilepath blank:false;
    }
}
