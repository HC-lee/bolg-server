package com.supcoder.blog.mapper;

import com.supcoder.blog.model.domain.Article;
import com.supcoder.blog.model.dto.ArticleInfoDto;
import com.supcoder.blog.util.Types;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Article Mapper
 *
 * @author zbw
 * @since 2017/7/8 10:39
 */
public interface ArticleMapper extends Mapper<Article> {

    /**
     * 根据metaId获取关联的文章
     *
     * @param metaId 属性id
     * @return List<Article>
     */
    @Select("SELECT * FROM fame.article WHERE id IN (SELECT a_id FROM fame.middle WHERE m_id = #{metaId}) " +
            "AND fame.article.type = '" + Types.POST + "' order by created desc")
    List<Article> selectByMeta(@Param("metaId") Integer metaId);

    /**
     * 根据metaId获取关联文章的数量
     *
     * @param metaId 属性id
     * @return Integer
     */
    @Select("SELECT count(*) FROM fame.article WHERE id " +
            "IN (SELECT a_id FROM fame.middle WHERE m_id = #{metaId}) " +
            "AND fame.article.type = '" + Types.POST + "' AND fame.article.status != '" + Types.DELETE + "'")
    Integer selectCountByMeta(@Param("metaId") Integer metaId);

    /**
     * 根据metaId获取关联的已发布文章
     *
     * @param metaId 属性id
     * @return List<Article>
     */
    @Select("SELECT * FROM fame.article WHERE id " +
            "IN (SELECT a_id FROM fame.middle WHERE m_id = #{metaId}) " +
            "AND fame.article.status = '" + Types.PUBLISH + "' AND fame.article.type = '" + Types.POST + "' order by created desc")
    List<ArticleInfoDto> selectPublishByMeta(@Param("metaId") Integer metaId);

    /**
     * 根据metaId获取关联已发布文章的数量
     *
     * @param metaId 属性id
     * @return Integer
     */
    @Select("SELECT count(*) FROM fame.article WHERE id " +
            "IN (SELECT a_id FROM fame.middle WHERE m_id = #{metaId}) " +
            "AND fame.article.status = '" + Types.PUBLISH + "' AND fame.article.type = '" + Types.POST + "'")
    Integer selectPublishCountByMeta(@Param("metaId") Integer metaId);
}
