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
import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.biz.intf.strategy.StrategyManager;
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
@RequestMapping(value={"/biz/strategy"})
public class StrategyController {
    private static final Logger log = LoggerFactory.getLogger(StrategyController.class);
    @Autowired
    private StrategyManager strategyManager;

    @PostMapping(value={"/list"})
    public R list(@RequestBody TenantDO tenantDO) {
        R r = R.ok();
        try {
            String strategyModule = (String)tenantDO.getExtInfo("strategyModule");
            List<Strategy> list = this.strategyManager.getStrategyList(strategyModule);
            list = list.stream().sorted(Comparator.comparing(Strategy::getOrder)).collect(Collectors.toList());
            ObjectMapper mapper = new ObjectMapper();
            r.put("strategys", (Object)mapper.writeValueAsString(list));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)BizBindingI18nUtil.getMessage("va.bizbinding.strategycontroller.getfailed"));
        }
        return r;
    }

    @PostMapping(value={"/execute"})
    public R execute(@RequestBody TenantDO tenantDO) {
        if (tenantDO.getExtInfo("strategyName") == null) {
            return R.error((String)BizBindingI18nUtil.getMessage("va.bizbinding.strategycontroller.notfountstrategyname"));
        }
        String strategyName = (String)tenantDO.getExtInfo("strategyName");
        String strategyModuleName = (String)tenantDO.getExtInfo("strategyModuleName");
        List<Strategy> list = this.strategyManager.getStrategyList(strategyModuleName);
        Stream<Strategy> stram = list.stream().filter(item -> item.getName().equals(strategyName));
        if (stram.count() <= 0L) {
            return R.error((String)BizBindingI18nUtil.getMessage("va.bizbinding.autotask.notfountautotask", new Object[]{strategyName}));
        }
        Strategy strategy = list.stream().filter(item -> item.getName().equalsIgnoreCase(strategyName)).findFirst().get();
        R r = R.ok();
        ObjectMapper mapper = new ObjectMapper();
        try {
            r.put("users", (Object)mapper.writeValueAsString(strategy.execute(tenantDO)));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)"\u83b7\u53d6\u5931\u8d25");
        }
        return r;
    }
}

