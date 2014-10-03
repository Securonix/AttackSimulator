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
        <g:javascript src="parameters.js"/>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <img id="logo" class="notlogin" src="${request.contextPath}/images/lg_images/logo_high_res.png" width="250" border=0 alt="Support Center" style="padding-top:5px; padding-left: 10px; cursor: pointer;">
                <p><span>TRIAL FEED </span> REQUEST</p>
                <p id="userinfo">Hi "${user}"  | <a href="/AttackSimulator/attack/">Choose Attacks</a> | <a href="/AttackSimulator/logout">Logout</a></p>
            </div>
            <div id="content">
                <div id="index">

                    <h1>Welcome to the Securonix feed generator system</h1>

                    <p class="big">In order to request trial feed from Securonix, please fill out the form below.</p>

                    <hr />

                    <br />
                    <div id="form_804346" class="appnitro"  method="post" action="">	
                        <!--ul id="" style="list-style: none; width: 700px; float: left;">
                            <li style="float: left; clear:both; width: 100%"><h2>What is the type of feed you are seeking?</h2></li>
                            <li style="float:left; width: 100%; padding-top: 20px">
                                <div style="float:left; padding-right: 10px; width: 20%">
                                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="Vontu" id="1"/> Vontu
                                </div>
                                <div id = "fromDate1" width="30%" style="float:left">
                                    <input id="from1" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                                </div>
                                <div id=" toDate1" width="30%" style="float:left; padding-left: 10px">
                                    <input id="to1" class="toDate" type="text"  value="" title="End Date for the feed"/>
                                </div>
                                <div id="frequencyDiv1" style="float:left; padding-left: 10px">
                                    <input id="freq1" style="width:30px" type="text"  title="This will be frequency of the logs sent through syslog (millisec)"/>
                                </div>
                            </li>
                            <li style="float:left; width: 100%; padding-top: 20px">
                                <div style="float:left; padding-right: 10px; width: 20%">
                                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="Sharepoint" id="2"/> Sharepoint
                                </div>
                                <div id = "fromDate2" width="30%" style="float:left">
                                    <input id="from2" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                                </div>
                                <div id=" toDate2" width="30%" style="float:left; padding-left: 10px">
                                    <input id="to2" class="toDate" type="text"  value="" title="End Date for the feed"/>
                                </div>
                                <div id="frequencyDiv2" style="float:left; padding-left: 10px">
                                    <input id="freq2" style="width:30px" type="text" title="This will be frequency of the logs sent through syslog (millisec)"/>
                                </div>
                            </li>
                            <li style="float:left; width: 100%; padding-top: 20px">
                                <div style="float:left; padding-right: 10px; width: 20%">
                                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="Proxy" id="3"/> Proxy
                                </div>
                                <div id = "fromDate3" width="30%" style="float:left">
                                    <input id="from3" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                                </div>
                                <div id=" toDate3" width="30%" style="float:left; padding-left: 10px">
                                    <input id="to3" class="toDate" type="text"  value="" title="End Date for the feed"/>
                                </div>
                                <div id="frequencyDiv3" style="float:left; padding-left: 10px">
                                    <input id="freq3" style="width:30px" type="text" title="This will be the frequency of the logs sent through syslog (millisec)"/>
                                </div>
                            </li>
                            <li style="float:left; width: 100%; padding-top: 20px">
                                <div style="float:left; padding-right: 10px; width: 20%">
                                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="Windows" id="4"/> Windows
                                </div>
                                <div id = "fromDate4" width="30%" style="float:left">
                                    <input id="from4" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                                </div>
                                <div id=" toDate4" width="30%" style="float:left; padding-left: 10px">
                                    <input id="to4" class="toDate" type="text"  value="" title="End Date for the feed"/>
                                </div>
                                <div id="frequencyDiv4" style="float:left; padding-left: 10px">
                                    <input id="freq4" style="width:30px" type="text" title="This will be the frequency of the logs sent through syslog (millisec)"/>
                                </div>
                            </li>
                            <li style="float:left; width: 100%; padding-top: 20px">
                                <div style="float:left; padding-right: 10px; width: 20%">
                                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="Database" id="5"/> Database
                                </div>
                                <div id = "fromDate5" width="30%" style="float:left">
                                    <input id="from5" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                                </div>
                                <div id=" toDate5" width="30%" style="float:left; padding-left: 10px">
                                    <input id="to5" class="toDate" type="text"  value="" title="End Date for the feed"/>
                                </div>
                                <div id="frequencyDiv5" style="float:left; padding-left: 10px">
                                    <input id="freq5" style="width:30px" type="text" title="This will be the frequency of the logs sent through syslog (millisec)"/>
                                </div>
                            </li>
                            <li style="float:left; width: 100%; padding-top: 20px">
                                <div style="float:left; padding-right: 10px; width: 20%">
                                    <input type="checkbox" name="feedtype" style="float: left; width: 20px" value="netflow" id="6"/> Netflow
                                </div>
                                <div id = "fromDate6" width="30%" style="float:left">
                                    <input id="from6" class="fromDate" type="text"  value="" title="Start Date for the feed"/> 
                                </div>
                                <div id=" toDate6" width="30%" style="float:left; padding-left: 10px">
                                    <input id="to6" class="toDate" type="text"  value="" title="End Date for the feed"/>
                                </div>
                                <div id="frequencyDiv6" style="float:left; padding-left: 10px">
                                    <input id="freq6" style="width:30px" type="text" title="This will be the frequency of the logs sent through syslog (millisec)"/>
                                </div>
                            </li>
                            <li style="float:left; width:100%; padding-top:20px">
                                
                            </li>
                        </ul>
                        <!--ul id="part2" style="list-style: none; width: 700px; float: left;">
                            <h2>Tell us about your organization - Users and Network</h2>
                            <p>The following is the preview of a set of Users we will supply for the purpose of generating a pseudo organization.
                                If you want you can use the format as seen from the preview window to upload a users file for your organization.</p>

                            <div id="previewusers" class="tableContainer">
                            </div>

                            <li><p>Do you want to upload a Users file ?</p><p id="yestoupload" style="float: left; clear: both; width: 100%"><input type="radio" name="uploadusers" value="1" style="width:20px"/>Yes, I would like to upload
                                <div id="importUsers" style="float: left; width: 100%"><form enctype="multipart/form-data" method="post" name="fileinfo"><input type="file" id="fileupload" name="file"/></form><br/><p style="font-size: 10px;"><span style="color: red">*</span>Please make sure to have the same format for the users file as shown in preview</p></div></p>
                                <p style="float: left"><input type="radio" name="uploadusers" value="2" style="width:20px"/>No, I will use the provided HR List of Users</p>
                            </li>
                        </ul-->
                    </div>
                    <div id="chooseIp" style="float: left; width:100%">
                        <ul style="list-style: none; float: left">
                            <li style="float: left; padding-right: 20px">Destination Ip: <input id="destinationIp" type="text" title="This will be the IP to which syslog will send the logs"/></li>
                            <li style="float: left">Destination Port: <input id="destinationPort" type="text" title="This will be port where syslog sends the log"/></li>
                        </ul>
                    </div>
                    <div style="float:left; width: 100%"><button id="parameterssubmit">Submit</button></div>
                    <div class="clear"></div>
                </div>

                <div style="clear:both; text-align: center; color: #fe7700; text-decoration: bold; font-size: 15px;" id="errormessages"></div> 
            </div>
            <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
            <div align="center">
                <a id="powered_by" href="http://securonix.com"><img src=" ${request.contextPath}/images/osticket-securonixonly.gif" width="126" height="23" alt="Powered by osTicket"></a></div>
        </div>

        <div id="loading" style="position: absolute; width: 960px; top: 200px; left: 250px"><img src=" ${request.contextPath}/images/loading.gif" width="100px"/></div>
    </body>
</html>
