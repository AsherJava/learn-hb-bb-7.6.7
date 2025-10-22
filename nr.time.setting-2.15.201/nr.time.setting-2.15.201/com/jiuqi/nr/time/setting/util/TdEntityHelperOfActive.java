/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.time.setting.util.TdUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TdEntityHelperOfActive {
    private TdUtils tdEntityUtil = (TdUtils)BeanUtil.getBean(TdUtils.class);
    private Map<String, IEntityTable> dataTabelMap = new HashMap<String, IEntityTable>();
    private static final String TIME_PARENT_TIME_CACHE = "TIME_PARENT_TIME_CACHE";

    private IEntityTable getEntityDatatable(String formSchemeKey, String period) {
        String dataMapKey = formSchemeKey + "@" + period;
        if (!this.dataTabelMap.containsKey(dataMapKey)) {
            IEntityTable entityDataTable = this.tdEntityUtil.entityQuerySet(formSchemeKey, period);
            this.dataTabelMap.put(dataMapKey, entityDataTable);
        }
        return this.dataTabelMap.get(dataMapKey);
    }

    public List<IEntityRow> getRootData(String formSchemeKey, String period) {
        IEntityTable entityTable = this.getEntityDatatable(formSchemeKey, period);
        return entityTable.getRootRows();
    }

    public List<IEntityRow> getDirectChildrenData(String formSchemeKey, String period, String parentId) {
        IEntityTable entityTable = this.getEntityDatatable(formSchemeKey, period);
        return entityTable.getChildRows(parentId);
    }

    public String getParentId(String formSchemeKey, String period, String entityRowId) throws Exception {
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getDefaultExtension();
        Object object = extension.get("TIME_PARENT_TIME_CACHE:" + formSchemeKey + ":" + entityRowId + ":" + period);
        if (object != null) {
            return object.toString();
        }
        IEntityTable entityTable = this.getEntityDatatable(formSchemeKey, period);
        IEntityRow row = entityTable.findByEntityKey(entityRowId);
        if (null != row) {
            String parentEntityKey = row.getParentEntityKey();
            extension.put("TIME_PARENT_TIME_CACHE:" + formSchemeKey + ":" + entityRowId + ":" + period, (Serializable)((Object)parentEntityKey));
            return parentEntityKey;
        }
        return null;
    }

    public List<String> getDirectSubordinate(String formSchemeKey, String period, String parentId) {
        IEntityTable entityTable = this.getEntityDatatable(formSchemeKey, period);
        return entityTable.getChildRows(parentId).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public List<String> getAllSubordinate(String formSchemeKey, String period, String parentId) {
        IEntityTable entityTable = this.getEntityDatatable(formSchemeKey, period);
        return entityTable.getAllChildRows(parentId).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public List<IEntityRow> getEntityRow(String formSchemeKey, String period) {
        IEntityTable entityTable = this.getEntityDatatable(formSchemeKey, period);
        List allRows = entityTable.getAllRows();
        return allRows;
    }
}

