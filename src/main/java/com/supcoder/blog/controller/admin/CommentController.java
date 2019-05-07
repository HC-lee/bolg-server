package com.supcoder.blog.controller.admin;

import com.github.pagehelper.Page;
import com.supcoder.blog.controller.BaseController;
import com.supcoder.blog.model.domain.Comment;
import com.supcoder.blog.model.dto.CommentDto;
import com.supcoder.blog.model.dto.Pagination;
import com.supcoder.blog.service.CommentService;
import com.supcoder.blog.util.FameConsts;
import com.supcoder.blog.util.FameUtil;
import com.supcoder.blog.util.JsonResult;
import com.supcoder.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台评论管理 Controller
 *
 * @author zbw
 * @since 2018/1/21 10:47
 */
@RestController
@RequestMapping("/api/admin/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取所有评论
     *
     * @param page  第几页
     * @param limit 每页数量
     * @return {@see Pagination<Comment>}
     */
    @GetMapping
    public JsonResult index(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = FameConsts.PAGE_SIZE) Integer limit) {
        Page<Comment> comments = commentService.getAdminComments(page, limit);
        return ResultUtil.success(new Pagination<Comment>(comments));
    }

    /**
     * 获取评论详情
     *
     * @param id 评论id
     * @return {@see CommentDto}
     */
    @GetMapping("{id}")
    public JsonResult detail(@PathVariable Integer id) {
        CommentDto comment = commentService.getCommentDetail(id);
        if (null == comment) {
            return this.error404();
        }
        if (null != comment.getPComment()) {
            comment.getPComment().setContent(FameUtil.mdToHtml(comment.getPComment().getContent()));
        }
        comment.setContent(FameUtil.mdToHtml(comment.getContent()));
        return ResultUtil.success(comment);
    }

    /**
     * 删除评论
     *
     * @param id 评论id
     * @return {@see ResultUtil.success()}
     */
    @DeleteMapping("{id}")
    public JsonResult delete(@PathVariable Integer id) {
        if (commentService.deleteComment(id)) {

            return ResultUtil.success();
        } else {
            return ResultUtil.error("删除评论失败");
        }
    }

    /**
     * 获取评论数量
     *
     * @return {@see Integer}
     */
    @GetMapping("count")
    public JsonResult count() {
        return ResultUtil.success(commentService.count());
    }

}
