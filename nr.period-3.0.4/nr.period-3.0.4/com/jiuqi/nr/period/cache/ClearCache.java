/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.cache;

import com.jiuqi.nr.period.cache.PeriodDataRowCache;
import com.jiuqi.nr.period.cache.PeriodEntityCache;
import com.jiuqi.nr.period.cache.PeriodLanguageCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClearCache {
    @Autowired
    private PeriodEntityCache periodEntityCache;
    @Autowired
    private PeriodDataRowCache periodDataRowCache;
    @Autowired
    private PeriodLanguageCache periodLanguageCache;

    public void clearCache() {
        this.periodDataRowCache.clearCache();
        this.periodEntityCache.clearCache();
        this.periodLanguageCache.clearCache();
    }
}

