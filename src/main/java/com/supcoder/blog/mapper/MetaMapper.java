package com.supcoder.blog.mapper;

import com.supcoder.blog.model.domain.Meta;
import com.supcoder.blog.model.dto.MetaDto;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Meta Mapper
 *
 * @author zbw
 * @since 2017/8/28 23:40
 */
public interface MetaMapper extends Mapper<Meta> {

    /**
     * 根据文章id获取该文章下的属性
     *
     * @param articleId 文章id
     * @param type      属性类型
     * @return List<Meta>
     */
    @Select("SELECT * FROM fame.meta WHERE type = #{type} AND id IN (SELECT m_id FROM fame.middle WHERE a_id = #{articleId})")
    List<Meta> selectByArticle(@Param("articleId") Integer articleId, @Param("type") String type);

    /**
     * 获取属性以及属性下的文章
     *
     * @param type 属性类型
     * @return List<MetaDto>
     */
    @Select("select * from fame.meta meta where meta.type = #{type}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "type", property = "type"),
            @Result(column = "id", property = "count",
                    one = @One(select = "com.supcoder.blog.mapper.ArticleMapper.selectCountByMeta"))
    })
    List<MetaDto> selectMetaDtos(@Param("type") String type);

    /**
     * 获取属性以及属性下的已发布文章
     *
     * @param type 属性类型
     * @return List<MetaDto>
     */
    @Select("select * from fame.meta meta where meta.type = #{type}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "type", property = "type"),
            @Result(column = "id", property = "count",
                    one = @One(select = "com.supcoder.blog.mapper.ArticleMapper.selectPublishCountByMeta")),
            @Result(column = "id", property = "articles",
                    many = @Many(select = "com.supcoder.blog.mapper.ArticleMapper.selectPublishByMeta"))
    })
    List<MetaDto> selectPublishMetaDtos(@Param("type") String type);
}
