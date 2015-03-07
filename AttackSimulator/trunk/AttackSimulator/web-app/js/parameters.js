$(document).ready(function () {
    var filebuttonclickcount = 0;
    $("#fileupload").hide();
    $("#fileonoff").click(function () {
        filebuttonclickcount++;
        if (filebuttonclickcount % 2 == 0) {
            //this means that there has been an even number of clicks
            $(this).css("background-color", "#E08341");
            $("#form_804346").show();
            $("#fileupload").hide();
        } else {
            //this means that there has been an odd number of clicks
            $(this).css("background-color", "green");
            //hide the original div and populate the div
            $("#form_804346").hide();
            $("#fileupload").show();
        }
    });

    $.post("/AttackSimulator/uielements/getFeedtypes", function (data) {
        $("#form_804346").html(data);
        var rowcount = $("#inputRequest tr").length;
        for (var i = 1; i < rowcount; i++) {
            $("#from" + i).datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 3,
                onClose: function (selectedDate) {
                    $("#to" + i).datepicker("option", "minDate", selectedDate);
                }
            });

            $("#to" + i).datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 3,
                onClose: function (selectedDate) {
                    $("#from" + i).datepicker("option", "maxDate", selectedDate);
                }
            });
        }

        $("#parameterssubmit").on("click", function (event) {
            event.preventDefault();
            //we have to make sure that each feed has the start date the end date and the frequency values set
            var feedtype = new Array();
            var ids = new Array();
            $("input[name=\"feedtype\"]").each(function () {
                if ($(this).is(":checked")) {
                    feedtype.push($(this).attr("value"));
                    ids.push($(this).attr("id"))
                }
            });

            //for each of the ids get the values for the start date the end date and the frequency
            var startdates = new Array();
            var enddates = new Array();
            var frequencies = new Array();

            for (i = 0; i < ids.length; i++) {
                startdates.push($("#from" + ids[i]).val());
                enddates.push($("#to" + ids[i]).val());
                freqval = "";
                for (j = 1; j < 5; j++) {
                    var num = parseFloat($("#freq" + ids[i] + "_" + j).val());
                    num = num * 1000;
                    freqval += num + ",";
                }
                freqval += parseFloat($("#freq" + ids[i] + "_" + 5).val()) * 1000;
                frequencies.push(freqval);
            }

            //sanity check that we have all the values correctly in the arrays; this is what is going to go inside the database.
            console.log(ids);
            console.log(startdates);
            console.log(enddates);
            console.log(frequencies);

            var checkVal = checkOtherParams(startdates, enddates, frequencies);
            if (ids.length == 0) {
                checkVal = false;
                $("#errormessages").html("You have to choose atleast one feedtype.");
            }
            if (checkVal === true) {
                //alert("ready to send data");
                $.post("/AttackSimulator/Order/saveOrder", {
                    feedtype: feedtype,
                    startdates: startdates,
                    enddates: enddates,
                    frequencies: frequencies
                }, function (data) {
                    if (data == "success") {
                        $("#errormessages").html("Your feed has been successfully saved. You can manage your feeds in your Orders page. Thank you for your interest in Securonix.");
                        //have to make the My Orders link operational
                        var textInSpan = $("#linkenable").html();
                        if (textInSpan.indexOf("a href") >= 0) {
                            //My Orders is already functional
                        } else {
                            $("#linkenable").html("<a href=\"/AttackSimulator/ThreadManage\">" + textInSpan + "</a>");
                        }
                    } else {
                        $("#errormessages").html("There has been some problem with saving your feed. Please contact us,and we will help you out.");
                    }
                });
            } else {
                //alert("You have missed some of the required parameters for one of the feeds you have selected, please make sure to enter the startdate, enddate and the frequency");
            }
        });
    });

    $.post("/AttackSimulator/uielements/getFeedtypesFile", function (data) {
        $("#fileupload").html(data);
        var rowcount = $("#inputRequest tr").length;
        for (var i = 1; i < rowcount; i++) {
            $("#fromFile" + i).datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 3,
                onClose: function (selectedDate) {
                    $("#toFile" + i).datepicker("option", "minDate", selectedDate);
                }
            });

            $("#toFile" + i).datepicker({
                defaultDate: "+1w",
                changeMonth: true,
                numberOfMonths: 3,
                onClose: function (selectedDate) {
                    $("#fromFile" + i).datepicker("option", "maxDate", selectedDate);
                }
            });
        }

        $("#fileordersubmit").on("click", function (event) {
            event.preventDefault();
            //we have to make sure that each feed has the start date the end date and the frequency values set
            var feedtype = new Array();
            var ids = new Array();
            $("input[name=\"feedtype\"]").each(function () {
                if ($(this).is(":checked")) {
                    feedtype.push($(this).attr("value"));
                    ids.push($(this).attr("id"))
                }
            });

            //for each of the ids get the values for the start date the end date and the frequency
            var startdates = new Array();
            var enddates = new Array();
            var frequencies = new Array();
            var filename = new Array();
            var dateformats =  new Array();
            for (i = 0; i < ids.length; i++) {
                startdates.push($("#fromFile" + ids[i]).val());
                enddates.push($("#toFile" + ids[i]).val());
                filename.push($("#filename" + ids[i]).val());
                freqval = "";
                for (j = 1; j < 2; j++) {
                    var num = parseFloat($("#freqFile" + ids[i] + "_" + j).val());
                    freqval += num + ",";
                }
                freqval += parseFloat($("#freqFile" + ids[i] + "_" + 2).val());
                frequencies.push(freqval);
                dateformats.push($("#dateformat" + ids[i]).val());
            }

            //sanity check that we have all the values correctly in the arrays; this is what is going to go inside the database.
            console.log(ids);
            console.log(startdates);
            console.log(enddates);
            console.log(frequencies);
            console.log(dateformats);
            
            var checkVal = true;
            if (ids.length == 0) {
                checkVal = false;
                $("#errormessages").html("You have to choose atleast one feedtype.");
            }
            if (checkVal === true) {
                //alert("ready to send data");
                $.post("/AttackSimulator/Order/saveOrderFile", {
                    feedtype: feedtype,
                    startdates: startdates,
                    enddates: enddates,
                    frequencies: frequencies,
                    destinationip: filename,
                    dateformat: dateformats
                }, function (data) {
                    if (data == "success") {
                        $("#errormessages").html("Your feed has been successfully saved. You can manage your feeds in your Orders page. Thank you for your interest in Securonix.");
                        //have to make the My Orders link operational
                        var textInSpan = $("#linkenable").html();
                        if (textInSpan.indexOf("a href") >= 0) {
                            //My Orders is already functional
                        } else {
                            $("#linkenable").html("<a href=\"/AttackSimulator/ThreadManage\">" + textInSpan + "</a>");
                        }
                    } else {
                        $("#errormessages").html("There has been some problem with saving your feed. Please contact us,and we will help you out.");
                    }
                });
            } else {
                //alert("You have missed some of the required parameters for one of the feeds you have selected, please make sure to enter the startdate, enddate and the frequency");
            }
        });
    });

