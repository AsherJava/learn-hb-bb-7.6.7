/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.core;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.core.MainJobProgressMonitor;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubJobProgressMonitor
extends MainJobProgressMonitor {
    private static Logger logger = LoggerFactory.getLogger(SubJobProgressMonitor.class);
    private String parentInstanceGuid;
    private String rootInstanceGuid;

    public SubJobProgressMonitor(String instanceGuid, String parentInstanceGuid) {
        this(instanceGuid, parentInstanceGuid, null);
    }

    public SubJobProgressMonitor(String instanceGuid, String parentInstanceGuid, String rootInstanceId) {
        super(instanceGuid);
        this.parentInstanceGuid = parentInstanceGuid;
        this.rootInstanceGuid = rootInstanceId;
    }

    public boolean isCanceled() {
        if (super.isCanceled()) {
            return true;
        }
        JobOperationManager jobOperationManager = new JobOperationManager();
        try {
            JobInstanceBean parentInstance;
            if (StringUtils.isNotEmpty((String)this.parentInstanceGuid) && (parentInstance = jobOperationManager.getInstanceById(this.parentInstanceGuid)) != null && (parentInstance.getResult() == 2 || parentInstance.getState() == State.CANCELING.getValue())) {
                return true;
            }
        }
        catch (JobsException e) {
            logger.error("\u67e5\u8be2\u7236\u4efb\u52a1\u5b9e\u4f8b\u5931\u8d25", e);
        }
        return false;
    }
}

