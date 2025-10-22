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

public enum ActionEnum {
    CHECK("check", "\u5ba1\u6838", 1),
    ALL_CHECK("allcheck", "\u5168\u5ba1", 2),
    BATCH_CHECK("batchcheck", "\u6279\u91cf\u5ba1\u6838", 3);

    private final String name;
    private final String title;
    private final int code;

    private ActionEnum(String name, String title, int code) {
        this.name = name;
        this.title = title;
        this.code = code;
    }

    public static ActionEnum getByName(String name) {
        if (StringUtils.isEmpty((String)name)) {
            return null;
        }
        Optional<ActionEnum> any = Arrays.stream(ActionEnum.class.getEnumConstants()).filter(e -> e.getName().equals(name)).findAny();
        return any.orElse(null);
    }

    public static ActionEnum getByCode(int code) {
        Optional<ActionEnum> any = Arrays.stream(ActionEnum.class.getEnumConstants()).filter(e -> e.getCode() == code).findAny();
        return any.orElse(null);
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public int getCode() {
        return this.code;
    }
}

