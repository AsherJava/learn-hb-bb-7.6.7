/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.exception.SnapshotException;
import com.jiuqi.nr.snapshot.input.BatchCreateSnapshotContext;
import com.jiuqi.nr.snapshot.input.BatchCreateSnapshotParam;
import com.jiuqi.nr.snapshot.input.CreateSnapshotContext;
import com.jiuqi.nr.snapshot.input.JudgeNameContext;
import com.jiuqi.nr.snapshot.input.UpdateSnapshotInfo;
import com.jiuqi.nr.snapshot.output.ResultInfo;
import com.jiuqi.nr.snapshot.output.SnapshotInfo;
import java.util.List;

public interface SnapshotService {
    public boolean judgeDuplicateNames(JudgeNameContext var1);

    public SnapshotInfo createSnapshot(CreateSnapshotContext var1, AsyncTaskMonitor var2) throws SnapshotException;

    public void batchCreateSnapshot(BatchCreateSnapshotContext var1, AsyncTaskMonitor var2) throws SnapshotException;

    public ResultInfo batchCreateSnapshotJudgeName(BatchCreateSnapshotParam var1, AsyncTaskMonitor var2) throws SnapshotException;

    public void deleteSnapshot(String var1, String var2) throws SnapshotException;

    public void deleteSnapshot(String var1, String var2, boolean var3) throws SnapshotException;

    public void updateSnapshot(UpdateSnapshotInfo var1) throws SnapshotException;

    public void updateSnapshot(UpdateSnapshotInfo var1, boolean var2) throws SnapshotException;

    public List<SnapshotInfo> querySnapshot(DimensionCombination var1, String var2);
}

