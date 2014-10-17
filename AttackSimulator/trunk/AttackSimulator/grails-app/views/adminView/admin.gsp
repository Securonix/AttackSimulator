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
         <link rel="stylesheet" href="${resource(dir: 'css', file: 'attackorders.css')}" type="text/css">
        <g:javascript src="admingrails.js"/>
        <title>Feed Approval Page</title>
    </head>
    <body>
            <div id="content">
                <div id="index">

                    <h1>Welcome to the Securonix feed generator system admin page.</h1>

                    <hr />

                    <br />

                    <div id="datafeed">
<!-- Unapproved users-->
                    <div class="fixed-table-container">
                    <div class="header-background"> </div>
                    <div class="fixed-table-container-inner">
                        <table cellspacing="0" class="environment">
                            <thead>
                                <tr>
                                    <th class="first">
                                        <div class="th-inner">Username</div>
                                    </th>
                                    <th class="second">
                                        <div class="th-inner">Work Email</div>
                                    </th>
                                    <th class="second">
                                        <div class="th-inner">Approve User</div>
                                    </th>
                                    <th class="second">
                                        <div class="th-inner">Delete User</div>
                                    </th>
                                    <th class="second">
                                        <div class="th-inner">Set Password</div>
                                    </th>
                                    <th class="third">
                                        <div class="th-inner">Make user admin</div>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <g:each var="user" in="${usersunappr}" status="i">
                                    <tr>
                                        <td style="width: 200px">${user.username}</td>
                                        <td style="width: 200px">${user.workemail}</td>
                                        <td style="width: 200px"><button id="approve${user.id}" type="button" \
                                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
                                                <span class="ui-button-text">Approve User</span></button></td>
                                        <td style="width: 200px"><button id="delete${user.id}" type="button" \
                                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
                                                <span class="ui-button-text">Delete User</span></button></td>
                                        <td ><button id="setpass${user.id}" type="button" \
                                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
                                                <span class="ui-button-text">Set password</span></button></td>
                                        <td ><button id="makeadmin${user.id}" type="button" \
                                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
                                                <span class="ui-button-text">Make Admin</span></button></td>
                                    </tr>
                                </g:each>
                                <g:each var="user" in="${usersappr}" status="i">
                                    <tr>
                                        <td style="width: 200px">${user.username}</td>
                                        <td style="width: 200px">${user.workemail}</td>
                                        <td style="width: 200px"><button id="disapprove${user.id}" type="button" \
                                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
                                                <span class="ui-button-text">Disable User</span></button></td>
                                        <td style="width: 200px"><button id="delete${user.id}" type="button" \
                                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
                                                <span class="ui-button-text">Delete User</span></button></td>
                                        <td ><button id="setpass${user.id}" type="button" \
                                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
                                                <span class="ui-button-text">Set password</span></button></td>
                                        <td ><button id="makeadmin${user.id}" type="button" \
                                            class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
                                                <span class="ui-button-text">Make Admin</span></button></td>
                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
















   <!--table id="orders" border="1">
            <thead>
                   <tr>
                       <th>Feedtype</th>
                       <th>Frequency</th>
                       <th>Start Date</th>
                       <th>End Date</th>
                       <th>Destination Ip</th>
                       <th>Destination Port</th>
                       <th>Approval (Yes/No)</th>
                   </tr>
                </thead>
                <tbody>
                     <g:each in="${orders}" status="i" var="order">
                         <tr id="${order?.id}" class="${(i % 2) == 0 ? 'odd' : 'even'}">
                              <td >${order?.feedtype}</td>
                              <td >${order?.frequency}</td>
                              <td >${order?.startdate}</td>
                              <td >${order?.enddate}</td>
                              <td >${order?.destinationip}</td>
                              <td >${order?.destinationport}</td>
                              <td><input id="Yes${order?.id}" type="radio" name="status${order?.id}" value="Yes" orderid="${order?.id}"/>Yes &nbsp;<input id="No${order?.id}" type="radio" name="status${order?.id}" value="No" orderid="${order?.id}"/>No</td>
                          </tr>
                     </g:each>
                 </tbody>
       </table>
   <ul style="list-style:none; width: 100%; float: right">  
       <button id="senddata">Submit</button>
   </ul>
   
</div>

<div class="clear"></div>
</div-->

                        <div id="messages"></div>

                        <div style="clear:both; text-align: center; color: red; text-decoration: bold; font-size: 15px;" id="message"></div> 
                    </div>
                <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
            </div>
            </div>
    </body>
</html>