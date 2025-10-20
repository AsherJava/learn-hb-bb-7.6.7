/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.organization.impl.enums;

public enum GcAuthorityType {
    NONE("NONE"),
    ACCESS("READ"),
    MANAGE("EDIT"),
    WRITE("EDIT_FORM_DATA"),
    SUBMIT("SUBMIT_FORM_DATA"),
    REPORT("UPLOAD_FORM_DATA"),
    APPROVAL("AUDIT_FORM_DATA");

    private String type;

    private GcAuthorityType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static final GcAuthorityType find(String type) {
        return GcAuthorityType.valueOf(type);
    }
}

