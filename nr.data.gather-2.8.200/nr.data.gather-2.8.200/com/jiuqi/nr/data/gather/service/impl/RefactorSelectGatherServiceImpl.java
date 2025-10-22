/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.data.access.service.DPEFactoryBuilder
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.common.ProviderStore
 */
package com.jiuqi.nr.data.gather.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.access.service.DPEFactoryBuilder;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.gather.bean.SelectDataGatherParam;
import com.jiuqi.nr.data.gather.listener.DataGatherHandler;
import com.jiuqi.nr.data.gather.refactor.factory.impl.DataGatherServiceFactory;
import com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.provider.DefaultNodeGatherOptionProvider;
import com.jiuqi.nr.data.gather.refactor.provider.NodeGatherOptionProvider;
import com.jiuqi.nr.data.gather.refactor.service.SelectGatherService;
import com.jiuqi.nr.data.gather.service.IDataSelectGatherService;
import com.jiuqi.nr.data.gather.service.impl.NodeGatherOptionProviderImpl;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.common.ProviderStore;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service(value="selectGatherService")
public class RefactorSelectGatherServiceImpl
implements IDataSelectGatherService {
    @Autowired
    private DataGatherServiceFactory dataGatherServiceFactory;
    @Autowired(required=false)
    private List<DataGatherHandler> dataGatherHandlerList;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IDataAccessServiceProvider dataAccessProvider;

    @Override
    public void selectNodeGather(SelectDataGatherParam selectDataGatherParam) {
        this.asyncDataSelectGather(selectDataGatherParam, null);
    }

    @Override
    public void asyncDataSelectGather(SelectDataGatherParam selectDataGatherParam, AsyncTaskMonitor asyncTaskMonitor) {
        Set<String> ignoreAccessItems = selectDataGatherParam.getIgnoreAccessItems();
        DPEFactoryBuilder dpeFactoryBuilder = new DPEFactoryBuilder(this.dataAccessProvider);
        for (String accessItem : ignoreAccessItems) {
            dpeFactoryBuilder.ignorePermission(accessItem);
        }
        DataPermissionEvaluatorFactory build = dpeFactoryBuilder.build();
        ProviderStore providerStore = new ProviderStore(build);
        NodeGatherOptionProvider nodeGatherOptionProvider = ignoreAccessItems.contains("upload") ? new NodeGatherOptionProviderImpl() : new DefaultNodeGatherOptionProvider();
        SelectGatherService selectGatherService = this.dataGatherServiceFactory.getSelectGatherService((IProviderStore)providerStore, nodeGatherOptionProvider);
        DefaultGatherServiceMonitor monitor = new DefaultGatherServiceMonitor(asyncTaskMonitor, this.dataGatherHandlerList, this.applicationContext);
        selectGatherService.selectNodeGather(selectDataGatherParam, monitor);
    }
}

