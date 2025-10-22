/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.i18n;

public enum I18NProcessBtn {
    I18N_START("start", "\u5f00\u59cb"),
    I18N_ACT_SUBMIT("act_submit", "\u9001\u5ba1"),
    I18N_ACT_RETURN("act_return", "\u9000\u5ba1"),
    I18N_ACT_UPLOAD("act_upload", "\u4e0a\u62a5"),
    I18N_ACT_REJECT("act_reject", "\u9000\u56de"),
    I18N_ACT_CONFIRM("act_confirm", "\u786e\u8ba4"),
    I18N_ACT_CANCEL_CONFIRM("act_cancel_confirm", "\u53d6\u6d88\u786e\u8ba4"),
    I18N_ACT_RETRIEVE("act_retrieve", "\u53d6\u56de");

    private String code;
    private String title;

    private I18NProcessBtn(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

