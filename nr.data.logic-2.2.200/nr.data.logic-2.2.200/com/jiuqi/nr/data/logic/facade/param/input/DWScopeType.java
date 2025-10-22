/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.bi.util.StringUtils;
import java.util.Arrays;
import java.util.Optional;

public enum DWScopeType {
    NULL("null", "\u9ed8\u8ba4(\u672c\u7ea7\u8282\u70b9)"),
    SELF("self", "\u672c\u7ea7\u8282\u70b9"),
    CHILDREN("children", "\u76f4\u8f96\u4e0b\u7ea7"),
    ALL_CHILDREN("allChildren", "\u6240\u6709\u4e0b\u7ea7");

    private final String key;
    private final String value;

    public static DWScopeType getByKey(String key) {
        if (StringUtils.isEmpty((String)key)) {
            return NULL;
        }
        Optional<DWScopeType> any = Arrays.stream(DWScopeType.class.getEnumConstants()).filter(e -> e.getKey().equals(key)).findAny();
        return any.orElse(NULL);
    }

    private DWScopeType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}

