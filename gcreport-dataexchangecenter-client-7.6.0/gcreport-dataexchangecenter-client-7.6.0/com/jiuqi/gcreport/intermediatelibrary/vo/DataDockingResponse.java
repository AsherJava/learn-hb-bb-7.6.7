/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import java.util.List;

public class DataDockingResponse {
    private String sn;
    private String requestTime;
    private List<String> messages;

    public DataDockingResponse() {
    }

    public DataDockingResponse(String sn, String requestTime, List<String> messages) {
        this.sn = sn;
        this.requestTime = requestTime;
        this.messages = messages;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}

