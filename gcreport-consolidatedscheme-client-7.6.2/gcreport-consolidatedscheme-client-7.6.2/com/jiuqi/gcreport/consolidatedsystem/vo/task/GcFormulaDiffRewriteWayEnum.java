/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.task;

import com.jiuqi.common.base.BusinessRuntimeException;

public enum GcFormulaDiffRewriteWayEnum {
    BDE("bde", "BDE\u56de\u5199"),
    EFDC("efdc", "EFDC\u56de\u5199");

    private String code;
    private String title;

    private GcFormulaDiffRewriteWayEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static GcFormulaDiffRewriteWayEnum getByCode(String code) {
        for (GcFormulaDiffRewriteWayEnum gcDiffRewriteWayEnum : GcFormulaDiffRewriteWayEnum.values()) {
            if (!gcDiffRewriteWayEnum.getCode().equals(code)) continue;
            return gcDiffRewriteWayEnum;
        }
        throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5dee\u989d\u56de\u5199\u65b9\u5f0f\u3010" + code + "\u3011\u5bf9\u5e94\u7684\u679a\u4e3e");
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

