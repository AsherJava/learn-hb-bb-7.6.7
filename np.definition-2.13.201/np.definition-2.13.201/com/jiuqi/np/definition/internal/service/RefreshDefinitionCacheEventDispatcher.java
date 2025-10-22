/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.service;

import com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class RefreshDefinitionCacheEventDispatcher
implements ApplicationListener<RefreshDefinitionCacheEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshDefinitionCacheEvent.class);
    @Autowired(required=false)
    private List<RuntimeDefinitionRefreshListener> listeners;

    RefreshDefinitionCacheEventDispatcher() {
    }

    @Override
    public void onApplicationEvent(RefreshDefinitionCacheEvent event) {
        this.nofifyRefresh();
    }

    private void nofifyRefresh() {
        if (this.listeners != null) {
            for (RuntimeDefinitionRefreshListener listener : this.listeners) {
                try {
                    listener.onClearCache();
                }
                catch (Exception e) {
                    LOGGER.error("\u54cd\u5e94\u53c2\u6570\u5237\u65b0\u4e8b\u4ef6\u5931\u8d25\u3002\u4e8b\u4ef6\uff1a\u53c2\u6570\u53d1\u5e03\uff0c\u76d1\u542c\u5668\uff1a" + listener.getClass().getName(), e);
                }
            }
        }
    }
}

