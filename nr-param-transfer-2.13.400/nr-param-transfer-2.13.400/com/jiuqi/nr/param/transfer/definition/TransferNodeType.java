/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.param.transfer.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

public enum TransferNodeType {
    TASK_GROUP(1, "\u4efb\u52a1\u5206\u7ec4"),
    TASK(2, "\u4efb\u52a1"),
    FORM_SCHEME(4, "\u62a5\u8868\u65b9\u6848"),
    FORM_GROUP(8, "\u8868\u5355\u5206\u7ec4"),
    FORMULA_GROUP(16, "\u516c\u5f0f\u65b9\u6848\u5206\u7ec4"),
    PARENT_GROUP(32, "\u6253\u5370\u65b9\u6848\u5206\u7ec4"),
    FORMULA_SCHEME(64, "\u516c\u5f0f\u65b9\u6848"),
    PRINT_SCHEME(128, "\u6253\u5370\u65b9\u6848"),
    FORM(256, "\u8868\u5355"),
    FORMULA_FORM_GROUP(512, "\u516c\u5f0f\u65b9\u6848\u4e0b\u7684\u8868\u5355\u5206\u7ec4"),
    FORMULA_FORM(1024, "\u8868\u5355\u516c\u5f0f"),
    PRINT_TEMPLATE(2048, "\u6253\u5370\u6a21\u7248"),
    PRINT_SETTING(32768, "\u6253\u5370\u8bbe\u7f6e"),
    PRINT_COMTEM(65536, "\u6253\u5370\u6bcd\u7248"),
    MAPPING_GROUP(4096, "\u6620\u5c04\u65b9\u6848\u5206\u7ec4"),
    MAPPING_DEFINE(8192, "\u6620\u5c04\u65b9\u6848"),
    CUSTOM_DATA(16384, "\u81ea\u5b9a\u4e49\u6570\u636e");

    private final int value;
    private final String title;
    private static final HashMap<Integer, TransferNodeType> MAP;
    private static final HashMap<String, TransferNodeType> TITLE_MAP;

    private TransferNodeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static TransferNodeType valueOf(int value) {
        return MAP.get(value);
    }

    public static TransferNodeType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static TransferNodeType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            TransferNodeType nodeType = MAP.get(value);
            TransferNodeType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals((Object)nodeType) ? nodeType : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    static {
        TransferNodeType[] values = TransferNodeType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (TransferNodeType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

