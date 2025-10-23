/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

public enum upload_audit_confirm_copy {
    start("start", "tsk_upload"),
    act_upload("act_upload", "tsk_audit"),
    act_reject("act_reject", "tsk_upload"),
    act_confirm("act_confirm", "tsk_audit_after_confirm"),
    act_cancel_confirm("act_cancel_confirm", "tsk_audit");

    public final String actionCode;
    public final String nodeCode;

    private upload_audit_confirm_copy(String actionCode, String nodeCode) {
        this.nodeCode = nodeCode;
        this.actionCode = actionCode;
    }
}

