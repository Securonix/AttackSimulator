/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
//    var totalOrders = 0;
//    $.post("/AttackSimulator/AdminView/getTotalOrderCount", function(data){
//        totalOrders = parseInt(data);
//    });
//    
//    for(i=0; i < totalOrders; i++){
//        $("Yes"+i).on("click", function(){
//            //Click on yes would mean that 
//            var offset = $(this).attr("offset");
//        });
//    }

    //we have to find the total number of radio buttons clicked on the page.. thankfully there are only one set of radiobuttons that we 
    //care about.. that might make the job easier
    
    $("#senddata").click(function(){
        //find all the radio buttons that have yes 
        var allcheckedradios = $("input[type=radio]:checked");
        var allyesids = new Array();
        var allnoids = new Array();
        allcheckedradios.each(function(index){
            if($(this).attr("value") == "Yes"){
                allyesids.push($(this).attr("orderid"));
            }else if($(this).attr("value") == "No"){
                allnoids.push($(this).attr("orderid"));
            }
        });
        
        console.log(allyesids);
        console.log(allnoids);
        
        $.post("/AttackSimulator/Order/deleteOrder", {
            allyesids: allyesids,
            allnoids: allnoids
        }, function(data){
            alert(data);
            if(data == "success"){
                location.reload();
            }
        })
    });
});

