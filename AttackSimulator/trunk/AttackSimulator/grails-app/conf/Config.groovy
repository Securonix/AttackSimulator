// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = AttackSimulator // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.includes = ['/images/**', '/css/**', '/js/**', '/plugins/**']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"
grails.views.gsp.sitemesh.preprocess = false

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}

grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false
grails.plugin.springsecurity.active = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}
    /*
    debug 'grails.app.controllers.feedgeneratorgrails.LoginRegisterController'
                'grails.app.controllers.feedgeneratorgrails.InputRequestController'
                'grails.app.controllers.feedgeneratorgrails.LoginController'
                'grails.app.controllers.feedgeneratorgrails.RegisterController'
                'org.springframework.security'
    */
    debug  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate',
           'org.quartz.*'
}



// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'org.feedgeneratorgrails.SecUser'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'org.feedgeneratorgrails.SecUserSecRole'
grails.plugins.springsecurity.authority.className = 'org.feedgeneratorgrails.SecRole'
grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/'
grails.plugins.springsecurity.successHandler. alwaysUseDefault =false

 grails.plugins.springsecurity.providerNames = [
   'daoAuthenticationProvider',
   ]

//Additions after consulting Profiler Code
/*
grails.plugins.springsecurity.useSecurityEventListener = true
grails.plugins.springsecurity.useSessionFixationPrevention = true
grails.plugins.springsecurity.sessionFixationPrevention.migrate = true

grails.plugins.springsecurity.successHandler.alwaysUseDefault = false
grails.plugins.springsecurity.successHandler.alwaysUseDefaultTargetUrl = false
grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/'
*/

/*
     *  For MySQL... only needed for netbeans
     *
     */
grails.naming.entries = ['jdbc/AttackSimulatorDS': [
	type: "javax.sql.DataSource", //required
        auth: "Container", // optional
        description: "Data source for ...", //optional
        //properties for particular type of resource
	//url: "jdbc:mysql://10.127.226.74:3306/securonix",
       url: "jdbc:mysql://localhost:3306/attacksimdev?autoReconnect=true",
       // url: "jdbc:mysql://localhost:3306/securonix46_demo_bh_final?autoReconnect=true",
        username: "root",
        password: "password",
//password: "\$ecurity.4u",
	driverClassName: "com.mysql.jdbc.Driver",
//        factory: "org.apache.tomcat.jdbc.pool.DataSourceFactory",

//        url: "jdbc:oracle:thin:@192.168.1.2:1522/orcl",	
//        username: "securonix",
//        password: "password",
//        driverClassName: "oracle.jdbc.OracleDriver",
       maxActive:"300",
       maxIdle:"20",
       testOnBorrow:"true",
       testWhileIdle:"true",
	testOnReturn:"true",
	timeBetweenEvictionRunsMillis:"30000" ,
	validationQuery:"SELECT 1",
	removeAbandonedTimeout:"60",
        removeAbandoned:"true",
        logAbandoned:"true",
	jdbcInterceptors:"ResetAbandonedTimer",
	abandonWhenPercentageFull:"50"
                        
//        initialSize:"5",
//        maxActive: "100",
//        maxIdle: "40",         
//        minIdle : "5",
//        maxIdle : "25",
//        initialSize : "50",
//        minEvictableIdleTimeMillis :"60000",
//        timeBetweenEvictionRunsMillis :"60000",
//        numTestsPerEvictionRun : "3",
//        maxWait :"10000",
//        validationQuery: "SELECT 1",
//        validationQueryTimeout: "3", 
//        validationInterval : "15000",
//        testOnBorrow:"true",
//        testOnReturn:"false", 
//        ignoreExceptionOnPreLoad:"true",
//        testWhileIdle :"true",
     //   jmxEnabled:"true",
       // maxAge: "10 * 60000", 
     //   jdbcInterceptors : "ConnectionState;StatementCache(max=200)",
      //  timeBetweenEvictionRunsMillis: "5000",
       // factory:"com.securonix.application.common.util.EncryptionFactory"
     	]

    /*
     *  For SQL Server... only needed for netbeans
     *
     */
        
//       
//        grails.naming.entries = ['jdbc/securonixDS': [
//	type: "javax.sql.DataSource", //required
//        auth: "Container", // optional
//        description: "Data source for ...", //optional
//        //properties for particular type of resource
//	url: "jdbc:jtds:sqlserver://192.168.1.29:1433/securonix",
//	username: "sa",
//	password: "abcd1234",
//	driverClassName: "net.sourceforge.jtds.jdbc.Driver",
//	maxActive: "10", //and so on
//        maxIdle: "4"
//	]
        
    //
]

hibernate {
    //config.location = "file:src/java/hibernate.cfg.xml"

    //cache.use_second_level_cache=true
    //cache.use_query_cache=true
    //cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
    cache.use_query_cache=true
    cache.use_second_level_cache=false
    cache.provider_class='org.hibernate.cache.EhCacheProvider'

}