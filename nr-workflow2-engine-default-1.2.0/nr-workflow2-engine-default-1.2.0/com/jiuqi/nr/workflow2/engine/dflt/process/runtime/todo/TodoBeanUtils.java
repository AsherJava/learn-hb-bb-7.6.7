/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class TodoBeanUtils
implements InitializingBean {
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private TodoManipulationService todoManipulationService;
    @Autowired
    private DefaultProcessDefinitionService processDefinitionService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    public static TodoBeanUtils INSTANCE;

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    public static TodoManipulationService getTodoManipulationService() {
        return TodoBeanUtils.INSTANCE.todoManipulationService;
    }

    public static DefaultProcessDefinitionService getProcessDefinitionService() {
        return TodoBeanUtils.INSTANCE.processDefinitionService;
    }

    public static WorkflowSettingsService getWorkflowSettingsService() {
        return TodoBeanUtils.INSTANCE.workflowSettingsService;
    }

    public static IRunTimeViewController getViewController() {
        return TodoBeanUtils.INSTANCE.viewController;
    }

    public static ApplicationEventPublisher getApplicationEventPublisher() {
        return TodoBeanUtils.INSTANCE.applicationEventPublisher;
    }

    public INvwaSystemOptionService getSystemOptionService() {
        return this.systemOptionService;
    }
}

