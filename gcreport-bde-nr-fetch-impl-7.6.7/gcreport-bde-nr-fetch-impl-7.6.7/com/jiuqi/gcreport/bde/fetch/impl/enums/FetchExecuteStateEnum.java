/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.enums;

public enum FetchExecuteStateEnum {
    EXECUTE(0, "\u6b63\u5728\u6267\u884c\u4e2d"),
    FAILED(1, "\u5931\u8d25"),
    SUCCESS(2, "\u6210\u529f");

    private final Integer stateNum;
    private final String stateStr;

    private FetchExecuteStateEnum(Integer stateNum, String stateStr) {
        this.stateNum = stateNum;
        this.stateStr = stateStr;
    }

    public Integer getStateNum() {
        return this.stateNum;
    }

    public String getStateStr() {
        return this.stateStr;
    }
}

