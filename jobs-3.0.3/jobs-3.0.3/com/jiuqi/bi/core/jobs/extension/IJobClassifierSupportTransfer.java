/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.jobs.manager.FolderManager;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IJobClassifierSupportTransfer {
    public List<ResItem> getRelatedBusiness(String var1) throws TransferException;

    default public FolderNode getFolderNode(String folderGuid) throws TransferException {
        FolderManager folderManager = new FolderManager();
        try {
            FolderItem folder = folderManager.getFolder(folderGuid);
            if (folder == null) {
                return null;
            }
            return new FolderNode(folder.getGuid(), folder.getTitle(), folder.getType(), folder.getParentGuid(), folder.getOrder());
        }
        catch (SQLException e) {
            throw new TransferException((Throwable)e);
        }
    }

    default public List<FolderNode> getBusinessPath(String jobId) throws TransferException {
        JobStorageManager storageManager = new JobStorageManager();
        try {
            JobModel job = storageManager.getJob(jobId);
            if (job == null) {
                return new ArrayList<FolderNode>();
            }
            return this.getFolderPath(job.getFolderGuid());
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
    }

    default public List<FolderNode> getFolderPath(String folderGuid) throws TransferException {
        ArrayList<FolderNode> path = new ArrayList<FolderNode>();
        try {
            List<FolderItem> folderPaths = new FolderManager().getFolderPaths(folderGuid);
            for (FolderItem folderPath : folderPaths) {
                path.add(new FolderNode(folderPath.getGuid(), folderPath.getTitle(), folderPath.getType(), folderPath.getParentGuid(), folderPath.getOrder()));
            }
            return path;
        }
        catch (Exception var7) {
            throw new TransferException("\u83b7\u53d6\u8def\u5f84\u65f6\u51fa\u73b0\u5f02\u5e38", (Throwable)var7);
        }
    }

    default public String addFolder(FolderNode folderItem) throws TransferException {
        try {
            return new FolderManager().addFolder(folderItem);
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
    }

    default public void modifyFolder(FolderNode folderItem) throws TransferException {
        try {
            new FolderManager().modifyFolder(folderItem);
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
    }
}

