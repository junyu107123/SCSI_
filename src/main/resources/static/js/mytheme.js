$(function () {

    var $body = $("html,body");
    var $win = $(window),
        _winW, _winH;

        $('.ligBox').click(function(){
            $(this).toggleClass("active");
        })

        $(".left-container .top button").click(function(){
            let index = Math.floor($(this).attr("num")) +1
            for(let i = 0 ; i < 3 ; i ++){
                $("#popup"+(i+1)).addClass("hidden")
            }
            $("#popup"+index).removeClass("hidden")
            $("form").submit(function(e){
                e.preventDefault();
            });
        })
        
        $('#gear-btn').click(function(){
            let container = $(".left-container");
            let length = container.length;
            for(let i = 0 ; i < length ; i ++){
                if(container.eq(i).hasClass("hidden")) {
                    container.eq(i).removeClass("hidden");
                } else {
                    container.eq(i).addClass("hidden");
                }
            }
        })
        $('#switch').click(function(){
            let img = $(this).find("img.switch-left");
            if(img.hasClass("hidden")){ //switch-left is about to open
                img.removeClass("hidden");
                $(this).find("img.switch-right").addClass("hidden");
                $("#mode-structure").removeClass("hidden");
                $("#mode-map").addClass("hidden");
            } else {
                img.addClass("hidden");
                $(this).find("img.switch-right").removeClass("hidden");
                $("#mode-structure").addClass("hidden");
                $("#mode-map").removeClass("hidden");
            }
        })
        $("#mode-text").click(function(){
            let iconImg = $("#mode-icon").children();
            let text = $("#mode-text-wrap").children();
            let length = text.length;
            for(let i = 0 ; i < length ; i ++){
                if(!text.eq(i).hasClass("hidden")){
                    let e = i;
                    for(let r = 0 ; r < length ; r ++){
                        text.eq(r).addClass("hidden")
                        iconImg.eq(r).addClass("hidden")
                    }
                    if(text.eq(e).next().length === 0){
                        text.eq(0).removeClass("hidden")
                        iconImg.eq(0).removeClass("hidden")
                        break
                    } else {
                        text.eq(e).next().removeClass("hidden")
                        iconImg.eq(e).next().removeClass("hidden")
                        break
                    }
                }
                
            }
        })

        // sort popup
        $(".sort").on("click",function(){
            let index = $(this).attr("num")
            $( "#sortbox"+index ).toggleClass("toggleBlock");
        });
        
        /*跑馬燈*/
            var $marqueeUl = $('div#marquee > ul').eq(0),
            $marqueeli = $marqueeUl.append($marqueeUl.html()).children(),        
            _height = $('.msg-item').height()* -1,
            scrollSpeed = 600,
            timer,
            speed = 3000 + scrollSpeed;
            
            $marqueeli.hover(function(){
                clearTimeout(timer);
            }, function(){
                timer = setTimeout(showad, speed);
            });
            
            function showad(){
                var _now = $marqueeUl.position().top / _height;
                _now = (_now + 1) % $marqueeli.length;    
                $marqueeUl.animate({
                    top: _now * _height
                }, scrollSpeed, function(){         
                    if(_now == $marqueeli.length / 2){
                        $marqueeUl.css('', 0);
                    }
                });
        
                timer = setTimeout(showad, speed);
            }        
            // timer = setTimeout(showad, speed);

})