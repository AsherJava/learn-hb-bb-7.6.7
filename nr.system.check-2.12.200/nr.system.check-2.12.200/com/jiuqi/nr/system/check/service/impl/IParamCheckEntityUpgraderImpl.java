/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.system.check.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.system.check.service.IParamCheckEntityUpgrader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IParamCheckEntityUpgraderImpl
implements IParamCheckEntityUpgrader {
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Override
    public List<IPeriodRow> getCustomPeriodDataList(String periodEntityKey) throws Exception {
        return this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityKey).getPeriodItems();
    }

    @Override
    public List<String> getAllUnitKeyForEntity(EntityViewDefine entityViewDefine, String periodCode, Map<String, String> unitTitle) throws Exception {
        ArrayList<String> allUnitKey = new ArrayList<String>();
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)periodCode);
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        entityQuery.setIgnoreViewFilter(false);
        ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
        IEntityTable entityTable = entityQuery.executeReader((IContext)executorContext);
        List iEntityRows = entityTable.getAllRows();
        if (iEntityRows != null) {
            for (IEntityRow iEntityRow : iEntityRows) {
                allUnitKey.add(iEntityRow.getEntityKeyData());
                unitTitle.put(iEntityRow.getEntityKeyData(), iEntityRow.getTitle());
            }
        }
        return allUnitKey;
    }
}

