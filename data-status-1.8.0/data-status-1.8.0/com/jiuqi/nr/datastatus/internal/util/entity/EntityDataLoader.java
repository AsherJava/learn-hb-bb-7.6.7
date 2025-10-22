/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.datastatus.internal.util.entity;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datastatus.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.HashMap;
import java.util.Map;

public class EntityDataLoader
implements IDimDataLoader {
    private final Map<String, IEntityRow> cache = new HashMap<String, IEntityRow>();
    private final IEntityTable entityTable;
    private final String rowFilter;

    public EntityDataLoader(IEntityTable entityTable, String rowFilter) {
        this.entityTable = entityTable;
        this.rowFilter = rowFilter;
    }

    public IEntityRow getRowByEntityDataKey(String entityDataKey) {
        if (this.cache.containsKey(entityDataKey)) {
            return this.cache.get(entityDataKey);
        }
        IEntityRow row = StringUtils.isNotEmpty((String)this.rowFilter) ? this.entityTable.findByEntityKey(entityDataKey) : this.entityTable.quickFindByEntityKey(entityDataKey);
        if (row == null) {
            row = this.entityTable.findByCode(entityDataKey);
        }
        this.cache.put(entityDataKey, row);
        return row;
    }

    @Override
    public String getCodeByEntityDataKey(String entityDataKey) {
        IEntityRow entityRow = this.getRowByEntityDataKey(entityDataKey);
        if (entityRow != null) {
            return entityRow.getCode();
        }
        return null;
    }

    @Override
    public String getTitleByEntityDataKey(String entityDataKey) {
        IEntityRow entityRow = this.getRowByEntityDataKey(entityDataKey);
        if (entityRow != null) {
            return entityRow.getTitle();
        }
        return null;
    }
}

