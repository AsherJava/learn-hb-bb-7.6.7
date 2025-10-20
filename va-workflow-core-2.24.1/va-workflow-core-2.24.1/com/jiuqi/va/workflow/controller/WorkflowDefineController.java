/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.node.WorkflowBatchNodeConfigDTO
 *  com.jiuqi.va.domain.workflow.node.WorkflowNodeConfigDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.domain.workflow.service.node.VaWorkflowNodeConfigService
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.node.WorkflowBatchNodeConfigDTO;
import com.jiuqi.va.domain.workflow.node.WorkflowNodeConfigDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowPlusApprovalService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.domain.workflow.service.node.VaWorkflowNodeConfigService;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowHelperService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowNodeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow"})
public class WorkflowDefineController {
    private static final Logger log = LoggerFactory.getLogger(WorkflowDefineController.class);
    private static final String PROPERTY_NAME = "propertyName";
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private WorkflowPlusApprovalService workflowPlusApprovalService;
    @Autowired
    private WorkflowSevice workflowSevice;
    @Autowired
    private WorkflowHelperService workflowHelperService;
    @Autowired
    private VaWorkflowNodeConfigService workflowNodeConfigService;
    @Autowired
    private MetaDataClient metaDataClient;

    @Deprecated
    @GetMapping(value={"/param/define/{uniqueCode}/{version}"})
    public R getParam(@PathVariable(value="uniqueCode") String uniqueCode, @PathVariable(value="version") Long version) {
        R r = new R();
        WorkflowModelDefine define = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, version);
        ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)define.getPlugins().get(ProcessParamPluginDefine.class);
        ArrayList params = processParamPluginDefine.getProcessParam();
        r.put("params", (Object)(params == null ? new ArrayList() : params));
        return r;
    }

    @PostMapping(value={"/param/list"})
    public R getParam(@RequestBody TenantDO tenantDO) {
        if (tenantDO.getExtInfo("uniqueCode") == null && tenantDO.getExtInfo("version") == null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String uniqueCode = String.valueOf(tenantDO.getExtInfo("uniqueCode"));
        Long version = Long.valueOf(String.valueOf(tenantDO.getExtInfo("version")));
        WorkflowModelDefine define = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, version);
        ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)define.getPlugins().get(ProcessParamPluginDefine.class);
        ArrayList params = processParamPluginDefine.getProcessParam();
        R r = new R();
        r.put("params", (Object)(params == null ? new ArrayList() : params));
        return r;
    }

    @Deprecated
    @GetMapping(value={"/node/define/{uniqueCode}/{version}"})
    public R getNode(@PathVariable(value="uniqueCode") String uniqueCode, @PathVariable(value="version") Long version) {
        String nodeType;
        R r = new R();
        ArrayList<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
        WorkflowModelDefine define = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, version);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)define.getPlugins().get(ProcessDesignPluginDefine.class);
        ArrayNode arrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode sequenceFlowArrayNode = mapper.createArrayNode();
        for (JsonNode jsonNode : arrayNode) {
            nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"SequenceFlow".equals(nodeType)) continue;
            sequenceFlowArrayNode.add(jsonNode);
        }
        for (JsonNode jsonNode : arrayNode) {
            nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(nodeType)) continue;
            ArrayNode outgings = (ArrayNode)jsonNode.get("outgoing");
            VaWorkflowNodeUtils.getNodes(outgings, arrayNode, sequenceFlowArrayNode, nodes);
            break;
        }
        r.put("nodes", nodes);
        return r;
    }

    @PostMapping(value={"/node/config/get"})
    public R getNodeConfig(@RequestBody TenantDO tenantDO) {
        boolean submitNodeFlag;
        Object uniqueCodeObj = tenantDO.getExtInfo("uniqueCode");
        Object nodeIdObj = tenantDO.getExtInfo("nodeId");
        Object submitNodeFlagObj = tenantDO.getExtInfo("submitNodeFlag");
        boolean bl = submitNodeFlag = submitNodeFlagObj != null && (Boolean)submitNodeFlagObj != false;
        if (uniqueCodeObj == null || nodeIdObj == null && !submitNodeFlag) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String uniqueCode = String.valueOf(uniqueCodeObj);
        Object versionObj = tenantDO.getExtInfo("version");
        Long version = ObjectUtils.isEmpty(nodeIdObj) ? null : Long.valueOf(String.valueOf(versionObj));
        String nodeId = ObjectUtils.isEmpty(nodeIdObj) ? null : (String)tenantDO.getExtInfo("nodeId");
        String propertyName = ObjectUtils.isEmpty(tenantDO.getExtInfo(PROPERTY_NAME)) ? null : (String)tenantDO.getExtInfo(PROPERTY_NAME);
        WorkflowNodeConfigDTO workflowNodeConfigDTO = new WorkflowNodeConfigDTO();
        workflowNodeConfigDTO.setUniqueCode(uniqueCode);
        workflowNodeConfigDTO.setVersion(version);
        workflowNodeConfigDTO.setNodeCode(nodeId);
        workflowNodeConfigDTO.setPropertyName(propertyName);
        workflowNodeConfigDTO.setSubmitNodeFlag(submitNodeFlag);
        Map nodeConfig = this.workflowNodeConfigService.getNodeConfig(workflowNodeConfigDTO);
        R r = new R();
        r.putAll(nodeConfig);
        return r;
    }

    @PostMapping(value={"/node/config/batch/get"})
    public R getNodeBatchConfig(@RequestBody WorkflowBatchNodeConfigDTO workflowBatchNodeConfigDTO) {
        HashMap<String, R> uniqueConfigMap = new HashMap<String, R>();
        List workflowNodeConfigs = workflowBatchNodeConfigDTO.getWorkflowNodeConfigs();
        for (WorkflowNodeConfigDTO workflowNodeConfig : workflowNodeConfigs) {
            String uniqueCode = workflowNodeConfig.getUniqueCode();
            Long version = workflowNodeConfig.getVersion();
            String nodeCode = workflowNodeConfig.getNodeCode();
            String propertyName = workflowNodeConfig.getPropertyName();
            boolean submitNodeFlag = workflowNodeConfig.isSubmitNodeFlag();
            Map properties = (Map)uniqueConfigMap.get(uniqueCode + version + nodeCode);
            if (properties != null) continue;
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("uniqueCode", (Object)uniqueCode);
            tenantDO.addExtInfo("version", (Object)version);
            tenantDO.addExtInfo("nodeId", (Object)nodeCode);
            tenantDO.addExtInfo(PROPERTY_NAME, (Object)propertyName);
            tenantDO.addExtInfo("submitNodeFlag", (Object)submitNodeFlag);
            R nodeConfig = this.getNodeConfig(tenantDO);
            if (nodeConfig.getCode() == 0) {
                nodeConfig.remove((Object)"code");
                nodeConfig.remove((Object)"msg");
                uniqueConfigMap.put(uniqueCode + version + nodeCode, nodeConfig);
                continue;
            }
            return R.error((String)nodeConfig.getMsg());
        }
        R r = new R();
        r.put("properties", uniqueConfigMap);
        return r;
    }

    @PostMapping(value={"/batch/node/config/get"})
    public List<Map<String, Object>> batchGetNodeConfig(@RequestBody List<Map<String, Object>> params) {
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        ArrayList<String> uniqueKeyList = new ArrayList<String>();
        for (Map<String, Object> param : params) {
            String processId = String.valueOf(param.get("PROCESSID"));
            String uniqueCode = String.valueOf(param.get("PROCESSDEFINEKEY"));
            String version = String.valueOf(param.get("PROCESSDEFINEVERSION"));
            String nodeId = String.valueOf(param.get("TASKDEFINEKEY"));
            String uniqueKey = processId + uniqueCode + version + nodeId;
            int taskType = Integer.parseInt(String.valueOf(param.get("TASKTYPE")));
            if (taskType != 2 || uniqueKeyList.contains(uniqueKey)) continue;
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("uniqueCode", (Object)uniqueCode);
            tenantDO.addExtInfo("version", (Object)version);
            tenantDO.addExtInfo("nodeId", (Object)nodeId);
            R nodeConfig = this.getNodeConfig(tenantDO);
            if (nodeConfig.getCode() == 0) {
                HashMap<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("unique", uniqueKey);
                tempMap.put("value", nodeConfig.get((Object)"plusapproval"));
                resultList.add(tempMap);
                uniqueKeyList.add(uniqueKey);
                continue;
            }
            throw new RuntimeException(nodeConfig.getMsg());
        }
        return resultList;
    }

    @PostMapping(value={"/node/list"})
    public R getNode(@RequestBody WorkflowDTO workflowDTO) {
        String nodeType;
        if (workflowDTO.getUniqueCode() == null) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String uniqueCode = workflowDTO.getUniqueCode();
        Long version = workflowDTO.getProcessDefineVersion();
        ArrayList<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        WorkflowModelDefine define = null;
        try {
            define = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, version);
        }
        catch (Exception e) {
            return R.error((String)VaWorkFlowI18nUtils.getInfo("va.workflow.getmetadataempty"));
        }
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)define.getPlugins().get(ProcessDesignPluginDefine.class);
        ArrayNode arrayNode = (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode sequenceFlow = mapper.createArrayNode();
        for (JsonNode jsonNode : arrayNode) {
            nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"SequenceFlow".equals(nodeType)) continue;
            sequenceFlow.add(jsonNode);
        }
        for (JsonNode jsonNode : arrayNode) {
            nodeType = jsonNode.get("stencil").get("id").asText();
            if (!"StartNoneEvent".equals(nodeType)) continue;
            ArrayNode outgoings = (ArrayNode)jsonNode.get("outgoing");
            VaWorkflowNodeUtils.getNodesInfo(outgoings, arrayNode, nodes, sequenceFlow);
            break;
        }
        R r = new R();
        r.put("nodes", nodes);
        return r;
    }

    @PostMapping(value={"/design/image"})
    public R getWorkflowDesignImage(@RequestBody TenantDO tenantDO) {
        return this.workflowSevice.getWorkflowDesignImage(tenantDO);
    }

    /*
     * Exception decompiling
     */
    @PostMapping(value={"/workflowBatchExport"})
    public ResponseEntity<byte[]> workflowBatchExport(@RequestBody List<MetaInfoDim> workflowMetaInfos) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static /* synthetic */ MetaInfoDim lambda$workflowBatchExport$1(List workflowMetaInfos, String uniqueCode) {
        return workflowMetaInfos.stream().filter(info -> uniqueCode.equals(info.getUniqueCode())).findFirst().orElse(null);
    }
}

