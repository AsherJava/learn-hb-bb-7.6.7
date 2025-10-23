/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotEmpty
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.summary.vo;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ExportConfig {
    private Map<String, String[]> conditions;
    @NotEmpty(message="\u5bfc\u51fa\u5931\u8d25\uff0c\u6c47\u603b\u8868\u53c2\u6570\u4e3a\u7a7a")
    private @NotEmpty(message="\u5bfc\u51fa\u5931\u8d25\uff0c\u6c47\u603b\u8868\u53c2\u6570\u4e3a\u7a7a") List<String> reportKeys;
    @NotNull(message="\u5bfc\u51fa\u5931\u8d25\uff0c\u6c47\u603b\u65b9\u6848\u53c2\u6570\u4e3a\u7a7a")
    private @NotNull(message="\u5bfc\u51fa\u5931\u8d25\uff0c\u6c47\u603b\u65b9\u6848\u53c2\u6570\u4e3a\u7a7a") String solutionKey;

    public Map<String, String[]> getConditions() {
        return this.conditions;
    }

    public void setConditions(Map<String, String[]> conditions) {
        this.conditions = conditions;
    }

    public List<String> getReportKeys() {
        return this.reportKeys;
    }

    public void setReportKeys(List<String> reportKeys) {
        this.reportKeys = reportKeys;
    }

    public String getSolutionKey() {
        return this.solutionKey;
    }

    public void setSolutionKey(String solutionKey) {
        this.solutionKey = solutionKey;
    }
}

