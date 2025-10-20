/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.definition.formulamapping.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaObj;
import java.util.ArrayList;
import java.util.List;

public class QueryFormulaObj
extends FormulaObj {
    private String rowId;
    @JsonIgnore
    private String meanning;
    private String errorMsg;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<QueryFormulaObj> children;

    public void addChild(QueryFormulaObj obj) {
        if (null == this.children) {
            this.children = new ArrayList<QueryFormulaObj>();
        }
        this.children.add(obj);
    }

    public String getMeanning() {
        return this.meanning;
    }

    public void setMeanning(String meanning) {
        this.meanning = meanning;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<QueryFormulaObj> getChildren() {
        return this.children;
    }

    public void setChildren(List<QueryFormulaObj> children) {
        this.children = children;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }
}

