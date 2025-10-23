/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.sbdata.carry.bean;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;

public class TzClearDataParam
implements Serializable {
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection masterKey;
    private String formKey;
    private String dataTableKey;
    private List<DataField> fields;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCollection getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionCollection masterKey) {
        this.masterKey = masterKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public List<DataField> getFields() {
        return this.fields;
    }

    public void setFields(List<DataField> fields) {
        this.fields = fields;
    }
}

