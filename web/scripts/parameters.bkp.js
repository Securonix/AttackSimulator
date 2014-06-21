$(document).ready(function() {
  //we need a few checks for the fields first.. have to make sure that none of the fields are empty before the form 
  //is submitted.. it cannot be that the contact number is empty.. there should be an option for a user to update this
  //information, if later this changes.

  //global variable to remember that all the fields were correctly filled;


  function checkForCsv(file){
    if(typeof(file) != "string"){
      throw "notastring";
    }else{
        var split = file.split("\.");
        //alert(split[split.length-1]);
        if(split[split.length-1] != "csv"){
            $("#yestoupload").css("color", "red");
            $("#errormessages").html("The file you tried to upload is not a csv, please try with a file with .csv extension");
        }else{
            return true;
        }
    }
  }

  $("#loading").hide();

  $(document).ajaxStart(function() {
    $("#loading").show();
  }).ajaxStop(function() {
    $("#loading").hide();
  });

  //initally we need to hide each part. 
  $("#part1").hide();
  $("#importUsers").hide();

  $("input[name=uploadusers]").click(function(){
    if($(this).is(':checked') && $(this).val() == "1"){
      $("#importUsers").show();
    }else{
      $("#importUsers").hide();
    }
  });
  
  function cleanupErrorMessages(id){
      $("#"+id).css("color", "black");
      $("#errormessages").html("");
  }
  
  $("input[type=file]").change(function(e){
    if($(this).val() == "" || $(this).val().length == 0){
      //do nothing
    }else{
      //check if the file extension is csv.. if not ask for it.
      var check;
      try{
           check = checkForCsv($(this).val());
      }catch(err){
          if(err == "notastring{"){
              $("#errormessages").html("Please don't try something cheeky");
          }
      }
      //start an ajax post request to upload the file to server.
      if(check  == true){
          cleanupErrorMessages("yestoupload");
          var fd = new FormData();
          fd.append('file', $( '#fileupload' )[0].files[0]);
          fd.append('user', $.cookie("user"));
          fd.append('orderid', $.cookie("orderid"));
          fd.append("usernumber", $.cookie("usernumber"));
          
          $.ajax({
            url: "http://localhost:8080/feedgeneratorui/UploadUsersFile",
            type: "POST",
            data: fd,
            processData: false,  // tell jQuery not to process the data
            contentType: false   // tell jQuery not to set contentType
          });
      }
    }
  });

  $.post("http://localhost:8080/feedgeneratorui/PreviewUsersFile", function(data){
      console.log(data);
    $("#previewusers").html(data);
  });
});
