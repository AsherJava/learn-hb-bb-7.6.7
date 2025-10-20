/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.text.TimeFieldFormat
 */
package com.jiuqi.bi.dataset.format;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.text.TimeFieldFormat;
import java.util.Locale;

public class TimeDimFormat
extends TimeFieldFormat {
    private static final long serialVersionUID = -1834674616002331587L;

    public TimeDimFormat(BIDataSetFieldInfo info) {
        this(info.getTimegranularity(), info.getShowPattern(), info.getValType(), info.isTimekey(), info.getDataPattern());
    }

    public TimeDimFormat(BIDataSetFieldInfo info, Locale locale) {
        this(info.getTimegranularity(), info.getShowPattern(), info.getValType(), info.isTimekey(), info.getDataPattern(), locale);
    }

    public TimeDimFormat(TimeGranularity granularity, String showPattern, int dataType, boolean isTimeKey, String dataPattern) {
        this(granularity, showPattern, dataType, isTimeKey, dataPattern, Locale.getDefault());
    }

    public TimeDimFormat(TimeGranularity granularity, String showPattern, int dataType, boolean isTimeKey, String dataPattern, Locale locale) {
        super(granularity.value(), showPattern, dataType, isTimeKey, dataPattern, locale);
    }
}

