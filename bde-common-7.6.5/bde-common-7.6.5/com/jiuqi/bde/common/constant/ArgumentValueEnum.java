/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum ArgumentValueEnum {
    RPUNITCODE("RPUNITCODE", "\u62a5\u8868\u7ec4\u7ec7\u673a\u6784"),
    UNITCODE("UNITCODE", "\u7ec4\u7ec7\u673a\u6784"),
    ASSISTCODE("ASSISTCODE", "\u7ec4\u7ec7\u673a\u6784\u6269\u5c55\u7ef4\u5ea6"),
    YEAR("YEAR", "\u5e74\u5ea6"),
    FULLPERIOD("FULLPERIOD", "\u671f\u95f4\uff08\u524d\u5bfc0\uff09"),
    PERIOD("PERIOD", "\u671f\u95f4"),
    YEARPERIOD("YEARPERIOD", "\u5e74\u5ea6\u671f\u95f4"),
    BOOKCODE("BOOKCODE", "\u8d26\u7c3f"),
    STARTDATE("STARTDATE", "\u5f00\u59cb\u65e5\u671f"),
    ENDDATE("ENDDATE", "\u7ed3\u675f\u65e5\u671f"),
    TASKID("TASKID", "\u4efb\u52a1ID"),
    INCLUDEUNCHARGED("INCLUDEUNCHARGED", "\u5305\u542b\u672a\u8bb0\u8d26"),
    INCLUDEADJUSTVCHR("INCLUDEADJUSTVCHR", "\u5305\u542b\u8c03\u6574\u51ed\u8bc1"),
    MD_GCADJTYPE("MD_GCADJTYPE", "\u5408\u5e76\u8c03\u6574\u7c7b\u578b"),
    MD_CURRENCY("MD_CURRENCY", "\u5e01\u522b"),
    MD_GCORGTYPE("MD_GCORGTYPE", "\u5408\u5e76\u5355\u4f4d\u7c7b\u578b"),
    PERIODSCHEME("PERIODSCHEME", "\u62a5\u8868\u671f\u95f4"),
    ADJUST_START_PERIOD("ADJUST_START_PERIOD", "\u8c03\u6574\u671f\u5f00\u59cb\u671f\u95f4"),
    ADJUST_END_PERIOD("ADJUST_END_PERIOD", "\u8c03\u6574\u671f\u7ed3\u675f\u671f\u95f4"),
    SELFANDCHILDUNIT("SELFANDCHILDUNIT", "\u672c\u5355\u4f4d\u53ca\u5168\u90e8\u4e0b\u7ea7\u5355\u4f4d");

    private String code;
    private String title;

    private ArgumentValueEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ArgumentValueEnum getArgumentValueEnumByCode(String code) {
        for (ArgumentValueEnum argumentValueEnum : ArgumentValueEnum.values()) {
            if (!argumentValueEnum.code.equals(code)) continue;
            return argumentValueEnum;
        }
        return null;
    }

    public static ArgumentValueEnum getArgumentValueEnumByTitle(String title) {
        for (ArgumentValueEnum argumentValueEnum : ArgumentValueEnum.values()) {
            if (!argumentValueEnum.title.equals(title)) continue;
            return argumentValueEnum;
        }
        return null;
    }
}

