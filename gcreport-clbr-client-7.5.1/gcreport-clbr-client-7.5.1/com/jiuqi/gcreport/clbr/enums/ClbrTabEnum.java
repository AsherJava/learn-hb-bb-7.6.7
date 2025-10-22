/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.enums;

public enum ClbrTabEnum {
    PROCESS_CONFIRM("process-confirm", "\u534f\u540c\u5904\u7406\u4e2d\u5fc3-\u53cc\u65b9\u5df2\u786e\u8ba4"),
    PROCESS_INITIATOR_NOT_CONFIRM("process-initiator-notconfirm", "\u534f\u540c\u5904\u7406\u4e2d\u5fc3-\u672c\u65b9\u5f85\u786e\u8ba4"),
    PROCESS_RECEIVER_NOT_CONFIRM("process-receiver-notconfirm", "\u534f\u540c\u5904\u7406\u4e2d\u5fc3-\u5bf9\u65b9\u5f85\u786e\u8ba4"),
    PROCESS_NOT_CONFIRM("process-notconfirm", "\u534f\u540c\u5904\u7406\u4e2d\u5fc3-\u672a\u786e\u8ba4"),
    GENERATE_CONFIRM("generate-confirm", "\u534f\u540c\u751f\u5355-\u5df2\u786e\u8ba4"),
    GENERATE_NOT_CONFIRM("generate-notconfirm", "\u534f\u540c\u751f\u5355-\u672a\u786e\u8ba4"),
    DATAQUERY_TOTAL("dataquery-total", "\u6570\u636e\u67e5\u8be2-\u603b\u6570"),
    DATAQUERY_PART_CONFIRM("dataquery-partconfirm", "\u6570\u636e\u67e5\u8be2-\u90e8\u5206\u786e\u8ba4"),
    DATAQUERY_CONFIRM("dataquery-confirm", "\u6570\u636e\u67e5\u8be2-\u5df2\u786e\u8ba4"),
    DATAQUERY_NOT_CONFIRM("dataquery-notconfirm", "\u6570\u636e\u67e5\u8be2-\u672a\u786e\u8ba4"),
    DATAQUERY_REJECT("dataquery-reject", "\u6570\u636e\u67e5\u8be2-\u5df2\u9a73\u56de"),
    ARBITRATION("arbitration", "\u534f\u540c\u4ef2\u88c1");

    private String code;
    private String title;

    private ClbrTabEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ClbrTabEnum getEnumByCode(String code) {
        for (ClbrTabEnum operateEnum : ClbrTabEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

