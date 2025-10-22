/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import java.util.Map;

public class InitLinkParam {
    private String period;
    private String taskKey;
    private String fieldCode;
    private Map<String, Object> linkParamMap;

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Map<String, Object> getLinkParamMap() {
        return this.linkParamMap;
    }

    public void setLinkParam(Map<String, Object> linkParamMap) {
        this.linkParamMap = linkParamMap;
    }
}

