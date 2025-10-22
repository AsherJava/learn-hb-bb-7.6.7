/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.efdc.internal.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.efdc.internal.service.IEFDCEntityUpgrader;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IEFDCEntityUpgraderImpl
implements IEFDCEntityUpgrader {
    private static final Logger log = LoggerFactory.getLogger(IEFDCEntityUpgraderImpl.class);
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    @Override
    public String getTargetKey(EntityViewData entityInfo, DimensionValueSet dimensionValueSet) {
        String targetKey = null;
        boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityInfo.getKey());
        if (!periodView && dimensionValueSet.hasValue(entityInfo.getDimensionName())) {
            targetKey = dimensionValueSet.getValue(entityInfo.getDimensionName()).toString();
        }
        return targetKey;
    }

    @Override
    public String getPeriodCode(String periodCode, EntityViewData entityInfo, DimensionValueSet dimensionValueSet) {
        boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityInfo.getKey());
        if (periodView && StringUtils.isEmpty((String)periodCode) && dimensionValueSet.hasValue(entityInfo.getDimensionName())) {
            periodCode = dimensionValueSet.getValue(entityInfo.getDimensionName()).toString();
        }
        return periodCode;
    }

    @Override
    public Map<String, String> getDimMap(EntityViewData entityInfo, DimensionValueSet dimensionValueSet, EfdcInfo efdcInfo) {
        HashMap<String, String> dimMap = new HashMap<String, String>();
        boolean periodView = this.periodEntityAdapter.isPeriodEntity(entityInfo.getKey());
        if (!entityInfo.isMasterEntity() && !periodView && dimensionValueSet.hasValue(entityInfo.getDimensionName())) {
            for (Map.Entry<String, DimensionValue> entry : efdcInfo.getDimensionSet().entrySet()) {
                if (entry.getKey().indexOf(entityInfo.getTableName()) == -1) continue;
                dimMap.put(entityInfo.getTableName(), entry.getValue().getValue());
            }
        }
        return dimMap;
    }

    @Override
    public IEntityTable getEntityTable(String entityId, Map<String, String> dimMap) {
        try {
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)dimMap.get("DATATIME"));
            entityQuery.setMasterKeys(dimensionValueSet);
            entityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(entityId));
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            return entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<IEntityRow> queryRowsByBizKeys(String entityId, DimensionValueSet valueSet) {
        List rootRows = null;
        try {
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(entityId));
            entityQuery.setMasterKeys(valueSet);
            IEntityTable entityTable = entityQuery.executeReader(null);
            rootRows = entityTable.getAllRows();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rootRows;
    }

    @Override
    public List<IEntityRow> queryAllRows(String entityId) {
        List rootRows = null;
        try {
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(entityId));
            entityQuery.setAuthorityOperations(AuthorityType.None);
            IEntityTable entityTable = entityQuery.executeReader(null);
            rootRows = entityTable.getAllRows();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rootRows;
    }
}

