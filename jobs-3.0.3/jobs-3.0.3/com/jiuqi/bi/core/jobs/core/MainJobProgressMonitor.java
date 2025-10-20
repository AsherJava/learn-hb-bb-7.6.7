/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.AbstractProgressMonitor
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.monitor.ProgressException
 */
package com.jiuqi.bi.core.jobs.core;

import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.monitor.AbstractProgressMonitor;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.monitor.ProgressException;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainJobProgressMonitor
extends AbstractProgressMonitor
implements IProgressMonitor {
    private static final long COMMIT_PROGRESS_INTERVAL = 2000L;
    private double currentProgress;
    private String currentPrompt;
    private long lastCommitTime;
    private final String instanceGuid;
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());

    public MainJobProgressMonitor(String instanceGuid) {
        this.instanceGuid = instanceGuid;
    }

    protected void notify(double progress) {
        this.currentProgress = progress;
    }

    public void prompt(String msg) {
        this.currentPrompt = msg;
        this.commitProgress(System.currentTimeMillis());
    }

    public void stepIn() {
        super.stepIn();
        long now = System.currentTimeMillis();
        if (now - this.lastCommitTime > 2000L) {
            this.commitProgress(now);
        }
    }

    public void finishTask(String taskName) throws ProgressException {
        super.finishTask(taskName);
        this.commitProgress(System.currentTimeMillis());
    }

    public void finishTask() {
        super.finishTask();
        this.commitProgress(System.currentTimeMillis());
    }

    private void commitProgress(long now) {
        this.lastCommitTime = now;
        JobOperationManager manager = new JobOperationManager();
        int progress = (int)(this.currentProgress * 100.0);
        try {
            manager.updateJobProgress(this.instanceGuid, progress, this.currentPrompt);
        }
        catch (SQLException e) {
            this.logger.error("\u66f4\u65b0\u4efb\u52a1\u8fdb\u5ea6\u51fa\u73b0\u5f02\u5e38", e);
        }
    }
}

