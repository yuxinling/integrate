/* javascript */
function getUrlParamVal(param, url) {
    var urls = url || window.location;
    var reg = new RegExp('(^|&)' + param + '=([^&]*)(&|$)');
    var r = urls.search.substr(1).match(reg);
    if (r !== null) {
        return decodeURIComponent(r[2]);
    }
    return null;
}

function fetch(api, data, success){
    $.ajax({
        url: api,
        data:data,
        cache:true,
        dataType:"jsonp",
        jsonpCallback: data.jsonp,
        timeout:10000,
        success: function(json) {
            if( json.status == 1 ){
                console.log(JSON.parse(json.data))
                success( JSON.parse(json.data))
            }else{
                window.location.href="404.html";
            }
        },
        error: function(XMLHttpRequest, status, e) {
            if(e!=null){
                window.location.href="404.html";
            }
        }
    })
}

!(function (win, doc) {
    function setFontSize() {
        var winWidth = window.innerWidth;
        var size = (winWidth / 750) * 100;
        doc.documentElement.style.fontSize = (size < 100 ? size : 100) + 'px';
    }

    var evt = 'onorientationchange' in win ? 'orientationchange' : 'resize';
    var timer = null;
    win.addEventListener(evt, function () {
        clearTimeout(timer);
        timer = setTimeout(setFontSize, 300);
    }, false);
    win.addEventListener("pageshow", function (e) {
        if (e.persisted) {
            clearTimeout(timer);
            timer = setTimeout(setFontSize, 300);
        }
    }, false);
    setFontSize();
}(window, document));


$(function () {

    var API = 'https://www.yk-zb.com/crowdsource/web/surface/web/surface'
    var questionApi = API + '/questionsInfo'
    var answerListApi = API + '/answerList'

    var nid = getUrlParamVal('nid');

    if( nid !== null ){

        fetch(questionApi, { 'nid':nid, 'jsonp':'questionsInfo' },function(res){

            $(".question-wrap .title").html(res.title)
            $(".question-wrap .desc").html(res.describe)
            $(".a-total").html(res.answerNum + "  回答")
            $(".w-total").html(res.focusNum + "  关注")
            $(".r-total").html(res.viewNum + "  浏览")

            //加载评论
            fetch(answerListApi, { 'nid':nid, 'jsonp':'answerList' }, function(res){
                var html = template('reply-item', {list:res});
                $(".reply-wrap").html(html);
            })

        });

    }else{
        window.location.href="404.html";
    }

})