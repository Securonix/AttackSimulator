package attacksimulator

class Extusermapping {
    Integer id;
    Integer secuserid;
    String country;
    
    static constraints = {
        id blank: false
        secuserid blank: false
        country blank: false
    }
}
