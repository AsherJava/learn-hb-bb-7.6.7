/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import java.util.List;

public class DFDAddRowQueryInfo {
    private DataFillContext context;
    private List<DFDimensionValue> addRowDimensionList;

    public DataFillContext getContext() {
        return this.context;
    }

    public void setContext(DataFillContext context) {
        this.context = context;
    }

    public List<DFDimensionValue> getAddRowDimensionList() {
        return this.addRowDimensionList;
    }

    public void setAddRowDimensionList(List<DFDimensionValue> addRowDimensionList) {
        this.addRowDimensionList = addRowDimensionList;
    }
}

