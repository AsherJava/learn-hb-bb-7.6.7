/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.auto.agree.AutoAgreeConfig
 *  com.jiuqi.va.domain.workflow.auto.agree.AutoAgreeOption
 *  com.jiuqi.va.domain.workflow.service.WorkflowInfoService
 *  com.jiuqi.va.feign.client.AuthUserClient
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.auto.agree.AutoAgreeConfig;
import com.jiuqi.va.domain.workflow.auto.agree.AutoAgreeOption;
import com.jiuqi.va.domain.workflow.service.WorkflowInfoService;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.workflow.config.ThreadPoolConst;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.model.impl.WorkflowModelImpl;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPlugin;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.service.impl.help.WorkflowParamService;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowInfoServiceImpl
implements WorkflowInfoService {
    private static final Logger log = LoggerFactory.getLogger(WorkflowInfoServiceImpl.class);
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowHelperService workflowHelperService;
    @Autowired
    private WorkflowParamService workflowParamService;
    @Autowired
    private AuthUserClient authUserClient;

    public boolean isAutoAgree(WorkflowDTO workflowDTO, String taskDefineKey) {
        JsonNode process = this.getDefineData(workflowDTO);
        boolean globleAutoAgree = process.get("globleAutoAgree") == null ? false : process.get("globleAutoAgree").asBoolean();
        ArrayNode nodes = (ArrayNode)process.get("childShapes");
        for (JsonNode node : nodes) {
            String nodeId = node.get("resourceId").asText();
            if (taskDefineKey.equals(nodeId)) {
                return this.isNodeAutoAgree(node, globleAutoAgree);
            }
            if (ObjectUtils.isEmpty(node.get("childShapes"))) continue;
            ArrayNode childShapes = (ArrayNode)node.get("childShapes");
            for (JsonNode childShape : childShapes) {
                String childShapeNodeId = childShape.get("resourceId").asText();
                if (!taskDefineKey.equals(childShapeNodeId)) continue;
                return this.isNodeAutoAgree(childShape, globleAutoAgree);
            }
        }
        return globleAutoAgree;
    }

    public boolean isNodeAutoAgree(JsonNode node, boolean globleAutoAgree) {
        String autoAgree;
        JsonNode autoAgreeNode = node.get("properties").get("autoAgree");
        String string = autoAgree = autoAgreeNode == null ? "01" : autoAgreeNode.asText();
        if ("01".equals(autoAgree)) {
            return globleAutoAgree;
        }
        return "02".equals(autoAgree);
    }

    public void executeNodeAutoTask(String nodeCode) {
        UserLoginDTO loginUser = VaWorkflowUtils.getLoginUserWithToken();
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        WorkflowDTO originalWorkflowDTO = vaWorkflowContext.getWorkflowDTO();
        WorkflowDTO workflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)originalWorkflowDTO), WorkflowDTO.class);
        Locale defaultLocal = LocaleContextHolder.getLocale();
        ThreadPoolConst.AUTOTASK_THREADPOOL.execute(() -> {
            try {
                ShiroUtil.unbindUser();
                ShiroUtil.bindUser((UserLoginDTO)loginUser);
                VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                LocaleContextHolder.setLocale(defaultLocal);
                Map customParam = vaWorkflowContext.getCustomParam();
                WorkflowModelImpl workflowModel = (WorkflowModelImpl)customParam.get("workflowModel");
                this.workflowHelperService.dealAutoTaskParamBeforeExecute(workflowDTO, nodeCode);
                workflowModel.executeAutoTasks(this.getNodeAutoTasks(nodeCode), workflowDTO);
            }
            catch (Exception e) {
                log.error("\u81ea\u52a8\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
            }
            finally {
                VaContext.removeVaWorkflowContext();
                ShiroUtil.unbindUser();
            }
        });
    }

    public void executeSubProcessAutoTask() {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        if (customParam.containsKey("subProcessInstanceFinish")) {
            this.executeSubProcessInstanceAutoTask((String)customParam.get("subProcessInstanceFinish"));
        }
        if (customParam.containsKey("subProcessNodeFinish")) {
            this.executeNodeAutoTask((String)customParam.get("subProcessNodeFinish"));
        }
    }

    public void executeSubProcessInstanceAutoTask(String nodeCode) {
        UserLoginDTO loginUser = VaWorkflowUtils.getLoginUserWithToken();
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Locale defaultLocal = LocaleContextHolder.getLocale();
        WorkflowDTO originalWorkflowDTO = vaWorkflowContext.getWorkflowDTO();
        WorkflowDTO workflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)originalWorkflowDTO), WorkflowDTO.class);
        ThreadPoolConst.AUTOTASK_THREADPOOL.execute(() -> {
            try {
                ShiroUtil.unbindUser();
                ShiroUtil.bindUser((UserLoginDTO)loginUser);
                VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                LocaleContextHolder.setLocale(defaultLocal);
                Map customParam = vaWorkflowContext.getCustomParam();
                WorkflowModelImpl workflowModel = (WorkflowModelImpl)customParam.get("workflowModel");
                this.workflowHelperService.dealAutoTaskParamBeforeExecute(workflowDTO, nodeCode);
                workflowModel.executeAutoTasks(this.getSubProcessInstanceAutoTasks(nodeCode), workflowDTO);
            }
            catch (Exception e) {
                log.error("\u81ea\u52a8\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
            }
            finally {
                VaContext.removeVaWorkflowContext();
                ShiroUtil.unbindUser();
            }
        });
    }

    public void executeNodePredecessorAutoTask(String nodeCode) {
        UserLoginDTO loginUser = VaWorkflowUtils.getLoginUserWithToken();
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Locale defaultLocal = LocaleContextHolder.getLocale();
        WorkflowDTO originalWorkflowDTO = vaWorkflowContext.getWorkflowDTO();
        WorkflowDTO workflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)originalWorkflowDTO), WorkflowDTO.class);
        ThreadPoolConst.AUTOTASK_THREADPOOL.execute(() -> {
            try {
                ShiroUtil.unbindUser();
                ShiroUtil.bindUser((UserLoginDTO)loginUser);
                VaContext.setVaWorkflowContext((VaWorkflowContext)vaWorkflowContext);
                LocaleContextHolder.setLocale(defaultLocal);
                Map customParam = vaWorkflowContext.getCustomParam();
                WorkflowModelImpl workflowModel = (WorkflowModelImpl)customParam.get("workflowModel");
                this.workflowHelperService.dealAutoTaskParamBeforeExecute(workflowDTO, nodeCode);
                workflowModel.executeAutoTasks(this.getNodePredecessorAutoTasks(nodeCode, workflowDTO), workflowDTO);
            }
            catch (Exception e) {
                log.error("\u524d\u7f6e\u81ea\u52a8\u4efb\u52a1\u6267\u884c\u5931\u8d25", e);
            }
            finally {
                VaContext.removeVaWorkflowContext();
                ShiroUtil.unbindUser();
            }
        });
    }

    private ArrayNode getNodeAutoTasks(String nodeCode) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
        Integer approvalResult = workflowDTO.getApprovalResult();
        String uniqueCode = workflowDTO.getUniqueCode();
        Long processDefineVersion = workflowDTO.getProcessDefineVersion();
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
        WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        ArrayNode oldArrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subProcessArrayNode = objectMapper.createArrayNode();
        for (JsonNode jsonNode : oldArrayNode) {
            if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
            JsonNode childShapes = jsonNode.get("childShapes");
            for (JsonNode childShape : childShapes) {
                String nodeType = childShape.get("stencil").get("id").asText();
                if ("StartNoneEvent".equals(nodeType) || "EndNoneEvent".equals(nodeType)) continue;
                JsonNode childShapeCopy = childShape.deepCopy();
                subProcessArrayNode.add(childShapeCopy);
            }
        }
        ArrayNode nodes = oldArrayNode.deepCopy();
        nodes.addAll(subProcessArrayNode);
        ArrayNode arrayNode = null;
        for (JsonNode node : nodes) {
            String resourceId = node.get("resourceId").asText();
            if (!nodeCode.equals(resourceId)) continue;
            if (approvalResult == 1 && node.get("properties").get("nodeautoagreetask") instanceof ArrayNode) {
                arrayNode = (ArrayNode)node.get("properties").get("nodeautoagreetask");
                break;
            }
            if (approvalResult != 2 || !(node.get("properties").get("nodeautorejecttask") instanceof ArrayNode)) break;
            arrayNode = (ArrayNode)node.get("properties").get("nodeautorejecttask");
            break;
        }
        return arrayNode;
    }

    private ArrayNode getSubProcessInstanceAutoTasks(String nodeCode) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        WorkflowDTO workflowDTO = vaWorkflowContext.getWorkflowDTO();
        Integer approvalResult = workflowDTO.getApprovalResult();
        String uniqueCode = workflowDTO.getUniqueCode();
        Long processDefineVersion = workflowDTO.getProcessDefineVersion();
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
        WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        ArrayNode nodes = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        ArrayNode arrayNode = null;
        for (JsonNode node : nodes) {
            String resourceId = node.get("resourceId").asText();
            if (!nodeCode.equals(resourceId)) continue;
            if (approvalResult == 1 && node.get("properties").get("subprocessautoagreetask") instanceof ArrayNode) {
                arrayNode = (ArrayNode)node.get("properties").get("subprocessautoagreetask");
                break;
            }
            if (approvalResult != 2 || !(node.get("properties").get("subprocessautorejecttask") instanceof ArrayNode)) break;
            arrayNode = (ArrayNode)node.get("properties").get("subprocessautorejecttask");
            break;
        }
        return arrayNode == null ? null : arrayNode.deepCopy();
    }

    public ArrayNode getNodePredecessorAutoTasks(String nodeCode, WorkflowDTO workflowDTO) {
        String uniqueCode = workflowDTO.getUniqueCode();
        Long processDefineVersion = workflowDTO.getProcessDefineVersion();
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
        WorkflowModel workflowModel = (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        ArrayNode oldArrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode subProcessArrayNode = objectMapper.createArrayNode();
        for (JsonNode jsonNode : oldArrayNode) {
            if (ObjectUtils.isEmpty(jsonNode.get("childShapes"))) continue;
            JsonNode childShapes = jsonNode.get("childShapes");
            for (JsonNode childShape : childShapes) {
                String nodeType = childShape.get("stencil").get("id").asText();
                if ("StartNoneEvent".equals(nodeType) || "EndNoneEvent".equals(nodeType)) continue;
                JsonNode childShapeCopy = childShape.deepCopy();
                subProcessArrayNode.add(childShapeCopy);
            }
        }
        ArrayNode nodes = oldArrayNode.deepCopy();
        nodes.addAll(subProcessArrayNode);
        ArrayNode arrayNode = null;
        for (JsonNode node : nodes) {
            String resourceId = node.get("resourceId").asText();
            if (!nodeCode.equals(resourceId)) continue;
            if (!(node.get("properties").get("nodepredecessortask") instanceof ArrayNode)) break;
            arrayNode = (ArrayNode)node.get("properties").get("nodepredecessortask");
            break;
        }
        return arrayNode;
    }

    public AutoAgreeConfig getAutoAgreeConfig(WorkflowDTO workflowDTO, String taskDefineKey) {
        AutoAgreeConfig globalAutoAgreeConfig;
        JsonNode defineData = this.getDefineData(workflowDTO);
        AutoAgreeConfig nodeAutoAgreeConfig = this.getNodeAutoAgreeConfig(taskDefineKey, defineData, globalAutoAgreeConfig = this.getGlobalAutoAgreeConfig(defineData));
        return nodeAutoAgreeConfig == null ? new AutoAgreeConfig() : nodeAutoAgreeConfig;
    }

    private JsonNode getDefineData(WorkflowDTO workflowDTO) {
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        WorkflowModel workflowModel = (WorkflowModel)vaWorkflowContext.getCustomParam().get("workflowModel");
        if (workflowModel == null) {
            workflowModel = this.workflowParamService.getModel(workflowDTO.getUniqueCode(), workflowDTO.getProcessDefineVersion());
        }
        ProcessDesignPlugin ProcessDesignPlugin2 = (ProcessDesignPlugin)workflowModel.getPlugins().find(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)ProcessDesignPlugin2.getDefine();
        return processDesignPluginDefine.getData();
    }

    private AutoAgreeConfig getGlobalAutoAgreeConfig(JsonNode defineData) {
        boolean globalAutoAgree = defineData.get("globleAutoAgree") != null && defineData.get("globleAutoAgree").asBoolean();
        AutoAgreeOption globalAutoAgreeOption = this.parseAutoAgreeOption(defineData.get("globalAutoAgreeOption"));
        return new AutoAgreeConfig(globalAutoAgree, globalAutoAgreeOption);
    }

    private AutoAgreeConfig getNodeAutoAgreeConfig(String taskDefineKey, JsonNode defineData, AutoAgreeConfig globalAutoAgreeConfig) {
        if (ObjectUtils.isEmpty(defineData.get("childShapes"))) {
            return null;
        }
        for (JsonNode childShape : defineData.get("childShapes")) {
            if (taskDefineKey.equals(childShape.get("resourceId").asText())) {
                return this.calculateNodeAutoAgreeConfig(childShape, globalAutoAgreeConfig);
            }
            AutoAgreeConfig autoAgreeConfig = this.getNodeAutoAgreeConfig(taskDefineKey, childShape, globalAutoAgreeConfig);
            if (autoAgreeConfig == null) continue;
            return autoAgreeConfig;
        }
        return null;
    }

    private AutoAgreeConfig calculateNodeAutoAgreeConfig(JsonNode childShape, AutoAgreeConfig globalAutoAgreeConfig) {
        String autoAgree;
        JsonNode autoAgreeNode = childShape.get("properties").get("autoAgree");
        String string = autoAgree = autoAgreeNode == null ? "01" : autoAgreeNode.asText();
        if ("01".equals(autoAgree)) {
            return globalAutoAgreeConfig;
        }
        boolean nodeAutoAgree = "02".equals(autoAgree);
        AutoAgreeOption autoAgreeOption = this.parseAutoAgreeOption(childShape.get("properties").get("autoagreeoption"));
        return new AutoAgreeConfig(nodeAutoAgree, autoAgreeOption);
    }

    private AutoAgreeOption parseAutoAgreeOption(JsonNode jsonNode) {
        if (jsonNode == null || !StringUtils.hasText(jsonNode.asText())) {
            return null;
        }
        try {
            return (AutoAgreeOption)JSONUtil.parseObject((String)jsonNode.asText(), AutoAgreeOption.class);
        }
        catch (Exception e) {
            log.error("\u89e3\u6790\u81ea\u52a8\u540c\u610f\u914d\u7f6e\u5931\u8d25", e);
            return null;
        }
    }

    public AutoAgreeConfig getAutoAgreeConfig(JsonNode processData, JsonNode nextNode) {
        AutoAgreeConfig globalAutoAgreeConfig = this.getGlobalAutoAgreeConfig(processData);
        return this.calculateNodeAutoAgreeConfig(nextNode, globalAutoAgreeConfig);
    }
}

