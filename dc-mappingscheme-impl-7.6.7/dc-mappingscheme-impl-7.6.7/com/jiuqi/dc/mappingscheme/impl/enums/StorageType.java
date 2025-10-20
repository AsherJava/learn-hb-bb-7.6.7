/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StorageType {
    ID("ID", "ID"),
    CODE("CODE", "CODE");

    private final String code;
    private final String name;

    private StorageType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static List<String> getStorageTypeList() {
        return Arrays.stream(StorageType.values()).map(StorageType::getCode).collect(Collectors.toList());
    }
}

