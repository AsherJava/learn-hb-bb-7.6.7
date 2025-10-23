/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.util.EnumTransfer;

public class PeriodUtil {
    public static String toBIPeriod(String nrPeriod, PeriodType periodType) {
        if (nrPeriod.length() == 8 || nrPeriod.length() == 4) {
            return nrPeriod;
        }
        return TimeDimUtils.getTimeDimByPeriodWrapper((PeriodWrapper)new PeriodWrapper(nrPeriod), (TimeGranularity)PeriodUtil.toTimeGranularity(periodType));
    }

    public static String toNrPeriod(String biPeriod, PeriodType periodType) {
        if (periodType == PeriodType.CUSTOM) {
            return biPeriod;
        }
        if (biPeriod.length() == 9) {
            return biPeriod;
        }
        return TimeDimUtils.getDataTimeByTimeDim((String)biPeriod, (String)String.valueOf((char)Character.toUpperCase(periodType.code())));
    }

    public static TimeGranularity toTimeGranularity(PeriodType periodType) {
        return EnumTransfer.toTimeGranularity(periodType);
    }

    public static String getShowFormat(PeriodType periodType, boolean timekey) {
        switch (periodType) {
            case YEAR: {
                return "yyyy\u5e74";
            }
            case HALFYEAR: {
                if (timekey) {
                    return "yyyy\u5e74BBB";
                }
                return "BBB";
            }
            case SEASON: {
                if (timekey) {
                    return "yyyy\u5e74Q\u5b63\u5ea6";
                }
                return "Q\u5b63\u5ea6";
            }
            case TENDAY: {
                if (timekey) {
                    return "yyyy\u5e74M\u6708XXX";
                }
                return "XXX";
            }
            case MONTH: {
                if (timekey) {
                    return "yyyy\u5e74M\u6708";
                }
                return "M\u6708";
            }
            case DAY: {
                if (timekey) {
                    return "yyyy\u5e74M\u6708d\u65e5";
                }
                return "d\u65e5";
            }
        }
        return null;
    }

    public static PeriodType getPeriodType(String dimFullName, PeriodType defaultValue) {
        PeriodType periodType;
        String code = BqlTimeDimUtils.getPeriodEntityId((String)dimFullName);
        if (code.length() == 1 && (periodType = PeriodType.fromCode((int)code.charAt(0))) != null && periodType != PeriodType.DEFAULT) {
            return periodType;
        }
        periodType = BqlTimeDimUtils.getPeriodType((String)code);
        if (periodType != null) {
            return periodType;
        }
        return defaultValue;
    }

    public static boolean is13Period(QueryDimension queryDim) {
        return queryDim.getDimensionType() == QueryDimensionType.PERIOD && queryDim.getPeriodType() == PeriodType.MONTH && !"NR_PERIOD_Y".equalsIgnoreCase(queryDim.getName());
    }
}