//we need a few checks for the fields first.. have to make sure that none of the fields are empty before the form 
    //is submitted.. it cannot be that the contact number is empty.. there should be an option for a user to update this
    //information, if later this changes.

    //global variable to remember that all the fields were correctly filled;

    $("#loading").hide();

    function cleanupErrorMessages(id) {
        $("#" + id).css("color", "black");
        $("#errormessages").html("");
    }

    $(document).tooltip();


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

    function checkOtherParams(startdates, enddates, frequencies) {
        for (i = 0; i < startdates.length; i++) {
            if (startdates[i] == "" || startdates[i] == null) {
                alert("You missed the start date on one of the feeds");
                return false;
            }
        }

        for (i = 0; i < enddates.length; i++) {
            if (enddates[i] == "" || enddates[i] == null) {
                alert("You missed the end date on one of the feeds");
                return false;
            }
        }

        for (i = 0; i < frequencies.length; i++) {
            if (frequencies[i] == "" || frequencies[i] == null) {
                alert("You missed freqencies of one of the feeds");
                return false;
            }
        }

        if (!checkStartEndDates(startdates, enddates)) {
            return false;
        }

        return true;
    }

    function checkStartEndDates(startdates, enddates) {
        var today = new Date();

        for (i = 0; i < startdates.length; i++) {
            var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
            var date1 = new Date(startdates[i].replace(pattern, '$3/$1/$2'));
            var date2 = new Date(enddates[i].replace(pattern, '$3/$1/$2'));
            var diff1 = dateDiff(date1, today);
            if (diff1 < 0) {
                alert("We don't allow start dates, earlier than today");
                return false;
            }

            var diff2 = dateDiff(date2, date1);
            if (diff2 < 0) {
                alert("Your start date is after your end date for the feed. Please correct and try again.");
                return false;
            }
        }

        return true;
    }

    function dateDiff(date1, date2) {
        //Dates are in string format mm/dd/yyyy
        //date2 has to be less than date1
        if (date2.getFullYear() > date1.getFullYear()) {
            return -1;
        } else if (date2.getFullYear() == date1.getFullYear()) {
            if (date2.getMonth() > date1.getMonth()) {
                return -1;
            } else if (date2.getMonth() == date1.getMonth()) {
                if (date2.getDate() > date1.getDate()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }


});
