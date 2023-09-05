//jQuery 3.2.1
//common function for all projects

//load javascript
function loadJs(jsFile){
  $.getScript(jsFile, function (data, textStatus, jqxhr) {
    //console.log(data); // Data returned
    //console.log(textStatus); // Success
    //console.log(jqxhr.status); // 200
    //console.log("Load jfunction1 was performed.");
  });
}
