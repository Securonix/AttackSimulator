dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/attacksimdev?useUnicode=yes&characterEncoding=UTF-8"
            username = "root"
            password = "open24X7"
            
            properties 
            { 
                maxActive = 50 
                maxIdle = 25 
                minIdle =1 
                initialSize = 1 
                minEvictableIdleTimeMillis = 60000 
                timeBetweenEvictionRunsMillis = 60000 
                numTestsPerEvictionRun = 3 
                maxWait = 10000 

                testOnBorrow = true 
                testWhileIdle = true 
                testOnReturn = false 

                validationQuery = "SELECT 1" 
            } 
        }
        hibernate {
            show_sql = true
        }
    }
    test {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/attacksimtest?useUnicode=yes&characterEncoding=UTF-8"
            username = "root"
            password = "open24X7"
            
            properties 
            { 
                maxActive = 50 
                maxIdle = 25 
                minIdle =1 
                initialSize = 1 
                minEvictableIdleTimeMillis = 60000 
                timeBetweenEvictionRunsMillis = 60000 
                numTestsPerEvictionRun = 3 
                maxWait = 10000 

                testOnBorrow = true 
                testWhileIdle = true 
                testOnReturn = false 

                validationQuery = "SELECT 1" 
            } 
        } 
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/attacksimprod?useUnicode=yes&characterEncoding=UTF-8"
            username = "root"
            password = "open24X7"
            
            properties 
            { 
                maxActive = 50 
                maxIdle = 25 
                minIdle =1 
                initialSize = 1 
                minEvictableIdleTimeMillis = 60000 
                timeBetweenEvictionRunsMillis = 60000 
                numTestsPerEvictionRun = 3 
                maxWait = 10000 

                testOnBorrow = true 
                testWhileIdle = true 
                testOnReturn = false 

                validationQuery = "SELECT 1" 
            } 
        }
    }
}