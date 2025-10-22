/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.sensitive.bean.viewObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import com.jiuqi.nr.sensitive.common.SensitiveWordType;

public class SensitiveWordViewObject {
    private String sensitiveWordKey;
    private String sensitiveType;
    private Integer sensitiveWordType;
    private String sensitiveInfo;
    private String sensitiveDescription;
    private boolean isEffective;

    public SensitiveWordViewObject() {
    }

    public SensitiveWordViewObject(SensitiveWordDaoObject sensitiveWordDaoObject) {
        this.sensitiveWordKey = sensitiveWordDaoObject.getSensitiveWordKey();
        this.sensitiveType = sensitiveWordDaoObject.getSensitiveType().equals(SensitiveWordType.REGULAR_EXPRESSION.getValue()) ? "expressionType" : "stringType";
        this.sensitiveWordType = sensitiveWordDaoObject.getSensitiveWordType();
        this.sensitiveInfo = sensitiveWordDaoObject.getSensitiveInfo();
        this.sensitiveDescription = sensitiveWordDaoObject.getSensitiveDescription();
        this.isEffective = sensitiveWordDaoObject.getIsEffective() == 0;
    }

    public String getSensitiveWordKey() {
        return this.sensitiveWordKey;
    }

    public void setSensitiveWordKey(String sensitiveWordKey) {
        this.sensitiveWordKey = sensitiveWordKey;
    }

    public String getSensitiveType() {
        return this.sensitiveType;
    }

    public void setSensitiveType(String sensitiveType) {
        this.sensitiveType = sensitiveType;
    }

    public String getSensitiveInfo() {
        return this.sensitiveInfo;
    }

    public void setSensitiveInfo(String sensitiveInfo) {
        this.sensitiveInfo = sensitiveInfo;
    }

    public String getSensitiveDescription() {
        return this.sensitiveDescription;
    }

    public void setSensitiveDescription(String sensitiveDescription) {
        this.sensitiveDescription = sensitiveDescription;
    }

    public Integer getSensitiveWordType() {
        return this.sensitiveWordType;
    }

    public void setSensitiveWordType(Integer sensitiveWordType) {
        this.sensitiveWordType = sensitiveWordType;
    }

    @JsonProperty(value="isEffective")
    public boolean isEffective() {
        return this.isEffective;
    }

    public void setEffective(boolean effective) {
        this.isEffective = effective;
    }
}

