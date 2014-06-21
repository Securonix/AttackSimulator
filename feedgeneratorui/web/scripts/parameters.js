$(document).ready(function() {
    //we need a few checks for the fields first.. have to make sure that none of the fields are empty before the form 
    //is submitted.. it cannot be that the contact number is empty.. there should be an option for a user to update this
    //information, if later this changes.

    //global variable to remember that all the fields were correctly filled;
    globalCheck = new Array(5);
    for (var i = 0; i < globalCheck.length; i++) {
        globalCheck[i] = 0;
    }

    $("#loading").hide();

    $(document).ajaxStart(function() {
        $("#loading").show();
    }).ajaxStop(function() {
        $("#loading").hide();
    });

    function noNullFieldCheck() {
        var contentPeriod1 = document.getElementById("from").value;
        var contentPeriod2 = document.getElementById("to").value;
        var frequency = document.getElementById("element_9").value;
        var destinationIp = document.getElementById("element_6").value;
        var destinationPort = document.getElementById("element_7").value;
        
        var checked = [];
        $("input[name='feedtype']:checked" ).each(function(){
            checked.push($(this).val());
        });
        
        if(checked.length == 0){
            return false;
        }
        
        contentPeriod1 = contentPeriod1.trim();
        contentPeriod2 = contentPeriod2.trim();
        frequency = frequency.trim();
        destinationPort = destinationPort.trim();
        destinationIp = destinationIp.trim();

        if (contentPeriod2 == null || contentPeriod2 == "") {
            return "contentPeriod2Empty";
        }

        if (contentPeriod1 == null || contentPeriod1 == "") {
            return "contentPeriod1Empty";
        }

        if (frequency == null || frequency == "") {
            return "frequencyEmpty";
        }

        if (destinationIp == null || destinationIp == "") {
            return "destinationIpEmpty";
        }

        if (destinationPort == null || destinationPort == "") {
            return "destinationPortEmpty";
        }

        return "success";
    }

    function checkNumberOfMonths(contentPeriod) {
        var splitarray = contentPeriod.split(/\//);

        var month = parseInt(splitarray[0]);
        var day = parseInt(splitarray[1]);
        var year = parseInt(splitarray[2]);

        if (month == undefined || day == undefined || year == undefined || isNaN(month) || isNaN(day) || isNaN(year)) {
            return "dateformaterror";
        }

        if (month < 1 || month > 12) {
            return "dateformaterrorMonth";
        }

        if (day < 1 || day > 31) {
            return "dateformaterrorDay";
        }

        if (year > 2015) {
            return "yearExceeded";
        }

        if (year < 2014) {
            return "yearPrevious";
        }

        return "month: " + month + " day: " + day + " year: " + year;
    }

    function checkFrequencyOfFeed(feedfrequency) {
        var feedfreq = parseInt(feedfrequency);
        if (feedfreq == null || feedfrequency == 0) {
            return "feederror";
        }
        return "success";
    }

    function checkIpAddress(ipAddress) {
        var pat = /(([2]([0-4][0-9]|[5][0-5])|[0-1]?[0-9]?[0-9])[.]){3}(([2]([0-4][0-9]|[5][0-5])|[0-1]?[0-9]?[0-9]))/;
        var address = pat.exec(ipAddress);
        if (address == null) {
            return "notAnIp";
        }

        return "success";
    }

    function checkPort(destinationPort) {
        var portVal = parseInt(destinationPort);
        if (portVal < 1 || portVal > 65535) {
            return "portValueError";
        }

        return "success";
    }

    $("#element_9").change(function() {
        //global check index 3
        var feedfrequency = $("#element_9").val();
        var returnValue = checkFrequencyOfFeed(feedfrequency);
        globalCheck[3] = 0;
        if (returnValue == "feederror") {
            $("#element_9").css('border', '1px solid #ff0000');
            $("#errormessages").html("Your feed value seems to be wrong, please put in a value in the range of 1-100");
            return;
        } else {
            globalCheck[3] = 1;
            $("#element_9").css('border-bottom', '1px solid #ddd');
            $("#element_9").css('border-left', '1px solid #c3c3c3');
            $("#element_9").css('border-right', '1px solid #c3c3c3');
            $("#element_9").css('border-top', '1px solid #7c7c7c');
            $("#errormessages").html("");
            return;
        }
    });

    $("#from").change(function() {
        //global check index 1
        var fromValue = $("#from").val();
        var returnValue = checkNumberOfMonths(fromValue);
        globalCheck[1] = 0;
        if (returnValue == "dateformaterror") {
            $("#from").css('border', '1px solid #ff0000');
            $("#errormessages").html("There is some error in the date format, please select the date by clicking on the calendar");
            return;
        } else if (returnValue == "dateformaterrorMonth") {
            $("#from").css('border', '1px solid #ff0000');
            $("#errormessages").html("Month value is invalid, please select the date by clicking on the calendar");
            return;
        } else if (returnValue == "dateformaterrorDay") {
            $("#from").css('border', '1px solid #ff0000');
            $("#errormessages").html("Day value cannot exceed 31, please select the date by clicking on the calendar");
            return;
        } else if (returnValue == "yearExceeded") {
            $("#from").css('border', '1px solid #ff0000');
            $("#errormessages").html("Please don't select an year so far ahead, no one has seen so far into the future");
            return;
        } else if (returnValue == "yearPrevious") {
            $("#from").css('border', '1px solid #ff0000');
            $("#errormessages").html("I wish I could go back in time too, please select a value that makes sense");
            return;
        } else if(returnValue == "noFeedSelected"){
            $("#errormessages").html("You have not selected any Feed, you have to choose atleast one.");
        }else {      
            globalCheck[1] = 1;
            $("#from").css('border-bottom', '1px solid #ddd');
            $("#from").css('border-left', '1px solid #c3c3c3');
            $("#from").css('border-right', '1px solid #c3c3c3');
            $("#from").css('border-top', '1px solid #7c7c7c');
            $("#errormessages").html("");
            return;
        }
    });

    $("#to").change(function() {
        //global check index 2
        var toValue = $("#to").val();
        var returnValue = checkNumberOfMonths(toValue);
        globalCheck[2] = 0;
        if (returnValue == "dateformaterror") {
            $("#to").css('border', '1px solid #ff0000');
            $("#errormessages").html("There is some error in the date format, please select the date by clicking on the calender");
            return;
        } else if (returnValue == "dateformaterrorMonth") {
            $("#to").css('border', '1px solid #ff0000');
            $("#errormessages").html("Month value is invalid, please select the date by clicking on the calendar");
            return;
        } else if (returnValue == "dateformaterrorDay") {
            $("#to").css('border', '1px solid #ff0000');
            $("#errormessages").html("Day value cannot exceed 31, please select the date by clicking on the calendar");
            return;
        } else if (returnValue == "yearExceeded") {
            $("#to").css('border', '1px solid #ff0000');
            $("#errormessages").html("Please don't select an year so far ahead, no one has seen so far into the future");
            return;
        } else if (returnValue == "yearPrevious") {
            $("#to").css('border', '1px solid #ff0000');
            $("#errormessages").html("I wish I could go back in time too, please select a value that makes sense");
            return;
        } else {
            globalCheck[2] = 1;
            $("#to").css('border-bottom', '1px solid #ddd');
            $("#to").css('border-left', '1px solid #c3c3c3');
            $("#to").css('border-right', '1px solid #c3c3c3');
            $("#to").css('border-top', '1px solid #7c7c7c');
            $("#errormessages").html("");
            return;
        }
    });

    $("#element_7").change(function() {
        //global check index 5
        var portNum = $("#element_7").val();
        var returnValue = checkPort(portNum);
        globalCheck[5] = 0;

        if (returnValue == "portValueError") {
            $("#element_7").css('border', '1px solid #ff0000');
            $("#errormessages").html("The port number should ideally be a 4-5 digit number, please select a number that is valid");
            return;
        } else {
            globalCheck[5] = 1;
            $("#element_7").css('border-bottom', '1px solid #ddd');
            $("#element_7").css('border-left', '1px solid #c3c3c3');
            $("#element_7").css('border-right', '1px solid #c3c3c3');
            $("#element_7").css('border-top', '1px solid #7c7c7c');
            $("#errormessages").html("");
        }
    });

    $("#element_6").change(function() {
        //global check index 4
        var ipAddress = $("#element_6").val();
        var returnValue = checkIpAddress(ipAddress);
        globalCheck[4] = 0;
        if (returnValue == "notAnIp") {
            $("#element_6").css('border', '1px solid #ff0000');
            $("#errormessages").html("The ip address is not valid");
            return;
        } else {
            globalCheck[4] = 1;
            $("#element_6").css('border-bottom', '1px solid #ddd');
            $("#element_6").css('border-left', '1px solid #c3c3c3');
            $("#element_6").css('border-right', '1px solid #c3c3c3');
            $("#element_6").css('border-top', '1px solid #7c7c7c');
            $("#errormessages").html("");
        }
    });

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i].trim();
            if (c.indexOf(name) == 0)
                return c.substring(name.length, c.length);
        }
        return null;
    }

    $("#saveForm").click(function(event) {
        event.preventDefault();
        var addUpGlobalCheck = 0;
        for (var i = 0; i < globalCheck.length; i++) {
            addUpGlobalCheck += globalCheck[i];
        }

        if (addUpGlobalCheck == 5) {
            var test = noNullFieldCheck();
            if (!test) {
                $("#errormessages").html("No fields can be left blank");
            } else {
                //submit the form
                $("#errormessages").html("");
                var contentPeriod1 = document.getElementById("from").value;
                var contentPeriod2 = document.getElementById("to").value;
                var frequency = document.getElementById("element_9").value;
                var destinationIp = document.getElementById("element_6").value;
                var destinationPort = document.getElementById("element_7").value;
                var checked = [];
                $("input[name='feedtype']:checked" ).each(function(){
                    checked.push($(this).val());
                });
                    
                var selectedLogFileType = "";
                for(var i=0; i<checked.length-1; i++){
                    selectedLogFileType += checked[i]+"-";
                }
                selectedLogFileType += checked[checked.length-1];
                
                var username = getCookie("user");
                $.post("http://107.20.142.115:8085/feedgeneratorui/updateDBWithChoices", {
                    username: username,
                    contentPeriod1: contentPeriod1,
                    contentPeriod2: contentPeriod2,
                    frequency: frequency,
                    destinationIp: destinationIp,
                    destinationPort: destinationPort,
                    selectedLogFileType: selectedLogFileType
                }, function(data) {
                    if (data == "success") {
                        $("#errormessages").html("Thank you for requesting sample feed. Your request will be reviewed by Securonix team and logs will be sent once approved.");
                        $("#from").val("");
                        $("#to").val("");
                        $("#element_6").val("");
                        $("#element_9").val("");
                        $("#element_7").val("");
                    } else if (data == "contactfalse") {
                        $("#errormessages").html("There is some problem with the contact number, please retry with another");
                    } else if (data == "contentPeriod2false") {
                        $("#errormessages").html("The is some problem with the end date, please fix and retry");
                    } else if (data == "contentPeriod1false") {
                        $("#errormessages").html("There is some problem with the start date, please fix and retry");
                    } else if (data == "failurefailed") {
                        $("#errormessages").html("There was an incorrect format for one of the fields, please check and try again.")
                    }
                });
            }
        } else {

            $("#errormessages").html("Some of the fields are incorrect, please fix them and try to resend data");
        }
    });
});
