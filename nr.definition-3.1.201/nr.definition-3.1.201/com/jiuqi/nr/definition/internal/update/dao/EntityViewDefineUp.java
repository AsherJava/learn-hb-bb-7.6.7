/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.update.dao;

import com.jiuqi.np.definition.internal.anno.DBAnno;

@DBAnno.DBTable(dbTable="sys_entityviewdefine")
public class EntityViewDefineUp {
    private static final long serialVersionUID = -3754050959605074121L;
    @DBAnno.DBField(dbField="ev_key", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="ev_row_filter")
    protected String rowFilterExpression;
    @DBAnno.DBField(dbField="ev_filter_by_auth", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean filterRowByAuthority;
    @DBAnno.DBField(dbField="ev_entity_id")
    protected String entityId;

    public String getRowFilterExpression() {
        return this.rowFilterExpression;
    }

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

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

