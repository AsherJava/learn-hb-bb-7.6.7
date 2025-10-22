/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.dto;

public enum UnitType {
    HBUNIT(0, "\u5408\u5e76\u5355\u4f4d"),
    DIFFUNIT(1, "\u5dee\u989d\u5355\u4f4d"),
    BASEUNIT(2, "\u672c\u90e8\u5355\u4f4d"),
    NONESTATE(3, "\u660e\u7ec6\u5355\u4f4d");

    private int state;
    private String stateInfo;

    private UnitType(int state, String stateInfo) {
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

    public static UnitType statOf(int index) {
        for (UnitType state : UnitType.values()) {
            if (state.getState() != index) continue;
            return state;
        }
        return null;
    }
}

