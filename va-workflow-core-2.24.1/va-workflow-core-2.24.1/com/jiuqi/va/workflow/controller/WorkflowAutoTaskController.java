/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.autotask.AutoTask
 *  com.jiuqi.va.biz.intf.autotask.AutoTaskManager
 *  com.jiuqi.va.domain.biz.AutoTaskDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.AutoTaskModule
 *  com.jiuqi.va.feign.client.AutoTaskClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.workflow.controller;

import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.biz.intf.autotask.AutoTaskManager;
import com.jiuqi.va.domain.biz.AutoTaskDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.AutoTaskModule;
import com.jiuqi.va.feign.client.AutoTaskClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.config.AutoTaskModuleConfig;
import com.jiuqi.va.workflow.domain.AutoTaskTree;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/workflow/autotask"})
public class WorkflowAutoTaskController {
    @Autowired
    private AutoTaskModuleConfig autoTaskModuleConfig;
    @Autowired
    private AutoTaskManager autoTaskManager;

    @Deprecated
    @GetMapping(value={"/list"})
    public R list() {
        return this.list(new TenantDO());
    }

    @PostMapping(value={"/list"})
    public R list(@RequestBody TenantDO tenantDO) {
        R result = new R();
        ArrayList<AutoTaskTree> autoTaskTreeList = new ArrayList<AutoTaskTree>();
        AutoTaskTree generalAutoTaskTree = new AutoTaskTree();
        AutoTaskModule generalAutoTaskModule = new AutoTaskModule();
        generalAutoTaskModule.setName("general");
        generalAutoTaskModule.setTitle("\u901a\u7528");
        List list = this.autoTaskManager.getAutoTaskList("general");
        list = list.stream().sorted(Comparator.comparing(AutoTask::getOrder)).collect(Collectors.toList());
        ArrayList<AutoTaskDTO> generalAutoTasks = new ArrayList<AutoTaskDTO>();
        for (AutoTask autoTask : list) {
            AutoTaskDTO autoTaskDTO = new AutoTaskDTO();
            BeanUtils.copyProperties(autoTask, autoTaskDTO);
            generalAutoTasks.add(autoTaskDTO);
        }
        generalAutoTaskTree.setAutoTaskModule(generalAutoTaskModule);
        generalAutoTaskTree.setAutoTasks(generalAutoTasks);
        autoTaskTreeList.add(generalAutoTaskTree);
        List<AutoTaskModule> modules = this.autoTaskModuleConfig.getModules();
        if (modules != null && !modules.isEmpty()) {
            for (AutoTaskModule autoTaskModule : modules) {
                AutoTaskClient autoTaskClient = (AutoTaskClient)FeignUtil.getDynamicClient(AutoTaskClient.class, (String)autoTaskModule.getAppName(), (String)autoTaskModule.getAppPath());
                tenantDO = new TenantDO();
                tenantDO.addExtInfo("autoTaskModule", (Object)autoTaskModule.getName());
                R r = autoTaskClient.getAutoTaskList(tenantDO);
                List autoTasks = JSONUtil.parseArray((String)String.valueOf(r.get((Object)"autoTasks")), AutoTaskDTO.class);
                AutoTaskTree autoTaskTree = new AutoTaskTree();
                autoTaskTree.setAutoTaskModule(autoTaskModule);
                autoTaskTree.setAutoTasks(autoTasks);
                autoTaskTreeList.add(autoTaskTree);
            }
        }
        result.put("autoTaskTree", autoTaskTreeList);
        return result;
    }
}

