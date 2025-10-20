/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.update.dao;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.internal.update.dao.EntityViewDefineUp;

@DBAnno.DBTable(dbTable="des_sys_entityviewdefine")
public class EntityViewDefineDesUp
extends EntityViewDefineUp {
    @DBAnno.DBField(dbField="ev_key", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="ev_row_filter")
    protected String rowFilterExpression;
    @DBAnno.DBField(dbField="ev_filter_by_auth", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean filterRowByAuthority;
    @DBAnno.DBField(dbField="ev_entity_id")
    protected String entityId;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getRowFilterExpression() {
        return this.rowFilterExpression;
    }

    @Override
    public void setRowFilterExpression(String rowFilterExpression) {
        this.rowFilterExpression = rowFilterExpression;
    }

    @Override
    public boolean isFilterRowByAuthority() {
        return this.filterRowByAuthority;
    }

    @Override
    public void setFilterRowByAuthority(boolean filterRowByAuthority) {
        this.filterRowByAuthority = filterRowByAuthority;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}

