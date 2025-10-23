/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.np.common.exception.ErrorEnum;

public class ExceptionUtils {
    private static final ErrorEnum DEFAULT = new ErrorEnum(){

        public String getCode() {
            return "ERROR";
        }

        public String getMessage() {
            return "\u672a\u77e5\u9519\u8bef";
        }
    };

    public static synchronized ErrorEnum convert(final Exception e) {
        if (e == null) {
            return DEFAULT;
        }
        return new ErrorEnum(){

            public String getCode() {
                return "ERROR";
            }

            public String getMessage() {
                return e.getMessage();
            }
        };
    }

    public static synchronized ErrorEnum convert(final String message) {
        if (message == null) {
            return DEFAULT;
        }
        return new ErrorEnum(){

            public String getCode() {
                return "ERROR";
            }

            public String getMessage() {
                return message;
            }
        };
    }
}

