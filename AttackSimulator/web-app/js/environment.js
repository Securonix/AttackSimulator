/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    filterInt = function (value) {
        if (/^(\-|\+)?([0-9]+|Infinity)$/.test(value))
            return Number(value);
        return NaN;
    };

    $("#loading").hide();

    $("#internalranges").change(function () {
        var selected = $("#internalranges option:selected").attr("value");
        if (selected === "classa") {
            $("#dmzranges").html("<option value=\"classb\">Class B (172.16.0.0 - 172.31.255.255)</option><option value=\"classc\">Class C (192.168.0.0 - 192.168.255.255)</option>");
        } else if (selected === "classb") {
            $("#dmzranges").html("<option value=\"classa\">Class A (10.0.0.0 - 10.255.255.255)</option><option value=\"classc\">Class C (192.168.0.0 - 192.168.255.255)</option>");
        } else if (selected === "classc") {
            $("#dmzranges").html("<option value=\"classa\">Class A (10.0.0.0 - 10.255.255.255)</option><option value=\"classb\">Class B (172.16.0.0 - 172.31.255.255)</option>");
        }
    });

    $("#type option:selected").removeAttr("selected");

    $("#payment").submit(function (event) {
        //test that the value of user.. cannot be null 
        //test the value of country.. cannot be null as well.
        var numberofusers = $("#name").val();
        var country = $("#countryname").val();
        var internalrange = $("#internalranges option:selected").attr("value");
        var dmzrange = $("#dmzranges option:selected").attr("value");
        var dmzhostslength = filterInt($("#numdmzval").val());
        event.preventDefault();

        var valid = true;

        if (country === null || country[0] === null || numberofusers < 1 || numberofusers > 665) {
            $("#errors").html("Values expected for number of users is within 665 and country selected should not contain null");
            valid = false;
        }

        if (valid) {
            $.post("/AttackSimulator/Environment/createUserTables", {country: country, numusers: numberofusers, internal: internalrange, dmzrange: dmzrange, dmzhostslength: dmzhostslength}, function (data) {
                if (data === "success") {
                    location.reload();
                    //window.location.href = "/AttackSimulator/inputRequest/inputRequest/inputRequest";
                }
            });
        }
    });

    $("#modifyenv").click(function () {
       dialog3.dialog("open");
    });

    $("#register").hide();

    dialog1 = $("#downloaduserdata").dialog({
        autoOpen: false,
        height: 200,
        width: 450,
        modal: true,
        buttons: {
            Cancel: function () {
                dialog1.dialog("close");
                //send out a post request to delete the temp file
                var href = $("#linkfromserver").html();
                var link = $(href).attr("href");
                var splitlink = link.split("/");
                var filename = splitlink[splitlink.length - 1];
                $.post("/AttackSimulator/Environment/deleteFile", {filename: filename});
            }
        },
        close: function () {
            dialog1.dialog("close");
        }
    });
    
    dialog3 = $("#delenvironment").dialog({
        autoOpen: false,
        height: 200,
        width: 450,
        modal: true,
        buttons: {
            "Delete Envrionment" : deleteEnvironmentForUser,
            Cancel: function () {
                dialog3.dialog("close");
                //send out a post request to delete the temp file
                var href = $("#linkfromserver").html();
                var link = $(href).attr("href");
                var splitlink = link.split("/");
                var filename = splitlink[splitlink.length - 1];
                $.post("/AttackSimulator/Environment/deleteFile", {filename: filename});
            }
        },
        close: function () {
            dialog3.dialog("close");
        }
    });
    
    function deleteEnvironmentForUser(){
         $.post("/AttackSimulator/Environment/modifyEnvironment", function (data) {
            console.log(data);
            location.reload();
        });
    }

    $("#begindownload").click(function () {
        dialog1.dialog("open");
        $.post("/AttackSimulator/Environment/getDownloadLink", function (data) {
            $("#infoMessage").html("Please click on the following link to begin your download");
            $("#linkfromserver").html(data);
        });
    });

    $("#fillhostname").click(function (event) {
        event.preventDefault();
        var listring1 = "<li style=\"width: 350px; float: left; margin-bottom: 18px\"><input type=\"text\" id=\"";
        var listring2 = "\" /></li>";
        var number = filterInt($("#numdmzval").val());
        if(number === NaN){
            $("#errors").html("Please enter a valid number for DMZ hosts");
            return;
        }
        var listring = "";
        for(var i=0; i < number; i++){
            listring += listring1+"hostnamedmz"+i+listring2;
        }
        
        $("#listhostnames").html(listring);
        dialog2.dialog("open");
    });
    
    $("span.dmzclass").click(function(){
        var dmzid = $(this).attr("dmzid");
        var actualid = dmzid.substring(5);
        var takeinput = $(this).attr("enabled");
        var withinputstring = "<input id=\"dmzprimary"+actualid+"\" value=\"";
        if(takeinput === "true"){
            //this is the first time the button was clicked.. 
            //change the value in the span to Save hostname
            $(this).html("Save hostname");
            var currentHostname = $("#dmzhname"+actualid).html();            
            $("#dmzhname"+actualid).html(withinputstring+currentHostname+"\"/>");
            $(this).attr("enabled", "false");
        }else if(takeinput === "false"){
            //this is the second time the button was clicked.
            //change the value in the span to 
            var valueInInput = $("#dmzprimary"+actualid).val();
            $.post("/AttackSimulator/Environment/saveDmzHostName", {hostname: valueInInput, dmzid: actualid}, function(data){
                if(data === "success"){
                    $("#errors").html("DMZ hostname updated");
                }
            });
            $("#dmzhname"+actualid).html(valueInInput);
            $(this).html("Edit hostname");
            $(this).attr("enabled", "true");
        }
    });
    
});

$(document).ajaxStart(function () {
    $("#loading").show();
}).ajaxStop(function () {
    $("#loading").hide();
});
