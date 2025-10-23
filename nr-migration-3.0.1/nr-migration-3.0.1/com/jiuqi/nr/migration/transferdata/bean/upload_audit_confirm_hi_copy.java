/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

public enum upload_audit_confirm_hi_copy {
    act_upload("act_upload", "tsk_upload"),
    act_reject("act_reject", "tsk_audit"),
    act_confirm("act_confirm", "tsk_audit"),
    act_cancel_confirm("act_cancel_confirm", "tsk_audit_after_confirm"),
    act_retrieve("act_retrieve", "tsk_audit");

    public final String actionCode;
    public final String nodeCode;

    private upload_audit_confirm_hi_copy(String actionCode, String nodeCode) {
        this.nodeCode = nodeCode;
        this.actionCode = actionCode;
    }
}

