import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_attack_attacktemplate_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/attack/_attacktemplate.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
expressionOut.print(feedconfig.feedtype)
printHtmlPart(1)
expressionOut.print(orderid)
printHtmlPart(2)
expressionOut.print(feedtype)
printHtmlPart(3)
expressionOut.print(feedconfigid)
printHtmlPart(4)
for( ths in (tableHeaders) ) {
printHtmlPart(5)
expressionOut.print(ths)
printHtmlPart(6)
}
printHtmlPart(7)
loop:{
int i = 0
for( ths in (tableHeaders) ) {
printHtmlPart(8)
expressionOut.print(orderid)
printHtmlPart(9)
expressionOut.print(i+1)
printHtmlPart(10)
i++
}
}
printHtmlPart(11)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1411014686000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
