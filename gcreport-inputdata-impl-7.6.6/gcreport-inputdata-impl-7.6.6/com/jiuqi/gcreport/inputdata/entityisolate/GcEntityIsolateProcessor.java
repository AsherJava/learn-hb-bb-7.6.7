/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 */
package com.jiuqi.gcreport.inputdata.entityisolate;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcEntityIsolateProcessor {
    private static GcEntityIsolateProcessor gcEntityIsolateProcessor;
    private static ConsolidatedTaskService consolidatedTaskService;
    private static Logger logger;

    public static GcEntityIsolateProcessor getInstance() {
        if (null == gcEntityIsolateProcessor) {
            gcEntityIsolateProcessor = new GcEntityIsolateProcessor();
        }
        return gcEntityIsolateProcessor;
    }

    public static String getSubjectEntityIsolateCondition(String entityId, String formSchemeKey, String periodStr) {
        if (StringUtils.isEmpty((String)entityId) || !entityId.contains("MD_GCSUBJECT")) {
            return null;
        }
        ConsolidatedTaskVO consolidatedTaskVO = consolidatedTaskService.getTaskBySchemeId(formSchemeKey, periodStr);
        if (Objects.isNull(consolidatedTaskVO) || StringUtils.isEmpty((String)consolidatedTaskVO.getSystemId())) {
            logger.error("\u5408\u5e76\u79d1\u76ee\u57fa\u7840\u6570\u636e\u9694\u79bb\u6761\u4ef6\u5904\u7406\uff0c\u83b7\u53d6\u4f53\u7cfb\u4fe1\u606f\u4e3a\u7a7a\uff0cformSchemeKey=" + formSchemeKey + ",periodStr=" + periodStr);
            return null;
        }
        return consolidatedTaskVO.getSystemId();
    }

    static {
        consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        logger = LoggerFactory.getLogger(GcEntityIsolateProcessor.class);
    }
}

