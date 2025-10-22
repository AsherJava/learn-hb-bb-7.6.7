/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.TaskDataFactoryManager
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.nr.data.common.service.TaskDataFactoryManager;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.io.service.ParamsMappingService;
import com.jiuqi.nr.io.service.impl.DefaultTaskDataService;

public class TaskDataService
extends DefaultTaskDataService {
    private IProviderStore providerStore;

    @Override
    protected IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public void setFactoryManager(TaskDataFactoryManager factoryManager) {
        this.factoryManager = factoryManager;
    }

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public void setParamsMappingService(ParamsMappingService paramsMappingService) {
        this.paramsMappingService = paramsMappingService;
    }
}

