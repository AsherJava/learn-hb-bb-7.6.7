/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.bi.dataset.report.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public class ExportError
implements ErrorEnum {
    private String code = "export error";
    private String message;

    public ExportError(String message) {
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

