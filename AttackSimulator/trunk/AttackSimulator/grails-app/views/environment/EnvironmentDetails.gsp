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
                    <div id="organizationstructure" style="float: left; width: 100%">
                         <div style="float:right; margin-bottom: 20px">
                          <div class="ui-dialog-buttonset"><button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false" id='begindownload'><span class="ui-button-text">Download User Data</span></button><button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false" id="modifyenv" ><span class="ui-button-text">Recreate your environment</span></button></div></div>
                        <div style="float: left; margin-top: 10px; width:100%">
                        <p style="font-size: 21px">User to IP Mapping</p>
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
                                    <g:each var="user" in="${users}" status="i">
                                  <tr>
                                    <td>${user.firstname}</td>
                                    <td>${user.lastname}</td>
                                    <td>${user.department}</td>
                                    <td>${ipaddress1.get(i)}</td>
                                    <td>${ipaddress2.get(i)}</td>
                                    <td>${ipaddress3.get(i)}</td>
                                  </tr>
                                  </g:each>
                                </tbody>
                              </table>
                            </div>
                          </div>
                        <p style="font-size: 21px">List of DMZ</p>
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
                        <p style="font-size: 21px">List of Countries</p>
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
                                      <div class="th-inner">Countries</div>
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
                          </div>
                      </div>
                </g:userenvironmentknown>
                <div style="clear:both; text-align: center; color: #fe7700; text-decoration: bold; font-size: 15px;" id="errormessages"></div> 
            </div>
            <div id="downloaduserdata" class="modalDialog">
                <span style="float: left; margin-top: 15px; margin-bottom: 15px; font-weight: bold"><h1 id="infoMessage" style="font-weight: bold">A 
                        download link will appear shorty...</h1> </span>
                <ul style="list-style: none; width: 400px; float: left;">
                    <li id="linkfromserver" style="width: 350px; float: left; margin-bottom: 18px"></li>
                </ul>
                <div id="messageModal" style="float:left; width:400px; color: red">Please don't close the dialog box before download is finished</div>
            </div>
    </body>
</html>
