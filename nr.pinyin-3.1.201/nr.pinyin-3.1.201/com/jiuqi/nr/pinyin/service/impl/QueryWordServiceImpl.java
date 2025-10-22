/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.pinyin.service.impl;

import com.jiuqi.nr.pinyin.service.IQueryWordService;
import com.jiuqi.nr.pinyin.util.CacheUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QueryWordServiceImpl
implements IQueryWordService {
    @Autowired
    CacheUtil cacheUtil;
    private static final Logger logger = LoggerFactory.getLogger(QueryWordServiceImpl.class);

    @Override
    public List<String> queryWords(String wordKey) {
        ArrayList<String> word = new ArrayList<String>();
        word.addAll(this.cacheUtil.findLikeHanziByPinyin(wordKey));
        logger.info("queryWords");
        return word;
    }
}

