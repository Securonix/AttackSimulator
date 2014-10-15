import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_attackOrderAttackOrder_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/attackOrder/AttackOrder.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
printHtmlPart(1)
expressionOut.print(resource(dir: 'css', file: 'attackorders.css'))
printHtmlPart(2)
invokeTag('javascript','g',15,['src':("attackorders.js")],-1)
printHtmlPart(3)
loop:{
int i = 0
for( attackorder in (attackorders) ) {
printHtmlPart(4)
expressionOut.print(i+1)
printHtmlPart(5)
expressionOut.print(attackorder.key)
printHtmlPart(6)
expressionOut.print(i+1)
printHtmlPart(7)
for( value in (attackorder.value) ) {
printHtmlPart(8)
expressionOut.print(value.attackid)
printHtmlPart(9)
expressionOut.print(value.transactionFilePath)
printHtmlPart(5)
expressionOut.print(value.description)
printHtmlPart(10)
}
printHtmlPart(11)
expressionOut.print(i+1)
printHtmlPart(12)
expressionOut.print(i+1)
printHtmlPart(13)
expressionOut.print(i+1)
printHtmlPart(14)
expressionOut.print(i+1)
printHtmlPart(7)
for( value in (listofusers) ) {
printHtmlPart(8)
expressionOut.print(value.userid)
printHtmlPart(15)
expressionOut.print(value.account1)
printHtmlPart(5)
expressionOut.print(value.firstname)
printHtmlPart(16)
expressionOut.print(value.lastname)
printHtmlPart(17)
}
printHtmlPart(18)
expressionOut.print(i+1)
printHtmlPart(19)
i++
}
}
printHtmlPart(20)
createTagBody(1, {->
printHtmlPart(21)
loop:{
int i = 0
for( userattack in (userattacks) ) {
printHtmlPart(22)
expressionOut.print(userattack.username)
printHtmlPart(23)
expressionOut.print(userattack.dayofattack)
printHtmlPart(23)
expressionOut.print(userattack.timeofattack)
printHtmlPart(23)
expressionOut.print(userattack.frequency)
printHtmlPart(24)
expressionOut.print(userattack.destinationip)
printHtmlPart(25)
expressionOut.print(userattack.destinationport)
printHtmlPart(26)
i++
}
}
printHtmlPart(27)
})
invokeTag('hasattackorders','g',123,[:],1)
printHtmlPart(28)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1413318874000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
