/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process.consts;

public enum ProcessAction {
    UPLOAD("act_upload", "\u4e0a\u62a5"),
    SUBMIT("act_submit", "\u9001\u5ba1"),
    CONFIRM("act_confirm", "\u786e\u8ba4"),
    REJECT("act_reject", "\u9000\u56de"),
    RETURN("act_return", "\u9000\u5ba1"),
    CANCEL_CONFIRM("act_cancel_confirm", "\u53d6\u6d88\u786e\u8ba4"),
    CUS_UPLOAD("cus_upload", "\u4e0a\u62a5"),
    CUS_SUBMIT("cus_submit", "\u9001\u5ba1"),
    CUS_CONFIRM("cus_confirm", "\u786e\u8ba4"),
    CUS_REJECT("cus_reject", "\u9000\u56de"),
    CUS_RETURN("cus_return", "\u9000\u5ba1");

    private String action;
    private String name;

    private ProcessAction(String action, String name) {
        this.action = action;
        this.name = name;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

