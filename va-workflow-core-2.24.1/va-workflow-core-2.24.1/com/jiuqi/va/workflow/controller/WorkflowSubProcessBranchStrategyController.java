/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategy
 *  com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategyManager
 *  com.jiuqi.va.domain.biz.StrategyDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule
 *  com.jiuqi.va.feign.client.SubProcessBranchStrategyClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategy;
import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategyManager;
import com.jiuqi.va.domain.biz.StrategyDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule;
import com.jiuqi.va.feign.client.SubProcessBranchStrategyClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.SubProcessBranchStrategyModuleConfig;
import com.jiuqi.va.workflow.domain.SubProcessBranchStrategyTree;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/subprocess/branch/strategy"})
public class WorkflowSubProcessBranchStrategyController {
    @Autowired
    private SubProcessBranchStrategyModuleConfig subProcessBranchStrategyModuleConfig;
    @Autowired
    private SubProcessBranchStrategyManager subProcessBranchStrategyManager;

    @PostMapping(value={"/list"})
    public R list(@RequestBody TenantDO tenantDO) {
        R result = new R();
        ArrayList<SubProcessBranchStrategyTree> strategyTreeList = new ArrayList<SubProcessBranchStrategyTree>();
        SubProcessBranchStrategyTree generalStrategyTree = new SubProcessBranchStrategyTree();
        SubProcessBranchStrategyModule generalStrategyModule = new SubProcessBranchStrategyModule();
        generalStrategyModule.setName("general");
        generalStrategyModule.setTitle("\u901a\u7528");
        List list = this.subProcessBranchStrategyManager.getStrategyList("general");
        ArrayList<StrategyDTO> generalStrategys = new ArrayList<StrategyDTO>();
        for (SubProcessBranchStrategy strategy : list) {
            StrategyDTO strategyDTO = new StrategyDTO();
            BeanUtils.copyProperties(strategy, strategyDTO);
            generalStrategys.add(strategyDTO);
        }
        generalStrategyTree.setSubProcessBranchStrategyModule(generalStrategyModule);
        generalStrategyTree.setStrategys(generalStrategys);
        strategyTreeList.add(generalStrategyTree);
        List<SubProcessBranchStrategyModule> modules = this.subProcessBranchStrategyModuleConfig.getModules();
        if (modules != null && !modules.isEmpty()) {
            for (SubProcessBranchStrategyModule strategyModule : modules) {
                SubProcessBranchStrategyClient strategyClient = (SubProcessBranchStrategyClient)FeignUtil.getDynamicClient(SubProcessBranchStrategyClient.class, (String)strategyModule.getAppName(), (String)strategyModule.getAppPath());
                tenantDO = new TenantDO();
                tenantDO.addExtInfo("strategyModule", (Object)strategyModule.getName());
                R r = strategyClient.getStrategyList(tenantDO);
                List strategys = JSONUtil.parseArray((String)String.valueOf(r.get((Object)"strategys")), StrategyDTO.class);
                SubProcessBranchStrategyTree strategyTree = new SubProcessBranchStrategyTree();
                strategyTree.setSubProcessBranchStrategyModule(strategyModule);
                strategyTree.setStrategys(strategys);
                strategyTreeList.add(strategyTree);
            }
        }
        result.put("subProcessBranchStrategyTree", strategyTreeList);
        return result;
    }
}

