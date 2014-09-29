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
        <script type="text/javascript" src="<g:resource dir='js' file='validation.js' />"></script>
        <script type="text/javascript" src="<g:resource dir='js' file='jquery.validate.js' />"></script>
        <script type="text/javascript" src="<g:resource dir='js' file='validation_customRules.js' />"></script>
        <script>
            //Window resize handler
            jQuery(window).resize(adjustLayout);

            jQuery(document).ready(function() {
            validatePasswordForm("loginchangePassword");
            jQuery( "input:submit, input:button", "#loginchangePassword" ).button();
            //display login details
            jQuery('#display-login-details').click(function() {
            jQuery( "#login-details-wrapper" ).slideDown( "slow");
            });
            //hide login details
            jQuery('#details-close').click(function() {
            jQuery( "#login-details-wrapper" ).slideUp( "slow");
            });

            adjustLayout();
            });

            //Function to adjust layout according to screen size
            function adjustLayout() {
            var containerLt  = (jQuery(window).width() - jQuery('#container').width()) / 2;
            var containerTp  = (jQuery(window).height() - jQuery('#container').height()) / 2;
            var containerCss = {
            'position': 'absolute',
            'left': containerLt,
            'top': containerTp - 40
            };

            //Set container left
            if (containerLt < 0)
            containerCss.left = 0;

            //Set container top
            if (containerTp < 0)
            containerCss.top  = 0;

            //Apply css to container
            jQuery('#container').css(containerCss);
            }
        </script>
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
            <div id="logo">
                <img src="${request.contextPath}/images/lg_images/logo_high_res.png" alt="" style="float:left;" />
                <span class="product-header">Attack Simulator</span>
                <div style="clear: both;"></div>
            </div>
            <div id="login-box" style="height:510px;">
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
                                <a href="#" id="display-login-details"></a> &nbsp;&nbsp; | &nbsp;&nbsp;
                                <a href="">
                                    <a href=""></a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="footer-wrapper">
                <span style="color:#434343">Footer text</span>
            </div>
        </div>
    </body>
</html>