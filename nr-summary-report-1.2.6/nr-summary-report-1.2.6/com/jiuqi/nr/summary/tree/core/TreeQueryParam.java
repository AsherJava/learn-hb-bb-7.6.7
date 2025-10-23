/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.tree.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeQueryParam {
    private String nodeKey;
    private String parentCode;
    private String rowFilter;
    private String period;
    private List<String> nodeKeyDatas;
    private Map<String, Object> customParams;

    public String getNodeKey() {
        return this.nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getRowFilter() {
        return this.rowFilter;
    }

    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getNodeKeyDatas() {
        return this.nodeKeyDatas;
    }

    public void setNodeKeyDatas(List<String> nodeKeyDatas) {
        this.nodeKeyDatas = nodeKeyDatas;
    }

    public Map<String, Object> getCustomParams() {
        return this.customParams;
    }

    public void setCustomParams(Map<String, Object> customParams) {
        this.customParams = customParams;
    }

    public Object getCustomValue(String key) {
        return this.customParams.get(key);
    }

    public void putCustomParam(String key, Object value) {
        if (this.customParams == null) {
            this.customParams = new HashMap<String, Object>();
        }
        this.customParams.put(key, value);
    }
}

