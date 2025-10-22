/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.nr.common.itree.INode;

public class EntityData
implements INode {
    private String key;
    private String title;
    private String code;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EntityData(String key, String title, String code) {
        this.key = key;
        this.title = title;
        this.code = code;
    }

    public EntityData() {
    }
}

