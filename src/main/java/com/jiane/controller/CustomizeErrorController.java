package com.jiane.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 注：
 *  该controller的作用是：处理应用的4xx、5xx这些错误,也就是CustomizeExceptionHandler处理不到的错误
 *
 *  我们自定义的异常处理器类无法处理的异常，默认由springboot的BasicErrorController(extends AbstractErrorController[implements ErrorController] )
 *  但是我们可以写如下类 实现ErrorController，重写getErrorPath和errorHtml来自定义错误页面，达到覆盖默认的BasicErrorControlelr的目的。
 *
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = this.getStatus(request);
        if (status.is4xxClientError()){
            model.addAttribute("message", "抱歉,你的操作服务器看不太懂哦,请稍后再试!");
        }else if(status.is5xxServerError()){
            model.addAttribute("message", "抱歉,服务器太累拉,需要休息会儿,请稍后再试!");
        }
        return new ModelAndView("error");
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
