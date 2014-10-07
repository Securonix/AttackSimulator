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
        <g:javascript src="threadManage.js"/>
        <title>Thread Management</title>
    </head>
    <body>
            <div id="content">
                <div id="index">

                    <h1>Manage your orders</h1>
                    <hr/>
                    <div class="clear"></div>
                </div>
                <div id="Control-panel">
                    <p id="editOrder" style="text-decoration: underline; margin-bottom: 10px; cursor: pointer; width: 87px">Edit your orders<p>
                    <table style="border-style: solid;" align="center" border="1">
                        <tr>
                            <th>Date Start</th>
                            <th>Date Ended</th>
                            <th>OrderId</th>
                            <th>User</th>
                            <th>Frequency</th>
                            <th>Dest. IP</th>
                            <th>Dest. Port</th>
                            <th>Feed</th>
                            <th>Start Service</th>
                            <th>Stop Service</th>
                        </tr>
                        
                            <g:each in="${orders}" status="i" var="order">
                            <tr class="actualOrders" id="tr${order.id}">
                               <td class="editable" varName="startdate">
                                  ${order.startdate}
                               </td>
                               <td class="editable" varName="enddate">
                                  ${order.enddate}
                               </td>
                               <td>
                                  ${order.id}
                               </td>
                               <td>
                                  ${order.userid}
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
                               <td style="text-transform: uppercase">
                                  ${order.feedtype}
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
    </body>
</html>