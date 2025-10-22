/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.snapshot.output.ComparisonResult
 *  com.jiuqi.nvwa.dispatch.core.ITaskContext
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  com.jiuqi.nvwa.dispatch.core.annotation.Dispatchable
 *  com.jiuqi.nvwa.dispatch.core.task.ITaskListener
 */
package com.jiuqi.nr.dataSnapshot.service.impl;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotParam;
import com.jiuqi.nr.dataSnapshot.service.IEnhancedDataSnapshotService;
import com.jiuqi.nr.dataSnapshot.service.impl.EnhancedDataSnapshotHelper;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import com.jiuqi.nvwa.dispatch.core.ITaskContext;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import com.jiuqi.nvwa.dispatch.core.annotation.Dispatchable;
import com.jiuqi.nvwa.dispatch.core.task.ITaskListener;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dispatchable(name="enhancedDataSnapshotService", title="\u65b0\u62a5\u8868\u5feb\u71672.0\u670d\u52a1\u5668", expireTime=300000L)
public class EnhancedDataSnapshotServiceImpl
implements IEnhancedDataSnapshotService,
ITaskListener {
    private Logger logger = LoggerFactory.getLogger(EnhancedDataSnapshotServiceImpl.class);

    @Override
    public AsyncTaskInfo createDataSnapshot(DataSnapshotParam param) throws JTableException {
        return EnhancedDataSnapshotHelper.getDataSnapshotBaseService().createDataSnapshot(param);
    }

    @Override
    public ReturnInfo updateDataSnapshot(DataSnapshotParam param) throws JTableException {
        return EnhancedDataSnapshotHelper.getDataSnapshotBaseService().updateDataSnapshot(param);
    }

    @Override
    public List<DataSnapshotInfo> queryDataSnapshot(DataSnapshotParam param) {
        return EnhancedDataSnapshotHelper.getDataSnapshotBaseService().queryDataSnapshot(param);
    }

    @Override
    public ReturnInfo deleteDataSnapshot(DataSnapshotParam param) {
        return EnhancedDataSnapshotHelper.getDataSnapshotBaseService().deleteDataSnapshot(param);
    }

    @Override
    public ReturnInfo restoreDataSnapshot(DataSnapshotParam param) {
        return EnhancedDataSnapshotHelper.getDataSnapshotBaseService().restoreDataSnapshot(param);
    }

    @Override
    public List<ComparisonResult> compareDataSnapshot(DataSnapshotParam param) {
        return EnhancedDataSnapshotHelper.getDataSnapshotBaseService().compareDataSnapshot(param);
    }

    public void onTaskStart(ITaskContext context) throws TaskException {
        this.logger.info("\u65b0\u62a5\u8868\u5feb\u71672.0\u670d\u52a1\u5668: \u521d\u59cb\u5316\u5feb\u71672.0\u670d\u52a1\u5668.");
    }

    public void onTaskEnd(ITaskContext context) throws TaskException {
        this.logger.info("\u65b0\u62a5\u8868\u5feb\u71672.0\u670d\u52a1\u5668: \u5feb\u71672.0\u670d\u52a1\u5668\u7ec8\u6b62.");
    }
}

