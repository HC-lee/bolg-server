package com.supcoder.blog.model.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 日志 Model
 *
 * @author zbw
 * @since 2017/10/11 9:57
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class Log extends BaseEntity {

    /**
     * 操作动作
     */
    private String action;

    /**
     * 操作数据
     */
    private String data;

    /**
     * 操作信息
     */
    private String message;

    /**
     * 操作类型
     */
    private String type;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 操作人
     */
    private Integer userId;

    /**
     * 操作时间
     */
    private Date created;
}
