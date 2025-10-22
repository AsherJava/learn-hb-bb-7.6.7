/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;

public enum FlexibleDimSrcTypeEnum {
    ITEM,
    DEBIT,
    CREDIT,
    DEBIT_RATE,
    CREDIT_RATE,
    ALL_RATE,
    NONE,
    CUSTOMIZE_FORMULA;


    public static FetchTypeEnum toFetchType(FlexibleDimSrcTypeEnum dimSrcType) {
        switch (dimSrcType) {
            case DEBIT: 
            case DEBIT_RATE: {
                return FetchTypeEnum.DEBIT_SUM;
            }
            case CREDIT: 
            case CREDIT_RATE: {
                return FetchTypeEnum.CREDIT_SUM;
            }
            case ALL_RATE: 
            case CUSTOMIZE_FORMULA: {
                return FetchTypeEnum.SUM;
            }
        }
        return null;
    }
}

