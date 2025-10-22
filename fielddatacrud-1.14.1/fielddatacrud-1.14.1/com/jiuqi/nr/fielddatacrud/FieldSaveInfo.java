/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import java.util.List;

public class FieldSaveInfo {
    private ImpMode mode;
    private DimensionCollection masterKey;
    private String dataTableKey;
    private List<IMetaData> fields;
    private List<Variable> variables;
    private List<RowFilter> rowFilters;
    private ResouceType authMode = ResouceType.FORM;

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public ImpMode getMode() {
        return this.mode;
    }

    public void setMode(ImpMode mode) {
        this.mode = mode;
    }

    public DimensionCollection getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionCollection masterKey) {
        this.masterKey = masterKey;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public ResouceType getAuthMode() {
        return this.authMode;
    }

    public void setAuthMode(ResouceType resouceType) {
        this.authMode = resouceType;
    }

    public List<IMetaData> getFields() {
        return this.fields;
    }

    public void setFields(List<IMetaData> fields) {
        this.fields = fields;
    }

    public List<RowFilter> getRowFilters() {
        return this.rowFilters;
    }

    public void setRowFilters(List<RowFilter> rowFilters) {
        this.rowFilters = rowFilters;
    }
}

