/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.task.api.tree.TreeData
 */
package com.jiuqi.nr.task.form.controller.vo;

import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.task.api.tree.TreeData;

public class DataSchemeTreeData
implements TreeData,
DataSchemeNode {
    private int type;
    private String parentKey;
    private String key;
    private String code;
    private String title;

    public DataSchemeTreeData() {
    }

    public DataSchemeTreeData(DataSchemeNode node) {
        if (node != null) {
            this.key = node.getKey();
            this.code = node.getCode();
            this.type = node.getType();
            this.title = node.getTitle();
            this.parentKey = node.getParentKey();
        }
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

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
}

