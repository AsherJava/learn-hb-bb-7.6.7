/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql;

import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomFetchCondiFieldHolder {
    private List<CustomCondition> modelCondiList;
    private Set<String> condiSet;

    public CustomFetchCondiFieldHolder(List<CustomCondition> modelCondiList) {
        this.modelCondiList = modelCondiList;
        this.condiSet = new HashSet<String>();
    }

    public void doAnalyze(Map<String, CustomCondition> condiMap) {
        for (String condiKey : condiMap.keySet()) {
            if (this.hasAnalyze(condiKey)) continue;
            this.condiSet.add(condiKey);
        }
    }

    public List<String> getCondiFieldList() {
        ArrayList<String> condiList = new ArrayList<String>();
        for (CustomCondition condition : this.modelCondiList) {
            if (!this.condiSet.contains(condition.getParamsCode())) continue;
            condiList.add(condition.getParamsCode().toUpperCase());
        }
        return condiList;
    }

    public boolean hasAnalyze(String key) {
        return this.condiSet.contains(key);
    }
}

