/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.service;

import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeExtendExecutor;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.impl.FcDefaultModeSchemeExtendExecutor;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.impl.FcOffsetVoucherModeSchemeExtendExecutor;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.np.core.utils.SpringBeanUtils;

public class FinancialCheckSchemeExtendExecutorFactory {
    public static FinancialCheckSchemeExtendExecutor getExecutor(String checkMode) {
        if (CheckModeEnum.OFFSETVCHR.getCode().equals(checkMode)) {
            return (FinancialCheckSchemeExtendExecutor)SpringBeanUtils.getBean(FcOffsetVoucherModeSchemeExtendExecutor.class);
        }
        return (FinancialCheckSchemeExtendExecutor)SpringBeanUtils.getBean(FcDefaultModeSchemeExtendExecutor.class);
    }
}

