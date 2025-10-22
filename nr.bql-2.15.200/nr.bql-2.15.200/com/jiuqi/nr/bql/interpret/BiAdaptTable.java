/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.cache.graph.TableNode
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.adhoc.cache.graph.TableNode;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BiAdaptTable {
    private DataTable dataTable;
    private String periodTableName;
    private PeriodType periodType;
    private Map<String, DataField> dimFields = new HashMap<String, DataField>();
    private Set<String> innerDimFields = new HashSet<String>();
    private TableNode tableNode;

    public BiAdaptTable(DataTable dataTable, TableNode tableNode) {
        this.dataTable = dataTable;
        this.tableNode = tableNode;
    }

    public String getCode() {
        return this.dataTable.getCode();
    }

    public DataField getDimField(String dimName) {
        return this.dimFields.get(dimName);
    }

    public void addDimField(String dimName, DataField dimField) {
        this.dimFields.put(dimName, dimField);
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public String getPeriodTableName() {
        return this.periodTableName;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodTableName(String periodTableName) {
        this.periodTableName = periodTableName;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public boolean isFloat() {
        return this.dataTable.getDataTableType() != DataTableType.TABLE;
    }

    public boolean isRepeatCode() {
        return this.dataTable.isRepeatCode();
    }

    public TableNode getTableNode() {
        return this.tableNode;
    }

    public void setTableNode(TableNode tableNode) {
        this.tableNode = tableNode;
    }

    public Set<String> getInnerDimFields() {
        return this.innerDimFields;
    }
}

