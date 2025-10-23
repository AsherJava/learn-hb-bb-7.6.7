/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.param.transfer.datascheme.dto.DataFieldLanguageDTO;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TransferTableDTO {
    private DataTable dataTable;
    private List<DesignDataField> fields;
    private List<DataFieldLanguageDTO> fieldLanguages;

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<DesignDataField> getFields() {
        return this.fields;
    }

    public void setFields(List<DesignDataField> fields) {
        this.fields = fields;
    }

    public List<DataFieldLanguageDTO> getFieldLanguages() {
        return this.fieldLanguages;
    }

    public void setFieldLanguages(List<DataFieldLanguageDTO> fieldLanguages) {
        this.fieldLanguages = fieldLanguages;
    }

    public String toString() {
        return "TransferTableDTO{dataTable=" + this.dataTable + ", fields=" + this.fields + ", fieldLanguages=" + this.fieldLanguages + '}';
    }
}

