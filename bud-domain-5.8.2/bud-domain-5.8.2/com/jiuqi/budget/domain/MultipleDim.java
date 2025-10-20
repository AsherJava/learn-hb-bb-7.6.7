/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import com.jiuqi.budget.domain.DimValObj;
import java.util.List;

public class MultipleDim {
    private String dimCode;
    private List<DimValObj> dimValObjList;

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public List<DimValObj> getDimValObjList() {
        return this.dimValObjList;
    }

    public void setDimValObjList(List<DimValObj> dimValObjList) {
        this.dimValObjList = dimValObjList;
    }
}

