function testForInvalidCharacters(stringvalue){
  var pat = "[a-zA-Z][a-zA-Z0-9]{5,31}";
  var modifiers = "";
  var pattern = new RegExp(pat, modifiers);
  var testVal = stringvalue.match(pattern);
  if(!testVal){
    return false;
  }else{
    return true;
  }
}

function checkTelephoneNumber(telnumber){
    var pat = /^(?:\(([0-9]{3})\)|([0-9]{3}))[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
    var match = pat.exec(telnumber);
    if(match === null){
      return false;
    }

    return true;
  }

function sanitizeInputUsername(username){
  username = username.trim();
  var test = testForInvalidCharacters(username);
  if(!test){
    return "invalidChars";
  }

  return username;
}

function testForInvalidPassChars(password){
  var charactersNotAllowed = "; ";
  for(var i=0; i < password.length; i++){
    for(var j=0; j < charactersNotAllowed.length; j++){
      if(charactersNotAllowed.charAt(0) == password.charAt(i)){
        return false;
      }
    }
  }
  return true;
}

function passLengthGreater8(password){
  if(password.length > 7){
    return true;
  }

  return false;
}

function sanitizeInputPassword(password){
  password = password.trim();

  if(!password){
    return "passwordEmpty";
  }

  var test = testForInvalidPassChars(password);

  if(!test){
    return "invalidCharsInPass";
  }

  test = passLengthGreater8(password);
  if(!test){
    return "passLength";
  }

  return password;
}

function testPassword(password){
  return sanitizeInputPassword(password);
}

function onButtonClick(){
  var username = document.getElementById("username").value;
  var password = document.getElementById("password").value;
  username = sanitizeInputUsername(username);
  password = sanitizeInputPassword(password);

  if(username == "invalidChars"){
    var messageBox = document.getElementById('message');
    messageBox.innerHTML = "The username has invalid characters or is too short.";
    return;
  }

  if(username == "firstCharNotAlpha"){
    var messageBox = document.getElementById('message');
    messageBox.innerHTML = "The username should start with an alphabet";
    return;
  }

  if(password == "invalidCharsInPass"){
    var messageBox = document.getElementById('message');
    messageBox.innerHTML = "The password contains invalid characters";
    return;
  }

  if(password == "passwordEmpty"){
    var messageBox = document.getElementById('message');
    messageBox.innerHTML = "The password cannot be empty";
    return;
  }

  var passhash = md5(password);
  
  $.post("http://107.20.142.115:8085/feedgeneratorui/authenticate", {username: username, password: passhash.toString()}, function(data){
    if(data == "loggedin"){
      window.location.href = "http://107.20.142.115:8085/feedgeneratorui/InputRequestParameters.jsp";
    }else{
      var message = document.getElementById('message');
      message.innerHTML = "Username and Password did not match, retry.";
    }
  });
}

function validEmail(email){
  var pat = "^[\\w\\-\\._\\+%]+@(?:[\\w\\-]+\\.)+[\\w]{2,6}$";
  var modifiers = "";
  var pattern = new RegExp(pat, modifiers);
  var result = email.match(pattern);
  if(result == null || result == ""){
    return false;
  }else{
    return true;
  }
}

function twoValueSame(string1, string2){
  if(string1 === string2){
    return true;
  }else{
    return false;
  }
}

function registerUser(){
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

  if(name == null || name == ""){
    var message = document.getElementById('message');
    message.innerHTML = "Name is required";
    return;
  }

  if(contact == null || contact == ""){
    var message = document.getElementById('message');
    message.innerHTML = "Contact Number is required";
    return;
  }

  if(company == null || company == ""){
    var message = document.getElementById('message');
    message.innerHTML = "Company is required";
    return;
  }

  var test = validEmail(email);
  if(!test){
    var message = document.getElementById('message');
    message.innerHTML = "Email is invalid";
    return;
  }

  if(username == null || username == ""){
    var message = document.getElementById('message');
    message.innerHTML = "Username cannot be empty";
    return;
  }

  username = sanitizeInputUsername(username);
  if(username == "invalidChars"){
    var message = document.getElementById('message');
    message.innerHTML = "Username has invalid characters";
    return;
  }

  test = twoValueSame(email, confirmEmail);
  if(!test){
    var message = document.getElementById('message');
    message.innerHTML = "Email does not match";
    return;
  }

  test = twoValueSame(password, confirmPassword);
  if(!test){
    var message = document.getElementById('message');
    message.innerHTML = "Passwords don't match";
    return;
  }

  test = testPassword(password);
  if(test == "passwordEmpty"){
    var message = document.getElementById('message');
    message.innerHTML = "Password is empty, please use prescribed format for the password";
    return;
  }

  if(test == "invalidCharsInPass"){
    var message = document.getElementById('message');
    message.innerHTML = "Password is invalid, please use prescribed format for the password";
    return;
  }

  if(test == "passLength"){
    var message = document.getElementById('message');
    message.innerHTML = "Password length is too short";
    return;
  }

  test = checkTelephoneNumber(contact);
  if(!test){
    var message = document.getElementById('message');
    message.innerHTML = "Telephone number format is wrong, please correct and try and again";
    return;
  }

  var passhash = md5(password);

  $.post("http://107.20.142.115:8085/feedgeneratorui/ajaxCheckUser", {username: username}, function(data){
    if(data == "true"){
      var message = document.getElementById('message');
      message.innerHTML = "Username already in use, please choose another.";
    }else{
      $.post("http://107.20.142.115:8085/feedgeneratorui/register", {
          username: username,
          password: passhash,
          fullname: name,
          company: company,
          email: email,
          contact: contact
      }, function(data){
         if(data == "success"){
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
         }else if(data == "failure"){
             $("#message").html("There was some problem registering, please try again later");
         }
      });
      /*
      var form = document.createElement("form");
      form.setAttribute('method', "post");
      form.setAttribute('action', "./register");
      var i = document.createElement("input");
      i.setAttribute("type", "text");
      i.setAttribute("name", "username");
      form.appendChild(i);
      var p = document.createElement("input");
      p.setAttribute("type", "password");
      p.setAttribute("name", "password");
      form.appendChild(p);
      var q = document.createElement("input");
      q.setAttribute("type", "text");
      q.setAttribute("name", "fullname");
      form.appendChild(q);
      var r = document.createElement("input");
      r.setAttribute("type", "text");
      r.setAttribute("name", "company");
      form.appendChild(r);
      var s = document.createElement("input");
      s.setAttribute("type", "text");
      s.setAttribute("name", "email");
      form.appendChild(s);
      var c = document.createElement("input");
      c.setAttribute("type", "text");
      c.setAttribute("name", "contact");
      form.appendChild(c);

      i.setAttribute("value", username);
      p.setAttribute("value", passhash);
      q.setAttribute("value", name);
      r.setAttribute("value", company);
      s.setAttribute("value", email);
      c.setAttribute("value", contact);
      //document.body.appendChild(form);
      form.submit();
      */
    }
  });
}

function afterLoadCallThis(){
  var registerButton = document.getElementById('register-button');
  registerButton.onclick = registerUser;

  getVariablesCapture();
}

function getVariablesCapture(){
  var $_GET = {};
  if(document.location.toString().indexOf('?') !== -1)
  {
    var query = document.location.toString().replace(/^.*?\?/, '').split('&');
    for(var i=0, l = query.length; i<l; i++){
      var aux = decodeURIComponent(query[i]).split('=');
      $_GET[aux[0]] = aux[1];
    }
  }
  //get the 'index' query parameter
  if($_GET['register']){
    var message = document.getElementById('message');
    message.innerHTML = "Registration was successful, please sign in now.";
  }
}
