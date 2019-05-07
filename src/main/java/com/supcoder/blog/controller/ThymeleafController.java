package com.supcoder.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 注意由于是RequestBody 所以这里将@RestController拆分出来了
 * @author lee
 */
@Controller
public class ThymeleafController {

    @GetMapping("/index")
    public ModelAndView say() {
        return new ModelAndView("/index");
    }

    @GetMapping("/useData")
    public String useData(ModelMap map) {
        map.addAttribute("replace_text", "张风捷特烈");
        return "/index";
    }

    @GetMapping("/uploadFile")
    public String uploadFile() {
        return "/uploadFile";
    }

    @GetMapping("/images")
    public String images() {
        return "/images";
    }

}

