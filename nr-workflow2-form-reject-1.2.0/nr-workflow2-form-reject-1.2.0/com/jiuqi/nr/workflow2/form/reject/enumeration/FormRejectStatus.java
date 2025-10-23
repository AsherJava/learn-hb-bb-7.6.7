/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.form.reject.enumeration;

public enum FormRejectStatus {
    rejected("FORM_REJECTED", "\u5df2\u9000\u56de"),
    locked("FORM_LOCKED", "\u5df2\u9501\u5b9a");

    public final String value;
    public final String title;

    private FormRejectStatus(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public static FormRejectStatus fromValue(String value) {
        FormRejectStatus[] values;
        for (FormRejectStatus status : values = FormRejectStatus.values()) {
            if (!status.value.equals(value)) continue;
            return status;
        }
        return null;
    }
}

