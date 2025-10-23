/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import java.util.HashMap;

public enum DeployStatusEnum {
    NEVER_DEPLOY(-1, "\u672a\u53d1\u5e03"),
    SUCCESS(0, "\u53d1\u5e03\u6210\u529f"),
    DEPLOY(1, "\u53d1\u5e03\u4e2d"),
    FAIL(2, "\u53d1\u5e03\u5931\u8d25"),
    PARAM_LOCKING(3, "\u53c2\u6570\u9501\u5b9a"),
    WARNING(4, "\u53d1\u5e03\u8b66\u544a");

    private final int value;
    private final String title;
    private static final HashMap<Integer, DeployStatusEnum> MAP;

    private DeployStatusEnum(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DeployStatusEnum valueOf(int value) {
        if (!MAP.containsKey(value)) {
            return FAIL;
        }
        return MAP.get(value);
    }

    static {
        DeployStatusEnum[] values = DeployStatusEnum.values();
        MAP = new HashMap(values.length);
        for (DeployStatusEnum value : values) {
            MAP.put(value.value, value);
        }
    }
}

