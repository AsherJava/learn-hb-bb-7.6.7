/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import java.util.List;
import java.util.Set;

public class ExpotrFilterResult {
    private List<FormDefine> selectFormsWithFmdmType;
    private List<FormDefine> selectFormsWithOutAuth;
    private List<EntityInfoParam> allRows;
    private Set<String> allNoAuthUnits;

    public List<FormDefine> getSelectFormsWithFmdmType() {
        return this.selectFormsWithFmdmType;
    }

    public void setSelectFormsWithFmdmType(List<FormDefine> selectFormsWithFmdmType) {
        this.selectFormsWithFmdmType = selectFormsWithFmdmType;
    }

    public List<FormDefine> getSelectFormsWithOutAuth() {
        return this.selectFormsWithOutAuth;
    }

    public void setSelectFormsWithOutAuth(List<FormDefine> selectFormsWithOutAuth) {
        this.selectFormsWithOutAuth = selectFormsWithOutAuth;
    }

    public List<EntityInfoParam> getAllRows() {
        return this.allRows;
    }

    public void setAllRows(List<EntityInfoParam> allRows) {
        this.allRows = allRows;
    }

    public Set<String> getAllNoAuthUnits() {
        return this.allNoAuthUnits;
    }

    public void setAllNoAuthUnits(Set<String> allNoAuthUnits) {
        this.allNoAuthUnits = allNoAuthUnits;
    }
}

