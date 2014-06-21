<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>Securonix :: Feed Generator System Admin Page</title>
    <script type="text/javascript" src="./scripts/jquery.min.js"></script>
    <script type="text/javascript" src="./scripts/md5.js"></script>
    <script type="text/javascript" src="./scripts/admin.js"></script>
    <link rel="stylesheet" href="./styles/main.css" media="screen">
    <link rel="stylesheet" href="./styles/colors.css" media="screen">
</head>
<body>
  <div id="container">
      <div id="header">
          <a id="logo" href="index.jsp" title="Support Center"><img src="images/SecuronixLogo.jpeg" width="250" border=0 alt="Support Center" style="padding-top:5px; padding-left: 10px;"></a>
          <p><span>TRIAL FEED </span> REQUEST</p>
      </div>
      <div id="content">
  <div id="index">

  <h1>Welcome to the Securonix feed generator system admin page.</h1>

  <p class="big">Please sign in to approve client feed requests.</p>

  <hr />

  <br />

  <div id="datafeed">

    <img src="./images/ticket_status_icon.jpg" width="48" height="48" align="left" style="">
    <h3>Please sign in</h3>
    <div id="login" style="float: left;">
      <ul style="list-style: none; width: 350px; float: left;">
        <li style="width: 150px; float: left;">Admin Username: </li><li style="width: 200px; float: left; clear: right;"><input id="username" type="text"/></li>
        <li style="width: 150px; float: left;">Password: </li><li style="width: 200px; float: left; clear: right;"><input id="password" type="password"/></li>
      </ul>
      <div style=""><button style="margin-left: 74px; width: 120px" onclick="onButtonClick()">Submit</button></div>
    </div>
  </div>

  <div class="clear"></div>
  </div>

  <div id="messages"></div>

   <div style="clear:both; text-align: center; color: red; text-decoration: bold; font-size: 15px;" id="message"></div> 
   </div>
   <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
   <div align="center">
       <a id="powered_by" href="http://securonix.com"><img src="./images/osticket-securonixonly.gif" width="126" height="23" alt="Powered by osTicket"></a></div>
  </div>
</body>
</html>
