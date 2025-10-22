/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.bpm.setting.constant;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SettingError implements ErrorEnum
{
    S_ERROR("001", "\u65e0\u6570\u636e");

    String code;
    String message;

    private SettingError() {
    }

    private SettingError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

