package com.jiane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {


    @GetMapping("/goToSearch")
    public String goToSearch(Model model, Integer currentPage , Integer record , String search){
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("record", record);
        model.addAttribute("search", search);
        return "search";
    }
}
