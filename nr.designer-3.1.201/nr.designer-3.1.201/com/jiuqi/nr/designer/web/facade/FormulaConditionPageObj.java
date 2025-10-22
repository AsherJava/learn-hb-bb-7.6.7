/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormulaConditionObj;
import java.util.List;

public class FormulaConditionPageObj {
    private Integer total;
    private List<FormulaConditionObj> data;

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<FormulaConditionObj> getData() {
        return this.data;
    }

    public void setData(List<FormulaConditionObj> data) {
        this.data = data;
    }
}

