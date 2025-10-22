/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityDataService
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.data.access.service.ITaskDimensionFilter;
import com.jiuqi.nr.data.access.service.impl.DefaultTaskDimensionFilter;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskDimensionFilterFactory {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;

    public ITaskDimensionFilter getTaskDimensionFilter(boolean filterByTask) {
        DefaultTaskDimensionFilter defaultTaskDimensionFilter = new DefaultTaskDimensionFilter();
        defaultTaskDimensionFilter.setRunTimeViewController(this.runTimeViewController);
        defaultTaskDimensionFilter.setDataAccessUtil(this.dataAccesslUtil);
        defaultTaskDimensionFilter.setEntityDataService(this.entityDataService);
        defaultTaskDimensionFilter.setDataDefinitionRuntimeController(this.runtimeController);
        defaultTaskDimensionFilter.setEntityViewRunTimeController(this.entityViewRunTimeController);
        defaultTaskDimensionFilter.setDimensionProviderFactory(this.dimensionProviderFactory);
        defaultTaskDimensionFilter.setFilterByTask(filterByTask);
        return defaultTaskDimensionFilter;
    }
}

