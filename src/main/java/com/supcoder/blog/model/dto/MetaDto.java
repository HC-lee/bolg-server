package com.supcoder.blog.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.supcoder.blog.model.domain.Meta;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 属性 Dto
 *
 * @author zbw
 * @since 2017/8/30 15:15
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MetaDto extends Meta {

    private Integer count;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ArticleInfoDto> articles;
}
