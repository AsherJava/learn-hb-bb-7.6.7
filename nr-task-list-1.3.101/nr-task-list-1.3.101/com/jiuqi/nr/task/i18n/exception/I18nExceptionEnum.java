/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.i18n.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum I18nExceptionEnum implements ErrorEnum
{
    I18N_QUERY_EXCEPTION("I18N_001", "\u67e5\u8be2\u591a\u8bed\u8a00\u914d\u7f6e\u51fa\u9519"),
    I18N_SAVE_EXCEPTION("I18N_002", "\u4fdd\u5b58\u591a\u8bed\u8a00\u914d\u7f6e\u51fa\u9519"),
    I18N_IMPORT_EXCEPTION("I18N_003", "\u5bfc\u5165\u591a\u8bed\u8a00\u914d\u7f6e\u51fa\u9519"),
    I18N_EXPORT_EXCEPTION("I18N_004", "\u5bfc\u51fa\u591a\u8bed\u8a00\u914d\u7f6e\u51fa\u9519"),
    I18N_DEPLOY_EXCEPTION("I18N_005", "\u53d1\u5e03\u591a\u8bed\u8a00\u914d\u7f6e\u51fa\u9519");

    private String code;
    private String message;

    public String getCode() {
        return null;
    }

    public String getMessage() {
        return null;
    }

    private I18nExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

