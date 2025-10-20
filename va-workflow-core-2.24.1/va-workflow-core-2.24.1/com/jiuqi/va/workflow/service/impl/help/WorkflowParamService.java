/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.workflow.BizType
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service.impl.help;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.workflow.BizType;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelDefine;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPlugin;
import com.jiuqi.va.workflow.plugin.processdesin.ProcessDesignPluginDefine;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="workflowParamService")
public class WorkflowParamService {
    @Autowired
    private BizTypeConfig bizTypeConfig;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private ModelDefineService modelDefineService;

    public WorkflowModel getModel(String uniqueCode, Long processDefineVersion) {
        WorkflowModelDefine workflowModelDefine = (WorkflowModelDefine)this.modelDefineService.getDefine(uniqueCode, processDefineVersion);
        return (WorkflowModel)this.modelDefineService.createModel(null, (ModelDefine)workflowModelDefine);
    }

    public JsonNode getWorkflowProcess(WorkflowModel workflowModel) {
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        return processDesignPluginDefine.getData();
    }

    public ArrayNode getWorkflowProcessNode(WorkflowModel workflowModel) {
        ProcessDesignPlugin processDesignPlugin = (ProcessDesignPlugin)workflowModel.getPlugins().get(ProcessDesignPlugin.class);
        ProcessDesignPluginDefine processDesignPluginDefine = (ProcessDesignPluginDefine)processDesignPlugin.getDefine();
        return (ArrayNode)processDesignPluginDefine.getData().get("childShapes");
    }

    public Map<String, Object> loadWorkflowVariables(String bizCode, String bizDefine, BigDecimal version, String bizType, String defineKey) {
        BussinessClient bussinessClient;
        if ("BILL".equals(bizType)) {
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setModuleName(bizDefine.split("_")[0]);
            moduleDTO.setFunctionType("BILL");
            R r = this.metaDataClient.getModuleByName(moduleDTO);
            String server = String.valueOf(r.get((Object)"server"));
            String path = String.valueOf(r.get((Object)"path"));
            bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
        } else {
            BizType bizTypeConfig = this.getBizTypeConfig(bizType);
            if (bizTypeConfig == null) {
                return null;
            }
            bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)bizTypeConfig.getAppName(), (String)bizTypeConfig.getAppPath());
        }
        TenantDO tenant = new TenantDO();
        tenant.setTraceId(Utils.getTraceId());
        tenant.addExtInfo("bizCode", (Object)bizCode);
        tenant.addExtInfo("bizType", (Object)bizDefine);
        tenant.addExtInfo("workflowdefinekey", (Object)defineKey);
        tenant.addExtInfo("workflowdefineversion", (Object)version);
        Map workflowVariables = bussinessClient.getBussinessParamVariables(tenant);
        return workflowVariables;
    }

    private BizType getBizTypeConfig(String bizType) {
        List<BizType> bizTypes = this.bizTypeConfig.getTypes();
        for (BizType bizTypeConfig : bizTypes) {
            if (!bizType.equalsIgnoreCase(bizTypeConfig.getName())) continue;
            return bizTypeConfig;
        }
        return null;
    }
}

