/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.data.logic.api.param;

import com.jiuqi.bi.util.StringUtils;
import java.util.Arrays;
import java.util.Optional;

public enum CalState {
    NO_FML(0, "\u65e0\u8fd0\u7b97\u516c\u5f0f"),
    NO_PERMISSION(1, "\u65e0\u8fd0\u7b97\u6743\u9650"),
    ERROR(2, "\u8fd0\u7b97\u6267\u884c\u5f02\u5e38"),
    SUCCESS(3, "\u8fd0\u7b97\u6267\u884c\u6210\u529f"),
    CANCELED(4, "\u53d6\u6d88");

    private final int value;
    private final String title;

    private CalState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public static CalState getByTitle(String title) {
        if (StringUtils.isEmpty((String)title)) {
            return null;
        }
        Optional<CalState> any = Arrays.stream(CalState.class.getEnumConstants()).filter(e -> e.getTitle().equals(title)).findAny();
        return any.orElse(null);
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

