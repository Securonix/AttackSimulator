<%-- 
    Document   : InputRequestParameters
    Created on : May 16, 2014, 10:53:56 AM
    Author     : securonix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
    <script type="text/javascript" src="scripts/jquery.cookie.js"></script>
    <script type="text/javascript" src="scripts/parameters.js"></script>
    <script type="text/javascript" src="./scripts/md5.js"></script>
    <link rel="stylesheet" href="./styles/main.css" media="screen">
    <link rel="stylesheet" href="./styles/colors.css" media="screen">
    <script type="text/javascript">
        $(function() {
            $(document).tooltip();
            $( ".fromDate" ).datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 3,
                onClose: function( selectedDate ) {
                    $( "#to" ).datepicker( "option", "minDate", selectedDate );
                }
            });
            
            $( ".toDate" ).datepicker({
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
          /*
           String userName = null;
           Cookie[] cookies = request.getCookies();
           if(cookies !=null){
              for(Cookie cookie : cookies){
                 if(cookie.getName().equals("user")) userName = cookie.getValue();
              }
           }
           if(userName == null) response.sendRedirect("index.jsp");
                  */
      %>

  <div id="container">
      <div id="header">
          <a id="logo" href="index.jsp"><img src="images/SecuronixLogo.jpeg" width="250" border=0 alt="Support Center" style="padding-top:5px; padding-left: 10px;"></a>
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
            <ul id="part1" style="list-style: none; width: 700px; float: left;">
                <li style="float: left; clear:both; width: 100%"><h2>What Feed Type are you seeking?</h2></li>
                <li style="float:left; width: 100%; padding-top: 20px">
                    <div style="float:left; padding-right: 10px; width: 20%">
                        <input type="checkbox" name="feedtype" style="float: left; width: 20px"/> Vontu
                    </div>
                    <div id = "fromDate1" width="30%" style="float:left">
                        <input id="from1" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                    </div>
                    <div id=" toDate1" width="30%" style="float:left; padding-left: 10px">
                        <input id="to1" class="toDate" type="text"  value="" title="End Date for the feed"/>
                    </div>
                    <div id="frequencyDiv1" style="float:left; padding-left: 10px">
                        <input style="width:30px" type="text"  title="This will be frequency of the logs sent through syslog (millisec)"/>
                    </div>
                </li>
                <li style="float:left; width: 100%; padding-top: 20px">
                    <div style="float:left; padding-right: 10px; width: 20%">
                        <input type="checkbox" name="feedtype" style="float: left; width: 20px"/> Sharepoint
                    </div>
                    <div id = "fromDate2" width="30%" style="float:left">
                        <input id="from2" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                    </div>
                    <div id=" toDate2" width="30%" style="float:left; padding-left: 10px">
                        <input id="to2" class="toDate" type="text"  value="" title="End Date for the feed"/>
                    </div>
                    <div id="frequencyDiv2" style="float:left; padding-left: 10px">
                        <input style="width:30px" type="text" title="This will be frequency of the logs sent through syslog (millisec)"/>
                    </div>
                </li>
                <li style="float:left; width: 100%; padding-top: 20px">
                    <div style="float:left; padding-right: 10px; width: 20%">
                        <input type="checkbox" name="feedtype" style="float: left; width: 20px"/> Proxy
                    </div>
                    <div id = "fromDate3" width="30%" style="float:left">
                        <input id="from3" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                    </div>
                    <div id=" toDate3" width="30%" style="float:left; padding-left: 10px">
                        <input id="to3" class="toDate" type="text"  value="" title="End Date for the feed"/>
                    </div>
                    <div id="frequencyDiv3" style="float:left; padding-left: 10px">
                        <input style="width:30px" type="text" title="This will be the frequency of the logs sent through syslog (millisec)"/>
                    </div>
                </li>
                <li style="float:left; width: 100%; padding-top: 20px">
                    <div style="float:left; padding-right: 10px; width: 20%">
                        <input type="checkbox" name="feedtype" style="float: left; width: 20px"/> Windows
                    </div>
                    <div id = "fromDate4" width="30%" style="float:left">
                        <input id="from4" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                    </div>
                    <div id=" toDate4" width="30%" style="float:left; padding-left: 10px">
                        <input id="to4" class="toDate" type="text"  value="" title="End Date for the feed"/>
                    </div>
                    <div id="frequencyDiv4" style="float:left; padding-left: 10px">
                        <input style="width:30px" type="text" title="This will be the frequency of the logs sent through syslog (millisec)"/>
                    </div>
                </li>
                <li style="float:left; width:100%; padding-top:20px">
                    <div id="chooseIp" style="float: left">
                        <ul style="list-style: none; float: left">
                            <li style="float: left; padding-right: 20px">Destination Ip: <input id="destinationIp" type="text" title="This will be the IP to which syslog will send the logs"/></li>
                            <li style="float: left">Destination Port: <input id="destinationPort" type="text" title="This will be port where syslog sends the log"/></li>
                        </ul>
                    </div>
                </li>
            </ul>
            <ul id="part2" style="list-style: none; width: 700px; float: left;">
                <h2>Tell us about your organization - Users and Network</h2>
                <p>The following is the preview of a set of Users we will supply for the purpose of generating a pseudo organization.
                If you want you can use the format as seen from the preview window to upload a users file for your organization.</p>

                <div id="previewusers" class="tableContainer">
                </div>
                
                <li><p>Do you want to upload a Users file ?</p><p id="yestoupload" style="float: left; clear: both; width: 100%"><input type="radio" name="uploadusers" value="1" style="width:20px"/>Yes, I would like to upload
                    <div id="importUsers" style="float: left; width: 100%"><form enctype="multipart/form-data" method="post" name="fileinfo"><input type="file" id="fileupload" name="file"/></form><br/><p style="font-size: 10px;"><span style="color: red">*</span>Please make sure to have the same format for the users file as shown in preview</p></div></p>
                    <p style="float: left"><input type="radio" name="uploadusers" value="2" style="width:20px"/>No, I will use the provided HR List of Users</p>
                </li>
            </ul>
            <button id="next">Next</button>
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
