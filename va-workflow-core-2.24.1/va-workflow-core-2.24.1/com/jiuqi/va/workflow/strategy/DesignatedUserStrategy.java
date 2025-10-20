/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 */
package com.jiuqi.va.workflow.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.biz.intf.strategy.Strategy;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class DesignatedUserStrategy
implements Strategy {
    public String getName() {
        return "designatedUser";
    }

    public String getTitle() {
        return "\u6307\u5b9a\u7528\u6237";
    }

    public String getOrder() {
        return "001";
    }

    public String getStrategyModule() {
        return "general";
    }

    public Set<String> execute(Object params) {
        LinkedHashSet<String> list = new LinkedHashSet<String>();
        Map param = (Map)params;
        ArrayNode paramsList = (ArrayNode)param.get("assignParam");
        for (JsonNode node : paramsList) {
            list.add(node.get("value").asText());
        }
        return list;
    }
}

