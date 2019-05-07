package com.supcoder.blog.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * SwordService
 *
 * @author lee
 * @date 2019/1/4
 */
public interface SwordService {

    @Transactional
    public void insertTwo();
}
