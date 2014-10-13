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
        <g:javascript src="attackorder.js"/>
    </head>
    <body>
        <div id="container">
            <g:hasattackorders>
            <div class="fixed-table-container">
                <div class="header-background"> </div>
                <div class="fixed-table-container-inner">
                    <table cellspacing="0" class="environment">
                        <thead>
                            <tr>
                                <th class="first">
                        <div class="th-inner">First Name</div>
                        </th>
                        <th class="second">
                        <div class="th-inner">Last Name</div>
                        </th>
                        <th class="second">
                        <div class="th-inner">Department</div>
                        </th>
                        <th class="third" colspan="3">
                        <div class="th-inner">IpAddress</div>
                        </th>
                        </tr>
                        </thead>
                        <tbody>
                            <g:each var="user" in="${attackorders}" status="i">
                                <tr>
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
