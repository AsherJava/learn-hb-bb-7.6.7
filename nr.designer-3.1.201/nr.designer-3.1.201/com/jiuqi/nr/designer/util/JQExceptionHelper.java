/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.xlib.utils.ExceptionUtils
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.xlib.utils.ExceptionUtils;

public class JQExceptionHelper {
    public static String printStackTraceToString(ErrorEnum errorEnum, Throwable t) {
        StringBuffer sb = new StringBuffer();
        if (null != errorEnum) {
            sb.append(errorEnum.getMessage());
        }
        if (null != t) {
            sb.append(ExceptionUtils.getStackTrace((Throwable)t, null));
        }
        return sb.toString();
    }
}

