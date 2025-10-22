/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.nr.unit.uselector.dataio.ExcelRowData;
import java.util.List;

public class FImportedConditions {
    private String selector;
    private List<ExcelRowData> conditions;

    public String getSelector() {
        return this.selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public List<ExcelRowData> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<ExcelRowData> conditions) {
        this.conditions = conditions;
    }
}

