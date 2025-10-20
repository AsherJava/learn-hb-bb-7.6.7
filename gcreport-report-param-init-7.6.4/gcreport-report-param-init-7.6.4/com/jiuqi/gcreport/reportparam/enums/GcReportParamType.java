/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.reportparam.enums;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;

public enum GcReportParamType {
    GZW_BB("GZW_BB", "\u56fd\u8d44\u59d4\u62a5\u8868\u53c2\u6570", "\u56fd\u8d44\u59d4\u53c2\u6570"),
    GZW_CX("GZW_CX", "\u56fd\u8d44\u59d4\u67e5\u8be2\u5206\u6790", "\u51b3\u7b97\u835f"),
    CWYB_BB("CWYB_BB", "\u8d22\u52a1\u6708\u62a5\u62a5\u8868\u53c2\u6570", "\u8d22\u52a1\u6708\u62a5\u7ba1\u7406"),
    CWYB_HBTX("CWYB_HBTX", "\u8d22\u52a1\u6708\u62a5\u5408\u5e76\u4f53\u7cfb", "\u8d22\u52a1\u6708\u62a5\u7ba1\u7406");

    private String code;
    private String name;
    private String taskGroupName;

    private GcReportParamType(String code, String name, String taskGroupName) {
        this.code = code;
        this.name = name;
        this.taskGroupName = taskGroupName;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static GcReportParamType getByName(String name) {
        if (StringUtils.isEmpty((String)name)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u7c7b\u578b\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        for (GcReportParamType type : GcReportParamType.values()) {
            if (!type.getName().equals(name.trim())) continue;
            return type;
        }
        throw new BusinessRuntimeException("\u6839\u636e\u3010" + name + "\u3011\u672a\u627e\u5230\u5bf9\u5e94\u7684\u62a5\u8868\u53c2\u6570\u7c7b\u578b");
    }

    public String getTaskGroupName() {
        return this.taskGroupName;
    }
}

