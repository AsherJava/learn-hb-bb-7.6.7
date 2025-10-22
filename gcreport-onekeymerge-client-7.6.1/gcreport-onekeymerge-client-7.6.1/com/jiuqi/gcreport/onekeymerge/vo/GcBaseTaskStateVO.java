/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

public class GcBaseTaskStateVO {
    private String id;
    private String state;
    private Boolean hasChildren;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getHasChildren() {
        return this.hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}

