
$(function(){
        $("#li_dropdown").hover(function(){
            $("#li_dropdown").attr("class", "dropdown open");
        },function(){
            $("#li_dropdown").attr("class", "dropdown");
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