package attacksimulator



class TestJob {
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute() {
        // execute job
       System.out.println("Executed from the job");
    }
}
