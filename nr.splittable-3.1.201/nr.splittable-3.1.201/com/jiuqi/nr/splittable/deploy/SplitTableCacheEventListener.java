/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.nr.definition.deploy.DeployFinishedEvent
 *  com.jiuqi.nr.definition.deploy.DeployParams
 */
package com.jiuqi.nr.splittable.deploy;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.splittable.bean.BaseBook;
import com.jiuqi.nr.splittable.bean.BaseSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SplitTableCacheEventListener
implements ApplicationListener<DeployFinishedEvent> {
    private final Logger logger = LoggerFactory.getLogger(SplitTableCacheEventListener.class);
    private final NedisCacheManager cacheManager;

    public SplitTableCacheEventListener(NedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void onApplicationEvent(DeployFinishedEvent event) {
        NedisCache splitTableTitleCache = this.cacheManager.getCache("splittable_title");
        DeployParams deployParams = event.getDeployParams();
        DeployItem form = deployParams.getForm();
        this.logger.info("\u6b63\u5728\u6e05\u9664\u62c6\u8868\u670d\u52a1\u7f13\u5b58......");
        for (String designTimeKey : form.getDesignTimeKeys()) {
            BaseBook baseBook;
            Cache.ValueWrapper valueWrapper = splitTableTitleCache.get(designTimeKey);
            if (splitTableTitleCache.exists("B" + designTimeKey)) {
                splitTableTitleCache.evict("B" + designTimeKey);
            }
            if (valueWrapper == null || (baseBook = (BaseBook)valueWrapper.get()) == null) continue;
            splitTableTitleCache.evict(designTimeKey);
            for (BaseSheet baseSheet : baseBook.getSheetList()) {
                splitTableTitleCache.evict(baseSheet.getUuid());
            }
        }
        this.logger.info("\u6e05\u9664\u62c6\u8868\u670d\u52a1\u7f13\u5b58\u5b8c\u6bd5......");
    }
}

