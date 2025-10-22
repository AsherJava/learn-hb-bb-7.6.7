/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 */
package com.jiuqi.nr.dataSnapshot.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotExportParam;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotFormInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotInfo;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotParam;
import com.jiuqi.nr.dataSnapshot.param.DifferenceResult;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import java.util.List;
import java.util.Map;

public interface IDataSnapshotService {
    public AsyncTaskInfo createDataSnapshot(DataSnapshotParam var1) throws JTableException;

    public ReturnInfo updateDataSnapshot(DataSnapshotParam var1) throws JTableException;

    public List<DataSnapshotInfo> queryDataSnapshot(DataSnapshotParam var1);

    public ReturnInfo deleteDataSnapshot(DataSnapshotParam var1);

    public ReturnInfo restoreDataSnapshot(DataSnapshotParam var1);

    public Map<String, Integer> compareDataSnapshotReturnTotal(DataSnapshotParam var1);

    public DifferenceResult compareDataSnapshot(DataSnapshotParam var1);

    public List<DataSnapshotFormInfo> queryFormList(String var1);

    public ExportData dataSnapshotCompareResultExport(DataSnapshotExportParam var1);

    public List<ExportData> dataSnapshotCompareResultBatchExport(DataSnapshotExportParam var1);

    public Map<String, Object> querySystemParam(String var1);
}

