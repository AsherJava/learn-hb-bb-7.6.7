/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.CellConditionStyle;
import java.util.List;

public class ConditionStyleField {
    private String fullName;
    private boolean enableCellStyle;
    private List<CellConditionStyle> cellStyles;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isEnableCellStyle() {
        return this.enableCellStyle;
    }

    public void setEnableCellStyle(boolean enableCellStyle) {
        this.enableCellStyle = enableCellStyle;
    }

    public List<CellConditionStyle> getCellStyles() {
        return this.cellStyles;
    }

    public void setCellStyles(List<CellConditionStyle> cellStyles) {
        this.cellStyles = cellStyles;
    }
}

