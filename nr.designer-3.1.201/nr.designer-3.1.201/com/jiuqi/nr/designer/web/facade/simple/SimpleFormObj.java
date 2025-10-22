/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.designer.web.facade.simple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.definition.facade.DesignFormDefine;

public class SimpleFormObj {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="SerialNumber")
    private String serialNumber;
    @JsonProperty(value="OwnGroupId")
    private String ownGroupId;
    @JsonProperty(value="FormType")
    private int formType;

    public SimpleFormObj() {
    }

    public SimpleFormObj(String formGroupKey, DesignFormDefine formDefine) {
        if (formDefine != null) {
            this.id = formDefine.getKey();
            this.title = formDefine.getTitle();
            this.code = formDefine.getFormCode();
            this.order = formDefine.getOrder();
            this.serialNumber = formDefine.getSerialNumber();
            this.ownGroupId = formGroupKey;
            this.formType = formDefine.getFormType().getValue();
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOwnGroupId() {
        return this.ownGroupId;
    }

    public void setOwnGroupId(String ownGroupId) {
        this.ownGroupId = ownGroupId;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }
}

