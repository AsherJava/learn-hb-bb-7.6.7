/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import com.jiuqi.bi.core.jobs.manager.FolderManager;
import com.jiuqi.bi.util.StringUtils;
import java.util.List;

public abstract class AbstractJobClassifier
implements IJobClassifier {
    public abstract String getCategoryId();

    @Override
    public List<FolderItem> getFolders(String parentGuid, IJobClassifier.ClassifierContext context) throws Exception {
        if (StringUtils.isEmpty((String)parentGuid)) {
            parentGuid = this.getCategoryId();
        }
        FolderManager folderManager = new FolderManager();
        return folderManager.getFolders(parentGuid);
    }

    @Override
    public IJobClassifier.Path locate(FolderItem item) throws Exception {
        return null;
    }

    @Override
    public IJobClassifier.Path locate(JobItem item) throws Exception {
        return null;
    }
}

