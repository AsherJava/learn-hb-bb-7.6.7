/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.ActionReturnObject;

public class ActionReturnUrl
implements ActionReturnObject {
    private String url;
    private String name;
    private Boolean replace;
    private String specs;
    private Boolean doublescan;
    protected String type = "url";

    @Override
    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getReplace() {
        return this.replace;
    }

    public void setReplace(Boolean replace) {
        this.replace = replace;
    }

    public String getSpecs() {
        return this.specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDoublescan() {
        return this.doublescan;
    }

    public void setDoublescan(Boolean doublescan) {
        this.doublescan = doublescan;
    }
}

