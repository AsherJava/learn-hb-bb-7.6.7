/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.biz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.biz.intf.autotask.AutoTaskManager;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/autotask"})
public class AutoTaskController {
    private static final Logger log = LoggerFactory.getLogger(AutoTaskController.class);
    @Autowired
    private AutoTaskManager autoTaskManager;

    @PostMapping(value={"/list"})
    public R getAutoTaskList(@RequestBody TenantDO tenantDO) {
        R r = R.ok();
        try {
            String autoTaskModule = (String)tenantDO.getExtInfo("autoTaskModule");
            List<AutoTask> list = this.autoTaskManager.getAutoTaskList(autoTaskModule);
            list = list.stream().sorted(Comparator.comparing(AutoTask::getOrder)).collect(Collectors.toList());
            ObjectMapper mapper = new ObjectMapper();
            r.put("autoTasks", (Object)mapper.writeValueAsString(list));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return R.error((int)1, (String)"\u83b7\u53d6\u5931\u8d25");
        }
        return r;
    }

    @PostMapping(value={"/execute"})
    public R execute(@RequestBody TenantDO tenantDO) {
        if (tenantDO.getExtInfo("autoTaskName") == null) {
            return R.error((String)BizBindingI18nUtil.getMessage("va.bizbinding.autotask.notfountname"));
        }
        String autoTaskName = (String)tenantDO.getExtInfo("autoTaskName");
        String autoTaskModuleName = (String)tenantDO.getExtInfo("autoTaskModuleName");
        List<AutoTask> list = this.autoTaskManager.getAutoTaskList(autoTaskModuleName);
        Stream<AutoTask> stram = list.stream().filter(item -> item.getName().equalsIgnoreCase(autoTaskName));
        if (stram.count() <= 0L) {
            return R.error((String)BizBindingI18nUtil.getMessage("va.bizbinding.autotask.notfountautotask", new Object[]{autoTaskName}));
        }
        AutoTask autoTask = list.stream().filter(item -> item.getName().equalsIgnoreCase(autoTaskName)).findFirst().get();
        return autoTask.execute(tenantDO);
    }

    @PostMapping(value={"/retract"})
    public R retract(@RequestBody TenantDO tenantDO) {
        if (tenantDO.getExtInfo("autoTaskName") == null) {
            return R.error((String)BizBindingI18nUtil.getMessage("va.bizbinding.autotask.notfountname"));
        }
        String autoTaskName = (String)tenantDO.getExtInfo("autoTaskName");
        String autoTaskModuleName = (String)tenantDO.getExtInfo("autoTaskModuleName");
        List<AutoTask> list = this.autoTaskManager.getAutoTaskList(autoTaskModuleName);
        Stream<AutoTask> stram = list.stream().filter(item -> item.getName().equalsIgnoreCase(autoTaskName));
        if (stram.count() <= 0L) {
            return R.error((String)BizBindingI18nUtil.getMessage("va.bizbinding.autotask.notfountautotask", new Object[]{autoTaskName}));
        }
        AutoTask autoTask = list.stream().filter(item -> item.getName().equalsIgnoreCase(autoTaskName)).findFirst().get();
        if (autoTask.canRetract().booleanValue()) {
            return autoTask.retrack(tenantDO);
        }
        return R.ok();
    }
}

