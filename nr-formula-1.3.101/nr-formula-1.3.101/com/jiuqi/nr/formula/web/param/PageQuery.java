/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.param;

import com.jiuqi.nr.formula.web.param.BasePM;

public class PageQuery
extends BasePM {
    private Integer pageNo;
    private Integer pageSize;

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

