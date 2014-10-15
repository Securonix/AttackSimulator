import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_loginauth_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/login/auth.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
invokeTag('message','g',4,['code':("springSecurity.login.title")],-1)
printHtmlPart(1)
invokeTag('message','g',90,['code':("springSecurity.login.header")],-1)
printHtmlPart(2)
if(true && (flash.message)) {
printHtmlPart(3)
expressionOut.print(flash.message)
printHtmlPart(4)
}
printHtmlPart(5)
expressionOut.print(postUrl)
printHtmlPart(6)
invokeTag('message','g',98,['code':("springSecurity.login.username.label")],-1)
printHtmlPart(7)
invokeTag('message','g',103,['code':("springSecurity.login.password.label")],-1)
printHtmlPart(8)
expressionOut.print(rememberMeParameter)
printHtmlPart(9)
if(true && (hasCookie)) {
printHtmlPart(10)
}
printHtmlPart(11)
invokeTag('message','g',109,['code':("springSecurity.login.remember.me.label")],-1)
printHtmlPart(12)
expressionOut.print(message(code: "springSecurity.login.button"))
printHtmlPart(13)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1408561200000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
