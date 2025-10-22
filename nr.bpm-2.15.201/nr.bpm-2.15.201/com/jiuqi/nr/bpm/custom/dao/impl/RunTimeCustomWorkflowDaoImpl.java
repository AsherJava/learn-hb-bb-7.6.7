/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.custom.dao.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefineSaveEntity;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.dao.CustomWorkFolwDao;
import com.jiuqi.nr.bpm.custom.dao.impl.BaseCustomWorkflow;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component(value="runtimeDao")
public class RunTimeCustomWorkflowDaoImpl
extends BaseCustomWorkflow
implements CustomWorkFolwDao {
    private static final String WORKFLOWGROUP = "SYS_WORKFLOW_GROUP";
    private static final String WORKFLOWDEFINE = "SYS_WORKFLOW_DEFINE";
    private static final String WORKFLOWNODESET = "SYS_WORKFLOW_NODESET";
    private static final String WORKFLOWPARTI = "SYS_WORKFLOW_PARTI";
    private static final String WORKFLOWACTION = "SYS_WORKFLOW_ACTION";
    private static final String WORKFLOWLINE = "SYS_WORKFLOW_LINE";

    @Override
    @Transactional
    public String saveWorkflow(WorkFlowDefineSaveEntity saveObj) throws JQException {
        List<String> list;
        List<WorkFlowAction> actions;
        List<WorkFlowAction> creat_actions;
        List<String> list2;
        List<WorkFlowParticipant> updateParticis;
        List<WorkFlowParticipant> creatParticis;
        List<String> list3;
        List<WorkFlowLine> updateLines;
        List<WorkFlowLine> creatLines;
        List<String> list4;
        List<WorkFlowNodeSet> updateNodes;
        List<WorkFlowNodeSet> creatNodes;
        WorkFlowDefine define = saveObj.getDefine();
        if (define != null) {
            this.insertWorkflowDefine(define, WORKFLOWDEFINE);
        }
        if ((creatNodes = saveObj.getCreat_nodes()) != null && !creatNodes.isEmpty()) {
            for (WorkFlowNodeSet workFlowNodeSet : creatNodes) {
                this.insertWorkflowNode(workFlowNodeSet, WORKFLOWNODESET);
            }
        }
        if ((updateNodes = saveObj.getUpdate_nodes()) != null && !updateNodes.isEmpty()) {
            for (WorkFlowNodeSet node : updateNodes) {
                this.updateWorkflowNode(node, WORKFLOWNODESET);
            }
        }
        if ((list4 = saveObj.getDel_nodes()) != null && !list4.isEmpty()) {
            this.deleteWorkflowNode(list4, define.getLinkid(), WORKFLOWNODESET);
        }
        if ((creatLines = saveObj.getCreat_lines()) != null && !creatLines.isEmpty()) {
            for (WorkFlowLine workFlowLine : creatLines) {
                this.insertWorkflowLine(workFlowLine, WORKFLOWLINE);
            }
        }
        if ((updateLines = saveObj.getUpdate_lines()) != null && !updateLines.isEmpty()) {
            for (WorkFlowLine line : updateLines) {
                this.updateWorkflowLine(line, WORKFLOWLINE);
            }
        }
        if ((list3 = saveObj.getDel_lines()) != null && !list3.isEmpty()) {
            this.deleteWorkflowLine(list3, define.getLinkid(), WORKFLOWLINE);
        }
        if ((creatParticis = saveObj.getCreat_participants()) != null && !creatParticis.isEmpty()) {
            for (WorkFlowParticipant workFlowParticipant : creatParticis) {
                this.insertWorkflowParticipant(workFlowParticipant, WORKFLOWPARTI);
            }
        }
        if ((updateParticis = saveObj.getUpdate_participants()) != null && !updateParticis.isEmpty()) {
            for (WorkFlowParticipant particis : updateParticis) {
                this.updateWorkflowParticipant(particis, WORKFLOWPARTI);
            }
        }
        if ((list2 = saveObj.getDel_participants()) != null && !list2.isEmpty()) {
            this.deleteWorkflowParticipant(list2, define.getLinkid(), WORKFLOWPARTI);
        }
        if ((creat_actions = saveObj.getCreat_actions()) != null && !creat_actions.isEmpty()) {
            for (WorkFlowAction workFlowAction : creat_actions) {
                this.insertWorkflowAction(workFlowAction, WORKFLOWACTION);
            }
        }
        if ((actions = saveObj.getUpdate_actions()) != null && !actions.isEmpty()) {
            for (WorkFlowAction action : actions) {
                this.updateWorkflowAction(action, WORKFLOWACTION);
            }
        }
        if ((list = saveObj.getDel_actions()) != null && !list.isEmpty()) {
            this.deleteWorkflowAction(list, define.getLinkid(), WORKFLOWACTION);
        }
        return define.getId();
    }

    @Override
    public void insertWorkflowGroup(WorkFlowGroup workFlowGroup) throws JQException {
        this.insertWorkflowGroup(workFlowGroup, WORKFLOWGROUP);
    }

    @Override
    public void insertWorkflowDefine(WorkFlowDefine define) throws JQException {
        this.insertWorkflowDefine(define, WORKFLOWDEFINE);
    }

    @Override
    public void insertWorkflowNode(WorkFlowNodeSet workflowNode) throws JQException {
        this.insertWorkflowNode(workflowNode, WORKFLOWNODESET);
    }

    @Override
    public void insertWorkflowLine(WorkFlowLine workflowLine) throws JQException {
        this.insertWorkflowLine(workflowLine, WORKFLOWLINE);
    }

    @Override
    public void insertWorkflowParticipant(WorkFlowParticipant workflowParticipant) throws JQException {
        this.insertWorkflowParticipant(workflowParticipant, WORKFLOWPARTI);
    }

    @Override
    public void insertWorkflowAction(WorkFlowAction workflowAction) throws JQException {
        this.insertWorkflowAction(workflowAction, WORKFLOWACTION);
    }

    @Override
    public void updateWorkflowDefine(WorkFlowDefine define) throws JQException {
        this.updateWorkflowDefine(define, WORKFLOWDEFINE);
    }

    @Override
    public void updateWorkflowNode(WorkFlowNodeSet workflowNode) {
        this.updateWorkflowNode(workflowNode, WORKFLOWNODESET);
    }

    @Override
    public void updateWorkflowLine(WorkFlowLine workflowLine) {
        this.updateWorkflowLine(workflowLine, WORKFLOWLINE);
    }

    @Override
    public void updateWorkflowParticipant(WorkFlowParticipant workflowParticipant) {
        this.updateWorkflowParticipant(workflowParticipant, WORKFLOWPARTI);
    }

    @Override
    public void updateWorkflowAction(WorkFlowAction workflowAction) {
        this.updateWorkflowAction(workflowAction, WORKFLOWACTION);
    }

    @Override
    public void deleteWorkflowNode(List<String> nodeIds, String linkId) throws JQException {
        this.deleteWorkflowNode(nodeIds, linkId, WORKFLOWNODESET);
    }

    @Override
    public void deleteWorkflowLine(List<String> lineIds, String linkId) throws JQException {
        this.deleteWorkflowLine(lineIds, linkId, WORKFLOWLINE);
    }

    @Override
    public void deleteWorkflowLine(String lineId, String linkId) throws JQException {
        this.deleteWorkflowLine(lineId, linkId, WORKFLOWLINE);
    }

    @Override
    public void deleteWorkflowParticipant(List<String> partiIds, String linkId) throws JQException {
        this.deleteWorkflowParticipant(partiIds, linkId, WORKFLOWPARTI);
    }

    @Override
    public void deleteWorkflowAction(List<String> actionIds, String linkId) throws JQException {
        this.deleteWorkflowAction(actionIds, linkId, WORKFLOWACTION);
    }

    @Override
    public void deleteWorkflowById(String id, int state) throws JQException {
        this.deleteWorkflowById(id, state, WORKFLOWDEFINE);
    }

    @Override
    public WorkFlowGroup getWorkFlowGroupByID(String groupID) {
        return this.getWorkFlowGroupByID(groupID, WORKFLOWGROUP);
    }

    @Override
    public List<WorkFlowGroup> getAllWorkFlowGroup() {
        return this.getAllWorkFlowGroup(WORKFLOWGROUP);
    }

    @Override
    public WorkFlowGroup getWorkFlowGroupByTitle(String title) {
        return this.getWorkFlowGroupByTitle(title, WORKFLOWGROUP);
    }

    @Override
    public List<WorkFlowDefine> getAllWorkFlowDefineNoParentid() {
        return this.getAllWorkFlowDefineNoParentid(WORKFLOWDEFINE);
    }

    @Override
    public List<WorkFlowDefine> searchDefineByinput(String input) {
        return this.searchDefineByinput(input, WORKFLOWDEFINE);
    }

    @Override
    public List<WorkFlowDefine> getWorkFlowDefineByGroupID(String groupID) {
        return this.getWorkFlowDefineByGroupID(groupID, WORKFLOWDEFINE);
    }

    @Override
    public WorkFlowDefine getWorkFlowDefineByID(String defineID, int state) {
        return this.getWorkFlowDefineByID(defineID, state, WORKFLOWDEFINE);
    }

    @Override
    public String getWorkFlowDefineXmlByID(String defineID, int state) {
        return this.getWorkFlowDefineXmlByID(defineID, state, WORKFLOWDEFINE);
    }

    @Override
    public List<WorkFlowNodeSet> getWorkFlowNodeSetsByDefineID(String defineid, int state) {
        List<String> linkidByDefineID = this.getLinkidByDefineID(defineid, state, WORKFLOWDEFINE);
        if (linkidByDefineID.size() > 0) {
            return this.getWorkFlowNodeSetsByLinkID(linkidByDefineID.get(0), WORKFLOWNODESET);
        }
        return null;
    }

    @Override
    public WorkFlowNodeSet getWorkFlowNodeSetByID(String nodesetid, String linkId) {
        return this.getWorkFlowNodeSetByID(nodesetid, linkId, WORKFLOWNODESET);
    }

    @Override
    public WorkFlowLine getWorkFlowLineByID(String lineid, String linkId) {
        return this.getWorkFlowLineByID(lineid, linkId, WORKFLOWLINE);
    }

    @Override
    public List<WorkFlowAction> getWorkFlowActionByNodeID(String nodeid, String linkId) {
        return this.getWorkFlowActionByNodeID(nodeid, linkId, WORKFLOWACTION);
    }

    @Override
    public List<WorkFlowParticipant> getWorkFlowParticipantByNodeID(String nodeid, String linkId) {
        return this.getWorkFlowParticipantByNodeID(nodeid, linkId, WORKFLOWPARTI);
    }

    @Override
    public void updateWorkFlowDefineTitle(String id, int state, String title) {
        this.updateWorkFlowDefineTitle(id, state, title, WORKFLOWDEFINE);
    }

    @Override
    public Object updateWorkFlowGroup(WorkFlowGroup group) {
        return this.updateWorkFlowGroup(group, WORKFLOWGROUP);
    }

    @Override
    public void delWorkFlowGroupByID(String groupid) throws JQException {
        this.delWorkFlowGroupByID(groupid, WORKFLOWGROUP);
    }

    @Override
    public boolean groupHasChildern(String groupID) {
        return this.groupHasChildern(groupID, WORKFLOWGROUP);
    }

    @Override
    public void delWorkFlowNodeSetByID(String nodeid, String linkId) throws JQException {
        this.delWorkFlowNodeSetByID(nodeid, linkId, WORKFLOWNODESET);
    }

    @Override
    public List<WorkFlowDefine> getAllWorkFlowDefineByState(int state) {
        return this.getAllWorkFlowDefineByState(state, WORKFLOWDEFINE);
    }

    @Override
    public List<String> getAllDefineTitle() {
        return this.getAllDefineTitle(WORKFLOWDEFINE);
    }

    @Override
    public List<WorkFlowDefine> getWorkFlowDefineByTitle(String title) {
        return this.getWorkFlowDefineByTitle(title, WORKFLOWDEFINE);
    }

    @Override
    public List<WorkFlowDefine> getWorkFlowDefineByTitleAndTaskkey(String title, String taskKey) {
        return this.getWorkFlowDefineByTitleAndTask(title, taskKey, WORKFLOWDEFINE);
    }

    @Override
    public List<WorkFlowDefine> getWorkFlowDefineByCode(String code) {
        return this.getWorkFlowDefineByCode(code, WORKFLOWDEFINE);
    }

    @Override
    public WorkFlowDefine getWorkFlowDefineByLinkID(String linkid) {
        return this.getWorkFlowDefineByLinkID(linkid, WORKFLOWDEFINE);
    }

    @Override
    public List<WorkFlowNodeSet> getWorkFlowNodeSetsByLinkID(String linkid) {
        return this.getWorkFlowNodeSetsByLinkID(linkid, WORKFLOWNODESET);
    }

    @Override
    public List<WorkFlowParticipant> getWorkFlowParticipantsByID(String[] idarray, String linkId) {
        return this.getWorkFlowParticipantsByID(idarray, linkId, WORKFLOWPARTI);
    }

    @Override
    public List<WorkFlowParticipant> getWorkFlowParticipantsByLinkID(String linkid) {
        return this.getWorkFlowParticipantsByLinkID(linkid, WORKFLOWPARTI);
    }

    @Override
    public List<WorkFlowLine> getWorkFlowLinesByLinkID(String linkid) {
        return this.getWorkFlowLinesByLinkID(linkid, WORKFLOWLINE);
    }

    @Override
    public WorkFlowAction getWorkflowActionByCode(String code, String linkId) {
        return this.getWorkflowActionByCode(code, linkId, WORKFLOWACTION);
    }

    @Override
    public WorkFlowAction getWorkflowActionByCodeAndActionId(String nodeId, String actionId, String linkId) {
        return this.getWorkflowActionByCodeAndActionId(nodeId, actionId, linkId, WORKFLOWACTION);
    }

    @Override
    public List<WorkFlowAction> getWorkFlowActionsByLinkID(String linkid) {
        return this.getWorkFlowActionsByLinkID(linkid, WORKFLOWACTION);
    }

    @Override
    public List<WorkFlowLine> getWorkFlowLinesByPreTask(String preTaskCode, String linkId) {
        return this.getWorkFlowLinesByPreTask(preTaskCode, linkId, WORKFLOWLINE);
    }

    @Override
    public List<WorkFlowLine> getWorkFlowLinesByPreTask(Set<String> preTaskCode, String linkId) {
        return this.getWorkFlowLinesByPreTask(preTaskCode, linkId, WORKFLOWLINE);
    }

    @Override
    public WorkFlowAction getWorkflowActionById(String id, String linkId) {
        return this.getWorkflowActionById(id, linkId, WORKFLOWACTION);
    }

    @Override
    public List<WorkFlowLine> getWorkFlowLineByEndNode(String endNode, String linkId) {
        return this.getWorkFlowLineByEndNode(endNode, linkId, WORKFLOWLINE);
    }

    @Override
    public WorkFlowAction getWorkflowActionByCodeandLinkId(String actionCode, String linkId) {
        return this.getWorkflowActionByCodeandLinkId(actionCode, linkId, WORKFLOWACTION);
    }

    @Override
    public List<WorkFlowAction> getAllWorkflowAction() {
        return this.getAllWorkFlowActions(WORKFLOWACTION);
    }

    @Override
    public WorkFlowLine getWorkFlowLines(String beforeNodeId, String afterNodeId, String actionId, String linkId) {
        return this.getWorkFlowLines(beforeNodeId, afterNodeId, actionId, linkId, WORKFLOWLINE);
    }

    @Override
    public List<WorkFlowLine> getWorkFlowLinesByPreTaskAndAction(String preTaskCode, String actionId, String linkId) {
        return this.getWorkFlowLinesByPreTask(preTaskCode, actionId, linkId, WORKFLOWLINE);
    }

    @Override
    public List<WorkFlowLine> getWorkFlowLineByBNodeAndEndNode(List<String> bNode, String endNode, String linkId) {
        return this.getWorkFlowLineByEndNode(bNode, endNode, linkId, WORKFLOWLINE);
    }

    @Override
    public void deleteWorkflowNodeByLinkId(String linkId) throws JQException {
        this.delWorkFlowNodeSetByLinkid(linkId, WORKFLOWNODESET);
    }

    @Override
    public void deleteWorkflowLineByLinkId(String linkId) throws JQException {
        this.delWorkFlowLinkByLinkid(linkId, WORKFLOWLINE);
    }

    @Override
    public void deleteWorkflowParticipantByLinkId(String linkId) throws JQException {
        this.delWorkFlowParticpantByLinkid(linkId, WORKFLOWPARTI);
    }

    @Override
    public void deleteWorkflowActionByLinkId(String linkId) throws JQException {
        this.delWorkFlowActionByLinkid(linkId, WORKFLOWACTION);
    }

    @Override
    public List<WorkFlowDefine> getWorkFlowDefineByTaskKey(String taskKey) {
        return this.getWorkFlowDefineByTaskKey(taskKey, WORKFLOWDEFINE);
    }
}

