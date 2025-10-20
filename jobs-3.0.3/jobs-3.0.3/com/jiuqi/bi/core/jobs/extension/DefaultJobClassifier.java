/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.extension.AbstractJobClassifier;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class DefaultJobClassifier
extends AbstractJobClassifier {
    private String categoryId;

    public DefaultJobClassifier(String category) {
        this.categoryId = category;
    }

    @Override
    public String getCategoryId() {
        return this.categoryId;
    }

    @Override
    public List<JobItem> getItems(String parentGuid, IJobClassifier.ClassifierContext context) throws Exception {
        if (StringUtils.isEmpty((String)parentGuid)) {
            parentGuid = this.categoryId;
        }
        JobStorageManager manager = new JobStorageManager();
        List<JobModel> models = manager.getJobsByFolder(parentGuid);
        ArrayList<JobItem> items = new ArrayList<JobItem>();
        for (JobModel model : models) {
            JobItem item = this.fromJobModel(model);
            items.add(item);
        }
        return items;
    }

    @Override
    public JobItem getJobItem(String jobGuid) throws Exception {
        JobStorageManager manager = new JobStorageManager();
        JobModel model = manager.getJob(jobGuid);
        return this.fromJobModel(model);
    }

    private JobItem fromJobModel(JobModel model) {
        JobItem item = new JobItem();
        if (model != null) {
            item.setJobId(model.getGuid());
            item.setJobTitle(model.getTitle());
            item.setFolderGuid(model.getFolderGuid());
        }
        return item;
    }
}

