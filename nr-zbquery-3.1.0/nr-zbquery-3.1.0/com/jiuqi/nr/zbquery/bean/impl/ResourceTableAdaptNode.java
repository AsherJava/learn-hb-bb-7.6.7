/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.DimAttribute
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.datascheme.api.DataField;

public class ResourceTableAdaptNode {
    private static final String ENTITY_TYPE = "entity";
    private static final String FIELD_TYPE = "field";
    private String code;
    private String title;
    private String referFieldKey;
    private String tableCode;

    public ResourceTableAdaptNode() {
    }

    public ResourceTableAdaptNode(DimAttribute dimAttribute) {
        this.code = dimAttribute.getCode();
        this.title = dimAttribute.getTitle();
        this.referFieldKey = FIELD_TYPE;
    }

    public ResourceTableAdaptNode(DataField dataField) {
        this.code = dataField.getCode();
        this.title = dataField.getTitle();
        this.referFieldKey = FIELD_TYPE;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReferFieldKey() {
        return this.referFieldKey;
    }

    public void setReferFieldKey(String referFieldKey) {
        this.referFieldKey = referFieldKey;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}

