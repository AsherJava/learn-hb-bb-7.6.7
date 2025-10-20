/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.bde.log.enums;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;

public enum FetchDimType implements IDimType
{
    BATCH("FETCH_BATCH", "\u6279\u91cf\u53d6\u6570"),
    UNITCODE("FETCH_UNITCODE", "\u53d6\u6570\u5355\u4f4d"),
    FORM("FETCH_FORM", "\u53d6\u6570\u62a5\u8868"),
    REGION("FETCH_FORM_REGION", "\u53d6\u6570\u533a\u57df"),
    COMPUTAT("FETCH_FORM_REGION_COMPUT", "\u53d6\u6570\u8ba1\u7b97\u6a21\u578b");

    private final String name;
    private final String title;

    private FetchDimType(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public static FetchDimType getEnumByName(String name) {
        if (StringUtils.isEmpty((String)name)) {
            return null;
        }
        for (FetchDimType dimType : FetchDimType.values()) {
            if (!dimType.getName().equals(name)) continue;
            return dimType;
        }
        return null;
    }
}

