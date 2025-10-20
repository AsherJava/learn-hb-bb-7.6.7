/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 */
package com.jiuqi.bde.fetch.impl.request.util;

import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import java.math.BigDecimal;
import java.util.Collection;

public class FetchDataUtil {
    public static String getDimCode(String formId, String regionId) {
        return BdeLogUtil.getDimCode((String)formId, (String)regionId);
    }

    public static void assertIsNotNull(Object object, String message, Object ... args) {
        if (object == null) {
            FetchDataUtil.throwException(message, args);
        }
    }

    public static void assertIsNotEmpty(String str, String message, Object ... args) {
        FetchDataUtil.assertIsFalse(StringUtils.isEmpty((String)str), message, args);
    }

    public static void assertIsNotEmpty(Collection<?> collection, String message, Object ... args) {
        if (collection == null || collection.isEmpty()) {
            FetchDataUtil.throwException(message, args);
        }
    }

    public static void assertIsFalse(boolean expression, String message, Object ... args) {
        FetchDataUtil.assertIsTrue(!expression, message, args);
    }

    public static void assertIsTrue(boolean expression, String message, Object ... args) {
        if (!expression) {
            FetchDataUtil.throwException(message, args);
        }
    }

    private static void throwException(String message, Object ... args) {
        throw new CheckRuntimeException(args == null || args.length == 0 ? message : String.format(message, args));
    }

    public static boolean valIsNum(Object col) {
        if (col == null) {
            return false;
        }
        return col instanceof BigDecimal || col instanceof Integer || col instanceof Double;
    }

    public static boolean valIsZero(Object col) {
        if (col == null) {
            return true;
        }
        if (col instanceof BigDecimal) {
            return ((BigDecimal)col).compareTo(BigDecimal.ZERO) == 0;
        }
        if (col instanceof Integer) {
            return ((Integer)col).compareTo(0) == 0;
        }
        if (col instanceof Double) {
            return NumberUtils.isZreo((Double)((Double)col));
        }
        return true;
    }
}

