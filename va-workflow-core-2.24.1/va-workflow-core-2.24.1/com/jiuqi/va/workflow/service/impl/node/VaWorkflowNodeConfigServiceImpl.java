/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.workflow.NodeRejectType
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.node.NodePropertyType
 *  com.jiuqi.va.domain.workflow.node.WorkflowNodeConfigDTO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice
 *  com.jiuqi.va.domain.workflow.service.node.VaWorkflowNodeConfigService
 */
package com.jiuqi.va.workflow.service.impl.node;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.workflow.NodeRejectType;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.node.NodePropertyType;
import com.jiuqi.va.domain.workflow.node.WorkflowNodeConfigDTO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice;
import com.jiuqi.va.domain.workflow.service.node.VaWorkflowNodeConfigService;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowNodeUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class VaWorkflowNodeConfigServiceImpl
implements VaWorkflowNodeConfigService {
    private static final Logger log = LoggerFactory.getLogger(VaWorkflowNodeConfigServiceImpl.class);
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private WorkflowStrategySevice workflowStrategySevice;
    @Autowired
    private VaWorkflowProcessService vaWorkflowProcessService;
    private static final List<String> ignoreFields = Arrays.asList("workflowNodes", "servicetaskclass", "prioritydefinition", "servicetaskresultvariable", "rejectforskipnode", "rejectimage", "rejectterminateprocess", "selectnextapprovalnode", "multiinstance_collection", "multiinstance_variable", "chooseapprover", "skipemptyparticipant", "todotransfer", "forbidemail", "resourceid", "usertaskassignment", "reapprovalstrategyforskipnode", "approvalmode", "reviewmodeconfig", "deadlineaudit", "rejectcommentnotrequired", "carboncopy", "approvalcommentrequireconfig", "skipemptybranchstrategy", "limitapprover", "closepageafterapprove", "donotgeneratetodotask");

    public Map<String, Object> getNodeConfig(WorkflowNodeConfigDTO workflowNodeConfigDTO) {
        JsonNode properties;
        String uniqueCode = workflowNodeConfigDTO.getUniqueCode();
        String nodeCode = workflowNodeConfigDTO.getNodeCode();
        boolean submitNodeFlag = workflowNodeConfigDTO.isSubmitNodeFlag();
        if (!StringUtils.hasText(uniqueCode) || !StringUtils.hasText(nodeCode) && !submitNodeFlag) {
            return Collections.emptyMap();
        }
        Long version = workflowNodeConfigDTO.getVersion();
        WorkflowModel workflowModel = this.workflowParamService.getModel(uniqueCode, version);
        JsonNode pluginDefineData = this.workflowParamService.getWorkflowProcess(workflowModel);
        ArrayList<NodePropertyType> propertyTypes = workflowNodeConfigDTO.getPropertyTypes();
        if (CollectionUtils.isEmpty(propertyTypes)) {
            propertyTypes = new ArrayList<NodePropertyType>(1);
            propertyTypes.add(NodePropertyType.ALL);
            workflowNodeConfigDTO.setPropertyTypes(propertyTypes);
        }
        HashMap<String, Object> nodeProperties = new HashMap<String, Object>();
        this.getGlobalExtendProperties(pluginDefineData, workflowNodeConfigDTO, nodeProperties);
        ArrayNode defineDataNode = (ArrayNode)pluginDefineData.get("childShapes");
        this.getGlobalProperties(defineDataNode, nodeProperties);
        if (submitNodeFlag) {
            List workflowNodes = (List)nodeProperties.get("workflowNodes");
            nodeCode = (String)((Map)workflowNodes.get(0)).get("nodeId");
        }
        if ((properties = this.getNodeProperties(defineDataNode, nodeCode)) == null) {
            return nodeProperties;
        }
        this.getGeneralProperties(properties, workflowNodeConfigDTO, nodeProperties);
        this.getAdvancedProperties(properties, workflowNodeConfigDTO, nodeProperties);
        this.getExtendProperties(properties, workflowNodeConfigDTO, nodeProperties);
        nodeProperties.remove("workflowNodes");
        return nodeProperties;
    }

    private void getGlobalProperties(ArrayNode defineDataNode, Map<String, Object> nodeProperties) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode sequenceFlowArrayNode = mapper.createArrayNode();
        for (Object jsonNode : defineDataNode) {
            String nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"SequenceFlow".equals(nodeType)) continue;
            sequenceFlowArrayNode.add((JsonNode)jsonNode);
        }
        List<Object> workflowNodes = new ArrayList();
        for (JsonNode jsonNode : defineDataNode) {
            String nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(nodeType)) continue;
            ArrayNode outGoings = (ArrayNode)jsonNode.get("outgoing");
            VaWorkflowNodeUtils.getNodes(outGoings, defineDataNode, sequenceFlowArrayNode, workflowNodes);
            break;
        }
        workflowNodes = workflowNodes.stream().distinct().collect(Collectors.toList());
        HashMap<String, String> nodeNameMap = new HashMap<String, String>();
        for (JsonNode jsonNode : defineDataNode) {
            String nodeType;
            JsonNode stencil = jsonNode.get("stencil");
            String stencilId = stencil.get("id").asText();
            String string = nodeType = stencil.get("nodeType") == null ? "" : stencil.get("nodeType").asText();
            if (!stencilId.equals("UserTask") && !stencilId.equals("ParallelGateway") && !"Manual".equals(nodeType)) continue;
            String resourceId = jsonNode.get("resourceId").asText();
            String name = jsonNode.get("properties").get("name").asText();
            nodeNameMap.put(resourceId, name);
        }
        workflowNodes.forEach(e -> e.put("nodeName", nodeNameMap.get(e.get("nodeId"))));
        nodeProperties.put("workflowNodes", workflowNodes);
    }

    private JsonNode getNodeProperties(ArrayNode defineDataNode, String nodeCode) {
        JsonNode properties = null;
        block0: for (JsonNode jsonNode : defineDataNode) {
            String resourceId = jsonNode.get("resourceId").asText();
            if (nodeCode.equals(resourceId)) {
                properties = jsonNode.get("properties");
                break;
            }
            JsonNode childShapes = jsonNode.get("childShapes");
            if (ObjectUtils.isEmpty(childShapes)) continue;
            for (JsonNode childShape : childShapes) {
                String childResourceId = childShape.get("resourceId").asText();
                if (!nodeCode.equals(childResourceId)) continue;
                properties = childShape.get("properties");
                continue block0;
            }
        }
        return properties;
    }

    private void getGlobalExtendProperties(JsonNode pluginDefineData, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        JsonNode globalExtendPropertiesNode = pluginDefineData.get("globalExtendProperties");
        if (globalExtendPropertiesNode == null) {
            return;
        }
        List propertyTypes = workflowNodeConfigDTO.getPropertyTypes();
        if (!propertyTypes.contains(NodePropertyType.ALL) && !propertyTypes.contains(NodePropertyType.GLOBAL_EXTEND)) {
            return;
        }
        Map globalExtendProperties = ObjectUtils.isEmpty(globalExtendPropertiesNode) ? new HashMap() : JSONUtil.parseMap((String)globalExtendPropertiesNode.toString());
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName)) {
            nodeProperties.put(propertyName, globalExtendProperties.get(propertyName));
        } else {
            nodeProperties.put("globalExtendProperties", globalExtendProperties);
        }
    }

    private void getGeneralProperties(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        List propertyTypes = workflowNodeConfigDTO.getPropertyTypes();
        if (!propertyTypes.contains(NodePropertyType.ALL) && !propertyTypes.contains(NodePropertyType.GENERAL)) {
            return;
        }
        this.getPlusapprovalmap(properties, workflowNodeConfigDTO, nodeProperties);
        this.getRejectforSkipNode(properties, workflowNodeConfigDTO, nodeProperties);
        this.getChooseApprover(properties, workflowNodeConfigDTO, nodeProperties);
        this.getSelectNextApprovalNode(properties, workflowNodeConfigDTO, nodeProperties);
        this.getRejectType(properties, workflowNodeConfigDTO, nodeProperties);
        this.getRejectTerminateProcess(properties, workflowNodeConfigDTO, nodeProperties);
        this.getSkipEmptyParticipant(properties, workflowNodeConfigDTO, nodeProperties);
        this.getSkipemptybranchstrategy(properties, workflowNodeConfigDTO, nodeProperties);
        this.getCarboncopy(properties, workflowNodeConfigDTO, nodeProperties);
        this.getTodoTransfer(properties, workflowNodeConfigDTO, nodeProperties);
    }

    private void getAdvancedProperties(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        List propertyTypes = workflowNodeConfigDTO.getPropertyTypes();
        if (!propertyTypes.contains(NodePropertyType.ALL) && !propertyTypes.contains(NodePropertyType.ADVANCED)) {
            return;
        }
        this.getForbidemail(properties, workflowNodeConfigDTO, nodeProperties);
        this.getReApprovalStrategyForSkipNode(properties, workflowNodeConfigDTO, nodeProperties);
        this.getRejectCommentNotRequired(properties, workflowNodeConfigDTO, nodeProperties);
        this.getLimitapprover(properties, workflowNodeConfigDTO, nodeProperties);
        this.getApprovalMode(properties, workflowNodeConfigDTO, nodeProperties);
        this.getReviewModeConfig(properties, workflowNodeConfigDTO, nodeProperties);
        this.getDeadlineAuditConfig(properties, workflowNodeConfigDTO, nodeProperties);
        this.getApprovalCommentRequireConfig(properties, workflowNodeConfigDTO, nodeProperties);
        this.getClosePageAfterApprove(properties, workflowNodeConfigDTO, nodeProperties);
        this.getDoNotGenerateTodoTask(properties, workflowNodeConfigDTO, nodeProperties);
    }

    private void getExtendProperties(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        List propertyTypes = workflowNodeConfigDTO.getPropertyTypes();
        if (!propertyTypes.contains(NodePropertyType.ALL) && !propertyTypes.contains(NodePropertyType.EXTEND)) {
            return;
        }
        this.getRejectImage(properties, workflowNodeConfigDTO, nodeProperties);
        this.getOtherExtendProperties(properties, workflowNodeConfigDTO, nodeProperties);
    }

    private void getPlusapprovalmap(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("plusapproval", propertyName)) {
            return;
        }
        HashMap<String, Boolean> plusapprovalmap = new HashMap<String, Boolean>();
        JsonNode servicetaskclass = properties.get("servicetaskclass");
        if (servicetaskclass != null) {
            JsonNode plusapproval = servicetaskclass.get("plusapproval");
            JsonNode plusapprovaladvnoreject = servicetaskclass.get("plusapprovaladvnoreject");
            JsonNode plusapprovalagree = servicetaskclass.get("plusapprovalAgree");
            JsonNode waitplusapprovalcompleted = servicetaskclass.get("waitplusapprovalcompleted");
            JsonNode nonParticipantPlusApproverCanReject = servicetaskclass.get("nonParticipantPlusApproverCanReject");
            if (plusapproval != null && plusapprovaladvnoreject != null) {
                plusapprovalmap.put("plusapproval", plusapproval.asBoolean());
                plusapprovalmap.put("plusapprovaladvnoreject", plusapprovaladvnoreject.asBoolean());
                if (plusapprovalagree != null) {
                    plusapprovalmap.put("plusapprovalAgree", plusapprovalagree.asBoolean());
                }
                if (plusapproval.asBoolean() && waitplusapprovalcompleted != null) {
                    plusapprovalmap.put("waitplusapprovalcompleted", waitplusapprovalcompleted.asBoolean());
                } else {
                    plusapprovalmap.put("waitplusapprovalcompleted", false);
                }
                if (nonParticipantPlusApproverCanReject != null) {
                    plusapprovalmap.put("nonParticipantPlusApproverCanReject", nonParticipantPlusApproverCanReject.asBoolean());
                }
            }
        }
        nodeProperties.put("plusapproval", plusapprovalmap);
    }

    private void getRejectforSkipNode(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("rejectforSkipNode", propertyName)) {
            return;
        }
        boolean rejectforSkipNode = false;
        JsonNode rejectforskipnodeJson = properties.get("rejectforskipnode");
        if (rejectforskipnodeJson != null && rejectforskipnodeJson.asBoolean()) {
            rejectforSkipNode = true;
        }
        nodeProperties.put("rejectforSkipNode", rejectforSkipNode);
    }

    private void getChooseApprover(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("chooseApprover", propertyName)) {
            return;
        }
        String chooseApprover = "";
        JsonNode chooseApproverConfig = properties.get("chooseapprover");
        if (chooseApproverConfig != null) {
            chooseApprover = chooseApproverConfig.asText();
        }
        nodeProperties.put("chooseApprover", chooseApprover);
    }

    private void getSelectNextApprovalNode(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("selectNextApprovalNode", propertyName)) {
            return;
        }
        boolean selectNextApprovalNode = false;
        JsonNode selectNextApprovalNodeConfig = properties.get("selectnextapprovalnode");
        if (selectNextApprovalNodeConfig != null && selectNextApprovalNodeConfig.asBoolean()) {
            selectNextApprovalNode = true;
        }
        nodeProperties.put("selectNextApprovalNode", selectNextApprovalNode);
    }

    private void getRejectType(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("rejectType", propertyName)) {
            return;
        }
        String rejectType = null;
        ArrayList<String> rejectNodesList = new ArrayList<String>();
        JsonNode priorityDefinition = properties.get("prioritydefinition");
        if (priorityDefinition != null) {
            ArrayNode rejectNodeArrayNode;
            JsonNode rejectNodeParamJsonNode;
            String priorityDefinitions;
            rejectType = priorityDefinitions = priorityDefinition.asText();
            if ("3".equals(priorityDefinitions) && (rejectNodeParamJsonNode = properties.get("servicetaskresultvariable")) != null && Objects.nonNull(rejectNodeArrayNode = (ArrayNode)rejectNodeParamJsonNode.get("value"))) {
                for (JsonNode value : rejectNodeArrayNode) {
                    JsonNode id = value.get("id");
                    if (!Objects.nonNull(id)) continue;
                    rejectNodesList.add(value.get("id").asText());
                }
            }
        }
        nodeProperties.put("rejectType", rejectType);
        List workflowNodes = (List)nodeProperties.get("workflowNodes");
        List nodes = workflowNodes.stream().filter(e -> rejectNodesList.contains(e.get("nodeId"))).collect(Collectors.toList());
        Collections.reverse(nodes);
        nodeProperties.put("nodes", nodes);
        boolean directRejectSubmitBtn = String.valueOf(NodeRejectType.REJECT_TO_DESIGNATE.getValue()).equals(rejectType) && (rejectNodesList.contains(((Map)workflowNodes.get(0)).get("nodeId")) || CollectionUtils.isEmpty(rejectNodesList));
        nodeProperties.put("directRejectSubmitBtn", directRejectSubmitBtn);
    }

    private void getRejectTerminateProcess(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("rejectTerminateProcess", propertyName)) {
            return;
        }
        boolean rejectTerminateProcess = false;
        JsonNode rejectTerminateProcessNode = properties.get("rejectterminateprocess");
        if (rejectTerminateProcessNode != null && rejectTerminateProcessNode.asBoolean()) {
            rejectTerminateProcess = true;
        }
        nodeProperties.put("rejectTerminateProcess", rejectTerminateProcess);
    }

    private void getSkipEmptyParticipant(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("skipEmptyParticipant", propertyName)) {
            return;
        }
        boolean skipEmptyParticipant = false;
        JsonNode skipEmptyParticipantNode = properties.get("skipemptyparticipant");
        if (skipEmptyParticipantNode != null && skipEmptyParticipantNode.asBoolean()) {
            skipEmptyParticipant = true;
        }
        nodeProperties.put("skipEmptyParticipant", skipEmptyParticipant);
    }

    private void getSkipemptybranchstrategy(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("skipemptybranchstrategy", propertyName)) {
            return;
        }
        boolean skipEmptyBranchStrategy = false;
        JsonNode skipemptybranchstrategy = properties.get("skipemptybranchstrategy");
        if (skipemptybranchstrategy != null && skipemptybranchstrategy.asBoolean()) {
            skipEmptyBranchStrategy = true;
        }
        nodeProperties.put("skipemptybranchstrategy", skipEmptyBranchStrategy);
    }

    private void getCarboncopy(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("carboncopy", propertyName)) {
            return;
        }
        boolean carbonCopy = false;
        JsonNode carbonCopyNode = properties.get("carboncopy");
        if (carbonCopyNode != null && carbonCopyNode.asBoolean()) {
            carbonCopy = true;
        }
        nodeProperties.put("carboncopy", carbonCopy);
    }

    private void getTodoTransfer(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("todoTransfer", propertyName)) {
            return;
        }
        HashMap<String, Boolean> todoTransferMap = new HashMap<String, Boolean>(8);
        JsonNode todoTransferNode = properties.get("todotransfer");
        if (todoTransferNode != null) {
            String todoTransferStr = todoTransferNode.asText();
            if ("true".equals(todoTransferStr) || "false".equals(todoTransferStr) || !StringUtils.hasText(todoTransferStr)) {
                boolean todoTransferFlag = todoTransferNode.asBoolean();
                todoTransferMap.put("transferFlag", todoTransferFlag);
                todoTransferMap.put("approvalTransfer", todoTransferFlag);
                todoTransferMap.put("plusApprovalTransfer", todoTransferFlag);
                todoTransferMap.put("plusNotApprovalTransfer", todoTransferFlag);
            } else {
                Map map = JSONUtil.parseMap((String)todoTransferStr);
                todoTransferMap.put("transferFlag", Boolean.parseBoolean(String.valueOf(map.get("transferFlag"))));
                todoTransferMap.put("approvalTransfer", Boolean.parseBoolean(String.valueOf(map.get("approvalTransfer"))));
                todoTransferMap.put("plusApprovalTransfer", Boolean.parseBoolean(String.valueOf(map.get("plusApprovalTransfer"))));
                todoTransferMap.put("plusNotApprovalTransfer", Boolean.parseBoolean(String.valueOf(map.get("plusNotApprovalTransfer"))));
            }
        }
        nodeProperties.put("todoTransfer", todoTransferMap);
    }

    private void getForbidemail(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("forbidemail", propertyName)) {
            return;
        }
        boolean sendEmail = false;
        JsonNode sendEmailNode = properties.get("forbidemail");
        if (sendEmailNode != null && sendEmailNode.asBoolean()) {
            sendEmail = true;
        }
        nodeProperties.put("forbidemail", sendEmail);
    }

    private void getReApprovalStrategyForSkipNode(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("reApprovalStrategyForSkipNode", propertyName)) {
            return;
        }
        boolean reApprovalStrategyForSkipNode = false;
        JsonNode reApprovalStrategyForSkipNodeJsonNode = properties.get("reapprovalstrategyforskipnode");
        if (reApprovalStrategyForSkipNodeJsonNode != null && reApprovalStrategyForSkipNodeJsonNode.asBoolean()) {
            reApprovalStrategyForSkipNode = true;
        }
        nodeProperties.put("reApprovalStrategyForSkipNode", reApprovalStrategyForSkipNode);
    }

    private void getRejectCommentNotRequired(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("rejectCommentNotRequired", propertyName)) {
            return;
        }
        boolean rejectCommentNotRequired = false;
        JsonNode rejectcommentnotrequired = properties.get("rejectcommentnotrequired");
        if (rejectcommentnotrequired != null && rejectcommentnotrequired.asBoolean()) {
            rejectCommentNotRequired = true;
        }
        nodeProperties.put("rejectCommentNotRequired", rejectCommentNotRequired);
    }

    private void getLimitapprover(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("limitapprover", propertyName)) {
            return;
        }
        boolean limitApprover = false;
        JsonNode limitapprover = properties.get("limitapprover");
        if (limitapprover != null && limitapprover.asBoolean()) {
            limitApprover = true;
        }
        nodeProperties.put("limitapprover", limitApprover);
    }

    private void getApprovalMode(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("approvalMode", propertyName)) {
            return;
        }
        String approvalMode = "";
        JsonNode approvalModeNode = properties.get("approvalmode");
        if (approvalModeNode != null) {
            approvalMode = approvalModeNode.asText();
        }
        nodeProperties.put("approvalMode", approvalMode);
    }

    private void getReviewModeConfig(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("reviewModeConfig", propertyName)) {
            return;
        }
        List reviewModeConfig = null;
        JsonNode approvalModeConfigNode = properties.get("reviewmodeconfig");
        if (approvalModeConfigNode != null) {
            reviewModeConfig = JSONUtil.parseMapArray((String)approvalModeConfigNode.asText());
        }
        nodeProperties.put("reviewModeConfig", reviewModeConfig);
    }

    private void getDeadlineAuditConfig(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("deadlineAuditConfig", propertyName)) {
            return;
        }
        Map deadlineAuditConfig = null;
        JsonNode deadlineAuditNode = properties.get("deadlineaudit");
        if (deadlineAuditNode != null) {
            deadlineAuditConfig = JSONUtil.parseMap((String)deadlineAuditNode.asText());
        }
        nodeProperties.put("deadlineAuditConfig", deadlineAuditConfig);
    }

    private void getApprovalCommentRequireConfig(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("approvalCommentRequireConfig", propertyName)) {
            return;
        }
        Map approvalCommentRequireConfig = new HashMap(8);
        JsonNode approvalCommentRequireConfigNode = properties.get("approvalcommentrequireconfig");
        if (approvalCommentRequireConfigNode != null) {
            approvalCommentRequireConfig = JSONUtil.parseMap((String)approvalCommentRequireConfigNode.asText());
        }
        nodeProperties.put("approvalCommentRequireConfig", approvalCommentRequireConfig);
    }

    private void getClosePageAfterApprove(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("closePageAfterApprove", propertyName)) {
            return;
        }
        String closePageAfterApprove = "2";
        JsonNode closePageAfterApproveConfig = properties.get("closepageafterapprove");
        if (closePageAfterApproveConfig != null) {
            closePageAfterApprove = closePageAfterApproveConfig.asText();
        }
        nodeProperties.put("closePageAfterApprove", closePageAfterApprove);
    }

    private void getDoNotGenerateTodoTask(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("doNotGenerateTodoTask", propertyName)) {
            return;
        }
        boolean doNotGenerateTodoTask = false;
        JsonNode doNotGenerateTodoTaskNode = properties.get("donotgeneratetodotask");
        if (doNotGenerateTodoTaskNode != null && doNotGenerateTodoTaskNode.asBoolean()) {
            doNotGenerateTodoTask = true;
        }
        nodeProperties.put("doNotGenerateTodoTask", doNotGenerateTodoTask);
    }

    private void getRejectImage(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        if (StringUtils.hasText(propertyName) && !Objects.equals("rejectImage", propertyName)) {
            return;
        }
        boolean rejectImage = false;
        JsonNode rejectImageNode = properties.get("rejectimage");
        if (rejectImageNode != null && rejectImageNode.asBoolean()) {
            rejectImage = true;
        }
        nodeProperties.put("rejectImage", rejectImage);
    }

    private void getOtherExtendProperties(JsonNode properties, WorkflowNodeConfigDTO workflowNodeConfigDTO, Map<String, Object> nodeProperties) {
        String propertyName = workflowNodeConfigDTO.getPropertyName();
        Iterator fields = properties.fields();
        while (fields.hasNext()) {
            Map.Entry entry = (Map.Entry)fields.next();
            if (ignoreFields.contains(entry.getKey()) || StringUtils.hasText(propertyName) && !Objects.equals(entry.getKey(), propertyName)) continue;
            nodeProperties.put((String)entry.getKey(), entry.getValue());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, Object> getNodeProperties(WorkflowDTO workflowDTO, boolean isNextNode) {
        Map<Object, Object> properties;
        String bizCode = workflowDTO.getBizCode();
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setBizcode(bizCode);
        ProcessDO processDO = this.vaWorkflowProcessService.get(processDTO);
        boolean finishedFlag = false;
        if (processDO == null) {
            if (isNextNode) {
                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processfinished"));
            }
            List processHistoryDOS = this.vaWorkflowProcessService.listHistory(processDTO);
            if (CollectionUtils.isEmpty(processHistoryDOS)) {
                return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.processfinished"));
            }
            ProcessHistoryDO processHistoryDO = (ProcessHistoryDO)processHistoryDOS.get(processHistoryDOS.size() - 1);
            processDO = new ProcessDO();
            BeanUtils.copyProperties(processHistoryDO, processDO);
            finishedFlag = true;
        }
        if (finishedFlag && !StringUtils.hasText(workflowDTO.getNodeCode())) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        WorkflowModel workflowModel = this.workflowParamService.getModel(processDO.getDefinekey(), processDO.getDefineversion().longValue());
        JsonNode pluginDefineData = this.workflowParamService.getWorkflowProcess(workflowModel);
        JsonNode globalExtendPropertiesNode = pluginDefineData.get("globalExtendProperties");
        Map globalExtendProperties = null;
        globalExtendProperties = ObjectUtils.isEmpty(globalExtendPropertiesNode) ? new HashMap() : JSONUtil.parseMap((String)globalExtendPropertiesNode.toString());
        ArrayNode oldArrayNode = (ArrayNode)pluginDefineData.get("childShapes");
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode subProcessArrayNode = mapper.createArrayNode();
        for (JsonNode jsonNode : oldArrayNode) {
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
        try {
            String activityId;
            VaWorkflowContext vaWorkflowContext = new VaWorkflowContext();
            vaWorkflowContext.setWorkflowDTO(workflowDTO);
            HashMap<String, WorkflowModel> calParams = new HashMap<String, WorkflowModel>();
            calParams.put("workflowModel", workflowModel);
            vaWorkflowContext.setCustomParam(calParams);
            VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
            Map<String, Object> workflowVariables = this.workflowParamService.loadWorkflowVariables(bizCode, processDO.getBiztype(), processDO.getDefineversion(), processDO.getBizmodule(), processDO.getDefinekey());
            workflowDTO.setWorkflowVariables(workflowVariables);
            workflowDTO.setUniqueCode(processDO.getDefinekey());
            workflowDTO.setProcessInstanceId(processDO.getId());
            if (isNextNode) {
                JsonNode nextNode = VaWorkflowNodeUtils.getNextNode(workflowDTO.getNodeCode(), arrayNode);
                workflowDTO.setNodeCode(nextNode.get("resourceId").asText());
            }
            properties = new HashMap<String, String>();
            if (finishedFlag) {
                activityId = workflowDTO.getNodeCode();
                properties.put("activityId", activityId);
            } else {
                properties = workflowModel.currNodeProperties(workflowDTO);
                activityId = (String)properties.get("activityId");
            }
            JsonNode currNode = null;
            for (JsonNode jsonNode : arrayNode) {
                if (!activityId.equals(jsonNode.get("resourceId").asText())) continue;
                properties.put("nodeProperties", JSONUtil.parseObject((String)jsonNode.get("properties").toString()));
                currNode = jsonNode;
                break;
            }
            if (finishedFlag) {
                properties.put("nodeType", currNode.get("stencil").get("id"));
            }
            ArrayList<UserDO> users = new ArrayList<UserDO>();
            JsonNode currNodeProperties = currNode.get("properties");
            if (!ObjectUtils.isEmpty(currNodeProperties.get("multiinstance_type"))) {
                JsonNode userNode;
                String multiinstanceType = currNodeProperties.get("multiinstance_type").asText();
                if (!"None".equals(multiinstanceType)) {
                    String multiinstance = currNodeProperties.get("multiinstance_collection").asText();
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        userNode = objectMapper.readTree(multiinstance);
                    }
                    catch (JsonProcessingException e) {
                        R r = R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.failedtogettheapprover"));
                        VaContext.removeVaWorkflowContext();
                        return r;
                    }
                } else {
                    userNode = currNodeProperties.get("usertaskassignment");
                }
                if (!ObjectUtils.isEmpty(userNode)) {
                    HashMap<String, UserDO> userCache = new HashMap<String, UserDO>();
                    HashMap<String, Object> processParams = new HashMap<String, Object>();
                    processParams.put("variables", workflowVariables);
                    processParams.put("processDO", processDO);
                    processParams.put("workflowDTO", workflowDTO);
                    for (JsonNode assignmentNode : userNode) {
                        Set userSet;
                        try {
                            String strategyModuleName = assignmentNode.get("strategyModuleName").asText();
                            String strategyName = assignmentNode.get("strategyName").asText();
                            processParams.put("assignParam", assignmentNode.get("items"));
                            processParams.put("currentNodeCode", activityId);
                            userSet = this.workflowStrategySevice.execute(strategyModuleName, strategyName, processParams);
                        }
                        catch (Exception e) {
                            log.error("\u83b7\u53d6\u5ba1\u6279\u4eba\u5931\u8d25", e);
                            continue;
                        }
                        for (String userId : userSet) {
                            users.add(VaWorkFlowDataUtils.getUserDOWithOrgInfo(userId, userCache));
                        }
                    }
                }
            }
            properties.put("users", users);
            properties.put("globalExtendProperties", globalExtendProperties);
        }
        finally {
            VaContext.removeVaWorkflowContext();
        }
        return properties;
    }
}

