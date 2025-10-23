/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.adapter.param.common;

import java.util.HashMap;

public enum NrdxParamNodeType {
    TASKGROUP("TASKGROUP", "\u4efb\u52a1\u5206\u7ec4"),
    TASK("TASK", "\u4efb\u52a1"),
    FORMSCHEME("FORMSCHEME", "\u62a5\u8868\u65b9\u6848"),
    FORMFOLDER("FORMGROUP", "\u8868\u5355\u76ee\u5f55"),
    FORMGROUP("FORMGROUP", "\u8868\u5355\u5206\u7ec4"),
    FORM("FORM", "\u8868\u5355"),
    FORMULAFOLDER("FORMULAFOLDER", "\u516c\u5f0f\u76ee\u5f55"),
    FORMULASCHEME("FORMULASCHEME", "\u516c\u5f0f\u65b9\u6848"),
    FORMULAFORMGROUP("FORMULAFORMGROUP", "\u516c\u5f0f\u65b9\u6848\u4e0b\u7684\u8868\u5355\u5206\u7ec4"),
    FORMULAFORM("FORMULAFORM", "\u8868\u5355\u516c\u5f0f"),
    PRINTFOLDER("PRINTFOLDER", "\u6253\u5370\u76ee\u5f55"),
    PRINTSCHEME("PRINTSCHEME", "\u6253\u5370\u65b9\u6848"),
    PRINTFORMGROUP("PRINTFORMGROUP", "\u6253\u5370\u65b9\u6848\u4e0b\u7684\u8868\u5355\u5206\u7ec4"),
    PRINTTEMPLATE("PRINTTEMPLATE", "\u6253\u5370\u6a21\u7248"),
    PRINTMOTHER_EMPLATE("PRINTMOTHERTEMPLATE", "\u6bcd\u7248\u8d44\u6e90"),
    MULTCHECKSCHEME("MULTCHECKSCHEME", "\u7efc\u5408\u5ba1\u6838\u65b9\u6848");

    private final String code;
    private final String title;
    private static final HashMap<String, NrdxParamNodeType> CODE_MAP;

    private NrdxParamNodeType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static NrdxParamNodeType codeOf(String code) {
        return CODE_MAP.get(code);
    }

    static {
        NrdxParamNodeType[] values = NrdxParamNodeType.values();
        CODE_MAP = new HashMap(values.length);
        for (NrdxParamNodeType value : values) {
            CODE_MAP.put(value.code, value);
        }
    }
}

