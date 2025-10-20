/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.domain;

import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import java.util.List;

public class PluginCheckResultVO {
    private String pluginName;
    private List<PluginCheckResultDTO> checkResults;

    public String getPluginName() {
        return this.pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public List<PluginCheckResultDTO> getCheckResults() {
        return this.checkResults;
    }

    public void setCheckResults(List<PluginCheckResultDTO> checkResults) {
        this.checkResults = checkResults;
    }
}

