/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 */
package com.jiuqi.nr.workflow2.instance.spring;

import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.bpm.instance.service.WorkflowInstanceService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceManipulationService;
import com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceManipulationServiceImpl;
import com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceQueryService;
import com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceQueryServiceImpl;
import com.jiuqi.nr.workflow2.instance.util.InstanceUtil;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"com.jiuqi.nr.workflow2.instance.web"})
@AutoConfiguration
public class Workflow2InstanceBeanProvider {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IProcessMetaDataService processMetaDataService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    @Value(value="${jiuqi.nr.task2.enable:false}")
    private boolean isTaskDesignVersion2_0;
    @Value(value="${jiuqi.nr.todo.version:2.0}")
    private String todoVersion;

    @Bean
    public Workflow2InstanceQueryService getWorkflow2InstanceQueryService(InstanceUtil instanceUtil) {
        Workflow2InstanceQueryServiceImpl workflow2InstanceQueryService = new Workflow2InstanceQueryServiceImpl();
        workflow2InstanceQueryService.setRunTimeViewController(this.runTimeViewController);
        workflow2InstanceQueryService.setWorkflowSettingsService(this.workflowSettingsService);
        workflow2InstanceQueryService.setProcessQueryService(this.processQueryService);
        workflow2InstanceQueryService.setProcessDimensionsBuilder(this.processDimensionsBuilder);
        workflow2InstanceQueryService.setProcessMetaDataService(this.processMetaDataService);
        workflow2InstanceQueryService.setPeriodEngineService(this.periodEngineService);
        workflow2InstanceQueryService.setDataSchemeService(this.dataSchemeService);
        workflow2InstanceQueryService.setEntityMetaService(this.entityMetaService);
        workflow2InstanceQueryService.setInstanceUtil(instanceUtil);
        workflow2InstanceQueryService.setWorkflowInstanceService(this.workflowInstanceService);
        workflow2InstanceQueryService.setDefaultEngineVersionUtil(this.defaultEngineVersionJudge);
        workflow2InstanceQueryService.setTaskDesignVersion2_0(this.isTaskDesignVersion2_0);
        return workflow2InstanceQueryService;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceManipulationServiceImpl"})
    public Workflow2InstanceManipulationService getWorkflow2InstanceManipulationService() {
        Workflow2InstanceManipulationServiceImpl workflow2InstanceManipulationService = new Workflow2InstanceManipulationServiceImpl();
        workflow2InstanceManipulationService.setRunTimeViewController(this.runTimeViewController);
        workflow2InstanceManipulationService.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        workflow2InstanceManipulationService.setEntityDataService(this.entityDataService);
        workflow2InstanceManipulationService.setEntityViewRunTimeController(this.entityViewRunTimeController);
        workflow2InstanceManipulationService.setEntityMetaService(this.entityMetaService);
        workflow2InstanceManipulationService.setWorkflowSettingsService(this.workflowSettingsService);
        workflow2InstanceManipulationService.setAsyncTaskManager(this.asyncTaskManager);
        workflow2InstanceManipulationService.setProcessDimensionsBuilder(this.processDimensionsBuilder);
        workflow2InstanceManipulationService.setTodoVersion(this.todoVersion);
        workflow2InstanceManipulationService.setDefaultEngineVersionUtil(this.defaultEngineVersionJudge);
        return workflow2InstanceManipulationService;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.instance.util.InstanceUtil"})
    public InstanceUtil getInstanceUtil() {
        InstanceUtil instanceUtil = new InstanceUtil();
        instanceUtil.setRunTimeViewController(this.runTimeViewController);
        instanceUtil.setDataDefinitionRuntimeController(this.dataDefinitionRuntimeController);
        instanceUtil.setEntityDataService(this.entityDataService);
        instanceUtil.setEntityViewRunTimeController(this.entityViewRunTimeController);
        instanceUtil.setPeriodEntityAdapter(this.periodEntityAdapter);
        instanceUtil.setEntityMetaService(this.entityMetaService);
        return instanceUtil;
    }
}

