/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.dto;

public enum PreprocessStateEnum {
    ON(0, "\u6267\u884c\u4e2d"),
    SUCCESS(1, "\u6267\u884c\u6210\u529f"),
    FAILED(2, "\u6267\u884c\u5931\u8d25");

    private int state;
    private String stateInfo;

    private PreprocessStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return this.stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static PreprocessStateEnum statOf(int index) {
        for (PreprocessStateEnum state : PreprocessStateEnum.values()) {
            if (state.getState() != index) continue;
            return state;
        }
        return null;
    }
}

