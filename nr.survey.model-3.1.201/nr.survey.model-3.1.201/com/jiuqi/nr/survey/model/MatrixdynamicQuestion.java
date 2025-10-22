/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.cell.CellColumn;
import java.util.List;

public class MatrixdynamicQuestion
extends Element {
    private List<CellColumn> columns;
    private String rowTitleWidth;
    private String columnsVisibleIf;
    private String rowsVisibleIf;
    private String defaultValue;

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<CellColumn> getColumns() {
        return this.columns;
    }

    public void setColumns(List<CellColumn> columns) {
        this.columns = columns;
    }

    public String getRowTitleWidth() {
        return this.rowTitleWidth;
    }

    public void setRowTitleWidth(String rowTitleWidth) {
        this.rowTitleWidth = rowTitleWidth;
    }

    public String getColumnsVisibleIf() {
        return this.columnsVisibleIf;
    }

    public void setColumnsVisibleIf(String columnsVisibleIf) {
        this.columnsVisibleIf = columnsVisibleIf;
    }

    public String getRowsVisibleIf() {
        return this.rowsVisibleIf;
    }

    public void setRowsVisibleIf(String rowsVisibleIf) {
        this.rowsVisibleIf = rowsVisibleIf;
    }
}

