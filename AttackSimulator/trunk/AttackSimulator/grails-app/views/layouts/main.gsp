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
    <g:javascript library="application"/>
    <g:javascript library="jquery" />
    <g:javascript library="jquery-ui" />
      <r:layoutResources />
  <g:layoutHead/>
    <g:javascript src="maingrails.js" />
    <g:javascript src="jquery.sidr.min.js" />
</head>
<body>
  <!--div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div-->
    
    <div id="sidebar-wrapper" class="close-box sidebar-show" style="height: 585px; display: block; width: 39px;">
        <div id="sb-wrap">
            <h3 class="ui-accordion-header ui-helper-reset ui-state-active-override-black expmenu">
          Filters
              <span id="hideSideBarAdmin" class="togglesidebar tooltipEl hideAdminSidebar"></span>
              <span class="labels"><a href="#" id="defaultPoliciesPanelHeader"></a></span>
            </h3>

            <div id="sidebar-content" class="sb-content-block left-panel-override left-panel">                
                <div id="task-filter" class="ui-accordion ui-widget ui-helper-reset" role="tablist">
                    <div class="ui-accordion-header ui-helper-reset ui-state-default ui-accordion-header-active ui-state-active ui-corner-top ui-accordion-icons" role="tab" id="ui-accordion-task-filter-header-0" aria-controls="f-job" aria-selected="true" tabindex="0"><span class="ui-accordion-header-icon ui-icon ui-icon-triangle-1-s"></span>By job status</div>
                    <div style="padding: 0px; display: block;" class="details-lp-div-override details-lp-div ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active" id="f-job" aria-labelledby="ui-accordion-task-filter-header-0" role="tabpanel" aria-expanded="true" aria-hidden="false">
                        <ul>
                            <li class="left-panel-item" data-value="All"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">All Jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e"></span></li>
                            <li class="left-panel-item" data-value="Completed"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Completed jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                            <li class="left-panel-item" data-value="Completed with errors"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Completed with errors jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                            <li class="left-panel-item" data-value="Failed"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Failed jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                            <li class="left-panel-item" data-value="Fired"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Fired jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                            <li class="left-panel-item" data-value="Created"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Tasks</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                            <li class="left-panel-item" data-value="Deleted"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Deleted jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                            <li class="left-panel-item" data-value="Saved"><span class="ui-icon-black ui-icon-gear fl"></span><span class="fl f-jobs-label">Saved jobs</span><span class="lp-icon ui-icon-blue ui-icon-carat-1-e" style="display:none;"></span></li>
                        </ul>
                    </div>
                    <div class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all ui-accordion-icons" role="tab" id="ui-accordion-task-filter-header-1" aria-controls="f-job-type" aria-selected="false" tabindex="-1"><span class="ui-accordion-header-icon ui-icon ui-icon-triangle-1-e"></span>By job type</div>
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
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="close-box sidebar-hide" style="float: left; height: 585px; width: 39px; display: block;">
        <div class="sidebar-hide-ico tooltipEl"></div>
    </div>
      <g:layoutBody/>
      
  <div class="footer" role="contentinfo"></div>
  <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
  <r:layoutResources />
</body>
</html>
