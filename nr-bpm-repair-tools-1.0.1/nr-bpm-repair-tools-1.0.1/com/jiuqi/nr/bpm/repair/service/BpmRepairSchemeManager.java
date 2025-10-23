/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.repair.service;

import com.jiuqi.nr.bpm.repair.scheme.BpmRepairScheme;
import com.jiuqi.nr.bpm.repair.web.param.BpmRepairSchemeInfo;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BpmRepairSchemeManager {
    private final Map<String, BpmRepairScheme> sourceMap = new HashMap<String, BpmRepairScheme>();
    private final List<BpmRepairScheme> allSchemes;

    @Autowired(required=true)
    public BpmRepairSchemeManager(List<BpmRepairScheme> bpmRepairSchemes) {
        this.allSchemes = bpmRepairSchemes.stream().sorted(new Comparator<BpmRepairScheme>(){

            @Override
            public int compare(BpmRepairScheme o1, BpmRepairScheme o2) {
                if (o1.getOrder() > o2.getOrder()) {
                    return -1;
                }
                if (o1.getOrder() < o2.getOrder()) {
                    return 1;
                }
                return 0;
            }
        }).collect(Collectors.toList());
        for (BpmRepairScheme bpmRepairScheme : bpmRepairSchemes) {
            this.sourceMap.put(bpmRepairScheme.getCode(), bpmRepairScheme);
        }
    }

    public BpmRepairScheme getSchemeByCode(String code) {
        return this.sourceMap.get(code);
    }

    public List<BpmRepairSchemeInfo> getAllSchemes() {
        return this.allSchemes.stream().map(e -> new BpmRepairSchemeInfo(e.getCode(), e.getTitle())).collect(Collectors.toList());
    }
}

