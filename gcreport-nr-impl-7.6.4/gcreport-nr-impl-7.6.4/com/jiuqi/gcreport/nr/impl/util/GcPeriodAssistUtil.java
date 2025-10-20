/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.nr.impl.util;

import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;

public class GcPeriodAssistUtil {
    public static YearPeriodDO getPeriodObject(String formSchemeKey, String periodString) {
        return YearPeriodUtil.transform((String)formSchemeKey, (String)periodString);
    }

    public static YearPeriodDO getPeriodObject(DataEntryContext context, String periodString) {
        return YearPeriodUtil.transform((String)context.getFormSchemeKey(), (String)periodString);
    }
}

