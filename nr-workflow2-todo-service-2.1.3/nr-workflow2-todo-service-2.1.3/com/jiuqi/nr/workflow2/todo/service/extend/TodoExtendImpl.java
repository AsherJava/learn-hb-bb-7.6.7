/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ProcessTaskNode
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow
 *  com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder
 *  com.jiuqi.nr.bpm.impl.upload.dao.TableConstant
 *  com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.todo.entityimpl.UploadState
 *  com.jiuqi.nr.workflow2.todo.entityimpl.WorkflowButton
 *  com.jiuqi.nr.workflow2.todo.enumeration.CommentType
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType
 *  com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface
 *  com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel
 *  com.jiuqi.nr.workflow2.todo.extend.env.WorkFlowNode
 *  com.jiuqi.nr.workflow2.todo.utils.TodoUtil
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.todo.service.extend;

import com.jiuqi.nr.bpm.de.dataflow.bean.ProcessTaskNode;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.todo.entityimpl.UploadState;
import com.jiuqi.nr.workflow2.todo.entityimpl.WorkflowButton;
import com.jiuqi.nr.workflow2.todo.enumeration.CommentType;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType;
import com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import com.jiuqi.nr.workflow2.todo.extend.env.WorkFlowNode;
import com.jiuqi.nr.workflow2.todo.service.extend.WorkFlowStateTableModelImpl;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoExtendImpl
implements TodoExtendInterface {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private Workflow workflowUtil;
    @Resource
    private IWorkflow workflow;
    @Resource
    private TodoUtil todoUtil;
    @Resource
    private ActionAndStateUtil actionAndStateUtil;
    @Resource
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IEntityMetaService entityMetaService;

    public List<WorkFlowNode> getAllWorkflowNode(String taskId, String period) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(taskId, period);
        TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
        boolean isDefaultWorkflow = this.workflowUtil.isDefaultWorkflow(formSchemeDefine.getKey());
        ArrayList<WorkFlowNode> nodeList = new ArrayList<WorkFlowNode>();
        List processTaskNodes = this.workflow.getProcessTaskNodes(formSchemeDefine);
        for (ProcessTaskNode node : processTaskNodes) {
            WorkFlowNode workFlowNode = new WorkFlowNode();
            workFlowNode.setCode(node.getCode());
            workFlowNode.setTitle(node.getTitle());
            workFlowNode.setOpenComment(node.isOpenCommitDes());
            if (node.getCode().equals("tsk_submit")) {
                boolean isReturnExplain = flowsSetting.isReturnExplain();
                boolean backDescriptionNeedWrite = flowsSetting.isBackDescriptionNeedWrite();
                workFlowNode.setCommentType(isReturnExplain ? (backDescriptionNeedWrite ? CommentType.BOTH : CommentType.ONLY_RETURN_REVIEW) : (backDescriptionNeedWrite ? CommentType.ONLY_RETURN : CommentType.NEITHER));
            }
            nodeList.add(workFlowNode);
        }
        if (isDefaultWorkflow && flowsSetting.isApplyReturn()) {
            WorkFlowNode requestRejectNode = new WorkFlowNode();
            requestRejectNode.setCode(TodoNodeType.REQUEST_REJECT.title);
            requestRejectNode.setTitle("\u7533\u8bf7\u9000\u56de");
            requestRejectNode.setOpenComment(true);
            nodeList.add(requestRejectNode);
        }
        return nodeList;
    }

    public WorkFlowType getFlowObjectType(String taskId, String period) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(taskId, period);
        TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
        return flowsSetting.getWordFlowType();
    }

    public WorkFlowType getFlowObjectType(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
        return flowsSetting.getWordFlowType();
    }

    public List<WorkflowButton> getWorkFlowButtons(String taskId, String period, String workFlowNode) {
        if (workFlowNode.equals(TodoNodeType.REQUEST_REJECT.title)) {
            return new ArrayList<WorkflowButton>();
        }
        ArrayList<WorkflowButton> buttonList = new ArrayList<WorkflowButton>();
        List actions = this.workflow.getExecuteActions(taskId, period, workFlowNode);
        for (WorkflowAction action : actions) {
            WorkflowButton button = new WorkflowButton();
            button.setCode(action.getCode());
            button.setTitle(action.getTitle());
            button.setShowBatch(action.getActionParam().isBatchOpt());
            button.setActionParam((Object)action.getActionParam());
            buttonList.add(button);
        }
        return buttonList;
    }

    public List<UploadState> getUploadStates(String taskId, String period, String workFlowNode) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(taskId, period);
        if (workFlowNode.equals(TodoNodeType.REQUEST_REJECT.title)) {
            ArrayList<UploadState> uploadStates = new ArrayList<UploadState>();
            UploadState uploadState = new UploadState();
            uploadState.setCode("act_upload");
            uploadState.setTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "act_upload", workFlowNode));
            uploadStates.add(uploadState);
            UploadState confirmState = new UploadState();
            confirmState.setCode("act_confirm");
            confirmState.setTitle(this.actionAndStateUtil.getStateTitleByActionCode(formSchemeDefine.getKey(), "act_confirm", workFlowNode));
            uploadStates.add(confirmState);
            return uploadStates;
        }
        List processAction = this.workflow.getProcessAction(formSchemeDefine, period, workFlowNode);
        return processAction.stream().map(action -> {
            UploadState uploadState = new UploadState();
            uploadState.setCode(action.getActionCode());
            uploadState.setTitle(action.getStateTitle());
            return uploadState;
        }).collect(Collectors.toList());
    }

    public WorkFlowStateTableModel getStateTableModel(String taskId, String period) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(taskId, period);
        return this.buildStateTableModel(formSchemeDefine);
    }

    public WorkFlowStateTableModel getStateTableModel(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.buildStateTableModel(formSchemeDefine);
    }

    private WorkFlowStateTableModelImpl buildStateTableModel(FormSchemeDefine formSchemeDefine) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(formSchemeDefine.getTaskKey());
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        List reportDimensions = this.taskService.getReportDimension(formSchemeDefine.getTaskKey());
        DataDimension reportDimension = null;
        for (DataDimension dimension : reportDimensions) {
            if (!this.workFlowDimensionBuilder.isCorporate(taskDefine, dimension, dwEntityModel)) continue;
            reportDimension = dimension;
            break;
        }
        String reportDimensionName = null;
        if (reportDimension != null) {
            reportDimensionName = this.todoUtil.getDimensionName(reportDimension.getDimKey());
        }
        WorkFlowStateTableModelImpl stateTableModel = new WorkFlowStateTableModelImpl();
        stateTableModel.setTableName(TableConstant.getSysUploadStateTableName((FormSchemeDefine)formSchemeDefine));
        stateTableModel.setHistoryTableName(TableConstant.getSysUploadRecordTableName((FormSchemeDefine)formSchemeDefine));
        stateTableModel.setWorkflowInstanceColumn("SERIAL_NUMBER");
        stateTableModel.setPeriodColumn("PERIOD");
        stateTableModel.setUnitColumn("MDCODE");
        stateTableModel.setWorkFlowObjectColumn("FORMID");
        stateTableModel.setUploadStateColumn("PREVEVENT");
        stateTableModel.setCurrentWorkFlowNodeColumn("CURNODE");
        stateTableModel.setReportDimensionColumn(reportDimensionName);
        stateTableModel.setCurrentActionColumn("CUREVENT");
        stateTableModel.setTimeColumn("TIME_");
        stateTableModel.setAdjustColumn("ADJUST");
        stateTableModel.setCommentColumn("CMT");
        return stateTableModel;
    }

    public boolean isReportDimensionEnable(String taskId) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskId);
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        List reportDimension = this.taskService.getReportDimension(taskId);
        for (DataDimension dimension : reportDimension) {
            if (!this.workFlowDimensionBuilder.isCorporate(taskDefine, dimension, dwEntityModel)) continue;
            return true;
        }
        return false;
    }

    public boolean isCorporate(TaskDefine taskDefine, DataDimension dimension) {
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        return this.workFlowDimensionBuilder.isCorporate(taskDefine, dimension, dwEntityModel);
    }

    public boolean isMultiEntityCaliberWithReportDimensionEnable(String taskId) {
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskId);
        List taskOrgLinkDefines = taskOrgLinkListStream.auth().getList();
        return taskOrgLinkDefines != null && taskOrgLinkDefines.size() > 1;
    }
}

