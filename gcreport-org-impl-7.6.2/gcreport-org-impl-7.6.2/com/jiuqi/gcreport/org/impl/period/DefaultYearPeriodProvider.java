/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.period.IYearPeriodProvider
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 */
package com.jiuqi.gcreport.org.impl.period;

import com.jiuqi.gcreport.org.api.period.IYearPeriodProvider;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.impl.period.PeriodObject;
import java.util.Calendar;
import org.springframework.stereotype.Component;

@Component
public class DefaultYearPeriodProvider
implements IYearPeriodProvider {
    public YearPeriodDO transform() {
        PeriodObject pObject = PeriodObject.getInstance();
        return this.createPeriod(pObject);
    }

    public YearPeriodDO transform(int year, int period) {
        PeriodObject pObject = PeriodObject.getInstance(year, period);
        return this.createPeriod(pObject);
    }

    public YearPeriodDO transform(String formSchemeKey) {
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u65f6\u671f\u7c7b\u578b\u83b7\u53d6\u3002");
    }

    public YearPeriodDO transform(String formSchemeKey, String periodString) {
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u65f6\u671f\u7c7b\u578b\u83b7\u53d6\u3002");
    }

    public YearPeriodDO transform(String formSchemeKey, int year, int type, int period) {
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u65f6\u671f\u7c7b\u578b\u83b7\u53d6\u3002");
    }

    public YearPeriodDO transform(String formSchemeKey, int type, Calendar calendar) {
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u65f6\u671f\u7c7b\u578b\u83b7\u53d6\u3002");
    }

    public YearPeriodDO transformByDataSchemeKey(String dataSchemeKey, String periodString) {
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u65f6\u671f\u7c7b\u578b\u83b7\u53d6\u3002");
    }

    private YearPeriodDO createPeriod(PeriodObject p) {
        YearPeriodDO yp = new YearPeriodDO();
        yp.setYear(p.getYear());
        yp.setType(p.getType().getId());
        yp.setTypeCode(p.getType().getCode());
        yp.setPeriod(p.getPeriod());
        yp.setBeginDate(p.getPeriodDateBegin());
        yp.setEndDate(p.getPeriodDateEnd());
        yp.setFormatValue(p.toString());
        return yp;
    }
}

