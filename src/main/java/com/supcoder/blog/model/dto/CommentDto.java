package com.supcoder.blog.model.dto;


import com.supcoder.blog.model.domain.Article;
import com.supcoder.blog.model.domain.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 评论 Dto
 *
 * @author zbw
 * @since 2018/1/21 16:08
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CommentDto extends Comment {

    /**
     * 评论文章
     */
    private Article article;

    /**
     * 父评论
     */
    private Comment pComment;
}
