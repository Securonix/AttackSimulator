<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <meta name="layout" content="main" />
        <title>Securonix :: Feed Generator System</title>
        <g:javascript src="md5.js"/>
        <g:javascript src="maingrails.js"/>
        <script type="text/javascript">
            function submitLogin(){
                var password = document.bannerlogin.password.value;
                var passhash = md5(password);
                document.bannerlogin.password.value = passhash;
                document.bannerlogin.submit();
            }
        </script>    
    </head>
    <body>
        <div id="container">
      <div id="header">
          <a id="logo" href="/AttackSimulator/" title="AttackSimulator"><img src="${request.contextPath}/images/SecuronixLogo.jpeg" width="250" border=0 alt="Support Center" style="padding-top:5px; padding-left: 10px;"></a>
          <p><span>TRIAL FEED </span> REQUEST</p>
      </div>
      <div id="content">
  <div id="index">

  <h1>Welcome to the Securonix feed generator system</h1>

  <p class="big">In order to request trial feed from Securonix, please register yourself. If you have already registered, please sign in.</p>

  <hr />

  <br />

  <div class="lcol">

    <img src="${request.contextPath}/images/new_ticket_icon.jpg" width="48" height="48" align="left"> 

    <h3>Register</h3>
    Please provide your details for registration.
    <ul style="list-style: none; width: 500px; float: left;">
      <li style="width: 150px; float: left;">Name: </li><li style="width: 350px; float: left;"><input type="text" id="register-name" required/></li>
      <li style="width: 150px; float: left;">Company </li><li style="width: 350px; float: left;"><input type="text" id="register-company" required/></li>
      <li style="width: 150px; float: left;">Work Email: </li><li style="width: 350px; float: left;"><input type="text" id="register-email" required/></li>
      <li style="width: 150px; float: left;">Confirm Email: </li><li style="width: 350px; float: left;"><input type="text" id="register-confirm-email" required/></li>
      <li style="width: 150px; float: left;">Contact Number: </li><li style="width: 350px; float: left;"><input type="text" id="register-contact" required/></li>
      <li style="width: 150px; float: left;">Username: </li><li style="width: 350px; float: left;"><input type="text" id="register-username" required/></li>
      <li style="width: 150px; float: left;">Desired Password: </li><li style="width: 350px; float: left;"><input type="password" id="register-password" required/></li>
      <li style="width: 150px; float: left;">Confirm Password: </li><li style="width: 350px; float: left;"><input type="password" id="register-confirm-password" required/></li>
    </ul>
      <div style="padding-left: 120px; clear: both;"><button id="register-button" style="width: 140px">Register</button></div>
  </div>

  <div class="rcol">

    <img src="${request.contextPath}/images/ticket_status_icon.jpg" width="48" height="48" align="left" style="">
    <h3>Please sign in</h3>
    You can specify properties for your feed.
    <div id="login" style="float: left;">
        <form name="bannerlogin" method="POST" action="${postUrl}">
      <ul style="list-style: none; width: 300px; float: left;">
        <li style="width: 70px; float: left;">Username: </li><li style="width: 200px; float: left; clear: right;"><input name="j_username" id="username" type="text"/></li>
        <li style="width: 70px; float: left;">Password: </li><li style="width: 200px; float: left; clear: right;"><input name="j_password" id="password" type="password"/></li>
      </ul>
      <div style="float: left; margin-left: 70px"><g:submitButton name="banner-login-button" value="Log In" id="loginregisterbutton"/></div>
        </form>
    </div>
  </div>

  <div class="clear"></div>
  </div>

   <div style="clear:both; text-align: center; color: red; text-decoration: bold; font-size: 15px;" id="message"></div> 
   </div>
   <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
   <div align="center">
       <a id="powered_by" href="http://securonix.com"><img src="${request.contextPath}/images/osticket-securonixonly.gif" width="126" height="23" alt="Powered by osTicket"></a></div>
  </div>
</body>
</html>