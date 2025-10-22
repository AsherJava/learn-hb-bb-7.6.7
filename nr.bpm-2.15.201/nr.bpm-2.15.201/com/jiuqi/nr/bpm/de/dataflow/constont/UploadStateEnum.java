/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.constont;

public enum UploadStateEnum {
    ORIGINAL("ORIGINAL", "\u672a\u77e5"),
    ORIGINAL_UPLOAD("ORIGINAL_UPLOAD", "\u672a\u4e0a\u62a5"),
    ORIGINAL_SUBMIT("ORIGINAL_SUBMIT", "\u672a\u9001\u5ba1"),
    SUBMITED("SUBMITED", "\u5df2\u9001\u5ba1"),
    RETURNED("RETURNED", "\u5df2\u9000\u5ba1"),
    UPLOADED("UPLOADED", " \u5df2\u4e0a\u62a5"),
    CONFIRMED("CONFIRMED", "\u5df2\u786e\u8ba4"),
    REJECTED("REJECTED", "\u5df2\u9000\u56de"),
    DELAY("DELAY", "\u5ef6\u8fdf\u4e0a\u62a5"),
    ACTION_START("start", "\u5f00\u59cb"),
    ACTION_UPLOAD("act_upload", "\u4e0a\u62a5"),
    ACTION_REJECT("act_reject", "\u9000\u56de"),
    ACTION_SUBMITCHECK("act_submit", "\u9001\u5ba1"),
    ACTION_RETURNCHECK("act_return", "\u9000\u5ba1"),
    ACTION_CONFIRM("act_confirm", "\u786e\u8ba4"),
    BATCH_ACTION_UPLOAD("batch_act_upload", "\u6279\u91cf\u4e0a\u62a5"),
    BATCH_ACTION_REJECT("batch_act_reject", "\u6279\u91cf\u9000\u56de"),
    BATCH_ACTION_CONFIRM("batch_act_confirm", "\u6279\u91cf\u786e\u8ba4"),
    BATCH_ACTION_SUBMITCHECK("batch_act_submit", "\u6279\u91cf\u9001\u5ba1"),
    BATCH_ACTION_RETURNCHECK("batch_act_return", "\u6279\u91cf\u9000\u5ba1"),
    CUSTOM_UOLOAD("cus_upload", "\u4e0a\u62a5"),
    CUSTOM_REJECT("cus_reject", "\u9000\u56de"),
    CUSTOM_CONFIRM("cus_confirm", "\u786e\u8ba4"),
    CUSTOM_SUBMIT("cus_submit", "\u9001\u5ba1"),
    CUSTOM_RETURN("cus_return", "\u9000\u5ba1");

    private static UploadStateEnum[] UPLOAD;
    private static UploadStateEnum[] SUBMIT;
    private static UploadStateEnum[] CONFIRM;
    private static UploadStateEnum[] RETURN;
    private static UploadStateEnum[] REJECT;
    private String code;
    private String name;

    private UploadStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        for (UploadStateEnum c : UploadStateEnum.values()) {
            if (c.getCode() != code) continue;
            return c.name;
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static {
        UPLOAD = new UploadStateEnum[]{ACTION_UPLOAD, BATCH_ACTION_UPLOAD, CUSTOM_UOLOAD};
        SUBMIT = new UploadStateEnum[]{ACTION_SUBMITCHECK, BATCH_ACTION_SUBMITCHECK, CUSTOM_SUBMIT};
        CONFIRM = new UploadStateEnum[]{ACTION_CONFIRM, BATCH_ACTION_CONFIRM, CUSTOM_CONFIRM};
        RETURN = new UploadStateEnum[]{ACTION_RETURNCHECK, BATCH_ACTION_RETURNCHECK, CUSTOM_RETURN};
        REJECT = new UploadStateEnum[]{ACTION_REJECT, BATCH_ACTION_REJECT, CUSTOM_REJECT};
    }
}

