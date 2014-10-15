import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_environmentEnvironmentDetails_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/environment/EnvironmentDetails.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
printHtmlPart(1)
invokeTag('javascript','g',13,['src':("application.js")],-1)
printHtmlPart(2)
invokeTag('javascript','g',14,['src':("environment.js")],-1)
printHtmlPart(3)
expressionOut.print(resource(dir: 'css', file: 'environment.css'))
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
invokeTag('select','g',63,['id':("countryname"),'name':("country"),'value':(countries),'noSelection':(['null':'Select Countries (press ctrl to select multiple)...']),'from':(countries)],-1)
printHtmlPart(6)
})
invokeTag('userenvironmentunknown','g',74,[:],1)
printHtmlPart(7)
createTagBody(1, {->
printHtmlPart(8)
loop:{
int i = 0
for( user in (users) ) {
printHtmlPart(9)
expressionOut.print(user.firstname)
printHtmlPart(10)
expressionOut.print(user.lastname)
printHtmlPart(10)
expressionOut.print(user.department)
printHtmlPart(10)
expressionOut.print(ipaddress1.get(i))
printHtmlPart(10)
expressionOut.print(ipaddress2.get(i))
printHtmlPart(10)
expressionOut.print(ipaddress3.get(i))
printHtmlPart(11)
i++
}
}
printHtmlPart(12)
loop:{
int i = 0
for( dmz in (dmzaddress) ) {
printHtmlPart(9)
expressionOut.print(i+1)
printHtmlPart(10)
expressionOut.print(dmz.dmzaddress)
printHtmlPart(13)
expressionOut.print(dmz.id)
printHtmlPart(14)
expressionOut.print(dmz.dmzhostname)
printHtmlPart(15)
expressionOut.print(dmz.id)
printHtmlPart(16)
i++
}
}
printHtmlPart(17)
loop:{
int i = 0
for( counUser in (countryByUser) ) {
printHtmlPart(9)
expressionOut.print(i+1)
printHtmlPart(10)
expressionOut.print(counUser.country)
printHtmlPart(11)
i++
}
}
printHtmlPart(18)
})
invokeTag('userenvironmentknown','g',177,[:],1)
printHtmlPart(19)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1413140090000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
