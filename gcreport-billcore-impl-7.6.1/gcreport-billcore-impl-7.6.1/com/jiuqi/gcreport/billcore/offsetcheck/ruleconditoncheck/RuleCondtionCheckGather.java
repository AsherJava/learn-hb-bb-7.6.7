/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheck;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RuleCondtionCheckGather
implements InitializingBean {
    @Autowired(required=false)
    private List<RuleCondtionCheck> ruleCondtionCheckList;
    private Map<String, List<RuleCondtionCheck>> ruleType2CondtionCheckMap = new HashMap<String, List<RuleCondtionCheck>>();

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(this.ruleCondtionCheckList)) {
            this.ruleType2CondtionCheckMap = this.ruleCondtionCheckList.stream().collect(Collectors.groupingBy(item -> item.getRuleContionType()));
        }
    }

    public List<RuleCondtionCheck> getRuleCondtionCheckList(String ruleContionCode) {
        return this.ruleType2CondtionCheckMap.get(ruleContionCode);
    }
}

