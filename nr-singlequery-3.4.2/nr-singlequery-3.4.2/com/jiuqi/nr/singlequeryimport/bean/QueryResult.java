/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.zbquery.rest.vo.QueryResultVO
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.nr.zbquery.rest.vo.QueryResultVO;

public class QueryResult
extends QueryResultVO {
    private String org;
    private String gridData;

    public String getGridData() {
        return this.gridData;
    }

    public void setGridData(String gridData) {
        this.gridData = gridData;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}

