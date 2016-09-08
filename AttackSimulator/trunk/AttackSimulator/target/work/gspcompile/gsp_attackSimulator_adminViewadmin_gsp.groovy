import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_adminViewadmin_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/adminView/admin.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
printHtmlPart(1)
expressionOut.print(resource(dir: 'css', file: 'attackorders.css'))
printHtmlPart(2)
invokeTag('javascript','g',14,['src':("admingrails.js")],-1)
printHtmlPart(3)
loop:{
int i = 0
for( user in (usersunappr) ) {
printHtmlPart(4)
expressionOut.print(user.username)
printHtmlPart(5)
expressionOut.print(user.workemail)
printHtmlPart(6)
expressionOut.print(i)
printHtmlPart(7)
expressionOut.print(user.id)
printHtmlPart(8)
expressionOut.print(i)
printHtmlPart(7)
expressionOut.print(user.id)
printHtmlPart(9)
expressionOut.print(i)
printHtmlPart(7)
expressionOut.print(user.id)
printHtmlPart(10)
expressionOut.print(i)
printHtmlPart(7)
expressionOut.print(user.id)
printHtmlPart(11)
i++
}
}
printHtmlPart(12)
loop:{
int i = 0
for( user in (usersappr) ) {
printHtmlPart(4)
expressionOut.print(user.username)
printHtmlPart(5)
expressionOut.print(user.workemail)
printHtmlPart(13)
expressionOut.print(i)
printHtmlPart(7)
expressionOut.print(user.id)
printHtmlPart(14)
expressionOut.print(i)
printHtmlPart(7)
expressionOut.print(user.id)
printHtmlPart(15)
expressionOut.print(i)
printHtmlPart(7)
expressionOut.print(user.id)
printHtmlPart(16)
expressionOut.print(i)
printHtmlPart(7)
expressionOut.print(user.id)
printHtmlPart(11)
i++
}
}
printHtmlPart(17)
loop:{
int i = 0
for( order in (orders) ) {
printHtmlPart(18)
expressionOut.print(order?.id)
printHtmlPart(19)
expressionOut.print((i % 2) == 0 ? 'odd' : 'even')
printHtmlPart(20)
expressionOut.print(order?.feedtype)
printHtmlPart(21)
expressionOut.print(order?.frequency)
printHtmlPart(21)
expressionOut.print(order?.startdate)
printHtmlPart(21)
expressionOut.print(order?.enddate)
printHtmlPart(21)
expressionOut.print(order?.destinationip)
printHtmlPart(21)
expressionOut.print(order?.destinationport)
printHtmlPart(22)
expressionOut.print(order?.id)
printHtmlPart(23)
expressionOut.print(order?.id)
printHtmlPart(24)
expressionOut.print(order?.id)
printHtmlPart(25)
expressionOut.print(order?.id)
printHtmlPart(23)
expressionOut.print(order?.id)
printHtmlPart(26)
expressionOut.print(order?.id)
printHtmlPart(27)
i++
}
}
printHtmlPart(28)
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
