/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.engine.result.PagingInfo
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.bi.quickreport.engine.result.PagingInfo;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.Serializable;

public class AnalyTableStyle
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Grid2Data gridData;
    private PagingInfo pagingInfo;
    private String analyExt;

    public Grid2Data getGridData() {
        return this.gridData;
    }

    public void setGridData(Grid2Data gridData) {
        this.gridData = gridData;
    }

    public PagingInfo getPagingInfo() {
        return this.pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    public String getAnalyExt() {
        return this.analyExt;
    }

    public void setAnalyExt(String analyExt) {
        this.analyExt = analyExt;
    }
}

