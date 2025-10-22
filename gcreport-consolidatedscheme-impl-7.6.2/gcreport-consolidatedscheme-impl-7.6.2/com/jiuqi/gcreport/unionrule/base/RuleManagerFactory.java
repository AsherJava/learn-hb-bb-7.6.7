/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.unionrule.base;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RuleManagerFactory {
    @Autowired(required=false)
    List<UnionRuleManager> rules;

    public UnionRuleManager getUnionRuleManager(String ruleType) {
        if (CollectionUtils.isEmpty(this.rules)) {
            return null;
        }
        return this.rules.stream().filter(rule -> {
            RuleType annotation = rule.getClass().getAnnotation(RuleType.class);
            if (Objects.isNull(annotation)) {
                return false;
            }
            return ruleType.equals(annotation.code());
        }).findFirst().orElse(null);
    }

    public List<RuleType> getAllRuleType() {
        if (CollectionUtils.isEmpty(this.rules)) {
            return CollectionUtils.newArrayList();
        }
        return this.rules.stream().map(ruleHandler -> ruleHandler.getClass().getAnnotation(RuleType.class)).sorted(Comparator.comparing(RuleType::order).reversed()).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public RuleType getRuleType(String ruleType) {
        if (CollectionUtils.isEmpty(this.rules)) {
            return null;
        }
        return this.rules.stream().map(ruleHandler -> ruleHandler.getClass().getAnnotation(RuleType.class)).filter(Objects::nonNull).filter(type -> type.code().equals(ruleType)).findFirst().orElse(null);
    }

    public static Map<String, String> convertRuleType2Map(RuleType type) {
        HashMap<String, String> map = new HashMap<String, String>(16);
        map.put("code", type.code());
        map.put("name", type.name());
        return map;
    }
}

