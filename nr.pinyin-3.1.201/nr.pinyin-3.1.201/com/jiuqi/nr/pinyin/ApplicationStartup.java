/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.pinyin;

import com.jiuqi.nr.pinyin.service.IPinyinCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup
implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    IPinyinCacheService service;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.service.initCache();
    }
}

