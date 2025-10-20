/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.va.biz.intf.autotask.AutoTask
 *  com.jiuqi.va.biz.intf.autotask.AutoTaskManager
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.constants.BizState
 *  com.jiuqi.va.domain.constants.CompleteUserTypeEnum
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.AutoTaskModule
 *  com.jiuqi.va.domain.workflow.NodeRejectType
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.SubProcessInfoDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.ValueType
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowOperation
 *  com.jiuqi.va.domain.workflow.WorkflowProcessStatus
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO
 *  com.jiuqi.va.domain.workflow.retract.RetractRejectInfo
 *  com.jiuqi.va.domain.workflow.retract.RetractTypeEnum
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice
 *  com.jiuqi.va.domain.workflow.service.WorkflowInfoService
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSubProcessBranchStrategyService
 *  com.jiuqi.va.extend.WorkflowNodeBehavior
 *  com.jiuqi.va.feign.client.AutoTaskClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.trans.domain.VaTransMessageDO
 *  org.activiti.engine.ActivitiException
 */
package com.jiuqi.va.workflow.model.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.biz.intf.autotask.AutoTaskManager;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.constants.BizState;
import com.jiuqi.va.domain.constants.CompleteUserTypeEnum;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.AutoTaskModule;
import com.jiuqi.va.domain.workflow.NodeRejectType;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.SubProcessInfoDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.ValueType;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowOperation;
import com.jiuqi.va.domain.workflow.WorkflowProcessStatus;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDTO;
import com.jiuqi.va.domain.workflow.retract.RetractRejectInfo;
import com.jiuqi.va.domain.workflow.retract.RetractTypeEnum;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice;
import com.jiuqi.va.domain.workflow.service.WorkflowInfoService;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowSubProcessBranchStrategyService;
import com.jiuqi.va.extend.WorkflowNodeBehavior;
import com.jiuqi.va.feign.client.AutoTaskClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.trans.domain.VaTransMessageDO;
import com.jiuqi.va.workflow.config.AutoTaskModuleConfig;
import com.jiuqi.va.workflow.config.ThreadPoolConst;
import com.jiuqi.va.workflow.constants.VaWorkflowConstants;
import com.jiuqi.va.workflow.formula.FormulaParam;
import com.jiuqi.va.workflow.formula.WorkflowContext;
import com.jiuqi.va.workflow.formula.WorkflowFormulaExecute;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.impl.BaseWorkflowModelImpl;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPlugin;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.service.plus.approval.WorkflowPlusApprovalConsultService;
import com.jiuqi.va.workflow.utils.SequenceConditionUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowNodeUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class WorkflowModelImpl
extends BaseWorkflowModelImpl {
    private static final Logger log = LoggerFactory.getLogger(WorkflowModelImpl.class);
    private VaWorkflowProcessService vaWorkflowProcessService;
    private AutoTaskManager autoTaskManager;
    private AutoTaskModuleConfig autoTaskModuleConfig;
    private WorkflowProcessNodeService workflowProcessNodeService;
    private WorkflowOptionService workflowOptionService;
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    private WorkflowMetaService workflowMetaService;
    private WorkflowInfoService workflowInfoService;
    private WorkflowParamService workflowParamService;
    private WorkflowSubProcessBranchStrategyService workflowSubProcessBranchStrategyService;
    private WorkflowPlusApprovalConsultService workflowPlusApprovalConsultService;

    protected boolean isAutoAgree(String taskDefineKey) {
        ProcessDesignPlugin ProcessDesignPlugin2 = (ProcessDesignPlugin)this.getPlugins().find(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)ProcessDesignPlugin2.getDefine();
        JsonNode process = processDesignPluginDefine.getData();
        boolean globleAutoAgree = process.get("globleAutoAgree") != null && process.get("globleAutoAgree").asBoolean();
        ArrayNode nodes = (ArrayNode)process.get("childShapes");
        for (JsonNode node : nodes) {
            String autoAgree;
            String nodeId = node.get("resourceId").asText();
            if (!taskDefineKey.equals(nodeId)) continue;
            JsonNode autoAgreeNode = node.get("properties").get("autoAgree");
            String string = autoAgree = autoAgreeNode == null ? "01" : autoAgreeNode.asText();
            if ("01".equals(autoAgree)) {
                return globleAutoAgree;
            }
            if ("02".equals(autoAgree)) {
                return true;
            }
            if (!"03".equals(autoAgree)) break;
            return false;
        }
        return globleAutoAgree;
    }

    protected int getNodeRejectType(String taskDefineKey) {
        ArrayNode nodes = this.getNodes();
        for (JsonNode node : nodes) {
            String nodeId = node.get("resourceId").asText();
            if (taskDefineKey.equals(nodeId)) {
                JsonNode rejectTypeNode = node.get("properties").get("prioritydefinition");
                return rejectTypeNode == null || "".equals(rejectTypeNode.asText()) ? NodeRejectType.REJECT_TO_SUBMITUSER.getValue() : rejectTypeNode.asInt();
            }
            if (ObjectUtils.isEmpty(node.get("childShapes"))) continue;
            JsonNode childShapes = node.get("childShapes");
            for (JsonNode childShape : childShapes) {
                String nodeType = childShape.get("stencil").get("id").asText();
                String resourceId = childShape.get("resourceId").asText();
                if ("StartNoneEvent".equals(nodeType) || "EndNoneEvent".equals(nodeType) || !resourceId.equals(taskDefineKey)) continue;
                JsonNode rejectTypeNode = childShape.get("properties").get("prioritydefinition");
                return rejectTypeNode == null || "".equals(rejectTypeNode.asText()) ? NodeRejectType.REJECT_TO_SUBMITUSER.getValue() : rejectTypeNode.asInt();
            }
        }
        return NodeRejectType.REJECT_TO_SUBMITUSER.getValue();
    }

    protected JsonNode getNodeConfig(String taskDefineKey) {
        ArrayNode nodes = this.getNodes();
        for (JsonNode node : nodes) {
            String nodeId = node.get("resourceId").asText();
            if (taskDefineKey.equals(nodeId)) {
                return node;
            }
            JsonNode childShapes = node.get("childShapes");
            if (ObjectUtils.isEmpty(childShapes)) continue;
            for (JsonNode childShape : childShapes) {
                String childResourceId = childShape.get("resourceId").asText();
                if (!taskDefineKey.equals(childResourceId)) continue;
                return childShape;
            }
        }
        return null;
    }

    @Override
    protected boolean isForbidEmail(String taskDefineKey) {
        ArrayNode nodes = this.getNodes();
        for (JsonNode node : nodes) {
            String nodeId = node.get("resourceId").asText();
            if (!taskDefineKey.equals(nodeId)) continue;
            JsonNode isForbidEmail = node.get("properties").get("forbidemail");
            return isForbidEmail != null && isForbidEmail.asBoolean();
        }
        return false;
    }

    private ArrayNode getNodes() {
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)this.getPlugins().find(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        JsonNode process = processDesignPluginDefine.getData();
        return (ArrayNode)process.get("childShapes");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R startProcess(WorkflowDTO workflowDTO) {
        UserLoginDTO userLoginDTO = ShiroUtil.getUser();
        String tenantName = userLoginDTO.getTenantName();
        workflowDTO.setTenantName(tenantName);
        String bizCode = workflowDTO.getBizCode();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO != null) {
            if (StringUtils.hasText(workflowDTO.getSubProcessBranch())) {
                workflowDTO.setApprovalResult(Integer.valueOf(1));
                workflowDTO.setApprovalComment("\u63d0\u4ea4");
                workflowDTO.setProcessInstanceId(processDO.getId());
                workflowDTO.addExtInfo("subProcessSubmit", (Object)true);
                return this.completeTask(workflowDTO);
            }
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.existnotendprocess"));
        }
        try {
            List todos;
            String bizType = workflowDTO.getBizType();
            String bizDefine = workflowDTO.getBizDefine();
            String processDefinitionKey = workflowDTO.getUniqueCode();
            Map<String, Object> workflowVariables = workflowDTO.getWorkflowVariables();
            if (workflowVariables == null) {
                processDO = new ProcessDO();
                processDO.setBizcode(bizCode);
                processDO.setDefinekey(processDefinitionKey);
                if (workflowDTO.getProcessDefineVersion() != null) {
                    processDO.setDefineversion(new BigDecimal(workflowDTO.getProcessDefineVersion()));
                }
                workflowVariables = this.getWorkflowVariables(processDO, bizType, bizDefine);
                workflowDTO.setWorkflowVariables(workflowVariables);
            }
            this.calProcessParam(workflowVariables);
            HashMap<String, WorkflowModelImpl> customParam = new HashMap<String, WorkflowModelImpl>();
            customParam.put("workflowModel", this);
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setDefineNodes(this.getNodes());
            vaWorkflowContext.setTransMessageId(workflowDTO.getTransMessageId());
            vaWorkflowContext.setOperation(WorkflowOperation.SUBMIT);
            workflowDTO.setApprovalResult(Integer.valueOf(1));
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setVariables(workflowVariables);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            if (workflowDTO.isRejectSkipNode()) {
                TenantDO tenantDO = new TenantDO();
                tenantDO.addExtInfo("metaVersion", (Object)workflowDTO.getProcessDefineVersion());
                tenantDO.addExtInfo("workflowDefineKey", (Object)processDefinitionKey);
                Integer workflowDefineVer = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
                this.workflowSevice.startProcess(processDefinitionKey, workflowDefineVer);
            } else {
                this.workflowSevice.startProcess(processDefinitionKey);
            }
            int processStatus = (Integer)customParam.get("processStatus");
            Object transMessageId = customParam.get("transMessageId");
            R result = new R();
            if (!ObjectUtils.isEmpty(transMessageId)) {
                try {
                    VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
                    vaTransMessageDO.setId(transMessageId.toString());
                    result = this.vaTransMessageService.triggerTrans(vaTransMessageDO);
                }
                catch (Exception e) {
                    log.error("{}\u63d0\u4ea4\u89e6\u53d1\u540e\u7eed\u7f16\u6392\u6267\u884c\u5931\u8d25", (Object)bizCode, (Object)e);
                    result = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.submitfailed"));
                }
            } else {
                result.put("data", (Object)vaWorkflowContext.getOutput());
            }
            this.executeAutoTask();
            if (WorkflowProcessStatus.PROCESS_UNFINSHED_NORMAL.getValue() == processStatus && customParam.containsKey("todoTasks") && !(todos = (List)customParam.get("todoTasks")).isEmpty()) {
                this.sendMessage(todos, tenantName, workflowDTO);
            }
            if (WorkflowProcessStatus.PROCESS_FINSHED_AGREE.getValue() == processStatus) {
                workflowDTO.setApprovalResult(Integer.valueOf(1));
                this.executeAutoTask(processStatus, workflowDTO);
                this.sendCompleteMessageBefore(workflowDTO);
                this.sendCompleteMessage(workflowDTO);
            }
            R r = result;
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R reStartProcess(WorkflowDTO workflowDTO, UserLoginDTO submitUser) {
        UserLoginDTO userLoginDTO = ShiroUtil.getUser();
        String tenantName = userLoginDTO.getTenantName();
        workflowDTO.setTenantName(tenantName);
        String bizCode = workflowDTO.getBizCode();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        if (processDO != null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.existnotendprocess"));
        }
        try {
            List todos;
            String processDefinitionKey = workflowDTO.getUniqueCode();
            String bizType = workflowDTO.getBizType();
            String bizDefine = workflowDTO.getBizDefine();
            processDO = new ProcessDO();
            processDO.setBizcode(bizCode);
            processDO.setDefinekey(processDefinitionKey);
            if (workflowDTO.getProcessDefineVersion() != null) {
                processDO.setDefineversion(new BigDecimal(workflowDTO.getProcessDefineVersion()));
            }
            Map<String, Object> workflowVariables = this.getWorkflowVariables(processDO, bizType, bizDefine);
            this.calProcessParam(workflowVariables);
            workflowDTO.setWorkflowVariables(workflowVariables);
            HashMap<String, WorkflowModelImpl> customParam = new HashMap<String, WorkflowModelImpl>();
            customParam.put("workflowModel", this);
            customParam.put("submitUser", (WorkflowModelImpl)submitUser);
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setDefineNodes(this.getNodes());
            vaWorkflowContext.setOperation(WorkflowOperation.RESTART);
            workflowDTO.setApprovalResult(Integer.valueOf(1));
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setTransMessageId(workflowDTO.getTransMessageId());
            vaWorkflowContext.setVariables(workflowVariables);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("metaVersion", (Object)workflowDTO.getProcessDefineVersion());
            tenantDO.addExtInfo("workflowDefineKey", (Object)processDefinitionKey);
            Integer workflowDefineVer = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
            this.workflowSevice.startProcess(processDefinitionKey, workflowDefineVer);
            int processStatus = (Integer)customParam.get("processStatus");
            if (WorkflowProcessStatus.PROCESS_UNFINSHED_NORMAL.getValue() == processStatus && customParam.containsKey("todoTasks") && !(todos = (List)customParam.get("todoTasks")).isEmpty()) {
                this.sendMessage(todos, tenantName, workflowDTO);
            }
            if (WorkflowProcessStatus.PROCESS_FINSHED_AGREE.getValue() == processStatus) {
                workflowDTO.setApprovalResult(Integer.valueOf(1));
                this.executeAutoTask(processStatus, workflowDTO);
                this.sendCompleteMessageBefore(workflowDTO);
                this.sendCompleteMessage(workflowDTO);
            }
            R result = new R();
            result.put("data", (Object)vaWorkflowContext.getOutput());
            R r = result;
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    @Override
    public Map<String, Map<String, Object>> getNodesProperty(String defineKey, int version, Set<String> resourceIds) {
        return this.workflowSevice.getNodesProperty(defineKey, Integer.valueOf(version), resourceIds);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R completeTask(WorkflowDTO workflowDTO) {
        Map todoParamMap;
        R checkSelectNextApprovalNodeResult;
        String processInstanceId = workflowDTO.getProcessInstanceId();
        ProcessDO processDO = this.getProcessDO(processInstanceId);
        if (processDO == null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.thisprocesshasend"));
        }
        int processStatus = processDO.getStatus().intValue();
        if (WorkflowProcessStatus.PROCESS_UNFINSHED_PAUSE.getValue() == processStatus) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processishung"));
        }
        if (workflowDTO.getProcessDefineVersion() == null) {
            workflowDTO.setProcessDefineVersion(Long.valueOf(processDO.getDefineversion().longValue()));
        }
        if ((checkSelectNextApprovalNodeResult = this.checkSelectNextApprovalNode(workflowDTO)).getCode() != 0) {
            return checkSelectNextApprovalNodeResult;
        }
        UserLoginDTO user = VaWorkFlowDataUtils.getCurrentUserInfo();
        String tenantName = user.getTenantName();
        workflowDTO.setTenantName(tenantName);
        boolean isSubProcessSubmit = !ObjectUtils.isEmpty(workflowDTO.getExtInfo("subProcessSubmit"));
        Map<String, Object> currentTask = isSubProcessSubmit ? this.getCurrentRejectTodoTask(workflowDTO, processDO.getBizcode()) : this.getCurrentTodoTask(workflowDTO);
        if (currentTask == null) {
            currentTask = new HashMap<String, Object>();
        }
        if ((todoParamMap = workflowDTO.getTodoParamMap()) != null) {
            currentTask.putAll(todoParamMap);
        }
        if (workflowDTO.getExtInfo() == null) {
            workflowDTO.setExtInfo(new HashMap<String, Object>(currentTask));
        } else {
            workflowDTO.putAll(currentTask);
        }
        if (isSubProcessSubmit) {
            workflowDTO.setTaskId((String)currentTask.get("TASKID"));
            workflowDTO.setTodoParamMap(currentTask);
            workflowDTO.setNodeCode(String.valueOf(currentTask.get("TASKDEFINEKEY")));
            workflowDTO.addExtInfo("isSubmit", (Object)true);
        }
        String bizType = String.valueOf(currentTask.get("BIZTYPE"));
        String bizDefine = String.valueOf(currentTask.get("BIZDEFINE"));
        String bizCode = String.valueOf(currentTask.get("BIZCODE"));
        workflowDTO.setBizType(bizType);
        workflowDTO.setBizDefine(bizDefine);
        workflowDTO.setBizCode(bizCode);
        if (workflowDTO.isConsult()) {
            try {
                VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
                HashMap customParam = new HashMap();
                vaWorkflowContext.setCustomParam(customParam);
                VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                this.workflowPlusApprovalConsultService.consult(workflowDTO);
                Object transMessageId = customParam.get("transMessageId");
                R result = new R();
                if (transMessageId != null) {
                    VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
                    vaTransMessageDO.setId(transMessageId.toString());
                    try {
                        result = this.vaTransMessageService.triggerTrans(vaTransMessageDO);
                    }
                    catch (Exception e) {
                        log.error("\u52a0\u7b7e\u4e0d\u53c2\u4e0e\u5ba1\u6279\u5b8c\u6210\u89e6\u53d1\u540e\u7eed\u7f16\u6392\u6267\u884c\u5931\u8d25", e);
                        result = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.completefailed"));
                    }
                }
                Locale parentLocale = LocaleContextHolder.getLocale();
                BaseWorkflowModelImpl.CounterSignerSendMessageTask counterSignerSendMessageTask = new BaseWorkflowModelImpl.CounterSignerSendMessageTask(this, workflowDTO, user, this.vaMessageTemplateClient, this.workflowModelHelper, parentLocale);
                ThreadPoolConst.SEND_MESSAGE_THREADPOOL.execute(counterSignerSendMessageTask);
                this.sendCarbonCopyMessage(workflowDTO, currentTask, user);
                R r = result;
                return r;
            }
            finally {
                VaContext.removeVaWorkflowContext();
            }
        }
        try {
            int approvalResult = workflowDTO.getApprovalResult();
            HashMap<String, Object> customParam = new HashMap<String, Object>();
            customParam.put("workflowModel", this);
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setDefineNodes(this.getNodes());
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setProcessDO(processDO);
            vaWorkflowContext.setCurrentTask(currentTask);
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setTransMessageId(workflowDTO.getTransMessageId());
            workflowDTO.getExtInfo().put("SUBMITTIME", processDO.getStarttime());
            workflowDTO.getExtInfo().put("SUBMITUSER", processDO.getStartuser());
            if (2 == approvalResult) {
                Integer rejectType = workflowDTO.getRejectType();
                if (rejectType == null) {
                    rejectType = this.getNodeRejectType(String.valueOf(currentTask.get("TASKDEFINEKEY")));
                }
                workflowDTO.setRejectType(rejectType);
                customParam.put("rejectType", rejectType);
                vaWorkflowContext.setOperation(WorkflowOperation.REJECT);
            } else if (1 == approvalResult) {
                vaWorkflowContext.setOperation(WorkflowOperation.AGREE);
            }
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            Map<String, Object> workflowVariables = this.getWorkflowVariables(processDO, bizType, bizDefine);
            if (workflowVariables != null) {
                if (workflowDTO.getWorkflowVariables() != null) {
                    workflowVariables.putAll(workflowDTO.getWorkflowVariables());
                }
                workflowDTO.setWorkflowVariables(workflowVariables);
            }
            this.calProcessParam(workflowVariables);
            vaWorkflowContext.setVariables(workflowDTO.getWorkflowVariables());
            this.workflowSevice.complete(workflowDTO.getTaskId(), workflowDTO.getApprovalResult().intValue());
            processStatus = (Integer)customParam.get("processStatus");
            Object transMessageId = customParam.get("transMessageId");
            R result = new R();
            if (!ObjectUtils.isEmpty(transMessageId)) {
                try {
                    VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
                    vaTransMessageDO.setId(transMessageId.toString());
                    result = this.vaTransMessageService.triggerTrans(vaTransMessageDO);
                }
                catch (Exception e) {
                    log.error("{}\u5ba1\u6279\u89e6\u53d1\u540e\u7eed\u7f16\u6392\u6267\u884c\u5931\u8d25", (Object)bizCode, (Object)e);
                    result = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.completefailed"));
                }
            } else {
                result.put("data", (Object)vaWorkflowContext.getOutput());
            }
            this.executeAutoTask();
            if (WorkflowProcessStatus.PROCESS_FINSHED_REJECT.getValue() == processStatus || WorkflowProcessStatus.PROCESS_FINSHED_AGREE.getValue() == processStatus) {
                this.executeAutoTask(processStatus, workflowDTO);
                this.sendCompleteMessageBefore(workflowDTO);
                this.sendCompleteMessage(workflowDTO);
            } else if (WorkflowProcessStatus.PROCESS_UNFINSHED_NORMAL.getValue() == processStatus) {
                List todos;
                if (customParam.containsKey("todoTasks") && !(todos = (List)customParam.get("todoTasks")).isEmpty()) {
                    this.sendMessage(todos, tenantName, workflowDTO);
                }
                this.sendNotifyCreateUserMessageBefore(workflowDTO);
            }
            this.counterSignerAuditSendMessage(workflowDTO, currentTask, user);
            this.sendCarbonCopyMessage(workflowDTO, currentTask, user);
            R r = result;
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    private void sendCompleteMessageBefore(WorkflowDTO workflowDTO) {
        Map customParam = VaContext.getVaWorkflowContext().getCustomParam();
        if (customParam.containsKey("processHistory")) {
            ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)customParam.get("processHistory");
            Map extInfo = workflowDTO.getExtInfo();
            if (!extInfo.containsKey("SUBMITTIME")) {
                workflowDTO.getExtInfo().put("SUBMITTIME", processHistoryDO.getStarttime());
            }
            if (!extInfo.containsKey("COMPLETETIME")) {
                workflowDTO.getExtInfo().put("COMPLETETIME", processHistoryDO.getEndtime());
            }
            if (!extInfo.containsKey("SUBMITUSER")) {
                workflowDTO.getExtInfo().put("SUBMITUSER", processHistoryDO.getStartuser());
            }
        }
    }

    private void sendNotifyCreateUserMessageBefore(WorkflowDTO workflowDTO) {
        if (!"1".equals(this.getWorkflowOption("WF1019"))) {
            return;
        }
        String nodeCode = workflowDTO.getNodeCode();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(workflowDTO.getBizCode());
        processNodeDTO.setProcessid(workflowDTO.getProcessInstanceId());
        List processNodeDOS = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        List<ProcessNodeDO> unApprovalProcessNodes = processNodeDOS.stream().filter(processNodeDO -> processNodeDO.getCompletetime() == null && !processNodeDO.getNodeid().equals(processNodeDO.getPgwnodeid()) && !processNodeDO.getNodeid().equals(processNodeDO.getSubprocessnodeid())).collect(Collectors.toList());
        for (ProcessNodeDO unApprovalProcessNode : unApprovalProcessNodes) {
            if (!nodeCode.equals(unApprovalProcessNode.getNodecode()) || !Objects.equals(workflowDTO.getSubProcessBranch(), unApprovalProcessNode.getSubprocessbranch())) continue;
            return;
        }
        ArrayList<String> nodeCodes = new ArrayList<String>();
        nodeCodes.add(nodeCode);
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        if (customParam.containsKey("todoTaskHistorys")) {
            List todoTaskHistorys = (List)customParam.get("todoTaskHistorys");
            List taskDefineKeys = todoTaskHistorys.stream().map(map -> (String)map.get("TASKDEFINEKEY")).distinct().collect(Collectors.toList());
            nodeCodes.addAll(taskDefineKeys);
        }
        List<ProcessNodeDO> approvalProcessNodes = processNodeDOS.stream().filter(processNodeDO -> processNodeDO.getCompletetime() != null).collect(Collectors.toList());
        Collections.reverse(processNodeDOS);
        this.sendNotifyCreateUserMessage(workflowDTO, unApprovalProcessNodes, approvalProcessNodes, nodeCodes);
    }

    @Override
    public R retractProcess(WorkflowDTO workflowDTO) {
        return this.retractTask(workflowDTO);
    }

    @Override
    public void checkRetract(WorkflowDTO workflowDTO) {
        this.checkRetract(workflowDTO, null);
    }

    public VaWorkflowContext checkRetract(WorkflowDTO workflowDTO, List<ProcessNodeDO> unApprovalProcessNodeDOs) {
        JsonNode preNodeProperties;
        JsonNode jsonNode;
        String preNodeType;
        List unApprovalSubProcessNodeDOs;
        ArrayList<ProcessNodeDO> newUnApprovalNodes;
        String userId = ShiroUtil.getUser().getId();
        if (unApprovalProcessNodeDOs == null) {
            unApprovalProcessNodeDOs = new ArrayList<ProcessNodeDO>();
        }
        VaWorkflowContext context = new VaWorkflowContext();
        String processInstanceId = workflowDTO.getProcessInstanceId();
        ProcessDO processDO = this.getProcessDO(processInstanceId);
        if (processDO == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.processfinishedmsg"));
        }
        context.setProcessDO(processDO);
        Map currNodeProperties = this.workflowSevice.currNodeProperties(workflowDTO.getProcessInstanceId(), null);
        String nodeType = (String)currNodeProperties.get("nodeType");
        if ("AutoManualTask".equals(nodeType)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextautomanualtasknotretract"));
        }
        Map workflowNodeBehaviorMap = this.workflowSevice.getWorkflowNodeBehaviorMap();
        WorkflowNodeBehavior workflowNodeBehavior = null;
        boolean isManualTaskFlag = false;
        VaTransMessageDO extendTransMessageDO = null;
        if ("ManualTask".equals(nodeType)) {
            workflowNodeBehavior = (WorkflowNodeBehavior)workflowNodeBehaviorMap.get(currNodeProperties.get("stencilId"));
            if (workflowNodeBehavior.isCheckRetractMq(workflowDTO)) {
                isManualTaskFlag = true;
                extendTransMessageDO = this.getExtendRetractMsg(workflowDTO, currNodeProperties, workflowNodeBehavior);
            } else {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
            }
        }
        List<ProcessNodeDO> processNodeDOList = this.listProcessNode(processInstanceId);
        String submitNodeDefineId = (String)this.workflowSevice.getNextTaskDefinekeys(processInstanceId, null).get(0);
        this.workflowModelHelper.checkRetract(workflowDTO, processNodeDOList, submitNodeDefineId);
        context.setProcessNodeList(processNodeDOList);
        ProcessNodeDO processNode = null;
        BigDecimal countersignFlag = null;
        if (workflowDTO.getTaskId() != null) {
            List processNodeList = processNodeDOList.stream().filter(item -> item.getNodeid().equals(workflowDTO.getTaskId())).collect(Collectors.toList());
            for (ProcessNodeDO processNodeDO2 : processNodeList) {
                if (Objects.equals(processNodeDO2.getCompleteuserid(), userId)) {
                    processNode = processNodeDO2;
                    continue;
                }
                countersignFlag = processNodeDO2.getCountersignflag();
            }
        }
        if (processNode != null && new BigDecimal(1).equals(processNode.getApprovalflag())) {
            processNode.setCountersignflag(countersignFlag);
            String nodeCode = (String)currNodeProperties.get("activityId");
            if (!nodeCode.equals(processNode.getNodecode())) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.retractisnotallowed"));
            }
            for (int i = processNodeDOList.size() - 1; i >= 0; --i) {
                ProcessNodeDO processNodeDO2 = processNodeDOList.get(i);
                if (!processNodeDO2.getNodecode().equals(nodeCode)) continue;
                if (processNodeDO2.getNodeid().equals(processNode.getNodeid())) break;
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
            }
            context.setProcessNodeDO(processNode);
            return context;
        }
        List unApprovalSubProcessNodeDOList = processNodeDOList.stream().filter(e -> e.getCompletetime() == null && Objects.equals(e.getNodeid(), e.getSubprocessnodeid())).collect(Collectors.toList());
        if (!unApprovalSubProcessNodeDOList.isEmpty()) {
            ProcessNodeDO subProcessNodeDO = (ProcessNodeDO)unApprovalSubProcessNodeDOList.get(0);
            List approvaledSubProcessNodeDOList = processNodeDOList.stream().filter(e -> e.getCompletetime() != null && Objects.equals(subProcessNodeDO.getNodeid(), e.getSubprocessnodeid())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(approvaledSubProcessNodeDOList)) {
                JsonNode subProcessBranchStrategyNode = null;
                JsonNode subProcessNode = null;
                ArrayNode nodes = this.getNodes();
                for (JsonNode node2 : nodes) {
                    String nodeId = node2.get("resourceId").asText();
                    if (!subProcessNodeDO.getNodecode().equals(nodeId)) continue;
                    subProcessNode = node2;
                    break;
                }
                if (!ObjectUtils.isEmpty(subProcessNode) && !"None".equals(subProcessNode.get("properties").get("multiinstance_type").asText())) {
                    String subProcessBranchStrategyName;
                    subProcessBranchStrategyNode = subProcessNode.get("properties").get("subprocessbranchstrategy");
                    JsonNode currSubProcessBranchStrategy = subProcessBranchStrategyNode.get(0);
                    String branchStrategyModule = currSubProcessBranchStrategy.get("subProcessBranchStrategyModuleName").asText();
                    boolean retractFlag = this.workflowSubProcessBranchStrategyService.checkSubProcessRetract(branchStrategyModule, subProcessBranchStrategyName = currSubProcessBranchStrategy.get("subProcessBranchStrategyName").asText(), workflowDTO);
                    if (retractFlag) {
                        String retractMq = this.workflowSubProcessBranchStrategyService.getSubProcessRetractMq(branchStrategyModule, subProcessBranchStrategyName);
                        extendTransMessageDO = this.getSubProcessNodeRetractMsg(workflowDTO, currNodeProperties, retractMq);
                    } else {
                        throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
                    }
                }
            }
        }
        context.setExtendTransMessageStr(JSONUtil.toJSONString((Object)extendTransMessageDO));
        context.setSubmitNodeDefineId(submitNodeDefineId);
        List<Object> processNodeDOs = processNodeDOList.stream().filter(e -> e.getCompletetime() != null && e.getRejectstatus() == null).collect(Collectors.toList());
        for (ProcessNodeDO processNodeDO3 : processNodeDOList) {
            if (processNodeDO3.getCompletetime() != null) continue;
            unApprovalProcessNodeDOs.add(processNodeDO3);
        }
        if (processNodeDOs.size() == 1 && submitNodeDefineId.equals(((ProcessNodeDO)processNodeDOs.get(0)).getNodecode())) {
            List rejectNodes = processNodeDOList.stream().filter(e -> e.getRejectstatus() != null).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(rejectNodes)) {
                return context;
            }
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notallowedtofetch"));
        }
        HashMap<String, Object> customParam = new HashMap<String, Object>();
        SubProcessInfoDTO subProcessInfoDTO = null;
        List unApprovalPgwNodeDOs = processNodeDOList.stream().filter(e -> e.getCompletetime() == null && e.getPgwnodeid() != null).collect(Collectors.toList());
        ProcessNodeDO targetNode = null;
        if (!unApprovalPgwNodeDOs.isEmpty()) {
            String pgwBranch;
            if (StringUtils.hasText(workflowDTO.getTaskId())) {
                targetNode = processNodeDOList.stream().filter(processNodeDO -> processNodeDO.getNodeid().equals(workflowDTO.getTaskId())).findFirst().get();
            }
            boolean retractPgwFlag = false;
            if (StringUtils.hasText(targetNode.getPgwnodeid())) {
                List currBranchCompletedNodes;
                pgwBranch = targetNode.getPgwbranch();
                if ("\u81ea\u52a8\u540c\u610f".equals(targetNode.getCompletecomment()) && CollectionUtils.isEmpty(currBranchCompletedNodes = processNodeDOs.stream().filter(processNodeDO -> pgwBranch.equals(processNodeDO.getPgwbranch())).filter(node -> !"\u81ea\u52a8\u540c\u610f".equals(node.getCompletecomment()) || "\u81ea\u52a8\u540c\u610f".equals(node.getCompletecomment()) && !userId.equals(node.getCompleteuserid())).collect(Collectors.toList()))) {
                    retractPgwFlag = true;
                }
            } else {
                retractPgwFlag = true;
            }
            if (retractPgwFlag) {
                String pgwNodeId = ((ProcessNodeDO)unApprovalPgwNodeDOs.get(0)).getPgwnodeid();
                List pgwCompletedNodes = processNodeDOs.stream().filter(processNodeDO -> pgwNodeId.equals(processNodeDO.getPgwnodeid()) && (!userId.equals(processNodeDO.getCompleteuserid()) || userId.equals(processNodeDO.getCompleteuserid()) && !"\u81ea\u52a8\u540c\u610f".equals(processNodeDO.getCompletecomment()))).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(pgwCompletedNodes)) {
                    customParam.put("retractPgw", true);
                } else {
                    throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
                }
            }
            if (!retractPgwFlag) {
                pgwBranch = targetNode.getPgwbranch();
                Optional<ProcessNodeDO> first = unApprovalPgwNodeDOs.stream().filter(processNodeDO -> pgwBranch.equals(processNodeDO.getPgwbranch()) && !processNodeDO.getNodeid().equals(processNodeDO.getPgwnodeid())).findFirst();
                if (!first.isPresent()) {
                    customParam.put("delNoApprovalNodeId", targetNode.getNodeid());
                    customParam.put("transmitRetractPwgBranch", targetNode.getPgwbranch());
                    customParam.put("transmitPgwNodeId", targetNode.getPgwnodeid());
                    customParam.put("pgwBranchEndNodeRetract", true);
                } else {
                    ProcessNodeDO branchUnApprovalNode = first.get();
                    customParam.put("retractPgwNodeCode", branchUnApprovalNode.getNodecode());
                    customParam.put("transmitRetractPwgBranch", branchUnApprovalNode.getPgwbranch());
                    customParam.put("transmitPgwNodeId", branchUnApprovalNode.getPgwnodeid());
                    customParam.put("delNoApprovalNodeId", branchUnApprovalNode.getNodeid());
                    newUnApprovalNodes = new ArrayList<ProcessNodeDO>(unApprovalProcessNodeDOs);
                    unApprovalProcessNodeDOs.clear();
                    for (ProcessNodeDO nodeDO : newUnApprovalNodes) {
                        if (!pgwBranch.equals(nodeDO.getPgwbranch()) || nodeDO.getNodeid().equals(nodeDO.getPgwnodeid())) continue;
                        unApprovalProcessNodeDOs.add(nodeDO);
                    }
                }
                processNodeDOs = processNodeDOs.stream().filter(processNodeDO -> !StringUtils.hasText(processNodeDO.getPgwbranch()) || pgwBranch.equals(processNodeDO.getPgwbranch())).collect(Collectors.toList());
            }
        }
        if (!(unApprovalSubProcessNodeDOs = processNodeDOList.stream().filter(e -> e.getCompletetime() == null && e.getSubprocessnodeid() != null).collect(Collectors.toList())).isEmpty()) {
            List subProcessCompletedNodes = processNodeDOs.stream().filter(e -> e.getSubprocessnodeid() != null).collect(Collectors.toList());
            if (!subProcessCompletedNodes.isEmpty()) {
                String subProcessBranch;
                if (StringUtils.hasText(workflowDTO.getSubProcessBranch())) {
                    subProcessBranch = workflowDTO.getSubProcessBranch();
                } else {
                    ProcessNodeDO processNodeDO4 = subProcessCompletedNodes.stream().filter(node -> Objects.equals(workflowDTO.getTaskId(), node.getNodeid())).findFirst().orElse(null);
                    if (processNodeDO4 == null) {
                        throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
                    }
                    subProcessBranch = processNodeDO4.getSubprocessbranch();
                }
                Optional<ProcessNodeDO> first = unApprovalSubProcessNodeDOs.stream().filter(node -> subProcessBranch.equals(node.getSubprocessbranch()) && !node.getNodeid().equals(node.getSubprocessnodeid())).findFirst();
                if (!first.isPresent()) {
                    subProcessInfoDTO = this.handleSubBranchEndRetract(workflowDTO, processNodeDOList, customParam);
                } else {
                    ProcessNodeDO branchUnApprovalNode = first.get();
                    subProcessInfoDTO = new SubProcessInfoDTO();
                    subProcessInfoDTO.setSubProcessNodeId(branchUnApprovalNode.getSubprocessnodeid());
                    subProcessInfoDTO.setSubProcessBranch(branchUnApprovalNode.getSubprocessbranch());
                    customParam.put("retractSubProcessUnApprovalNodeCode", branchUnApprovalNode.getNodecode());
                    customParam.put("retractSubProcessUnApprovalNodeId", branchUnApprovalNode.getNodeid());
                    customParam.put("delNoApprovalNodeId", branchUnApprovalNode.getNodeid());
                    String processnodename = branchUnApprovalNode.getProcessnodename();
                    String[] split = processnodename.split("\u2014");
                    String string = split.length > 1 ? split[split.length - 1].trim() : "";
                    subProcessInfoDTO.setSubProcessBranchName(string);
                }
                processNodeDOs = processNodeDOs.stream().filter(processNodeDO -> !StringUtils.hasText(processNodeDO.getSubprocessbranch()) || subProcessBranch.equals(processNodeDO.getSubprocessbranch())).collect(Collectors.toList());
                newUnApprovalNodes = new ArrayList<ProcessNodeDO>(unApprovalProcessNodeDOs);
                unApprovalProcessNodeDOs.clear();
                for (ProcessNodeDO nodeDO : newUnApprovalNodes) {
                    if (!subProcessBranch.equals(nodeDO.getSubprocessbranch()) || nodeDO.getNodeid().equals(nodeDO.getSubprocessnodeid())) continue;
                    unApprovalProcessNodeDOs.add(nodeDO);
                }
            } else {
                customParam.put("retractSubProcess", true);
            }
        }
        if (CollectionUtils.isEmpty(unApprovalPgwNodeDOs) && CollectionUtils.isEmpty(unApprovalSubProcessNodeDOs) && StringUtils.hasText(workflowDTO.getTaskId())) {
            String taskId = workflowDTO.getTaskId();
            targetNode = processNodeDOList.stream().filter(processNodeDO -> processNodeDO.getNodeid().equals(taskId)).findFirst().orElse(null);
            if (targetNode == null) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundretractnode"));
            }
            if (StringUtils.hasText(targetNode.getPgwnodeid())) {
                processNodeDOs = this.handlePgwEndRetract(targetNode, processNodeDOList, customParam, processNodeDOs, taskId);
            } else if (StringUtils.hasText(targetNode.getSubprocessnodeid())) {
                List subProcessNodeList = processNodeDOList.stream().filter(node -> Objects.equals(node.getSubprocessnodeid(), node.getNodeid())).collect(Collectors.toList());
                if (subProcessNodeList.isEmpty()) {
                    throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundsubprocessnode"));
                }
                ProcessNodeDO subProcessNodeDO = (ProcessNodeDO)subProcessNodeList.get(subProcessNodeList.size() - 1);
                subProcessInfoDTO = new SubProcessInfoDTO();
                processNodeDOs = this.handleSubProcessEndRetract(subProcessInfoDTO, subProcessNodeDO, targetNode, customParam, processNodeDOs);
            }
        }
        Collections.reverse(processNodeDOs);
        ProcessNodeDO lastNode = null;
        String lastTaskDefineKey = null;
        ArrayList<String> taskDefineKeys = new ArrayList<String>();
        LinkedHashMap<String, List<String>> nodeCompleteUserMap = new LinkedHashMap<String, List<String>>();
        LinkedHashMap nodeMap = new LinkedHashMap();
        for (ProcessNodeDO processNodeDO3 : processNodeDOs) {
            String taskDefineKey = processNodeDO3.getNodecode();
            if (submitNodeDefineId.equals(taskDefineKey) && !StringUtils.hasText(processNodeDO3.getSubprocessnodeid())) continue;
            if (lastTaskDefineKey == null) {
                lastTaskDefineKey = taskDefineKey;
                lastNode = processNodeDO3;
            }
            if (taskDefineKey.equals(lastTaskDefineKey) && "\u5ba1\u6279\u9a73\u56de".equals(processNodeDO3.getCompleteresult())) break;
            if (!taskDefineKeys.contains(taskDefineKey)) {
                taskDefineKeys.add(taskDefineKey);
            }
            String completeUser = processNodeDO3.getCompleteuserid();
            if (nodeCompleteUserMap.containsKey(taskDefineKey)) {
                ((List)nodeCompleteUserMap.get(taskDefineKey)).add(completeUser);
                ((List)nodeMap.get(taskDefineKey)).add(processNodeDO3);
                continue;
            }
            ArrayList<String> completeUsers = new ArrayList<String>();
            completeUsers.add(completeUser);
            nodeCompleteUserMap.put(taskDefineKey, completeUsers);
            ArrayList<ProcessNodeDO> tempList = new ArrayList<ProcessNodeDO>();
            tempList.add(processNodeDO3);
            nodeMap.put(taskDefineKey, tempList);
        }
        String string = preNodeType = (jsonNode = (preNodeProperties = this.getNodeConfig(lastTaskDefineKey)).get("stencil").get("nodeType")) == null ? "" : jsonNode.asText();
        if (StringUtils.hasText(preNodeType) && "AutoManualTask".equals(preNodeType)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.autonodenotallowedretract"));
        }
        List completeUsers = (List)nodeCompleteUserMap.get(lastTaskDefineKey);
        if (CollectionUtils.isEmpty(completeUsers) || !completeUsers.contains(userId)) {
            int i = processNodeDOs.indexOf(lastNode);
            ProcessNodeDO node3 = (ProcessNodeDO)processNodeDOs.get(i + 1);
            if (StringUtils.hasText(node3.getSubprocessnodeid())) {
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.subprocessbranchend"));
            }
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.nextnodehasbeenapproval"));
        }
        String retractTaskDefinekey = this.getRetractTaskDefinekey(nodeCompleteUserMap, lastTaskDefineKey, taskDefineKeys, processNodeDOs);
        context.setRetractTaskDefinekey(retractTaskDefinekey);
        if ("end".equals(retractTaskDefinekey)) {
            return context;
        }
        int index = taskDefineKeys.indexOf(retractTaskDefinekey);
        Map<String, Object> currentTask = this.getTodoTask(processInstanceId, retractTaskDefinekey, workflowDTO.getTraceId());
        completeUsers = (List)nodeCompleteUserMap.get(retractTaskDefinekey);
        List completeNodes = (List)nodeMap.get(retractTaskDefinekey);
        ProcessNodeDO processNodeDO6 = (ProcessNodeDO)completeNodes.stream().filter(e -> userId.equals(e.getCompleteuserid())).collect(Collectors.toList()).get(0);
        if (customParam.containsKey("pgwEndRetract") && processNodeDO6.getPgwnodeid() == null) {
            customParam.remove("pgwEndRetract");
            customParam.remove("pgwNodeCode");
            customParam.remove("transmitPgwNodeId");
            customParam.remove("transmitRetractPwgBranch");
        }
        String delegateUser = processNodeDO6.getDelegateuser();
        BigDecimal completeusertype = processNodeDO6.getCompleteusertype();
        boolean isMulti = processNodeDO6.getCountersignflag().equals(BigDecimal.ONE);
        String approvalUser = userId;
        HashMap<String, Object> currentRetractUserMap = new HashMap<String, Object>();
        currentRetractUserMap.put("taskType", 1);
        currentRetractUserMap.put("userId", userId);
        boolean isPlusApprovalRetract = false;
        if (BigDecimal.ONE.equals(completeusertype)) {
            isPlusApprovalRetract = true;
            currentRetractUserMap.put("taskType", 2);
        } else if (BigDecimal.valueOf(2L).equals(completeusertype)) {
            approvalUser = delegateUser;
            currentRetractUserMap.put("userId", delegateUser);
        } else if (BigDecimal.valueOf(3L).equals(completeusertype)) {
            isPlusApprovalRetract = true;
            approvalUser = delegateUser;
            currentRetractUserMap.put("taskType", 2);
            currentRetractUserMap.put("userId", delegateUser);
        }
        List unapprovedProcessNodes = processNodeDOList.stream().filter(e -> e.getNodecode().equals(retractTaskDefinekey) && e.getCompletetime() == null && new BigDecimal("1").equals(e.getCountersignflag())).collect(Collectors.toList());
        boolean isCurrentNodeRetract = !unapprovedProcessNodes.isEmpty();
        String plusApprovalVal = null;
        boolean plusApprovalFlag = true;
        OptionItemDTO optionItemDTO = new OptionItemDTO();
        optionItemDTO.setName("WF1004");
        List options = this.workflowOptionService.list(optionItemDTO);
        if (options != null && !options.isEmpty()) {
            plusApprovalVal = ((OptionItemVO)options.get(0)).getVal();
            plusApprovalFlag = ((OptionItemVO)options.get(0)).getVal().equals("1");
        }
        ProcessNodeDO retractNode = null;
        for (ProcessNodeDO processNodeDO4 : processNodeDOs) {
            if (!retractTaskDefinekey.equals(processNodeDO4.getNodecode())) continue;
            retractNode = processNodeDO4;
            break;
        }
        PlusApprovalInfoDTO plusApprovalInfoDTO = new PlusApprovalInfoDTO();
        plusApprovalInfoDTO.setProcessid(processInstanceId);
        plusApprovalInfoDTO.setNodecode(retractTaskDefinekey);
        BigDecimal bigDecimal = retractNode.getCountersignflag();
        plusApprovalInfoDTO.setCountersignflag(retractNode.getCountersignflag());
        if (new BigDecimal(1).equals(bigDecimal)) {
            plusApprovalInfoDTO.setNodeid(retractNode.getNodeid());
        }
        List<PlusApprovalInfoDO> plusApprovalInfos = Optional.ofNullable(this.workflowPlusApprovalService.list(plusApprovalInfoDTO)).orElse(Collections.emptyList());
        for (PlusApprovalInfoDO plusApprovalInfo : plusApprovalInfos) {
            Integer plusSignApprovalFlag = plusApprovalInfo.getPlusSignApprovalFlag();
            if (!Objects.isNull(plusSignApprovalFlag)) continue;
            plusApprovalInfo.setPlusSignApprovalFlag(Integer.valueOf(plusApprovalVal));
        }
        List<String> plusApprovalUsers = null;
        if (isCurrentNodeRetract && !isPlusApprovalRetract && !plusApprovalFlag) {
            String userTemp = approvalUser;
            List<String> plusApprovalUserList = plusApprovalInfos.stream().filter(e -> e.getUsername().equals(userTemp)).map(PlusApprovalInfoDO::getApprovaluser).collect(Collectors.toList());
            this.calcPlusApprovalUser(plusApprovalUserList, completeNodes);
            plusApprovalUsers = plusApprovalUserList;
        }
        if (isPlusApprovalRetract && !isMulti) {
            plusApprovalUsers = this.getCounterSingerReactUserList(processNodeDOs, plusApprovalInfos, plusApprovalFlag);
        }
        if (!isCurrentNodeRetract && !isPlusApprovalRetract) {
            List<String> plusApprovalUserList = null;
            if (isMulti) {
                String userTemp = approvalUser;
                plusApprovalUserList = plusApprovalInfos.stream().filter(e -> userTemp.equals(e.getUsername())).map(PlusApprovalInfoDO::getApprovaluser).collect(Collectors.toList());
            } else {
                plusApprovalUserList = plusApprovalInfos.stream().map(PlusApprovalInfoDO::getApprovaluser).collect(Collectors.toList());
            }
            this.calcPlusApprovalUser(plusApprovalUserList, completeNodes);
            plusApprovalUsers = plusApprovalUserList;
        }
        List<String> retractNodes = this.getRetractNodes(submitNodeDefineId, processNodeDOs, userId, retractTaskDefinekey);
        if (retractTaskDefinekey.equals(submitNodeDefineId) && !ObjectUtils.isEmpty(currentTask.get("SUBPROCESSBRANCH"))) {
            customParam.put("subProcessRetractSubmit", true);
        }
        List<Map<String, Object>> todoTransferMapList = this.workflowModelHelper.getTodoTransferInfo(retractTaskDefinekey, processInstanceId, workflowDTO.getBizCode(), workflowDTO.getTaskId());
        customParam.put("workflowModel", this);
        customParam.put("retractTaskDefinekey", retractTaskDefinekey);
        customParam.put("completeUsers", completeUsers);
        customParam.put("TASKDEFINEKEYS", new ArrayList(taskDefineKeys.subList(0, index + 1)));
        customParam.put("retractNodes", retractNodes);
        customParam.put("processNodeDO", processNodeDO6);
        customParam.put("isPlusApprovalRetract", isPlusApprovalRetract);
        customParam.put("isTransferApprovalRetract", completeusertype == null && !todoTransferMapList.isEmpty());
        customParam.put("isCurrentNodeRetract", isCurrentNodeRetract);
        customParam.put("currentRetractUserMap", currentRetractUserMap);
        customParam.put("plusApprovalUsers", plusApprovalUsers);
        customParam.put("plusApprovalFlag", plusApprovalFlag);
        customParam.put("transferList", todoTransferMapList);
        context.setDefineNodes(this.getNodes());
        context.setSubmitNodeDefineId(submitNodeDefineId);
        context.setOperation(WorkflowOperation.RETRACT);
        context.setWorkflowDTO(workflowDTO);
        context.setProcessDO(processDO);
        context.setCurrentTask(currentTask);
        context.setCustomParam(customParam);
        context.setTransMessageId(workflowDTO.getTransMessageId());
        context.setSubProcessInfoDTO(subProcessInfoDTO);
        context.setPlusApprovalInfoDoList(plusApprovalInfos);
        Map<String, Object> workflowVariables = this.getWorkflowVariables(processDO, processDO.getBizmodule(), processDO.getBiztype());
        this.calProcessParam(workflowVariables);
        context.setVariables(workflowVariables);
        if (isManualTaskFlag) {
            VaTransMessageDO vaTransMessageDO = this.getExtendRetractMsg(workflowDTO, currNodeProperties, workflowNodeBehavior);
            customParam.put("vaExtendRetractsMsg", vaTransMessageDO);
        }
        return context;
    }

    private List<ProcessNodeDO> handlePgwEndRetract(ProcessNodeDO targetNode, List<ProcessNodeDO> processNodeDOList, Map<String, Object> customParam, List<ProcessNodeDO> processNodeDOs, String taskId) {
        ProcessNodeDO finalTargetNode = targetNode;
        ProcessNodeDO pgwNode = processNodeDOList.stream().filter(processNodeDO -> processNodeDO.getNodeid().equals(finalTargetNode.getPgwnodeid())).findFirst().get();
        customParam.put("transmitRetractPwgBranch", targetNode.getPgwbranch());
        customParam.put("transmitPgwNodeId", targetNode.getPgwnodeid());
        customParam.put("pgwNodeCode", pgwNode.getNodecode());
        customParam.put("pgwEndRetract", true);
        boolean findJoinPgwNodeFlag = false;
        for (ProcessNodeDO processNodeDO2 : processNodeDOs) {
            if (findJoinPgwNodeFlag && !StringUtils.hasText(processNodeDO2.getPgwnodeid())) {
                customParam.put("pgwJoinNodeId", processNodeDO2.getNodeid());
                break;
            }
            if (!taskId.equals(processNodeDO2.getNodeid())) continue;
            findJoinPgwNodeFlag = true;
        }
        ProcessNodeDO finalTargetNodeTemp = targetNode;
        processNodeDOs = processNodeDOs.stream().filter(processNodeDO -> (!StringUtils.hasText(processNodeDO.getPgwbranch()) || finalTargetNodeTemp.getPgwbranch().equals(processNodeDO.getPgwbranch())) && StringUtils.hasText(processNodeDO.getCompleteuserid())).collect(Collectors.toList());
        return processNodeDOs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R retractTask(WorkflowDTO workflowDTO) {
        List rejectNodes;
        UserLoginDTO user = ShiroUtil.getUser();
        String userId = user.getId();
        String processInstanceId = workflowDTO.getProcessInstanceId();
        workflowDTO.setApprovalResult(Integer.valueOf(3));
        ArrayList<ProcessNodeDO> unApprovalProcessNodeDOs = new ArrayList<ProcessNodeDO>();
        VaWorkflowContext context = null;
        try {
            context = this.checkRetract(workflowDTO, unApprovalProcessNodeDOs);
        }
        catch (Exception e2) {
            log.error("\u53d6\u56de\u524d\u6821\u9a8c\u4e0d\u901a\u8fc7:{}", (Object)e2.getMessage(), (Object)e2);
            throw new WorkflowException(e2.getMessage(), (Throwable)e2);
        }
        ProcessDO processDO = context.getProcessDO();
        workflowDTO.setUniqueCode(processDO.getDefinekey());
        workflowDTO.setProcessDefineVersion(Long.valueOf(processDO.getDefineversion().longValue()));
        List processNodeDOList = context.getProcessNodeList();
        ProcessNodeDO processNode = context.getProcessNodeDO();
        if (processNode != null && new BigDecimal(1).equals(processNode.getApprovalflag())) {
            try {
                Object todos;
                VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
                vaWorkflowContext.setWorkflowDTO(workflowDTO);
                HashMap customParam = new HashMap();
                vaWorkflowContext.setCustomParam(customParam);
                VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                this.workflowPlusApprovalConsultService.retract(processNode);
                Object transMessageId = customParam.get("transMessageId");
                R result = new R();
                if (transMessageId != null) {
                    VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
                    vaTransMessageDO.setId(transMessageId.toString());
                    try {
                        result = this.vaTransMessageService.triggerTrans(vaTransMessageDO);
                    }
                    catch (Exception e3) {
                        log.error("\u52a0\u7b7e\u4e0d\u53c2\u4e0e\u5ba1\u6279\u4eba\u53d6\u56de\u89e6\u53d1\u540e\u7eed\u7f16\u6392\u6267\u884c\u5931\u8d25", e3);
                        result = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.retract.failed"));
                    }
                }
                if (customParam.containsKey("todoTasks") && !(todos = (List)customParam.get("todoTasks")).isEmpty()) {
                    this.sendMessage((List<Map<String, Object>>)todos, user.getTenantName(), workflowDTO);
                }
                todos = result;
                return todos;
            }
            finally {
                VaContext.removeVaWorkflowContext();
            }
        }
        List processNodeDOs = processNodeDOList.stream().filter(e -> e.getCompletetime() != null && e.getRejectstatus() == null).collect(Collectors.toList());
        String submitNodeDefineId = context.getSubmitNodeDefineId();
        VaTransMessageDO extendTransMessageDO = (VaTransMessageDO)JSONUtil.parseObject((String)context.getExtendTransMessageStr(), VaTransMessageDO.class);
        if (processNodeDOs.size() == 1 && submitNodeDefineId.equals(((ProcessNodeDO)processNodeDOs.get(0)).getNodecode()) && CollectionUtils.isEmpty(rejectNodes = processNodeDOList.stream().filter(e -> e.getRejectstatus() != null).collect(Collectors.toList()))) {
            this.executeRetractPredecessorAutoTask(unApprovalProcessNodeDOs, workflowDTO);
            return this.excuteRetractProcess(workflowDTO, processDO, userId, submitNodeDefineId, extendTransMessageDO);
        }
        String retractTaskDefinekey = context.getRetractTaskDefinekey();
        if ("end".equals(retractTaskDefinekey)) {
            this.executeRetractPredecessorAutoTask(unApprovalProcessNodeDOs, workflowDTO);
            return this.excuteRetractProcess(workflowDTO, processDO, userId, submitNodeDefineId, extendTransMessageDO);
        }
        try {
            List todos;
            VaContext.setVaWorkflowContext((VaWorkflowContext)context);
            Map customParam = context.getCustomParam();
            boolean isCurrentNodeRetract = (Boolean)customParam.get("isCurrentNodeRetract");
            if (!isCurrentNodeRetract) {
                this.executeRetractPredecessorAutoTask(unApprovalProcessNodeDOs, workflowDTO);
            }
            this.workflowSevice.retractTask(processInstanceId, retractTaskDefinekey);
            Object transMessageId = customParam.get("transMessageId");
            R result = new R();
            if (!ObjectUtils.isEmpty(transMessageId)) {
                try {
                    VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
                    vaTransMessageDO.setId(transMessageId.toString());
                    result = this.vaTransMessageService.triggerTrans(vaTransMessageDO);
                }
                catch (Exception e4) {
                    log.error("{}\u53d6\u56de\u89e6\u53d1\u540e\u7eed\u7f16\u6392\u6267\u884c\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e4);
                    result = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.retract.failed"));
                }
            } else {
                result.put("data", (Object)context.getOutput());
            }
            if (customParam.containsKey("todoTasks") && !(todos = (List)customParam.get("todoTasks")).isEmpty()) {
                this.sendMessage(todos, user.getTenantName(), workflowDTO);
            }
            this.retractTaskExecuteAutoTask(retractTaskDefinekey, workflowDTO);
            if (result.getCode() == 0 && !result.containsKey((Object)"taskId") && customParam.get("taskId") != null) {
                result.put("taskId", customParam.get("taskId"));
            }
            R r = result;
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    private SubProcessInfoDTO handleSubBranchEndRetract(WorkflowDTO workflowDTO, List<ProcessNodeDO> processNodeDOList, Map<String, Object> customParam) {
        ProcessNodeDO targetNode = processNodeDOList.stream().filter(processNodeDO -> processNodeDO.getNodeid().equals(workflowDTO.getTaskId())).findFirst().orElse(null);
        if (targetNode == null) {
            throw new ActivitiException(VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundretractnode"));
        }
        List subProcessNodeList = processNodeDOList.stream().filter(node -> Objects.equals(node.getSubprocessnodeid(), node.getNodeid())).collect(Collectors.toList());
        if (subProcessNodeList.isEmpty()) {
            throw new ActivitiException(VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundsubprocessnode"));
        }
        ProcessNodeDO subProcessNodeDO = (ProcessNodeDO)subProcessNodeList.get(subProcessNodeList.size() - 1);
        SubProcessInfoDTO subProcessInfoDTO = new SubProcessInfoDTO();
        subProcessInfoDTO.setSubProcessNodeId(subProcessNodeDO.getSubprocessnodeid());
        subProcessInfoDTO.setSubProcessBranch(targetNode.getSubprocessbranch());
        String processnodename = targetNode.getProcessnodename();
        String[] split = processnodename.split("\u2014");
        String subProcessBranchName = split.length > 1 ? split[split.length - 1].trim() : "";
        subProcessInfoDTO.setSubProcessBranchName(subProcessBranchName);
        String subNodecode = subProcessNodeDO.getNodecode();
        ArrayNode nodes = this.getNodes();
        for (JsonNode node2 : nodes) {
            String nodeId = node2.get("resourceId").asText();
            if (!subNodecode.equals(nodeId)) continue;
            JsonNode jsonNode = node2.get("properties").get("subprocessbranchstrategy");
            List subProcessBranchStrategy = JSONUtil.parseMapArray((String)JSONUtil.toJSONString((Object)jsonNode));
            subProcessInfoDTO.setBranchStrategy(subProcessBranchStrategy);
            break;
        }
        customParam.put("subProcessNodeCode", subNodecode);
        customParam.put("delNoApprovalNodeId", targetNode.getNodeid());
        customParam.put("transmitRetractSubBranch", targetNode.getSubprocessbranch());
        customParam.put("transmitSubNodeId", targetNode.getSubprocessnodeid());
        customParam.put("subBranchEndNodeRetract", true);
        return subProcessInfoDTO;
    }

    private List<ProcessNodeDO> handleSubProcessEndRetract(SubProcessInfoDTO subProcessInfoDTO, ProcessNodeDO subProcessNodeDO, ProcessNodeDO targetNode, Map<String, Object> customParam, List<ProcessNodeDO> processNodeDOs) {
        subProcessInfoDTO.setSubProcessNodeId(subProcessNodeDO.getSubprocessnodeid());
        subProcessInfoDTO.setSubProcessBranch(targetNode.getSubprocessbranch());
        String processnodename = targetNode.getProcessnodename();
        String[] split = processnodename.split("\u2014");
        String subProcessBranchName = split.length > 1 ? split[split.length - 1].trim() : "";
        subProcessInfoDTO.setSubProcessBranchName(subProcessBranchName);
        String subNodecode = subProcessNodeDO.getNodecode();
        ArrayNode nodes = this.getNodes();
        for (JsonNode node : nodes) {
            String nodeId = node.get("resourceId").asText();
            if (!subNodecode.equals(nodeId)) continue;
            JsonNode jsonNode = node.get("properties").get("subprocessbranchstrategy");
            List subProcessBranchStrategy = JSONUtil.parseMapArray((String)JSONUtil.toJSONString((Object)jsonNode));
            subProcessInfoDTO.setBranchStrategy(subProcessBranchStrategy);
            break;
        }
        boolean multiInstanceFlag = !CollectionUtils.isEmpty(subProcessInfoDTO.getBranchStrategyList());
        customParam.put("multiInstanceFlag", multiInstanceFlag);
        customParam.put("subProcessNodeCode", subNodecode);
        customParam.put("transmitRetractSubBranch", targetNode.getSubprocessbranch());
        customParam.put("transmitSubNodeId", targetNode.getSubprocessnodeid());
        customParam.put("subProcessEndRetract", true);
        processNodeDOs = processNodeDOs.stream().filter(processNodeDO -> (!StringUtils.hasText(processNodeDO.getSubprocessbranch()) || targetNode.getSubprocessbranch().equals(processNodeDO.getSubprocessbranch())) && StringUtils.hasText(processNodeDO.getCompleteuserid())).collect(Collectors.toList());
        return processNodeDOs;
    }

    @Override
    public R retractRejectProcess(WorkflowDTO workflowDTO) {
        R r = this.checkRetractRejectR(workflowDTO, this.getNodes());
        if (r.getCode() != 0) {
            return r;
        }
        return this.retractRejectTask(workflowDTO);
    }

    private R checkRetractRejectR(WorkflowDTO workflowDTO, ArrayNode nodes) {
        try {
            this.workflowModelHelper.checkRetractReject(workflowDTO, nodes);
        }
        catch (Exception e) {
            log.error("\u9a73\u56de\u540e\u53d6\u56de\u6821\u9a8c\u4e0d\u901a\u8fc7\uff1a{}", (Object)e.getMessage(), (Object)e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public R retractRejectTask(WorkflowDTO workflowDTO) {
        UserLoginDTO user = ShiroUtil.getUser();
        String currUserId = user.getId();
        log.info("\u7528\u6237{}\uff0c\u4e1a\u52a1{}\u8fdb\u5165\u6d41\u7a0b\u9a73\u56de\u540e\u53d6\u56de\u903b\u8f91", (Object)currUserId, (Object)workflowDTO.getBizCode());
        String processInstanceId = workflowDTO.getProcessInstanceId();
        String taskId = workflowDTO.getTaskId();
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processInstanceId);
        processNodeDTO.setSearchIgnore(true);
        List processNodes = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        ProcessNodeDO retractNode = processNodes.stream().filter(node -> Objects.equals(taskId, node.getNodeid())).filter(node -> Objects.equals(currUserId, node.getCompleteuserid())).findFirst().orElse(null);
        if (retractNode == null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundretractnode"));
        }
        ArrayNode nodes = this.getNodes();
        workflowDTO.setApprovalResult(Integer.valueOf(3));
        HashMap<String, Object> customParam = new HashMap<String, Object>();
        customParam.put("RetractType", RetractTypeEnum.RETRACT_REJECT);
        RetractRejectInfo retractRejectInfo = new RetractRejectInfo();
        String subProcessNodeId = retractNode.getSubprocessnodeid();
        String pgwNodeId = retractNode.getPgwnodeid();
        ProcessDO processDO = (ProcessDO)workflowDTO.getExtInfo().remove("processDO");
        Map<String, Object> currentTask = this.getTodoTask(processInstanceId, retractNode.getNodecode(), workflowDTO.getTraceId());
        ProcessHistoryDO historyDO = null;
        String submitNodeDefineId = VaWorkflowNodeUtils.getSubmitNodeDefineId(nodes);
        if (submitNodeDefineId == null) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notfoundsubmitnode"));
        }
        retractRejectInfo.setSubmitNodeCode(submitNodeDefineId);
        if (processDO == null) {
            String processDefinitionKey = workflowDTO.getUniqueCode();
            Long processDefineVersion = workflowDTO.getProcessDefineVersion();
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("metaVersion", (Object)processDefineVersion);
            tenantDO.addExtInfo("workflowDefineKey", (Object)processDefinitionKey);
            Integer workflowDefineVer = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
            retractRejectInfo.setWorkflowDefineVer(workflowDefineVer);
            historyDO = (ProcessHistoryDO)workflowDTO.getExtInfo().remove("processHistoryDO");
            retractRejectInfo.setStartUser(historyDO.getStartuser());
            retractRejectInfo.setStartUnitCode(historyDO.getStartunitcode());
            if (StringUtils.hasText(pgwNodeId)) {
                this.workflowModelHelper.handlePgwRetractRejectSubmit(processNodes, retractNode, retractRejectInfo, nodes);
            } else {
                this.workflowModelHelper.handleCommonRetractRejectSubmit(retractNode, processNodes, retractRejectInfo);
            }
        } else {
            retractRejectInfo.setChangedBizState(BizState.AUDITING);
            ArrayList<ProcessNodeDO> completeNodes = new ArrayList<ProcessNodeDO>();
            ArrayList<ProcessNodeDO> unCompleteNodes = new ArrayList<ProcessNodeDO>();
            for (ProcessNodeDO processNode : processNodes) {
                if (BigDecimal.ONE.equals(processNode.getIgnoreflag()) || BigDecimal.ONE.equals(processNode.getHiddenflag())) continue;
                Date completetime = processNode.getCompletetime();
                if (completetime == null) {
                    unCompleteNodes.add(processNode);
                    continue;
                }
                completeNodes.add(processNode);
            }
            ProcessNodeDO existsUncompleteSubNode = null;
            ProcessNodeDO existsUncompletePgwNode = null;
            for (ProcessNodeDO unCompleteNode : unCompleteNodes) {
                if (StringUtils.hasText(unCompleteNode.getPgwnodeid())) {
                    existsUncompletePgwNode = unCompleteNode;
                    continue;
                }
                if (!StringUtils.hasText(unCompleteNode.getSubprocessnodeid())) continue;
                existsUncompleteSubNode = unCompleteNode;
            }
            if (existsUncompleteSubNode != null) {
                if (!StringUtils.hasText(subProcessNodeId)) throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notallowmasterbranchretractsubbranch"));
                this.workflowModelHelper.handleSubRetractRejectSub(retractNode, processNodes, completeNodes, retractRejectInfo);
            } else if (existsUncompletePgwNode != null) {
                if (StringUtils.hasText(pgwNodeId)) {
                    this.workflowModelHelper.handlePgwRetractRejectPgw(retractNode, processNodes, completeNodes, retractRejectInfo);
                } else {
                    this.workflowModelHelper.handleCommonRetractRejectPgw(retractNode, unCompleteNodes, processNodes, completeNodes, retractRejectInfo);
                }
            } else if (StringUtils.hasText(pgwNodeId)) {
                this.workflowModelHelper.handlePgwRetractRejectCommon(processNodes, retractNode, completeNodes, retractRejectInfo, nodes);
            } else {
                this.workflowModelHelper.handleCommonRetractRejectCommon(unCompleteNodes, retractNode, processNodes, completeNodes, retractRejectInfo);
            }
        }
        log.info("\u9a73\u56de\u53d6\u56de\u7c7b\u578b{}", (Object)retractRejectInfo.getRetractRejectType());
        try {
            List todos;
            Map<String, Object> workflowVariables;
            customParam.put("workflowModel", this);
            retractRejectInfo.setRetractNode(retractNode);
            String newTaskId = UUID.randomUUID().toString();
            AbstractMap.SimpleEntry<String, String> simpleEntry = new AbstractMap.SimpleEntry<String, String>(retractNode.getNodeid(), newTaskId);
            retractRejectInfo.setRetractNewTaskIdMap(simpleEntry);
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setRetractRejectInfo(retractRejectInfo);
            vaWorkflowContext.setDefineNodes(nodes);
            vaWorkflowContext.setSubmitNodeDefineId(submitNodeDefineId);
            vaWorkflowContext.setOperation(WorkflowOperation.RETRACT);
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setProcessDO(processDO);
            vaWorkflowContext.setProcessHistoryDO(historyDO);
            vaWorkflowContext.setCurrentTask(currentTask);
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setTransMessageId(workflowDTO.getTransMessageId());
            if (processDO == null) {
                ProcessDO newProcessDO = new ProcessDO();
                newProcessDO.setBizcode(historyDO.getBizcode());
                newProcessDO.setDefinekey(historyDO.getDefinekey());
                newProcessDO.setDefineversion(historyDO.getDefineversion());
                workflowVariables = this.getWorkflowVariables(newProcessDO, historyDO.getBizmodule(), historyDO.getBiztype());
            } else {
                workflowVariables = this.getWorkflowVariables(processDO, processDO.getBizmodule(), processDO.getBiztype());
            }
            this.calProcessParam(workflowVariables);
            vaWorkflowContext.setVariables(workflowVariables);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            this.workflowSevice.retractRejectTask(processInstanceId);
            Object transMessageId = customParam.get("transMessageId");
            R result = new R();
            if (!ObjectUtils.isEmpty(transMessageId)) {
                try {
                    VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
                    vaTransMessageDO.setId(transMessageId.toString());
                    result = this.vaTransMessageService.triggerTrans(vaTransMessageDO);
                }
                catch (Exception e) {
                    log.error("{}\u53d6\u56de\u89e6\u53d1\u540e\u7eed\u7f16\u6392\u6267\u884c\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e);
                    result = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.retract.failed"));
                }
            } else {
                result.put("data", (Object)vaWorkflowContext.getOutput());
            }
            if (customParam.containsKey("todoTasks") && !(todos = (List)customParam.get("todoTasks")).isEmpty()) {
                this.sendMessage(todos, user.getTenantName(), workflowDTO);
            }
            result.put("taskId", (Object)newTaskId);
            R r = result;
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    private void executeRetractPredecessorAutoTask(List<ProcessNodeDO> unApprovalProcessNodeDOs, WorkflowDTO workflowDTO) {
        Set nodeCodeSet = unApprovalProcessNodeDOs.stream().map(ProcessNodeDO::getNodecode).collect(Collectors.toSet());
        UserLoginDTO loginUser = VaWorkflowUtils.getLoginUserWithToken();
        Locale defaultLocal = LocaleContextHolder.getLocale();
        for (String nodeCode : nodeCodeSet) {
            ArrayNode autoTaskArrayNode = this.workflowInfoService.getNodePredecessorAutoTasks(nodeCode, workflowDTO);
            if (autoTaskArrayNode == null) {
                return;
            }
            if (ObjectUtils.isEmpty(workflowDTO.getWorkflowVariables())) {
                Map<String, Object> workflowVariables = this.workflowParamService.loadWorkflowVariables(workflowDTO.getBizCode(), workflowDTO.getBizDefine(), new BigDecimal(workflowDTO.getProcessDefineVersion()), workflowDTO.getBizType(), workflowDTO.getUniqueCode());
                workflowDTO.setWorkflowVariables(workflowVariables);
            }
            ThreadPoolConst.AUTOTASK_THREADPOOL.execute(() -> {
                try {
                    ShiroUtil.unbindUser();
                    ShiroUtil.bindUser((UserLoginDTO)loginUser);
                    LocaleContextHolder.setLocale(defaultLocal);
                    this.executeRetractAutoTasks(autoTaskArrayNode, workflowDTO);
                }
                catch (Exception e) {
                    log.error("\u81ea\u52a8\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
                }
                finally {
                    ShiroUtil.unbindUser();
                }
            });
        }
    }

    private void executeRetractAutoTasks(ArrayNode autoTaskArrayNode, WorkflowDTO workflowDTO) {
        Map variables = workflowDTO.getWorkflowVariables();
        WorkflowContext workflowContext = null;
        UserLoginDTO currentUser = ShiroUtil.getUser();
        for (JsonNode autoTaskNode : autoTaskArrayNode) {
            JsonNode autoTaskParams;
            String autoTaskName;
            String autoTaskModuleName;
            block13: {
                autoTaskModuleName = autoTaskNode.get("autoTaskModuleName").asText();
                autoTaskName = autoTaskNode.get("autoTaskName").asText();
                autoTaskParams = autoTaskNode.get("items");
                try {
                    if (autoTaskParams.isNull()) break block13;
                    for (JsonNode autoTaskParamNode : autoTaskParams) {
                        Iterator iterator = autoTaskParamNode.fields();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry)iterator.next();
                            JsonNode paramValue = (JsonNode)entry.getValue();
                            JsonNode paramType = paramValue.get("paramType");
                            if (null == paramType || paramType.isNull() || !"\"formula\"".equals(paramType.toString())) continue;
                            JsonNode formula = paramValue.get("value");
                            String formulaType = formula.findValue("formulaType").asText();
                            String expression = formula.findValue("expression").asText();
                            if (!StringUtils.hasText(expression)) continue;
                            FormulaImpl formulaImpl = VaWorkflowUtils.getFormulaImpl(expression, FormulaType.valueOf((String)formulaType));
                            if (workflowContext == null) {
                                workflowContext = this.createWorkflowContext(variables);
                            }
                            Object result = WorkflowFormulaExecute.evaluate(workflowContext, formulaImpl);
                            ObjectNode objectNode = (ObjectNode)paramValue;
                            if (result instanceof String) {
                                objectNode.put("realValue", (String)result);
                            }
                            if (!(result instanceof ArrayData)) continue;
                            List resultList = ((ArrayData)result).toList();
                            ObjectMapper mapper = new ObjectMapper();
                            String json = mapper.writeValueAsString((Object)resultList);
                            objectNode.set("realValue", mapper.readTree(json));
                        }
                    }
                }
                catch (Exception e) {
                    log.error("\u5904\u7406\u81ea\u52a8\u4efb\u52a1-{} \u53c2\u6570\u5931\u8d25\uff1a", (Object)autoTaskName, (Object)e);
                    continue;
                }
            }
            log.info("\u6267\u884c\u81ea\u52a8\u4efb\u52a1\uff1a{}", (Object)autoTaskName);
            TenantDO tenantDO = new TenantDO();
            tenantDO.setTraceId(workflowDTO.getTraceId());
            Map extInfo = Optional.ofNullable(workflowDTO.getExtInfo()).orElse(Collections.emptyMap());
            tenantDO.addExtInfo("processNodeId", extInfo.get("processNodeId"));
            tenantDO.addExtInfo("processNodeName", extInfo.get("processNodeName"));
            tenantDO.addExtInfo("bizCode", (Object)workflowDTO.getBizCode());
            tenantDO.addExtInfo("bizType", (Object)workflowDTO.getBizDefine());
            tenantDO.addExtInfo("autoTaskName", (Object)autoTaskName);
            tenantDO.addExtInfo("autoTaskModuleName", (Object)autoTaskModuleName);
            tenantDO.addExtInfo("autoTaskParam", (Object)autoTaskParams);
            tenantDO.addExtInfo("todoParam", extInfo);
            tenantDO.addExtInfo("variables", (Object)variables);
            tenantDO.addExtInfo("processStatus", workflowDTO.getExtInfo("processStatus"));
            tenantDO.addExtInfo("subProcessBranch", (Object)workflowDTO.getSubProcessBranch());
            if ("general".equalsIgnoreCase(autoTaskModuleName)) {
                List generalAutoTaskList = this.autoTaskManager.getAutoTaskList(autoTaskModuleName);
                for (AutoTask autoTask : generalAutoTaskList) {
                    if (!autoTaskName.equalsIgnoreCase(autoTask.getName())) continue;
                    if (!autoTask.canRetract().booleanValue()) break;
                    autoTask.retrack((Object)tenantDO);
                    if (ShiroUtil.getUser() == null) {
                        ShiroUtil.bindUser((UserLoginDTO)currentUser);
                        break;
                    }
                    UserLoginDTO user = ShiroUtil.getUser();
                    if (currentUser.getId().equals(user.getId())) break;
                    ShiroUtil.unbindUser();
                    ShiroUtil.bindUser((UserLoginDTO)currentUser);
                    break;
                }
            }
            List<AutoTaskModule> autoTaskModules = this.autoTaskModuleConfig.getModules();
            for (AutoTaskModule autoTaskModule : autoTaskModules) {
                if (!autoTaskModuleName.equalsIgnoreCase(autoTaskModule.getName())) continue;
                AutoTaskClient autoTaskClient = (AutoTaskClient)FeignUtil.getDynamicClient(AutoTaskClient.class, (String)autoTaskModule.getAppName(), (String)autoTaskModule.getAppPath());
                log.info("\u5f00\u59cb\u6267\u884c\u81ea\u52a8\u4efb\u52a1\uff1a{}", (Object)autoTaskName);
                try {
                    autoTaskClient.retract(tenantDO);
                }
                catch (Exception e) {
                    log.error("\u6267\u884c\u81ea\u52a8\u4efb\u52a1{}\u5f02\u5e38", (Object)autoTaskName, (Object)e);
                }
            }
        }
    }

    private List<String> getCounterSingerReactUserList(List<ProcessNodeDO> processNodeDOList, List<PlusApprovalInfoDO> plusApprovalInfos, boolean plusApprovalFlag) {
        ArrayList<String> userIdList = new ArrayList<String>();
        if (CollectionUtils.isEmpty(plusApprovalInfos)) {
            return userIdList;
        }
        List<String> allUserList = plusApprovalInfos.stream().map(PlusApprovalInfoDO::getApprovaluser).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(processNodeDOList)) {
            return allUserList;
        }
        List notParticipateInList = plusApprovalInfos.stream().filter(x -> Objects.nonNull(x.getPlusSignApprovalFlag()) && 1 == x.getPlusSignApprovalFlag()).map(PlusApprovalInfoDO::getApprovaluser).collect(Collectors.toList());
        List oldAllList = plusApprovalInfos.stream().filter(x -> Objects.isNull(x.getApprovaluser())).map(PlusApprovalInfoDO::getApprovaluser).collect(Collectors.toList());
        if (Boolean.FALSE.equals(plusApprovalFlag) && !CollectionUtils.isEmpty(oldAllList)) {
            notParticipateInList.addAll(oldAllList);
        }
        List tempProcessNodeDOList = processNodeDOList.stream().filter(x -> Objects.nonNull(x.getCompleteusertype()) && x.getCompleteusertype().compareTo(CompleteUserTypeEnum.COUNTERSIGN.getFlag()) == 0).collect(Collectors.toList());
        for (ProcessNodeDO processNodeDO : tempProcessNodeDOList) {
            String completeUserId = processNodeDO.getCompleteuserid();
            if (notParticipateInList.contains(completeUserId)) continue;
            userIdList.add(completeUserId);
        }
        return userIdList;
    }

    private void retractTaskExecuteAutoTask(String retractTaskDefineKey, WorkflowDTO workflowDTO) {
        try {
            ArrayNode nodes = this.getNodes();
            JsonNode currentNode = VaWorkflowConstants.emptyJsonNode();
            for (JsonNode node : nodes) {
                String resourceId = node.findValue("resourceId").asText();
                if (!retractTaskDefineKey.equals(resourceId)) continue;
                currentNode = node;
                break;
            }
            JsonNode propertiesNode = Optional.ofNullable(currentNode.findValue("properties")).orElse(VaWorkflowConstants.emptyJsonNode());
            JsonNode nodeRejectTaskNode = Optional.ofNullable(propertiesNode.findValue("nodeautorejecttask")).orElse(VaWorkflowConstants.emptyJsonNode());
            List<AutoTaskModule> autoTaskModules = this.autoTaskModuleConfig.getModules();
            WorkflowContext context = null;
            block5: for (JsonNode jsonNode : nodeRejectTaskNode) {
                String autoTaskModuleName = jsonNode.get("autoTaskModuleName").asText();
                String autoTaskName = jsonNode.findValue("autoTaskName").asText();
                if (!"WriteBackBillDataAutoTask".equals(autoTaskName)) continue;
                JsonNode items = jsonNode.findValue("items");
                HashMap<String, Object> tableColumnMap = new HashMap<String, Object>(16);
                context = Optional.ofNullable(context).orElse(this.createWorkflowContext(new HashMap<String, Object>()));
                String approvalComment = workflowDTO.getApprovalComment();
                if (!ObjectUtils.isEmpty(approvalComment)) {
                    FormulaParam approvalOpinionFormula = new FormulaParam(ValueType.STRING, approvalComment);
                    context.put("approvalOpinion", approvalOpinionFormula);
                }
                FormulaParam approvalTimeFormula = new FormulaParam(ValueType.DATETIME, VaWorkflowUtils.getCurrentDateTime());
                context.put("approverTime", approvalTimeFormula);
                String tableName = this.calculateWriteBillExpression(items, tableColumnMap, context, workflowDTO);
                if (ObjectUtils.isEmpty(tableName)) {
                    log.error("\u5355\u636e\u56de\u5199\u81ea\u52a8\u4efb\u52a1\u8868\u540d\u4e3a\u7a7a");
                    continue;
                }
                for (AutoTaskModule autoTaskModule : autoTaskModules) {
                    if (!autoTaskModuleName.equalsIgnoreCase(autoTaskModule.getName())) continue;
                    AutoTaskClient autoTaskClient = (AutoTaskClient)FeignUtil.getDynamicClient(AutoTaskClient.class, (String)autoTaskModule.getAppName(), (String)autoTaskModule.getAppPath());
                    TenantDO tenantDO = new TenantDO();
                    tenantDO.setTraceId(workflowDTO.getTraceId());
                    tenantDO.addExtInfo("bizCode", (Object)workflowDTO.getBizCode());
                    tenantDO.addExtInfo("bizType", (Object)workflowDTO.getBizDefine());
                    tenantDO.addExtInfo("autoTaskName", (Object)autoTaskName);
                    tenantDO.addExtInfo("autoTaskModuleName", (Object)autoTaskModuleName);
                    tenantDO.addExtInfo("autoTaskParam", (Object)items);
                    tenantDO.addExtInfo("todoParam", (Object)workflowDTO.getExtInfo());
                    tenantDO.addExtInfo("result", tableColumnMap);
                    if (tableName.contains("(")) {
                        tableName = VaWorkflowConstants.RIGHT_BRACKET_PATTERN.split(VaWorkflowConstants.LEFT_BRACKET_PATTERN.split(tableName)[1])[0];
                        tenantDO.addExtInfo("tableName", (Object)tableName);
                    }
                    log.info("\u5f00\u59cb\u6267\u884c\u81ea\u52a8\u4efb\u52a1\uff1a{}", (Object)autoTaskName);
                    try {
                        autoTaskClient.execute(tenantDO);
                    }
                    catch (Exception e) {
                        log.error("\u6267\u884c\u81ea\u52a8\u4efb\u52a1 {} \u5f02\u5e38", (Object)autoTaskName, (Object)e);
                    }
                    continue block5;
                }
            }
        }
        catch (Exception e) {
            log.error("\u53d6\u56de\u5f85\u529e\u6267\u884c\u5355\u636e\u56de\u5199\u81ea\u52a8\u4efb\u52a1\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }

    private VaTransMessageDO getExtendRetractMsg(WorkflowDTO workflowDTO, Map<String, Object> nodeProp, WorkflowNodeBehavior workflowNodeBehavior) {
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        vaTransMessageDO.setId(UUID.randomUUID().toString());
        vaTransMessageDO.setBizcode(workflowDTO.getBizCode());
        vaTransMessageDO.setBiztype(workflowDTO.getBizDefine());
        vaTransMessageDO.setBizcategory(workflowDTO.getBizType());
        vaTransMessageDO.setOperatordesc("\u5de5\u4f5c\u6d41\u8282\u70b9" + nodeProp.get("nodeName") + "\u6267\u884c\u53d6\u56de\u64cd\u4f5c");
        HashMap<String, String> msgContent = new HashMap<String, String>();
        msgContent.put("workflowDTO", JSONUtil.toJSONString((Object)workflowDTO));
        vaTransMessageDO.setInputparam(JSONUtil.toJSONString(msgContent));
        String retractTransDefine = workflowNodeBehavior.getRetractTransDefine();
        if (StringUtils.hasText(retractTransDefine)) {
            vaTransMessageDO.setDefinename(retractTransDefine);
        } else {
            vaTransMessageDO.setDefinename("-");
            vaTransMessageDO.setMqname(workflowNodeBehavior.getRetractMq());
        }
        return vaTransMessageDO;
    }

    private VaTransMessageDO getSubProcessNodeRetractMsg(WorkflowDTO workflowDTO, Map<String, Object> nodeProp, String retractMq) {
        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
        vaTransMessageDO.setId(UUID.randomUUID().toString());
        vaTransMessageDO.setBizcode(workflowDTO.getBizCode());
        vaTransMessageDO.setBiztype(workflowDTO.getBizDefine());
        vaTransMessageDO.setBizcategory(workflowDTO.getBizType());
        vaTransMessageDO.setOperatordesc("\u5de5\u4f5c\u6d41\u5b50\u6d41\u7a0b\u8282\u70b9\u6267\u884c\u53d6\u56de\u64cd\u4f5c");
        HashMap<String, String> msgContent = new HashMap<String, String>();
        msgContent.put("workflowDTO", JSONUtil.toJSONString((Object)workflowDTO));
        vaTransMessageDO.setInputparam(JSONUtil.toJSONString(msgContent));
        vaTransMessageDO.setDefinename("-");
        vaTransMessageDO.setMqname(retractMq);
        return vaTransMessageDO;
    }

    private void calcPlusApprovalUser(List<String> plusApprovalUsers, List<ProcessNodeDO> completeNodes) {
        for (ProcessNodeDO e : completeNodes) {
            BigDecimal completeusertype = e.getCompleteusertype();
            String completeUserId = e.getCompleteuserid();
            String delegateuser = e.getDelegateuser();
            if (BigDecimal.ONE.equals(completeusertype)) {
                plusApprovalUsers.remove(completeUserId);
            }
            if (!BigDecimal.valueOf(3L).equals(completeusertype)) continue;
            plusApprovalUsers.remove(delegateuser);
        }
    }

    private List<ProcessNodeDO> listProcessNode(String processInstanceId) {
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setProcessid(processInstanceId);
        return this.workflowProcessNodeService.listProcessNode(processNodeDTO);
    }

    private ProcessDO getProcessDO(String processInstanceId) {
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(processInstanceId);
        return this.vaWorkflowProcessService.get(processDTO);
    }

    private List<String> getRetractNodes(String submitNodeDefineId, List<ProcessNodeDO> processNodeDOs, String userId, String retractTaskDefinekey) {
        ArrayList<String> retractNodes = new ArrayList<String>();
        for (ProcessNodeDO processNodeDO : processNodeDOs) {
            if (retractTaskDefinekey.equals(processNodeDO.getNodecode())) {
                if (!userId.equals(processNodeDO.getCompleteuserid())) continue;
                retractNodes.add(processNodeDO.getNodeid());
                break;
            }
            if (submitNodeDefineId.equals(processNodeDO.getNodecode()) && !StringUtils.hasText(processNodeDO.getSubprocessnodeid())) continue;
            retractNodes.add(processNodeDO.getNodeid());
        }
        return retractNodes;
    }

    private Map<String, Object> getTodoTask(String processInstanceId, String retractTaskDefinekey, String traceId) {
        Map currentTask = null;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setProcessId(processInstanceId);
        taskDTO.setBackendRequest(true);
        taskDTO.setPagination(false);
        taskDTO.setTraceId(traceId);
        PageVO pageVO = this.todoClient.listHistoryTask(taskDTO);
        List taskHistorys = pageVO.getRows();
        UserLoginDTO user = ShiroUtil.getUser();
        String userId = user.getId();
        for (int i = taskHistorys.size() - 1; i >= 0; --i) {
            Map taskHistory = (Map)taskHistorys.get(i);
            String taskDefineKey = (String)taskHistory.get("TASKDEFINEKEY");
            String completeUser = (String)taskHistory.get("COMPLETEUSER");
            if (!retractTaskDefinekey.equals(taskDefineKey) || !userId.equals(completeUser)) continue;
            currentTask = taskHistory;
            currentTask.remove("PROCESSSTATUS");
            currentTask.remove("COMPLETEUSER");
            currentTask.remove("COMPLETEUNITCODE");
            currentTask.remove("COMPLETETIME");
            currentTask.remove("COMPLETERESULT");
            currentTask.remove("COMPLETECOMMENT");
            currentTask.remove("APPROVALFLAG");
            break;
        }
        return currentTask;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private R excuteRetractProcess(WorkflowDTO workflowDTO, ProcessDO processDO, String userId, String submitNodeDefineId, VaTransMessageDO extendRetractMsg) {
        try {
            HashMap<String, WorkflowModelImpl> customParam = new HashMap<String, WorkflowModelImpl>();
            customParam.put("workflowModel", this);
            customParam.put("vaExtendRetractsMsg", (WorkflowModelImpl)extendRetractMsg);
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setOperation(WorkflowOperation.RETRACT);
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setSubmitNodeDefineId(submitNodeDefineId);
            vaWorkflowContext.setTransMessageId(workflowDTO.getTransMessageId());
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            Map output = null;
            if (userId.equals(processDO.getStartuser())) {
                this.workflowSevice.closeProcess(processDO.getId(), "\u63d0\u4ea4\u4eba\u64a4\u56de");
                Object transMessageId = customParam.get("transMessageId");
                R result = new R();
                if (!ObjectUtils.isEmpty(transMessageId)) {
                    try {
                        VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
                        vaTransMessageDO.setId(transMessageId.toString());
                        result = this.vaTransMessageService.triggerTrans(vaTransMessageDO);
                    }
                    catch (Exception e) {
                        log.error("{}\u53d6\u56de\u89e6\u53d1\u540e\u7eed\u7f16\u6392\u6267\u884c\u5931\u8d25", (Object)workflowDTO.getBizCode(), (Object)e);
                        result = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.retract.failed"));
                    }
                } else {
                    output = vaWorkflowContext.getOutput();
                    if (output != null) {
                        output.put("processStatus", WorkflowProcessStatus.PROCESS_FINSHED_RETRACT.getValue());
                        ProcessDTO processDTO = new ProcessDTO();
                        processDTO.setBizcode(processDO.getBizcode());
                        List processHistorys = this.vaWorkflowProcessService.listHistory(processDTO);
                        if (processHistorys != null && !processHistorys.isEmpty()) {
                            output.put("hasRejectRecord", true);
                        }
                    }
                    result.put("data", (Object)vaWorkflowContext.getOutput());
                }
                R r = result;
                return r;
            }
            R r = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.notallowedtofetchuser"));
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R refreshParticipant(WorkflowDTO workflowDTO) {
        String tenantName = workflowDTO.getTenantName();
        String processInstanceId = workflowDTO.getProcessInstanceId();
        String nodeCode = workflowDTO.getNodeCode();
        String subProcessBranch = workflowDTO.getSubProcessBranch();
        String uniqueCode = workflowDTO.getUniqueCode();
        boolean oldCountSignFlag = false;
        ProcessNodeDTO dto = new ProcessNodeDTO();
        dto.setProcessid(processInstanceId);
        dto.setOrder("DESC");
        List processNodeDOList = this.workflowProcessNodeService.listProcessNode(dto);
        ProcessNodeDO targetNode = VaWorkflowUtils.findTargetNode(nodeCode, subProcessBranch, processNodeDOList);
        VaWorkflowUtils.filterOtherBranch(processNodeDOList, targetNode);
        HashMap<String, String> oldCompleteUsers = new HashMap<String, String>();
        boolean stopFlag = false;
        for (ProcessNodeDO processNodeDO : processNodeDOList) {
            if (nodeCode.equals(processNodeDO.getNodecode())) {
                stopFlag = true;
                oldCountSignFlag = new BigDecimal(1).equals(processNodeDO.getCountersignflag());
                oldCompleteUsers.put(processNodeDO.getCompleteuserid(), processNodeDO.getDelegateuser());
                continue;
            }
            if (!stopFlag) continue;
            break;
        }
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setProcessId(processInstanceId);
        taskDTO.setTaskDefineKey(nodeCode);
        taskDTO.setBackendRequest(true);
        taskDTO.setSubProcessBranch(subProcessBranch);
        taskDTO.setTraceId(workflowDTO.getTraceId());
        List tasks = this.todoClient.listUnfinished(taskDTO).getRows();
        if (tasks.isEmpty()) {
            List<ProcessNodeDO> processNodeDOS = this.listProcessNode(processInstanceId);
            for (ProcessNodeDO processNodeDO : processNodeDOS) {
                if (StringUtils.hasText(processNodeDO.getCompleteresult())) continue;
                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.notsupportedcustomnode"));
            }
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.notfindtodo"));
        }
        HashMap<String, Object> customParam = new HashMap<String, Object>();
        customParam.put("workflowModel", this);
        customParam.put("processInstanceId", processInstanceId);
        customParam.put("nodeCode", nodeCode);
        customParam.put("subProcessBranch", subProcessBranch);
        customParam.put("oldCompleteUsers", oldCompleteUsers);
        customParam.put("refreshFlag", true);
        taskDTO = new TaskDTO();
        taskDTO.setProcessId(processInstanceId);
        taskDTO.setSubProcessBranch(subProcessBranch);
        taskDTO.setTraceId(workflowDTO.getTraceId());
        PageVO pageVO = this.todoClient.listHistoryTask(taskDTO);
        if (pageVO != null && pageVO.getRows() != null && !pageVO.getRows().isEmpty()) {
            int size;
            Map todoHis;
            List todoHisRows = pageVO.getRows();
            ArrayList<String> completeUsers = new ArrayList<String>();
            for (int i = size = todoHisRows.size() - 1; i >= 0 && Objects.equals((todoHis = (Map)todoHisRows.get(i)).get("TASKDEFINEKEY"), nodeCode); --i) {
                completeUsers.add((String)todoHis.get("COMPLETEUSER"));
            }
            customParam.put("completeUsers", completeUsers);
        }
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(processInstanceId);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        String bizType = processDO.getBizmodule();
        String bizDefine = processDO.getBiztype();
        Map<String, Object> bussinessParamVariables = this.getWorkflowVariables(processDO, bizType, bizDefine);
        this.calProcessParam(bussinessParamVariables);
        try {
            workflowDTO = new WorkflowDTO();
            workflowDTO.setProcessDefineVersion(Long.valueOf(processDO.getDefineversion().longValue()));
            workflowDTO.setBizType(bizType);
            workflowDTO.setBizDefine(bizDefine);
            workflowDTO.setBizCode(processDO.getBizcode());
            workflowDTO.setSubProcessBranch(subProcessBranch);
            workflowDTO.setUniqueCode(uniqueCode);
            workflowDTO.setNodeCode(nodeCode);
            workflowDTO.setTodoParamMap(this.loadTodoParam(workflowDTO));
            SubProcessInfoDTO subProcessInfoDTO = null;
            if (StringUtils.hasText(subProcessBranch) && !CollectionUtils.isEmpty(processNodeDOList)) {
                processNodeDOList = processNodeDOList.stream().filter(item -> item.getCompleteresult() == null && nodeCode.equals(item.getNodecode())).collect(Collectors.toList());
                ProcessNodeDO processNodeDO = (ProcessNodeDO)processNodeDOList.get(0);
                workflowDTO.setTaskId(processNodeDO.getNodeid());
                String subProcessNodeId = processNodeDO.getSubprocessnodeid();
                subProcessInfoDTO = new SubProcessInfoDTO();
                subProcessInfoDTO.setSubProcessNodeId(subProcessNodeId);
                subProcessInfoDTO.setSubProcessBranch(subProcessBranch);
                subProcessInfoDTO.addSubProcessTaskNodeCode(nodeCode);
                customParam.put("subProcessNodeId", subProcessNodeId);
                customParam.put("subProcessBranch", subProcessBranch);
            }
            this.setNodeInfo(workflowDTO, oldCountSignFlag, customParam);
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setOperation(WorkflowOperation.REFRESH);
            vaWorkflowContext.setCurrentTask((Map)tasks.get(0));
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setSubProcessInfoDTO(subProcessInfoDTO);
            vaWorkflowContext.setProcessDO(processDO);
            vaWorkflowContext.setVariables(bussinessParamVariables);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            this.workflowSevice.refreshParticipant(processInstanceId, nodeCode);
            if (customParam.containsKey("todoTasks")) {
                boolean countSignFlag = (Boolean)customParam.get("countSignFlag");
                List todos = (List)customParam.get("todoTasks");
                if (!CollectionUtils.isEmpty(todos)) {
                    if (countSignFlag) {
                        this.sendMessage(todos, tenantName, workflowDTO);
                    } else {
                        ((Map)todos.get(0)).put("USERS", customParam.get("USERS"));
                        this.sendMessage(todos, tenantName, workflowDTO);
                    }
                }
            }
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
        return R.ok();
    }

    private void setNodeInfo(WorkflowDTO workflowDTO, boolean oldCountSignFlag, Map<String, Object> customParam) {
        WorkflowModel model = this.workflowParamService.getModel(workflowDTO.getUniqueCode(), workflowDTO.getProcessDefineVersion());
        ArrayNode workflowProcessNode = this.workflowParamService.getWorkflowProcessNode(model);
        if (StringUtils.hasText(workflowDTO.getSubProcessBranch())) {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode subProcessArrayNode = objectMapper.createArrayNode();
            for (JsonNode jsonNode : workflowProcessNode) {
                if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
                JsonNode childShapes = jsonNode.get("childShapes");
                for (JsonNode childShape : childShapes) {
                    String nodeType = childShape.get("stencil").get("id").asText();
                    if ("StartNoneEvent".equals(nodeType) || "EndNoneEvent".equals(nodeType)) continue;
                    subProcessArrayNode.add(childShape);
                }
            }
            workflowProcessNode = workflowProcessNode.deepCopy();
            workflowProcessNode.addAll(subProcessArrayNode);
        }
        for (JsonNode jsonNode : workflowProcessNode) {
            JsonNode userNode;
            String resourceId = jsonNode.get("resourceId").asText();
            if (!workflowDTO.getNodeCode().equals(resourceId)) continue;
            JsonNode properties = jsonNode.get("properties");
            String multiinstanceType = properties.get("multiinstance_type").asText();
            boolean countSignFlag = false;
            if (!"None".equals(multiinstanceType)) {
                String multiinstance = properties.get("multiinstance_collection").asText();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    userNode = objectMapper.readTree(multiinstance);
                }
                catch (JsonProcessingException e) {
                    throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.refreshparticipantsfailed"));
                }
                countSignFlag = true;
            } else {
                userNode = properties.get("usertaskassignment");
            }
            customParam.put("countSignFlag", countSignFlag);
            customParam.put("refreshFlag", oldCountSignFlag == countSignFlag);
            customParam.put("userNode", userNode);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public R plusApproval(WorkflowDTO workflowDTO) {
        String tenantName = workflowDTO.getTenantName();
        Map<String, Object> currentTask = this.getCurrentTodoTask(workflowDTO);
        if (currentTask == null) {
            ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
            processNodeDTO.setProcessid(workflowDTO.getProcessInstanceId());
            processNodeDTO.setNodeid(workflowDTO.getTaskId());
            List processNodeDOS = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
            if (CollectionUtils.isEmpty(processNodeDOS)) return R.error((int)1, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.taskbeendealneedtorefresh"));
            List unCompleteProcessNodeDOS = processNodeDOS.stream().filter(processNodeDO -> Objects.isNull(processNodeDO.getCompletetime())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(unCompleteProcessNodeDOS)) {
                return R.error((int)1, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.taskbeendealneedtorefresh"));
            }
            currentTask = this.loadTodoParam(workflowDTO);
            currentTask.put("SYSCODE", "WORKFLOW");
            currentTask.put("BIZCODE", workflowDTO.getBizCode());
            currentTask.put("PROCESSID", workflowDTO.getProcessInstanceId());
            currentTask.put("PROCESSDEFINEKEY", workflowDTO.getUniqueCode());
            currentTask.put("PROCESSDEFINEVERSION", workflowDTO.getProcessDefineVersion());
            currentTask.put("BIZTYPE", workflowDTO.getBizType());
            currentTask.put("BIZDEFINE", workflowDTO.getBizDefine());
            currentTask.put("TASKDEFINEKEY", workflowDTO.getNodeCode());
            currentTask.put("COUNTERSIGNFLAG", workflowDTO.getExtInfo("COUNTERSIGNFLAG"));
        } else {
            currentTask.putAll(this.loadTodoParam(workflowDTO));
        }
        try {
            List<Map<String, Object>> todos;
            Map customParam = workflowDTO.getExtInfo();
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setOperation(WorkflowOperation.PLUSAPPROVAL);
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setCurrentTask(currentTask);
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            this.workflowModelHelper.packageParamPlusApproval(customParam, workflowDTO);
            customParam = vaWorkflowContext.getCustomParam();
            if (!customParam.containsKey("todoTasks") || (todos = VaWorkflowUtils.getList(customParam.get("todoTasks"))).isEmpty()) return null;
            this.sendMessage(todos, tenantName, workflowDTO);
            return null;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R trigger(WorkflowDTO workflowDTO) {
        R r = R.ok();
        String processInstanceId = workflowDTO.getProcessInstanceId();
        try {
            List todos;
            ProcessDO processDO = this.getProcessDO(processInstanceId);
            int approvalResult = workflowDTO.getApprovalResult();
            workflowDTO.setUniqueCode(processDO.getDefinekey());
            workflowDTO.setProcessDefineVersion(Long.valueOf(processDO.getDefineversion().longValue()));
            HashMap<String, WorkflowModelImpl> customParam = new HashMap<String, WorkflowModelImpl>();
            customParam.put("workflowModel", this);
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setDefineNodes(this.getNodes());
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setProcessDO(processDO);
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setTransMessageId(workflowDTO.getTransMessageId());
            workflowDTO.getExtInfo().put("SUBMITTIME", processDO.getStarttime());
            workflowDTO.getExtInfo().put("SUBMITUSER", processDO.getStartuser());
            workflowDTO.getExtInfo().put("CREATEUSER", processDO.getStartuser());
            if (2 == approvalResult) {
                vaWorkflowContext.setOperation(WorkflowOperation.REJECT);
                if (workflowDTO.isRejectSkipNode()) {
                    workflowDTO.setTaskId(this.getTaskId(workflowDTO));
                }
            } else if (1 == approvalResult) {
                vaWorkflowContext.setOperation(WorkflowOperation.AGREE);
            }
            Map<String, Object> workflowVariables = this.getWorkflowVariables(processDO, workflowDTO.getBizType(), workflowDTO.getBizDefine());
            this.calProcessParam(workflowVariables);
            workflowDTO.setWorkflowVariables(workflowVariables);
            workflowDTO.setTodoParamMap(this.loadTodoParam(workflowDTO));
            vaWorkflowContext.setVariables(workflowVariables);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            String currentNodeCode = workflowDTO.getNodeCode();
            this.workflowSevice.trigger(processInstanceId, approvalResult, workflowVariables);
            this.workflowInfoService.executeNodeAutoTask(currentNodeCode);
            int processStatus = (Integer)customParam.get("processStatus");
            if (WorkflowProcessStatus.PROCESS_FINSHED_REJECT.getValue() == processStatus || WorkflowProcessStatus.PROCESS_FINSHED_AGREE.getValue() == processStatus) {
                this.executeAutoTask(processStatus, workflowDTO);
                this.sendCompleteMessageBefore(workflowDTO);
                this.sendCompleteMessage(workflowDTO);
            } else if (WorkflowProcessStatus.PROCESS_UNFINSHED_NORMAL.getValue() == processStatus && customParam.containsKey("todoTasks") && !(todos = (List)customParam.get("todoTasks")).isEmpty()) {
                String tenantName = workflowDTO.getTenantName();
                this.sendMessage(todos, tenantName, workflowDTO);
            }
            r.put("data", (Object)vaWorkflowContext.getOutput());
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
        return r;
    }

    private String getTaskId(WorkflowDTO workflowDTO) {
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(workflowDTO.getBizCode());
        processNodeDTO.setProcessid(workflowDTO.getProcessInstanceId());
        processNodeDTO.setCompleteuserid(workflowDTO.getCompleteUser());
        processNodeDTO.setNodecode(workflowDTO.getNodeCode());
        List processNodeDOList = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        if (CollectionUtils.isEmpty(processNodeDOList)) {
            throw new WorkflowException("\u89e6\u53d1\u5de5\u4f5c\u6d41\u9a73\u56de\u64cd\u4f5c\u5931\u8d25\uff1a\u672a\u627e\u5230\u5f53\u524d\u8282\u70b9\u6d41\u7a0b\u8f68\u8ff9");
        }
        return ((ProcessNodeDO)processNodeDOList.get(processNodeDOList.size() - 1)).getNodeid();
    }

    @Override
    public Map<String, Object> currNodeProperties(WorkflowDTO workflowDTO) {
        return this.workflowSevice.currNodeProperties(workflowDTO.getProcessInstanceId(), workflowDTO.getNodeCode());
    }

    private String getWorkflowOption(String optionName) {
        OptionItemDTO workflowOptionDTO = new OptionItemDTO();
        workflowOptionDTO.setName(optionName);
        List list = this.workflowOptionService.list(workflowOptionDTO);
        return ((OptionItemVO)list.get(0)).getVal();
    }

    private Map<String, Object> getCurrentTodoTask(WorkflowDTO workflowDTO) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(workflowDTO.getTaskId());
        taskDTO.setProcessId(workflowDTO.getProcessInstanceId());
        taskDTO.setParticipant(ShiroUtil.getUser().getId());
        taskDTO.setBackendRequest(true);
        taskDTO.setPagination(false);
        taskDTO.setTraceId(workflowDTO.getTraceId());
        List tasks = this.todoClient.listUnfinished(taskDTO).getRows();
        if (!CollectionUtils.isEmpty(tasks)) {
            return (Map)tasks.get(0);
        }
        return null;
    }

    private Map<String, Object> getCurrentRejectTodoTask(WorkflowDTO workflowDTO, String bizCode) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setSubProcessBranch(workflowDTO.getSubProcessBranch());
        taskDTO.setBizCode(bizCode);
        taskDTO.setBackendRequest(true);
        taskDTO.setPagination(false);
        taskDTO.setTraceId(workflowDTO.getTraceId());
        List tasks = this.todoClient.listReject(taskDTO).getRows();
        if (!CollectionUtils.isEmpty(tasks)) {
            return (Map)tasks.get(0);
        }
        return null;
    }

    private void executeAutoTask() {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        if (customParam.containsKey("autoTaskInfoList")) {
            List autoTaskInfoList = (List)customParam.get("autoTaskInfoList");
            for (Map autoTaskInfo : autoTaskInfoList) {
                String nodeCode = (String)autoTaskInfo.get("nodeCode");
                String subProcessBranch = (String)autoTaskInfo.get("subProcessBranch");
                if (StringUtils.hasText(subProcessBranch)) {
                    vaWorkflowContext.getWorkflowDTO().setSubProcessBranch(subProcessBranch);
                    this.workflowInfoService.executeSubProcessInstanceAutoTask(nodeCode);
                    continue;
                }
                this.workflowInfoService.executeNodeAutoTask(nodeCode);
            }
        }
    }

    private void executeAutoTask(int processStatus, WorkflowDTO originalWorkflowDTO) {
        WorkflowDTO workflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)originalWorkflowDTO), WorkflowDTO.class);
        UserLoginDTO loginUser = VaWorkflowUtils.getLoginUserWithToken();
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Locale defaultLocal = LocaleContextHolder.getLocale();
        ThreadPoolConst.AUTOTASK_THREADPOOL.execute(() -> {
            try {
                log.info("\u8fdb\u5165\u81ea\u52a8\u4efb\u52a1\u5904\u7406\u903b\u8f91");
                ShiroUtil.unbindUser();
                ShiroUtil.bindUser((UserLoginDTO)loginUser);
                VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                LocaleContextHolder.setLocale(defaultLocal);
                Map todoParamMap = workflowDTO.getTodoParamMap();
                workflowDTO.putAll(todoParamMap);
                workflowDTO.getExtInfo().put("BIZCODE", workflowDTO.getBizCode());
                workflowDTO.addExtInfo("processStatus", (Object)processStatus);
                this.executeAutoTasks(this.getAutoTasks(processStatus), workflowDTO);
            }
            catch (Exception e) {
                log.error("\u81ea\u52a8\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
            }
            finally {
                ShiroUtil.unbindUser();
                VaContext.removeVaWorkflowContext();
            }
        });
    }

    private ArrayNode getAutoTasks(int processFinishedStatus) {
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)this.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        if (WorkflowProcessStatus.PROCESS_FINSHED_AGREE.getValue() == processFinishedStatus) {
            JsonNode jsonNode = processDesignPluginDefine.getData().get("agreeAutoTasks");
            JsonNode jsonNodeCopy = jsonNode.deepCopy();
            return (ArrayNode)jsonNodeCopy;
        }
        JsonNode jsonNode = processDesignPluginDefine.getData().get("rejectAutoTasks");
        JsonNode jsonNodeCopy = jsonNode.deepCopy();
        return (ArrayNode)jsonNodeCopy;
    }

    public void executeAutoTasks(ArrayNode autoTaskArrayNode, WorkflowDTO workflowDTO) {
        if (autoTaskArrayNode == null) {
            return;
        }
        String bizCode = workflowDTO.getBizCode();
        boolean subProcessRejectSubmit = false;
        if (!ObjectUtils.isEmpty(VaContext.getVaWorkflowContext()) && !ObjectUtils.isEmpty(VaContext.getVaWorkflowContext().getCustomParam()) && VaContext.getVaWorkflowContext().getCustomParam().containsKey("subProcessRejectSubmit")) {
            subProcessRejectSubmit = (Boolean)VaContext.getVaWorkflowContext().getCustomParam().get("subProcessRejectSubmit");
        }
        WorkflowContext workflowContext = null;
        Map variables = workflowDTO.getWorkflowVariables();
        UserLoginDTO currentUser = ShiroUtil.getUser();
        for (JsonNode autoTaskNode : autoTaskArrayNode) {
            JsonNode autoTaskParams;
            String autoTaskName;
            String autoTaskModuleName;
            block23: {
                autoTaskModuleName = autoTaskNode.get("autoTaskModuleName").asText();
                autoTaskName = autoTaskNode.get("autoTaskName").asText();
                autoTaskParams = autoTaskNode.get("items");
                if ("bill".equalsIgnoreCase(autoTaskModuleName) && "BillNotice".equalsIgnoreCase(autoTaskName)) {
                    autoTaskModuleName = "general";
                    autoTaskName = "BizMessageNotice";
                }
                try {
                    if (autoTaskParams.isNull()) break block23;
                    for (JsonNode autoTaskParamNode : autoTaskParams) {
                        if ("BizMessageNotice".equalsIgnoreCase(autoTaskName) && Objects.nonNull(autoTaskParamNode) && autoTaskParamNode.has("messageTemplateCode")) {
                            this.workflowModelHelper.calculateAutoTaskUser(workflowContext, autoTaskParamNode, workflowDTO);
                            continue;
                        }
                        Iterator iterator = autoTaskParamNode.fields();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry)iterator.next();
                            JsonNode paramValue = (JsonNode)entry.getValue();
                            JsonNode paramType = paramValue.get("paramType");
                            if (null == paramType || paramType.isNull() || !"\"formula\"".equals(paramType.toString())) continue;
                            JsonNode formula = paramValue.get("value");
                            String formulaType = formula.findValue("formulaType").asText();
                            String expression = formula.findValue("expression").asText();
                            if (!StringUtils.hasText(expression)) continue;
                            FormulaImpl formulaImpl = VaWorkflowUtils.getFormulaImpl(expression, FormulaType.valueOf((String)formulaType));
                            if (workflowContext == null) {
                                workflowContext = this.createWorkflowContext(variables);
                            }
                            Object result = WorkflowFormulaExecute.evaluate(workflowContext, formulaImpl);
                            ObjectNode objectNode = (ObjectNode)paramValue;
                            if (result instanceof String) {
                                objectNode.put("realValue", (String)result);
                            }
                            if (!(result instanceof ArrayData)) continue;
                            List resultList = ((ArrayData)result).toList();
                            ObjectMapper mapper = new ObjectMapper();
                            String json = mapper.writeValueAsString((Object)resultList);
                            objectNode.set("realValue", mapper.readTree(json));
                        }
                    }
                }
                catch (Exception e) {
                    log.error("\u4e1a\u52a1\u7f16\u53f7\uff1a{}\u5904\u7406\u81ea\u52a8\u4efb\u52a1-{} \u53c2\u6570\u5931\u8d25\uff1a{}", bizCode, autoTaskName, e.getMessage(), e);
                    continue;
                }
            }
            log.info("\u6267\u884c\u81ea\u52a8\u4efb\u52a1\uff1a{}", (Object)autoTaskName);
            TenantDO tenantDO = new TenantDO();
            HashMap<String, String> extInfo = new HashMap<String, String>(workflowDTO.getExtInfo());
            if (workflowDTO.getBizType() != null) {
                extInfo.put("BIZTYPE", workflowDTO.getBizType());
            }
            tenantDO.addExtInfo("approvalResult", (Object)workflowDTO.getApprovalResult());
            String approveUserId = (String)extInfo.get("approveUserId");
            if (!StringUtils.hasText(approveUserId)) {
                UserLoginDTO user = Optional.ofNullable(ShiroUtil.getUser()).orElse(new UserLoginDTO());
                approveUserId = user.getId();
            }
            tenantDO.addExtInfo("approveUserId", (Object)approveUserId);
            String nodeName = (String)extInfo.get("processNodeName");
            tenantDO.addExtInfo("approverTime", (Object)VaWorkflowUtils.getCurrentDateTime());
            tenantDO.addExtInfo("approvalOpinion", (Object)workflowDTO.getApprovalComment());
            tenantDO.addExtInfo("processNodeId", extInfo.get("processNodeId"));
            tenantDO.addExtInfo("processNodeName", (Object)nodeName);
            tenantDO.addExtInfo("bizCode", (Object)bizCode);
            tenantDO.addExtInfo("bizType", (Object)workflowDTO.getBizDefine());
            tenantDO.addExtInfo("autoTaskName", (Object)autoTaskName);
            tenantDO.addExtInfo("autoTaskModuleName", (Object)autoTaskModuleName);
            tenantDO.addExtInfo("autoTaskParam", (Object)autoTaskParams);
            tenantDO.addExtInfo("todoParam", extInfo);
            tenantDO.addExtInfo("rejectType", (Object)workflowDTO.getRejectType());
            tenantDO.addExtInfo("rejectSkipNode", (Object)workflowDTO.isRejectSkipNode());
            tenantDO.addExtInfo("variables", (Object)variables);
            tenantDO.addExtInfo("processStatus", extInfo.get("processStatus"));
            tenantDO.addExtInfo("subProcessBranch", (Object)workflowDTO.getSubProcessBranch());
            tenantDO.addExtInfo("subProcessRejectSubmit", (Object)subProcessRejectSubmit);
            tenantDO.addExtInfo("approvalComment", (Object)workflowDTO.getApprovalComment());
            tenantDO.setTraceId(workflowDTO.getTraceId());
            if ("general".equalsIgnoreCase(autoTaskModuleName)) {
                List generalAutoTaskList = this.autoTaskManager.getAutoTaskList(autoTaskModuleName);
                for (AutoTask autoTask : generalAutoTaskList) {
                    if (!autoTaskName.equalsIgnoreCase(autoTask.getName())) continue;
                    log.info("\u4e1a\u52a1\u7f16\u53f7\uff1a{},\u8282\u70b9\u6807\u8bc6\uff1a{},\u5f00\u59cb\u6267\u884c\u81ea\u52a8\u4efb\u52a1\uff1a{}", bizCode, nodeName, autoTaskName);
                    autoTask.execute((Object)tenantDO);
                    if (ShiroUtil.getUser() == null) {
                        ShiroUtil.bindUser((UserLoginDTO)currentUser);
                    } else {
                        UserLoginDTO user = ShiroUtil.getUser();
                        if (!currentUser.getId().equals(user.getId())) {
                            ShiroUtil.unbindUser();
                            ShiroUtil.bindUser((UserLoginDTO)currentUser);
                        }
                    }
                    break;
                }
            } else if ("WriteBackBillDataAutoTask".equalsIgnoreCase(autoTaskName)) {
                this.dealWriteBackBillDataAutoTask(autoTaskNode, workflowDTO, workflowContext, variables, tenantDO);
            }
            List<AutoTaskModule> autoTaskModules = this.autoTaskModuleConfig.getModules();
            for (AutoTaskModule autoTaskModule : autoTaskModules) {
                if (!autoTaskModuleName.equalsIgnoreCase(autoTaskModule.getName())) continue;
                AutoTaskClient autoTaskClient = (AutoTaskClient)FeignUtil.getDynamicClient(AutoTaskClient.class, (String)autoTaskModule.getAppName(), (String)autoTaskModule.getAppPath());
                log.info("\u4e1a\u52a1\u7f16\u53f7\uff1a{},\u8282\u70b9\u6807\u8bc6\uff1a{},\u5f00\u59cb\u6267\u884c\u81ea\u52a8\u4efb\u52a1\uff1a{}", bizCode, nodeName, autoTaskName);
                try {
                    autoTaskClient.execute(tenantDO);
                }
                catch (Exception e) {
                    log.error("\u4e1a\u52a1\u7f16\u53f7\uff1a{},\u8282\u70b9\u6807\u8bc6\uff1a{},\u6267\u884c\u81ea\u52a8\u4efb\u52a1{}\u5f02\u5e38", bizCode, nodeName, autoTaskName, e);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R reopenLastNode(WorkflowDTO workflowDTO) {
        String bizCode = workflowDTO.getBizCode();
        String subProcessBranch = workflowDTO.getSubProcessBranch();
        boolean openSubProcessNodeFlag = StringUtils.hasText(subProcessBranch);
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        boolean existNotEndProcessFlag = false;
        HashMap<String, Object> customParam = new HashMap<String, Object>();
        customParam.put("workflowModel", this);
        if (processDO != null) {
            existNotEndProcessFlag = true;
            if (!openSubProcessNodeFlag) {
                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.existnotendprocess"));
            }
            workflowDTO.setProcessInstanceId(processDO.getId());
            workflowDTO.addExtInfo("CREATEUSER", (Object)processDO.getStartuser());
        } else {
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            if (!CollectionUtils.isEmpty(processHistoryDOS)) {
                UserLoginDTO submitUser = new UserLoginDTO();
                ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(0);
                submitUser.setId(processHistoryDO.getStartuser());
                submitUser.setLoginUnit(processHistoryDO.getStartunitcode());
                customParam.put("submitUser", submitUser);
                workflowDTO.addExtInfo("CREATEUSER", (Object)processHistoryDO.getStartuser());
            }
        }
        ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
        processNodeDTO.setBizcode(bizCode);
        List allProcessNode = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
        if (CollectionUtils.isEmpty(allProcessNode)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.norecorder"));
        }
        UserLoginDTO userLoginDTO = ShiroUtil.getUser();
        String tenantName = userLoginDTO.getTenantName();
        workflowDTO.setTenantName(tenantName);
        try {
            List todos;
            int allProcessNodeSize;
            ProcessNodeDO endProcessNode;
            String processDefinitionKey = workflowDTO.getUniqueCode();
            String bizType = workflowDTO.getBizType();
            String bizDefine = workflowDTO.getBizDefine();
            processDO = new ProcessDO();
            processDO.setBizcode(bizCode);
            processDO.setDefinekey(processDefinitionKey);
            processDO.setStartuser((String)workflowDTO.getExtInfo("CREATEUSER"));
            if (workflowDTO.getProcessDefineVersion() != null) {
                processDO.setDefineversion(new BigDecimal(workflowDTO.getProcessDefineVersion()));
            }
            Map<String, Object> workflowVariables = this.getWorkflowVariables(processDO, bizType, bizDefine);
            this.calProcessParam(workflowVariables);
            workflowDTO.setWorkflowVariables(workflowVariables);
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            ArrayNode nodes = this.getNodes();
            vaWorkflowContext.setDefineNodes(nodes);
            vaWorkflowContext.setOperation(WorkflowOperation.REOPENLASTNODE);
            workflowDTO.setApprovalResult(Integer.valueOf(1));
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            vaWorkflowContext.setCustomParam(customParam);
            vaWorkflowContext.setTransMessageId(workflowDTO.getTransMessageId());
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            String lastNodeCode = workflowDTO.getNodeCode();
            if (openSubProcessNodeFlag) {
                List branchNodeDOs = allProcessNode.stream().filter(processNodeDO -> subProcessBranch.equals(processNodeDO.getSubprocessbranch())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(branchNodeDOs)) {
                    R r = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.norecorder"));
                    return r;
                }
                ProcessNodeDO branchNodeDO = (ProcessNodeDO)branchNodeDOs.get(0);
                String subProcessNodeId = branchNodeDO.getSubprocessnodeid();
                ProcessNodeDO subProcessNode = allProcessNode.stream().filter(processNodeDO -> subProcessNodeId.equals(processNodeDO.getNodeid())).findFirst().orElse(null);
                if (ObjectUtils.isEmpty(subProcessNode)) {
                    R r = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.norecorder"));
                    return r;
                }
                String subProcessNodeCode = subProcessNode.getNodecode();
                boolean multiInstanceFlag = false;
                for (JsonNode node : nodes) {
                    String nodeId = node.get("resourceId").asText();
                    if (!subProcessNodeCode.equals(nodeId)) continue;
                    JsonNode jsonNode = node.get("properties").get("subprocessbranchstrategy");
                    try {
                        List subProcessBranchStrategy = JSONUtil.parseMapArray((String)JSONUtil.toJSONString((Object)jsonNode));
                        multiInstanceFlag = !CollectionUtils.isEmpty(subProcessBranchStrategy);
                    }
                    catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    break;
                }
                customParam.put("multiInstanceFlag", multiInstanceFlag);
                customParam.put("subProcessNodeCode", subProcessNodeCode);
                String processNodeName = branchNodeDO.getProcessnodename();
                int index = processNodeName.lastIndexOf(" \u2014 ");
                String branchName = null;
                if (index != -1) {
                    branchName = processNodeName.substring(index + 3);
                }
                SubProcessInfoDTO subProcessInfoDTO = new SubProcessInfoDTO();
                HashMap<String, String> branchInfo = new HashMap<String, String>();
                branchInfo.put(subProcessBranch, branchName);
                subProcessInfoDTO.setAllBranchInfo(branchInfo);
                ArrayList<String> branches = new ArrayList<String>();
                branches.add(subProcessBranch);
                subProcessInfoDTO.setAllBranches(branches);
                vaWorkflowContext.setSubProcessInfoDTO(subProcessInfoDTO);
                int branchNodeDOSize = branchNodeDOs.size();
                if (!StringUtils.hasText(lastNodeCode)) {
                    lastNodeCode = workflowDTO.isRejectToSubmitter() ? VaWorkflowNodeUtils.getSubmitNodeDefineId(nodes) : ((ProcessNodeDO)branchNodeDOs.get(branchNodeDOSize - 1)).getNodecode();
                }
            } else if (!StringUtils.hasText(lastNodeCode) && "\u6d41\u7a0b\u7ed3\u675f".equals((endProcessNode = (ProcessNodeDO)allProcessNode.get((allProcessNodeSize = allProcessNode.size()) - 1)).getNodecode()) && allProcessNodeSize > 1) {
                lastNodeCode = ((ProcessNodeDO)allProcessNode.get(allProcessNodeSize - 2)).getNodecode();
            }
            customParam.put("lastNodeCode", lastNodeCode);
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("metaVersion", (Object)workflowDTO.getProcessDefineVersion());
            tenantDO.addExtInfo("workflowDefineKey", (Object)processDefinitionKey);
            tenantDO.setTraceId(workflowDTO.getTraceId());
            Integer workflowDefineVer = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
            if (openSubProcessNodeFlag && existNotEndProcessFlag) {
                vaWorkflowContext.setProcessDO(processDO);
                workflowDTO.setNodeCode(lastNodeCode);
                customParam.remove("subProcessNodeCode");
                ProcessNodeDO subProcessNode = allProcessNode.stream().filter(processNodeDO -> processNodeDO.getNodeid().equals(processNodeDO.getSubprocessnodeid()) && !StringUtils.hasText(processNodeDO.getCompleteresult())).findFirst().orElse(null);
                SubProcessInfoDTO subProcessInfoDTO = vaWorkflowContext.getSubProcessInfoDTO();
                subProcessInfoDTO.setSubProcessNodeId(subProcessNode.getNodeid());
                subProcessInfoDTO.setSubProcessBranch(subProcessBranch);
                subProcessInfoDTO.setSubProcessBranchName((String)subProcessInfoDTO.getAllBranchInfo().get(subProcessBranch));
                this.workflowSevice.reopenLastNode(workflowDTO.getProcessInstanceId(), workflowVariables);
            } else {
                this.workflowSevice.startProcess(processDefinitionKey, workflowDefineVer);
            }
            int processStatus = (Integer)customParam.get("processStatus");
            Object transMessageId = customParam.get("transMessageId");
            R result = new R();
            if (!ObjectUtils.isEmpty(transMessageId)) {
                try {
                    VaTransMessageDO vaTransMessageDO = new VaTransMessageDO();
                    vaTransMessageDO.setId(transMessageId.toString());
                    result = this.vaTransMessageService.triggerTrans(vaTransMessageDO);
                }
                catch (Exception e) {
                    log.error("{}\u63d0\u4ea4\u89e6\u53d1\u540e\u7eed\u7f16\u6392\u6267\u884c\u5931\u8d25", (Object)bizCode, (Object)e);
                    result = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.submitfailed"));
                }
            } else {
                result.put("data", (Object)vaWorkflowContext.getOutput());
            }
            if (WorkflowProcessStatus.PROCESS_UNFINSHED_NORMAL.getValue() == processStatus && customParam.containsKey("todoTasks") && !(todos = (List)customParam.get("todoTasks")).isEmpty()) {
                this.sendMessage(todos, tenantName, workflowDTO);
            }
            if (WorkflowProcessStatus.PROCESS_FINSHED_AGREE.getValue() == processStatus) {
                workflowDTO.setApprovalResult(Integer.valueOf(1));
                this.executeAutoTask(processStatus, workflowDTO);
                this.sendCompleteMessageBefore(workflowDTO);
                this.sendCompleteMessage(workflowDTO);
            }
            result.put("data", (Object)vaWorkflowContext.getOutput());
            R r = result;
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    private R checkSelectNextApprovalNode(WorkflowDTO workflowDTO) {
        if (workflowDTO.isConsult()) {
            return R.ok();
        }
        Integer approvalResult = workflowDTO.getApprovalResult();
        if (1 != approvalResult) {
            return R.ok();
        }
        if (!ObjectUtils.isEmpty(workflowDTO.getExtInfo()) && workflowDTO.getExtInfo().containsKey("subProcessSubmit")) {
            return R.ok();
        }
        String nodeCode = workflowDTO.getNodeCode();
        String nextNodeCode = workflowDTO.getNextNodeCode();
        ArrayNode arrayNode = this.getNodes();
        JsonNode properties = null;
        JsonNode outgoing = null;
        block0: for (JsonNode jsonNode : arrayNode) {
            String resourceId = jsonNode.get("resourceId").asText();
            if (nodeCode.equals(resourceId)) {
                properties = jsonNode.get("properties");
                outgoing = jsonNode.get("outgoing");
                break;
            }
            JsonNode childShapes = jsonNode.get("childShapes");
            if (ObjectUtils.isEmpty(childShapes)) continue;
            for (JsonNode childShape : childShapes) {
                String childResourceId = childShape.get("resourceId").asText();
                if (!nodeCode.equals(childResourceId)) continue;
                properties = childShape.get("properties");
                outgoing = jsonNode.get("outgoing");
                continue block0;
            }
        }
        boolean selectNextApprovalNode = false;
        if (ObjectUtils.isEmpty(properties)) {
            return R.ok();
        }
        JsonNode selectNextApprovalNodeConfig = properties.get("selectnextapprovalnode");
        if (selectNextApprovalNodeConfig != null && selectNextApprovalNodeConfig.asBoolean()) {
            selectNextApprovalNode = true;
        }
        if (selectNextApprovalNode) {
            if (outgoing.size() == 1) {
                return R.ok();
            }
            Map todoParamMap = workflowDTO.getTodoParamMap();
            if (Integer.parseInt(String.valueOf(todoParamMap.get("COUNTERSIGNFLAG"))) == 1) {
                TaskDTO taskDTO = new TaskDTO();
                taskDTO.setBizCode(workflowDTO.getBizCode());
                taskDTO.setTaskDefineKey(nodeCode);
                taskDTO.setSubProcessBranch(workflowDTO.getSubProcessBranch());
                taskDTO.setBackendRequest(true);
                taskDTO.setTraceId(workflowDTO.getTraceId());
                PageVO pageVO = this.todoClient.listUnfinished(taskDTO);
                List taskList = pageVO.getRows();
                List tasks = taskList.stream().filter(map -> ObjectUtils.isEmpty(map.get("APPROVALFLAG")) || !new BigDecimal("1").equals(new BigDecimal(String.valueOf(map.get("APPROVALFLAG"))))).collect(Collectors.toList());
                if (tasks.size() > 1) {
                    return R.ok();
                }
            }
            int executableSequenceSize = this.countExecutableSequence(workflowDTO, outgoing, arrayNode);
            if (!StringUtils.hasText(nextNodeCode) && executableSequenceSize > 1) {
                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.manualselectapprovernode"));
            }
        }
        return R.ok();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int countExecutableSequence(WorkflowDTO workflowDTO, JsonNode outgoing, ArrayNode arrayNode) {
        String bizCode = workflowDTO.getBizCode();
        String bizDefine = workflowDTO.getBizDefine();
        String uniqueCode = workflowDTO.getUniqueCode();
        Long processDefineVersion = workflowDTO.getProcessDefineVersion();
        String bizType = workflowDTO.getBizType();
        Map<String, Object> params = this.workflowParamService.loadWorkflowVariables(bizCode, bizDefine, new BigDecimal(processDefineVersion), bizType, uniqueCode);
        workflowDTO.setWorkflowVariables(params);
        HashMap<String, WorkflowModelImpl> calParams = new HashMap<String, WorkflowModelImpl>();
        calParams.put("workflowModel", this);
        VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
        vaWorkflowContext.setWorkflowDTO(workflowDTO);
        vaWorkflowContext.setCustomParam(calParams);
        VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
        int result = 0;
        try {
            List sequenceFlowOrderList = outgoing.findValues("resourceId");
            block3: for (JsonNode sequenceFlow : sequenceFlowOrderList) {
                for (JsonNode flowNode : arrayNode) {
                    if (!sequenceFlow.asText().equals(flowNode.get("resourceId").asText())) continue;
                    JsonNode properties = flowNode.get("properties");
                    JsonNode conditionNode = properties.get("conditionsequenceflow");
                    JsonNode conditionViewNode = properties.get("conditionview");
                    String conditionViewStr = JSONUtil.toJSONString((Object)conditionViewNode);
                    if (conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString())) {
                        if (!SequenceConditionUtils.executeConditionView(conditionViewStr)) continue block3;
                        ++result;
                        continue block3;
                    }
                    WorkflowFormulaSevice workflowFormulaSevice = (WorkflowFormulaSevice)ApplicationContextRegister.getBean(WorkflowFormulaSevice.class);
                    boolean executeResult = workflowFormulaSevice.judge(uniqueCode, null, workflowDTO.getWorkflowVariables(), conditionNode.toString());
                    if (!executeResult || !SequenceConditionUtils.executeConditionView(conditionViewStr)) continue block3;
                    ++result;
                    continue block3;
                }
            }
            int n = result;
            return n;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    private void dealWriteBackBillDataAutoTask(JsonNode autoTaskNode, WorkflowDTO workflowDTO, WorkflowContext workflowContext, Map<String, Object> variablesMap, TenantDO tenantDO) {
        String tableName;
        WorkflowContext context = workflowContext;
        String autoTaskName = autoTaskNode.get("autoTaskName").asText();
        JsonNode autoTaskParams = autoTaskNode.get("items");
        if (ObjectUtils.isEmpty(autoTaskParams) || autoTaskParams.isNull()) {
            return;
        }
        HashMap<String, Object> tableColumnMap = new HashMap<String, Object>(16);
        try {
            String approvalComment;
            WorkflowOperation workflowOperation;
            Map todoParamMap;
            String participant;
            if (context == null) {
                context = this.createWorkflowContext(variablesMap);
            }
            if (!StringUtils.hasText(participant = (String)(todoParamMap = Optional.ofNullable(workflowDTO.getTodoParamMap()).orElse(Collections.emptyMap())).get("PARTICIPANT")) && (workflowOperation = VaContext.getVaWorkflowContext().getOperation()) == WorkflowOperation.SUBMIT) {
                participant = ShiroUtil.getUser().getId();
            }
            if (!ObjectUtils.isEmpty(participant)) {
                FormulaParam formulaParamNode = new FormulaParam(ValueType.STRING, participant);
                context.put("approver", formulaParamNode);
            }
            if (!ObjectUtils.isEmpty(approvalComment = workflowDTO.getApprovalComment())) {
                FormulaParam approvalOpinionFormula = new FormulaParam(ValueType.STRING, approvalComment);
                context.put("approvalOpinion", approvalOpinionFormula);
            }
            FormulaParam approvalTimeFormula = new FormulaParam(ValueType.DATETIME, VaWorkflowUtils.getCurrentDateTime());
            context.put("approverTime", approvalTimeFormula);
            tableName = this.calculateWriteBillExpression(autoTaskParams, tableColumnMap, context, workflowDTO);
        }
        catch (Exception e) {
            log.error("\u5904\u7406\u81ea\u52a8\u4efb\u52a1-{}\u53c2\u6570\u5931\u8d25\uff1a", (Object)autoTaskName, (Object)e);
            return;
        }
        if (ObjectUtils.isEmpty(tableName)) {
            log.error("\u5904\u7406\u81ea\u52a8\u4efb\u52a1 {} \u53c2\u6570\u5931\u8d25\uff1a{} ", (Object)autoTaskName, (Object)"\u8868\u540d\u4e3a\u7a7a");
            return;
        }
        tenantDO.addExtInfo("result", tableColumnMap);
        if (StringUtils.hasText(tableName) && tableName.contains("(")) {
            tableName = VaWorkflowConstants.RIGHT_BRACKET_PATTERN.split(VaWorkflowConstants.LEFT_BRACKET_PATTERN.split(tableName)[1])[0];
            tenantDO.addExtInfo("tableName", (Object)tableName);
        }
    }

    private String calculateWriteBillExpression(JsonNode autoTaskParams, Map<String, Object> tableColumnMap, WorkflowContext context, WorkflowDTO workflowDTO) {
        WorkflowOperation workflowOperation = VaContext.getVaWorkflowContext().getOperation();
        String tableName = null;
        for (JsonNode autoTaskParamNode : autoTaskParams) {
            JsonNode tableDataNode = autoTaskParamNode.findValue("tableData");
            tableName = autoTaskParamNode.get("tableName").asText();
            if (tableDataNode == null) continue;
            block13: for (JsonNode columnDataNode : tableDataNode) {
                String columnName = columnDataNode.get("columnName").asText();
                String expression = columnDataNode.get("param").get("expression").asText();
                String formulaType = columnDataNode.get("param").get("formulaType").asText();
                if (!StringUtils.hasText(expression)) continue;
                switch (expression) {
                    case "[approvalOpinion]": {
                        tableColumnMap.put(columnName, String.valueOf(workflowDTO.getApprovalComment()));
                        continue block13;
                    }
                    case "[approver]": {
                        Map todoParamMap = Optional.ofNullable(workflowDTO.getTodoParamMap()).orElse(Collections.emptyMap());
                        String participant = (String)todoParamMap.get("PARTICIPANT");
                        if (!StringUtils.hasText(participant) && workflowOperation == WorkflowOperation.SUBMIT) {
                            participant = ShiroUtil.getUser().getId();
                        }
                        tableColumnMap.put(columnName, participant);
                        continue block13;
                    }
                    case "[approverTime]": {
                        tableColumnMap.put(columnName, VaWorkflowUtils.getCurrentDateTime());
                        continue block13;
                    }
                }
                try {
                    FormulaImpl formulaImpl = VaWorkflowUtils.getFormulaImpl(expression, FormulaType.valueOf((String)formulaType));
                    Object expressionResult = WorkflowFormulaExecute.evaluate(context, formulaImpl);
                    if (expressionResult instanceof ArrayData) {
                        List resultList = ((ArrayData)expressionResult).toList();
                        ObjectMapper mapper = new ObjectMapper();
                        String json = mapper.writeValueAsString((Object)resultList);
                        tableColumnMap.put(columnName, mapper.readTree(json));
                        continue;
                    }
                    tableColumnMap.put(columnName, expressionResult);
                }
                catch (Exception e) {
                    log.error("error occur: {}", (Object)e.getMessage(), (Object)e);
                }
            }
        }
        return tableName;
    }

    private WorkflowContext createWorkflowContext(Map<String, Object> variables) {
        WorkflowContext workflowContext = new WorkflowContext(new HashMap<String, Object>());
        ProcessParamPlugin processParamPlugin = (ProcessParamPlugin)this.getPlugins().get(ProcessParamPlugin.class);
        ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)processParamPlugin.getDefine();
        List<ProcessParam> processParams = processParamPluginDefine.getProcessParam();
        for (ProcessParam processParam : processParams) {
            FormulaParam formulaParamNode;
            Object paramValue = variables.get(processParam.getParamName());
            try {
                if (!ObjectUtils.isEmpty(paramValue) && paramValue instanceof String) {
                    String paramValueStr = (String)paramValue;
                    if (paramValueStr.startsWith("{")) {
                        Map jSONObject = JSONUtil.parseMap((String)paramValueStr);
                        int baseType = (Integer)jSONObject.get("baseType");
                        List data = (List)jSONObject.get("data");
                        formulaParamNode = new FormulaParam(processParam.getParamType(), new ArrayData(baseType, (Collection)data));
                    } else {
                        formulaParamNode = new FormulaParam(processParam.getParamType(), variables.get(processParam.getParamName()));
                    }
                } else {
                    formulaParamNode = new FormulaParam(processParam.getParamType(), variables.get(processParam.getParamName()));
                }
                workflowContext.put(processParam.getParamName(), formulaParamNode);
            }
            catch (Exception e) {
                formulaParamNode = new FormulaParam(processParam.getParamType(), variables.get(processParam.getParamName()));
                workflowContext.put(processParam.getParamName(), formulaParamNode);
            }
        }
        return workflowContext;
    }

    private String getRetractTaskDefinekey(Map<String, List<String>> nodeCompleteUserMap, String taskDefineKey, List<String> taskDefineKeys, List<ProcessNodeDO> processNodeDOs) {
        UserLoginDTO user = ShiroUtil.getUser();
        String username = user.getId();
        List<String> completeUsers = nodeCompleteUserMap.get(taskDefineKey);
        if (completeUsers.size() > 1 || !this.isAutoAgree(taskDefineKey) || !"\u81ea\u52a8\u540c\u610f".equals(this.getApproValComment(taskDefineKey, username, processNodeDOs))) {
            return taskDefineKey;
        }
        int index = taskDefineKeys.indexOf(taskDefineKey);
        if (index + 1 >= taskDefineKeys.size()) {
            return "end";
        }
        String nextTaskDefineKey = taskDefineKeys.get(index + 1);
        List<String> nextCompleteUsers = nodeCompleteUserMap.get(nextTaskDefineKey);
        if (!nextCompleteUsers.contains(username)) {
            return taskDefineKey;
        }
        return this.getRetractTaskDefinekey(nodeCompleteUserMap, nextTaskDefineKey, taskDefineKeys, processNodeDOs);
    }

    private String getApproValComment(String taskDefineKey, String username, List<ProcessNodeDO> processNodeDOs) {
        for (ProcessNodeDO taskHistory : processNodeDOs) {
            if (!taskDefineKey.equals(taskHistory.getNodecode()) || !username.equals(taskHistory.getCompleteuserid())) continue;
            return taskHistory.getCompletecomment();
        }
        return null;
    }

    private void calProcessParam(Map<String, Object> workflowVariables) {
        ProcessParamPlugin processParamPlugin = (ProcessParamPlugin)this.getPlugins().get(ProcessParamPlugin.class);
        ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)processParamPlugin.getDefine();
        List<ProcessParam> processParams = processParamPluginDefine.getProcessParam();
        for (ProcessParam processParam : processParams) {
            Object paramValue = workflowVariables.get(processParam.getParamName());
            if (paramValue == null) {
                ValueType valueType = processParam.getParamType();
                if (ValueType.BOOLEAN == valueType) {
                    paramValue = false;
                }
                if (ValueType.STRING == valueType) {
                    paramValue = "";
                }
                if (ValueType.DECIMAL == valueType || ValueType.INTEGER == valueType || ValueType.LONG == valueType) {
                    paramValue = 0;
                }
            }
            workflowVariables.put(processParam.getParamName(), paramValue);
        }
    }

    public void setAutoTaskManager(AutoTaskManager autoTaskManager) {
        this.autoTaskManager = autoTaskManager;
    }

    public void setAutoTaskModuleConfig(AutoTaskModuleConfig autoTaskModuleConfig) {
        this.autoTaskModuleConfig = autoTaskModuleConfig;
    }

    public void setVaWorkflowProcessService(VaWorkflowProcessService vaWorkflowProcessService) {
        this.vaWorkflowProcessService = vaWorkflowProcessService;
    }

    public void setWorkflowProcessNodeService(WorkflowProcessNodeService workflowProcessNodeService) {
        this.workflowProcessNodeService = workflowProcessNodeService;
    }

    public void setWorkflowOptionService(WorkflowOptionService workflowOptionService) {
        this.workflowOptionService = workflowOptionService;
    }

    public void setWorkflowPlusApprovalService(WorkflowPlusApprovalService workflowPlusApprovalService) {
        this.workflowPlusApprovalService = workflowPlusApprovalService;
    }

    public void setWorkflowMetaService(WorkflowMetaService workflowMetaService) {
        this.workflowMetaService = workflowMetaService;
    }

    public void setWorkflowInfoService(WorkflowInfoService workflowInfoService) {
        this.workflowInfoService = workflowInfoService;
    }

    public void setWorkflowParamService(WorkflowParamService workflowParamService) {
        this.workflowParamService = workflowParamService;
    }

    public void setWorkflowSubProcessBranchStrategyService(WorkflowSubProcessBranchStrategyService workflowSubProcessBranchStrategyService) {
        this.workflowSubProcessBranchStrategyService = workflowSubProcessBranchStrategyService;
    }

    public void setWorkflowPlusApprovalConsultService(WorkflowPlusApprovalConsultService workflowPlusApprovalConsultService) {
        this.workflowPlusApprovalConsultService = workflowPlusApprovalConsultService;
    }
}

