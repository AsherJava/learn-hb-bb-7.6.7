/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class SchemeExecuteResult1 {
    List<String> successList = new ArrayList<String>();
    Map<String, List<MCLabel>> failedMap = new HashMap<String, List<MCLabel>>();
    Map<String, List<String>> ignoreMap = new HashMap<String, List<String>>();

    public List<String> getSuccessList() {
        return this.successList;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    public void successListAdd(String org) {
        if (CollectionUtils.isEmpty(this.successList)) {
            this.successList = new ArrayList<String>();
        }
        if (!this.successList.contains(org)) {
            this.successList.add(org);
        }
    }

    public Map<String, List<MCLabel>> getFailedMap() {
        return this.failedMap;
    }

    public void setFailedMap(Map<String, List<MCLabel>> failedMap) {
        this.failedMap = failedMap;
    }

    public void failedMapAdd(String org, MCLabel label) {
        if (CollectionUtils.isEmpty(this.failedMap)) {
            this.failedMap = new HashMap<String, List<MCLabel>>();
        }
        if (!this.failedMap.containsKey(org)) {
            this.failedMap.put(org, new ArrayList());
        }
        this.failedMap.get(org).add(label);
    }

    public Map<String, List<String>> getIgnoreMap() {
        return this.ignoreMap;
    }

    public void setIgnoreMap(Map<String, List<String>> ignoreMap) {
        this.ignoreMap = ignoreMap;
    }

    public void ignoreMapAdd(String org, String item) {
        if (CollectionUtils.isEmpty(this.ignoreMap)) {
            this.ignoreMap = new HashMap<String, List<String>>();
        }
        if (!this.ignoreMap.containsKey(org)) {
            this.ignoreMap.put(org, new ArrayList());
        }
        this.ignoreMap.get(org).add(item);
    }
}

