<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="layout" content="main" />
        <g:javascript src="application.js" />
        <g:javascript src="attack.js"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'environment.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'attack.css')}" type="text/css">
        <title>Choose your Insider Threat</title>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <img id="logo" src="${request.contextPath}/images/lg_images/logo_high_res.png" width="250" border=0 alt="Support Center" style="padding-top:5px; padding-left: 10px; cursor: pointer;">
                <p><span>TRIAL FEED </span> REQUEST</p>
                <p id="userinfo">Hi "${user}" | <a href="/AttackSimulator/inputRequest/inputRequest">Place Orders</a> | <a href="/AttackSimulator/logout">Logout</a></p>
            <div id="content">
                <div id="index">

                    <h1>Welcome to the Securonix Attack Simulator System</h1>

                    <p class="big" style="float: left; width: 100%; text-align: left">Here we give you the option of choosing a particular kind of attack to be simulated in the logs
                    for your loggers to collect. Securonix will consume these logs and will give actionable items for you to follow up on, to find the </p>
                    <p style="float: left; width: 100%; text-align: left">1. Source of the attack.</p>
                    <p style="float: left; width: 100%; text-align: left">2. The remedial steps needed to ascertain/fix the damage done.</p>
                    <p style="float: left; width: 100%; text-align: left">3. Identification of preventible insider threat based on behavioral changes of a resource.</p>

                    <hr />

                    <br />
                    <div id="form-fields" style="float:left; width: 800px">
                        <form id=payment method="POST" style="float:left">
                            <legend style="width: 100%">Configure the attacker parameter for the feeds that you have setup for yourself</legend>
                            <fieldset>
                                <legend>Choose the feed</legend>
                                <table style="width:100%" border="1">
                                    <thead>
                                        <tr>
                                            <td colspan="2" style="width: 50px"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Feed types you have chosen</strong></td>
                                        </tr>
                                    </thead>
                                    <tbody style="width:100%">
                                        <g:each in="${orders}" var="item">  
                                            <tr>
                                                <td style="width:50px"><input style="width:40px" name ="feedtype" type="radio" id="${item.id}" orderid="${item.id}" feedtype="${item.feedtype}"/></td><td style="width:100%">${item.feedtype} | OrderID: ${item.id}</td>
                                            </tr>
                                        </g:each>
                                    </tbody>
                                </table>
                            </fieldset>
                            
                        </form>
                        <div id="modal-form-encapsulator">
                            <div id="close-modal-form"><img src="${resource(dir: 'images', file: 'close.png')}" width="20px" height="20px" style="float:right"/></div>
                            <div style="padding:30px">
                                <div id="modal-form"></div>
                                <div><button id="modal-submit">Submit</button></div>
                            
                                <div id="ajax-responses"></div>
                            </div>
                    </div>
                    
                    <div id="arrow" style="float:right; margin-right: 180px; margin-top: 80px"><img src="${resource(dir: 'images', file: 'right.png')}" width="200px"/></div>
                    <div class="clear"></div>
                </div>
                <div id="sequenceattack">
                    
                </div>
                <div style="clear:both; text-align: center; color: #fe7700; text-decoration: bold; font-size: 15px;" id="errormessages"></div> 
            </div>
            <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
            <div align="center">
                <a id="powered_by" href="http://securonix.com"><img src=" ${request.contextPath}/images/osticket-securonixonly.gif" width="126" height="23" alt="Powered by osTicket"></a></div>
        </div>

        <div id="loading"><img src=" ${request.contextPath}/images/loading.gif" /></div>
    </body>
</html>
