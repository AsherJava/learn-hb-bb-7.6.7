/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 */
package com.jiuqi.nr.snapshot.service;

import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.snapshot.exception.SnapshotException;
import com.jiuqi.nr.snapshot.input.BatchComparisonContext;
import com.jiuqi.nr.snapshot.input.ComparisonContext;
import com.jiuqi.nr.snapshot.input.ReversionByPeriodContext;
import com.jiuqi.nr.snapshot.input.ReversionBySnapshotContext;
import com.jiuqi.nr.snapshot.output.BatchComparisonResult;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import java.util.List;

public interface DataOperationService {
    public List<ComparisonResult> comparison(ComparisonContext var1) throws SnapshotException;

    public List<BatchComparisonResult> batchComparison(BatchComparisonContext var1) throws SnapshotException;

    public void reversionByPeriod(ReversionByPeriodContext var1) throws SnapshotException;

    public void reversionBySnapshot(ReversionBySnapshotContext var1) throws SnapshotException;

    public IRegionDataSet querySanpshotData(IQueryInfo var1, String var2);

    public IRegionDataSet querySanpshotCellData(IQueryInfo var1, String var2);
}

