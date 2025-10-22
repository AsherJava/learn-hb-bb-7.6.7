/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.upload.utils;

public enum DefaultButton {
    NOSONGSHEN("start_songshen", "", "ORIGINAL_SUBMIT", "\u672a\u9001\u5ba1", "\u672a\u9001\u5ba1"),
    NOSHANGBAO("start_shangbao", "", "ORIGINAL_UPLOAD", "\u672a\u4e0a\u62a5", "\u672a\u9001\u5ba1"),
    SONGSHEN("act_submit", "cus_submit", "SUBMITED", "\u9001\u5ba1", "\u5df2\u9001\u5ba1"),
    TUISHEN("act_return", "cus_return", "RETURNED", "\u9000\u5ba1", "\u5df2\u9000\u5ba1"),
    SHANGBAO("act_upload", "cus_upload", "UPLOADED", "\u4e0a\u62a5", "\u5df2\u4e0a\u62a5"),
    QUEREN("act_confirm", "cus_confirm", "CONFIRMED", "\u786e\u8ba4", "\u5df2\u786e\u8ba4"),
    TUIHUI("act_reject", "cus_reject", "REJECTED", "\u9000\u56de", "\u5df2\u9000\u56de"),
    QUHUI("act_retrieve", "", "", "\u53d6\u56de", "\u672a\u4e0a\u62a5"),
    CANCEL_QUEREN("act_cancel_confirm", "", "CANCELED", "\u53d6\u6d88\u786e\u8ba4", "\u5df2\u786e\u8ba4"),
    APPLY_REJECT("act_apply_return", "", "REJECTED", "\u7533\u8bf7\u9000\u56de", "");

    private final String actionCode;
    private final String cusActionCode;
    private final String stateCode;
    private final String name;
    private final String stateName;
    private static DefaultButton[] TYPES;

    private DefaultButton(String actionCode, String cusActionCode, String stateCode, String name, String stateName) {
        this.actionCode = actionCode;
        this.cusActionCode = cusActionCode;
        this.stateCode = stateCode;
        this.name = name;
        this.stateName = stateName;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public String getCusActionCode() {
        return this.cusActionCode;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public String getName() {
        return this.name;
    }

    public String getStateName() {
        return this.stateName;
    }

    public static DefaultButton fromType(int type) {
        return TYPES[type];
    }

    public static DefaultButton formByActionCode(String actionCode) {
        DefaultButton db = null;
        for (int x = 0; x < TYPES.length; ++x) {
            if (!DefaultButton.TYPES[x].actionCode.equals(actionCode) && !DefaultButton.TYPES[x].cusActionCode.equals(actionCode)) continue;
            db = TYPES[x];
        }
        return db;
    }

    public static DefaultButton formStateCode(String stateCode) {
        DefaultButton db = null;
        for (int x = 0; x < TYPES.length; ++x) {
            if (!DefaultButton.TYPES[x].stateCode.equals(stateCode)) continue;
            db = TYPES[x];
        }
        return db;
    }

    static {
        TYPES = new DefaultButton[]{NOSONGSHEN, NOSHANGBAO, SONGSHEN, TUISHEN, SHANGBAO, QUEREN, QUHUI, TUIHUI, CANCEL_QUEREN, APPLY_REJECT};
    }
}

