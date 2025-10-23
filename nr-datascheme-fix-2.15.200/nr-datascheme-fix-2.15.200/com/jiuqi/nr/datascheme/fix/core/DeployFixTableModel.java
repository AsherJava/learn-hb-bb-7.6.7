/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.datascheme.fix.core;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.nr.datascheme.fix.core.DeployFixTableModelCheckResult;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.List;

public class DeployFixTableModel {
    private String tableModelKey;
    private TableModelDefine tableModelDefine;
    private DesignTableModelDefine desTableModelDefine;
    private List<ColumnModelDefine> columnModels;
    private List<DesignColumnModelDefine> desColumnModels;
    private LogicTable logicTable;
    private List<LogicField> logicFields;
    private DeployFixTableModelCheckResult checkResult;

    public DeployFixTableModel() {
    }

    public DeployFixTableModel(String tableModelKey) {
        this.tableModelKey = tableModelKey;
    }

    public String getTableModelKey() {
        return this.tableModelKey;
    }

    public void setTableModelKey(String tableModelKey) {
        this.tableModelKey = tableModelKey;
    }

    public List<ColumnModelDefine> getColumnModels() {
        return this.columnModels;
    }

    public void setColumnModels(List<ColumnModelDefine> columnModels) {
        this.columnModels = columnModels;
    }

    public List<DesignColumnModelDefine> getDesColumnModels() {
        return this.desColumnModels;
    }

    public void setDesColumnModels(List<DesignColumnModelDefine> desColumnModels) {
        this.desColumnModels = desColumnModels;
    }

    public LogicTable getLogicTable() {
        return this.logicTable;
    }

    public void setLogicTable(LogicTable logicTable) {
        this.logicTable = logicTable;
    }

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public void setLogicFields(List<LogicField> logicFields) {
        this.logicFields = logicFields;
    }

    public TableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    public void setTableModelDefine(TableModelDefine tableModelDefine) {
        this.tableModelDefine = tableModelDefine;
    }

    public DesignTableModelDefine getDesTableModelDefine() {
        return this.desTableModelDefine;
    }

    public void setDesTableModelDefine(DesignTableModelDefine desTableModelDefine) {
        this.desTableModelDefine = desTableModelDefine;
    }

    public DeployFixTableModelCheckResult getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(DeployFixTableModelCheckResult checkResult) {
        this.checkResult = checkResult;
    }
}

