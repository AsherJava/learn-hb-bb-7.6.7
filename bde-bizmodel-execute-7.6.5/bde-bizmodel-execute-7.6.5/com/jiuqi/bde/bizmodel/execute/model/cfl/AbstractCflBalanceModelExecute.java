/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.SumTypeEnum
 *  com.jiuqi.common.base.util.NumberUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.cfl;

import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.SumTypeEnum;
import com.jiuqi.common.base.util.NumberUtils;
import java.math.BigDecimal;
import java.util.Map;

public abstract class AbstractCflBalanceModelExecute
extends AbstractGenericModelExecute {
    protected BigDecimal cflSumVal(SumTypeEnum sumType, FetchTypeEnum fetchType, BigDecimal sumVal) {
        return this.caclValByCfl(fetchType, sumVal);
    }

    protected BigDecimal caclValByCfl(FetchTypeEnum fetchType, BigDecimal sumVal) {
        switch (fetchType) {
            case JNC: 
            case JYH: 
            case WJNC: 
            case WJYH: {
                if (sumVal.compareTo(BigDecimal.ZERO) > 0) {
                    return sumVal;
                }
                return BigDecimal.ZERO;
            }
            case DNC: 
            case DYH: 
            case WDNC: 
            case WDYH: {
                if (sumVal.compareTo(BigDecimal.ZERO) < 0) {
                    return sumVal.abs();
                }
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    protected void caclVal(SumTypeEnum sumType, FetchTypeEnum fetchType, Map<String, BigDecimal> sumVal, AssBalanceData assBalance) {
        switch (fetchType) {
            case JNC: 
            case DNC: {
                this.caclVal(sumVal, sumType, fetchType, this.getSumKey(sumType, assBalance), assBalance.getNc());
                break;
            }
            case JYH: 
            case DYH: {
                this.caclVal(sumVal, sumType, fetchType, this.getSumKey(sumType, assBalance), assBalance.getYe());
                break;
            }
            case WJNC: 
            case WDNC: {
                this.caclVal(sumVal, sumType, fetchType, this.getSumKey(sumType, assBalance), assBalance.getWnc());
                break;
            }
            case WJYH: 
            case WDYH: {
                this.caclVal(sumVal, sumType, fetchType, this.getSumKey(sumType, assBalance), assBalance.getWye());
                break;
            }
        }
    }

    protected void caclVal(Map<String, BigDecimal> sumVal, SumTypeEnum sumType, FetchTypeEnum fetchType, String sumKey, BigDecimal val) {
        sumVal.computeIfAbsent(sumKey, key -> BigDecimal.ZERO);
        sumVal.put(sumKey, NumberUtils.sum((BigDecimal)sumVal.get(sumKey), (BigDecimal)val));
    }

    protected String getSumKey(SumTypeEnum sumType, AssBalanceData assBalance) {
        switch (sumType) {
            case FMX: {
                return assBalance.getAssistKey();
            }
        }
        return assBalance.getSubjectCode().concat(assBalance.getAssistKey());
    }
}

