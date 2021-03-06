package com.supcoder.blog.controller;

import com.github.pagehelper.Page;
import com.supcoder.blog.model.domain.Article;
import com.supcoder.blog.model.domain.Comment;
import com.supcoder.blog.model.dto.Archive;
import com.supcoder.blog.model.dto.CommentDto;
import com.supcoder.blog.model.dto.MetaDto;
import com.supcoder.blog.model.dto.Pagination;
import com.supcoder.blog.service.ArticleService;
import com.supcoder.blog.service.CommentService;
import com.supcoder.blog.service.EmailService;
import com.supcoder.blog.service.MetaService;
import com.supcoder.blog.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客前台 Controller
 *
 * @author zbw
 * @since 2017/7/15 18:29
 */
@RestController
@RequestMapping("/api")
public class FrontController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MetaService metaService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EmailService emailService;

    /**
     * 文章列表
     *
     * @param page  第几页
     * @param limit 每页数量
     * @return {@see Pagination<Article>}
     */
    @GetMapping("article")
    public JsonResult home(@RequestParam(required = false, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = FameConsts.PAGE_SIZE) Integer limit) {
        Page<Article> articles = articleService.getFrontArticles(page, limit);
        return ResultUtil.success(new Pagination<Article>(articles));
    }

    /**
     * 文章内容页
     *
     * @param id 文章id
     * @return {@see Article}
     */
    @GetMapping("article/{id}")
    public JsonResult article(@PathVariable Integer id) {
        Article article = articleService.getFrontArticle(id);
        if (null == article) {
            return this.error404();
        }
        this.updateHits(article.getId(), article.getHits());
        return ResultUtil.success(article);
    }

    /**
     * 点击量添加
     *
     * @param articleId 文章id
     * @param hits      当前点击量
     */
    private void updateHits(Integer articleId, Integer hits) {
        Integer cHits = cacheUtil.getCacheValue(FameConsts.CACHE_ARTICLE_HITS, articleId, Integer.class);
        cHits = null == cHits ? 1 : cHits + 1;
        if (cHits >= FameConsts.CACHE_ARTICLE_HITS_SAVE) {
            Article temp = new Article();
            temp.setId(articleId);
            temp.setHits(hits + cHits);
            articleService.updateArticle(temp);
            cacheUtil.putCacheValue(FameConsts.CACHE_ARTICLE_HITS, articleId, 0);
        } else {
            cacheUtil.putCacheValue(FameConsts.CACHE_ARTICLE_HITS, articleId, cHits);
        }
    }


    /**
     * 标签页
     *
     * @return {@see List<MetaDto>}
     */
    @GetMapping("tag")
    public JsonResult tag() {
        List<MetaDto> metaDtos = metaService.getPublishMetaDtos(Types.TAG);
        return ResultUtil.success(metaDtos);
    }

    /**
     * 分类页
     *
     * @return {@see List<MetaDto>}
     */
    @GetMapping("/category")
    public JsonResult category() {
        List<MetaDto> metaDtos = metaService.getPublishMetaDtos(Types.CATEGORY);
        return ResultUtil.success(metaDtos);
    }

    /**
     * 归档页
     *
     * @return {@see List<Archive>}
     */
    @GetMapping("archive")
    public JsonResult archive() {
        List<Archive> archives = articleService.getArchives();
        return ResultUtil.success(archives);
    }

    /**
     * 自定义页面
     *
     * @param title 页面标题
     * @return {@see Article}
     */
    @GetMapping("page/{title}")
    public JsonResult page(@PathVariable String title) {
        Article page = articleService.getFrontPage(title);
        if (null == page) {
            return error404();
        }
        return ResultUtil.success(page);
    }

    /**
     * 获取文章的评论
     *
     * @param articleId 文章id
     * @param page      第几页
     * @param limit     每页数量
     * @return {@see Pagination<Comment>}
     */
    @GetMapping("comment")
    public JsonResult getArticleComment(@RequestParam Integer articleId, @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false, defaultValue = FameConsts.PAGE_SIZE) Integer limit) {
        Page<Comment> comments = commentService.getCommentsByArticleId(page, limit, articleId);
        return ResultUtil.success(new Pagination<Comment>(comments));
    }


    /**
     * 发表评论
     *
     * @param articleId 文章id
     * @param pId       父评论id
     * @param content   评论内容
     * @param name      评论用户名
     * @param email     评论用户email
     * @param website   评论用户网址
     * @return {@see ResultUtil.success()}
     */
    @PostMapping("comment")
    public JsonResult postComment(@RequestParam Integer articleId, @RequestParam(required = false) Integer pId,
                                    @RequestParam String content, @RequestParam String name,
                                    @RequestParam(required = false) String email, @RequestParam(required = false) String website) {
        Comment comments = new Comment();
        comments.setArticleId(articleId);
        comments.setPId(pId);
        comments.setContent(content);
        comments.setName(name);
        comments.setEmail(email);
        comments.setWebsite(website);
        comments.setIp(FameUtil.getIp());
        comments.setAgent(FameUtil.getAgent());
        commentService.save(comments);

        //发送邮件提醒
        CommentDto commentDetail = commentService.getCommentDetail(comments.getId());
        emailService.sendEmailToAdmin(commentDetail);
        if (null != commentDetail.getPComment() && !StringUtils.isEmpty(commentDetail.getPComment().getEmail())) {
            emailService.sendEmailToUser(commentDetail, commentDetail.getPComment().getEmail());
        }
        return ResultUtil.success();
    }

    /**
     * 顶或踩评论
     *
     * @param commentId 评论id
     * @param assess    {@link Types#AGREE},{@link Types#DISAGREE}
     * @return {@see ResultUtil.success()}
     */
    @PostMapping("comment/{commentId}/assess")
    public JsonResult assessComment(@PathVariable Integer commentId, @RequestParam String assess) {
        commentService.assessComment(commentId, assess);
        return ResultUtil.success();
    }
}
