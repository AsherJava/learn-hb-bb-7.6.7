/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.delegate.DelegateDTO
 *  com.jiuqi.va.domain.delegate.DelegateService
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.domain.workflow.ProcessRejectNodeDO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.auto.agree.AutoAgreeConfig
 *  com.jiuqi.va.domain.workflow.auto.agree.AutoAgreeOption
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDO
 *  com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.forward.process.ForwardProcessDTO
 *  com.jiuqi.va.domain.workflow.forward.process.ForwardProcessNode
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice
 *  com.jiuqi.va.domain.workflow.service.WorkflowForwardProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowInfoService
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService
 *  com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice
 *  com.jiuqi.va.domain.workflow.service.node.VaWorkflowNodeConfigService
 *  com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService
 *  com.jiuqi.va.feign.client.TodoClient
 */
package com.jiuqi.va.workflow.service.impl.node.next;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.delegate.DelegateDTO;
import com.jiuqi.va.domain.delegate.DelegateService;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.domain.workflow.ProcessRejectNodeDO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.auto.agree.AutoAgreeConfig;
import com.jiuqi.va.domain.workflow.auto.agree.AutoAgreeOption;
import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDO;
import com.jiuqi.va.domain.workflow.commonuser.WorkflowCommonUserDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.forward.process.ForwardProcessDTO;
import com.jiuqi.va.domain.workflow.forward.process.ForwardProcessNode;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowFormulaSevice;
import com.jiuqi.va.domain.workflow.service.WorkflowForwardProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowInfoService;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowProcessRejectNodeService;
import com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice;
import com.jiuqi.va.domain.workflow.service.node.VaWorkflowNodeConfigService;
import com.jiuqi.va.domain.workflow.service.node.next.WorkflowNextNodeService;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPlugin;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowCommonUserService;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.SequenceConditionUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowNodeUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowNextNodeServiceImpl
implements WorkflowNextNodeService {
    private static final Logger log = LoggerFactory.getLogger(WorkflowNextNodeServiceImpl.class);
    public static final String DISABLE = "disable";
    public static final String CHOOSE_APPROVER_NODE = "chooseApproverNode";
    public static final String NODE_NAME = "nodeName";
    public static final String CHOOSE_APPROVER = "chooseApprover";
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    @Autowired
    private WorkflowOptionService workflowOptionService;
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private WorkflowInfoService workflowInfoService;
    @Autowired
    private DelegateService delegateService;
    @Autowired
    private WorkflowForwardProcessService forwardProcessService;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowFormulaSevice workflowFormulaSevice;
    @Autowired
    private WorkflowStrategySevice workflowStrategySevice;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private WorkflowProcessRejectNodeService workflowProcessRejectNodeService;
    @Autowired
    private WorkflowCommonUserService workflowSelectApproverService;
    @Autowired
    private WorkflowProcessNodeService workflowProcessNodeService;
    @Autowired
    private VaWorkflowNodeConfigService vaWorkflowNodeConfigService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public R listNextNode(WorkflowDTO workflowDTO) {
        String uniqueCode = workflowDTO.getUniqueCode();
        Long processDefineVersion = workflowDTO.getProcessDefineVersion();
        String nodeCode = workflowDTO.getNodeCode();
        String bizCode = workflowDTO.getBizCode();
        String bizDefine = workflowDTO.getBizDefine();
        String bizType = workflowDTO.getBizType();
        if (!StringUtils.hasText(uniqueCode)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        if (!(StringUtils.hasText(bizCode) && StringUtils.hasText(bizDefine) && StringUtils.hasText(bizType))) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
        WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        ArrayNode oldArrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        HashMap<String, JsonNode> nodeCodeMap = new HashMap<String, JsonNode>();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subProcessArrayNode = objectMapper.createArrayNode();
        for (JsonNode jsonNode : oldArrayNode) {
            nodeCodeMap.put(jsonNode.get("resourceId").asText(), jsonNode);
            if ("StartNoneEvent".equals(jsonNode.get("stencil").get("id").asText()) && !StringUtils.hasText(nodeCode)) {
                ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
                String outgoingId = outgoings.get(0).get("resourceId").asText();
                for (JsonNode node : oldArrayNode) {
                    if (!outgoingId.equals(node.get("resourceId").asText())) continue;
                    nodeCode = node.get("target").get("resourceId").asText();
                }
            }
            if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
            JsonNode childShapes = jsonNode.get("childShapes");
            for (JsonNode childShape : childShapes) {
                String nodeType = childShape.get("stencil").get("id").asText();
                if ("StartNoneEvent".equals(nodeType) || "EndNoneEvent".equals(nodeType)) continue;
                subProcessArrayNode.add(childShape);
            }
        }
        JsonNode currentNode = (JsonNode)nodeCodeMap.get(nodeCode);
        if (currentNode == null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.currapprovalnodenotfound"));
        }
        R result = new R();
        JsonNode selectNextApprovalNode = currentNode.get("properties").get("selectnextapprovalnode");
        if (selectNextApprovalNode == null || !selectNextApprovalNode.asBoolean()) {
            result.put(CHOOSE_APPROVER_NODE, (Object)false);
            return result;
        }
        ArrayNode arrayNode = oldArrayNode.deepCopy();
        arrayNode.addAll(subProcessArrayNode);
        BigDecimal version = processDefineVersion == null ? null : new BigDecimal(processDefineVersion);
        Map<String, Object> params = this.workflowParamService.loadWorkflowVariables(bizCode, bizDefine, version, bizType, uniqueCode);
        workflowDTO.setWorkflowVariables(params);
        try {
            boolean counterSignFlag;
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            HashMap<String, WorkflowModel> calParams = new HashMap<String, WorkflowModel>();
            calParams.put("workflowModel", workflowModel);
            vaWorkflowContext.setCustomParam(calParams);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            Map todoParamMap = workflowDTO.getTodoParamMap();
            Object rejectskipflagObj = todoParamMap.get("rejectskipflag");
            Object countersignflagObj = todoParamMap.get("countersignflag");
            boolean bl = counterSignFlag = countersignflagObj != null && Integer.parseInt(String.valueOf(countersignflagObj)) == 1;
            if (rejectskipflagObj != null && Integer.parseInt(String.valueOf(rejectskipflagObj)) == 1) {
                R tasks;
                if (counterSignFlag) {
                    tasks = this.getTasks(workflowDTO, nodeCode, bizCode);
                    if (tasks.size() > 1) {
                        result.put(CHOOSE_APPROVER_NODE, (Object)false);
                    } else {
                        this.packageApproverResult(arrayNode, result, todoParamMap);
                    }
                } else {
                    this.packageApproverResult(arrayNode, result, todoParamMap);
                }
                tasks = result;
                return tasks;
            }
            if (counterSignFlag) {
                List<Map<String, Object>> tasks = this.getTasks(workflowDTO, nodeCode, bizCode);
                tasks.removeIf(task -> Objects.equals("1", String.valueOf(task.get("APPROVALFLAG"))));
                if (tasks.size() > 1) {
                    result.put(CHOOSE_APPROVER_NODE, (Object)false);
                    R r = result;
                    return r;
                }
            }
            List<Map<String, String>> nextNodes = this.listNextNode(nodeCode, arrayNode);
            result.put(CHOOSE_APPROVER_NODE, (Object)true);
            result.put("chooseApproverNodeList", nextNodes);
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
        return result;
    }

    private void packageApproverResult(ArrayNode arrayNode, R result, Map<String, Object> todoParamMap) {
        JsonNode nextNode = this.getNextJsonNode(String.valueOf(todoParamMap.get("rejectnodeid")), arrayNode);
        ArrayList nodes = new ArrayList();
        HashMap<String, String> node = new HashMap<String, String>();
        node.put("nodeCode", nextNode.get("resourceId").asText());
        node.put(NODE_NAME, nextNode.get("properties").get("name").asText());
        nodes.add(node);
        result.put(CHOOSE_APPROVER_NODE, (Object)true);
        result.put("chooseApproverNodeList", nodes);
    }

    private JsonNode getNextJsonNode(String taskDefineKey, ArrayNode arrayNode) {
        for (JsonNode jsonNode : arrayNode) {
            if (taskDefineKey.equals(jsonNode.get("resourceId").asText())) {
                return jsonNode;
            }
            if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
            JsonNode childShapes = jsonNode.get("childShapes");
            for (JsonNode childShape : childShapes) {
                if (!taskDefineKey.equals(childShape.get("resourceId").asText())) continue;
                return childShape;
            }
        }
        return null;
    }

    private List<Map<String, Object>> getTasks(WorkflowDTO workflowDTO, String nodeCode, String bizCode) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setBizCode(bizCode);
        taskDTO.setTaskDefineKey(nodeCode);
        taskDTO.setBackendRequest(true);
        taskDTO.setSubProcessBranch(workflowDTO.getSubProcessBranch());
        PageVO pageVO = this.todoClient.listUnfinished(taskDTO);
        return pageVO.getRows().stream().filter(task -> !"3".equals(String.valueOf(task.get("TASKTYPE")))).collect(Collectors.toList());
    }

    private List<Map<String, String>> listNextNode(String resourceId, ArrayNode arrayNode) {
        ArrayList<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
        WorkflowDTO workflowDTO = VaContext.getVaWorkflowContext().getWorkflowDTO();
        for (JsonNode jsonNode : arrayNode) {
            JsonNode sequenceFlowOrderJson;
            if (!resourceId.equals(jsonNode.get("resourceId").asText())) continue;
            JsonNode sequencefloworder = jsonNode.get("properties").get("sequencefloworder");
            if (sequencefloworder != null && (sequenceFlowOrderJson = sequencefloworder.get("sequenceFlowOrder")) != null && sequenceFlowOrderJson.isArray()) {
                for (JsonNode sequenceFlowOrder : sequenceFlowOrderJson) {
                    this.findTargetNode(arrayNode, nodes, workflowDTO, sequenceFlowOrder.asText());
                }
            } else {
                List sequenceFlowOrderList = jsonNode.get("outgoing").findValues("resourceId");
                for (JsonNode node : sequenceFlowOrderList) {
                    String sequenceFlowId = node.asText();
                    this.findTargetNode(arrayNode, nodes, workflowDTO, sequenceFlowId);
                }
            }
            break;
        }
        return nodes;
    }

    private void findTargetNode(ArrayNode arrayNode, List<Map<String, String>> nodes, WorkflowDTO workflowDTO, String sequenceFlowId) {
        block0: for (JsonNode flowNode : arrayNode) {
            if (!sequenceFlowId.equals(flowNode.get("resourceId").asText())) continue;
            JsonNode properties = flowNode.get("properties");
            JsonNode conditionNode = properties.get("conditionsequenceflow");
            JsonNode conditionViewNode = properties.get("conditionview");
            boolean executeResult = conditionNode instanceof TextNode || "\"\"".equals(conditionNode.get("expression").toString()) ? true : this.workflowFormulaSevice.judge(workflowDTO.getUniqueCode(), null, workflowDTO.getWorkflowVariables(), conditionNode.toString());
            if (!executeResult || !SequenceConditionUtils.executeConditionView(JSONUtil.toJSONString((Object)conditionViewNode))) break;
            String nextResourceId = flowNode.get("target").get("resourceId").asText();
            for (JsonNode nodeInfo : arrayNode) {
                if (!nextResourceId.equals(nodeInfo.get("resourceId").asText())) continue;
                HashMap<String, String> node = new HashMap<String, String>();
                node.put("nodeCode", nodeInfo.get("resourceId").asText());
                node.put(NODE_NAME, nodeInfo.get("properties").get("name").asText());
                nodes.add(node);
                break block0;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public R getNextNodeApprover(WorkflowDTO workflowDTO) {
        if (!(StringUtils.hasText(workflowDTO.getUniqueCode()) && StringUtils.hasText(workflowDTO.getBizCode()) && StringUtils.hasText(workflowDTO.getBizDefine()))) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        OptionItemDTO optionItemDTO = new OptionItemDTO();
        optionItemDTO.setName("WF1006");
        List optionItemVOs = this.workflowOptionService.list(optionItemDTO);
        if (optionItemVOs.isEmpty() || "0".equals(((OptionItemVO)optionItemVOs.get(0)).getVal())) {
            R result = new R();
            result.put(CHOOSE_APPROVER, (Object)DISABLE);
            return result;
        }
        String uniqueCode = workflowDTO.getUniqueCode();
        Long processDefineVersion = workflowDTO.getProcessDefineVersion();
        String nodeCode = workflowDTO.getNodeCode();
        String bizCode = workflowDTO.getBizCode();
        String processInstanceId = workflowDTO.getProcessInstanceId();
        String bizDefine = workflowDTO.getBizDefine();
        String subProcessBranch = workflowDTO.getSubProcessBranch();
        WorkflowModel workflowModel = this.workflowParamService.getModel(uniqueCode, processDefineVersion);
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        ArrayNode oldArrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        boolean counterSignFlag = false;
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subProcessArrayNode = objectMapper.createArrayNode();
        for (JsonNode jsonNode : oldArrayNode) {
            if (Objects.equals(nodeCode, jsonNode.get("resourceId").asText())) {
                boolean bl = counterSignFlag = !"None".equals(jsonNode.get("properties").get("multiinstance_type").asText());
            }
            if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
            JsonNode childShapes = jsonNode.get("childShapes");
            for (JsonNode childShape : childShapes) {
                String nodeType = childShape.get("stencil").get("id").asText();
                if ("StartNoneEvent".equals(nodeType) || "EndNoneEvent".equals(nodeType)) continue;
                subProcessArrayNode.add(childShape);
            }
        }
        ArrayNode arrayNode = oldArrayNode.deepCopy();
        arrayNode.addAll(subProcessArrayNode);
        JsonNode nextNode = null;
        ProcessDO processDO = null;
        try {
            JsonNode properties;
            String multiinstanceType;
            Map<String, Object> workflowVariables;
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            HashMap<String, WorkflowModel> calParams = new HashMap<String, WorkflowModel>();
            calParams.put("workflowModel", workflowModel);
            vaWorkflowContext.setCustomParam(calParams);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            Map todoParamMap = workflowDTO.getTodoParamMap();
            HashMap<String, Object> todoTask = null;
            if (StringUtils.hasText(nodeCode)) {
                String definekey;
                List taskList;
                if (counterSignFlag) {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setBizCode(bizCode);
                    taskDTO.setTaskDefineKey(nodeCode);
                    taskDTO.setSubProcessBranch(subProcessBranch);
                    taskDTO.setBackendRequest(true);
                    PageVO pageVO = this.todoClient.listUnfinished(taskDTO);
                    taskList = pageVO.getRows();
                    List tasks = taskList.stream().filter(map -> ObjectUtils.isEmpty(map.get("APPROVALFLAG")) || !new BigDecimal("1").equals(new BigDecimal(String.valueOf(map.get("APPROVALFLAG"))))).collect(Collectors.toList());
                    if (tasks.size() > 1) {
                        R result = new R();
                        result.put(CHOOSE_APPROVER, (Object)DISABLE);
                        R r = result;
                        return r;
                    }
                    todoTask = new HashMap<String, Object>((Map)tasks.get(0));
                }
                ProcessDTO processDTO = new ProcessDTO();
                processDTO.setBizcode(bizCode);
                processDO = this.vaWorkflowProcessService.get(processDTO);
                if (processDO == null) {
                    R result = new R();
                    result.put(CHOOSE_APPROVER, (Object)DISABLE);
                    taskList = result;
                    return taskList;
                }
                String bizType = processDO.getBizmodule();
                BigDecimal defineVersion = processDO.getDefineversion();
                workflowVariables = this.workflowParamService.loadWorkflowVariables(bizCode, bizDefine, defineVersion, bizType, definekey = processDO.getDefinekey());
                if (workflowVariables != null) {
                    if (workflowDTO.getWorkflowVariables() != null) {
                        workflowVariables.putAll(workflowDTO.getWorkflowVariables());
                    }
                    workflowDTO.setWorkflowVariables(workflowVariables);
                }
                VaWorkflowUtils.calProcessParam(workflowVariables, workflowModel);
                String nextNodeCode = workflowDTO.getNextNodeCode();
                nextNode = StringUtils.hasText(nextNodeCode) ? this.getNextJsonNode(nextNodeCode, arrayNode) : (Integer.parseInt(String.valueOf(todoParamMap.get("rejectskipflag"))) == 1 ? this.getNextJsonNode(String.valueOf(todoParamMap.get("rejectnodeid")), arrayNode) : VaWorkflowNodeUtils.getNextNode(nodeCode, arrayNode));
            } else {
                workflowVariables = workflowDTO.getWorkflowVariables();
                VaWorkflowUtils.calProcessParam(workflowVariables, workflowModel);
                String rejectNodeId = "";
                if (!ObjectUtils.isEmpty(workflowDTO.isRejectSkipNode()) && workflowDTO.isRejectSkipNode()) {
                    Object submitNodeCode = "";
                    for (JsonNode jsonNode : arrayNode) {
                        String nodeType = jsonNode.get("stencil").get("id").asText();
                        if (!"StartNoneEvent".equals(nodeType)) continue;
                        submitNodeCode = VaWorkflowNodeUtils.getNextNode(jsonNode.get("resourceId").asText(), arrayNode).get("resourceId").asText();
                        break;
                    }
                    ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
                    processRejectNodeDO.setBizcode(bizCode);
                    processRejectNodeDO.setProcessdefinekey(uniqueCode);
                    processRejectNodeDO.setProcessdefineversion(BigDecimal.valueOf(processDefineVersion));
                    processRejectNodeDO.setBerejectednodecode((String)submitNodeCode);
                    processRejectNodeDO.setSubprocessbranch(workflowDTO.getSubProcessBranch());
                    ProcessRejectNodeDO rejectNodeInfo = this.workflowProcessRejectNodeService.getRejectNodeInfo(processRejectNodeDO);
                    if (rejectNodeInfo != null) {
                        rejectNodeId = rejectNodeInfo.getRejectnodecode();
                    }
                }
                if (StringUtils.hasText(rejectNodeId)) {
                    nextNode = this.getNextJsonNode(rejectNodeId, arrayNode);
                } else if (StringUtils.hasText(workflowDTO.getNextNodeCode())) {
                    nextNode = this.getNextJsonNode(workflowDTO.getNextNodeCode(), arrayNode);
                } else {
                    for (JsonNode jsonNode : arrayNode) {
                        String nodeType = jsonNode.get("stencil").get("id").asText();
                        if (!"StartNoneEvent".equals(nodeType)) continue;
                        nextNode = VaWorkflowNodeUtils.getNextNode(jsonNode.get("resourceId").asText(), arrayNode);
                        break;
                    }
                    nextNode = VaWorkflowNodeUtils.getNextNode(nextNode.get("resourceId").asText(), arrayNode);
                }
            }
            ArrayNode copySubProcessNodes = null;
            if (nextNode != null && "SubProcess".equals(nextNode.get("stencil").get("id").asText()) && "None".equals(multiinstanceType = (properties = nextNode.get("properties")).get("multiinstance_type").asText())) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode sequenceFlowArrayNode = mapper.createArrayNode();
                for (JsonNode jsonNode : arrayNode) {
                    String nodeType = jsonNode.get("stencil").get("id").asText();
                    if (!"SequenceFlow".equals(nodeType)) continue;
                    sequenceFlowArrayNode.add(jsonNode);
                }
                ArrayNode subProcessNodes = (ArrayNode)nextNode.get("childShapes");
                copySubProcessNodes = subProcessNodes.deepCopy();
                copySubProcessNodes.addAll(sequenceFlowArrayNode);
                for (JsonNode subProcessNode : copySubProcessNodes) {
                    String nodeType = subProcessNode.get("stencil").get("id").asText();
                    if (!"StartNoneEvent".equals(nodeType)) continue;
                    nextNode = VaWorkflowNodeUtils.getNextNode(subProcessNode.get("resourceId").asText(), copySubProcessNodes);
                    break;
                }
            }
            JsonNode modelNode = processDesignPluginDefine.getData();
            if (nextNode != null) {
                try {
                    List<ForwardProcessNode> forwardProcess = this.getForwardProcessNodes(workflowDTO, (ArrayNode)modelNode.get("childShapes"));
                    List latestProcessNodes = this.workflowProcessNodeService.getLatestProcessNodes(processInstanceId, bizCode, nodeCode, subProcessBranch);
                    NextNodeContext context = new NextNodeContext(modelNode, arrayNode, workflowDTO, processDO, todoTask, forwardProcess, latestProcessNodes);
                    nextNode = this.getRealNextNode(nextNode, copySubProcessNodes, context);
                }
                catch (Exception e) {
                    log.error("\u8ba1\u7b97\u4e0b\u4e00\u8282\u70b9\u5f02\u5e38", e);
                    nextNode = null;
                }
            }
            Map<String, Object> processParams = this.getParticipantStrategyParam(workflowDTO, processDO);
            R r = this.getNodeConfig(nextNode, arrayNode, modelNode, processParams, workflowDTO);
            return r;
        }
        catch (Exception e) {
            R r;
            log.error(e.getMessage(), e);
            if (nextNode == null || nextNode.get("properties") == null) {
                r = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.failedtogettheapprover"));
                return r;
            }
            r = R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.failedtogetthenextnode") + nextNode.get("properties").get("name").asText()));
            return r;
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
    }

    private JsonNode getRealNextNode(JsonNode nextNode, ArrayNode subProcessNodes, NextNodeContext context) {
        boolean nextNodeCounterSignFlag;
        if (nextNode == null) {
            return null;
        }
        JsonNode defineData = context.getDefineData();
        AutoAgreeConfig autoAgreeConfig = this.getAutoAgreeConfig(nextNode, defineData);
        boolean autoAgree = autoAgreeConfig.isAutoAgree();
        if (!autoAgree) {
            return nextNode;
        }
        JsonNode nextNodeParticipantStrategyConfig = this.getNextNodeParticipantStrategyConfig(nextNode);
        WorkflowDTO workflowDTO = context.getWorkflowDTO();
        ProcessDO processDO = context.getProcessDO();
        Map<String, Object> strategyParam = this.getParticipantStrategyParam(workflowDTO, processDO);
        Map<String, Object> todoTask = context.getTodoTask();
        Map<String, Object> delegateParam = this.getDelegateParam(workflowDTO, todoTask);
        Set<String> nextNodeApprovers = this.getNextNodeApprovers(nextNode, nextNodeParticipantStrategyConfig, strategyParam, delegateParam);
        boolean calculateTheNextNodeAgain = nextNodeCounterSignFlag = this.isNextNodeCounterSignFlag(nextNode);
        AutoAgreeOption autoAgreeOption = autoAgreeConfig.getAutoAgreeOption();
        for (String nextNodeApprover : nextNodeApprovers) {
            boolean autoAgreeFlag = this.canAutoAgree(autoAgreeOption, nextNodeApprover, context);
            if (autoAgreeFlag && !nextNodeCounterSignFlag) {
                calculateTheNextNodeAgain = true;
                break;
            }
            if (autoAgreeFlag || !nextNodeCounterSignFlag) continue;
            calculateTheNextNodeAgain = false;
            break;
        }
        if (calculateTheNextNodeAgain) {
            ArrayNode arrayNode = context.getArrayNode();
            if ("SubProcess".equals(nextNode.get("stencil").get("id").asText()) && !nextNodeCounterSignFlag) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode sequenceFlowArrayNode = mapper.createArrayNode();
                for (JsonNode jsonNode : arrayNode) {
                    String nodeType = jsonNode.get("stencil").get("id").asText();
                    if (!"SequenceFlow".equals(nodeType)) continue;
                    sequenceFlowArrayNode.add(jsonNode);
                }
                ArrayNode currentSubProcessNodes = (ArrayNode)nextNode.get("childShapes");
                subProcessNodes = currentSubProcessNodes.deepCopy();
                subProcessNodes.addAll(sequenceFlowArrayNode);
                for (JsonNode subProcessNode : subProcessNodes) {
                    String nodeType = subProcessNode.get("stencil").get("id").asText();
                    if (!"StartNoneEvent".equals(nodeType)) continue;
                    nextNode = VaWorkflowNodeUtils.getNextNode(subProcessNode.get("resourceId").asText(), subProcessNodes);
                    break;
                }
            } else {
                nextNode = ObjectUtils.isEmpty(subProcessNodes) ? VaWorkflowNodeUtils.getNextNode(nextNode.get("resourceId").asText(), arrayNode) : VaWorkflowNodeUtils.getNextNode(nextNode.get("resourceId").asText(), subProcessNodes);
            }
            return this.getRealNextNode(nextNode, subProcessNodes, context);
        }
        return nextNode;
    }

    private AutoAgreeConfig getAutoAgreeConfig(JsonNode nextNode, JsonNode processData) {
        if ("UserTask".equals(nextNode.get("stencil").get("id").asText())) {
            boolean chooseApprover = this.isChooseApprover(nextNode, processData);
            if (chooseApprover) {
                return new AutoAgreeConfig(false, null);
            }
            return this.workflowInfoService.getAutoAgreeConfig(processData, nextNode);
        }
        return new AutoAgreeConfig(false, null);
    }

    private boolean isChooseApprover(JsonNode nextNode, JsonNode processData) {
        String chooseapprover = nextNode.get("properties").get("chooseapprover").asText();
        if (Objects.equals(chooseapprover, "inherit")) {
            JsonNode globalChooseApprovalNode = processData.get("globleChooseApprover");
            chooseapprover = globalChooseApprovalNode == null ? "" : globalChooseApprovalNode.asText();
        }
        return !Objects.equals(chooseapprover, DISABLE);
    }

    private boolean isNextNodeCounterSignFlag(JsonNode nextNode) {
        JsonNode nextProperties = nextNode.get("properties");
        String multiInstanceType = nextProperties.get("multiinstance_type").asText();
        return !"None".equals(multiInstanceType);
    }

    private JsonNode getNextNodeParticipantStrategyConfig(JsonNode nextNode) {
        JsonNode nextProperties = nextNode.get("properties");
        if (this.isNextNodeCounterSignFlag(nextNode)) {
            String multiInstance = nextProperties.get("multiinstance_collection").asText();
            ObjectMapper nextObjectMapper = new ObjectMapper();
            try {
                return nextObjectMapper.readTree(multiInstance);
            }
            catch (JsonProcessingException e) {
                String nextNodeName = nextProperties.get("name").asText();
                throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.failedtogetthenextnode") + nextNodeName);
            }
        }
        return nextProperties.get("usertaskassignment");
    }

    private Map<String, Object> getParticipantStrategyParam(WorkflowDTO workflowDTO, ProcessDO processDO) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("variables", workflowDTO.getWorkflowVariables());
        params.put("processDO", processDO);
        params.put("workflowDTO", workflowDTO);
        return params;
    }

    private Map<String, Object> getDelegateParam(WorkflowDTO workflowDTO, Map<String, Object> todoTask) {
        HashMap<String, Object> variables = new HashMap<String, Object>();
        if (StringUtils.hasText(workflowDTO.getNodeCode()) && ObjectUtils.isEmpty(todoTask)) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setBizCode(workflowDTO.getBizCode());
            taskDTO.setBackendRequest(true);
            PageVO pageVO = this.todoClient.listUnfinished(taskDTO);
            todoTask = (Map)pageVO.getRows().get(0);
            variables.putAll(todoTask);
        }
        return variables;
    }

    private Set<String> getNextNodeApprovers(JsonNode nextNode, JsonNode nextNodeParticipantStrategyConfig, Map<String, Object> strategyParam, Map<String, Object> delegateParam) {
        HashSet<String> allUsers = new HashSet<String>();
        for (JsonNode assignmentNode : nextNodeParticipantStrategyConfig) {
            try {
                String strategyModuleName = assignmentNode.get("strategyModuleName").asText();
                String strategyName = assignmentNode.get("strategyName").asText();
                strategyParam.put("assignParam", assignmentNode.get("items"));
                strategyParam.put("currentNodeCode", nextNode.get("resourceId").asText());
                Set users = this.workflowStrategySevice.execute(strategyModuleName, strategyName, strategyParam);
                for (String userId : users) {
                    List delegates = this.delegateService.getDelegate(userId, delegateParam);
                    for (DelegateDTO delegate : delegates) {
                        if (delegate.getAgentusers() == null) continue;
                        allUsers.addAll(delegate.getAgentusers());
                    }
                }
                allUsers.addAll(users);
            }
            catch (Exception e) {
                log.error("\u83b7\u53d6\u5ba1\u6279\u4eba\u5931\u8d25", e);
            }
        }
        return allUsers;
    }

    private List<ForwardProcessNode> getForwardProcessNodes(WorkflowDTO workflowDTO, ArrayNode defineData) {
        List<Object> forwardProcess = new ArrayList<ForwardProcessNode>();
        if (StringUtils.hasText(workflowDTO.getNodeCode())) {
            ForwardProcessDTO forwardProcessDTO = new ForwardProcessDTO();
            forwardProcessDTO.setBizCode(workflowDTO.getBizCode());
            forwardProcessDTO.setSubProcessBranch(workflowDTO.getSubProcessBranch());
            forwardProcessDTO.setWorkflowDefineData(defineData);
            forwardProcessDTO.setCalculatePendingNode(true);
            try {
                forwardProcess = this.forwardProcessService.getForwardProcess(forwardProcessDTO);
            }
            catch (Exception e) {
                log.error("\u8ba1\u7b97\u6b63\u5411\u8f68\u8ff9\u5931\u8d25", e);
                return Collections.emptyList();
            }
        }
        return forwardProcess;
    }

    private boolean canAutoAgree(AutoAgreeOption autoAgreeOption, String nextNodeApprover, NextNodeContext context) {
        boolean executeOldAutoAgreeLogic = autoAgreeOption == null;
        Set<String> autoAgreeUserCache = this.getAutoAgreeUserCache();
        if (executeOldAutoAgreeLogic || autoAgreeOption.isSameAsSubmitter()) {
            String submitter;
            ProcessDO processDO = context.getProcessDO();
            String string = submitter = processDO == null ? ShiroUtil.getUser().getId() : processDO.getStartuser();
            if (nextNodeApprover.equals(submitter)) {
                autoAgreeUserCache.add(nextNodeApprover);
                return true;
            }
        }
        if (!executeOldAutoAgreeLogic && autoAgreeOption.isCustomCondition()) {
            String condition = JSONUtil.toJSONString((Object)autoAgreeOption.getCustomConditionFormula());
            Map workflowVariables = context.getWorkflowDTO().getWorkflowVariables();
            boolean judge = this.workflowFormulaSevice.judge(null, null, workflowVariables, condition);
            if (judge) {
                autoAgreeUserCache.add(nextNodeApprover);
                return true;
            }
        }
        if (executeOldAutoAgreeLogic || autoAgreeOption.isRepeat() || autoAgreeOption.isRepeatAndContinuous()) {
            if (autoAgreeUserCache.contains(nextNodeApprover)) {
                return true;
            }
            boolean repeatApprove = this.handleRepeatApprove(autoAgreeOption, nextNodeApprover, context, executeOldAutoAgreeLogic);
            if (repeatApprove) {
                autoAgreeUserCache.add(nextNodeApprover);
                return true;
            }
        }
        return false;
    }

    private Set<String> getAutoAgreeUserCache() {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        return (Set)customParam.computeIfAbsent("autoAgreeUserCache", k -> new HashSet());
    }

    private boolean handleRepeatApprove(AutoAgreeOption autoAgreeOption, String nextNodeApprover, NextNodeContext context, boolean executeOldAutoAgreeLogic) {
        if (nextNodeApprover.equals(ShiroUtil.getUser().getId())) {
            return true;
        }
        if (executeOldAutoAgreeLogic || autoAgreeOption.isRepeat()) {
            return this.hasApproved(nextNodeApprover, context.getForwardProcess());
        }
        return this.hasContinuousApproved(nextNodeApprover, context.getLatestProcessNodes());
    }

    private boolean hasApproved(String nextNodeApprover, List<ForwardProcessNode> forwardProcess) {
        for (ForwardProcessNode forwardProcessNode : forwardProcess) {
            List processNodeDOList;
            if (forwardProcessNode.isRejectNode()) continue;
            if (!CollectionUtils.isEmpty(forwardProcessNode.getPgwNodeMap())) {
                Map pgwNodeMap = forwardProcessNode.getPgwNodeMap();
                for (List forwardProcessNodes : pgwNodeMap.values()) {
                    if (!this.hasApproved(nextNodeApprover, forwardProcessNodes)) continue;
                    return true;
                }
            }
            if (!CollectionUtils.isEmpty(forwardProcessNode.getSubProcessNodeMap())) {
                Map subProcessNodeMap = forwardProcessNode.getSubProcessNodeMap();
                for (List forwardProcessNodes : subProcessNodeMap.values()) {
                    if (!this.hasApproved(nextNodeApprover, forwardProcessNodes)) continue;
                    return true;
                }
            }
            if (CollectionUtils.isEmpty(processNodeDOList = forwardProcessNode.getProcessNodeDOList())) continue;
            for (ProcessNodeDO processNodeDO : processNodeDOList) {
                if (!nextNodeApprover.equals(processNodeDO.getCompleteuserid()) || processNodeDO.getCompletetime() == null) continue;
                return true;
            }
        }
        return false;
    }

    private boolean hasContinuousApproved(String userId, List<ProcessNodeDO> latestProcessNodes) {
        for (ProcessNodeDO node : latestProcessNodes) {
            if (!Objects.equals(node.getCompleteuserid(), userId) || node.getCompletetime() == null) continue;
            return true;
        }
        return false;
    }

    private R getNodeConfig(JsonNode node, ArrayNode arrayNode, JsonNode modelNode, Map<String, Object> processParams, WorkflowDTO workflowDTO) {
        JsonNode userNode;
        boolean globalSubmitterCannotApprove;
        R result = new R();
        JsonNode globleChooseApproverNode = modelNode.get("globleChooseApprover");
        JsonNode globalSubmitterCannotApproveNode = modelNode.get("globalSubmitterCannotApprove");
        String globleChooseApprover = globleChooseApproverNode != null ? globleChooseApproverNode.asText() : DISABLE;
        boolean bl = globalSubmitterCannotApprove = globalSubmitterCannotApproveNode != null && globalSubmitterCannotApproveNode.asBoolean();
        if (node == null || !"UserTask".equals(node.get("stencil").get("id").asText())) {
            result.put(CHOOSE_APPROVER, (Object)DISABLE);
            return result;
        }
        JsonNode properties = node.get("properties");
        String multiinstanceType = properties.get("multiinstance_type").asText();
        if (!"None".equals(multiinstanceType)) {
            String multiinstance = properties.get("multiinstance_collection").asText();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                userNode = objectMapper.readTree(multiinstance);
            }
            catch (JsonProcessingException e) {
                return R.error((String)(VaWorkFlowI18nUtils.getInfo("va.workflow.failedtogetthenextnode") + node.get("properties").get("name").asText()));
            }
        } else {
            userNode = properties.get("usertaskassignment");
        }
        HashMap<String, UserDO> userCache = new HashMap<String, UserDO>();
        HashSet allUsersSet = new HashSet();
        for (JsonNode assignmentNode : userNode) {
            try {
                String strategyModuleName = assignmentNode.get("strategyModuleName").asText();
                String strategyName = assignmentNode.get("strategyName").asText();
                processParams.put("assignParam", assignmentNode.get("items"));
                processParams.put("currentNodeCode", node.get("resourceId").asText());
                allUsersSet.addAll(this.workflowStrategySevice.execute(strategyModuleName, strategyName, processParams));
            }
            catch (Exception e) {
                log.error("\u83b7\u53d6\u5ba1\u6279\u4eba\u5931\u8d25", e);
            }
        }
        boolean isSubmitterCannotApprove = VaWorkflowUtils.isSubmitterCannotApprove(globalSubmitterCannotApprove, properties);
        ProcessDO processDO = (ProcessDO)processParams.get("processDO");
        String submitUserId = processDO == null ? ShiroUtil.getUser().getId() : processDO.getStartuser();
        HashSet<String> allUsers = new HashSet<String>();
        for (String userId : allUsersSet) {
            if (isSubmitterCannotApprove && userId.equals(submitUserId)) continue;
            allUsers.add(VaWorkFlowDataUtils.getUserDOWithOrgInfo(userId, userCache).getName());
        }
        if (allUsers.isEmpty()) {
            boolean skipEmptyParticipant = false;
            JsonNode skipEmptyParticipantNode = properties.get("skipemptyparticipant");
            if (skipEmptyParticipantNode != null && skipEmptyParticipantNode.asBoolean()) {
                skipEmptyParticipant = true;
            }
            if (skipEmptyParticipant) {
                JsonNode nextNode = VaWorkflowNodeUtils.getNextNode(node.get("resourceId").asText(), arrayNode);
                return this.getNodeConfig(nextNode, arrayNode, modelNode, processParams, workflowDTO);
            }
            result.put(CHOOSE_APPROVER, (Object)DISABLE);
            return result;
        }
        JsonNode chooseApproverConfig = properties.get("chooseapprover");
        String chooseApprover = "";
        if (chooseApproverConfig != null) {
            chooseApprover = chooseApproverConfig.asText();
        }
        if (DISABLE.equals(chooseApprover) || !StringUtils.hasText(chooseApprover)) {
            result.put(CHOOSE_APPROVER, (Object)DISABLE);
            return result;
        }
        if ("inherit".equals(chooseApprover)) {
            chooseApprover = globleChooseApprover;
        }
        if (DISABLE.equals(chooseApprover)) {
            result.put(CHOOSE_APPROVER, (Object)DISABLE);
            return result;
        }
        result.put(CHOOSE_APPROVER, (Object)chooseApprover);
        result.put(NODE_NAME, (Object)properties.get("name"));
        if ("single".equals(chooseApprover)) {
            ArrayList<UserDO> users = new ArrayList<UserDO>();
            String nodeCode = node.get("resourceId").asText();
            ProcessRejectNodeDO processRejectNodeDO = new ProcessRejectNodeDO();
            processRejectNodeDO.setBizcode(workflowDTO.getBizCode());
            processRejectNodeDO.setRejectnodecode(nodeCode);
            String subProcessBranch = workflowDTO.getSubProcessBranch();
            processRejectNodeDO.setSubprocessbranch(subProcessBranch);
            ProcessRejectNodeDO rejectNodeInfo = this.workflowProcessRejectNodeService.getRejectNodeInfo(processRejectNodeDO);
            if (!ObjectUtils.isEmpty(rejectNodeInfo)) {
                ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
                processNodeDTO.setBizcode(workflowDTO.getBizCode());
                List processNodeDOS = this.workflowProcessNodeService.listProcessNode(processNodeDTO);
                List latestProcessNodes = this.workflowProcessNodeService.getLatestProcessNodes(nodeCode, processNodeDOS, subProcessBranch);
                Collections.reverse(processNodeDOS);
                for (ProcessNodeDO processNodeDO : processNodeDOS) {
                    UserDO userDO;
                    if (!nodeCode.equals(processNodeDO.getNodecode()) || !"\u5ba1\u6279\u9a73\u56de".equals(processNodeDO.getCompleteresult())) continue;
                    BigDecimal completeusertype = processNodeDO.getCompleteusertype();
                    String completeuserid = processNodeDO.getCompleteuserid();
                    if (BigDecimal.ONE.equals(completeusertype)) {
                        completeuserid = VaWorkflowUtils.getOriginalApproverByPlus(processNodeDO, latestProcessNodes);
                    }
                    if (ObjectUtils.isEmpty(userDO = (UserDO)userCache.get(completeuserid)) || !ObjectUtils.isEmpty(userDO.getStopflag()) && userDO.getStopflag() != 0) continue;
                    users.add(userDO);
                    result.put("userCache", users);
                    return result;
                }
            }
        }
        result.put("newNextNodeCode", (Object)node.get("resourceId").asText());
        result.put("workflowDefineKey", (Object)workflowDTO.getUniqueCode());
        result.put("userCache", this.getUserCacheByCommonUser(userCache, node, workflowDTO));
        return result;
    }

    private List<UserDO> getUserCacheByCommonUser(Map<String, UserDO> userCache, JsonNode node, WorkflowDTO workflowDTO) {
        ArrayList<UserDO> userCacheList = new ArrayList<UserDO>();
        ArrayList<UserDO> users = new ArrayList<UserDO>();
        int index = 0;
        for (String userid : userCache.keySet()) {
            UserDO userDO = userCache.get(userid);
            userDO.addExtInfo("inTop", (Object)false);
            userDO.addExtInfo("originalIndex", (Object)index++);
            users.add(userDO);
        }
        Set userIds = users.stream().map(UserDO::getId).collect(Collectors.toSet());
        log.info("\u9009\u62e9\u5ba1\u6279\u4eba\u5355\u636e\u7f16\u53f7\uff1a{},\u8282\u70b9\u6807\u8bc6\uff1a{}\uff0c\u8282\u70b9\u540d\u79f0\uff1a{}, \u5ba1\u6279\u4eba\uff1a{}", workflowDTO.getBizCode(), node.get("resourceId"), node.get("properties").get("name"), String.join((CharSequence)",", userIds));
        WorkflowCommonUserDTO workflowCommonUserDTO = new WorkflowCommonUserDTO();
        workflowCommonUserDTO.setWorkflowDefineKey(workflowDTO.getUniqueCode());
        workflowCommonUserDTO.setNodeCode(node.get("resourceId").asText());
        List<WorkflowCommonUserDO> workflowCommonUserDOList = this.workflowSelectApproverService.list(workflowCommonUserDTO);
        List workflowCommonUserIdList = workflowCommonUserDOList.stream().map(WorkflowCommonUserDO::getCommonUserId).filter(userIds::contains).collect(Collectors.toList());
        for (String commonUserId : workflowCommonUserIdList) {
            UserDO userDO = userCache.get(commonUserId);
            userDO.addExtInfo("inTop", (Object)true);
            userCacheList.add(userDO);
        }
        for (UserDO user : users) {
            if (workflowCommonUserIdList.contains(user.getId())) continue;
            userCacheList.add(user);
        }
        return userCacheList;
    }

    public R getNextNodeConfig(WorkflowDTO workflowDTO) {
        String bizCode = workflowDTO.getBizCode();
        String currNodeCode = workflowDTO.getNodeCode();
        if (!StringUtils.hasText(bizCode) || !StringUtils.hasText(currNodeCode)) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        R result = new R();
        result.putAll(this.vaWorkflowNodeConfigService.getNodeProperties(workflowDTO, true));
        return result;
    }

    public static class NextNodeContext {
        private final JsonNode defineData;
        private final ArrayNode arrayNode;
        private final WorkflowDTO workflowDTO;
        private final ProcessDO processDO;
        private final Map<String, Object> todoTask;
        private final List<ProcessNodeDO> latestProcessNodes;
        private final List<ForwardProcessNode> forwardProcess;

        public NextNodeContext(JsonNode defineData, ArrayNode arrayNode, WorkflowDTO workflowDTO, ProcessDO processDO, Map<String, Object> todoTask, List<ForwardProcessNode> forwardProcess, List<ProcessNodeDO> latestProcessNodes) {
            this.defineData = defineData;
            this.arrayNode = arrayNode;
            this.workflowDTO = workflowDTO;
            this.processDO = processDO;
            this.todoTask = todoTask;
            this.forwardProcess = forwardProcess;
            this.latestProcessNodes = latestProcessNodes;
        }

        public JsonNode getDefineData() {
            return this.defineData;
        }

        public ArrayNode getArrayNode() {
            return this.arrayNode;
        }

        public WorkflowDTO getWorkflowDTO() {
            return this.workflowDTO;
        }

        public ProcessDO getProcessDO() {
            return this.processDO;
        }

        public Map<String, Object> getTodoTask() {
            return this.todoTask;
        }

        public List<ProcessNodeDO> getLatestProcessNodes() {
            return this.latestProcessNodes;
        }

        public List<ForwardProcessNode> getForwardProcess() {
            return this.forwardProcess;
        }
    }
}

