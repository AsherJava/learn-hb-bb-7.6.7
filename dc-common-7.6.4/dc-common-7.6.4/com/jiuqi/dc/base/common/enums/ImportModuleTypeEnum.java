/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.base.common.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum ImportModuleTypeEnum {
    SKIP_REPEAT("skipRepeat"),
    OVERWRITE_REPEAT("overwriteRepeat");

    private final String code;

    private ImportModuleTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static ImportModuleTypeEnum fromCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return SKIP_REPEAT;
        }
        for (ImportModuleTypeEnum importModuleTypeEnum : ImportModuleTypeEnum.values()) {
            if (!importModuleTypeEnum.code.equals(code)) continue;
            return importModuleTypeEnum;
        }
        return SKIP_REPEAT;
    }
}

