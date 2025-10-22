/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.definition.internal.provider;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.internal.provider.IBusinessEntityService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessEntityServiceImpl
implements IBusinessEntityService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessEntityServiceImpl.class);
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public TableModelDefine queryTableModelByEntityId(String entityId) {
        boolean isPeriod = this.periodEntityAdapter.isPeriodEntity(entityId);
        return this.getTableModelDefineByEntityId(entityId, isPeriod);
    }

    @Override
    public String getDimensionNameByEntityId(String entityId) {
        boolean isPeriod = this.periodEntityAdapter.isPeriodEntity(entityId);
        return this.getDimensionName(entityId, isPeriod);
    }

    private String getDimensionName(String entityId, boolean isPeriod) {
        String dimensionName = null;
        if (isPeriod) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityId);
            if (periodEntity != null) {
                dimensionName = periodEntity.getDimensionName();
            }
        } else {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            if (entityDefine != null) {
                dimensionName = entityDefine.getDimensionName();
            }
        }
        return dimensionName;
    }

    @Override
    public String getBusinessFieldCode(FieldDefine define) {
        return "MDCODE";
    }

    private TableModelDefine getTableModelDefineByEntityId(String entityId, boolean isPeriod) {
        TableModelDefine tableModelDefine = isPeriod ? this.periodEntityAdapter.getPeriodEntityTableModel(entityId) : this.entityMetaService.getTableModel(entityId);
        return tableModelDefine;
    }
}

