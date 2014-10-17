package attacksimulator

class Usersyslogdetails {
    
    Integer id;
    Integer secuserid;
    String destinationip;
    String destinationport;
    
    static constraints = {
        id blank: false
        secuserid blank: false
        destinationip blank: false
        destinationport blank: false
    }
}
