/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.impl.va.impl;

import java.util.ArrayList;
import java.util.List;

public class BaseDataTempDO {
    private String id;
    private String code;
    private String parentcode;
    private String parents;
    private List<BaseDataTempDO> childrens;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentcode() {
        return this.parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public List<BaseDataTempDO> getChildrens() {
        if (this.childrens == null) {
            this.childrens = new ArrayList<BaseDataTempDO>();
        }
        return this.childrens;
    }

    public void setChildrens(List<BaseDataTempDO> childrens) {
        this.childrens = childrens;
    }

    public void addChildren(BaseDataTempDO children) {
        this.getChildrens().add(children);
    }
}

