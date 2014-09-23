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
        <g:javascript src="admingrails.js"/>
        <title>Feed Approval Page</title>
    </head>
    <body>
        <div id="container">
      <div id="header">
          <a id="logo" href="/AttackSimulator/" title="AttackSimulator"><img src="${request.getContextPath()}/images/SecuronixLogo.jpeg" width="250" border=0 alt="Support Center" style="padding-top:5px; padding-left: 10px;"></a>
          <p><span>TRIAL FEED </span> REQUEST</p>
          <p id="userinfo"><a href="/AttackSimulator/ThreadManage">Manage Threads</a>&nbsp;|&nbsp;<a href="/AttackSimulator/logout">Logout</a></p>
      </div>
      <div id="content">
  <div id="index">

  <h1>Welcome to the Securonix feed generator system admin page.</h1>

  <hr />

  <br />

  <div id="datafeed">
          <table id="orders" border="1">
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
      <g:paginate next="Forward" prev="Back"
            maxsteps="0" controller="AdminView" total="${count}"/>
  </div>

  <div class="clear"></div>
  </div>

  <div id="messages"></div>

   <div style="clear:both; text-align: center; color: red; text-decoration: bold; font-size: 15px;" id="message"></div> 
   </div>
   <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
   <div align="center">
       <a id="powered_by" href="http://securonix.com"><img src="${request.getContextPath()}/images/osticket-securonixonly.gif" width="126" height="23" alt="Powered by osTicket"></a></div>
  </div>
</body>
</html>