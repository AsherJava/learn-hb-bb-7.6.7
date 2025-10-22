/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportparam.vo;

import java.util.List;

public class GcReportParamInitExecuteVO {
    private String sn;
    private List<String> reportParam;

    public GcReportParamInitExecuteVO() {
    }

    public GcReportParamInitExecuteVO(String sn, List<String> reportParam) {
        this.sn = sn;
        this.reportParam = reportParam;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<String> getReportParam() {
        return this.reportParam;
    }

    public void setReportParam(List<String> reportParam) {
        this.reportParam = reportParam;
    }
}

