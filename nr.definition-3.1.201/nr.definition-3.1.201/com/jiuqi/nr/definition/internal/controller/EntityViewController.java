/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.controller.IEntityViewController;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityViewController
implements IEntityViewController {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    @Override
    public String getDimensionNameByViewKey(String entityId) throws JQException {
        String dimensionName = !this.judgementPeriodView(entityId) ? this.entityMetaService.getDimensionName(entityId) : this.periodEntityAdapter.getPeriodDimensionName(entityId);
        return dimensionName;
    }

    @Override
    public String getBizFieldKeyByViewKey(String entityId) throws JQException {
        String bizFieldKey = null;
        if (!this.judgementPeriodView(entityId)) {
            IEntityAttribute bizKeyField;
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            if (entityModel != null && (bizKeyField = entityModel.getBizKeyField()) != null) {
                bizFieldKey = bizKeyField.getID();
            }
        } else {
            TableModelDefine periodViewByKey = this.periodEntityAdapter.getPeriodEntityTableModel(entityId);
            if (periodViewByKey != null) {
                bizFieldKey = periodViewByKey.getBizKeys();
            }
        }
        return bizFieldKey;
    }

    @Override
    public String getTableModelKey(String entityId) throws JQException {
        String tableKey = null;
        if (!this.judgementPeriodView(entityId)) {
            TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
            if (tableModel != null) {
                tableKey = tableModel.getID();
            }
        } else {
            TableModelDefine periodEntityTableModel = this.periodEntityAdapter.getPeriodEntityTableModel(entityId);
            if (periodEntityTableModel != null) {
                tableKey = periodEntityTableModel.getID();
            }
        }
        return tableKey;
    }

    private boolean judgementPeriodView(String entityId) {
        return this.periodEntityAdapter.isPeriodEntity(entityId);
    }
}

