/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 *  com.jiuqi.va.biz.intf.strategy.StrategyManager
 *  com.jiuqi.va.domain.biz.StrategyDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.role.RoleDO
 *  com.jiuqi.va.domain.role.RoleDTO
 *  com.jiuqi.va.domain.workflow.StrategyModule
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  com.jiuqi.va.feign.client.StrategyClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.biz.intf.strategy.StrategyManager;
import com.jiuqi.va.domain.biz.StrategyDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.role.RoleDO;
import com.jiuqi.va.domain.role.RoleDTO;
import com.jiuqi.va.domain.workflow.StrategyModule;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.feign.client.StrategyClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.StrategyModuleConfig;
import com.jiuqi.va.workflow.domain.StrategyTree;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/strategy"})
public class WorkflowStrategyController {
    public static final String PUBLIC_ROLE_ID = "ffffffff-ffff-ffff-bbbb-ffffffffffff";
    @Autowired
    private StrategyModuleConfig strategyModuleConfig;
    @Autowired
    private StrategyManager strategyManager;
    @Autowired
    private AuthRoleClient authRoleClient;

    @GetMapping(value={"/list"})
    @Deprecated
    public R list() {
        return this.list(new TenantDO());
    }

    @PostMapping(value={"/list"})
    public R list(@RequestBody TenantDO tenantDO) {
        R result = new R();
        ArrayList<StrategyTree> strategyTreeList = new ArrayList<StrategyTree>();
        StrategyTree generalStrategyTree = new StrategyTree();
        StrategyModule generalStrategyModule = new StrategyModule();
        generalStrategyModule.setName("general");
        generalStrategyModule.setTitle("\u901a\u7528");
        List list = this.strategyManager.getStrategyList("general");
        ArrayList<StrategyDTO> generalStrategys = new ArrayList<StrategyDTO>();
        for (Strategy strategy : list) {
            StrategyDTO strategyDTO = new StrategyDTO();
            BeanUtils.copyProperties(strategy, strategyDTO);
            generalStrategys.add(strategyDTO);
        }
        generalStrategyTree.setStrategyModule(generalStrategyModule);
        generalStrategyTree.setStrategys(generalStrategys);
        strategyTreeList.add(generalStrategyTree);
        List<StrategyModule> modules = this.strategyModuleConfig.getModules();
        if (modules != null && !modules.isEmpty()) {
            for (StrategyModule strategyModule : modules) {
                StrategyClient strategyClient = (StrategyClient)FeignUtil.getDynamicClient(StrategyClient.class, (String)strategyModule.getAppName(), (String)strategyModule.getAppPath());
                tenantDO = new TenantDO();
                tenantDO.addExtInfo("strategyModule", (Object)strategyModule.getName());
                R r = strategyClient.getStrategyList(tenantDO);
                List strategys = JSONUtil.parseArray((String)String.valueOf(r.get((Object)"strategys")), StrategyDTO.class);
                StrategyTree strategyTree = new StrategyTree();
                strategyTree.setStrategyModule(strategyModule);
                strategyTree.setStrategys(strategys);
                strategyTreeList.add(strategyTree);
            }
        }
        result.put("strategyTree", strategyTreeList);
        return result;
    }

    @PostMapping(value={"/role/list"})
    PageVO<RoleDO> list(@RequestBody RoleDTO role) {
        PageVO list = this.authRoleClient.list(role);
        List rows = list.getRows();
        if (!CollectionUtils.isEmpty(rows)) {
            rows.removeIf(roleDO -> PUBLIC_ROLE_ID.equals(roleDO.getId()));
            list.setTotal(list.getTotal() - 1);
        }
        return list;
    }
}

