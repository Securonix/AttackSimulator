<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}"/>
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main_1.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.ui.datepicker.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.sidr.dark.css')}" type="text/css"/>
    <g:javascript library="application"/>
    <g:javascript library="jquery" />
    <g:javascript library="jquery-ui" />
      <r:layoutResources />
  <g:layoutHead/>
    <g:javascript src="maingrails.js" />
    <g:javascript src="jquery.sidr.min.js" />
</head>
<body>
  <!--div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div-->
    <g:userloggedin>
      <div style="float: left; position: relative; top:0; left:0; margin:0;">
        <a id="simple-menu" href="#sidr" style="float:left; width: 50px"><img width="50px" src="${resource(dir: 'images', file: 'right.png')}"/></a>
      </div>
      <div id="sidr">
        <!-- Your content -->
        <ul>
          <li></li>
          <li><a href="/AttackSimulator/Environment">My Environment</a></li>
          <li><g:userenvironmentknown><a href="/AttackSimulator/inputRequest/inputRequest"></g:userenvironmentknown>Place Orders<g:userenvironmentknown></a></g:userenvironmentknown></li>
          <li><g:userhasorders><a href="/AttackSimulator/ThreadManage"></g:userhasorders>My Orders<g:userhasorders></a></g:userhasorders></li>
        </ul>
      </div>
    </g:userloggedin>
      <g:layoutBody/>
      
  <div class="footer" role="contentinfo"></div>
  <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
  <r:layoutResources />
</body>
</html>
