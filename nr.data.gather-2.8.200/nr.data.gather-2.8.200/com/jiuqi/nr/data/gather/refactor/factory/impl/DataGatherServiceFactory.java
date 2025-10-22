/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.engine.gather.GatherTempTableHandler
 *  com.jiuqi.nr.data.engine.gather.IDataGatherProvider
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.gather.refactor.factory.impl;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.engine.gather.GatherTempTableHandler;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.gather.listener.DataGatherHandler;
import com.jiuqi.nr.data.gather.listener.DataGatherUnitEntityFilterProvider;
import com.jiuqi.nr.data.gather.listener.FloatTableGatherHandler;
import com.jiuqi.nr.data.gather.refactor.factory.IDataGatherServiceFactory;
import com.jiuqi.nr.data.gather.refactor.provider.NodeCheckOptionProvider;
import com.jiuqi.nr.data.gather.refactor.provider.NodeGatherOptionProvider;
import com.jiuqi.nr.data.gather.refactor.service.NodeCheckService;
import com.jiuqi.nr.data.gather.refactor.service.NodeGatherService;
import com.jiuqi.nr.data.gather.refactor.service.SelectGatherService;
import com.jiuqi.nr.data.gather.refactor.service.impl.NodeCheckServiceImpl;
import com.jiuqi.nr.data.gather.refactor.service.impl.NodeGatherServiceImpl;
import com.jiuqi.nr.data.gather.refactor.service.impl.SelectGatherServiceImpl;
import com.jiuqi.nr.data.gather.service.impl.ErrorItemListGatherService;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DataGatherServiceFactory
implements IDataGatherServiceFactory {
    @Autowired
    NodeGatherService nodeGatherService;
    @Autowired
    SelectGatherService selectGatherService;
    @Autowired
    NodeCheckService nodeCheckService;
    @Resource
    IRunTimeViewController runtimeView;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    IDataGatherProvider dataGatherProvider;
    @Autowired(required=false)
    private List<DataGatherHandler> dataGatherHandlerList;
    @Autowired
    private GatherTempTableHandler gatherTempTableHandler;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private DataGatherUnitEntityFilterProvider dataGatherUnitEntityFilterProvider;
    @Autowired
    private ErrorItemListGatherService errorItemListGatherService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private FloatTableGatherHandler floatTableGatherHandler;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private NodeGatherOptionProvider nodeGatherOptionProvider;
    @Autowired
    private NodeCheckOptionProvider nodeCheckOptionProvider;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;

    @Override
    public NodeGatherService getNodeGatherService() {
        return this.nodeGatherService;
    }

    @Override
    public NodeGatherService getNodeGatherService(IProviderStore providerStore) {
        NodeGatherServiceImpl service = new NodeGatherServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataGatherUnitEntityFilterProvider(this.dataGatherUnitEntityFilterProvider);
        service.setErrorItemListGatherService(this.errorItemListGatherService);
        service.setEntityDataService(this.entityDataService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setiNvwaSystemOptionService(this.iNvwaSystemOptionService);
        service.setApplicationContext(this.applicationContext);
        service.setFloatTableGatherHandler(this.floatTableGatherHandler);
        service.setOptionProvider(this.nodeGatherOptionProvider);
        service.setDataGatherHandlerList(this.dataGatherHandlerList);
        service.setGatherTempTableHandler(this.gatherTempTableHandler);
        service.setProviderStore(providerStore);
        return service;
    }

    @Override
    public NodeGatherService getNodeGatherService(NodeGatherOptionProvider optionProvider) {
        NodeGatherServiceImpl service = new NodeGatherServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataGatherUnitEntityFilterProvider(this.dataGatherUnitEntityFilterProvider);
        service.setErrorItemListGatherService(this.errorItemListGatherService);
        service.setEntityDataService(this.entityDataService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setiNvwaSystemOptionService(this.iNvwaSystemOptionService);
        service.setApplicationContext(this.applicationContext);
        service.setFloatTableGatherHandler(this.floatTableGatherHandler);
        service.setProviderStore(this.providerStore);
        service.setDataGatherHandlerList(this.dataGatherHandlerList);
        service.setGatherTempTableHandler(this.gatherTempTableHandler);
        service.setOptionProvider(optionProvider);
        return service;
    }

    @Override
    public NodeGatherService getNodeGatherService(IProviderStore providerStore, NodeGatherOptionProvider optionProvider) {
        NodeGatherServiceImpl service = new NodeGatherServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataGatherUnitEntityFilterProvider(this.dataGatherUnitEntityFilterProvider);
        service.setErrorItemListGatherService(this.errorItemListGatherService);
        service.setEntityDataService(this.entityDataService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setiNvwaSystemOptionService(this.iNvwaSystemOptionService);
        service.setApplicationContext(this.applicationContext);
        service.setFloatTableGatherHandler(this.floatTableGatherHandler);
        service.setDataGatherHandlerList(this.dataGatherHandlerList);
        service.setGatherTempTableHandler(this.gatherTempTableHandler);
        service.setOptionProvider(optionProvider);
        service.setProviderStore(providerStore);
        return service;
    }

    @Override
    public SelectGatherService getSelectGatherService() {
        return this.selectGatherService;
    }

    @Override
    public SelectGatherService getSelectGatherService(IProviderStore providerStore) {
        SelectGatherServiceImpl service = new SelectGatherServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherHandlerList(this.dataGatherHandlerList);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataGatherUnitEntityFilterProvider(this.dataGatherUnitEntityFilterProvider);
        service.setErrorItemListGatherService(this.errorItemListGatherService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setApplicationContext(this.applicationContext);
        service.setProviderStore(providerStore);
        service.setOptionProvider(this.nodeGatherOptionProvider);
        return service;
    }

    @Override
    public SelectGatherService getSelectGatherService(NodeGatherOptionProvider optionProvider) {
        SelectGatherServiceImpl service = new SelectGatherServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherHandlerList(this.dataGatherHandlerList);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataGatherUnitEntityFilterProvider(this.dataGatherUnitEntityFilterProvider);
        service.setErrorItemListGatherService(this.errorItemListGatherService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setApplicationContext(this.applicationContext);
        service.setProviderStore(this.providerStore);
        service.setOptionProvider(optionProvider);
        return service;
    }

    @Override
    public SelectGatherService getSelectGatherService(IProviderStore providerStore, NodeGatherOptionProvider optionProvider) {
        SelectGatherServiceImpl service = new SelectGatherServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherHandlerList(this.dataGatherHandlerList);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataGatherUnitEntityFilterProvider(this.dataGatherUnitEntityFilterProvider);
        service.setErrorItemListGatherService(this.errorItemListGatherService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setApplicationContext(this.applicationContext);
        service.setProviderStore(providerStore);
        service.setOptionProvider(optionProvider);
        return service;
    }

    @Override
    public NodeCheckService getNodeCheckService() {
        return this.nodeCheckService;
    }

    @Override
    public NodeCheckService getNodeCheckService(IProviderStore providerStore) {
        NodeCheckServiceImpl service = new NodeCheckServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setEntityDataService(this.entityDataService);
        service.setProviderStore(providerStore);
        service.setNodeCheckOptionProvider(this.nodeCheckOptionProvider);
        service.setGatherTempTableHandler(this.gatherTempTableHandler);
        service.setDimCollectionBuildUtil(this.dimCollectionBuildUtil);
        return service;
    }

    @Override
    public NodeCheckService getNodeCheckService(NodeCheckOptionProvider optionProvider) {
        NodeCheckServiceImpl service = new NodeCheckServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setEntityDataService(this.entityDataService);
        service.setProviderStore(this.providerStore);
        service.setGatherTempTableHandler(this.gatherTempTableHandler);
        service.setDimCollectionBuildUtil(this.dimCollectionBuildUtil);
        service.setNodeCheckOptionProvider(optionProvider);
        return service;
    }

    @Override
    public NodeCheckService getNodeCheckService(IProviderStore providerStore, NodeCheckOptionProvider optionProvider) {
        NodeCheckServiceImpl service = new NodeCheckServiceImpl();
        service.setRuntimeView(this.runtimeView);
        service.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        service.setEntityViewRunTimeController(this.entityViewRunTimeController);
        service.setDataGatherProvider(this.dataGatherProvider);
        service.setEntityMetaService(this.entityMetaService);
        service.setPeriodEngineService(this.periodEngineService);
        service.setDataServiceLoggerFactory(this.dataServiceLoggerFactory);
        service.setEntityDataService(this.entityDataService);
        service.setGatherTempTableHandler(this.gatherTempTableHandler);
        service.setDimCollectionBuildUtil(this.dimCollectionBuildUtil);
        service.setProviderStore(providerStore);
        service.setNodeCheckOptionProvider(optionProvider);
        return service;
    }
}

