/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  org.json.JSONObject
 */
package com.jiuqi.nr.io.record;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import org.json.JSONObject;

public class ImportHistoryPlanTask
extends AbstractSysJob {
    private final Logger logger = LogFactory.getLogger(ImportHistoryPlanTask.class);
    ImportHistoryService importHistoryService = (ImportHistoryService)BeanUtil.getBean(ImportHistoryService.class);

    public String getId() {
        return "DELETE_IMPORT_RECORD";
    }

    public String getTitle() {
        return "\u5220\u9664NRDX\u5bfc\u5165\u8bb0\u5f55";
    }

    public void exec(JobContext context, String config) throws Exception {
        this.logger.info("\u5f00\u59cb\u5220\u9664NRDX\u8fc7\u671f\u7684\u5bfc\u5165\u5386\u53f2\u8bb0\u5f55");
        JSONObject jsonObject = new JSONObject(config);
        int cleanDays = jsonObject.getInt("cleanDays");
        if (cleanDays < 0) {
            cleanDays = 3;
        }
        this.importHistoryService.deleteTimeOutImportHistory(cleanDays);
        this.logger.info("\u5220\u9664NRDX\u8fc7\u671f\u7684\u5bfc\u5165\u5386\u53f2\u8bb0\u5f55\u5b8c\u6210");
    }

    public String getModelName() {
        return "job-cleanNrdxImportRecord";
    }

    public String getSysJobType() {
        return "SYS_CLEAN_JOB_TYPE";
    }

    public static String getDefaultConf() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cleanDays", 3);
        return jsonObject.toString();
    }
}

