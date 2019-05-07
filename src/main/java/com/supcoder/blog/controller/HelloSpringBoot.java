package com.supcoder.blog.controller;

import com.supcoder.blog.exception.ErrorCodeEnum;
import com.supcoder.blog.exception.TipException;
import com.supcoder.blog.util.EmptyUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lee
 */
@RestController
public class HelloSpringBoot {

    @RequestMapping(value = "/hello" ,method = RequestMethod.GET)
    public String say(@RequestParam(value = "word" ,defaultValue = "toly1994") String param){
        if (EmptyUtil.isEmpty(param)){
            throw new TipException(ErrorCodeEnum.PARAM_VALID_ERROR);
        }
        return "Hello World!";
    }


}
