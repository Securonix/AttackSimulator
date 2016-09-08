import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_inputRequestInputRequestParameters_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/inputRequest/InputRequestParameters.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
printHtmlPart(1)
invokeTag('javascript','g',14,['src':("parameters.js")],-1)
printHtmlPart(2)
invokeTag('javascript','g',15,['src':("threadManage.js")],-1)
printHtmlPart(3)
loop:{
int i = 0
for( order in (orders) ) {
printHtmlPart(4)
expressionOut.print(order.id)
printHtmlPart(5)
expressionOut.print(order.feedtype)
printHtmlPart(6)
expressionOut.print(order.startdate)
printHtmlPart(7)
expressionOut.print(order.enddate)
printHtmlPart(8)
expressionOut.print(order.frequency)
printHtmlPart(9)
expressionOut.print(order.destinationip)
printHtmlPart(10)
expressionOut.print(order.destinationport)
printHtmlPart(11)
expressionOut.print(order.id)
printHtmlPart(12)
expressionOut.print(order.id)
printHtmlPart(13)
expressionOut.print(order.id)
printHtmlPart(14)
expressionOut.print((order.threadid == -1)?'#FF0000':'#00FF00')
printHtmlPart(15)
i++
}
}
printHtmlPart(16)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1470811590000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
