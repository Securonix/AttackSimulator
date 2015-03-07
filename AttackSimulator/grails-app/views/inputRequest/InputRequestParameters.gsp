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
        <title>Attack Simulator: Order your feeds</title>
        <g:javascript src="parameters.js"/>
        <g:javascript src="threadManage.js"/>
    </head>
    <body>
        <div id="content">
            <div id="index">
                <div id="turnfileon">
                    <button id="fileonoff" style="background-color: #E08341">Would you like to generate file for historical data?</button>
                </div>
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
                <!--div id="chooseIp" style="float: left; width:35%; border: 1px solid; margin: 20px; padding: 5px">
                    <ul style="list-style: none; float: left">
                        <li style="float: left; padding-right: 20px">Destination Ip: <input id="destinationIp" type="text" title="This will be the IP to which syslog will send the logs"/></li>
                        <li style="float: right">Destination Port: <input id="destinationPort" type="text" title="This will be port where syslog sends the log"/></li>
                    </ul>
                </div-->
                
                <div class="clear"></div>
                <div id="fileupload"></div>
                <div id="Control-panel">
                    <button id="editOrder" type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">Edit your orders</button>

                    <table style="border-style: solid;" align="center" border="1">
                        <tr>
                            <th>Feed</th>
                            <th>Date Start</th>
                            <th>Date Ended</th>
                            <th>Frequency</th>
                            <th>Dest. IP</th>
                            <th>Dest. Port</th>
                            <th>Start Service</th>
                            <th>Stop Service</th>
                        </tr>

                        <g:each in="${orders}" status="i" var="order">
                            <tr class="actualOrders" id="tr${order.id}">
                                <td style="text-transform: uppercase">
                                    ${order.feedtype}
                                </td>
                                <td class="editable" varName="startdate">
                                    ${order.startdate}
                                </td>
                                <td class="editable" varName="enddate">
                                    ${order.enddate}
                                </td>
                                <td class="editable" varName="frequency">
                                    ${order.frequency}
                                </td>
                                <td class="editable" varName="destinationip">
                                    ${order.destinationip}
                                </td>
                                <td class="editable" varName="destinationport">
                                    ${order.destinationport}
                                </td>
                                <td >
                                    <button id="${order.id}" service-type="startthread">Start Service </button>
                                </td>
                                <td >
                                    <button id="${order.id}" service-type="stopthread">Stop Service </button>
                                </td>
                                <td  id="threadstate${order.id}" style="width: 50px; background-color: ${(order.threadid == -1)?'#FF0000':'#00FF00'}">                     
                                </td>
                            </tr>
                        </g:each>
                    </table>

                    <br/>
                    <br/>
                    <button id="submitButton">Submit</button>
                    <div id="messages"></div>

                    <div style="clear:both; text-align: center; color: red; text-decoration: bold; font-size: 15px;" id="message"></div> 
                </div>
            </div>

            <div style="clear:both; text-align: center; color: #fe7700; text-decoration: bold; font-size: 15px;" id="errormessages"></div> 
        </div>
        <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
    </body>
</html>
