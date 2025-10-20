/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

import java.util.List;

public class QueryConfigureImportVO {
    private List<ImportResult> successInfo;
    private List<ImportResult> failureInfo;

    public List<ImportResult> getSuccessInfo() {
        return this.successInfo;
    }

    public void setSuccessInfo(List<ImportResult> successInfo) {
        this.successInfo = successInfo;
    }

    public List<ImportResult> getFailureInfo() {
        return this.failureInfo;
    }

    public void setFailureInfo(List<ImportResult> failureInfo) {
        this.failureInfo = failureInfo;
    }

    public String toString() {
        return "QueryConfigureImportVO{successInfo=" + this.successInfo + ", failureInfo=" + this.failureInfo + '}';
    }

    public static class ImportResult {
        private String msg;

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String toString() {
            return "ImportResult{msg='" + this.msg + '\'' + '}';
        }
    }
}

