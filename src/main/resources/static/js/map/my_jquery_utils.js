//jQuery 3.2.1
//common function for all projects

//一些功能開關
var cableMapEnable = true;
var detailEnable = true; //進入鄉鎮市地圖

//load javascript
function loadJs(jsFile){
  $.getScript(jsFile, function (data, textStatus, jqxhr) {
    //console.log(data); // Data returned
    //console.log(textStatus); // Success
    //console.log(jqxhr.status); // 200
    //console.log("Load jfunction1 was performed.");
  });
}

/*
function loadScript(path, callback) {

  var done = false;
  var scr = document.createElement('script');

  scr.onload = handleLoad;
  scr.onreadystatechange = handleReadyStateChange;
  scr.onerror = handleError;
  scr.src = path;
  document.body.appendChild(scr);

  function handleLoad() {
      if (!done) {
          done = true;
          callback(path, "ok");
      }
  }

  function handleReadyStateChange() {
      var state;

      if (!done) {
          state = scr.readyState;
          if (state === "complete") {
              handleLoad();
          }
      }
  }
  function handleError() {
      if (!done) {
          done = true;
          callback(path, "error");
      }
  }
}
*/