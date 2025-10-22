/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.time.setting.util.TdUtils;
import com.jiuqi.nr.time.setting.util.TimeEntityUpgraderImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TdEntityHelper
extends TimeEntityUpgraderImpl
implements IEntityUpgrader {
    @Autowired
    private TdUtils tdEntityUtil;

    public List<IEntityRow> getRootData(String formSchemeKey, String period) {
        IEntityTable entityTable = this.tdEntityUtil.entityQuerySet(formSchemeKey, period);
        return entityTable.getRootRows();
    }

    public List<IEntityRow> getDirectChildrenData(String formSchemeKey, String period, String parentId) {
        IEntityTable entityTable = this.tdEntityUtil.entityQuerySet(formSchemeKey, period);
        return entityTable.getChildRows(parentId);
    }

    public String getParentId(String formSchemeKey, String period, String entityRowId) throws Exception {
        IEntityTable entityTable = this.tdEntityUtil.entityQuerySet(formSchemeKey, period);
        IEntityRow row = entityTable.findByEntityKey(entityRowId);
        if (null != row) {
            return row.getParentEntityKey();
        }
        return null;
    }

    public List<String> getDirectSubordinate(String formSchemeKey, String period, String parentId) {
        IEntityTable entityTable = this.tdEntityUtil.entityQuerySet(formSchemeKey, period);
        return entityTable.getChildRows(parentId).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public List<String> getAllSubordinate(String formSchemeKey, String period, String parentId) {
        IEntityTable entityTable = this.tdEntityUtil.entityQuerySet(formSchemeKey, period);
        return entityTable.getAllChildRows(parentId).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public List<IEntityRow> getEntityRow(String formSchemeKey, String period) {
        IEntityTable entityTable = this.tdEntityUtil.entityQuerySet(formSchemeKey, period);
        List allRows = entityTable.getAllRows();
        return allRows;
    }
}

