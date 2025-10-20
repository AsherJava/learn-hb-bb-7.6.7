/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.vo;

import java.util.HashMap;
import java.util.Map;

public class ClbrReceiveSettingVO {
    private String id;
    private String oppClbrTypes;
    private String oppRelation;
    private String roleCodes;
    private String userNames;
    private String department;
    private Map<String, String> oppClbrTypesMap = new HashMap<String, String>();
    private Map<String, String> oppRelationMap = new HashMap<String, String>();
    private Map<String, String> usersMap = new HashMap<String, String>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Map<String, String> getOppClbrTypesMap() {
        return this.oppClbrTypesMap;
    }

    public void setOppClbrTypesMap(Map<String, String> oppClbrTypesMap) {
        this.oppClbrTypesMap = oppClbrTypesMap;
    }

    public Map<String, String> getOppRelationMap() {
        return this.oppRelationMap;
    }

    public void setOppRelationMap(Map<String, String> oppRelationMap) {
        this.oppRelationMap = oppRelationMap;
    }

    public Map<String, String> getUsersMap() {
        return this.usersMap;
    }

    public void setUsersMap(Map<String, String> usersMap) {
        this.usersMap = usersMap;
    }
}

