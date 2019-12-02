package com.jiane.exception;

import com.alibaba.fastjson.JSON;
import com.jiane.dto.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)//精确匹配哪些异常  此处是所有异常都匹配
    public ModelAndView getExceptionPage(HttpServletRequest request , Throwable e, HttpServletResponse response){

        HttpStatus httpStatus = getStatus(request);//通过request和下面的方法到的状态码
        ModelAndView modelAndView = new ModelAndView("error");

        System.out.println("通过请求拿到的状态码："+httpStatus);
        System.out.println("通过modelAndView拿到的状态码："+modelAndView.getStatus());//这里拿到的一直是null 需要setStatus才可以
        //modelAndView.setStatus();

        if ("application/json".equals(request.getContentType())){//返回json


            ResultDTO resultDTO;
            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SERVER_ERROR);
            }
            response.setContentType("application/json;charset=UTF-8");
            try{
                PrintWriter writer = response.getWriter();
                writer.print(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (Exception e1){

            }
            return null;

        }else {//返回错误页面
            if (e instanceof CustomizeException){
                request.setAttribute("message",e.getMessage());
            }else{
                request.setAttribute("message",e.getMessage());
            }
            return modelAndView;//去error页面
        }



    }
    public HttpStatus getStatus(HttpServletRequest request) {
        Integer errorCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (errorCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR; //返回500
        }
        return HttpStatus.valueOf(errorCode);
    }
}
