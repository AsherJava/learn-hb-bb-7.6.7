/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.common;

import java.util.HashMap;
import java.util.Map;

public enum VersionStatus {
    UNPUBLISHED(0, "\u672a\u53d1\u5e03"),
    PUBLISHED(1, "\u53d1\u5e03"),
    CANCELED(2, "\u64a4\u9500");

    private final int value;
    private final String title;
    private static final Map<Integer, VersionStatus> mappings;

    private VersionStatus(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static VersionStatus forValue(int value) {
        return mappings.get(value);
    }

    static {
        mappings = new HashMap<Integer, VersionStatus>();
        for (VersionStatus status : VersionStatus.values()) {
            mappings.put(status.getValue(), status);
        }
    }
}

