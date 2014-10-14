package attacksimulator

class Attackorders {
    
    Integer id;
    Integer attackid;
    Integer attackerid;
    String username;
    Integer secuserid;
    String transactionfile;
    String destinationip;
    String destinationport;
    Date dayofattack;
    String timeofattack;
    String frequency;
    
    static constraints = {
        id blank: false
        attackid blank: false
        attackerid blank: false
        secuserid blank: false
        transactionfile blank: false
    }
}
