/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 */
package com.jiuqi.gcreport.financialcheckapi.reltxquery.vo;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.financialcheckapi.reltxquery.vo.RelTxCheckQueryTableDataVO;
import java.util.List;
import java.util.Map;

public class RelTxCheckQueryDataVO {
    private List<Map<String, Object>> columns;
    private PageInfo<RelTxCheckQueryTableDataVO> data;

    public List<Map<String, Object>> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Map<String, Object>> columns) {
        this.columns = columns;
    }

    public PageInfo<RelTxCheckQueryTableDataVO> getData() {
        return this.data;
    }

    public void setData(PageInfo<RelTxCheckQueryTableDataVO> data) {
        this.data = data;
    }
}

