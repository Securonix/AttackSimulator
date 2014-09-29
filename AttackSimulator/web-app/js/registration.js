/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $("#register").hide();
    
    $("#registerfunction").click(function(event){
        event.preventDefault();
        $("#register").show();
    });
    
    $("#close-modal").click(function(event){
        event.preventDefault();
        $("#register").hide();
    });
});
