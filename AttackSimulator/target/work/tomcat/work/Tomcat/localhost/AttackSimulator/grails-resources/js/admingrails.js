/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    
    function setPassword(){
        //read the two passwords and check 
        var password = $("#set-password").val();
        var conpassword = $("#set-confirm-password").val();

        if(password !== conpassword){
            $("#messageModal").html("The passwords don't match!!");
            return;
        }

        $.post("/AttackSimulator/AdminView/setNewPassword", {
            userid: userid,
            password: password
        }, function(data){
            if(data === "success"){
                dialog.dialog("close");
                location.reload();
            }else{
                $("#messageModal").html("There was some problem while saving your passwords.. please try again later");
                return;
            }
        });
    }
    
    dialog = $( "#setpassworddiv" ).dialog({
        autoOpen: false,
        height: 500,
        width: 450,
        modal: true,
        buttons: {
          "Set New Password": setPassword,
          Cancel: function() {
              dialog.dialog( "close" );
          }
        },
        close: function() {
              dialog.dialog("close");
        }
      });
    
    var rowcount = $("#admintable tr").length;
    var userid;
    
    for(var i=0; i<rowcount; i++){
        $("#approve"+i).click(function(){
            userid = $(this).attr("userid");
            $.post("/AttackSimulator/AdminView/approve", {
                userid: userid
            }, function(data){
                if (data === "success"){
                    console.log("success");
                    location.reload();
                }
            });
        });
        $("#disapprove"+i).click(function(){
            userid = $(this).attr("userid");
            $.post("/AttackSimulator/AdminView/disapprove", {
                userid: userid
            }, function(data){
                if (data === "success"){
                    console.log("success");
                    location.reload();
                }
            });
        });
        
        $("#setpass"+i).click(function(){
            //before opening the dialog, set the userid variable.
            userid = $(this).attr("userid");
            dialog.dialog("open");
        });
        
        $("#setpassappr"+i).click(function(){
            userid = $(this).attr("userid");
            dialog.dialog("open");
        });
        
        $("#makeadmin"+i).click(function(){
            userid = $(this).attr("userid");
            $.post("/AttackSimulator/AdminView/makeAdmin", {
                userid: userid
            }, function(data){
                if (data === "success"){
                    console.log("success");
                    location.reload();
                }
            });
        });
        $("#makeadminappr"+i).click(function(){
            userid = $(this).attr("userid");
            $.post("/AttackSimulator/AdminView/makeAdmin", {
                userid: userid
            }, function(data){
                if (data === "success"){
                    console.log("success");
                    location.reload();
                }
            });
        });
        
        $("#delete"+i).click(function(){
            userid = $(this).attr("userid");
            $.post("/AttackSimulator/AdminView/delete", {
                userid: userid
            }, function(data){
                if (data === "success"){
                    console.log("success");
                    location.reload();
                }    
            });
        });
        $("#deleteappr"+i).click(function(){
            userid = $(this).attr("userid");
            $.post("/AttackSimulator/AdminView/delete", {
                userid: userid
            }, function(data){
                if (data === "success"){
                    console.log("success");
                    location.reload();
                }
            });
        });
    }
});