/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ajaxStart(function() {
    $("#loading").show();
}).ajaxStop(function() {
    $("#loading").hide();
});

$(document).ready(function(){
    $("#loading").hide();
    $("#modal-form-encapsulator").hide();
    $("#sequenceattack").hide();
    
    $("input[name='feedtype']").click(function(){
        if($(this).is(":checked")){
            var feedtype = $(this).attr("feedtype");
            var orderid = $(this).attr("orderid");
            
            $.post("/AttackSimulator/attack/getFeedConfig", {feedtype: feedtype, orderid: orderid}, function(data){
                //expecting this data to be JSON data or should I make it HTML?
                //I can build the HTML here and append it and remove it as.. it seems that grails
                //can be used to render the html and then send the html accross in the data .. I think let me try that
                console.log(data);
                $("#modal-form").html(data);
                $("#ajax-responses").html("");
                $("#modal-form-encapsulator").show();
            });
        }
    });
    
    $("#modal-submit").click(function(){
        //get all the values in the input boxes.
        var attackvectors = new Array();
        var orderid = $("#attacktemplate").attr("orderid");
        var feedconfigid = $("#attacktemplate").attr("feedconfigid");
        var feedtype = $("#attacktemplate").attr("feedtype");
        $("#attacktemplate :input").each(function(){
            attackvectors.push($(this).val());
        });
        
        $.post("/AttackSimulator/attack/saveAttackVector",{attackvectors: attackvectors, orderid: orderid, feedtype: feedtype, feedconfigid: feedconfigid}, function(data){
            if(data === "success"){
                $("#ajax-responses").html("Attack Vector Saved!!");
            }else{
                $("#ajax-responses").html("Problem saving the attack vector");
            }
        });
    });
    
    $("#close-modal-form").click(function(){
        $("#modal-form-encapsulator").hide();
    });
    
    $("#arrow").click(function(){
        //with the arrow click we are ready to show the user the attack vectors that he has ready for scheduling.
        //scheduling can be troublesome, will have to figure out an easy way for it.
        
        //we begin by checking if the particular user has any attackvectors stored in the attack definition table
        $.post("/AttackSimulator/attack/savedVectors", function(data){
            //data == success has saved vectors go ahead and show him the vectors. 
            //data == failed has no attack vectors saved so keep him on this page and ask him to create some vectors
            if(data == "failed"){
                alert("You don't have any attack vectors saved. Please save a few attack vectors before scheduling them to simulate a particular kind of attack.")
            }else{
                //we hope to get the html here instead of failed text.
                //this needs to be populated inside the
                $("#form-fields").hide();
                $("#sequenceattack").html(data);
                $("#sequenceattack").show();
            }
        });
    });
});