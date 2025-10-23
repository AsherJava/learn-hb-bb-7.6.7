/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.intf.bean;

import java.io.Serializable;

public class SearchModelItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String modelId;
    private String name;
    private boolean result;
    private Integer type;
    private String org;

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}

