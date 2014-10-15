import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulatorindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
invokeTag('message','g',84,['code':("default.link.skip.label"),'default':("Skip to content&hellip;")],-1)
printHtmlPart(1)
invokeTag('meta','g',88,['name':("app.version")],-1)
printHtmlPart(2)
invokeTag('meta','g',89,['name':("app.grails.version")],-1)
printHtmlPart(3)
expressionOut.print(GroovySystem.getVersion())
printHtmlPart(4)
expressionOut.print(System.getProperty('java.version'))
printHtmlPart(5)
expressionOut.print(grails.util.Environment.reloadingAgentEnabled)
printHtmlPart(6)
expressionOut.print(grailsApplication.controllerClasses.size())
printHtmlPart(7)
expressionOut.print(grailsApplication.domainClasses.size())
printHtmlPart(8)
expressionOut.print(grailsApplication.serviceClasses.size())
printHtmlPart(9)
expressionOut.print(grailsApplication.tagLibClasses.size())
printHtmlPart(10)
for( plugin in (applicationContext.getBean('pluginManager').allPlugins) ) {
printHtmlPart(11)
expressionOut.print(plugin.name)
printHtmlPart(12)
expressionOut.print(plugin.version)
printHtmlPart(13)
}
printHtmlPart(14)
for( c in (grailsApplication.controllerClasses.sort { it.fullName }) ) {
printHtmlPart(15)
createTagBody(2, {->
expressionOut.print(c.fullName)
})
invokeTag('link','g',116,['controller':(c.logicalPropertyName)],2)
printHtmlPart(16)
}
printHtmlPart(17)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1403014810000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
