/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.dto;

public enum ReportPickEnum {
    BYONEKEY(1, "\u4e00\u952e\u5408\u5e76\u81ea\u52a8\u63d0\u53d6"),
    BYHAND(0, "\u624b\u5de5\u63d0\u53d6");

    private int state;
    private String stateInfo;

    private ReportPickEnum(int state, String stateInfo) {
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

    public static ReportPickEnum statOf(int index) {
        for (ReportPickEnum state : ReportPickEnum.values()) {
            if (state.getState() != index) continue;
            return state;
        }
        return null;
    }
}

