/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO;

public class FunctionEditorVO {
    private String code;
    private String detail;
    private String userId;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return "FunctionEditorVO{code='" + this.code + '\'' + ", detail='" + this.detail + '\'' + ", userId=" + this.userId + '}';
    }
}

