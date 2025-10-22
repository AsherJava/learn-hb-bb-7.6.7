/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.cell.CellColumn;
import com.jiuqi.nr.survey.model.cell.CellRow;
import java.util.List;

public class MatrixfloatQuestion
extends Element {
    private List<CellColumn> columns;
    private List<CellRow> rows;
    private String rowTitleWidth;
    private String columnsVisibleIf;
    private String rowsVisibleIf;
    private boolean enumSame;
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

    public List<CellRow> getRows() {
        return this.rows;
    }

    public void setRows(List<CellRow> rows) {
        this.rows = rows;
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

    public boolean isEnumSame() {
        return this.enumSame;
    }

    public void setEnumSame(boolean enumSame) {
        this.enumSame = enumSame;
    }
}

