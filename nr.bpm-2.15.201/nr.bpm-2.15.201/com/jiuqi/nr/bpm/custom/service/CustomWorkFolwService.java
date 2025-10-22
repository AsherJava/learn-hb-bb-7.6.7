/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.bpm.custom.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefineSaveEntity;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowTreeNode;
import com.jiuqi.nr.bpm.custom.bean.WorkflowTreeParam;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyParameterType;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public interface CustomWorkFolwService {
    public List<WorkFlowGroup> getAllWorkFlowGroup();

    public List<WorkFlowTreeNode> getFirstLevelTNode();

    public List<WorkFlowTreeNode> getFirstLevelTNodeByDefine(WorkFlowDefine var1);

    public WorkFlowGroup getWorkFlowGroupByID(String var1);

    public WorkFlowGroup getWorkFlowGroupByTitle(String var1);

    public WorkFlowTreeNode getWorkFlowGroupTNodeByID(String var1);

    public List<WorkFlowDefine> getWorkFlowDefineByGroupID(String var1);

    public List<WorkFlowTreeNode> getWorkFlowDefineTNodeByGroupID(String var1);

    public WorkFlowDefine getWorkFlowDefineByID(String var1, int var2);

    public WorkFlowTreeNode getWorkFlowDefineTNodeByID(String var1, int var2);

    public String getWorkFlowDefineXmlByID(String var1, int var2);

    public List<WorkFlowNodeSet> getWorkFlowNodeSets(String var1, int var2);

    public List<WorkFlowNodeSet> getWorkFlowNodeSets(String var1);

    public WorkFlowNodeSet getWorkFlowNodeSetByID(String var1, String var2);

    public WorkFlowLine getWorkFlowLineByID(String var1, String var2);

    public List<WorkFlowAction> getWorkFlowNodeAction(String var1, String var2);

    public List<WorkFlowAction> getRunWorkFlowNodeAction(String var1, String var2);

    public List<WorkFlowParticipant> getWorkFlowParticipant(String var1, String var2);

    public ActorStrategyParameterType getActorStrategyParameterType(String var1);

    public WorkFlowTreeNode creatWorkFlowGroup(WorkFlowGroup var1) throws JQException;

    @Deprecated
    public void creatWorkFlowDefine(WorkFlowDefine var1) throws JQException;

    public Object updateWorkFlowDefine(WorkFlowDefine var1);

    public Object updateWorkFlowDefineTitle(String var1, int var2, String var3) throws JQException;

    public void delWorkFlowDefineByID(String var1, int var2) throws JQException;

    public void delWorkFlowGroupByID(String var1) throws JQException;

    public String creatWorkFlowNodeSet(WorkFlowNodeSet var1);

    public WorkFlowTreeNode getWorkFlowTreeNode(WorkFlowGroup var1);

    public WorkFlowTreeNode getWorkFlowTreeNode(WorkFlowDefine var1);

    public boolean groupHasChildern(String var1);

    public List<ActorStrategy<?>> getSysParticipantSchemes();

    public ActorStrategy<?> getSysParticipantSchemeByID(String var1);

    public String creatWorkFlowNParticipant(WorkFlowParticipant var1);

    @Deprecated
    public WorkFlowDefine cloneMaintenanceWorkFlowDefine(String var1, int var2);

    public void delWorkFlowNodeSetByID(String var1, String var2) throws JQException;

    public WorkFlowDefine releaseWorkFlowDefine(String var1, int var2) throws Exception;

    public Object updateWorkFlowGroup(WorkFlowGroup var1);

    public WorkFlowDefine getWorkFlowDefineByLinkID(String var1);

    public String creatWorkFlowLine(WorkFlowLine var1);

    public String saveWrokflowdDfine(WorkFlowDefineSaveEntity var1) throws JQException;

    public List<WorkFlowDefine> searchDefineByinput(String var1);

    public String exportWorkFlowDefine(String var1, Integer var2, HttpServletResponse var3);

    public String getWorkFlowDefineDefaultTitle();

    public List<WorkFlowDefine> getDefineByTitle(String var1);

    public List<WorkFlowDefine> getDefineByTitleAndTaskKey(String var1, String var2);

    public List<WorkFlowDefine> getDefineByCode(String var1);

    public WorkFlowGroup getGroupByTitle(String var1);

    public List<WorkFlowParticipant> getWorkFlowParticipantsByID(String[] var1, String var2);

    public String checkBeforeRelease(WorkFlowDefine var1);

    public WorkFlowInfoObj getWorkFlowInfoObj(WorkFlowDefine var1);

    public List<WorkFlowLine> getWorkflowLinesByLinkid(String var1);

    public List<WorkFlowLine> getWorkflowLinesByRunLinkid(String var1);

    public List<WorkFlowDefine> getWorkflowByState();

    public WorkFlowAction getWorkflowActionByCode(String var1, String var2);

    public WorkFlowAction getWorkflowActionByCode(String var1, String var2, String var3);

    public List<WorkFlowAction> getWorkflowActionsByLinkid(String var1);

    public List<WorkFlowLine> getWorkflowLinesByPreTask(String var1, String var2);

    public WorkFlowAction getWorkflowActionById(String var1, String var2);

    public List<WorkFlowAction> getAllWorkflowAction();

    public List<WorkFlowLine> getWorkflowLineByEndNode(String var1, String var2);

    public WorkFlowAction getWorlflowActionByCodeAndLinkId(String var1, String var2);

    public WorkFlowNodeSet getDesignWorkFlowNodeSetByID(String var1, String var2);

    public WorkFlowDefine getRunWorkFlowDefineByID(String var1, int var2);

    public List<WorkFlowAction> getRunWorkflowActionsByLinkid(String var1);

    public WorkFlowLine getWorkflowLine(String var1, String var2, String var3, String var4);

    public List<WorkFlowLine> getWorkflowLinesByPreTask(String var1, String var2, String var3);

    public List<WorkFlowLine> getWorkflowLineByBNodeAndEndNode(List<String> var1, String var2, String var3);

    public List<WorkFlowLine> getWorkflowLinesByPreTask(Set<String> var1, String var2);

    public List<WorkFlowTreeNode> getTaskAndWorkflows(WorkflowTreeParam var1);

    public void saveTaskAndWorkflows(WorkflowTreeParam var1);
}

