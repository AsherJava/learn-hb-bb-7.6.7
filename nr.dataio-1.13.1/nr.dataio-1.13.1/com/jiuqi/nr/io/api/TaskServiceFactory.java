/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.TaskDataFactoryManager
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.io.api;

import com.jiuqi.nr.data.common.service.TaskDataFactoryManager;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.io.api.ITaskDataService;
import com.jiuqi.nr.io.service.ParamsMappingService;
import com.jiuqi.nr.io.service.impl.TaskDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskServiceFactory {
    @Autowired
    private ITaskDataService taskDataService;
    @Autowired
    private TaskDataFactoryManager factoryManager;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired(required=false)
    private ParamsMappingService paramsMappingService;

    public ITaskDataService getTaskDataService() {
        return this.taskDataService;
    }

    public ITaskDataService getTaskDataService(IProviderStore providerStore) {
        TaskDataService dataService = new TaskDataService();
        dataService.setProviderStore(providerStore);
        dataService.setFactoryManager(this.factoryManager);
        dataService.setRunTimeViewController(this.runTimeViewController);
        dataService.setParamsMappingService(this.paramsMappingService);
        return dataService;
    }
}

