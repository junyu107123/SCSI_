
function testPOST(){

$.post( x, {scable:,func:"6"})
  .done(function( data ) {
	//console.log("2:"+TrimMe(data));
    showSelect(TrimMe(data),z);
  });

}

$("#submitbutton").click(function(event){
    event.preventDefault(); //Prevent form beeing send
    var allFormDatas = {}; //Object thats stores all form data
    $.each($("#youtform").find("input"),function() { //loop over all inputs in the form
        allFormDatas[$(this).attr("name")] = $(this).val(); //Gets the value from an form element and puts into "allFormDatas"
    });

    var stringForEncryption = JSON.stringify(allFormDatas); //Now we have one long Json string for encryption
    var encrypted = CryptoJS.AES.encrypt(stringForEncryption, "Secret Passphrase");
    //Now use $.post here to send post data to your backend.
});

//Encryption should be 
encrypted>decrypt>parseJson>jsObject