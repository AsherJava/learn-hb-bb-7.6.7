/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.bpm.custom.dao;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefineSaveEntity;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import java.util.List;
import java.util.Set;

public interface CustomWorkFolwDao {
    public String saveWorkflow(WorkFlowDefineSaveEntity var1) throws JQException;

    public void insertWorkflowGroup(WorkFlowGroup var1) throws JQException;

    public void insertWorkflowDefine(WorkFlowDefine var1) throws JQException;

    public void insertWorkflowNode(WorkFlowNodeSet var1) throws JQException;

    public void insertWorkflowLine(WorkFlowLine var1) throws JQException;

    public void insertWorkflowParticipant(WorkFlowParticipant var1) throws JQException;

    public void insertWorkflowAction(WorkFlowAction var1) throws JQException;

    public Object updateWorkFlowGroup(WorkFlowGroup var1);

    public void updateWorkflowDefine(WorkFlowDefine var1) throws JQException;

    public void updateWorkflowNode(WorkFlowNodeSet var1);

    public void updateWorkflowLine(WorkFlowLine var1);

    public void updateWorkflowParticipant(WorkFlowParticipant var1);

    public void updateWorkflowAction(WorkFlowAction var1);

    public void deleteWorkflowNode(List<String> var1, String var2) throws JQException;

    public void deleteWorkflowLine(List<String> var1, String var2) throws JQException;

    public void deleteWorkflowLine(String var1, String var2) throws JQException;

    public void deleteWorkflowParticipant(List<String> var1, String var2) throws JQException;

    public void deleteWorkflowAction(List<String> var1, String var2) throws JQException;

    public void deleteWorkflowById(String var1, int var2) throws JQException;

    public void deleteWorkflowNodeByLinkId(String var1) throws JQException;

    public void deleteWorkflowLineByLinkId(String var1) throws JQException;

    public void deleteWorkflowParticipantByLinkId(String var1) throws JQException;

    public void deleteWorkflowActionByLinkId(String var1) throws JQException;

    public WorkFlowGroup getWorkFlowGroupByID(String var1);

    public List<WorkFlowGroup> getAllWorkFlowGroup();

    public WorkFlowGroup getWorkFlowGroupByTitle(String var1);

    public List<WorkFlowDefine> getAllWorkFlowDefineNoParentid();

    public List<WorkFlowDefine> searchDefineByinput(String var1);

    public List<WorkFlowDefine> getWorkFlowDefineByGroupID(String var1);

    public WorkFlowDefine getWorkFlowDefineByID(String var1, int var2);

    public String getWorkFlowDefineXmlByID(String var1, int var2);

    public List<WorkFlowNodeSet> getWorkFlowNodeSetsByDefineID(String var1, int var2);

    public WorkFlowNodeSet getWorkFlowNodeSetByID(String var1, String var2);

    public WorkFlowLine getWorkFlowLineByID(String var1, String var2);

    public List<WorkFlowAction> getWorkFlowActionByNodeID(String var1, String var2);

    public List<WorkFlowParticipant> getWorkFlowParticipantByNodeID(String var1, String var2);

    public void updateWorkFlowDefineTitle(String var1, int var2, String var3) throws JQException;

    public void delWorkFlowGroupByID(String var1) throws JQException;

    public boolean groupHasChildern(String var1);

    public void delWorkFlowNodeSetByID(String var1, String var2) throws JQException;

    public List<WorkFlowDefine> getAllWorkFlowDefineByState(int var1);

    public List<String> getAllDefineTitle();

    public List<WorkFlowDefine> getWorkFlowDefineByTitle(String var1);

    public List<WorkFlowDefine> getWorkFlowDefineByTitleAndTaskkey(String var1, String var2);

    public List<WorkFlowDefine> getWorkFlowDefineByCode(String var1);

    public WorkFlowDefine getWorkFlowDefineByLinkID(String var1);

    public List<WorkFlowNodeSet> getWorkFlowNodeSetsByLinkID(String var1);

    public List<WorkFlowParticipant> getWorkFlowParticipantsByID(String[] var1, String var2);

    public List<WorkFlowParticipant> getWorkFlowParticipantsByLinkID(String var1);

    public List<WorkFlowLine> getWorkFlowLinesByLinkID(String var1);

    public WorkFlowAction getWorkflowActionByCode(String var1, String var2);

    public WorkFlowAction getWorkflowActionByCodeAndActionId(String var1, String var2, String var3);

    public List<WorkFlowAction> getWorkFlowActionsByLinkID(String var1);

    public List<WorkFlowLine> getWorkFlowLinesByPreTask(String var1, String var2);

    public List<WorkFlowLine> getWorkFlowLinesByPreTask(Set<String> var1, String var2);

    public List<WorkFlowLine> getWorkFlowLinesByPreTaskAndAction(String var1, String var2, String var3);

    public WorkFlowAction getWorkflowActionById(String var1, String var2);

    public List<WorkFlowLine> getWorkFlowLineByEndNode(String var1, String var2);

    public List<WorkFlowLine> getWorkFlowLineByBNodeAndEndNode(List<String> var1, String var2, String var3);

    public WorkFlowAction getWorkflowActionByCodeandLinkId(String var1, String var2);

    public List<WorkFlowAction> getAllWorkflowAction();

    public WorkFlowLine getWorkFlowLines(String var1, String var2, String var3, String var4);

    public List<WorkFlowDefine> getWorkFlowDefineByTaskKey(String var1);
}

