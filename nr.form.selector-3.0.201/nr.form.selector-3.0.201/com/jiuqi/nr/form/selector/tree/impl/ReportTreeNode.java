/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;

public class ReportTreeNode
implements IReportTreeNode {
    @JsonIgnore
    private String key;
    @JsonIgnore
    private String code;
    @JsonIgnore
    private String title;
    private String type;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

