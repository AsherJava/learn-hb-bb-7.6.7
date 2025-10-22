/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.pinyin.service.impl;

import com.jiuqi.nr.pinyin.service.IPinyinCacheService;
import com.jiuqi.nr.pinyin.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PinyinCacheServiceImpl
implements IPinyinCacheService {
    @Autowired
    CacheUtil cacheUtil;

    @Override
    public void initCache() {
    }
}

