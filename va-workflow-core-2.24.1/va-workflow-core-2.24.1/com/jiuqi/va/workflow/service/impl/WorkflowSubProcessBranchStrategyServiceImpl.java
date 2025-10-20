/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategy
 *  com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategyManager
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.WorkflowOperation
 *  com.jiuqi.va.domain.workflow.service.WorkflowSubProcessBranchStrategyService
 *  com.jiuqi.va.feign.client.SubProcessBranchStrategyClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategy;
import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategyManager;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.WorkflowOperation;
import com.jiuqi.va.domain.workflow.service.WorkflowSubProcessBranchStrategyService;
import com.jiuqi.va.feign.client.SubProcessBranchStrategyClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import com.jiuqi.va.workflow.config.SubProcessBranchStrategyModuleConfig;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorkflowSubProcessBranchStrategyServiceImpl
implements WorkflowSubProcessBranchStrategyService {
    @Autowired
    private SubProcessBranchStrategyManager subProcessBranchStrategyManager;
    @Autowired
    private SubProcessBranchStrategyModuleConfig subProcessBranchStrategyModuleConfig;
    @Autowired
    private VaTransMessageService vaTransMessageService;
    @Autowired
    private JoinTemplate joinTemplate;

    public Map<String, Object> execute(String strategyModuleName, String strategyName, Map<String, Object> params) {
        Map<String, Object> branchMap = new LinkedHashMap<String, Object>();
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map customParam = vaWorkflowContext.getCustomParam();
        WorkflowModel workflowModel = (WorkflowModel)customParam.get("workflowModel");
        ProcessParamPlugin processParamPlugin = (ProcessParamPlugin)workflowModel.getPlugins().get(ProcessParamPlugin.class);
        ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)processParamPlugin.getDefine();
        List<ProcessParam> processParams = processParamPluginDefine.getProcessParam();
        params.put("processParams", processParams);
        if ("general".equalsIgnoreCase(strategyModuleName)) {
            List strategies = this.subProcessBranchStrategyManager.getStrategyList(strategyModuleName);
            for (SubProcessBranchStrategy strategy : strategies) {
                if (!strategyName.equalsIgnoreCase(strategy.getName())) continue;
                branchMap = strategy.execute(params);
                break;
            }
        } else {
            List<SubProcessBranchStrategyModule> modules = this.subProcessBranchStrategyModuleConfig.getModules();
            for (SubProcessBranchStrategyModule strategyModule : modules) {
                if (!strategyModuleName.equalsIgnoreCase(strategyModule.getName())) continue;
                SubProcessBranchStrategyClient strategyClient = (SubProcessBranchStrategyClient)FeignUtil.getDynamicClient(SubProcessBranchStrategyClient.class, (String)strategyModule.getAppName(), (String)strategyModule.getAppPath());
                TenantDO tenantDO = new TenantDO();
                tenantDO.addExtInfo("params", params);
                tenantDO.addExtInfo("strategyName", (Object)strategyName);
                tenantDO.addExtInfo("strategyModuleName", (Object)strategyModuleName);
                R r = strategyClient.execute(tenantDO);
                Map branches = JSONUtil.parseMap((String)String.valueOf(r.get((Object)"branchs")));
                branchMap.putAll(branches);
            }
        }
        return branchMap;
    }

    public boolean checkSubProcessRetract(String branchStrategyModule, String subProcessBranchStrategyName, WorkflowDTO workflowDTO) {
        List strategies = this.subProcessBranchStrategyManager.getStrategyList(branchStrategyModule);
        for (SubProcessBranchStrategy strategy : strategies) {
            if (!subProcessBranchStrategyName.equalsIgnoreCase(strategy.getName())) continue;
            return strategy.checkRetract((Object)workflowDTO);
        }
        return false;
    }

    public String getSubProcessRetractMq(String branchStrategyModule, String subProcessBranchStrategyName) {
        List strategies = this.subProcessBranchStrategyManager.getStrategyList(branchStrategyModule);
        for (SubProcessBranchStrategy strategy : strategies) {
            if (!subProcessBranchStrategyName.equalsIgnoreCase(strategy.getName())) continue;
            return strategy.getRetractMq();
        }
        return null;
    }

    public void retract(WorkflowDTO workflowDTO, List<Map<String, Object>> branchStrategyList, WorkflowOperation operation, Map<String, Object> customParam, boolean errorFlag) {
        for (Map<String, Object> branchStrategy : branchStrategyList) {
            HashMap<String, Object> msgContent;
            String mqName = VaWorkflowUtils.getSubProcessRetractMq(branchStrategy);
            if (!StringUtils.hasText(mqName)) continue;
            if (errorFlag) {
                HashMap<String, Object> msgContent2 = new HashMap<String, Object>();
                msgContent2.put("workflowDTO", JSONUtil.toJSONString((Object)workflowDTO));
                msgContent2.put("strategyName", branchStrategy.get("subProcessBranchStrategyName"));
                this.joinTemplate.sendAndReceive(mqName, JSONUtil.toJSONString(msgContent2));
                continue;
            }
            Object subProcessEndRetractObj = customParam.get("subProcessEndRetract");
            Object subBranchEndNodeRetractObj = customParam.get("subBranchEndNodeRetract");
            if (subProcessEndRetractObj != null && ((Boolean)subProcessEndRetractObj).booleanValue()) {
                msgContent = new HashMap<String, Object>(4);
                msgContent.put("workflowDTO", JSONUtil.toJSONString((Object)workflowDTO));
                msgContent.put("subProcessRetractType", "subProcessEndRetract");
                msgContent.put("subProcessRetractBranch", customParam.get("transmitRetractSubBranch"));
                this.joinTemplate.sendAndReceive(mqName, JSONUtil.toJSONString(msgContent));
                continue;
            }
            if (subBranchEndNodeRetractObj == null || !((Boolean)subBranchEndNodeRetractObj).booleanValue()) continue;
            msgContent = new HashMap(4);
            msgContent.put("workflowDTO", JSONUtil.toJSONString((Object)workflowDTO));
            msgContent.put("subProcessRetractType", "subBranchEndNodeRetract");
            msgContent.put("subProcessRetractBranch", customParam.get("transmitRetractSubBranch"));
            this.joinTemplate.sendAndReceive(mqName, JSONUtil.toJSONString(msgContent));
        }
    }
}

