/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $("#submitButton").hide();
    
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
    
    $("#editOrder").click(function(event){
        event.preventDefault();
        //start reading all the tds with the editable class and replace the values in it with the input text and filling in the value 
        //that it has currently. A new submit button should automatically appear in order to save the data, and I can use the original
        //saveOrder action to save the new order since its anyways going to overwrite the order.
        var allEditableTds = $("td[class='editable']");
        
        allEditableTds.each(function(index){
            var text = $(this).html();
            text = text.trim();
            var withInput = "<input type=\"text\" value=\"" + text + "\" prevValue=\""+text+"\"/>";
            $(this).html(withInput);
        });
        
        $("#submitButton").show();
    });
    
    $("#submitButton").click(function(){
        var allTrsWithOrders = $("tr[class='actualOrders'");
        allTrsWithOrders.each(function(index){
            //get all the tds in this particular row.
            var orderid = $(this).attr("id");
            orderid = orderid.substr(2);
            var order = new Object();
            order.id = orderid;
            var changed = false;
            $(this).children("td.editable").each(function(i){
                var currentValue = $(this).children('input').val();
                var oldValue = $(this).children('input').attr('prevValue');
                var variableName = $(this).attr('varName');
                if(currentValue.trim() !== oldValue.trim()){
                    order[variableName] = currentValue;
                    changed = true;
                }
            });
            if(changed){
                $.post("/AttackSimulator/Order/updateOrder", {order: JSON.stringify(order)}, function(data){
                    if(data == "success"){
                        location.reload();
                    }else{
                        alert("There was some problem saving your order");
                        location.reload();
                    }
                });
            }
        });
    });
});
    

