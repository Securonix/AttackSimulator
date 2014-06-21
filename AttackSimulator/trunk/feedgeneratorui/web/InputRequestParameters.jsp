<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>Securonix :: Feed Generator System</title>
    <link href="css/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <link href="css/jquery.ui.datepicker.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="scripts/jquery.min.js"></script>
    <script type="text/javascript" src="scripts/jquery-ui-1.10.4.custom.min.js"></script>
    <script type="text/javascript" src="scripts/jquery.ui.core.min.js"></script>
    <script type="text/javascript" src="scripts/jquery.ui.widget.min.js"></script>
    <script type="text/javascript" src="scripts/jquery.ui.datepicker.min.js"></script>
    <script type="text/javascript" src="scripts/parameters.js"></script>
    <script type="text/javascript" src="./scripts/md5.js"></script>
    <link rel="stylesheet" href="./styles/main.css" media="screen">
    <link rel="stylesheet" href="./styles/colors.css" media="screen">
    <script type="text/javascript">
        $(function() {
            $( "#from" ).datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 3,
                onClose: function( selectedDate ) {
                    $( "#to" ).datepicker( "option", "minDate", selectedDate );
                }
            });
            
            $( "#to" ).datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 3,
                onClose: function( selectedDate ) {
                    $( "#from" ).datepicker( "option", "maxDate", selectedDate );
                }
            });

            function getCookie(cname){
		var name = cname + "=";
		var ca = document.cookie.split(';');
		for(var i=0; i<ca.length; i++) {
			var c = ca[i].trim();
			if (c.indexOf(name)==0) return c.substring(name.length,c.length);
		}
		return null;
	}

            user = getCookie("user");
            $("#userinfo").html("Welcome " + user + " <a id=\"logout\" style=\"cursor: pointer\" href=\"LogoutServlet\">logout</a>");
        });
    </script>
</head>
<body>
      <%
           String userName = null;
           Cookie[] cookies = request.getCookies();
           if(cookies !=null){
              for(Cookie cookie : cookies){
                 if(cookie.getName().equals("user")) userName = cookie.getValue();
              }
           }
           if(userName == null) response.sendRedirect("index.jsp");
      %>

  <div id="container">
      <div id="header">
          <a id="logo" href="index.jsp" title="Support Center"><img src="images/SecuronixLogo.jpeg" width="250" border=0 alt="Support Center" style="padding-top:5px; padding-left: 10px;"></a>
          <p><span>TRIAL FEED </span> REQUEST</p>
          <p id="userinfo"></p>
      </div>
      <div id="content">
  <div id="index">

  <h1>Welcome to the Securonix feed generator system</h1>

  <p class="big">In order to request trial feed from Securonix, please fill out the form below.</p>

  <hr />

  <br />
        <form id="form_804346" class="appnitro"  method="post" action="">	
            <ul style="list-style: none; width: 700px; float: left;">
              <li style="width: 150px; float: left">Feed Type: </li>
              <li id="li_8" style="width: 450px; float: left; clear: right">
                  <input type="checkbox" name="feedtype" value="Vontu" style="width: 10px"/><p>Vontu</p>
                  <input type="checkbox" name="feedtype" value="Sharepoint" style="width: 10px"/><p>Sharepoint</p>
                  <input type="checkbox" name="feedtype" value="Proxy" style="width: 10px"/><p>Proxy</p>
                  <input type="checkbox" name="feedtype" value="Windows" style="width: 10px"/><p>Windows</p>
              </li>
              <li style="width: 150px; float: left;">Months: </li>
                <li style="width: 550px; float: left;">
                  <ul style="list-style: none; float: left; width: 500px; padding: 0px">
                    <li style="width: 200px; float: left"><input id="from" name="element_5" class="hasDatePicker" type="text"  value=""/></li> <li style="float: left;">to</li> 
                    <li style="width: 200px; float: left; clear: right; padding-left: 20px"><input id="to" name="element_9" class="hasDatePicker" type="text" value=""/></li>
                  </ul>
                </li>
              </li>
              <li style="width: 150px; float: left;">Frequency (in millisec): </li>
              <li id="li_9" style="width: 450px; float: left;" >
                <input id="element_9" name="element_9" class="element text medium" type="text" maxlength="255" value=""/>
              </li>
              <li style="width: 150px; float: left;">Destination IP: </li>
              <li id="li_6" style="width: 450px; float: left;">
                <input id="element_6" name="element_6" class="element text medium" type="text" maxlength="255" value=""/> 
              </li>
              <li style="width: 150px; float: left;">Destination Port: </li>
              <li id="li_7" style="width: 550px; float: left;">
                <input id="element_7" name="element_7" class="element text medium" type="text" maxlength="255" value=""/> 
              </li>
              <li class="buttons" style="witdh: 600px; float: left">
                <input type="hidden" name="form_id" value="804346" />
                <button id="saveForm" class="button_text" type="submit" >Submit</button>
              </li>
            </ul>
        </form>	

  <div class="clear"></div>
  </div>

   <div style="clear:both; text-align: center; color: #fe7700; text-decoration: bold; font-size: 15px;" id="errormessages"></div> 
   </div>
   <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
   <div align="center">
       <a id="powered_by" href="http://securonix.com"><img src="./images/osticket-securonixonly.gif" width="126" height="23" alt="Powered by osTicket"></a></div>
  </div>
      
      <div id="loading" style="position: absolute; width: 960px; top: 200px; left: 250px"><img src="images/loading.gif" width="100px"/></div>
</body>
</html>
