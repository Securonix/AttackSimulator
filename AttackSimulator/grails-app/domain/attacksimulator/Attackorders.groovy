package attacksimulator

class Attackorders {
    
    Integer id;
    Integer attackid;
    Integer secuserid;
    String transactionfile
    String destinationip;
    String destinationport;
    Date dayofattack;
    Date timeofattack;
    String frequency;
    Integer recurring;
    
    static constraints = {
        id blank: false
        attackid blank: false
        secuserid blank: false
        transactionfile blank: false
    }
}
