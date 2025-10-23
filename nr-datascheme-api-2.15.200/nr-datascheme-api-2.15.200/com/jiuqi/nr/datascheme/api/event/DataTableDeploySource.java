/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import java.io.Serializable;

public class DataTableDeploySource
implements Serializable {
    private static final long serialVersionUID = -3669561032623493438L;
    private final DataTable dataTable;
    private final DesignTableModel tableModel;
    private final Status status;
    private final boolean codeChanged;

    public DataTableDeploySource(DataTable dataTable, DesignTableModel tableModel, Status status, boolean codeChanged) {
        this.dataTable = dataTable;
        this.tableModel = tableModel;
        this.status = status;
        this.codeChanged = codeChanged;
    }

    public String getDataSchemeKey() {
        return this.dataTable.getDataSchemeKey();
    }

    public String getDataTableKey() {
        return this.dataTable.getKey();
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public String getTableModelKey() {
        return this.tableModel.getID();
    }

    public DesignTableModel getTableModel() {
        return this.tableModel;
    }

    public boolean isDelete() {
        return Status.DELETE == this.status;
    }

    public boolean isExecutedDDL() {
        return Status.UPDATE_NO_DDL != this.status;
    }

    public boolean isCodeChanged() {
        return this.codeChanged;
    }

    public Status getStatus() {
        return this.status;
    }

    public String toString() {
        return this.dataTable.toString() + ", " + this.tableModel.toString() + ", status=" + (Object)((Object)this.status) + ", codeChanged=" + this.codeChanged;
    }

    public static enum Status {
        ADD,
        DELETE,
        UPDATE,
        UPDATE_NO_DDL;

    }
}

