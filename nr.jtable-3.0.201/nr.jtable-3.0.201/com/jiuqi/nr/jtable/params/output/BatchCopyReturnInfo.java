/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.CopyReturnInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatchCopyReturnInfo {
    private String sourcePeriod;
    private Map<String, String> dimensionNum;
    private String copyForms;
    private List<CopyReturnInfo> copyMessage = new ArrayList<CopyReturnInfo>();
    private String toPeriod;
    private String sourcePeriodTitle;
    private String toPeriodTitle;
    private int status;

    public String getSourcePeriodTitle() {
        return this.sourcePeriodTitle;
    }

    public void setSourcePeriodTitle(String sourcePeriodTitle) {
        this.sourcePeriodTitle = sourcePeriodTitle;
    }

    public String getToPeriodTitle() {
        return this.toPeriodTitle;
    }

    public void setToPeriodTitle(String toPeriodTitle) {
        this.toPeriodTitle = toPeriodTitle;
    }

    public String getSourcePeriod() {
        return this.sourcePeriod;
    }

    public void setSourcePeriod(String sourcePeriod) {
        this.sourcePeriod = sourcePeriod;
    }

    public String getCopyForms() {
        return this.copyForms;
    }

    public void setCopyForms(String copyForms) {
        this.copyForms = copyForms;
    }

    public Map<String, String> getDimensionNum() {
        return this.dimensionNum;
    }

    public void setDimensionNum(Map<String, String> dimensionNum) {
        this.dimensionNum = dimensionNum;
    }

    public List<CopyReturnInfo> getCopyMessage() {
        return this.copyMessage;
    }

    public void setCopyMessage(List<CopyReturnInfo> copyMessage) {
        this.copyMessage = copyMessage;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

