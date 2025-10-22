/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import java.util.ArrayList;
import java.util.List;

public class BatchReturnInfo {
    private int succnt = 0;
    private int errcnt = 0;
    private List<String> message = new ArrayList<String>();
    private int status;

    public int getSuccnt() {
        return this.succnt;
    }

    public void setSuccnt(int succnt) {
        this.succnt = succnt;
    }

    public void addSuccess() {
        ++this.succnt;
    }

    public int getErrcnt() {
        return this.errcnt;
    }

    public void setErrcnt(int errcnt) {
        this.errcnt = errcnt;
    }

    public void addError() {
        ++this.errcnt;
    }

    public List<String> getMessage() {
        return this.message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

