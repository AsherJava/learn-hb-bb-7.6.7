/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.meta.MetaState
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.domain.biz.DeployDTO
 *  com.jiuqi.va.domain.biz.DeployType
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.i18n.domain.VaI18nResourceBatchDTO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceInitDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.meta.MetaState;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.domain.biz.DeployDTO;
import com.jiuqi.va.domain.biz.DeployType;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessRelDesignDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.i18n.domain.VaI18nResourceBatchDTO;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.domain.VaI18nResourceInitDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.utils.VaI18nParamUtil;
import com.jiuqi.va.workflow.dao.WorkflowBusinessDao;
import com.jiuqi.va.workflow.dao.WorkflowMetaDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDesignDao;
import com.jiuqi.va.workflow.dao.business.WorkflowBusinessRelDraftDao;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import com.jiuqi.va.workflow.service.WorkflowPublicParamRelationService;
import com.jiuqi.va.workflow.utils.VaWorkflowThreadUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkflowMetaServiceImpl
implements WorkflowMetaService {
    private static final Logger log = LoggerFactory.getLogger(WorkflowMetaServiceImpl.class);
    @Autowired
    private WorkflowMetaDao workflowMetaDao;
    @Autowired
    private WorkflowBusinessDao workflowBusinessDao;
    @Autowired
    private WorkflowPublicParamRelationService publicParamRelationService;
    @Autowired
    private WorkflowBusinessRelDraftDao workflowBusinessRelDraftDao;
    @Autowired
    private WorkflowBusinessRelDesignDao workflowBusinessRelDesignDao;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private VaI18nClient vaI18nClient;

    @Transactional
    public void publish(DeployDTO deployDTO, Integer workflowVersion) {
        DeployType deployType = deployDTO.getDeployType();
        if (DeployType.ADD.equals((Object)deployType) || DeployType.UPDATE.equals((Object)deployType)) {
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue() && DeployType.ADD.equals((Object)deployType)) {
                ThreadPoolExecutor poolExecutor = VaWorkflowThreadUtils.getThreadPoolExecutor();
                CompletableFuture.runAsync(() -> {
                    try {
                        this.handleWorkflowNodeI18n(deployDTO);
                    }
                    catch (Exception e) {
                        log.error("\u5de5\u4f5c\u6d41\u53d1\u5e03\u5904\u7406\u591a\u8bed\u8a00\u5f02\u5e38{}", (Object)e.getMessage(), (Object)e);
                    }
                }, poolExecutor);
            }
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenantDO.addExtInfo("workflowDefineKey", (Object)deployDTO.getUniqueCode());
            tenantDO.addExtInfo("workflowDefineVersion", (Object)workflowVersion);
            tenantDO.addExtInfo("metaVersion", (Object)deployDTO.getVersion());
            this.workflowMetaDao.save(tenantDO);
            WorkflowBusinessDTO workflowBusinessDTO = new WorkflowBusinessDTO();
            workflowBusinessDTO.setWorkflowdefinekey(deployDTO.getUniqueCode());
            List<WorkflowBusinessDO> workflowBusinessList = this.workflowBusinessDao.selectLatest(workflowBusinessDTO);
            if (CollectionUtils.isEmpty(workflowBusinessList)) {
                workflowBusinessList = this.workflowBusinessRelDraftDao.selectLatestList(workflowBusinessDTO);
            } else {
                workflowBusinessDTO.setDesignstate(Integer.valueOf(MetaState.APPENDED.getValue()));
                workflowBusinessList.addAll(this.workflowBusinessRelDraftDao.selectLatestList(workflowBusinessDTO));
            }
            for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessList) {
                Integer designstate = workflowBusinessDO.getDesignstate();
                UUID newId = UUID.randomUUID();
                if (designstate != null && designstate.intValue() == MetaState.APPENDED.getValue()) {
                    this.workflowBusinessRelDraftDao.delete(workflowBusinessDO);
                    workflowBusinessDO.setWorkflowdefineversion(deployDTO.getVersion());
                    workflowBusinessDO.setRelversion(Long.valueOf(System.currentTimeMillis()));
                    workflowBusinessDO.setModifytime(new Date());
                    this.workflowBusinessRelDraftDao.insert(workflowBusinessDO);
                } else {
                    workflowBusinessDO.setId(newId);
                    workflowBusinessDO.setWorkflowdefineversion(deployDTO.getVersion());
                    workflowBusinessDO.setRelversion(Long.valueOf(System.currentTimeMillis()));
                    this.workflowBusinessDao.add(workflowBusinessDO);
                }
                WorkflowBusinessRelDesignDO insertDesignDO = new WorkflowBusinessRelDesignDO();
                insertDesignDO.setId(newId);
                String designData = workflowBusinessDO.getDesigndata();
                if (!StringUtils.hasText(designData)) {
                    designData = workflowBusinessDO.getConfig();
                }
                insertDesignDO.setDesignData(designData);
                this.workflowBusinessRelDesignDao.insert(insertDesignDO);
            }
            WorkflowBusinessDO deleteDraftDO = new WorkflowBusinessDO();
            deleteDraftDO.setWorkflowdefinekey(deployDTO.getUniqueCode());
            deleteDraftDO.setDesignstate(Integer.valueOf(MetaState.DELETED.getValue()));
            this.workflowBusinessRelDraftDao.delete(deleteDraftDO);
            Long editVersion = (Long)deployDTO.getExtInfo("editVersion");
            WorkflowPublicParamRelationDTO relationDTO = new WorkflowPublicParamRelationDTO();
            relationDTO.setDefineversion(deployDTO.getVersion());
            relationDTO.setDefinekey(deployDTO.getUniqueCode());
            relationDTO.setUsername(ShiroUtil.getUser().getId());
            relationDTO.addExtInfo("editVersion", (Object)editVersion);
            this.publicParamRelationService.updatePublicParamRel(relationDTO);
        } else if (DeployType.DELETE.equals((Object)deployType)) {
            WorkflowBusinessDTO workflowBusinessDTO = new WorkflowBusinessDTO();
            workflowBusinessDTO.setWorkflowdefinekey(deployDTO.getUniqueCode());
            List<WorkflowBusinessDO> draftDOList = this.workflowBusinessRelDraftDao.select(workflowBusinessDTO);
            if (!CollectionUtils.isEmpty(draftDOList)) {
                this.workflowBusinessRelDraftDao.delete((WorkflowBusinessDO)workflowBusinessDTO);
                List idList = draftDOList.stream().map(o -> o.getId().toString()).collect(Collectors.toList());
                workflowBusinessDTO.setDeployIds(idList);
                this.workflowBusinessRelDesignDao.deleteByIdList(workflowBusinessDTO);
            }
            workflowBusinessDTO.setStopflag(Integer.valueOf(1));
            this.workflowBusinessDao.update((WorkflowBusinessDO)workflowBusinessDTO);
            WorkflowPublicParamRelationDTO dto = new WorkflowPublicParamRelationDTO();
            dto.setDefinekey(deployDTO.getUniqueCode());
            this.publicParamRelationService.delete(dto);
        }
    }

    private void handleWorkflowNodeI18n(DeployDTO deployDTO) {
        String uniqueCode = deployDTO.getUniqueCode();
        String moduleName = deployDTO.getModuleName();
        MetaInfoDTO metaInfoDTO = new MetaInfoDTO();
        metaInfoDTO.setUniqueCode(uniqueCode);
        metaInfoDTO.setModelName(moduleName);
        R r = this.metaDataClient.listMetaInfoHis(metaInfoDTO);
        if (r.getCode() != 0) {
            log.error("\u83b7\u53d6\u4e0a\u4e00\u7248\u672c\u5143\u6570\u636e\u5f02\u5e38\uff1a{}", (Object)r.getMsg());
            return;
        }
        Object data = r.get((Object)"data");
        List metaInfos = JSONUtil.parseMapArray((String)JSONUtil.toJSONString((Object)data));
        Map lastInfo = metaInfos.stream().max(Comparator.comparing(s -> (Long)s.get("versionNO"))).orElse(null);
        if (lastInfo == null) {
            return;
        }
        Long lastVersionNO = (Long)lastInfo.get("versionNO");
        Long currVersion = deployDTO.getVersion();
        String lastVersionStr = String.valueOf(lastVersionNO);
        String currVersionStr = String.valueOf(currVersion);
        WorkflowModelDefine lastDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, lastVersionNO);
        ProcessDesignPluginDefine lastPluginDefine = (ProcessDesignPluginDefine)lastDefine.getPlugins().get(ProcessDesignPluginDefine.class);
        ArrayNode lastArrayNode = (ArrayNode)lastPluginDefine.getData().get("childShapes");
        WorkflowModelDefine currDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, currVersion);
        ProcessDesignPluginDefine currPluginDefine = (ProcessDesignPluginDefine)currDefine.getPlugins().get(ProcessDesignPluginDefine.class);
        ArrayNode currArrayNode = (ArrayNode)currPluginDefine.getData().get("childShapes");
        List<String> lastNodeCodes = this.getWorkflowEle(lastArrayNode);
        List<String> currNodeCodes = this.getWorkflowEle(currArrayNode);
        List nodeCodes = lastNodeCodes.stream().filter(currNodeCodes::contains).collect(Collectors.toList());
        String prefixKey = "VA#workflow#";
        String lastPrefixKey = prefixKey + uniqueCode + "&define#" + lastVersionStr + "&workflowversion#processDesignPlugin&plugin#";
        String currPrefixKey = prefixKey + uniqueCode + "&define#" + currVersionStr + "&workflowversion#processDesignPlugin&plugin#";
        ArrayList<String> lastI18nKeys = new ArrayList<String>();
        ArrayList<String> curr18nKeys = new ArrayList<String>();
        for (String nodeCode : nodeCodes) {
            lastI18nKeys.add(lastPrefixKey + nodeCode);
            curr18nKeys.add(currPrefixKey + nodeCode);
        }
        if (lastI18nKeys.isEmpty()) {
            return;
        }
        VaI18nResourceDTO i18nResourceDTO = new VaI18nResourceDTO();
        i18nResourceDTO.setKey(lastI18nKeys);
        i18nResourceDTO.setLocale(Locale.SIMPLIFIED_CHINESE);
        List i18nZHValues = this.vaI18nClient.queryList(i18nResourceDTO);
        i18nResourceDTO.setLocale(Locale.ENGLISH);
        List i18nENValues = this.vaI18nClient.queryList(i18nResourceDTO);
        HashMap<String, String> i18nZHMap = new HashMap<String, String>();
        HashMap<String, String> i18nENMap = new HashMap<String, String>();
        HashSet<String> keySet = new HashSet<String>();
        int i18nKeysSize = lastI18nKeys.size();
        for (int i = 0; i < i18nKeysSize; ++i) {
            String enValue;
            String zhValue = (String)i18nZHValues.get(i);
            if (StringUtils.hasText(zhValue)) {
                String key = (String)curr18nKeys.get(i);
                keySet.add(key);
                i18nZHMap.put(key, zhValue);
            }
            if (!StringUtils.hasText(enValue = (String)i18nENValues.get(i))) continue;
            String key = (String)curr18nKeys.get(i);
            keySet.add(key);
            i18nENMap.put(key, enValue);
        }
        if (keySet.isEmpty()) {
            return;
        }
        ArrayList<VaI18nResourceInitDTO> resourceInitDTOs = new ArrayList<VaI18nResourceInitDTO>();
        for (String key : keySet) {
            String zh = (String)i18nZHMap.get(key);
            String en = (String)i18nENMap.get(key);
            VaI18nResourceInitDTO initDTO = new VaI18nResourceInitDTO();
            initDTO.setMessageKey(key);
            initDTO.setEn_us(en);
            initDTO.setZh_cn(zh);
            resourceInitDTOs.add(initDTO);
        }
        VaI18nResourceBatchDTO resourceBatchDTO = new VaI18nResourceBatchDTO();
        resourceBatchDTO.setResourceList(resourceInitDTOs);
        this.vaI18nClient.initI18nResource(resourceBatchDTO);
    }

    private List<String> getWorkflowEle(ArrayNode lastArrayNode) {
        ArrayList<String> lastNodeCodes = new ArrayList<String>();
        for (JsonNode jsonNode : lastArrayNode) {
            String resourceId = jsonNode.get("resourceId").asText();
            lastNodeCodes.add(resourceId);
            JsonNode stencil = jsonNode.get("stencil");
            if (!"SubProcess".equals(stencil.get("id").asText())) continue;
            ArrayNode childArrayNode = (ArrayNode)jsonNode.get("childShapes");
            for (JsonNode child : childArrayNode) {
                String childResourceId = child.get("resourceId").asText();
                lastNodeCodes.add(childResourceId);
            }
        }
        return lastNodeCodes;
    }

    public Integer getworkflowDefineVersion(TenantDO tenantDO) {
        return this.workflowMetaDao.getworkflowDefineVersion(tenantDO);
    }

    public R getWorkflowMetaRelation(TenantDO tenantDO) {
        R r = new R();
        ArrayList relations = new ArrayList(Optional.ofNullable(this.workflowMetaDao.getWorkflowMetaRelation(tenantDO)).orElse(Collections.emptyList()));
        r.put("relations", relations);
        return r;
    }

    public R getWorkflowMetaRelationAll(TenantDO tenantDO) {
        R r = new R();
        ArrayList relations = new ArrayList(Optional.ofNullable(this.workflowMetaDao.getWorkflowMetaRelationAll(tenantDO)).orElse(Collections.emptyList()));
        r.put("relations", relations);
        return r;
    }

    public Long getWorkflowMetaVersion(TenantDO tenantDO) {
        return this.workflowMetaDao.getMetaVersion(tenantDO);
    }
}

