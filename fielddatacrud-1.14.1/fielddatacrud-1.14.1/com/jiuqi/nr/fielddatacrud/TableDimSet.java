/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableDimSet {
    private DataTable dataTable;
    private String dwDimName;
    private Map<String, String> fieldCode2DimName = new HashMap<String, String>();
    private Map<String, String> dimName2FieldCode = new HashMap<String, String>();
    private ENameSet masterKeyName = new ENameSet();
    private ENameSet tableKeyName = new ENameSet();
    private List<DataField> dimField = new ArrayList<DataField>();
    private List<DataField> tableDimField = new ArrayList<DataField>();

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public Map<String, String> getFieldCode2DimName() {
        return this.fieldCode2DimName;
    }

    public void setFieldCode2DimName(Map<String, String> fieldCode2DimName) {
        this.fieldCode2DimName = fieldCode2DimName;
    }

    public Map<String, String> getDimName2FieldCode() {
        return this.dimName2FieldCode;
    }

    public void setDimName2FieldCode(Map<String, String> dimName2FieldCode) {
        this.dimName2FieldCode = dimName2FieldCode;
    }

    public ENameSet getMasterKeyName() {
        return this.masterKeyName;
    }

    public void setMasterKeyName(ENameSet masterKeyName) {
        this.masterKeyName = masterKeyName;
    }

    public List<DataField> getDimField() {
        return this.dimField;
    }

    public void setDimField(List<DataField> dimField) {
        this.dimField = dimField;
    }

    public List<DataField> getTableDimField() {
        return this.tableDimField;
    }

    public void setTableDimField(List<DataField> tableDimField) {
        this.tableDimField = tableDimField;
    }

    public ENameSet getTableKeyName() {
        return this.tableKeyName;
    }

    public void setTableKeyName(ENameSet tableKeyName) {
        this.tableKeyName = tableKeyName;
    }

    public String getDwDimName() {
        return this.dwDimName;
    }

    public void setDwDimName(String dwDimName) {
        this.dwDimName = dwDimName;
    }
}

