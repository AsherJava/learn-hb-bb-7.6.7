/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.budget.masterdata.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.budget.masterdata.organization.OrgDataObjDTO;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class OrgDataObjAttrVO
extends OrgDataObjDTO {
    private String fieldName;

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "OrgDataObjAttrVO{fieldName='" + this.fieldName + '\'' + "} " + super.toString();
    }
}

