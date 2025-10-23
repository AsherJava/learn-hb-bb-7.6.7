/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.print.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PrintSchemeMoveType {
    MOVE_UP("up", "\u4e0a\u79fb"),
    MOVE_DOWN("down", "\u4e0b\u79fb");

    private String code;
    private String title;
    private static final Map<String, PrintSchemeMoveType> VALUE_MAP;

    private PrintSchemeMoveType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static PrintSchemeMoveType parseType(String code) {
        return VALUE_MAP.get(code);
    }

    static {
        VALUE_MAP = Arrays.stream(PrintSchemeMoveType.values()).collect(Collectors.toMap(t -> t.getCode(), t -> t));
    }
}

