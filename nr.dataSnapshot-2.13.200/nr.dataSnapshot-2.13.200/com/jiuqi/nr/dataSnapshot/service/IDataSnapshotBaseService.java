/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.snapshot.output.ComparisonResult
 */
package com.jiuqi.nr.dataSnapshot.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotParam;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import java.util.List;

public interface IDataSnapshotBaseService {
    public AsyncTaskInfo createDataSnapshot(DataSnapshotParam var1) throws JTableException;

    public ReturnInfo updateDataSnapshot(DataSnapshotParam var1) throws JTableException;

    public List<DataSnapshotInfo> queryDataSnapshot(DataSnapshotParam var1);

    public ReturnInfo deleteDataSnapshot(DataSnapshotParam var1);

    public ReturnInfo restoreDataSnapshot(DataSnapshotParam var1);

    public List<ComparisonResult> compareDataSnapshot(DataSnapshotParam var1);
}

