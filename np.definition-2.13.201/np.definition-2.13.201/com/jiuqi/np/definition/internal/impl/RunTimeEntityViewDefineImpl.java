/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.impl;

import com.jiuqi.np.definition.facade.EntityViewDefine;

public class RunTimeEntityViewDefineImpl
implements EntityViewDefine {
    private static final long serialVersionUID = -3754050959605074121L;
    private String rowFilterExpression;
    private boolean filterRowByAuthority;
    private String entityId;

    @Override
    public String getRowFilterExpression() {
        return this.rowFilterExpression;
    }

    @Override
    public boolean getFilterRowByAuthority() {
        return this.filterRowByAuthority;
    }

    public void setRowFilterExpression(String rowFilterExpression) {
        this.rowFilterExpression = rowFilterExpression;
    }

    public boolean isFilterRowByAuthority() {
        return this.filterRowByAuthority;
    }

    public void setFilterRowByAuthority(boolean filterRowByAuthority) {
        this.filterRowByAuthority = filterRowByAuthority;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

