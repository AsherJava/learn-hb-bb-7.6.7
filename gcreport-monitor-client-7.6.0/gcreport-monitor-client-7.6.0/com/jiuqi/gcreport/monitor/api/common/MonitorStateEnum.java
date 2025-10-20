/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.common;

public enum MonitorStateEnum {
    FINISH_IS(1, "\u5df2\u5b8c\u6210"),
    FINISH_NOT_IS(0, "\u672a\u5b8c\u6210"),
    INPUT_IS(1, "\u5df2\u5f55\u5165"),
    INPUT_NOT_IS(0, "\u672a\u5f55\u5165"),
    FILLIN_IS(1, "\u5df2\u586b\u6bd5"),
    FILLIN_NOT_IS(0, "\u672a\u586b\u6bd5"),
    FETCH_IS(1, "\u5df2\u63d0\u53d6"),
    FETCH_NOT_IS(0, "\u672a\u63d0\u53d6"),
    PASS_IS(1, "\u5df2\u901a\u8fc7"),
    PASS_NOT_IS(0, "\u672a\u901a\u8fc7"),
    ADJUST_IS(1, "\u5df2\u8c03\u6574"),
    ADJUST_NOT_IS(0, "\u672a\u8c03\u6574"),
    ADJUST_IS_CONFIRM_IS(1, "\u5df2\u8c03\u6574\u5df2\u786e\u8ba4"),
    ADJUST_NOT_IS_CONFIRM_IS(2, "\u672a\u8c03\u6574\u5df2\u786e\u8ba4"),
    ADJUST_IS_CONFIRM_NOT_IS(3, "\u5df2\u8c03\u6574\u672a\u786e\u8ba4"),
    ADJUST_NOT_IS_CONFIRM_NOT_IS(0, "\u672a\u8c03\u6574\u672a\u786e\u8ba4"),
    CONVERSION_IS(1, "\u5df2\u6298\u7b97"),
    CONVERSION_NOT_IS(0, "\u672a\u6298\u7b97"),
    AUDIT_IS(1, "\u5ba1\u6838\u901a\u8fc7"),
    AUDIT_NOT(2, "\u672a\u5ba1\u6838"),
    AUDIT_NOT_IS(0, "\u5ba1\u6838\u4e0d\u901a\u8fc7"),
    UPLOAD_IS(1, "\u5df2\u4e0a\u62a5"),
    UPLOAD_NOT(0, "\u672a\u4e0a\u62a5"),
    SUBMITTED_IS(1, "\u5df2\u9001\u5ba1"),
    SUBMITTED_NOT(0, "\u672a\u9001\u5ba1"),
    RETURNED_NOT_IS(2, "\u5df2\u9000\u5ba1"),
    REJECT_NOT_IS(3, "\u5df2\u9000\u56de"),
    PICK_IS(1, "\u5df2\u91c7\u96c6"),
    PICK_NOT_IS(0, "\u672a\u91c7\u96c6"),
    INITIAL_IS(1, "\u5df2\u521d\u59cb"),
    INITIAL_NOT_IS(0, "\u672a\u521d\u59cb"),
    CHECK_IS(1, "\u5df2\u5bf9\u4e0a"),
    CHECK_NOT_IS(0, "\u672a\u5bf9\u4e0a"),
    OFFSET_IS(1, "\u5df2\u62b5\u9500"),
    OFFSET_NOT_IS(0, "\u672a\u62b5\u9500"),
    CREATE_IS(1, "\u5df2\u751f\u6210"),
    CREATE_NOT_IS(0, "\u672a\u751f\u6210");

    private Integer code;
    private String name;

    private MonitorStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

