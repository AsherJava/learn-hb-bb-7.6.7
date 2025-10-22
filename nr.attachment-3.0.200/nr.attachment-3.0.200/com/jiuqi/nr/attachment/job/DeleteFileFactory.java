/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 */
package com.jiuqi.nr.attachment.job;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.nr.attachment.job.DeleteFileExecutor;

public class DeleteFileFactory
extends JobFactory {
    public static final String ID = "attachment_delete_file_job";
    public static final String TITLE = "\u6e05\u9664\u88ab\u6807\u8bb0\u5220\u9664\u7684\u9644\u4ef6";

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new DeleteFileExecutor();
    }

    public String getJobCategoryId() {
        return ID;
    }

    public String getJobCategoryTitle() {
        return TITLE;
    }
}

