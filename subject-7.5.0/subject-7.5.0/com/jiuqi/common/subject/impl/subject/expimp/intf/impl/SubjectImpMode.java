/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.common.subject.impl.subject.expimp.intf.impl;

import com.jiuqi.common.base.util.StringUtils;

public enum SubjectImpMode {
    SKIP_REPEAT("skipRepeat"),
    OVERWRITE_REPEAT("overwriteRepeat");

    private final String code;

    private SubjectImpMode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static SubjectImpMode fromCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return SKIP_REPEAT;
        }
        for (SubjectImpMode as : SubjectImpMode.values()) {
            if (!as.code.equals(code)) continue;
            return as;
        }
        return SKIP_REPEAT;
    }
}

