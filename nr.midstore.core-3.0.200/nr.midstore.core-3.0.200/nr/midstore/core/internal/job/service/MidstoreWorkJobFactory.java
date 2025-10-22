/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 */
package nr.midstore.core.internal.job.service;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import nr.midstore.core.internal.job.service.MidstoreWorkJobExecutor;
import org.springframework.stereotype.Component;

@Component
public class MidstoreWorkJobFactory
extends JobFactory
implements IJobAdvanceConfiguration {
    public static final String ID = "midstore_work_job";
    public static final String TITLE = "\u4e2d\u95f4\u5e93\u6267\u884c\u8ba1\u5212\u4efb\u52a1";
    private static final String JOB_CONFIG_PAGE = "midstore-scheme";

    public JobExecutor createJobExecutor(String jobId) throws JobsException {
        return (JobExecutor)SpringBeanProvider.getBean(MidstoreWorkJobExecutor.class);
    }

    public String getJobCategoryId() {
        return ID;
    }

    public String getJobCategoryTitle() {
        return TITLE;
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }
}

