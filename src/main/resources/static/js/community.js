$(function () {


    $("#li_dropdown").hover(function () {
        $("#li_dropdown").attr("class", "dropdown open");
    }, function () {
        $("#li_dropdown").attr("class", "dropdown");
    });



    $(".comment_btn").hover(function () {
        var btn = $(this).parent().find("div").attr("class").toString().indexOf("in");
        if (btn >= 0) {
            /* $(this).find("span").css("color", "white");
             $(this).css({"background-color":"#499ef2", "background-position":"0px -28px","opacity":"1"});*/
        } else if (btn == -1) {
            $(this).css({"background-color": "#499ef2", "background-position": "0px -28px", "opacity": "1"});
            $(this).find("span").css("color", "white");
        }
    }, function () {
        var btn = $(this).parent().find("div").attr("class").toString().indexOf("in");
        if (btn >= 0) {
            // $(this).find("span").css("color", "white");
        } else if (btn == -1) {
            $(this).css({"background-color": "#f5f5f5", "background-position": "0px -28px", "opacity": "0.6"});
            $(this).find("span").css("color", "#999");
        }
    });

    $(".comment_btn_color").hover(function () {
            $(this).css({"background-color": "#499ef2", "background-position": "0px -28px", "opacity": "1"});
            $(this).find("span").css("color", "white");
    }, function () {
            $(this).css({"background-color": "#f5f5f5", "background-position": "0px -28px", "opacity": "0.6"});
            $(this).find("span").css("color", "#999");
    });

    /*展开子集评论*/
    $(".comment_open").click(function () {
        var btn = $(this);
        var li = $(this).parent().find("div"); //整个评论列表
        if (li.attr("class").toString().indexOf("in") == -1) {//等于-1,表示没有in
            var firstLevelCommentId = $(this).parent().find("input").val();//一级评论的id
            // alert("firstLevelCommentId:" + firstLevelCommentId);
            li.empty();
            $.ajax({
                url: "/comment/" + firstLevelCommentId,
                type: "get",
                dataType: "json",
                error: function (data) {
                    alert("error");
                },
                success: function (data) {
                    if (data.data.length != 0) {

                        //将数据进行倒叙输出
                        for (var i = data.data.length-1 ; i >=0; i --){
                            var tags = ' <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px;margin-bottom:6px;">\n' +
                                '                                    <div class="media" style="margin-top: 20px;border-bottom: solid 1px #eee;">\n' +
                                '                                        <div class="media-left pull-left">\n' +
                                '                                            <a href="#">\n' +
                                '                                                <img width="40px" height="40px" class="media-object" src="'+data.data[i].user.avatarUrl+'">\n' +
                                '                                            </a>\n' +
                                '                                        </div>\n' +
                                '                                        <div class="media-body">\n' +
                                '                                            <h4 class="media-heading">\n' +
                                '                                                <span>'+data.data[i].user.name+'</span>\n' +
                                '                                            </h4>\n' +
                                '                                            <span>'+data.data[i].content+'</span>\n' +
                                '\n' +
                                '                                            <br/>\n' +
                                '                                            <span class="pull-right" style="color: #999;" \\n>'+new Date(data.data[i].gmtCreate).Format('yy-MM-dd')+'</span>\n' +
                                '\n' +
                                '                                        </div>\n' +
                                '                                    </div>\n' +
                                '                                </div>';
                            li.append(tags);
                        }
                    }

                    var endWithTags = '<div>\n' +
                        '                                    <!--二级回复框-->\n' +
                        '                                    <input type="text" style="margin-top:8px;" id="secondCommentContent" class="form-control"\n' +
                        '                                           placeholder="评论一下...">\n' +
                        '\n' +
                        '                                    <div style="margin-top: 6px;margin-bottom: 5px;">\n' +
                        '\n' +
                        '                                        <!--取消按钮-->\n' +
                        '                                        <button onclick="btn_close(this);" type="button"\n' +
                        '                                                style="float: right;margin-bottom:7px;outline: none;" class="btn btn-default">取消\n' +
                        '                                        </button>\n' +
                        '                                        <!--回复按钮-->\n' +
                        '                                        <button type="button" style="float: right;margin-right: 10px;outline: none;"\n' +
                        '                                                class="btn btn-success" onclick="sendSecondLevelComment(this);">回复\n' +
                        '                                        </button>\n' +
                        '                                        <input type="hidden" id="secondLevelCommentId" value="\'+data.data[i].parentId+\'">\n' +
                        '\n' +
                        '                                    </div>\n' +
                        '\n' +
                        '                                    <div id="msg_second" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' +
                        '\n' +
                        '                                    </div>\n' +
                        '\n' +
                        '                                </div>';

                    li.append(endWithTags);
                }
            });


           /* btn.css({"background-color":"#499ef2","opacity":"1"});
            btn.find("span").css({"color":"white"});*/
            li.addClass("in");
        } else {
           /* btn.css({"background-color":"#999","opacity":"0.6"});
            btn.find("span").css({"color":"#eee"});*/
            li.removeClass("in");
        }
    });



});

    /*评论回复中的取消按钮*/
    function btn_close(t) {
        var commentbtn = $(t).parent().parent().parent().parent().find("button").next();
        commentbtn.css({"background-color": "#f5f5f5", "background-position": "0px -28px", "opacity": "0.6"});
        commentbtn.find("span").css("color", "#999");
        $(t).parent().parent().parent().removeClass("in");
        $(t).parent().parent().parent().empty();


    };


    /*时间戳转时间格式*/
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, // 月份
            "d+": this.getDate(), // 日
            "h+": this.getHours(), // 小时
            "m+": this.getMinutes(), // 分
            "s+": this.getSeconds(), // 秒
            "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
            "S": this.getMilliseconds() // 毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + ""));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }


    function gotoLogin() {
        $("#msg").empty();
        window.open("https://github.com/login/oauth/authorize?client_id=89cceb7e8f19ce08fdea&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
        window.localStorage.setItem("closable", "true");
    }


    function sendPost() {
        $("#msg").empty();
        var questionId = $("#question_id").val();
        var questionContent = $.trim($("#question_content").val());

        if (questionContent == "") {
            var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true'>&times;</span>" +
                "</button>" +
                "<strong>错误：</strong>" + "评论内容不能为空!" +
                "</div>";
            $("#msg").append(errormsg);
            return;
        }
        $.ajax({
            url: "/comment",
            contentType: "application/json",  //不写报错
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                "parentId": questionId,
                "content": questionContent,
                "type": 1
            }),
            success: function (data) {
                if (data.code == 200) {
                    var errormsg = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                        "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                        "<span aria-hidden='true'>&times;</span>" +
                        "</button>" +
                        "<strong>恭喜：</strong>您的评论发布成功" +
                        "</div>";
                    $("#msg").append(errormsg);
                    window.location.reload();
                } else {
                    if (data.code == 1001) {
                        var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                            "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                            "<span aria-hidden='true'>&times;</span>" +
                            "</button>" +
                            "<strong>错误：</strong>" + data.message + "&nbsp;&nbsp;&nbsp;<strong><a style='text-decoration:none;' href='javascript:gotoLogin();'>点击登陆</a></strong>" +
                            "</div>";
                        $("#msg").append(errormsg);
                    } else {
                        var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                            "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                            "<span aria-hidden='true'>&times;</span>" +
                            "</button>" +
                            "<strong>错误：</strong>" + data.message +
                            "</div>";
                        $("#msg").append(errormsg);
                    }

                }
            }
        });
    }


    function sendSecondLevelComment(tt) {

        var commentId = $(tt).parent().parent().parent().parent().find("input").val();
        var questionContent = $.trim($(tt).parent().parent().find("input").val());
        if (questionContent == "") {
            $(tt).parent().next().empty();
            var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                "<span aria-hidden='true'>&times;</span>" +
                "</button>" +
                "<strong>错误：</strong>" + "评论内容不能为空!" +
                "</div>";
            $(tt).parent().next().append(errormsg);
            return;
        }
        $.ajax({
            url: "/comment",
            contentType: "application/json",  //不写报错
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                "parentId": commentId,
                "content": questionContent,
                "type": 2
            }),
            success: function (data) {
                if (data.code == 200) {
                    var errormsg = "<div class='alert alert-success alert-dismissible' role='alert'>" +
                        "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                        "<span aria-hidden='true'>&times;</span>" +
                        "</button>" +
                        "<strong>恭喜：</strong>您的评论发布成功" +
                        "</div>";
                    $(tt).parent().next().append(errormsg);
                    window.location.reload();
                } else {
                    if (data.code == 1001) {
                        var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                            "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                            "<span aria-hidden='true'>&times;</span>" +
                            "</button>" +
                            "<strong>错误：</strong>" + data.message + "&nbsp;&nbsp;&nbsp;<strong><a style='text-decoration:none;' href='javascript:gotoLogin();'>点击登陆</a></strong>" +
                            "</div>";
                        $(tt).parent().next().append(errormsg);
                    } else {
                        var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                            "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                            "<span aria-hidden='true'>&times;</span>" +
                            "</button>" +
                            "<strong>错误：</strong>" + data.message +
                            "</div>";
                        $(tt).parent().next().append(errormsg);
                    }

                }
            }
        });
    }
