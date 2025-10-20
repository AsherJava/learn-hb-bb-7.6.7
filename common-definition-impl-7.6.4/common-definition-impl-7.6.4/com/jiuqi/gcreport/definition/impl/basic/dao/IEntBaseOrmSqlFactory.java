/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.dao;

import com.jiuqi.gcreport.definition.impl.basic.base.provider.EntityTableDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.TableSqlDeclarator;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;

public interface IEntBaseOrmSqlFactory<Entity extends BaseEntity> {
    public EntityTableDeclarator<Entity> getTableDeclarator();

    public void setTableDeclarator(EntityTableDeclarator<Entity> var1);

    public TableSqlDeclarator<Entity> getSqlDeclarator();

    public void setSqlDeclarator(TableSqlDeclarator<Entity> var1);

    public Class<Entity> getEntityType();

    public String getTableName();
}

