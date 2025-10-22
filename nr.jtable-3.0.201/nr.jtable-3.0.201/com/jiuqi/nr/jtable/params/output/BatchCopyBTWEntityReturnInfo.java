/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.CopyReturnInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatchCopyBTWEntityReturnInfo {
    private String sourceEntity;
    private Map<String, String> dimensionNum;
    private String copyForms;
    private String period;
    private List<CopyReturnInfo> copyMessage = new ArrayList<CopyReturnInfo>();
    private String targetEntity;
    private int status;

    public String getSourceEntity() {
        return this.sourceEntity;
    }

    public void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public Map<String, String> getDimensionNum() {
        return this.dimensionNum;
    }

    public void setDimensionNum(Map<String, String> dimensionNum) {
        this.dimensionNum = dimensionNum;
    }

    public String getCopyForms() {
        return this.copyForms;
    }

    public void setCopyForms(String copyForms) {
        this.copyForms = copyForms;
    }

    public List<CopyReturnInfo> getCopyMessage() {
        return this.copyMessage;
    }

    public void setCopyMessage(List<CopyReturnInfo> copyMessage) {
        this.copyMessage = copyMessage;
    }

    public String getTargetEntity() {
        return this.targetEntity;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}

