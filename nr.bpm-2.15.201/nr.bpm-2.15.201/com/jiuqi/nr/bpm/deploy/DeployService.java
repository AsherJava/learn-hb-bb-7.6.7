/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.deploy;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.deploy.DeploymentBuilder;
import com.jiuqi.nr.bpm.deploy.DeploymentResourceQuery;
import com.jiuqi.nr.bpm.deploy.ProcessDefinitionQuery;
import com.jiuqi.nr.bpm.deploy.ProcessRunningConfigBuilder;
import com.jiuqi.nr.bpm.deploy.ProcessRunningConfigQuery;
import com.jiuqi.nr.bpm.deploy.UserTaskQuery;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DeployService {
    public DeploymentBuilder createDeploymentBuilder();

    public DeploymentResourceQuery createDeploymentResourceQuery();

    public ProcessDefinitionQuery createProcessDefinitionQuery();

    public UserTaskQuery createUserTaskQuery();

    public ProcessRunningConfigBuilder createProcessRunningConfigBuilder();

    public ProcessRunningConfigQuery createProcessRunningConfigQuery();

    public void suspendProcessDefinitionById(String var1);

    public void suspendProcessDefinitionById(String var1, boolean var2);

    public void activateProcessDefinitionById(String var1);

    public void activateProcessDefinitionById(String var1, boolean var2);

    public Map<String, List<BusinessKey>> checkActorExists(UUID var1);
}

