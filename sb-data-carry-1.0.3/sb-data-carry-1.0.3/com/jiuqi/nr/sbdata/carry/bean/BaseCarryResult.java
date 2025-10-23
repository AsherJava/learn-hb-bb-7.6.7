/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sbdata.carry.bean;

import com.jiuqi.nr.sbdata.carry.bean.DataFileCheckInfo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseCarryResult
implements Serializable {
    private DataFileCheckInfo dataFileCheckInfo;
    private boolean success = true;
    private Set<String> noAuthDw = new HashSet<String>();
    private List<Object> errorRowData;
    private String errorMessage;

    public DataFileCheckInfo getDataFileCheckInfo() {
        return this.dataFileCheckInfo;
    }

    public void setDataFileCheckInfo(DataFileCheckInfo dataFileCheckInfo) {
        this.dataFileCheckInfo = dataFileCheckInfo;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getNoAuthDw() {
        return this.noAuthDw;
    }

    public void setNoAuthDw(Set<String> noAuthDw) {
        this.noAuthDw = noAuthDw;
    }

    public List<Object> getErrorRowData() {
        return this.errorRowData;
    }

    public void setErrorRowData(List<Object> errorRowData) {
        this.errorRowData = errorRowData;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

