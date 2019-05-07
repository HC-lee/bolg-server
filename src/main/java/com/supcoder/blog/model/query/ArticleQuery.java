package com.supcoder.blog.model.query;

import lombok.Data;
import lombok.ToString;

/**
 * 文章查询条件
 *
 * @author zbw
 * @since 2019/5/1 12:33
 */
@Data
@ToString
public class ArticleQuery {

    private String title;

    private String status;

    private String tag;

    private String category;

}
