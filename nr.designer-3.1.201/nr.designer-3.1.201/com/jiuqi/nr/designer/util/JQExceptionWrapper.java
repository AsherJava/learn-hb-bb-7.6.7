/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;

public class JQExceptionWrapper {
    public static JQException wrapper(String message) {
        return JQExceptionWrapper.wrapper(message, "");
    }

    public static JQException wrapper(Exception e) {
        return JQExceptionWrapper.wrapper(e, "");
    }

    public static JQException wrapper(Exception e, String code) {
        return JQExceptionWrapper.wrapper(e.getMessage(), code);
    }

    public static JQException wrapper(final String message, final String code) {
        return new JQException(new ErrorEnum(){

            public String getCode() {
                return code;
            }

            public String getMessage() {
                return message;
            }
        });
    }
}

