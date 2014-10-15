import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_attack_sequenceattack_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/attack/_sequenceattack.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
loop:{
int i = 0
for( atkdef in (attackdefinition) ) {
printHtmlPart(0)
expressionOut.print(atkdef.orderid)
printHtmlPart(1)
expressionOut.print(atkdef.feedtype)
printHtmlPart(2)
expressionOut.print(atkdef.orderid)
printHtmlPart(3)
expressionOut.print(attackelements[i])
printHtmlPart(4)
i++
}
}
printHtmlPart(5)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1411078875000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
