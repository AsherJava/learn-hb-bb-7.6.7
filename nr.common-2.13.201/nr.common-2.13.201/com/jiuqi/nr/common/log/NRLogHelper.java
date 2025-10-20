/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.log.enums.OperResult
 */
package com.jiuqi.nr.common.log;

import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.log.enums.OperResult;
import com.jiuqi.nr.common.log.NRLogEntity;

public class NRLogHelper {
    private NRLogHelper() {
    }

    public static void info(NRLogEntity logEntity) {
        LogHelper.info((String)logEntity.getModule(), (String)logEntity.getTitle(), (String)logEntity.getMessage(), logEntity.getCustomMap(), (OperResult)OperResult.SUCCESS, (OperLevel)logEntity.getOperLevel());
    }

    public static void error(NRLogEntity logEntity) {
        LogHelper.error((String)logEntity.getModule(), (String)logEntity.getTitle(), (String)logEntity.getMessage(), logEntity.getCustomMap(), (OperResult)OperResult.FAIL, (OperLevel)logEntity.getOperLevel());
    }
}

