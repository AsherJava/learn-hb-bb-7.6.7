/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.inputdata.query.constant;

import com.jiuqi.common.base.util.StringUtils;

public enum InputDataQueryFilterType {
    ALL("ALL", "\u6240\u6709\u5408\u5e76\u5c42\u7ea7"),
    CURRENT("CURRENT", "\u5f53\u524d\u5408\u5e76\u5c42\u7ea7"),
    PARENTUNION("PARENTUNION", "\u4e0a\u7ea7\u5408\u5e76\u5c42\u7ea7"),
    CHILDREN("CHILDREN", "\u4e0b\u7ea7\u5408\u5e76\u5c42\u7ea7");

    private String code;
    private String title;

    private InputDataQueryFilterType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static InputDataQueryFilterType findTabsType(String type) {
        if (StringUtils.isEmpty((String)type)) {
            return null;
        }
        for (InputDataQueryFilterType filterType : InputDataQueryFilterType.values()) {
            if (filterType.name().equalsIgnoreCase(type)) {
                return filterType;
            }
            if (type.equals(String.valueOf(filterType.getCode()))) {
                return filterType;
            }
            if (!type.equals(filterType.getTitle())) continue;
            return filterType;
        }
        return null;
    }
}

