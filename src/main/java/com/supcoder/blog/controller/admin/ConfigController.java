package com.supcoder.blog.controller.admin;

import com.github.pagehelper.Page;
import com.supcoder.blog.controller.BaseController;
import com.supcoder.blog.model.domain.Log;
import com.supcoder.blog.model.dto.Pagination;
import com.supcoder.blog.model.dto.SiteConfig;
import com.supcoder.blog.service.ConfigService;
import com.supcoder.blog.service.LogService;
import com.supcoder.blog.util.FameConsts;
import com.supcoder.blog.util.JsonResult;
import com.supcoder.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 网站设置 Controller
 *
 * @author zbw
 * @since 2017/10/12 20:27
 */
@RestController
@RequestMapping("/api/admin/config")
public class ConfigController extends BaseController {

    @Autowired
    private LogService logsService;

    @Autowired
    private ConfigService configService;

    /**
     * 获取日志列表
     *
     * @param page  第几页
     * @param limit 每页数量
     * @return {@see Pagination<Log>}
     */
    @GetMapping("logs")
    public JsonResult getLogs(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = FameConsts.PAGE_SIZE) Integer limit) {
        Page<Log> logs = logsService.getLogs(page, limit);
        return ResultUtil.success(new Pagination<Log>(logs));
    }

    /**
     * 获取网站设置缓存
     *
     * @return {@see SiteConfig}
     */
    @GetMapping("site")
    public JsonResult getSiteConfig() {
        return ResultUtil.success(configService.getSiteConfig());
    }

    /**
     * 保存网站设置缓存
     *
     * @param title         网页title
     * @param description   网页description
     * @param keywords      网页keywords
     * @param emailSend     是否发送邮件提示
     * @param emailHost     邮箱Host
     * @param emailPort     邮箱Port
     * @param emailUsername 邮箱用户名
     * @param emailPassword 邮箱密码
     * @return {@see ResultUtil.success()}
     */
    @PostMapping("site")
    public JsonResult saveSiteConfig(@RequestParam String title, @RequestParam String description,
                                       @RequestParam String keywords, @RequestParam Boolean emailSend,
                                       @RequestParam(required = false) String emailHost, @RequestParam(required = false) Integer emailPort,
                                       @RequestParam(required = false) String emailUsername, @RequestParam(required = false) String emailPassword) {
        SiteConfig config = SiteConfig.builder()
                .title(title)
                .description(description)
                .keywords(keywords)
                .emailSend(emailSend)
                .build();
        if (emailSend) {
            config.setEmailHost(emailHost);
            config.setEmailPort(emailPort);
            config.setEmailUsername(emailUsername);
            config.setEmailPassword(emailPassword);
        }
        configService.saveSiteConfig(config);
        return ResultUtil.success();
    }


}
