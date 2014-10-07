<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}"/>
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main_1.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.ui.datepicker.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.sidr.dark.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'new_framework.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'nprogress.css')}" type="text/css"/>
    <g:javascript library="jquery" />
    <g:javascript library="jquery-ui" />
      <r:layoutResources />
  <g:layoutHead/>
    <g:javascript src="nprogress.js" />
    <g:javascript src="maingrails.js" />
    <g:javascript src="jquery.sidr.min.js" />
</head>
<body class="yui-skin-sam">
  <!--div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div-->
    <g:userloggedin>
        <g:set var="user" value="${sec.username()}" />
        <div id="wrapper" style="width: 100%;float: left;min-width: 1122px;background: #F1F2F7;margin-bottom: 30px;">
            <div id="main-wrapper">
                <div id="header-wrapper">
                    <table id="topInfoBar">
                        <tbody><tr>
                          <td class="logo-cntr">
                            <div id="logo"></div>
                          </td>
                          <td>
                            <div class="menuBar">
                              <ul id="jQMenu">            
                                <li style="position:relative;" data-menuname="dashboard" data-menuidx="1" class=""><span class="menuBaritemlabel"><span class="menu-label">WELCOME TO THE ATTACK SIMULATOR SYSTEM</span></span>
                                </li>            
                              </ul>
                            </div>
                          </td>
                          <td class="td">
                            <ul class="headerOptions">
                                <li>
                                    <span style="padding:5px;" class="fl rt-menu-icons" alt="Application statistics">
                                      <a href="/Profiler/stats/applicationStatistics" target="_blank"><span class="icon-app-stats h_ico" style="float:left;"></span></a>
                                    </span>
                                  </li>
                                <li>
                                  <span style="padding:5px;" class="fl rt-menu-icons" alt="Click to View White List">
                                    <a id="ad12009d0fb4417ab15b5cb6d8ca4fa6" href="#"><span class="icon-ql-whitelist" style="float:left;padding-right:2px;"></span></a>
                                  </span>
                                </li>
                                <li>
                                  <span style="padding:5px;" class="fl rt-menu-icons" alt="Launch Investigation Workbench">
                                    <a href="/Profiler/workbench/showInvestigationWorkbench?userid=-1&amp;parentObject=Search&amp;parentObjectId=-1&amp;object=" target="_blank"><span class="icon-ql-investigate h_ico" style="float:left;padding-right:2px;"></span></a>
                                  </span>
                                </li>
                                <li>
                                  <span class="fl rt-menu-icons" alt="Click to go Resources View" style="padding:5px;">
                                    <form method="POST" action="/Profiler/manageData/showResourceSearch" name="mlnkForm_home" id="mlnkForm_home">
                                      <input type="hidden" name="org.codehaus.groovy.grails.SYNCHRONIZER_TOKEN" value="36d29926-6d10-436f-a527-c89091bc0568">
                                      <a class="mlnk" href="/Profiler/manageData/showResourceSearch"><span class="icon-ql-resources h_ico"></span></a>
                                    </form>
                                  </span>
                                </li>
                                <li>
                                  <span class="fl rt-menu-icons" alt="Click to go Users View" style="padding:5px;">
                                    <form method="POST" action="/Profiler/manageData/showUserSearch" name="mlnkForm_home" id="mlnkForm_home">
                                      <input type="hidden" name="org.codehaus.groovy.grails.SYNCHRONIZER_TOKEN" value="36d29926-6d10-436f-a527-c89091bc0568">
                                      <a class="mlnk" href="/Profiler/manageData/showUserSearch"><span class="icon-ql-users h_ico"></span></a>
                                    </form>
                                  </span>
                                </li>


                                <li>
                                  <span class="fl rt-menu-icons" alt="Click to go Peers View" style="padding:5px;">
                                    <form method="POST" action="/Profiler/manageData/showPeerSearch" name="mlnkForm_home" id="mlnkForm_home">
                                      <input type="hidden" name="org.codehaus.groovy.grails.SYNCHRONIZER_TOKEN" value="36d29926-6d10-436f-a527-c89091bc0568">
                                      <a class="mlnk" href="/Profiler/manageData/showPeerSearch"><span class="icon-ql-peers h_ico"></span></a>
                                    </form>
                                  </span>
                                </li>


                                <li>
                                  <span style="padding:5px;" class="fl rt-menu-icons" alt="Third Party Intelligence Search ">
                                    <a id="ca890281fd224012a3f1b6b356e56c2c" href="#"><span class="icon-ql-tpi h_ico" style="float:left;padding-right:2px;"></span></a> 
                                  </span>
                                </li>

                              <li>
                                <div id="top-rt-menu">
                                  <span class="loggedin-user"></span>
                                  <span class="username">${user}</span>
                                  <span class="ico-dn-arrow dn-arrow"></span>

                                  <div class="drpdwn-menu">
                                    <span class="arrow-up"></span>
                                    <ul class="rt-menu-dropdown">
                                      <li><span class="ico-help drpdwn-menu-ico"></span><a class="mlnk helplnk" style="cursor: pointer;">Help</a></li>
                                      <li>
                                        <span class="ico-home drpdwn-menu-ico"></span>
                                        <form method="POST" action="/Profiler/dashboard/loadDashboard" name="mlnkForm_home" id="mlnkForm_home">
                                          <input type="hidden" name="org.codehaus.groovy.grails.SYNCHRONIZER_TOKEN" value="36d29926-6d10-436f-a527-c89091bc0568">
                                          <a class="mlnk" href="/Profiler/dashboard/loadDashboard">Home</a>
                                        </form>
                                      </li>
                                      <li>
                                        <span class="ico-chng-pass drpdwn-menu-ico"></span>
                                        <a id="ca1c8b384e06486692a49c8702576599" href="#"> Change Password</a>
                                      </li>
                                      <li><span class="ico-logout drpdwn-menu-ico"></span><a class="logout-link" href="/AttackSimulator/logout">Logout</a></li>
                                    </ul>
                                  </div>
                                </div>
                              </li>
                            </ul>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                </div>
                <div id="main-content-wrapper" style="float: left;width: 100%;position:relative;">
                    <div id="breadcrumb-wrapper" class="pageFrame">
                        <table>
                          <tbody><tr>
                            <td>
                              <div class="pageCrumb"><span class="icon-securitydashboard labels" style="float: left;">Attack Simulator</span><span class="bc-dash labels breadcrumb-link"><g:currentUrl/></span></div>
                            </td>
                            <td style="text-align:right">
                              <!--<span data-url="help.dashboard.securitydashboard" title="help.dashboard.securitydashboard" class="icon-help"></span>-->
                            </td>
                          </tr>
                        </tbody></table>
                      </div>
                    <div id="sidebar-wrapper" class="close-box sidebar-show" style="height: 585px; display: block; width: 39px;">
                        <div id="sb-wrap">
                            <h3 class="ui-accordion-header ui-helper-reset ui-state-active-override-black expmenu">
                          Actions
                              <span id="hideSideBarAdmin" class="togglesidebar tooltipEl hideAdminSidebar"></span>
                              <span class="labels"><a href="#" id="defaultPoliciesPanelHeader"></a></span>
                            </h3>

                            <div id="sidebar-content" class="sb-content-block left-panel-override left-panel">                
                                <div id="task-filter" class="ui-accordion ui-widget ui-helper-reset" role="tablist">
                                    <div style="padding: 0px; display: block;" class="details-lp-div-override details-lp-div ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active" id="f-job" aria-labelledby="ui-accordion-task-filter-header-0" role="tabpanel" aria-expanded="true" aria-hidden="false">
                                        <ul>
                                            <li class="left-panel-item" data-value="All"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label"><a href="/AttackSimulator/Environment">Your Environment</a></span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e"></span></li>
                                            <li class="left-panel-item" data-value="Completed"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label"><a href="/AttackSimulator/ThreadManage">My Orders</a></span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="Completed with errors"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label"><a href="/AttackSimulator/inputRequest/inputRequest">Place a new order</a></span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                        </ul>
                                    </div>

                                    <!--div class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all ui-accordion-icons" role="tab" id="ui-accordion-task-filter-header-1" aria-controls="f-job-type" aria-selected="false" tabindex="-1"><span class="ui-accordion-header-icon ui-icon ui-icon-triangle-1-e"></span>By job type</div>
                                    <div style="padding: 0px; display: none;" class="details-lp-div-override details-lp-div ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" id="f-job-type" aria-labelledby="ui-accordion-task-filter-header-1" role="tabpanel" aria-expanded="false" aria-hidden="true">
                                        <ul>
                                            <li class="left-panel-item" data-value=""><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">All jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e"></span></li>
                                            <li class="left-panel-item" data-value="GR_USER_IMPORT"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">User import jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GR_EVENT_IMPORT"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Activity import jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GR_ACCESS_IMPORT"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Access import jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GR_GLOSSARY_IMPORT"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Glossary import jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_DATETIME_ARCHIVE"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Activity archival jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_GEOLOCATION"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Populate geolocation jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_USERIPMAPPING"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Ip mapping jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GR_ACCESS_EXPORT"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Export access jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_RESOURCE_IMPORT"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Resources jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_TPI"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Third party intelligence jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_PEER_GENERATION"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Peer creation/assignment jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GR_LOOKUP"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Hostname lookup jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_INDEXER"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Indexing service jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GR_METADATA_IMPORT"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Metadata import jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_POLICY"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Policy scanner jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_WATCHLIST"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Watch list import jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                            <li class="left-panel-item" data-value="GROUP_POLICY_CASE"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Generate Cases</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                                        </ul>
                                    </div-->
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="adminBarClosed" class="close-box sidebar-hide" style="float: left; height: 585px; width: 39px; display: block;">
                        <div id="showSideBarAdmin" class="sidebar-hide-ico tooltipEl"></div>
                    </div>
                <g:layoutBody/>
                </div>
        </div>
        <div id="footer">Copyright &copy; Securonix.com. All rights reserved</div>
     </div>
    </g:userloggedin>
      
      
  <div class="footer" role="contentinfo"></div>
  <r:layoutResources />
</body>
</html>
