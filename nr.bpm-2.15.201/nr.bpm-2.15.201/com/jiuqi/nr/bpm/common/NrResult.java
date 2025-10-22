/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import java.io.Serializable;

public class NrResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer status;
    private String msg;

    public static NrResult build(Integer status, String msg) {
        return new NrResult(status, msg);
    }

    public static NrResult ok() {
        return new NrResult();
    }

    public NrResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public NrResult() {
        this.status = 1;
        this.msg = "OK";
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

