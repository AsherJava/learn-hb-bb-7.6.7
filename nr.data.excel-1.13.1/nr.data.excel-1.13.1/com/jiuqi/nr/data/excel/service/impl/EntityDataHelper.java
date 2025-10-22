/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.service.IOEntityIsolateCondition
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.excel.utils.ExportUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.service.IOEntityIsolateCondition;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityDataHelper {
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IOEntityIsolateCondition entityIsolateCondition;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    public IEntityTable getIsoReadEntityTable(String queryEntityId, FormSchemeDefine formScheme, DimensionCombination dimensionCombination) throws Exception {
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(queryEntityId);
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(entityViewDefine);
        String periodDimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName(formScheme.getDateTime());
        String period = String.valueOf(dimensionCombination.getValue(periodDimensionName));
        Date entityQueryVersionDate = ExportUtil.getEntityQueryVersionDate(formScheme.getDateTime(), period);
        if (entityQueryVersionDate != null) {
            query.setQueryVersionDate(entityQueryVersionDate);
        }
        query.setAuthorityOperations(AuthorityType.Read);
        query.maskedData();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(queryEntityId);
        if (entityDefine.getIsolation() != 0) {
            if (this.entityIsolateCondition != null) {
                String queryIsoCondition = this.entityIsolateCondition.queryIsoCondition(formScheme.getTaskKey(), period, queryEntityId);
                query.setIsolateCondition(queryIsoCondition);
            } else {
                String dimensionName = this.entityMetaService.getDimensionName(formScheme.getDw());
                query.setIsolateCondition(String.valueOf(dimensionCombination.getValue(dimensionName)));
            }
        }
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        return query.executeReader((IContext)context);
    }
}

