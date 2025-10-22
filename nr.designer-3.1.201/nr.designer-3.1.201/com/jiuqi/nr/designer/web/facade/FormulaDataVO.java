/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormulaObj;
import java.util.List;

public class FormulaDataVO {
    private List<FormulaObj> data;
    private int total;
    private String maxCode;

    public FormulaDataVO() {
    }

    public FormulaDataVO(int total, String maxCode) {
        this.total = total;
        this.maxCode = maxCode;
    }

    public List<FormulaObj> getData() {
        return this.data;
    }

    public void setData(List<FormulaObj> data) {
        this.data = data;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMaxCode() {
        return this.maxCode;
    }

    public void setMaxCode(String maxCode) {
        this.maxCode = maxCode;
    }
}

