/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.system.check.common;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;

public class Util {
    public static JQException getError(final String code, final Exception e) {
        return new JQException(new ErrorEnum(){

            public String getCode() {
                return code;
            }

            public String getMessage() {
                return e.getMessage();
            }
        });
    }

    public static JQException getError(final String code, final String message) {
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

