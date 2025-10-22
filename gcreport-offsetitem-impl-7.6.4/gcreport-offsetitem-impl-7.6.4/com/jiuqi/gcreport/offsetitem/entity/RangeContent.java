/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.entity;

import java.sql.Blob;

public class RangeContent {
    private String ID;
    private Blob CONTENT;

    public String toString() {
        return "RangeContent{id='" + this.ID + '\'' + ", content='" + this.CONTENT + '\'' + '}';
    }

    public String getId() {
        return this.ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public Blob getContent() {
        return this.CONTENT;
    }

    public void setContent(Blob content) {
        this.CONTENT = content;
    }
}

