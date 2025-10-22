/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.efdc.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.efdc.internal.service.IEFDCEntityUpgrader;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityDataSerivce {
    @Autowired
    private IEFDCEntityUpgrader iefdcEntityUpgrader;

    public List<IEntityRow> queryRowsByBizKeys(String entityId, DimensionValueSet valueSet) {
        return this.iefdcEntityUpgrader.queryRowsByBizKeys(entityId, valueSet);
    }

    public List<IEntityRow> queryAllRows(String entityId) {
        return this.iefdcEntityUpgrader.queryAllRows(entityId);
    }

    public IEntityTable getEntityTable(String entityId, Map<String, String> dimMap) {
        return this.iefdcEntityUpgrader.getEntityTable(entityId, dimMap);
    }
}

