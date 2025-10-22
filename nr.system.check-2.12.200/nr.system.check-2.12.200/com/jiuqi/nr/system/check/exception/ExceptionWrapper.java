/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.system.check.exception;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;

public class ExceptionWrapper {
    private ExceptionWrapper() {
    }

    public static JQException wrapper(Exception e) {
        return ExceptionWrapper.wrapper("error", "\u9519\u8bef", e);
    }

    public static JQException wrapper(String code, String message, Exception e) {
        return new JQException(ExceptionWrapper.getErrorEnum(code, message), (Throwable)e);
    }

    private static ErrorEnum getErrorEnum(final String code, final String message) {
        return new ErrorEnum(){

            public String getCode() {
                return code;
            }

            public String getMessage() {
                return message;
            }
        };
    }
}

