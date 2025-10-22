/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.RegionGradeInfo;

public class DataQueryPar {
    private IQueryInfo queryInfo;
    private RegionGradeInfo regionGradeInfo;
    private String snapshotId;

    public IQueryInfo getQueryInfo() {
        return this.queryInfo;
    }

    public void setQueryInfo(IQueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    public String getSnapshotId() {
        return this.snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public RegionGradeInfo getRegionGradeInfo() {
        return this.regionGradeInfo;
    }

    public void setRegionGradeInfo(RegionGradeInfo regionGradeInfo) {
        this.regionGradeInfo = regionGradeInfo;
    }
}

