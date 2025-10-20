/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

public class ClbrReceiveSettingDTO {
    private String id;
    private String oppClbrTypes;
    private String oppRelation;
    private String roleCodes;
    private String userNames;
    private String department;

    public String getOppClbrTypes() {
        return this.oppClbrTypes;
    }

    public void setOppClbrTypes(String oppClbrTypes) {
        this.oppClbrTypes = oppClbrTypes;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public String getRoleCodes() {
        return this.roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getUserNames() {
        return this.userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

