/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.event.BaseDataEvent
 */
package com.jiuqi.gcreport.consolidatedsystem.listener;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.cache.SubjectCache;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.event.BaseDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression(value="${spring.redis.enabled:false}")
public class GcSubjectDataListener
implements ApplicationListener<BaseDataEvent> {
    private static Logger logger = LoggerFactory.getLogger(GcSubjectDataListener.class);
    @Autowired
    SubjectCache subjectCache;

    @Override
    public void onApplicationEvent(BaseDataEvent event) {
        String systemId;
        BaseDataDTO baseDataDTO = event.getBaseDataDTO();
        if (!baseDataDTO.getTableName().equals("MD_GCSUBJECT")) {
            return;
        }
        String string = systemId = baseDataDTO.get((Object)"systemid") == null ? null : baseDataDTO.get((Object)"systemid").toString();
        if (StringUtils.isEmpty((String)systemId)) {
            logger.error("\u4f53\u7cfbid\u4e3a\u7a7a");
            return;
        }
        this.subjectCache.clearCache(systemId);
    }
}

