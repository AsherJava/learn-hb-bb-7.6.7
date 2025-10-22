/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeHelper
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.period.common.utils;

import com.jiuqi.bi.adhoc.model.TimeGranularity;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeHelper;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nr.period.common.utils.TimeDimUtils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BqlTimeDimUtils {
    private static HashSet<String> STANDARD_PERIODS = new HashSet<String>(Arrays.asList("N", "H", "J", "Y", "X", "Z", "R", "NR_PERIOD_N", "NR_PERIOD_H", "NR_PERIOD_J", "NR_PERIOD_Y", "NR_PERIOD_X", "NR_PERIOD_Z", "NR_PERIOD_R"));
    private static I18nHelper i18nHelper;
    private static PeriodEngineService periodEngineService;
    private static final String I18N_KEY_PERIOD = "nvwa.base.period";
    private static final String I18N_KEY_YEAR = "nvwa.base.year";
    private static final String I18N_KEY_QUARTER = "nvwa.base.quarter";
    private static final String I18N_KEY_MONTH = "nvwa.base.month";
    private static final String I18N_KEY_DAY = "nvwa.base.day";
    public static final String NR_PERIOD_FORMAT_NAME = "nr.period.format";

    public static TimeGranularity adaptTimeGranularity(PeriodType periodType) {
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
        if (periodType == PeriodType.WEEK) {
            return TimeGranularity.WEEK;
        }
        return null;
    }

    public static String periodCode2TimeKey(String periodCode) {
        return TimeDimUtils.periodToTimeKey(periodCode);
    }

    public static String getBqlTimeDimTable(String periodEntityKey) {
        return PeriodUtils.addPrefix(periodEntityKey);
    }

    @Deprecated
    public static List<TimeDimField> getTimeDimFields(String periodEntityKey) {
        PeriodType periodType = PeriodType.fromCode((int)periodEntityKey.charAt(0));
        return BqlTimeDimUtils.getTimeDimFields(periodEntityKey, periodType);
    }

    public static List<TimeDimField> getTimeDimFields(String periodEntityKey, PeriodType periodType) {
        IPeriodEntity periodEntity;
        if ((periodType == null || PeriodType.DEFAULT == periodType) && (periodEntity = BqlTimeDimUtils.getPeriodEntity(periodEntityKey)) != null) {
            periodType = periodEntity.getPeriodType();
        }
        ArrayList<TimeDimField> fields = new ArrayList<TimeDimField>();
        if (periodType == PeriodType.CUSTOM) {
            TimeDimField field = new TimeDimField();
            field.setName(PeriodTableColumn.TITLE.getCode());
            field.setDataType(PeriodTableColumn.TITLE.getType().getValue());
            field.setTitle(BqlTimeDimUtils.getTimeDimFieldTitle(null));
            fields.add(field);
            return fields;
        }
        switch (periodType) {
            case DAY: {
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.TIMEKEY, periodType);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.YEAR, PeriodType.YEAR);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.QUARTER, PeriodType.SEASON);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.MONTH, PeriodType.MONTH);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.DAY, PeriodType.DAY);
                break;
            }
            case TENDAY: {
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.TIMEKEY, periodType);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.YEAR, PeriodType.YEAR);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.QUARTER, PeriodType.SEASON);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.MONTH, PeriodType.MONTH);
                break;
            }
            case MONTH: {
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.TIMEKEY, periodType);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.YEAR, PeriodType.YEAR);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.QUARTER, PeriodType.SEASON);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.MONTH, PeriodType.MONTH);
                break;
            }
            case SEASON: {
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.TIMEKEY, periodType);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.YEAR, PeriodType.YEAR);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.QUARTER, PeriodType.SEASON);
                break;
            }
            case HALFYEAR: {
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.TIMEKEY, periodType);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.YEAR, PeriodType.YEAR);
                break;
            }
            case YEAR: {
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.TIMEKEY, periodType);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.YEAR, PeriodType.YEAR);
                break;
            }
            case WEEK: {
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.TIMEKEY, periodType);
                BqlTimeDimUtils.addTimeDimField(fields, PeriodTableColumn.YEAR, PeriodType.YEAR);
                break;
            }
        }
        return fields;
    }

    public static PeriodType getPeriodType(String periodEntityKey) {
        IPeriodEntity periodEntity = BqlTimeDimUtils.getPeriodEntity(periodEntityKey);
        if (periodEntity != null) {
            return periodEntity.getPeriodType();
        }
        return PeriodType.DEFAULT;
    }

    private static void addTimeDimField(List<TimeDimField> fields, PeriodTableColumn column, PeriodType periodType) {
        TimeDimField field = BqlTimeDimUtils.getTimeDimField(column, periodType);
        fields.add(field);
    }

    public static TimeDimField getTimeDimField(PeriodTableColumn column, PeriodType periodType) {
        TimeDimField field = new TimeDimField();
        field.setName(column.getCode());
        field.setDataType(column.getType().getValue());
        TimeGranularity timeGranularity = BqlTimeDimUtils.adaptTimeGranularity(periodType);
        field.setTimeGranularity(timeGranularity);
        if (PeriodTableColumn.TIMEKEY == column) {
            field.setTimeKey(true);
            timeGranularity = null;
        }
        String title = BqlTimeDimUtils.getTimeDimFieldTitle(timeGranularity);
        field.setTitle(title);
        return field;
    }

    private static String getTimeDimFieldTitle(TimeGranularity timeGranularity) {
        String i18nKey = I18N_KEY_PERIOD;
        if (timeGranularity != null) {
            i18nKey = timeGranularity == TimeGranularity.YEAR ? I18N_KEY_YEAR : (timeGranularity == TimeGranularity.QUARTER ? I18N_KEY_QUARTER : (timeGranularity == TimeGranularity.MONTH ? I18N_KEY_MONTH : (timeGranularity == TimeGranularity.DAY ? I18N_KEY_DAY : null)));
        }
        if (i18nKey != null) {
            if (i18nHelper == null) {
                i18nHelper = (I18nHelper)SpringBeanUtils.getBean((String)"nvwa", I18nHelper.class);
            }
            return i18nHelper.getMessage(i18nKey);
        }
        if (timeGranularity != null) {
            return timeGranularity.title();
        }
        return "\u65f6\u671f";
    }

    public static String getTimeKeyFieldShowPattern(TimeGranularity timeGranularity, String periodEntityKey) throws TimeCalcException {
        String periodEntityId = BqlTimeDimUtils.getPeriodEntityId(periodEntityKey);
        IPeriodEntity periodEntity = BqlTimeDimUtils.getPeriodEntity(periodEntityId);
        if (PeriodUtils.checkTitle(periodEntity)) {
            return "${nr.period.format:" + periodEntityId + "}";
        }
        return TimeHelper.getDefaultShowPattern((int)timeGranularity.value(), (boolean)true);
    }

    public static String getPeriodEntityId(String periodTableName) {
        if (periodTableName.startsWith("NR_PERIOD_")) {
            return periodTableName.substring("NR_PERIOD_".length());
        }
        return periodTableName;
    }

    public static boolean isCustomPeriod(String entityKeyOrCode) {
        boolean notStandard;
        boolean bl = notStandard = !STANDARD_PERIODS.contains(entityKeyOrCode);
        if (!notStandard) {
            PeriodType periodType = BqlTimeDimUtils.getPeriodType(entityKeyOrCode);
            return PeriodType.CUSTOM == periodType;
        }
        return false;
    }

    private static IPeriodEntity getPeriodEntity(String periodEntityKey) {
        if (periodEngineService == null) {
            periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
        }
        IPeriodEntity periodEntity = periodEngineService.getPeriodAdapter().getPeriodEntity(periodEntityKey);
        return periodEntity;
    }
}

