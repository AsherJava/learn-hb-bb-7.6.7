/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 */
package com.jiuqi.nr.data.excel.export;

import com.jiuqi.nr.data.excel.extend.IRegionDataSetProvider;
import com.jiuqi.nr.data.excel.param.DataQueryPar;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.RegionGradeInfo;

public class RegionDataSetPageLoader {
    private final QueryInfoBuilder queryInfoBuilder;
    private final String snapShotId;
    private final IRegionDataSetProvider regionDataSetProvider;
    private final int rowsPerPage;
    private IRegionDataSet curRegionDataSet;
    private int curPageIndex = -1;
    private final PageInfo pageInfo = new PageInfo();
    private boolean nullRegionDataSet;
    private boolean existDataNext;
    private RegionGradeInfo gradeInfo;

    public RegionDataSetPageLoader(QueryInfoBuilder queryInfoBuilder, String snapShotId, IRegionDataSetProvider regionDataSetProvider, int rowsPerPage) {
        this.queryInfoBuilder = queryInfoBuilder;
        this.snapShotId = snapShotId;
        this.regionDataSetProvider = regionDataSetProvider;
        this.rowsPerPage = rowsPerPage;
        this.pageInfo.setRowsPerPage(rowsPerPage);
    }

    public RegionDataSetPageLoader(QueryInfoBuilder queryInfoBuilder, RegionGradeInfo gradeInfo, String snapShotId, IRegionDataSetProvider regionDataSetProvider, int rowsPerPage) {
        this.queryInfoBuilder = queryInfoBuilder;
        this.snapShotId = snapShotId;
        this.regionDataSetProvider = regionDataSetProvider;
        this.rowsPerPage = rowsPerPage;
        this.pageInfo.setRowsPerPage(rowsPerPage);
        this.gradeInfo = gradeInfo;
    }

    public boolean next() {
        if (this.curRegionDataSet == null && !this.nullRegionDataSet) {
            ++this.curPageIndex;
            if (this.rowsPerPage >= 0) {
                this.pageInfo.setPageIndex(this.curPageIndex);
                this.queryInfoBuilder.setPage(this.pageInfo);
            }
            IQueryInfo queryInfo = this.queryInfoBuilder.build();
            DataQueryPar dataQueryPar = new DataQueryPar();
            dataQueryPar.setQueryInfo(queryInfo);
            dataQueryPar.setSnapshotId(this.snapShotId);
            dataQueryPar.setRegionGradeInfo(this.gradeInfo);
            this.curRegionDataSet = this.regionDataSetProvider.getRegionDataSet(dataQueryPar);
            this.nullRegionDataSet = this.curRegionDataSet == null;
            this.existDataNext = true;
        } else {
            if (this.nullRegionDataSet) {
                this.existDataNext = false;
                return false;
            }
            if (this.rowsPerPage < 0) {
                this.existDataNext = false;
            } else if (this.curRegionDataSet.getTotalCount() > this.curPageIndex * this.rowsPerPage + this.curRegionDataSet.getRowCount()) {
                this.pageInfo.setPageIndex(++this.curPageIndex);
                this.queryInfoBuilder.setPage(this.pageInfo);
                IQueryInfo queryInfo = this.queryInfoBuilder.build();
                DataQueryPar dataQueryPar = new DataQueryPar();
                dataQueryPar.setQueryInfo(queryInfo);
                dataQueryPar.setSnapshotId(this.snapShotId);
                this.curRegionDataSet = this.regionDataSetProvider.getRegionDataSet(dataQueryPar);
                this.nullRegionDataSet = this.curRegionDataSet == null;
                this.existDataNext = true;
            } else {
                this.existDataNext = false;
            }
        }
        return this.existDataNext;
    }

    public IRegionDataSet getCurRegionDataSet() {
        return this.curRegionDataSet;
    }

    public boolean isExistDataNext() {
        return this.existDataNext;
    }

    public void skipPages(int skipPageNum) {
        this.curPageIndex = skipPageNum;
    }
}

