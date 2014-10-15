import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_attackattack_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/attack/attack.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
printHtmlPart(1)
invokeTag('javascript','g',13,['src':("application.js")],-1)
printHtmlPart(2)
invokeTag('javascript','g',14,['src':("attack.js")],-1)
printHtmlPart(3)
expressionOut.print(resource(dir: 'css', file: 'environment.css'))
printHtmlPart(4)
expressionOut.print(resource(dir: 'css', file: 'attack.css'))
printHtmlPart(5)
expressionOut.print(request.contextPath)
printHtmlPart(6)
expressionOut.print(user)
printHtmlPart(7)
for( item in (orders) ) {
printHtmlPart(8)
expressionOut.print(item.id)
printHtmlPart(9)
expressionOut.print(item.id)
printHtmlPart(10)
expressionOut.print(item.feedtype)
printHtmlPart(11)
expressionOut.print(item.feedtype)
printHtmlPart(12)
expressionOut.print(item.id)
printHtmlPart(13)
}
printHtmlPart(14)
expressionOut.print(resource(dir: 'images', file: 'close.png'))
printHtmlPart(15)
expressionOut.print(resource(dir: 'images', file: 'right.png'))
printHtmlPart(16)
expressionOut.print(request.contextPath)
printHtmlPart(17)
expressionOut.print(request.contextPath)
printHtmlPart(18)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1412009348000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
