/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheClearEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent
 */
package com.jiuqi.gcreport.org.impl.cache.listener;

import com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheClearEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgCacheManage;

public class GcOrgCacheMessageSubscriber {
    public void onMessage(GcOrgCacheManage manager, GcOrgDataCacheBaseEvent message) {
        if (message instanceof GcOrgDataAuthzChangeEvent) {
            manager.clearUserCache((GcOrgDataAuthzChangeEvent)message);
        } else if (message instanceof GcOrgDataItemChangeEvent) {
            manager.clearOneCache((GcOrgDataItemChangeEvent)message);
        } else if (message instanceof GcOrgDataCacheClearEvent) {
            manager.clearAllCache((GcOrgDataCacheClearEvent)message);
        }
    }

    public void onTypeVersionMessage(GcOrgCacheManage manager, GcOrgBaseEvent<?> message) {
        manager.clearTVCache(message);
    }
}

