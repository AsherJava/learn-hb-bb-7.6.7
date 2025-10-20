/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.common.itree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.common.itree.INode;

public class INodeImpl
implements INode {
    @JsonIgnore
    private String key;
    @JsonIgnore
    private String code;
    @JsonIgnore
    private String title;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

