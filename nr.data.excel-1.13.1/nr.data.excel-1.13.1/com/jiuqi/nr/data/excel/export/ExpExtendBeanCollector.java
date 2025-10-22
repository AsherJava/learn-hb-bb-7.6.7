/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.data.excel.export;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.excel.param.ExcelRule;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ExpExtendBeanCollector {
    private final Map<String, ExcelRule> excelRuleMap = new HashMap<String, ExcelRule>();

    public ExpExtendBeanCollector(Map<String, ExcelRule> excelRuleBeans) {
        for (Map.Entry<String, ExcelRule> e : excelRuleBeans.entrySet()) {
            if (StringUtils.isEmpty((String)e.getValue().name())) {
                throw new IllegalArgumentException("ExcelRule.name() must not return null or \"\"!-check your implementation:" + e.getKey());
            }
            if (this.excelRuleMap.containsKey(e.getValue().name())) {
                throw new IllegalArgumentException(e.getValue().name() + ":repeated ExcelRule.name(),try a new name! ");
            }
            this.excelRuleMap.put(e.getValue().name(), e.getValue());
        }
    }

    public ExcelRule getExcelRuleByName(String excelRuleName) {
        if (this.excelRuleMap.containsKey(excelRuleName)) {
            return this.excelRuleMap.get(excelRuleName);
        }
        throw new IllegalArgumentException("can not find excelRule by your name:" + excelRuleName);
    }
}

