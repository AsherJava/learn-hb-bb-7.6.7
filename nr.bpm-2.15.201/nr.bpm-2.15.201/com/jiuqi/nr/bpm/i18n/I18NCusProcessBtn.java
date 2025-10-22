/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.i18n;

public enum I18NCusProcessBtn {
    I18N_CUSTOM_UOLOAD("cus_upload", "\u4e0a\u62a5"),
    I18N_CUSTOM_REJECT("cus_reject", "\u9000\u56de"),
    I18N_CUSTOM_CONFIRM("cus_confirm", "\u786e\u8ba4"),
    I18N_CUSTOM_SUBMIT("cus_submit", "\u9001\u5ba1"),
    I18N_CUSTOM_RETURN("cus_return", "\u9000\u5ba1");

    private String code;
    private String title;

    private I18NCusProcessBtn(String code, String title) {
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

