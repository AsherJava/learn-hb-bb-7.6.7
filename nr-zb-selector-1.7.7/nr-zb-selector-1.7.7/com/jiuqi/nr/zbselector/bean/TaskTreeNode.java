/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.zbselector.bean;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.zbselector.bean.TaskNodeType;

public class TaskTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String order;
    private String parentKey;
    private TaskNodeType type;

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

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public TaskNodeType getType() {
        return this.type;
    }

    public void setType(TaskNodeType type) {
        this.type = type;
    }
}

