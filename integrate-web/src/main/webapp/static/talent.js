function getUrlParamVal(param, url) {
    var urls = url || window.location;
    var reg = new RegExp('(^|&)' + param + '=([^&]*)(&|$)');
    var r = urls.search.substr(1).match(reg);
    if (r !== null) {
        return decodeURIComponent(r[2]);
    }
    return null;
}

function setRootFont( psdWidth, rate ){
    var t,
        win = $(window),
        psdW = psdWidth || 750,
        rates = rate || 300;

    function setFontSize(){
        var winW = win.width();
        var minW = 320;
        var maxW = psdW;
        if( winW < minW ){
            winW = minW
        }
        if( winW > maxW ){
            winW = maxW
        }

        $("html").css("font-size", winW*100/maxW + "px")
    }
    window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function(){
        clearTimeout(t);
        t = setTimeout(function () {
            setFontSize()
        }, rates );
    },false)

    $(function(){
        setFontSize()
    })


}

function fetch(api, data, success){
    return $.ajax({
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
                window.location.href="./404.html";
            }
        },
        error: function(XMLHttpRequest, status, e) {
            if(e!=null){
                window.location.href="./404.html";
            }
        }
    })
}

$(function(){

    setRootFont()

    var SERVE = 'https://www.yk-zb.com/crowdsource/web'
    var API_TALENT_INFO = SERVE + '/surface/getTalentInfo'
    var API_TALENT_EVA = SERVE + '/surface/getTalentEvaluate'

    var talentID = getUrlParamVal('nid')

    var info = {
        nid: talentID,
        jsonp: 'getTalentInfo'
    }

    fetch(API_TALENT_INFO, info ,function(res){
        $("#usr-img").attr('src',  res.imgurl )
        $(".usr-name").html( res.name )
        $(".resume .info").html( res.introduction )
        $(".usr-tit span").html( res.position )

        if( res.serviceList && res.serviceList.length != 0 ){
            var serviceDom = ''
            for( var i = 0; i < res.serviceList.length; i++ ){
                var mode = res.serviceList[i].serviceMode
                if( mode == 0 ){
                    mode = '面谈'
                }
                if( mode == 1 ){
                    mode = '电话'
                }
                serviceDom += '<div class="item">' +
                        '<div class="item-tit">' + res.serviceList[i].serviceContent + '</div>' +
                        '<div class="item-price">¥' + res.serviceList[i].price + '/次</div>' +
                        '<div class="item-other">' +
                            '<span class="tag">' + mode + '</span>' +
                            '<span class="time">约' + res.serviceList[i].time + '小时</span>' +
                        '</div>' +
                    '</div>'
            }
            $(".item-wrap").html(serviceDom)
        }else{
            $(".item-wrap").html('<div class="item"><div class="item-tit">暂无数据</div></div>')
        }
    });

    var evaList = {
        nid: talentID,
        jsonp: 'evaList',
        pageSize:10
    }

    fetch( API_TALENT_EVA, evaList, function(res){

        if( res.length !== 0 ){

            var evaDom = ''
            for( var i = 0; i < res.length; i ++ ){
                evaDom += '<div class="eva-item">' +
                        '<div class="eva-img">' +
                            '<img src="'+ res[i].userBase.headImgUrl +'">' +
                        '</div>' +
                        '<div class="eva-info">' +
                            '<div class="nike-name">'+ res[i].userBase.nickname +'</div>' +
                            '<div class="eva-content">' + res[i].content + '</div>' +
                        '</div>' +
                    '</div>'
            }
            $('.eva-list').html(evaDom)
        }else{
            $('.eva-list').html('暂无评论')
        }

    })

})