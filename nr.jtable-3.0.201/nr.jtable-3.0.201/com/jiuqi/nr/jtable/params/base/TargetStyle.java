/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.nr.jtable.params.base.Cell;
import java.util.LinkedHashSet;
import java.util.Set;

public class TargetStyle {
    private String fieldCode;
    private String dataLinkCode;
    private Set<Cell> cells;

    public String getFieldCode() {
        return this.fieldCode;
    }

    public String getDataLinkCode() {
        return this.dataLinkCode;
    }

    public TargetStyle(String datalinkCode, String filedCode) {
        this.dataLinkCode = datalinkCode;
        this.fieldCode = filedCode;
        this.cells = new LinkedHashSet<Cell>();
    }

    public void setCell(Set<Cell> cell) {
        this.cells = new LinkedHashSet<Cell>(cell);
    }

    public Set<Cell> getCells() {
        return this.cells;
    }

    public void AddCell(TargetStyle targetStyle) {
        for (Cell cell : targetStyle.getCells()) {
            this.cells.add(cell);
        }
    }
}

