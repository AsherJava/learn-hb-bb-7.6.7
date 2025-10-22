/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.np.definition.impl.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum DefinitionError implements ErrorEnum
{
    DEFINITION_IMPL_000("000", "\u8be5\u65b9\u6cd5\u65e0\u5b9e\u73b0\uff01");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private DefinitionError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

