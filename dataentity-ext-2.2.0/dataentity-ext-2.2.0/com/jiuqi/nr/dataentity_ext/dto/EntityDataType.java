/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentity_ext.dto;

import java.util.Arrays;
import java.util.Optional;

public enum EntityDataType {
    EXIST(1, "\u5f53\u524d\u7cfb\u7edf\u4e2d\u5df2\u5b58\u5728"),
    NOT_EXIST(2, "\u5f53\u524d\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728"),
    EXIST_DISABLE(3, "\u5f53\u524d\u7cfb\u7edf\u4e2d\u5b58\u5728\u4f46\u9700\u8981\u7981\u7528");

    private final int code;
    private final String desc;

    private EntityDataType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static EntityDataType getByCode(int code) {
        Optional<EntityDataType> any = Arrays.stream(EntityDataType.class.getEnumConstants()).filter(e -> e.getCode() == code).findAny();
        if (any.isPresent()) {
            return any.get();
        }
        throw new IllegalArgumentException("code=" + code + " is not supported");
    }
}

