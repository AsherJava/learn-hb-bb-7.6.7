/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.task.form.controller.dto;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;

public class ReverseFieldCodeDTO {
    private String code;
    private String key;
    private DataFieldKind dataFieldKind;
    private String dataTableKey;

    public ReverseFieldCodeDTO(DesignDataField dataField) {
        this.dataFieldKind = dataField.getDataFieldKind();
        this.dataTableKey = dataField.getDataTableKey();
        this.code = dataField.getCode();
        this.key = dataField.getKey();
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataFieldKind getDataFieldKind() {
        return this.dataFieldKind;
    }

    public void setDataFieldKind(DataFieldKind dataFieldKind) {
        this.dataFieldKind = dataFieldKind;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }
}

