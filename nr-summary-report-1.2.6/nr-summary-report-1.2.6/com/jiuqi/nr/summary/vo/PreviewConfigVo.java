/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.model.PageInfo;
import java.util.HashMap;
import java.util.Map;

public class PreviewConfigVo {
    private String reportKey;
    private Map<String, String[]> conditions = new HashMap<String, String[]>();
    private Map<Integer, PageInfo> pageInfos = new HashMap<Integer, PageInfo>();

    public String getReportKey() {
        return this.reportKey;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    public Map<String, String[]> getConditions() {
        return this.conditions;
    }

    public void setConditions(Map<String, String[]> conditions) {
        this.conditions = conditions;
    }

    public Map<Integer, PageInfo> getPageInfos() {
        return this.pageInfos;
    }

    public void setPageInfos(Map<Integer, PageInfo> pageInfos) {
        this.pageInfos = pageInfos;
    }
}

