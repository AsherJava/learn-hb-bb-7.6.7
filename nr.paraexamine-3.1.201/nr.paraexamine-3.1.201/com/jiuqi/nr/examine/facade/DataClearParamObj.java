/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.facade;

import java.util.List;

public class DataClearParamObj {
    private String dataSchemeKey;
    public List<String> taskKey;

    public DataClearParamObj() {
    }

    public DataClearParamObj(String dataSchemeKey, List<String> taskKey) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public List<String> getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(List<String> taskKey) {
        this.taskKey = taskKey;
    }
}

