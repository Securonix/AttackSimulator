/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $("#loading").hide();
    
    $("#internalranges").change(function(){
        var selected = $("#internalranges option:selected").attr("value");
        if(selected === "classa"){
            $("#dmzranges").html("<option value=\"classb\">Class B (128.0.0.0 - 191.255.255.255)</option><option value=\"classc\">Class C (192.0.0.0 - 223.255.255.255)</option>");
        }else if(selected === "classb"){
            $("#dmzranges").html("<option value=\"classa\">Class A (0.0.0.0 - 127.255.255.255)</option><option value=\"classc\">Class C (192.0.0.0 - 223.255.255.255)</option>");
        }else if(selected === "classc"){
            $("#dmzranges").html("<option value=\"classa\">Class A (0.0.0.0 - 127.255.255.255)</option><option value=\"classb\">Class B (128.0.0.0 - 191.255.255.255)</option>");
        }
    });
    
    $("#type option:selected").removeAttr("selected");
    
    $("#payment").submit(function(event){
        //test that the value of user.. cannot be null 
        //test the value of country.. cannot be null as well.
        var numberofusers = $("#name").val();
        var country = $("#type").val();
        var internalrange = $("#internalranges option:selected").attr("value");
        var dmzrange = $("#dmzranges option:selected").attr("value");
        event.preventDefault();
        
        var valid = true;
        
        if(country == null || country[0] == null || numberofusers < 1 || numberofusers > 665){
            alert("Values expected for number of users is within 665 and country selected should not contain null");
            valid =false;
        }
        
        if(valid){
            $.post("/AttackSimulator/Environment/createUserTables", {country: country, numusers: numberofusers, internal: internalrange, dmzrange: dmzrange}, function(data){
                if(data == "success"){
                    alert("Your environment details have been successfully saved. We will be using these details for all your orders.")
                    window.location.href = "/AttackSimulator/inputRequest/inputRequest/inputRequest";
                }
            });
        }
    });
});

$(document).ajaxStart(function() {
    $("#loading").show();
}).ajaxStop(function() {
    $("#loading").hide();
});
