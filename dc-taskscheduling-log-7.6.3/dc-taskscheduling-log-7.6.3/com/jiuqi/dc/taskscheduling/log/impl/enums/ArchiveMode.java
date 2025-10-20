/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.log.impl.enums;

import java.util.Objects;
import java.util.stream.Stream;

public enum ArchiveMode {
    ARCHIVE_CLEAR("ARCHIVECLEAR", "\u5f52\u6863\u5e76\u6e05\u9664"),
    ONLY_CLEAR("ONLYCLEAR", "\u4ec5\u6e05\u9664");

    private String code;
    private String name;

    private ArchiveMode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static ArchiveMode fromCode(String code) {
        return Stream.of(ArchiveMode.values()).filter(e -> Objects.equals(e.getCode(), code)).findFirst().orElse(null);
    }
}

