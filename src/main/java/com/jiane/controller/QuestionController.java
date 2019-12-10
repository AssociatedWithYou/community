package com.jiane.controller;

import com.jiane.dto.CommentListDTO;
import com.jiane.dto.ImgUploadDTO;
import com.jiane.dto.QuestionDTO;
import com.jiane.enums.CommentTypeEnum;
import com.jiane.mapper.NotificationMapper;
import com.jiane.mapper.UserMapper;
import com.jiane.model.Question;
import com.jiane.model.User;
import com.jiane.service.CommentService;
import com.jiane.service.QuestionService;
import com.jiane.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    NotificationMapper notificationMapper;

    @GetMapping("/questions/{id}")
    public String gotoQuestion(@PathVariable("id") Integer id, Model model, HttpSession session) {
        System.out.println(id);

        QuestionDTO questionDto = questionService.findQuestionById(id);
        User userById = userMapper.findUserById(questionDto.getCreator());

        User sesssion_user = (User) session.getAttribute("user");

        if ((int) sesssion_user.getId() != (int) userById.getId()) {
            questionService.updateViewCount();
        }

        questionDto.setUser(userById);
        model.addAttribute("question", questionDto);

        List<CommentListDTO> commentListDTOS = commentService.findAllParentCommentsByQuestionIdAndType(questionDto.getId(), CommentTypeEnum.QUESTION.getType());

        Set<Integer> set = new HashSet<>();

        for (CommentListDTO commentListDTO : commentListDTOS) {
            set.add(commentListDTO.getCommentator());
        }

        for (Integer integer : set) {
            System.out.println(integer);
        }

        for (Integer aInteger : set) {
            User user = userService.findUserByCommentator(aInteger);
            System.out.println("for:" + user);
            for (int i = 0; i < commentListDTOS.size(); i++) {
                if (commentListDTOS.get(i).getCommentator().toString().equals(user.getId().toString())) {
                    commentListDTOS.get(i).setUser(user);
                }
            }
        }

        for (int i = 0; i < commentListDTOS.size(); i++) {
            System.out.println(commentListDTOS.get(i));
        }

        /*问题页面的相关问题*/
        String[] split = questionDto.getTags().split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            if (i == split.length - 1) {
                String s1 = null;
                if (split[i].length() > 3) {
                    s1 = split[i].substring(1, split[i].length() - 2);
                } else {
                    s1 = split[i];
                }
                sb.append(s1);
                break;
            }
            String s2 = null;
            if (split[i].length() > 3) {
                s2 = split[i].substring(1, split[i].length() - 2);
                System.out.println(s2);
                sb.append(split[i]).append("|");
            } else {
                s2 = split[i];
                System.out.println(s2);
                sb.append(split[i]).append("|");
            }


        }
        String s = new StringBuffer().toString();
        System.out.println("final_sb:" + sb);
        List<Question> questions = questionService.findRelatedQuestionByTags(questionDto.getId(), s);

        model.addAttribute("questionsByTags", questions);
        model.addAttribute("comments", commentListDTOS);

        return "question";
    }


    /*问题发布时的文件上传*/
    @RequestMapping("/question/imgUpload")
    @ResponseBody
    public ImgUploadDTO imgUpload(HttpServletRequest request, MultipartFile multipartFile) {
       /* ImgUploadDTO imgUploadDTO = new ImgUploadDTO();
        String realPath = request.getServletContext().getRealPath("/questionImages") + "/";
        System.out.println("realPath:" + realPath);
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String suffix = multipartFile.getContentType().split(".")[1];//文件后缀

        String oldFileName = multipartFile.getOriginalFilename();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMDDhhmmss");
        String yyyyMMDDhhmmss = simpleDateFormat.format(new Date());
        String newFileName = realPath + yyyyMMDDhhmmss + "_" + oldFileName+suffix;

        File imgFile = new File(newFileName);
        try {
            multipartFile.transferTo(imgFile);
            imgUploadDTO.setSuccess(1);
            imgUploadDTO.setMessage("文件上传成功!");
            imgUploadDTO.setUrl("http://localhost:8080/questionImages/"+yyyyMMDDhhmmss + "_" + oldFileName+suffix);
        } catch (Exception e) {
            imgUploadDTO.setSuccess(0);
            imgUploadDTO.setMessage("文件上传失败啦!");

        }*/
        ImgUploadDTO imgUploadDTO = new ImgUploadDTO();
        imgUploadDTO.setSuccess(1);
        imgUploadDTO.setMessage("文件上传成功!");
        imgUploadDTO.setUrl("http://localhost:8080/img/a.png");
        return imgUploadDTO;
    }


}
