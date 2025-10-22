/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckResultTimeSpan;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExplainInfoCheckResultCache {
    private static final String NAME = "REMOTE_NR";
    private static final String CACHENAME = "EXPLAININFOCHECK";
    private Map<String, ExplainInfoCheckResultTimeSpan> resultCache = new Hashtable<String, ExplainInfoCheckResultTimeSpan>();
    private NedisCacheManager cacheManager;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager(NAME);
    }

    private void checkCache() {
        Iterator<Map.Entry<String, ExplainInfoCheckResultTimeSpan>> it = this.resultCache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ExplainInfoCheckResultTimeSpan> entry = it.next();
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(entry.getValue().getCheckDate());
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(new Date());
            int day1 = cal1.get(5);
            int day2 = cal2.get(5);
            if (day2 - day1 < 3) continue;
            it.remove();
        }
    }

    public void addResult(String asynTaskID, List<ExplainInfoCheckResultItem> items) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        if (cache != null) {
            ExplainInfoCheckResultTimeSpan r = (ExplainInfoCheckResultTimeSpan)cache.get(asynTaskID, ExplainInfoCheckResultTimeSpan.class);
            if (r != null) {
                r.getResult().clear();
            } else {
                r = new ExplainInfoCheckResultTimeSpan();
                r.setResult(items);
                cache.put(asynTaskID, (Object)r);
            }
        } else {
            ExplainInfoCheckResultTimeSpan r = new ExplainInfoCheckResultTimeSpan();
            r.setResult(items);
            cache.put(asynTaskID, (Object)r);
        }
    }

    public List<ExplainInfoCheckResultItem> getResultItemsByasynTaskID(String asynTaskID) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        if (cache != null) {
            ExplainInfoCheckResultTimeSpan r = (ExplainInfoCheckResultTimeSpan)cache.get(asynTaskID, ExplainInfoCheckResultTimeSpan.class);
            if (r != null) {
                return r.getResult();
            }
            return new ArrayList<ExplainInfoCheckResultItem>();
        }
        return new ArrayList<ExplainInfoCheckResultItem>();
    }
}

