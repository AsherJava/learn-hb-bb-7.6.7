/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.web.bean;

import java.util.HashMap;

public enum ExamineNodeType {
    REFUSE(101, "\u5783\u573e\u53c2\u6570"),
    QUOTE(102, "\u9519\u8bef\u5f15\u7528"),
    ERROR(103, "\u9519\u8bef\u53c2\u6570"),
    TASK(201, "\u4efb\u52a1"),
    FORMSCHEME(202, "\u62a5\u8868\u65b9\u6848"),
    FORMULASCHEME(203, "\u516c\u5f0f\u65b9\u6848"),
    PRINTSCHEME(204, "\u6253\u5370\u65b9\u6848"),
    FORMULA(205, "\u516c\u5f0f"),
    FORMGROUP(206, "\u62a5\u8868\u5206\u7ec4"),
    FORM(207, "\u62a5\u8868"),
    REGION(208, "\u533a\u57df"),
    LINK(209, "\u94fe\u63a5"),
    UNRESTORE(301, "\u672a\u4fee\u590d"),
    SUCCESS(302, "\u4fee\u590d\u6210\u529f"),
    MARKSUCCESS(303, "\u6807\u8bb0\u4fee\u590d"),
    IGNORE(304, "\u5ffd\u7565"),
    FAILED(305, "\u5931\u8d25");

    private int code;
    private String msg;
    private static HashMap<Integer, ExamineNodeType> mappings;

    private ExamineNodeType(int code, String msg) {
        this.code = code;
        this.msg = msg;
        ExamineNodeType.getMappings().put(code, this);
    }

    private static synchronized HashMap<Integer, ExamineNodeType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    public int getValue() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static ExamineNodeType forValue(int value) {
        return ExamineNodeType.getMappings().get(value);
    }
}

