import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_loginRegisterLoginRegisterSecuronix_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/loginRegister/LoginRegisterSecuronix.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
printHtmlPart(1)
expressionOut.print(request.contextPath)
printHtmlPart(2)
invokeTag('resource','g',13,['dir':("css"),'file':("framework_lte_ie9.css")],-1)
printHtmlPart(3)
expressionOut.print(resource(dir: 'css', file: 'new_framework.css'))
printHtmlPart(4)
expressionOut.print(resource(dir: 'css', file: 'nprogress.css'))
printHtmlPart(4)
expressionOut.print(resource(dir: 'css', file: 'jquery-ui.css'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'js', file: 'jquery.min.js'))
printHtmlPart(6)
expressionOut.print(resource(dir: 'js', file: 'jquery-ui-1.10.4.custom.min.js'))
printHtmlPart(6)
expressionOut.print(resource(dir: 'js', file: 'nprogress.js'))
printHtmlPart(6)
expressionOut.print(resource(dir: 'js', file: 'maingrails.js'))
printHtmlPart(6)
expressionOut.print(resource(dir: 'js', file: 'registration.js'))
printHtmlPart(7)
expressionOut.print(request.contextPath)
printHtmlPart(8)
expressionOut.print(postUrl)
printHtmlPart(9)
expressionOut.print(targetUri)
printHtmlPart(10)
expressionOut.print(g.message(code:'login.auth.firstTime.title'))
printHtmlPart(11)
expressionOut.print(g.message(code:'login.auth.firstTime.subTitle'))
printHtmlPart(12)
expressionOut.print(g.message(code:'login.auth.firstTime.username'))
printHtmlPart(13)
expressionOut.print(username)
printHtmlPart(14)
expressionOut.print(g.message(code:'login.auth.firstTime.password'))
printHtmlPart(13)
expressionOut.print(password)
printHtmlPart(15)
expressionOut.print(username)
printHtmlPart(16)
expressionOut.print(g.message(code:'login.auth.firstTime.password'))
printHtmlPart(17)
expressionOut.print(password)
printHtmlPart(18)
if(true && (flash.message)) {
printHtmlPart(19)
expressionOut.print(flash.message)
printHtmlPart(20)
}
printHtmlPart(21)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1412885837000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
