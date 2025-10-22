/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.RepositoryService
 */
package com.jiuqi.nr.bpm.service;

import com.jiuqi.nr.bpm.common.DeploymentBuilder;
import com.jiuqi.nr.bpm.common.ProcessDefinition;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import com.jiuqi.nr.bpm.impl.activiti6.extension.ExtensionService;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.activiti.engine.RepositoryService;

public interface DeployService {
    public DeploymentBuilder createDeploymentBuilder();

    public InputStream getDeploymentResourceByProcessDefinitionId(String var1);

    public List<ProcessDefinition> getAllProcessDefinition();

    public Optional<ProcessDefinition> getProcessDefinitionById(String var1);

    public List<ProcessDefinition> getProcessDefinitionByKey(String var1);

    @Deprecated
    public Optional<UserTask> getUserTask(String var1, String var2);

    public Optional<UserTask> getUserTask(String var1, String var2, String var3);

    public void suspendProcessDefinitionById(String var1);

    public void suspendProcessDefinitionById(String var1, boolean var2);

    public void activateProcessDefinitionById(String var1);

    public void activateProcessDefinitionById(String var1, boolean var2);

    public void deleteDeployment(String var1);

    public void deleteDeploymentByProcessDefinitionKey(String var1);

    public Optional<EventDispatcher> getActionEventHandler();

    public ExtensionService getActivitExtensionService();

    public RepositoryService getRepositoryService();

    public void deployToRunTime(WorkFlowInfoObj var1) throws Exception;
}

