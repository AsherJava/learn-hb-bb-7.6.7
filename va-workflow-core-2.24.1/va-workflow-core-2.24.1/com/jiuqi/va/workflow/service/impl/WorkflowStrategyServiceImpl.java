/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 *  com.jiuqi.va.biz.intf.strategy.StrategyManager
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.StrategyModule
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.StrategyClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.biz.intf.strategy.StrategyManager;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.StrategyModule;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.service.WorkflowStrategySevice;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.StrategyClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.StrategyModuleConfig;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPlugin;
import com.jiuqi.va.workflow.plugin.processparam.ProcessParamPluginDefine;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowStrategyServiceImpl
implements WorkflowStrategySevice {
    private static final Logger log = LoggerFactory.getLogger(WorkflowStrategyServiceImpl.class);
    private final int USER_STOP_FLAG = 1;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private StrategyManager strategyManager;
    @Autowired
    private StrategyModuleConfig strategyModuleConfig;

    public Set<String> execute(String strategyModuleName, String strategyName, Map<String, Object> params) {
        WorkflowDTO workflowDTO;
        Set users = new LinkedHashSet();
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        if (Objects.nonNull(vaWorkflowContext)) {
            Map customParam = vaWorkflowContext.getCustomParam();
            Iterator workflowModel = (WorkflowModel)customParam.get("workflowModel");
            ProcessParamPlugin processParamPlugin = (ProcessParamPlugin)workflowModel.getPlugins().get(ProcessParamPlugin.class);
            ProcessParamPluginDefine processParamPluginDefine = (ProcessParamPluginDefine)processParamPlugin.getDefine();
            List<ProcessParam> processParams = processParamPluginDefine.getProcessParam();
            params.put("processParams", processParams);
        }
        if ("general".equalsIgnoreCase(strategyModuleName)) {
            List strategys = this.strategyManager.getStrategyList(strategyModuleName);
            for (Strategy strategy : strategys) {
                if (!strategyName.equalsIgnoreCase(strategy.getName())) continue;
                users = strategy.execute(params);
                break;
            }
        } else {
            List<StrategyModule> list = this.strategyModuleConfig.getModules();
            for (StrategyModule strategyModule : list) {
                if (!strategyModuleName.equalsIgnoreCase(strategyModule.getName())) continue;
                StrategyClient strategyClient = (StrategyClient)FeignUtil.getDynamicClient(StrategyClient.class, (String)strategyModule.getAppName(), (String)strategyModule.getAppPath());
                TenantDO tenantDO = new TenantDO();
                tenantDO.addExtInfo("params", params);
                tenantDO.addExtInfo("strategyName", (Object)strategyName);
                tenantDO.addExtInfo("strategyModuleName", (Object)strategyModuleName);
                R r = strategyClient.execute(tenantDO);
                List userList = JSONUtil.parseArray((String)String.valueOf(r.get((Object)"users")), String.class);
                users.addAll(userList);
            }
        }
        LinkedHashSet<String> resultUsers = new LinkedHashSet<String>();
        for (String user : users) {
            UserDO userDO;
            block10: {
                userDO = null;
                UserDTO userDTO = new UserDTO();
                try {
                    UUID.fromString(user);
                    userDTO.setId(user);
                    userDO = this.authUserClient.get(userDTO);
                }
                catch (Exception e) {
                    userDTO.setUsername(user);
                    userDO = this.authUserClient.get(userDTO);
                    if (userDO != null) break block10;
                    userDTO = new UserDTO();
                    userDTO.setId(user);
                    userDO = this.authUserClient.get(userDTO);
                }
            }
            if (userDO == null || userDO.getStopflag() == 1) continue;
            resultUsers.add(userDO.getId());
        }
        if (resultUsers.isEmpty() && (workflowDTO = (WorkflowDTO)params.get("workflowDTO")) != null) {
            log.error("\u83b7\u53d6\u5ba1\u6279\u4eba\u4e3a\u7a7a\uff0c\u53c2\u4e0e\u8005\u7b56\u7565\u6a21\u578b\uff1a{}\uff0c\u53c2\u4e0e\u8005\u7b56\u7565\uff1a{}\uff0c\u4e1a\u52a1\u7f16\u7801\uff1a{}\uff0c\u6d41\u7a0b\u6807\u8bc6\uff1a{}", strategyModuleName, strategyName, workflowDTO.getBizCode(), workflowDTO.getUniqueCode());
        }
        return resultUsers;
    }
}

