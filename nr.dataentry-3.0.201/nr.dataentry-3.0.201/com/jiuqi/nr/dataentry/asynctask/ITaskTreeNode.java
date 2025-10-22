/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

public class ITaskTreeNode
implements Serializable {
    private static final long serialVersionUID = -1L;
    private String key;
    private String code;
    private String title;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<ITaskTreeNode> children;

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

    public List<ITaskTreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<ITaskTreeNode> children) {
        this.children = children;
    }
}

