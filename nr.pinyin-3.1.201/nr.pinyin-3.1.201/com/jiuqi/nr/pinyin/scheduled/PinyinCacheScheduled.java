/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.pinyin.scheduled;

import com.jiuqi.nr.pinyin.service.IPinyinCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PinyinCacheScheduled {
    @Autowired
    IPinyinCacheService pinyinCacheService;

    public void initPinyinCache() {
        this.pinyinCacheService.initCache();
    }
}

