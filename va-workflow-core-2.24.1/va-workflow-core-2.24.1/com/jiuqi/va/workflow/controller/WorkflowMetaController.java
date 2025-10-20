/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.impl.model.ModelDefineImpl
 *  com.jiuqi.va.biz.impl.model.PluginCheckManager
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.value.TypedContainer
 *  com.jiuqi.va.domain.biz.DeployDTO
 *  com.jiuqi.va.domain.biz.DeployType
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.biz.ModelDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.service.WorkflowMetaService
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginCheckManager;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.domain.biz.DeployDTO;
import com.jiuqi.va.domain.biz.DeployType;
import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.biz.ModelDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.service.WorkflowMetaService;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow"})
public class WorkflowMetaController {
    private static final Logger log = LoggerFactory.getLogger(WorkflowMetaController.class);
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private WorkflowSevice workflowSevice;
    @Autowired
    private WorkflowMetaService workflowMetaService;
    @Autowired
    private PluginCheckManager pluginCheckManager;

    @PostMapping(value={"/models/get"})
    public PageVO<ModelDTO> getModels(@RequestBody ModelDTO modelDTO) {
        List models = this.modelManager.getModelList(modelDTO.getMetaType());
        ArrayList<ModelDTO> resultList = new ArrayList<ModelDTO>();
        if (models != null && !models.isEmpty()) {
            for (ModelType modelType : models) {
                ModelDTO resultModelDTO = new ModelDTO();
                resultModelDTO.setModelName(modelType.getName());
                resultModelDTO.setModelTitle(modelType.getTitle());
                resultModelDTO.setMetaType(modelType.getMetaType());
                resultModelDTO.setModule(modelDTO.getModule());
                resultList.add(resultModelDTO);
            }
        }
        return new PageVO(resultList, resultList.size());
    }

    @PostMapping(value={"/metas/get"})
    public R createDefine(@RequestBody MetaDataDTO metaDataDTO) {
        R r = new R();
        try {
            ModelType modelType = (ModelType)this.modelManager.find(metaDataDTO.getModelName());
            WorkflowModelDefine workflowDefine = (WorkflowModelDefine)modelType.getModelDefineClass().newInstance();
            modelType.initModelDefine((ModelDefine)workflowDefine, metaDataDTO.getUniqueCode());
            ObjectMapper mapper = new ObjectMapper();
            String workflowDefineString = mapper.writeValueAsString((Object)workflowDefine);
            r.put("data", (Object)workflowDefineString);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.getfailed"));
        }
        return r;
    }

    @PostMapping(value={"/version/get"})
    public R getworkflowDefineVersions(@RequestBody TenantDO tenantDO) {
        R r = new R();
        try {
            List params = (List)tenantDO.getExtInfo("params");
            for (Map param : params) {
                tenantDO = new TenantDO();
                tenantDO.addExtInfo("metaVersion", param.get("workflowdefineversion"));
                tenantDO.addExtInfo("workflowDefineKey", param.get("workflowdefinekey"));
                Integer workflowDefineVer = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
                param.put("simpleworkflowdefineversion", workflowDefineVer);
            }
            r.put("versionRelation", (Object)params);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.getfailed"));
        }
        return r;
    }

    @PostMapping(value={"/meta/relation"})
    public R gatherMetaVersionRelation(@RequestBody TenantDO tenantDO) {
        R r = new R();
        try {
            r = this.workflowMetaService.getWorkflowMetaRelation(tenantDO);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.getfailed"));
        }
        return r;
    }

    @PostMapping(value={"/publish"})
    public R publishMeta(@RequestBody DeployDTO deployDTO) {
        int workflowVersion;
        if (DeployType.DELETE.equals((Object)deployDTO.getDeployType())) {
            try {
                this.workflowMetaService.publish(deployDTO, null);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                return R.error((String)e.getMessage());
            }
        }
        try {
            workflowVersion = this.workflowSevice.deploy(deployDTO);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        if (workflowVersion > 0) {
            try {
                this.workflowMetaService.publish(deployDTO, Integer.valueOf(workflowVersion));
            }
            catch (Exception e) {
                this.workflowSevice.rollbackDeploy(deployDTO.getUniqueCode(), workflowVersion);
                log.error(e.getMessage(), e);
                return R.error((String)e.getMessage());
            }
        }
        return R.ok();
    }

    @PostMapping(value={"/published/update"})
    public R updatepublished(@RequestBody DeployDTO deployDTO) {
        try {
            TenantDO tenantDO = new TenantDO();
            tenantDO.addExtInfo("workflowDefineKey", (Object)deployDTO.getUniqueCode());
            tenantDO.addExtInfo("metaVersion", (Object)deployDTO.getVersion());
            Integer workflowDefineVersion = this.workflowMetaService.getworkflowDefineVersion(tenantDO);
            if (workflowDefineVersion != null) {
                deployDTO.setDatas(deployDTO.getDatas());
                this.workflowSevice.updateWorkflowDefine(deployDTO, workflowDefineVersion.intValue());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    @PostMapping(value={"/meta/datas/check"})
    public R checkMetaData(@RequestBody MetaDataDTO metaDataDTO) {
        R result = new R();
        ArrayList resultList = new ArrayList();
        String datas = metaDataDTO.getDatas();
        try {
            Map rootObject = JSONUtil.parseMap((String)datas);
            String modelTypeName = rootObject.get("modelType").toString();
            ModelType modelType = (ModelType)this.modelManager.get(modelTypeName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            ModelDefineImpl modelDefine = (ModelDefineImpl)mapper.readValue(datas, modelType.getModelDefineClass());
            TypedContainer definePlugins = modelDefine.getPlugins();
            definePlugins.forEachIndex((integer, pluginDefine) -> {
                String pluginName = pluginDefine.getType();
                PluginCheck pluginCheck = null;
                try {
                    pluginCheck = (PluginCheck)this.pluginCheckManager.get(pluginName);
                }
                catch (Exception e) {
                    log.warn(e.getMessage());
                    return;
                }
                PluginCheckResultVO pluginCheckResultVO = pluginCheck.checkPlugin(pluginDefine, (ModelDefine)modelDefine);
                resultList.add(pluginCheckResultVO);
            });
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.checkdatafailed"));
        }
        result.put("checkInfos", resultList);
        return result;
    }

    @PostMapping(value={"/meta/getWorkflowVersion"})
    public R getWorkflowVersion(@RequestBody TenantDO tenantDOS) {
        return this.workflowMetaService.getWorkflowMetaRelationAll(tenantDOS);
    }
}

