import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulatorerror_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/error.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
if((grails.util.Environment.current.name == 'development') && true) {
printHtmlPart(1)
}
else {
printHtmlPart(2)
}
printHtmlPart(3)
if((grails.util.Environment.current.name == 'development') && true) {
printHtmlPart(4)
expressionOut.print(resource(dir: 'css', file: 'errors.css'))
printHtmlPart(5)
}
printHtmlPart(6)
if((grails.util.Environment.current.name == 'development') && true) {
printHtmlPart(7)
invokeTag('renderException','g',10,['exception':(exception)],-1)
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1403021054000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
