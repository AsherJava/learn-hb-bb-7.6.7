/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.definition.impl.basic.entity;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import java.util.List;

public abstract class ShardingBaseEntity
extends BaseEntity {
    private String tableName;

    public abstract List<String> getShardingList();

    public abstract String getTableNamePrefix();

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getTableName() {
        if (StringUtils.isEmpty((String)this.tableName)) {
            return this.getTableNamePrefix();
        }
        return this.tableName;
    }
}

