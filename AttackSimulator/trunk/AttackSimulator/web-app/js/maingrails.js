/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
  
 //   $('#simple-menu').sidr();
    
    $("#sidebar-wrapper").hide();
    $("#adminBarClosed").show();
    $("#content").css("margin-left","0px");
    
    $("#hideSideBarAdmin").click(function(event){
        event.preventDefault();
        $("#adminBarClosed").show("slow");
        $("#sidebar-wrapper").hide("slow");
        $("#content").css("margin-left","0px");
    });
    
    $("#showSideBarAdmin").click(function(event){
        event.preventDefault();
        $("#sidebar-wrapper").show("slow");
        $("#adminBarClosed").hide("slow");
        $("#content").css("margin-left","280px");
    })
    
    $("#register-button").click(function(){
        var name = document.getElementById('register-name').value;
        var email = document.getElementById('register-email').value;
        var confirmEmail = document.getElementById('register-confirm-email').value;
        var username = document.getElementById('register-username').value;
        var password = document.getElementById('register-password').value;
        var confirmPassword = document.getElementById('register-confirm-password').value;
        var businessuser = false;
        
        name = name.trim();
        email = email.trim();
        confirmEmail = confirmEmail.trim();

        username = username.trim();
        password = password.trim();
        confirmPassword = confirmPassword.trim();
        
        if(name === null || name === ""){
            $("#messageModal").html("Name cannot be left blank");
            return;
        }
        
        if(email === null || email === ""){
            $("#messageModal").html("Email cannot be left blank");
            return;
        }
        
        if(email !== confirmEmail){
            $("#messageModal").html("Email's don't match, please correct them");
            return;
        }
        
        if(username === null || username === ""){
            $("#messageModal").html("Username cannot be blank");
            return;
        }
        
        if(password === null || password === ""){
            $("messageModal").html("Password cannot be blank");
            return;
        }
        
        if(password !== confirmPassword){
            $("#messageModal").html("Passwords do not match, please correct them");
            return;
        }
        
        if($("#businessuse").prop("checked")){
            businessuser = "true";
        }
        
        $.post("/AttackSimulator/Register/addSecUser", {
            name: name,
            username: username,
            password: password,
            fullname: name,
            email: email,
            businessuser: businessuser
        }, function(data){
           if(data === "success"){
               $("#message").html("Thank you for registering with Securonix. An email will be sent to you when your account has been approved.");
               //clear up the registration form
              document.getElementById('register-name').value = "";
              document.getElementById('register-email').value = "";
              document.getElementById('register-confirm-email').value = "";
              document.getElementById('register-username').value = "";
              document.getElementById('register-password').value = "";
              document.getElementById('register-confirm-password').value = "";
              $("#register").hide();
           }else if(data === "failure"){
               $("#messageModal").html("There was some problem registering, please try again later");
           }
        });
    });
    
    function toggleDiv(divId)
{
    //jQuery(".leftPanel").parent().slideToggle('fast');

    if(jQuery(divId).hasClass('expandedPanel'))
    {
        jQuery(divId).removeClass('expandedPanel');
        jQuery(divId).addClass('collapsedPanel');
        jQuery(".leftPanel").parent().animate({
            width: 'hide'
        });
        jQuery(".leftPanel").toggle('fast');

    }
    else
    {
        jQuery(divId).addClass('expandedPanel');
        jQuery(divId).removeClass('collapsedPanel');
        jQuery(".leftPanel").parent().slideToggle('fast');
        jQuery(".leftPanel").toggle('fast');
    }

//jQuery(divId).css("background","url('"+contextPath+"/images/tabexpand.png') no-repeat")
}

//This function toggle left panel
function hideSidebar(divId)
{
    jQuery(".leftPanel").slideToggle('fast');
    //    jQuery(".leftPanel").toggle('fast');
    jQuery(".leftPanel").parent().width(20);
    jQuery('#showSidebar').show();
}


//This function toggle left panel
function showSidebar(divId)
{
    jQuery(".leftPanel").slideToggle('fast');
    //    jQuery(".leftPanel").toggle('fast');
    jQuery(".leftPanel").parent().width(260);
    jQuery('#showSidebar').hide();
}

    //This function toggle left panel
    function hideDashboardSidebar(divId)
    {
        jQuery('.dash-wrapper').removeClass('leftPanel-wrapper').addClass('leftPanel-wrapper-cl');
        jQuery('.dash-wrapper .leftPanel').css('width', '');
        jQuery(".colmenu").show();
        jQuery(".expmenu").toggle('fast');
        jQuery(".colspmenu").show();
        jQuery('#showSidebar').show();
        //jQuery('.scan-wgt').closest('div').css('width', '1220px');
        //scrollIndicator(6);
        securonix.dashboard.helpers.updateWidthOnResize();
        securonix.dashboard.helpers.updateChartWidth(46);
        jQuery('.dash_charts_cntr').width(1230);
        //jQuery('.wid-container').width(1230);
    }


    //This function toggle left panel
    function showDashboardSidebar(divId)
    {
        jQuery('.dash-wrapper').addClass('leftPanel-wrapper').removeClass('leftPanel-wrapper-cl');
        jQuery('.dash-wrapper .leftPanel').css('width', '260px');
        jQuery(".colmenu").hide();
        jQuery(".expmenu").toggle('fast');
        jQuery(".colspmenu").hide();
        jQuery('#showSidebar').hide();
        //jQuery('.scan-wgt').closest('div').css('width', '1021px');
        //scrollIndicator(5);
        securonix.dashboard.helpers.updateWidthOnResize();
        securonix.dashboard.helpers.updateChartWidth(260);
        jQuery('.dash_charts_cntr').width(1028);
        //jQuery('.wid-container').width(1028);
    }

    //This function toggle left panel
    function hideLeftPanel(divId){
        jQuery(".left-panel").slideToggle('fast');
        //    jQuery(".left-panel").toggle('fast');
        jQuery(".left-panel").parent().width(20);
        jQuery('#showSidebar').show();
        jQuery('#hideSidebar').hide();
    }


    //This function toggle left panel
    function showLeftPanel(divId){
        jQuery(".left-panel").slideToggle('fast');
        jQuery(".left-panel").parent().width(300);

        jQuery('#showSidebar').hide();
        jQuery('#hideSidebar').show();
    }
    
    

});