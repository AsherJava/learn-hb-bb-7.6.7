/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.GcPeriodUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.organization.impl.service.impl;

import com.jiuqi.common.base.util.GcPeriodUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.organization.impl.bean.PeriodDO;
import com.jiuqi.gcreport.organization.impl.service.impl.PeriodProvider;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DefaultPeriodProvider
implements PeriodProvider {
    @Override
    public PeriodDO transformByPeriodStr(String periodString) {
        if (StringUtils.isEmpty((String)periodString)) {
            return this.transformByDate(4, new Date());
        }
        try {
            PeriodWrapper periodWrapper = new PeriodWrapper();
            periodWrapper.parseString(periodString);
            return this.createPeriod(periodWrapper);
        }
        catch (Exception exp) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            try {
                Date date = df.parse(periodString);
                return this.transformByDate(4, date);
            }
            catch (ParseException e) {
                return this.transformByDate(4, new Date());
            }
        }
    }

    @Override
    public PeriodDO transformByPeriodStr(String formSchemeKey, String periodString) {
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            return this.transformByPeriodStr(periodString);
        }
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u65f6\u671f\u7c7b\u578b\u83b7\u53d6\u3002");
    }

    private PeriodDO transformByDate(int type, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        PeriodWrapper pw = PeriodType.fromType((int)type).fromCalendar(calendar);
        return this.createPeriod(pw);
    }

    private PeriodDO createPeriod(PeriodWrapper p) {
        try {
            PeriodDO yp = new PeriodDO();
            Date[] periods = GcPeriodUtils.getDateArr((PeriodWrapper)p);
            yp.setYear(p.getYear());
            yp.setType(p.getType());
            yp.setTypeCode((char)PeriodConsts.typeToCode((int)p.getType()));
            yp.setPeriod(p.getPeriod());
            yp.setBeginDate(periods[0]);
            yp.setEndDate(periods[1]);
            yp.setFormatValue(p.toString());
            return yp;
        }
        catch (Exception e) {
            throw new RuntimeException("\u89e3\u6790\u671f\u95f4\u5931\u8d25." + p.toString(), e);
        }
    }

    private IPeriodAdapter getPeriodAdapterByEntityId() {
        return new DefaultPeriodAdapter();
    }
}

