/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.service.IEntityDataQueryAssist
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.datascheme.api.service.IEntityDataQueryAssist;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class DimensionEnvironment {
    private static IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private static IRunTimeViewController runTimeViewController;
    private static IEntityViewRunTimeController entityViewRunTimeController;
    private static IEntityDataService entityDataService;
    private static IRuntimeDataSchemeService dataSchemeService;
    private static IEntityMetaService entityMetaService;
    private static IEntityDataQueryAssist entityDataQueryAssist;
    private static IPeriodEntityAdapter periodEntityAdapter;
    private static DimensionProviderFactory factory;

    @Autowired
    public void setDataDefinitionRuntimeController(IDataDefinitionRuntimeController dataDefinitionRuntimeController) {
        DimensionEnvironment.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
    }

    @Autowired
    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        DimensionEnvironment.runTimeViewController = runTimeViewController;
    }

    @Autowired
    public void setEntityViewRunTimeController(IEntityViewRunTimeController entityViewRunTimeController) {
        DimensionEnvironment.entityViewRunTimeController = entityViewRunTimeController;
    }

    @Autowired
    public void setEntityDataService(IEntityDataService entityDataService) {
        DimensionEnvironment.entityDataService = entityDataService;
    }

    @Autowired
    public void setDataSchemeService(IRuntimeDataSchemeService dataSchemeService) {
        DimensionEnvironment.dataSchemeService = dataSchemeService;
    }

    @Autowired
    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        DimensionEnvironment.entityMetaService = entityMetaService;
    }

    @Autowired
    public void setEntityDataQueryAssist(IEntityDataQueryAssist entityDataQueryAssist) {
        DimensionEnvironment.entityDataQueryAssist = entityDataQueryAssist;
    }

    @Autowired
    public void setPeriodEntityAdapter(IPeriodEntityAdapter periodEntityAdapter) {
        DimensionEnvironment.periodEntityAdapter = periodEntityAdapter;
    }

    @Autowired
    public void setFactory(DimensionProviderFactory factory) {
        DimensionEnvironment.factory = factory;
    }

    public static IDataDefinitionRuntimeController getDataDefinitionRuntimeController() {
        return dataDefinitionRuntimeController;
    }

    public static IRunTimeViewController getRunTimeViewController() {
        return runTimeViewController;
    }

    public static IEntityViewRunTimeController getEntityViewRunTimeController() {
        return entityViewRunTimeController;
    }

    public static IEntityDataService getEntityDataService() {
        return entityDataService;
    }

    public static IRuntimeDataSchemeService getDataSchemeService() {
        return dataSchemeService;
    }

    public static IEntityMetaService getEntityMetaService() {
        return entityMetaService;
    }

    public static IEntityDataQueryAssist getEntityDataQueryAssist() {
        return entityDataQueryAssist;
    }

    public static IPeriodEntityAdapter getPeriodEntityAdapter() {
        return periodEntityAdapter;
    }

    public static DimensionProviderFactory getFactory() {
        return factory;
    }
}

