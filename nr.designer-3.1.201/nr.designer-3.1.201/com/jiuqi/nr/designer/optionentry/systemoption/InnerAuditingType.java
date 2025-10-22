/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.nr.definition.facade.AuditType
 */
package com.jiuqi.nr.designer.optionentry.systemoption;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.definition.facade.AuditType;
import java.util.List;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class InnerAuditingType {
    private String address;
    private String systemId;
    private String optionId;
    private List<AuditType> object;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AuditType> getObject() {
        return this.object;
    }

    public void setObject(List<AuditType> object) {
        this.object = object;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getOptionId() {
        return this.optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
}

