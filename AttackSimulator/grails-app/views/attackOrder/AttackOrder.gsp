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
        <title>Choose Attacks</title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'attackorders.css')}" type="text/css">
        <g:javascript src="attackorders.js"/>
    </head>
    <body>
        <div id="content">
            <div class="fixed-table-container">
                <div class="header-background"> </div>
                <div class="fixed-table-container-inner">
                    <table cellspacing="0" class="environment" id="attacktable">
                        <thead>
                            <tr>
                                <th class="first">
                                    <div class="th-inner">Feed Type</div>
                                </th>
                                <th class="second">
                                    <div class="th-inner">Types of Attack Available</div>
                                </th>
                                <th class="second">
                                    <div class="th-inner">Start Date of Attack</div>
                                </th>
                                <th class="second">
                                    <div class="th-inner">Time of Attack</div>
                                </th>
                                <th class="second">
                                    <div class="th-inner">Frequency of Attack</div>
                                </th>
                                <th class="second">
                                    <div class="th-inner">Attack by which user</div>
                                </th>
                                <th class="third">
                                    <div class="th-inner">Save Attack Order</div>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each var="attackorder" in="${attackorders}" status="i">
                                <tr>
                                    <td style="text-transform: uppercase" id="feedname${i+1}">${attackorder.key}</td>
                                    <td>
                                        <select id="selectedattack${i+1}">
                                            <g:each var="value" in="${attackorder.value}">
                                                <option value=${value.attackid} transactionfilepath="${value.transactionFilePath}">${value.description}</option>
                                            </g:each>
                                        </select>
                                    </td>
                                    <td>
                                        <input id="from${i+1}" class="fromDate" type="text"  value="" title="Start Date for the attack"/>
                                    </td>
                                    <td>
                                        <input id="to${i+1}" class="toDate" type="text"  value="" title="End Date for the attack"/>
                                    </td>
                                    <td><input type="text" id="frequency${i+1}"/></td>
                                    <td>
                                        <select id="selecteduser${i+1}">
                                            <g:each var="value" in="${listofusers}">
                                                <option value=${value.userid} account="${value.account1}">${value.firstname} ${value.lastname} </option>
                                            </g:each>
                                        </select>
                                    </td>
                                    <td><button id="attkorder${i+1}" type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false" id='begindownload'><span class="ui-button-text">Submit Attack</span></button></td>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <g:hasattackorders>
                <div class="fixed-table-container">
                    <div class="header-background"> </div>
                    <div class="fixed-table-container-inner">
                        <table cellspacing="0" class="environment">
                            <thead>
                                <tr>
                                    <th class="first">
                                        <div class="th-inner">Attacker Name</div>
                                    </th>
                                    <th class="second">
                                        <div class="th-inner">Day of Attack</div>
                                    </th>
                                    <th class="second">
                                        <div class="th-inner">Time of Attack</div>
                                    </th>
                                    <th class="second">
                                        <div class="th-inner">Frequency Of Attack</div>
                                    </th>
                                    <th class="second">
                                        <div class="th-inner">Destination IP:Port</div>
                                    </th>
                                    <th class="third">
                                        <div class="th-inner">Manage jobs</div>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <g:each var="userattack" in="${userattacks}" status="i">
                                    <tr>
                                        <td style="width: 200px">${userattack.username}</td>
                                        <td style="width: 200px">${userattack.dayofattack}</td>
                                        <td style="width: 200px">${userattack.timeofattack}</td>
                                        <td style="width: 200px">${userattack.frequency}</td>
                                        <td >${userattack.destinationip}:${userattack.destinationport}</td>
                                        <td></td>
                                    </tr>
                                </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
            </g:hasattackorders>
        </div>
    </body>
</html>
