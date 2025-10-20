/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.domain.metaupdate;

import java.util.List;
import java.util.Map;

public class MetaDataUpdateDTO {
    private String info;
    private List<String> successData;
    private List<Map<String, String>> failedData;

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getSuccessData() {
        return this.successData;
    }

    public void setSuccessData(List<String> successData) {
        this.successData = successData;
    }

    public List<Map<String, String>> getFailedData() {
        return this.failedData;
    }

    public void setFailedData(List<Map<String, String>> failedData) {
        this.failedData = failedData;
    }
}

