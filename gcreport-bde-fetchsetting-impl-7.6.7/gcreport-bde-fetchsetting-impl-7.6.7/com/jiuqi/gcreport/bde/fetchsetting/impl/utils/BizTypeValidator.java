/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.utils;

import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;

public class BizTypeValidator {
    public static boolean isValidBud(String bizType) {
        return BizTypeEnum.BUDGET.getCode().equals(bizType);
    }

    public static boolean isValidBud(String bizType, String message) {
        if (BizTypeEnum.BUDGET.getCode().equals(bizType)) {
            return true;
        }
        throw new BdeRuntimeException(message);
    }
}

