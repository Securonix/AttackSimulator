<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name="layout" content="main" />
        <link type="text/css" href="${request.contextPath}/css/login.css" rel="stylesheet" />
        <!--[if lte IE 9]>  
            <link href="<g:resource dir='css' file='framework_lte_ie9.css' />" rel="stylesheet" type="text/css">
        <![endif]-->
        <script type="text/javascript" src="<g:resource dir='js' file='maingrails.js'/>"></script>
        <script type="text/javascript" src="<g:resource dir='js' file='registration.js' />"></script>
        
        <title>Login </title>
        <meta name="generator" content="editplus" />
        <meta name="author" content="Securonix Solutions" />
        <meta name="keywords" content="Attack Simulator" />
        <meta name="description" content="This is log feed simulator and can be used to model log feeds when a particular attack happens on company network" />
    </head>
    <body onLoad="document.forms.loginForm.username.focus();">
        <br/>
        <br/>
        <br/>
        <div id="container">
            <div id="logo-main">
                <img src="${request.contextPath}/images/lg_images/logo_high_res.png" alt="" style="float:left;" />
                <span class="product-header">Attack Simulator</span>
                <div style="clear: both;"></div>
            </div>
            <div id="login-box" style="height:150px;">
                <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                    <input type="hidden" value="${targetUri}" name="spring-security-redirect"/>
                    <table width="230px" class="login-table">
                        <tr>
                            <td>
                                <div class="field-wrapper">
                                    <input type="text" id="username" name="j_username" size="45" height="22"/>
                                </div>
                            </td>
                            <td>
                                <div class="field-wrapper">
                                    <input type='password' name='j_password' id='password'  size="45" height="22" />
                                </div>
                            </td>
                            <td>
                                <input type="submit" value="Sign in" class="login-button" />
                            </td>
                        </tr>

                        <tr>
                            <td colspan="3">
                            </td>
                        </tr>

                        <tr>
                            <td colspan="3" id="first-time-login">
                                <div id="login-details-wrapper" style="display: none;text-align: left;">
                                    <span id="details-close" style="position:relative;">x</span>
                                    <span id="first-time-title">${g.message(code:'login.auth.firstTime.title')}</span>
                                    <span id="first-time-sub-title">${g.message(code:'login.auth.firstTime.subTitle')}</span>
                                    <table id="first-time-table">
                                        <tr>
                                            <td>
                                                <div style="width: 500px; float: left;">
                                                    <span style="width: 90px; float: left;">${g.message(code:'login.auth.firstTime.username')}</span>
                                                    <span style="width: 200px; float: left;"> : ${username}</span>
                                                </div>
                                                <div style="width: 500px; float: left;">
                                                    <span style="width: 90px; float: left;"> ${g.message(code:'login.auth.firstTime.password')}</span>
                                                    <span style="width: 200px; float: left;"> : ${password}</span>
                                                </div>
                                            </td>
                                            <!--<td>: ${username}</td>-->
                                        </tr>
                <!--                        <tr>
                                            <td><div style="width: 90px;">${g.message(code:'login.auth.firstTime.password')}</div></td>
                                            <td>: ${password}</td>
                                        </tr>-->
                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td id="lost-password" colspan="3" style="text-align: left;">
                                <a href="#register" id="registerfunction">First time user? Register</a> &nbsp;&nbsp; | &nbsp;&nbsp;
                                <a href="">Lost Password? Request reset</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="messages" style="float:left; color: red; font-weight: bold">
                <g:if test="${flash.message}">
                    ${flash.message}
                  </g:if>
            </div>
            <div id="information" style="margin-top: 50px; min-height: 200px; float:left"><p style="font-weight: bold">Information:</p>
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sit amet ipsum eleifend, feugiat elit sit amet, posuere ex. Etiam vel vulputate leo. Aenean mauris sapien, pretium ac feugiat vitae, pellentesque eu mi. Integer eu pulvinar justo. Nam nibh sapien, dictum id aliquet ac, blandit a ante. Morbi tristique euismod dictum. Aliquam dignissim vulputate turpis, et cursus nisl bibendum in. Morbi eleifend elit a blandit faucibus. Pellentesque dignissim sagittis turpis. Donec auctor semper scelerisque.
            </div>
            <div id="register" class="modalDialog">
                <img id="close-modal" src="${request.contextPath}/images/close.png" style="float:right; width: 50px; height: 50px"/>
                <ul style="list-style: none; width: 500px; float: left;">
                    <li style="width: 150px; float: left;">Name: </li><li style="width: 350px; float: left;"><input type="text" id="register-name" required/></li>
                    <li style="width: 150px; float: left;">Username: </li><li style="width: 350px; float: left;"><input type="text" id="register-username" required/></li>
                    <li style="width: 150px; float: left;">Desired Password: </li><li style="width: 350px; float: left;"><input type="password" id="register-password" required/></li>
                    <li style="width: 150px; float: left;">Confirm Password: </li><li style="width: 350px; float: left;"><input type="password" id="register-confirm-password" required/></li>
                    <li style="width: 150px; float: left;">Work Email: </li><li style="width: 350px; float: left;"><input type="text" id="register-email" required/></li>
                    <li style="width: 150px; float: left;">Confirm Email: </li><li style="width: 350px; float: left;"><input type="text" id="register-confirm-email" required/></li>
                    <li style="width: 150px; float: left;">Is this for Business use: </li><li style="width: 350px; float: left;"><input type="checkbox" id="businessuse" required/></li>
                </ul>
                <div style="padding-left: 120px; clear: both;"><button id="register-button" style="width: 140px">Register</button></div>
            </div>
            <div class="footer-wrapper">
                <span style="color:#434343"></span>
            </div>
        </div>
    </body>
</html>