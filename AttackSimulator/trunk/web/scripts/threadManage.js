/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $(":button").click(function(){
        var id = $(this).attr("id");
        var service = $(this).attr("service-type");
        if(service){
            $.post("http://107.20.142.115:8085/feedgeneratorui/ThreadManagement", {orderid: id, operation: service});
        }
    });
});
    

