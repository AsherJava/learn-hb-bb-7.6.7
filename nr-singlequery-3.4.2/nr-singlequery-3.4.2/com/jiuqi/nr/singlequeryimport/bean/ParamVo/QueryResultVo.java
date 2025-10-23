/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.bean.ParamVo;

import java.io.Serializable;
import java.util.List;

public class QueryResultVo
implements Serializable {
    private int totalCount;
    private transient List<Object[]> result;

    public int getTotalCount() {
        return this.totalCount;
    }

    public List<Object[]> getResult() {
        return this.result;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setResult(List<Object[]> result) {
        this.result = result;
    }
}

