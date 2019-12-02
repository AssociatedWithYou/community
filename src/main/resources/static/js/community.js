function sendPost(){
    $("#msg").empty();
    var questionId = $("#question_id").val();
    var questionContent = $.trim($("#question_content").val());
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

            } else{
                var errormsg = "<div class='alert alert-danger alert-dismissible' role='alert'>" +
                    "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>"+
                    "<span aria-hidden='true'>&times;</span>"+
                    "</button>"+
                    "<strong>错误：</strong>"+data.message+
                    "</div>";
                $("#msg").append(errormsg);
            }
        }
    });
}