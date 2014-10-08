package attacksimulator

class Extusermapping {
    Integer id;
    Integer secUserid;
    String country;
    
    static constraints = {
        id blank: false
        secUserid blank: false
        country blank: false
    }
}
