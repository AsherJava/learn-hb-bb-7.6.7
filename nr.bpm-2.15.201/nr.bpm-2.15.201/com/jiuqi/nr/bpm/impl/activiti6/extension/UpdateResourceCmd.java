/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl
 *  org.activiti.engine.impl.cmd.AbstractCustomSqlExecution
 *  org.activiti.engine.impl.cmd.CustomSqlExecution
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.interceptor.CommandContext
 *  org.activiti.engine.repository.ProcessDefinition
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.ibatis.annotations.Param
 *  org.apache.ibatis.annotations.Update
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.cmd.CustomSqlExecution;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public class UpdateResourceCmd
implements Command<Integer> {
    private String deploymentId;
    private String processDefinitionId;
    private byte[] newResource;
    private String resourceName;
    private String oldResourceName;

    public UpdateResourceCmd(String deploymentId, String processDefinitionId, byte[] newResource, String resourceName, String oldResourceName) {
        this.newResource = newResource;
        this.deploymentId = deploymentId;
        this.processDefinitionId = processDefinitionId;
        this.resourceName = resourceName;
        this.oldResourceName = oldResourceName;
    }

    public UpdateResourceCmd(ProcessDefinition processDefinition, byte[] newResource, String resourceName, String oldResourceName) {
        this(processDefinition.getDeploymentId(), processDefinition.getId(), newResource, resourceName, oldResourceName);
    }

    public Integer execute(CommandContext commandContext) {
        AbstractCustomSqlExecution<UpdateResourceMapper, Integer> customSqlExecution = new AbstractCustomSqlExecution<UpdateResourceMapper, Integer>(UpdateResourceMapper.class){

            public Integer execute(UpdateResourceMapper customMapper) {
                if (StringUtils.isEmpty((CharSequence)UpdateResourceCmd.this.oldResourceName)) {
                    return customMapper.updateResource(UpdateResourceCmd.this.deploymentId, UpdateResourceCmd.this.newResource, UpdateResourceCmd.this.resourceName);
                }
                int procdefCount = customMapper.updateResourceProcdef(UpdateResourceCmd.this.deploymentId, UpdateResourceCmd.this.newResource, UpdateResourceCmd.this.resourceName, UpdateResourceCmd.this.oldResourceName);
                return procdefCount + customMapper.updateResourceForOld(UpdateResourceCmd.this.deploymentId, UpdateResourceCmd.this.newResource, UpdateResourceCmd.this.resourceName, UpdateResourceCmd.this.oldResourceName);
            }
        };
        ProcessEngineConfigurationImpl cfg = commandContext.getProcessEngineConfiguration();
        cfg.getManagementService().executeCustomSql((CustomSqlExecution)customSqlExecution);
        return null;
    }

    public static interface UpdateResourceMapper {
        @Update(value={"update ACT_GE_BYTEARRAY set BYTES_= #{newResource} where NAME_ = #{resourceName} and DEPLOYMENT_ID_= #{oldDeploymentId}"})
        public int updateResource(@Param(value="oldDeploymentId") String var1, @Param(value="newResource") byte[] var2, @Param(value="resourceName") String var3);

        @Update(value={"update ACT_GE_BYTEARRAY set BYTES_= #{newResource}, NAME_= #{resourceName} where NAME_ = #{oldResourceName} and DEPLOYMENT_ID_= #{oldDeploymentId}"})
        public int updateResourceForOld(@Param(value="oldDeploymentId") String var1, @Param(value="newResource") byte[] var2, @Param(value="resourceName") String var3, @Param(value="oldResourceName") String var4);

        @Update(value={"update ACT_RE_PROCDEF set RESOURCE_NAME_= #{resourceName} where RESOURCE_NAME_ = #{oldResourceName} and DEPLOYMENT_ID_= #{oldDeploymentId}"})
        public int updateResourceProcdef(@Param(value="oldDeploymentId") String var1, @Param(value="newResource") byte[] var2, @Param(value="resourceName") String var3, @Param(value="oldResourceName") String var4);

        @Update(value={"update ACT_RE_PROCDEF set ID_= #{newId} where Key_ = #{processDefinitionId} "})
        public int updateProcessId(@Param(value="newId") String var1, @Param(value="processDefinitionId") String var2);
    }
}

