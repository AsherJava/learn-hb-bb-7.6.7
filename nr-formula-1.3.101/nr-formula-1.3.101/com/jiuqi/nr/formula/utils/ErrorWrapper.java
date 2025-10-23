/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.formula.utils;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;

public class ErrorWrapper {
    private ErrorWrapper() {
    }

    public static JQException wrap(Exception e) {
        return ErrorWrapper.wrap("", e);
    }

    public static JQException wrap(final String code, final Exception e) {
        return new JQException(new ErrorEnum(){

            public String getCode() {
                return code;
            }

            public String getMessage() {
                return e.getMessage();
            }
        });
    }
}

