/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    
    $("#register-button").click(function(){
        var name = document.getElementById('register-name').value;
        var company = document.getElementById('register-company').value;
        var email = document.getElementById('register-email').value;
        var confirmEmail = document.getElementById('register-confirm-email').value;
        var contact = document.getElementById('register-contact').value;
        var username = document.getElementById('register-username').value;
        var password = document.getElementById('register-password').value;
        var confirmPassword = document.getElementById('register-confirm-password').value;
        
        name = name.trim();
        company = company.trim();
        email = email.trim();
        confirmEmail = confirmEmail.trim();
        contact = contact.trim();
        username = username.trim();
        password = password.trim();
        confirmPassword = confirmPassword.trim();
        
        if(name === null || name === ""){
            $("#message").html("Name cannot be left blank");
            return;
        }
        
        if(company === null || company === ""){
            $("#message").html("Company cannot be left blank");
            return;
        }
        
        if(email === null || email === ""){
            $("#message").html("Email cannot be left blank");
            return;
        }
        
        if(email !== confirmEmail){
            $("#message").html("Email's don't match, please correct them");
            return;
        }
        
        if(contact === null || contact === ""){
            $("#message").html("Contact field cannot be left blank");
            return;
        }
        
        if(username === null || username === ""){
            $("#message").html("Username cannot be blank");
            return;
        }
        
        if(password === null || password === ""){
            $("message").html("Password cannot be blank");
            return;
        }
        
        if(password != confirmPassword){
            $("#message").html("Passwords do not match, please correct them");
            return;
        }
        
        var passhash = md5(password);
        $.post("/AttackSimulator/Register/addSecUser", {
            username: username,
            password: password,
            fullname: name,
            company: company,
            email: email,
            contact: contact
        }, function(data){
           if(data === "success"){
               $("#message").html("Thank you for registering with Securonix. You can use your account to request for sample feeds from Securonix");
               //clear up the registration form
              document.getElementById('register-name').value = "";
              document.getElementById('register-company').value = "";
              document.getElementById('register-email').value = "";
              document.getElementById('register-confirm-email').value = "";
              document.getElementById('register-contact').value = "";
              document.getElementById('register-username').value = "";
              document.getElementById('register-password').value = "";
              document.getElementById('register-confirm-password').value = "";
           }else if(data === "failure"){
               $("#message").html("There was some problem registering, please try again later");
           }
        });
    });
});