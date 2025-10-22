/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import java.util.List;

public class ParameterChangeVO {
    private String exchangeMode;
    private List<String> taskKeys;

    public String getExchangeMode() {
        return this.exchangeMode;
    }

    public void setExchangeMode(String exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public List<String> getTaskKeys() {
        return this.taskKeys;
    }

    public void setTaskKeys(List<String> taskKeys) {
        this.taskKeys = taskKeys;
    }
}

