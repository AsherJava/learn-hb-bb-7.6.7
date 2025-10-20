/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$ClassifierContext
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$Path
 *  com.jiuqi.bi.core.jobs.extension.item.FolderItem
 *  com.jiuqi.bi.core.jobs.extension.item.JobItem
 */
package com.jiuqi.common.plantask.extend.job;

import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import java.util.List;

public class RunnerJobClassifier
implements IJobClassifier {
    public List<FolderItem> getFolders(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
        return null;
    }

    public List<JobItem> getItems(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
        return null;
    }

    public JobItem getJobItem(String jobId) throws Exception {
        return null;
    }

    public IJobClassifier.Path locate(FolderItem folderItem) throws Exception {
        return null;
    }

    public IJobClassifier.Path locate(JobItem jobItem) throws Exception {
        return null;
    }
}

