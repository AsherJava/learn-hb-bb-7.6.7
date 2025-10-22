/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO
 *  com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.consolidatedsystem.listener;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.cache.SubjectCache;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcSubjectDataSyncCacheMsgReciver {
    private static Logger logger = LoggerFactory.getLogger(GcSubjectDataSyncCacheMsgReciver.class);
    @Autowired
    SubjectCache subjectCache;
    @Autowired
    private BaseDataCacheService baseDataCacheService;

    public void receiveMessage(String message) {
        String systemId;
        BaseDataSyncCacheDTO dmsc = (BaseDataSyncCacheDTO)JSONUtil.parseObject((String)message, BaseDataSyncCacheDTO.class);
        if (dmsc == null || dmsc.getBaseDataDTO() == null) {
            logger.error("\u57fa\u7840\u6570\u636e\u6d88\u606f\u53cd\u5e8f\u5217\u5316\u7ed3\u679c\u4e0d\u6b63\u786e");
            return;
        }
        BaseDataDTO baseDataDTO = dmsc.getBaseDataDTO();
        if (!baseDataDTO.getTableName().equals("MD_GCSUBJECT")) {
            return;
        }
        String string = systemId = baseDataDTO.get((Object)"systemid") == null ? null : baseDataDTO.get((Object)"systemid").toString();
        if (StringUtils.isEmpty((String)systemId)) {
            logger.error("\u4f53\u7cfbid\u4e3a\u7a7a");
            this.subjectCache.clearCache();
            return;
        }
        this.subjectCache.clearCache(systemId);
    }
}

