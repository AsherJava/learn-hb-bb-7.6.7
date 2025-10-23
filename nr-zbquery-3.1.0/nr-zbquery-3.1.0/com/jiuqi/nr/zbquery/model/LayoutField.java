/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.QueryObjectType;

public class LayoutField {
    private String fullName;
    private QueryObjectType type;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public QueryObjectType getType() {
        return this.type;
    }

    public void setType(QueryObjectType type) {
        this.type = type;
    }

    public String toString() {
        return "{fullName=" + this.fullName + ", type=" + (Object)((Object)this.type) + "}";
    }
}

