/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.tree;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.IActionAlias;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TreeWorkflow {
    private static final Logger logger = LoggerFactory.getLogger(TreeWorkflow.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController npRunController;
    @Resource
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IActionAlias actionAlias;

    public FieldDefine getFiledDefine(String formSchemeKey) {
        try {
            List<FieldDefine> allFiledDefine = this.getAllFiledDefine(formSchemeKey);
            if (allFiledDefine.size() > 0) {
                for (FieldDefine fieldDefine : allFiledDefine) {
                    String fieldCode = fieldDefine.getCode();
                    if (!"PREVEVENT".equals(fieldCode)) continue;
                    return fieldDefine;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public List<FieldDefine> getAllFiledDefine(String formSchemeKey) {
        try {
            if (StringUtils.isEmpty((String)formSchemeKey)) {
                return null;
            }
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String tableCode = TableConstant.getSysUploadStateTableName(formScheme);
            TableDefine tableDefine = this.npRunController.queryTableDefineByCode(tableCode);
            if (tableDefine != null) {
                List allFieldsInTable = this.npRunController.getAllFieldsInTable(tableDefine.getKey());
                return allFieldsInTable;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Map<String, String> getActionCodeAndActionName(String formSchemeKey) {
        Map<String, String> actionMap = new HashedMap<String, String>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
                if (defaultWorkflow) {
                    actionMap = this.defaultActionCodeAndName(formSchemeKey);
                } else {
                    WorkFlowDefine workFlowDefine;
                    WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                    if (workflowDefine != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefine.getWorkflowId(), 1)) != null) {
                        List actionCodes;
                        List<WorkFlowAction> workflowActions = this.customWorkFolwService.getRunWorkflowActionsByLinkid(workFlowDefine.getLinkid());
                        if (workflowActions != null && workflowActions.size() > 0) {
                            for (WorkFlowAction workFlowAction : workflowActions) {
                                String actionCode = workFlowAction.getActionCode();
                                String actionTitle = workFlowAction.getActionTitle();
                                actionMap.put(actionCode, actionTitle);
                                Map<String, String> action = this.getAction(workFlowDefine.getLinkid());
                                if (action == null || action.size() <= 0) continue;
                                for (Map.Entry<String, String> codeAndTitle : action.entrySet()) {
                                    actionMap.put("start", codeAndTitle.getValue());
                                }
                            }
                        }
                        if ((actionCodes = workflowActions.stream().map(e -> e.getActionCode()).collect(Collectors.toList())).contains("cus_submit")) {
                            actionMap.put("start", "\u5f00\u59cb");
                        } else if (actionCodes.contains("cus_upload")) {
                            actionMap.put("start", "\u5f00\u59cb");
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return actionMap;
    }

    public Map<String, String> getActionCodeAndStateName(String formSchemeKey) {
        Map<String, String> actionMap = new HashedMap<String, String>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
                if (defaultWorkflow) {
                    actionMap = this.defaultActionCodeAndStateName(formSchemeKey);
                } else {
                    WorkFlowDefine workFlowDefine;
                    WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                    if (workflowDefine != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefine.getWorkflowId(), 1)) != null) {
                        List actionCodes;
                        List<WorkFlowAction> workflowActions = this.customWorkFolwService.getRunWorkflowActionsByLinkid(workFlowDefine.getLinkid());
                        if (workflowActions != null && workflowActions.size() > 0) {
                            for (WorkFlowAction workFlowAction : workflowActions) {
                                String actionCode = workFlowAction.getActionCode();
                                String stateName = workFlowAction.getStateName();
                                actionMap.put(actionCode, stateName);
                                Map<String, String> action = this.getAction(workFlowDefine.getLinkid());
                                if (action == null || action.size() <= 0) continue;
                                for (Map.Entry<String, String> codeAndTitle : action.entrySet()) {
                                    actionMap.put("start", "\u672a" + codeAndTitle.getValue());
                                }
                            }
                        }
                        if ((actionCodes = workflowActions.stream().map(e -> e.getActionCode()).collect(Collectors.toList())).contains("cus_submit")) {
                            actionMap.put("start", "\u672a\u9001\u5ba1");
                        } else if (actionCodes.contains("cus_upload")) {
                            actionMap.put("start", "\u672a\u4e0a\u62a5");
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return actionMap;
    }

    private Map<String, String> getAction(String linkId) {
        HashSet<String> nodes = new HashSet<String>();
        HashMap<String, String> actionAndNode = new HashMap<String, String>();
        Map<String, String> actionAndTitle = new HashMap<String, String>();
        try {
            List<WorkFlowLine> linesByLinkid = this.customWorkFolwService.getWorkflowLinesByLinkid(linkId);
            if (linesByLinkid != null && linesByLinkid.size() > 0) {
                for (WorkFlowLine lines : linesByLinkid) {
                    String[] actions;
                    if (!lines.getBeforeNodeID().startsWith("Start")) continue;
                    String aftreNodeID = lines.getAfterNodeID();
                    nodes.add(aftreNodeID);
                    WorkFlowNodeSet nodeSetByID = this.customWorkFolwService.getWorkFlowNodeSetByID(aftreNodeID, linkId);
                    if (nodeSetByID == null) continue;
                    for (String actionId : actions = nodeSetByID.getActions()) {
                        WorkFlowAction action = this.customWorkFolwService.getWorkflowActionById(actionId, linkId);
                        actionAndNode.put(action.getActionCode(), action.getActionTitle());
                        if (!"cus_upload".equals(action.getActionCode()) && !"cus_confirm".equals(action.getActionCode())) continue;
                        actionAndTitle.put(action.getActionCode(), action.getActionTitle());
                        return actionAndTitle;
                    }
                }
                int i = 0;
                actionAndTitle = this.actionAndNodeInfo(nodes, actionAndNode, i, linkId);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionAndTitle;
    }

    private Map<String, String> actionAndNodeInfo(Set<String> nodeId, Map<String, String> actionAndNode, int i, String linkId) {
        List<WorkFlowLine> lineByBeforeNode = this.customWorkFolwService.getWorkflowLinesByPreTask(nodeId, linkId);
        if (lineByBeforeNode != null && lineByBeforeNode.size() > 0) {
            nodeId.clear();
            for (WorkFlowLine workFlowLine : lineByBeforeNode) {
                String[] actions;
                nodeId.add(workFlowLine.getAfterNodeID());
                WorkFlowNodeSet nodeSetByID = this.customWorkFolwService.getWorkFlowNodeSetByID(workFlowLine.getAfterNodeID(), linkId);
                if (nodeSetByID == null || (actions = nodeSetByID.getActions()) == null || actions.length <= 0) continue;
                for (String actionId : actions) {
                    WorkFlowAction workFlowAction = this.customWorkFolwService.getWorkflowActionById(actionId, linkId);
                    actionAndNode.put(workFlowAction.getActionCode(), workFlowAction.getActionTitle());
                }
            }
            for (Map.Entry entry : actionAndNode.entrySet()) {
                if (!"cus_upload".equals(entry.getKey()) && !"cus_confirm".equals(entry.getKey())) continue;
                actionAndNode.clear();
                actionAndNode.put((String)entry.getKey(), (String)entry.getValue());
                return actionAndNode;
            }
            if (++i < 25) {
                this.actionAndNodeInfo(nodeId, actionAndNode, i, linkId);
            }
        }
        return actionAndNode;
    }

    private Map<String, String> defaultActionCodeAndName(String formSchemeKey) {
        Map<String, String> actionMap = new HashedMap<String, String>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            actionMap = this.actionAlias.actionCodeAndActionName(formSchemeKey);
            if (actionMap == null) {
                actionMap = new HashedMap();
                TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
                if (flowsSetting.isUnitSubmitForCensorship()) {
                    actionMap.put("start".toString(), "\u9001\u5ba1");
                    actionMap.put("act_upload".toString(), "\u4e0a\u62a5");
                    if (flowsSetting.isDataConfirm()) {
                        actionMap.put("act_confirm".toString(), "\u786e\u8ba4");
                    }
                    actionMap.put("act_reject".toString(), "\u9000\u56de");
                    actionMap.put("act_submit".toString(), "\u9001\u5ba1");
                    actionMap.put("act_return".toString(), "\u9000\u5ba1");
                } else {
                    actionMap.put("start".toString(), "\u4e0a\u62a5");
                    actionMap.put("act_upload".toString(), "\u4e0a\u62a5");
                    if (flowsSetting.isDataConfirm()) {
                        actionMap.put("act_confirm".toString(), "\u786e\u8ba4");
                    }
                    actionMap.put("act_reject".toString(), "\u9000\u56de");
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionMap;
    }

    private Map<String, String> defaultActionCodeAndStateName(String formSchemeKey) {
        Map<String, String> actionMap = new HashedMap<String, String>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            actionMap = this.actionAlias.actionCodeAndStateName(formSchemeKey);
            if (actionMap == null) {
                actionMap = new HashedMap();
                TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
                if (flowsSetting.isUnitSubmitForCensorship()) {
                    actionMap.put("start".toString(), "\u672a\u9001\u5ba1");
                    actionMap.put("act_upload".toString(), "\u5df2\u4e0a\u62a5");
                    if (flowsSetting.isDataConfirm()) {
                        actionMap.put("act_confirm".toString(), "\u5df2\u786e\u8ba4");
                    }
                    actionMap.put("act_reject".toString(), "\u5df2\u9000\u56de");
                    actionMap.put("act_submit".toString(), "\u5df2\u9001\u5ba1");
                    actionMap.put("act_return".toString(), "\u5df2\u9000\u5ba1");
                } else {
                    actionMap.put("start".toString(), "\u672a\u4e0a\u62a5");
                    actionMap.put("act_upload".toString(), "\u5df2\u4e0a\u62a5");
                    if (flowsSetting.isDataConfirm()) {
                        actionMap.put("act_confirm".toString(), "\u5df2\u786e\u8ba4");
                    }
                    actionMap.put("act_reject".toString(), "\u5df2\u9000\u56de");
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionMap;
    }

    public Map<String, String> getActionInfo(String formSchemeKey, String period) {
        Map<String, String> actionMap = null;
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            actionMap = this.actionAlias.actionCodeAndStateName(formSchemeKey);
            if (actionMap == null) {
                WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formScheme.getKey());
                WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
                actionMap = this.queryUploadState(dimensionValueSet, null, formScheme, workFlowDefine.getLinkid());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionMap;
    }

    public Map<String, String> queryUploadState(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, String linkId) {
        TableModelDefine uploadStateTable = null;
        List allColumns = null;
        try {
            String tableCode = "SYS_UP_ST_" + formScheme.getFormSchemeCode();
            uploadStateTable = this.dataModelService.getTableModelDefineByCode(tableCode);
            allColumns = this.dataModelService.getColumnModelDefinesByTable(uploadStateTable.getID());
        }
        catch (Exception e) {
            throw new BpmException(e);
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        for (ColumnModelDefine column : allColumns) {
            switch (column.getCode()) {
                case "FORMID": {
                    if (formKey == null) break;
                    ArrayList<String> formKeyList = new ArrayList<String>();
                    formKeyList.add(formKey);
                    NvwaQueryColumn queryColumn = new NvwaQueryColumn(column);
                    queryModel.getColumnFilters().put(column, formKeyList);
                    queryColumn.setAggrType(AggrType.NONE);
                    break;
                }
                case "PREVEVENT": {
                    NvwaQueryColumn queryColumn = new NvwaQueryColumn(column);
                    queryModel.getColumns().add(queryColumn);
                    queryColumn.setAggrType(AggrType.NONE);
                    queryModel.getGroupByColumns().add(0);
                    break;
                }
                case "CURNODE": {
                    NvwaQueryColumn curnodeColumn = new NvwaQueryColumn(column);
                    queryModel.getColumns().add(curnodeColumn);
                    curnodeColumn.setAggrType(AggrType.COUNT);
                    queryModel.getGroupByColumns().add(1);
                    break;
                }
            }
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        String action = "";
        String node = "";
        try {
            MemoryDataSet dataSets = readOnlyDataAccess.executeQuery(context);
            if (dataSets.size() == 0) {
                return null;
            }
            for (DataRow dataRow : dataSets) {
                block24: for (int j = 0; j < allColumns.size(); ++j) {
                    ColumnModelDefine column = (ColumnModelDefine)allColumns.get(j);
                    String fieldValue = "";
                    fieldValue = dataRow.getString(j);
                    switch (column.getCode()) {
                        case "PREVEVENT": {
                            action = fieldValue;
                            continue block24;
                        }
                        case "CURNODE": {
                            node = fieldValue;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.getState(action, node, formScheme, linkId);
    }

    private Map<String, String> getState(String action, String node, FormSchemeDefine formScheme, String linkId) {
        LinkedHashMap<String, String> actionInfo = new LinkedHashMap<String, String>();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme.getKey());
        if (defaultWorkflow) {
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            if (flowsSetting.isUnitSubmitForCensorship()) {
                actionInfo.put("start".toString(), "\u672a\u9001\u5ba1");
                actionInfo.put("act_upload".toString(), "\u5df2\u4e0a\u62a5");
                if (flowsSetting.isDataConfirm()) {
                    actionInfo.put("act_confirm".toString(), "\u5df2\u786e\u8ba4");
                }
                actionInfo.put("act_reject".toString(), "\u5df2\u9000\u56de");
                actionInfo.put("act_submit".toString(), "\u5df2\u9001\u5ba1");
                actionInfo.put("act_return".toString(), "\u5df2\u9000\u5ba1");
            } else {
                actionInfo.put("start".toString(), "\u672a\u4e0a\u62a5");
                actionInfo.put("act_upload".toString(), "\u5df2\u4e0a\u62a5");
                if (flowsSetting.isDataConfirm()) {
                    actionInfo.put("act_confirm".toString(), "\u5df2\u786e\u8ba4");
                }
                actionInfo.put("act_reject".toString(), "\u5df2\u9000\u56de");
            }
        } else if ("start".equals(action)) {
            actionInfo.put("start".toString(), "\u672a\u9001\u5ba1");
        } else {
            String linkid;
            WorkFlowAction workFlowAction;
            List<WorkFlowLine> workflowAction = this.customWorkFolwService.getWorkflowLineByEndNode(node, linkId);
            if (workflowAction.size() > 0 && (workFlowAction = this.customWorkFolwService.getWorlflowActionByCodeAndLinkId(action, linkid = workflowAction.get(0).getLinkid())) != null) {
                if ("start".equals(action)) {
                    actionInfo.put("start".toString(), "\u672a" + workFlowAction.getActionDesc());
                } else {
                    String stateCode = (String)actionInfo.get(workFlowAction.getActionCode());
                    if (null == stateCode) {
                        actionInfo.put(workFlowAction.getActionCode(), workFlowAction.getStateCode());
                    }
                }
            }
        }
        return actionInfo;
    }
}

