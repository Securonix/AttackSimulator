import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_uielements_orderlist_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/uielements/_orderlist.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
loop:{
int i = 0
for( feedtype in (feeds) ) {
printHtmlPart(1)
expressionOut.print(feedtype.feedtype)
printHtmlPart(2)
expressionOut.print(i+1)
printHtmlPart(3)
expressionOut.print(feedtype.feedtype)
printHtmlPart(4)
expressionOut.print(i+1)
printHtmlPart(5)
expressionOut.print(i+1)
printHtmlPart(6)
expressionOut.print(i+1)
printHtmlPart(7)
expressionOut.print(i+1)
printHtmlPart(8)
expressionOut.print(i+1)
printHtmlPart(9)
expressionOut.print(i+1)
printHtmlPart(10)
expressionOut.print(i+1)
printHtmlPart(11)
i++
}
}
printHtmlPart(12)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1412625558000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
