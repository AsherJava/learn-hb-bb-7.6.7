/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.system.check2.zbScheme;

import com.jiuqi.np.common.exception.ErrorEnum;

class ZBErrorEnum
implements ErrorEnum {
    private final String message;

    public ZBErrorEnum(String message) {
        this.message = message;
    }

    public String getCode() {
        return "";
    }

    public String getMessage() {
        return this.message;
    }
}

