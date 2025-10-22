/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dispatch.core.ITaskContext
 *  com.jiuqi.nvwa.dispatch.core.TaskContextBuilder
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher
 */
package com.jiuqi.nr.dataSnapshot.service.impl;

import com.jiuqi.nr.dataSnapshot.service.IEnhancedDataSnapshotService;
import com.jiuqi.nr.dataSnapshot.service.impl.EnhancedDataSnapshotServiceImpl;
import com.jiuqi.nvwa.dispatch.core.ITaskContext;
import com.jiuqi.nvwa.dispatch.core.TaskContextBuilder;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractEnhancedDataSnapshotService {
    public static final String DATA_SNAPSHOT_TASKID = "enhancedDataSnapshotService";
    private IEnhancedDataSnapshotService defaultDataSnapshotService = null;
    private Logger logger = LoggerFactory.getLogger(AbstractEnhancedDataSnapshotService.class);
    @Value(value="${jiuqi.nr.dataSnapshot.enhanced:true}")
    private boolean enableEnhancedDataSnapshot = true;
    @Autowired
    protected ITaskDispatcher dispatcher;

    protected IEnhancedDataSnapshotService getIDataSnapshotService() {
        if (this.enableEnhancedDataSnapshot) {
            TaskContextBuilder builder = new TaskContextBuilder();
            try {
                ITaskContext taskContext = builder.taskId(DATA_SNAPSHOT_TASKID).build();
                return (IEnhancedDataSnapshotService)this.dispatcher.createService(taskContext, EnhancedDataSnapshotServiceImpl.class);
            }
            catch (TaskException e) {
                this.logger.error("\u65b0\u62a5\u8868\u5feb\u71672.0\u670d\u52a1\u5668\uff1a\u83b7\u53d6\u5206\u5e03\u5f0f\u6846\u67b6\u6269\u5c55\u5feb\u71672.0\u670d\u52a1\u5668\u5931\u8d25\uff0c\u5c06\u4f7f\u7528\u9ed8\u8ba4\u5feb\u71672.0\u670d\u52a1\u5668. ", e);
            }
        }
        if (null == this.defaultDataSnapshotService) {
            this.logger.info("\u65b0\u62a5\u8868\u5feb\u71672.0\u670d\u52a1\u5668\uff1a\u672a\u542f\u7528\u5206\u5e03\u5f0f\u5bbd\u8857\u6269\u5c55\u6216\u8005\u542f\u7528\u5931\u8d25\uff0c\u5c06\u4f7f\u7528\u9ed8\u8ba4\u5feb\u71672.0\u670d\u52a1\u5668.");
            this.defaultDataSnapshotService = new EnhancedDataSnapshotServiceImpl();
        }
        return this.defaultDataSnapshotService;
    }
}

