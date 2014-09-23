/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function onButtonClick(){
       username = document.getElementById("username").value;
       password = document.getElementById("password").value;
       
       $.post("/FeedGeneratorUI/authAdmin", {
           username: username,
           password: password
       }, function(data){
           //alert(JSON.stringify(data));
           if(data == "nousers"){
           	$("#datafeed").html("No users need approval");
                    return;
           }

           $("#datafeed").html(data.table);
           var script = "";
           for(var i=0; i<data.users.length; i++){
          	script  += "$(\"body\").on(\"click\", \"#" + data.users[i] +"yes\", function(){ \
          		$.post(\"FeedGeneratorUI/ModifyProperties\", {userNumber: " + data.users[i]+ ", action: \"yes\"}, function(data){ if(data == \"nousers\"){ $(\"#datafeed\").html(\"No users need approval\"); return;}else{ $(\"#datafeed\").html(data.table);}});});";
           }
           //console.log(script);	
           $("body").append('<script type="text/javascript">' + script + '</script>');       
           for(var i=0; i<data.users.length; i++){
          	script  += "$(\"body\").on(\"click\", \"#" + data.users[i] +"no\", function(){ \
          		$.post(\"FeedGeneratorUI/ModifyProperties\", {userNumber: " + data.users[i]+ ", action: \"no\"}, function(data){ if(data == \"nousers\"){ $(\"#datafeed\").html(\"No users need approval\"); return;}else{ $(\"#datafeed\").html(data.table);}});});";	
           }
           //console.log(script);	
           $("body").append('<script type="text/javascript">' + script + '</script>');
       });
   }  

$(document).ready(function(){
    function getCookie(cname){
      var name = cname + "=";
      var ca = document.cookie.split(';');
      for(var i=0; i<ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name)==0) return c.substring(name.length,c.length);
      }
      return null;
    }
});



