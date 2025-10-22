/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.util.entityUtil;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataentryEntityUtils
implements IEntityUpgrader {
    @Resource
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private DataEntityFullService dataEntityFullService;

    public IEntityTable entDataQuerySet(FormSchemeDefine formscheme, EntityViewDefine view, DimensionValueSet valueSet, AuthorityType authType) throws Exception {
        IEntityQuery query = this.createEntityQuery(view, valueSet);
        query.setAuthorityOperations(authType);
        ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
        context.setPeriodView(formscheme.getDateTime());
        return this.dataEntityFullService.executeEntityReader(query, context, view, formscheme.getKey()).getEntityTable();
    }

    IEntityQuery createEntityQuery(EntityViewDefine view, DimensionValueSet valueSet) {
        IEntityQuery query = this.iEntityDataService.newEntityQuery();
        query.setEntityView(view);
        query.setMasterKeys(valueSet == null ? new DimensionValueSet() : valueSet);
        return query;
    }
}

