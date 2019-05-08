package com.supcoder.blog.controller.manage;


import com.github.pagehelper.Page;
import com.supcoder.blog.controller.BaseController;
import com.supcoder.blog.model.domain.Article;
import com.supcoder.blog.model.domain.User;
import com.supcoder.blog.model.dto.Pagination;
import com.supcoder.blog.model.query.ArticleQuery;
import com.supcoder.blog.service.ArticleService;
import com.supcoder.blog.service.LogService;
import com.supcoder.blog.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 后台文章管理 Controller
 *
 * @author zbw
 * @since 2017/7/11 19:52
 */
@RestController
@RequestMapping("/api/manage/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LogService logService;


    /**
     * 文章信息列表
     *
     * @param page  第几页
     * @param limit 每页数量
     * @return {@see Pagination<Article>}
     */
    @GetMapping
    public JsonResult index(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = FameConsts.PAGE_SIZE) Integer limit, ArticleQuery query) {
        Page<Article> articles = articleService.getAdminArticles(page, limit, query);
        return ResultUtil.success(new Pagination<Article>(articles));
    }

    /**
     * 单个文章信息
     *
     * @param id 文章id
     * @return {@see Article}
     */
    @GetMapping("{id}")
    public JsonResult showArticle(@PathVariable Integer id) {
        Article article = articleService.getAdminArticle(id);
        if (null == article) {
            return this.error404();
        }
        return ResultUtil.success(article);
    }

    /**
     * 新建或修改文章
     *
     * @param id           文章id
     * @param title        文章标题
     * @param content      文章内容
     * @param tags         文章标签
     * @param category     文章分类
     * @param status       {@link Types#DRAFT},{@link Types#PUBLISH}
     * @param allowComment 是否允许评论
     * @return {@see ResultUtil.success()}
     */
    @PostMapping
    public JsonResult saveArticle(@RequestParam(value = "id", required = false) Integer id,
                                    @RequestParam(value = "title") String title,
                                    @RequestParam(value = "content") String content,
                                    @RequestParam(value = "tags") String tags,
                                    @RequestParam(value = "category") String category,
                                    @RequestParam(value = "status", defaultValue = Types.DRAFT) String status,
                                    @RequestParam(value = "allowComment", defaultValue = "false") Boolean allowComment) {
        User user = this.user();
        Article article = new Article();
        if (!StringUtils.isEmpty(id)) {
            article.setId(id);
        }
        article.setTitle(title);
        article.setContent(content);
        article.setTags(tags);
        article.setCategory(category);
        article.setStatus(status);
        article.setAllowComment(allowComment);
        article.setAuthorId(user.getId());
        articleService.saveArticle(article);
        return ResultUtil.success("保存文章成功");
    }

    /**
     * 删除文章
     *
     * @param id 文章id
     * @return {@see ResultUtil.success()}
     */
    @DeleteMapping("{id}")
    public JsonResult deleteArticle(@PathVariable Integer id) {
        if (articleService.deleteArticle(id)) {
            logService.save(Types.LOG_ACTION_DELETE, "id:" + id, Types.LOG_MESSAGE_DELETE_ARTICLE, Types.LOG_TYPE_OPERATE, FameUtil.getIp());
            return ResultUtil.success("删除文章成功");
        } else {
            return ResultUtil.error();
        }
    }

    /**
     * 已发布文章数量
     *
     * @return {@see Integer}
     */
    @GetMapping("count")
    public JsonResult count() {
        return ResultUtil.success(articleService.count());
    }

}
