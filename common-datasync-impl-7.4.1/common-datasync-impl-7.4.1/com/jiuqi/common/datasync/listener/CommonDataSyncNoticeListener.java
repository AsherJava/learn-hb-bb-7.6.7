/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.config.JoinApiConfig
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 */
package com.jiuqi.common.datasync.listener;

import com.jiuqi.common.datasync.producer.CommonDataSyncMessageProducer;
import com.jiuqi.va.join.api.config.JoinApiConfig;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CommonDataSyncNoticeListener
implements ApplicationListener<StorageSyncFinishedEvent> {
    @Autowired
    private CommonDataSyncMessageProducer producer;

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        if (!StringUtils.hasText(JoinApiConfig.getPrimaryTemplate())) {
            return;
        }
        this.producer.publishCommonDataSyncMqTopicNoticeMessageDTO();
    }
}

