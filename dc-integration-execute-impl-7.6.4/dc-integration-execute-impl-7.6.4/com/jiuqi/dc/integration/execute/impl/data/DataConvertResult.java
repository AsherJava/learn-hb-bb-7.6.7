/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.data;

public class DataConvertResult {
    private String log;
    private String param;

    public DataConvertResult() {
    }

    public DataConvertResult(String log, String param) {
        this.log = log;
        this.param = param;
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}

