/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  org.apache.commons.lang3.time.DateUtils
 */
package com.jiuqi.nr.multcheck2.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.multcheck2.service.IMCResultService;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultcheckCleanJob
extends AbstractSysJob {
    private static final Logger logger = LoggerFactory.getLogger(MultcheckCleanJob.class);
    public static final String JOB_ID = "NR_MULT_CHECK_CLEAN_RES_JOB";
    private static final String JOB_TITLE = "\u7efc\u5408\u5ba1\u68382.0\u7ed3\u679c\u6570\u636e\u6e05\u9664";

    public String getId() {
        return JOB_ID;
    }

    public String getTitle() {
        return JOB_TITLE;
    }

    public void exec(JobContext context, String config) throws Exception {
        com.jiuqi.bi.core.jobs.defaultlog.Logger defaultLogger = context.getDefaultLogger();
        IMCResultService mcResultService = (IMCResultService)SpringBeanUtils.getBean(IMCResultService.class);
        defaultLogger.info("\u7efc\u5408\u5ba1\u68382.0\u8ba1\u5212\u4efb\u52a1\u6e05\u9664\u5386\u53f2\u6570\u636e\u5f00\u59cb");
        logger.info("\u7efc\u5408\u5ba1\u68382.0\u8ba1\u5212\u4efb\u52a1\u6e05\u9664\u5386\u53f2\u6570\u636e\u5f00\u59cb");
        Date cleanTime = DateUtils.addDays((Date)new Date(), (int)-90);
        mcResultService.cleanRecord(cleanTime, defaultLogger);
        defaultLogger.info("\u7efc\u5408\u5ba1\u68382.0\u8ba1\u5212\u4efb\u52a1\u6e05\u9664\u5386\u53f2\u6570\u636e\u6210\u529f");
        logger.info("\u7efc\u5408\u5ba1\u68382.0\u8ba1\u5212\u4efb\u52a1\u6e05\u9664\u5386\u53f2\u6570\u636e\u6210\u529f");
    }

    public String getModelName() {
        return null;
    }

    public String getSysJobType() {
        return "SYS_CLEAN_JOB_TYPE";
    }
}

