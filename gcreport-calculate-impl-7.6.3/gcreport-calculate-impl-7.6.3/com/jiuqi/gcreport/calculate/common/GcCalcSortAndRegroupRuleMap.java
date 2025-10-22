/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.common;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.enums.GcCalcRuleDispatcherPriorityEnum;
import com.jiuqi.gcreport.calculate.service.GcCalcRuleDispatcherService;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class GcCalcSortAndRegroupRuleMap {
    private GcCalcRuleDispatcherService dispatcherService;
    private final Map<GcCalcRuleDispatcher, List<AbstractUnionRule>> ruleDispatcherMap = new ConcurrentHashMap<GcCalcRuleDispatcher, List<AbstractUnionRule>>();
    private final Map<GcCalcRuleDispatcherPriorityEnum, HashSet<GcCalcRuleDispatcher>> dispatcherPriorityMap = new ConcurrentSkipListMap<GcCalcRuleDispatcherPriorityEnum, HashSet<GcCalcRuleDispatcher>>((o1, o2) -> Integer.valueOf(o2.getOrder()).compareTo(o1.getOrder()));

    public GcCalcSortAndRegroupRuleMap() {
        this.dispatcherService = (GcCalcRuleDispatcherService)SpringContextUtils.getBean(GcCalcRuleDispatcherService.class);
    }

    public void putAll(GcCalcEnvContext env, List<AbstractUnionRule> rules) {
        if (rules == null || rules.size() == 0) {
            return;
        }
        rules.stream().forEach(rule -> this.put(env, (AbstractUnionRule)rule));
    }

    public void put(GcCalcEnvContext env, AbstractUnionRule rule) {
        if (rule == null) {
            return;
        }
        GcCalcRuleDispatcher ruleDispatcher = this.dispatcherService.findGroupDispatcherByRule(rule, env);
        if (this.ruleDispatcherMap.get(ruleDispatcher) == null) {
            this.ruleDispatcherMap.put(ruleDispatcher, Collections.synchronizedList(new ArrayList()));
        }
        this.ruleDispatcherMap.get(ruleDispatcher).add(rule);
        GcCalcRuleDispatcherPriorityEnum dispatcherPriority = ruleDispatcher.getDispatcherPriority();
        if (this.dispatcherPriorityMap.get((Object)dispatcherPriority) == null) {
            this.dispatcherPriorityMap.put(dispatcherPriority, new HashSet());
        }
        this.dispatcherPriorityMap.get((Object)dispatcherPriority).add(ruleDispatcher);
    }

    public Map<GcCalcRuleDispatcher, List<AbstractUnionRule>> getRuleDispatcherMap() {
        return Collections.unmodifiableMap(this.ruleDispatcherMap);
    }

    public List<AbstractUnionRule> getRulesByDispatcher(GcCalcRuleDispatcher dispatcher) {
        List<AbstractUnionRule> rules = Collections.synchronizedList(this.ruleDispatcherMap.get(dispatcher));
        return Collections.unmodifiableList(rules);
    }

    public Map<GcCalcRuleDispatcherPriorityEnum, HashSet<GcCalcRuleDispatcher>> getSortDispatcherPriorityMap() {
        return Collections.unmodifiableMap(this.dispatcherPriorityMap);
    }
}

