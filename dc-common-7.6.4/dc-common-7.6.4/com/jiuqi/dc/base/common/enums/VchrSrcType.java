/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum VchrSrcType {
    NORMAL("NORMAL"),
    DEFERRAL("DEFERRAL"),
    EXCHANGE("EXCHANGE"),
    TRANSFER("TRANSFER"),
    COLLABORATE("COLLABORATE"),
    MECHANISM("MECHANISM"),
    EXTERNAL("EXTERNAL"),
    ADJUST_CURRENTYEAR("ADJUST_CURRENTYEAR"),
    ADJUST_NEXTYEAR("ADJUST_NEXTYEAR");

    private final String value;

    private VchrSrcType(String value) {
        this.value = value;
    }

    public String toValue() {
        return this.value;
    }

    public static VchrSrcType fromValue(String value) {
        for (VchrSrcType vst : VchrSrcType.values()) {
            if (!vst.value.equals(value)) continue;
            return vst;
        }
        return null;
    }

    public boolean isMechanism() {
        return this == EXCHANGE || this == TRANSFER || this == MECHANISM || this == DEFERRAL;
    }
}

