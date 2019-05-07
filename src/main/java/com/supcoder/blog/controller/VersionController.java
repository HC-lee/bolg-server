package com.supcoder.blog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * VersionController
 *
 * @author lee
 * @date 2019/1/4
 */
@RestController
@RequestMapping(value = "/version")
public class VersionController {

    /**
     * 配置文件字段使用
     */
    @Value("${versionInfo}")
    private String desc;

    @GetMapping("/getInfo")
    public String getVersionInfo(){
        return desc;
    }
}
