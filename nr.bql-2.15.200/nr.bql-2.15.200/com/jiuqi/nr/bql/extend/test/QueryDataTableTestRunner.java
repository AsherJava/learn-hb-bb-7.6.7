/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 */
package com.jiuqi.nr.bql.extend.test;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.bql.extend.test.NvwaModelDataTableInit;

public class QueryDataTableTestRunner
extends JobFactory {
    private static final String JOB_ID = "32bd5ca5-58r0-4cf6-abf0-a97156012345";
    private static final String JOB_TITLE = "\u67e5\u8be2\u6570\u636e\u65b9\u6848\u4e34\u65f6\u6d4b\u8bd5";
    private NvwaModelDataTableInit NvwaModelDataTableInit;

    public JobExecutor createJobExecutor(String arg0) throws JobsException {
        return new TestExecuter1();
    }

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    class TestExecuter2
    extends JobExecutor {
        TestExecuter2() {
        }

        public void execute(JobContext context) throws JobExecutionException {
        }
    }

    class TestExecuter1
    extends JobExecutor {
        TestExecuter1() {
        }

        public void execute(JobContext context) throws JobExecutionException {
            try {
                QueryDataTableTestRunner.this.NvwaModelDataTableInit.init();
            }
            catch (Exception e) {
                throw new JobExecutionException(e.getMessage(), (Throwable)e);
            }
        }
    }
}

