/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.quickreport.model.HeaderMode
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.zbquery.model.HeaderMode;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryField;

public class EnumTransfer {
    public static TimeGranularity toTimeGranularity(PeriodType periodType) {
        if (periodType == PeriodType.YEAR) {
            return TimeGranularity.YEAR;
        }
        if (periodType == PeriodType.HALFYEAR) {
            return TimeGranularity.HALFYEAR;
        }
        if (periodType == PeriodType.SEASON) {
            return TimeGranularity.QUARTER;
        }
        if (periodType == PeriodType.MONTH) {
            return TimeGranularity.MONTH;
        }
        if (periodType == PeriodType.TENDAY) {
            return TimeGranularity.XUN;
        }
        if (periodType == PeriodType.DAY) {
            return TimeGranularity.DAY;
        }
        return null;
    }

    public static PeriodType toPeriodType(com.jiuqi.bi.adhoc.model.TimeGranularity timeGranularity) {
        if (timeGranularity == com.jiuqi.bi.adhoc.model.TimeGranularity.YEAR) {
            return PeriodType.YEAR;
        }
        if (timeGranularity == com.jiuqi.bi.adhoc.model.TimeGranularity.HALFYEAR) {
            return PeriodType.HALFYEAR;
        }
        if (timeGranularity == com.jiuqi.bi.adhoc.model.TimeGranularity.QUARTER) {
            return PeriodType.SEASON;
        }
        if (timeGranularity == com.jiuqi.bi.adhoc.model.TimeGranularity.MONTH) {
            return PeriodType.MONTH;
        }
        if (timeGranularity == com.jiuqi.bi.adhoc.model.TimeGranularity.XUN) {
            return PeriodType.TENDAY;
        }
        if (timeGranularity == com.jiuqi.bi.adhoc.model.TimeGranularity.DAY) {
            return PeriodType.DAY;
        }
        return null;
    }

    public static PeriodType toPeriodType(TimeGranularity timeGranularity) {
        if (timeGranularity == TimeGranularity.YEAR) {
            return PeriodType.YEAR;
        }
        if (timeGranularity == TimeGranularity.HALFYEAR) {
            return PeriodType.HALFYEAR;
        }
        if (timeGranularity == TimeGranularity.QUARTER) {
            return PeriodType.SEASON;
        }
        if (timeGranularity == TimeGranularity.MONTH) {
            return PeriodType.MONTH;
        }
        if (timeGranularity == TimeGranularity.XUN) {
            return PeriodType.TENDAY;
        }
        if (timeGranularity == TimeGranularity.DAY) {
            return PeriodType.DAY;
        }
        return null;
    }

    public static FieldType toFieldType(QueryDimensionType queryDimensionType) {
        if (queryDimensionType == QueryDimensionType.PERIOD) {
            return FieldType.TIME_DIM;
        }
        return FieldType.GENERAL_DIM;
    }

    public static ApplyType toApplyType(DataFieldApplyType applyType) {
        switch (applyType) {
            case PERIODIC: {
                return ApplyType.PERIOD;
            }
            case TIME_POINT: {
                return ApplyType.TIMEPOINT;
            }
            case OPENING_BALANCE: {
                return ApplyType.PERIOD_OPENNING_BLANCE;
            }
            case CLOSING_BALANCE: {
                return ApplyType.PERIOD_CLOSING_BLANCE;
            }
            case SUM: {
                return ApplyType.TOTAL;
            }
        }
        return ApplyType.PERIOD;
    }

    public static com.jiuqi.bi.quickreport.model.HeaderMode toHeaderMode(HeaderMode headerMode) {
        if (headerMode == HeaderMode.LIST) {
            return com.jiuqi.bi.quickreport.model.HeaderMode.LIST;
        }
        return com.jiuqi.bi.quickreport.model.HeaderMode.MERGE;
    }

    public static boolean isFileDataType(QueryField queryField) {
        return queryField.getDataType() == DataFieldType.FILE.getValue();
    }
}

