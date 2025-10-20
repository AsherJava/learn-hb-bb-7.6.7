/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain;

import java.util.List;
import java.util.Map;

public class BillSublistImportVO {
    private int successData;
    private int errorData;
    private List<Map<String, Object>> tableData;
    private List<Map<String, Object>> appendData;
    private String exception;

    public int getSuccessData() {
        return this.successData;
    }

    public void setSuccessData(int successData) {
        this.successData = successData;
    }

    public int getErrorData() {
        return this.errorData;
    }

    public void setErrorData(int errorData) {
        this.errorData = errorData;
    }

    public List<Map<String, Object>> getTableData() {
        return this.tableData;
    }

    public void setTableData(List<Map<String, Object>> tableData) {
        this.tableData = tableData;
    }

    public void setAppendData(List<Map<String, Object>> appendData) {
        this.appendData = appendData;
    }

    public List<Map<String, Object>> getAppendData() {
        return this.appendData;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return this.exception;
    }
}

