/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class EntityData {
    private String tableName;
    private IEntityDefine entityDefine;
    private IPeriodEntity periodEntity;
    private String kind;

    public EntityData() {
    }

    public EntityData(IEntityDefine entityDefine) {
        this.entityDefine = entityDefine;
        this.kind = "NORMAL_ENTITY_KIND";
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        TableModelDefine tableModel = entityMetaService.getTableModel(entityDefine.getId());
        this.tableName = tableModel.getName();
    }

    public EntityData(IPeriodEntity periodEntity) {
        this.periodEntity = periodEntity;
        this.kind = "PERIOD_ENTITY_KIND";
    }

    public EntityData(String kind) {
        this.kind = kind;
    }

    public String getTableName() {
        return this.tableName;
    }

    public IEntityDefine getEntityDefine() {
        return this.entityDefine;
    }

    public IPeriodEntity getPeriodEntity() {
        return this.periodEntity;
    }

    public String getKind() {
        return this.kind;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setEntityDefine(IEntityDefine entityDefine) {
        this.entityDefine = entityDefine;
    }

    public void setPeriodEntity(IPeriodEntity periodEntity) {
        this.periodEntity = periodEntity;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKey() {
        if ("NORMAL_ENTITY_KIND".equals(this.kind)) {
            return this.entityDefine.getId();
        }
        if ("ADJUST_ENTITY_KIND".equals(this.kind)) {
            return "ADJUST";
        }
        return this.periodEntity.getKey();
    }

    public String getDimensionName() {
        if ("NORMAL_ENTITY_KIND".equals(this.kind)) {
            return this.entityDefine.getDimensionName();
        }
        if ("ADJUST_ENTITY_KIND".equals(this.kind)) {
            return "ADJUST";
        }
        return this.periodEntity.getDimensionName();
    }
}

