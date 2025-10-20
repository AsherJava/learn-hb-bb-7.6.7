/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.event.BaseDataEvent
 */
package com.jiuqi.common.subject.impl.subject.listener;

import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.common.subject.impl.subject.service.impl.SubjectCacheProvider;
import com.jiuqi.va.event.BaseDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SubjectBaseDataListener
implements ApplicationListener<BaseDataEvent> {
    private Logger logger = LoggerFactory.getLogger(SubjectBaseDataListener.class);
    @Autowired
    private SubjectService service;
    @Autowired
    private SubjectCacheProvider cacheProvider;

    @Override
    public void onApplicationEvent(BaseDataEvent event) {
        if (!event.getBaseDataDTO().getTableName().equals("MD_ACCTSUBJECT")) {
            return;
        }
        try {
            if (!this.service.list().isEmpty()) {
                this.cacheProvider.cleanCache();
            }
        }
        catch (Exception e) {
            this.logger.error("\u79d1\u76ee\u57fa\u7840\u6570\u636e\u540c\u6b65\u79d1\u76ee\u7ba1\u7406\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }
}

