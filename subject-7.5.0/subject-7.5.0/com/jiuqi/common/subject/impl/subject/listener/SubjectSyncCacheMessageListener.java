/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.common.subject.impl.subject.listener;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.subject.impl.subject.cache.intf.impl.Instance;
import com.jiuqi.common.subject.impl.subject.cache.intf.impl.SyncCacheMessage;
import com.jiuqi.common.subject.impl.subject.cache.proider.ICacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value="subjectSyncCacheMessageListener")
public class SubjectSyncCacheMessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void receiveMessage(String message, String channel) {
        SyncCacheMessage msg = (SyncCacheMessage)JsonUtils.readValue((String)message, SyncCacheMessage.class);
        if (msg == null || msg.getCacheDefine() == null) {
            this.logger.error("\u6d88\u606f\u4f53\u683c\u5f0f\u9519\u8bef\u5bfc\u81f4\u7f13\u5b58\u540c\u6b65\u5931\u8d25\uff0c\u6d88\u606f\u4f53\uff1a{}", (Object)message);
            return;
        }
        if (msg.getId().equals(Instance.getInstanceId())) {
            return;
        }
        ((ICacheProvider)SpringContextUtils.getBean(ICacheProvider.class)).cleanCache();
        this.logger.info("{}\u3010{}\u3011\u7f13\u5b58\u6e05\u9664\u6210\u529f", (Object)msg.getCacheDefine().getCode(), (Object)msg.getCacheDefine().getName());
    }
}

