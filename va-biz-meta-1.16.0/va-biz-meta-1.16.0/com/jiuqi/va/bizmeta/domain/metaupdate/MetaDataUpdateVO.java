/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.bizmeta.domain.metaupdate;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MetaDataUpdateVO {
    private Boolean flag;
    private String message;
    private List<String> successData;
    private List<Map<String, String>> failedData;

    public Boolean getFlag() {
        return this.flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
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

