/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;

public class DeleteCompleteFlagPlanTaskJob
extends AbstractSysJob {
    private final Logger logger = LogFactory.getLogger(DeleteCompleteFlagPlanTaskJob.class);
    private final AsyncTaskDao dao = (AsyncTaskDao)BeanUtil.getBean(AsyncTaskDao.class);

    public String getId() {
        return "DELETE_COMPLETEFLAG_JOB";
    }

    public String getTitle() {
        return "\u5f02\u6b65\u4efb\u52a1\u5df2\u8bfb\u6807\u8bb0\u6e05\u7406\u4efb\u52a1";
    }

    public void exec(JobContext context, String config) throws Exception {
        this.logger.info("\u5f00\u59cb\u6267\u884c\u5220\u9664\u5f02\u6b65\u4efb\u52a1CompleteFlag\u8fc7\u671f\u6570\u636e\uff01");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            this.dao.deleteStaleCompleteFlag(currentTimeMillis);
            this.dao.deleteJunkCompleteFlag();
        }
        catch (Exception e) {
            this.logger.info("\u5220\u9664\u5f02\u6b65\u4efb\u52a1CompleteFlag\u8fc7\u671f\u6570\u636e\u9519\u8bef\uff1a" + e.getMessage());
        }
        this.logger.info("\u5220\u9664\u5f02\u6b65\u4efb\u52a1CompleteFlag\u8fc7\u671f\u6570\u636e\u6267\u884c\u5b8c\u6bd5\uff01");
    }

    public String getModelName() {
        return "nr-asynctask";
    }

    public String getSysJobType() {
        return "SYS_CLEAN_JOB_TYPE";
    }
}

