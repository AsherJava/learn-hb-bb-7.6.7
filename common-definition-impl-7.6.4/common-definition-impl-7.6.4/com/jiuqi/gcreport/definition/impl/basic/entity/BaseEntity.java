/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.entity;

import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;

public abstract class BaseEntity
extends AbstractFieldDynamicDeclarator {
    private static final long serialVersionUID = 1L;

    public abstract String getId();

    public abstract void setId(String var1);

    public Long getRecver() {
        return null;
    }

    public void setRecver(Long recver) {
    }

    public abstract String getTableName();
}

