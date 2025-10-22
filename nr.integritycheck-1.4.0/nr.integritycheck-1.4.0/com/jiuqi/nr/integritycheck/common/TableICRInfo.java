/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.integritycheck.common.ErrorDesInfo;
import java.io.Serializable;
import java.util.Map;

public class TableICRInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String batchId;
    private String recId;
    private Map<String, String> dimNameValueMap;
    private int bblx;
    private String formKey;
    private String result;
    private ErrorDesInfo errorDesInfo;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getRecId() {
        return this.recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public Map<String, String> getDimNameValueMap() {
        return this.dimNameValueMap;
    }

    public void setDimNameValueMap(Map<String, String> dimNameValueMap) {
        this.dimNameValueMap = dimNameValueMap;
    }

    public int getBblx() {
        return this.bblx;
    }

    public void setBblx(int bblx) {
        this.bblx = bblx;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ErrorDesInfo getErrorDesInfo() {
        return this.errorDesInfo;
    }

    public void setErrorDesInfo(ErrorDesInfo errorDesInfo) {
        this.errorDesInfo = errorDesInfo;
    }
}

