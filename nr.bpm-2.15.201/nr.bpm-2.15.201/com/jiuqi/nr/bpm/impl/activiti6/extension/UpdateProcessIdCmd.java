/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl
 *  org.activiti.engine.impl.cmd.AbstractCustomSqlExecution
 *  org.activiti.engine.impl.cmd.CustomSqlExecution
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.interceptor.CommandContext
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import com.jiuqi.nr.bpm.impl.activiti6.extension.UpdateResourceCmd;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.cmd.CustomSqlExecution;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

public class UpdateProcessIdCmd
implements Command<Integer> {
    private String processDefinitionId;

    public UpdateProcessIdCmd(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public Integer execute(CommandContext commandContext) {
        AbstractCustomSqlExecution<UpdateResourceCmd.UpdateResourceMapper, Integer> customSqlExecution = new AbstractCustomSqlExecution<UpdateResourceCmd.UpdateResourceMapper, Integer>(UpdateResourceCmd.UpdateResourceMapper.class){
            private String newId;
            {
                this.newId = UpdateProcessIdCmd.this.processDefinitionId + ":1:" + UpdateProcessIdCmd.this.processDefinitionId;
            }

            public Integer execute(UpdateResourceCmd.UpdateResourceMapper customMapper) {
                return customMapper.updateProcessId(this.newId, UpdateProcessIdCmd.this.processDefinitionId);
            }
        };
        ProcessEngineConfigurationImpl cfg = commandContext.getProcessEngineConfiguration();
        cfg.getManagementService().executeCustomSql((CustomSqlExecution)customSqlExecution);
        return null;
    }
}

