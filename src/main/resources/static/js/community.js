
$(function(){



        $("#li_dropdown").hover(function(){
            $("#li_dropdown").attr("class", "dropdown open");
        },function(){
            $("#li_dropdown").attr("class", "dropdown");
        });



    $(".comment_btn").hover(function () {
        var btn = $(this).parent().find("div").attr("class").toString().indexOf("in");
        if (btn>=0){
           /* $(this).find("span").css("color", "white");
            $(this).css({"background-color":"#499ef2", "background-position":"0px -28px","opacity":"1"});*/
        } else if(btn==-1){
            $(this).css({"background-color":"#499ef2", "background-position":"0px -28px","opacity":"1"});
            $(this).find("span").css("color", "white");
        }
    }, function () {
        var btn = $(this).parent().find("div").attr("class").toString().indexOf("in");
        if (btn>=0){
            // $(this).find("span").css("color", "white");
        } else if(btn==-1){
            $(this).css({"background-color":"#f5f5f5", "background-position":"0px -28px","opacity":"0.6"});
            $(this).find("span").css("color", "#999");
        }
    });


    $(".comment_open").click(function(){
        var btn = $(this);
        var clazz = btn.parent().find("div").attr("class").toString().indexOf("in");
        var targetDIV = btn.parent().find("div");
        if (clazz>=0) { //获取某个子串在父串中出现的下标，不存在则返回-1
            targetDIV.removeClass("in");
            btn.css({"background-color":"#f5f5f5","opacity":"0.6"});
            btn.find("span").css("color","#999");
        }else if(clazz==-1){
            targetDIV.addClass("in");
            btn.css({"background-color":"#499ef2","opacity":"1"});
            btn.find("span").css({"color":"white"});
        }

    });


});



function gotoLogin(){
    $("#msg").empty();
    window.open("https://github.com/login/oauth/authorize?client_id=89cceb7e8f19ce08fdea&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
    window.localStorage.setItem("closable","true");
}

function sendPost(){
    $("#msg").empty();
    var questionId = $("#question_id").val();
    var questionContent = $.trim($("#question_content").val());

    if (questionContent==""){
        var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
            "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"+
            "<span aria-hidden='true'>&times;</span>"+
            "</button>"+
            "<strong>错误：</strong>"+"评论内容不能为空!"+
            "</div>";
        $("#msg").append(errormsg);
        return;
    }
    $.ajax({
        url:"/comment",
        contentType:"application/json",  //不写报错
        dataType: "json",
        type: "post",
        data:JSON.stringify({
            "parentId": questionId,
            "content":questionContent,
            "type":1
        }),
        success:function(data){
            if (data.code==200){
                var errormsg = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                    "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"+
                    "<span aria-hidden='true'>&times;</span>"+
                    "</button>"+
                    "<strong>恭喜：</strong>您的评论发布成功"+
                    "</div>";
                $("#msg").append(errormsg);
                window.location.reload();
            } else{
                if (data.code==1001){
                    var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                        "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"+
                        "<span aria-hidden='true'>&times;</span>"+
                        "</button>"+
                        "<strong>错误：</strong>"+data.message+"&nbsp;&nbsp;&nbsp;<strong><a style='text-decoration:none;' href='javascript:gotoLogin();'>点击登陆</a></strong>"+
                        "</div>";
                    $("#msg").append(errormsg);
                }else{
                    var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                        "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"+
                        "<span aria-hidden='true'>&times;</span>"+
                        "</button>"+
                        "<strong>错误：</strong>"+data.message+
                        "</div>";
                    $("#msg").append(errormsg);
                }

            }
        }
    });
}

/*

function openSecondLevelComment(tag){
    // alert("a");
    // $(".comment_open").click(function(){
    // $(this).closest("div").find("div").addClass("in");//当前标签的指定祖先标签的指定子标签
    var name = $(tag).tagName;
    alert(name);
    // alert($(this).attr("class"));
    // });
}*/
