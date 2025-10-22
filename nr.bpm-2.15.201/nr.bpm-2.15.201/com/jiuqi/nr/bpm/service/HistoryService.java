/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.history.HistoricActivityInstance
 *  org.activiti.engine.history.HistoricProcessInstance
 *  org.activiti.engine.history.HistoricTaskInstance
 *  org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl
 *  org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity
 */
package com.jiuqi.nr.bpm.service;

import com.jiuqi.nr.bpm.common.ProcessActivity;
import com.jiuqi.nr.bpm.common.TaskComment;
import java.util.List;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;

public interface HistoryService {
    public List<TaskComment> queryCommentByInstanceId(String var1);

    public List<TaskComment> queryCommentByTaskId(String var1);

    public List<ProcessActivity> queryActivityByInstanceId(String var1, boolean var2);

    public boolean hasHistoricProcessInstance(String var1, String var2);

    public List<HistoricProcessInstance> queryHistoricProcessInstance(String var1);

    public List<HistoricActivityInstance> queryHistoricActivityInstance(String var1);

    public List<HistoricTaskInstance> queryHistoricTaskInstance(String var1);

    public List<HistoricVariableInstanceEntity> queryHistoricVariableInstance(String var1);

    public List<HistoricIdentityLinkEntityImpl> queryHistoricIdentityLinkInstance(String var1);

    public void deleteHistoricProcessInstance(String var1, String var2);

    public void deleteHistoricProcessInstance(String var1);

    public List<HistoricProcessInstance> queryHistoricProcessInstanceByBusinessKey(String var1);
}

