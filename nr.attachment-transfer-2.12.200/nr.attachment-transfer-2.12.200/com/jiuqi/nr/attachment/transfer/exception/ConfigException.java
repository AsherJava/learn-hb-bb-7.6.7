/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.attachment.transfer.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ConfigException implements ErrorEnum
{
    FAILED_SAVE("001", "\u66f4\u65b0\u914d\u7f6e\u4fe1\u606f\u5931\u8d25"),
    FAILED_UPDATE("002", "\u66f4\u65b0\u914d\u7f6e\u4fe1\u606f\u5931\u8d25");

    private String code;
    private String title;

    private ConfigException(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.title;
    }
}

