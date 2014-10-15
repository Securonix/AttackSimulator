import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_attackSimulator_layoutsmain_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/main.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
printHtmlPart(0)
invokeTag('layoutTitle','g',10,['default':("Grails")],-1)
printHtmlPart(1)
expressionOut.print(resource(dir: 'images', file: 'favicon.ico'))
printHtmlPart(2)
expressionOut.print(resource(dir: 'images', file: 'apple-touch-icon.png'))
printHtmlPart(3)
expressionOut.print(resource(dir: 'images', file: 'apple-touch-icon-retina.png'))
printHtmlPart(4)
expressionOut.print(resource(dir: 'css', file: 'main_1.css'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'css', file: 'jquery-ui.css'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'css', file: 'jquery.ui.datepicker.css'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'css', file: 'mobile.css'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'css', file: 'jquery.sidr.dark.css'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'css', file: 'new_framework.css'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'css', file: 'nprogress.css'))
printHtmlPart(5)
expressionOut.print(resource(dir: 'css', file: 'jquery.ui.timepicker.css'))
printHtmlPart(6)
invokeTag('javascript','g',23,['library':("jquery")],-1)
printHtmlPart(7)
invokeTag('javascript','g',24,['library':("jquery-ui")],-1)
printHtmlPart(8)
invokeTag('layoutResources','r',25,[:],-1)
printHtmlPart(8)
invokeTag('layoutHead','g',26,[:],-1)
printHtmlPart(8)
invokeTag('javascript','g',27,['src':("nprogress.js")],-1)
printHtmlPart(8)
invokeTag('javascript','g',28,['src':("maingrails.js")],-1)
printHtmlPart(8)
invokeTag('javascript','g',29,['src':("jquery.sidr.min.js")],-1)
printHtmlPart(8)
invokeTag('javascript','g',30,['src':("jquery.ui.timepicker.js")],-1)
printHtmlPart(9)
expressionOut.print(resource(dir: 'images', file: 'grails_logo.png'))
printHtmlPart(10)
createTagBody(1, {->
printHtmlPart(7)
invokeTag('set','g',35,['var':("user"),'value':(sec.username())],-1)
printHtmlPart(11)
expressionOut.print(user)
printHtmlPart(12)
invokeTag('currentUrl','g',142,[:],-1)
printHtmlPart(13)
createClosureForHtmlPart(14, 2)
invokeTag('userhasorders','g',163,[:],2)
printHtmlPart(15)
createClosureForHtmlPart(16, 2)
invokeTag('userhasorders','g',163,[:],2)
printHtmlPart(17)
createClosureForHtmlPart(18, 2)
invokeTag('userenvironmentknown','g',164,[:],2)
printHtmlPart(19)
createClosureForHtmlPart(16, 2)
invokeTag('userenvironmentknown','g',164,[:],2)
printHtmlPart(20)
createClosureForHtmlPart(21, 2)
invokeTag('userhasorders','g',165,[:],2)
printHtmlPart(22)
createClosureForHtmlPart(16, 2)
invokeTag('userhasorders','g',165,[:],2)
printHtmlPart(23)
invokeTag('layoutBody','g',200,[:],-1)
printHtmlPart(24)
})
invokeTag('userloggedin','g',205,[:],1)
printHtmlPart(25)
invokeTag('layoutResources','r',209,[:],-1)
printHtmlPart(26)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1413318737000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
