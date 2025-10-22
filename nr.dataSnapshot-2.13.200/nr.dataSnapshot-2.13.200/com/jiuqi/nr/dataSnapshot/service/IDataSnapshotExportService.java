/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.ExportData
 */
package com.jiuqi.nr.dataSnapshot.service;

import com.jiuqi.nr.dataSnapshot.param.DataSnapshotDifference;
import com.jiuqi.nr.dataSnapshot.param.DataSnapshotExportParam;
import com.jiuqi.nr.dataentry.bean.ExportData;
import java.util.List;

public interface IDataSnapshotExportService {
    public ExportData compareResultExport(DataSnapshotExportParam var1, List<DataSnapshotDifference> var2, String var3);

    public List<ExportData> compareResultBatchExport(DataSnapshotExportParam var1, List<DataSnapshotDifference> var2, List<String> var3);

    public List<ExportData> compareResultBatchExport(DataSnapshotExportParam var1, List<DataSnapshotDifference> var2);
}

