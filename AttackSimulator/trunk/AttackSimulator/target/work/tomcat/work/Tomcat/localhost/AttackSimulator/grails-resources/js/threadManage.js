/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $(":button").click(function(){
         id = $(this).attr("id");
         //alert(id);
        var service = $(this).attr("service-type");
        if(service){
            $.post("/AttackSimulator/ThreadManage/startStop", {orderid: id, operation: service}, function(data){
                if(data == "threadstarted"){
                    //make the color green
                    //alert("thread started");
                    //alert(id);
                    $("#threadstate"+id).css("background-color", "#00FF00");
                }else if (data == "threadstopped"){
                    //make the color red
                    //alert("thread stopped");
                    $("#threadstate"+id).css("background-color", "#FF0000");
                }else if (data == "wrongoperation"){
                    //make the color grey
                    //alert("wrong operation sent");
                    $("#threadstate"+id).css("background-color", "#7F7F7F");
                }
            });
        }
    });
});
    

