
quartz {
    autoStartup = true
    jdbcStore = true
    waitForJobsToCompleteOnShutdown = false
    exposeSchedulerInRepository = false

    props {
        scheduler.skipUpdateCheck = true
    }
}

environments {
    test {
        quartz {
            autoStartup = false
        }
    }
}
