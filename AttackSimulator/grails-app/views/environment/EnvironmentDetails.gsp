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
        <g:javascript src="application.js" />
        <g:javascript src="environment.js"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'environment.css')}" type="text/css">
        <title>Environment Details</title>
    </head>
    <body>
            <div id="content">
                <g:userenvironmentunknown>
                <div id="index">
                    <p class="big">Describe your Environment. We will pick randomized users for you and assign them ips based on your choice of 
                    IP range</p>
                    <hr />
                    <br />
                    <div id="form-fields">
                        <form id=payment method="POST">
                            <fieldset>
                                <legend>Number of Users in your Organization</legend>
                                <ol>
                                    <li>
                                        <label for=name>Number of Users</label>
                                        <input id="name" name="name" type="number" placeholder="1-665" required autofocus>
                                    </li>
                                </ol>
                            </fieldset>
                            <fieldset>
                                <legend>Network Information for your organization</legend>
                                <ol>
                                    <li>
                                        <label for=postcode>Internal IP Address Ranges</label>
                                        <select name = "internal" id = "internalranges">
                                            <option value="classa">Class A (0.0.0.0 - 127.255.255.255)</option>
                                            <option value="classb">Class B (128.0.0.0 - 191.255.255.255)</option>
                                            <option value="classc">Class C (192.0.0.0 - 223.255.255.255)</option>
                                         </select>
                                    </li>
                                    .<li>
                                        <label for=postcode>DMZ IP Address Ranges</label>
                                        <select name = "dmz" id="dmzranges">
                                            <option value="classb">Class B (128.0.0.0 - 191.255.255.255)</option>
                                            <option value="classc">Class C (192.0.0.0 - 223.255.255.255)</option>
                                          </select>
                                    </li>
                                    <li>
                                        <label for=country>External IP address ranges</label>
                                        <g:select id = "type" name="country" value="${countries}" noSelection="${['null':'Select Countries (press ctrl to select multiple)...']}"
                                            from="${countries}"></g:select>
                                    </li>
                                </ol>
                            </fieldset>
                            <fieldset>
                                <button type=submit>Submit</button>
                            </fieldset>
                        </form>
                    </div>

                    <div class="clear"></div>
                </div>
                </g:userenvironmentunknown>
                <g:userenvironmentknown>
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
                                <th class="third">
                                  <div class="th-inner">IpAddress</div>
                                </th>
                              </tr>
                            </thead>
                            <tbody>
                                <g:each var="user" in="${users}" status="i">
                              <tr>
                                <td>${user.firstname}</td>
                                <td>${user.lastname}</td>
                                <td>${user.department}</td>
                                <td>${ipaddress.get(i)}</td>
                              </tr>
                              </g:each>
                            </tbody>
                          </table>
                        </div>
                      </div>
                      
                      <div class="fixed-table-container">
                        <div class="header-background"> </div>
                        <div class="fixed-table-container-inner">
                          <table cellspacing="0" class="environment">
                            <thead>
                              <tr>
                                <th class="first">
                                  <div class="th-inner">Sno.</div>
                                </th>
                                <th class="third">
                                  <div class="th-inner">DMZ Address</div>
                                </th>
                              </tr>
                            </thead>
                            <tbody>
                                <g:each var="dmz" in="${dmzaddress}" status="i">
                              <tr>
                                <td>${i+1}</td>
                                <td>${dmz.dmzaddress}</td>
                              </tr>
                              </g:each>
                            </tbody>
                          </table>
                        </div>
                      </div>
                      
                      <div class="fixed-table-container">
                        <div class="header-background"> </div>
                        <div class="fixed-table-container-inner">
                          <table cellspacing="0" class="environment">
                            <thead>
                              <tr>
                                <th class="first">
                                  <div class="th-inner">Sno.</div>
                                </th>
                                <th class="third">
                                  <div class="th-inner">DMZ Address</div>
                                </th>
                              </tr>
                            </thead>
                            <tbody>
                                <g:each var="counUser" in="${countryByUser}" status="i">
                              <tr>
                                <td>${i+1}</td>
                                <td>${counUser.country}</td>
                              </tr>
                              </g:each>
                            </tbody>
                          </table>
                        </div>
                      </div>
                </g:userenvironmentknown>
                <div style="clear:both; text-align: center; color: #fe7700; text-decoration: bold; font-size: 15px;" id="errormessages"></div> 
            </div>
    </body>
</html>
