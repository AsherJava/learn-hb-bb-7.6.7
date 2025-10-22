/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GatherDataTable {
    private GatherTableDefine gatherTableDefine;
    private List<ColumnModelDefine> allColumns;
    private TableModelDefine tableModelDefine;
    private List<ColumnModelDefine> allDimsColumns;
    private Map<ColumnModelDefine, DataField> gatherColumns2DataField = new HashMap<ColumnModelDefine, DataField>();

    public List<ColumnModelDefine> getAllColumns() {
        return this.allColumns;
    }

    public void setAllColumns(List<ColumnModelDefine> allColumns) {
        this.allColumns = allColumns;
    }

    public TableModelDefine getTableModelDefine() {
        return this.tableModelDefine;
    }

    public void setTableModelDefine(TableModelDefine tableModelDefine) {
        this.tableModelDefine = tableModelDefine;
    }

    public GatherTableDefine getGatherTableDefine() {
        return this.gatherTableDefine;
    }

    public void setGatherTableDefine(GatherTableDefine gatherTableDefine) {
        this.gatherTableDefine = gatherTableDefine;
    }

    public List<ColumnModelDefine> getAllDimsColumns() {
        return this.allDimsColumns;
    }

    public void setAllDimsColumns(List<ColumnModelDefine> allDimsColumns) {
        this.allDimsColumns = allDimsColumns;
    }

    public Map<ColumnModelDefine, DataField> getGatherColumns2DataField() {
        return this.gatherColumns2DataField;
    }

    public void setGatherColumns2DataField(Map<ColumnModelDefine, DataField> gatherColumns2DataField) {
        this.gatherColumns2DataField = gatherColumns2DataField;
    }
}

