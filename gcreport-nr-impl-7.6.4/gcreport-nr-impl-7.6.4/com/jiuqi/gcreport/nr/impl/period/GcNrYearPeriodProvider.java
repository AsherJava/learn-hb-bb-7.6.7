/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.GcPeriodUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.period.IYearPeriodProvider
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.gcreport.nr.impl.period;

import com.jiuqi.common.base.util.GcPeriodUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.period.IYearPeriodProvider;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class GcNrYearPeriodProvider
implements IYearPeriodProvider {
    public YearPeriodDO transform() {
        return this.transform(null);
    }

    public YearPeriodDO transform(int year, int period) {
        PeriodWrapper pw = new PeriodWrapper(year, 4, period);
        String entityId = this.getEntityIdByFormSchemeKey(null);
        IPeriodAdapter adapter = this.getPeriodAdapterByEntityId(entityId);
        return this.createPeriod(pw, adapter);
    }

    public YearPeriodDO transform(String formSchemeKey) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        return this.transform(formSchemeKey, 4, date);
    }

    public YearPeriodDO transform(String formSchemeKey, String periodString) {
        String entityId = this.getEntityIdByFormSchemeKey(formSchemeKey);
        return this.transformByEntityId(entityId, periodString);
    }

    public YearPeriodDO transform(String formSchemeKey, int year, int type, int period) {
        PeriodWrapper pw = new PeriodWrapper(year, type, period);
        String entityId = this.getEntityIdByFormSchemeKey(formSchemeKey);
        IPeriodAdapter adapter = this.getPeriodAdapterByEntityId(entityId);
        return this.createPeriod(pw, adapter);
    }

    public YearPeriodDO transform(String formSchemeKey, int type, Calendar calendar) {
        PeriodWrapper pw = PeriodType.fromType((int)type).fromCalendar(calendar);
        String entityId = this.getEntityIdByFormSchemeKey(formSchemeKey);
        IPeriodAdapter adapter = this.getPeriodAdapterByEntityId(entityId);
        return this.createPeriod(pw, adapter);
    }

    public YearPeriodDO transformByDataSchemeKey(String dataSchemeKey, String periodString) {
        String entityId = this.getEntityIdByDataSchemeKey(dataSchemeKey);
        return this.transformByEntityId(entityId, periodString);
    }

    private YearPeriodDO transformByEntityId(String entityId, String periodString) {
        if (StringUtils.isEmpty((String)periodString)) {
            return this.transformByEntityIdAndDate(entityId, 4, new Date());
        }
        if (!StringUtils.isEmpty((String)entityId)) {
            PeriodWrapper pw = new PeriodWrapper(periodString);
            IPeriodAdapter adapter = this.getPeriodAdapterByEntityId(entityId);
            return this.createPeriod(pw, adapter);
        }
        try {
            PeriodWrapper periodWrapper = new PeriodWrapper();
            periodWrapper.parseString(periodString);
            return this.createPeriod(periodWrapper, null);
        }
        catch (Exception exp) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            try {
                Date date = df.parse(periodString);
                return this.transformByEntityIdAndDate(entityId, 4, date);
            }
            catch (ParseException e) {
                return this.transformByEntityIdAndDate(entityId, 4, new Date());
            }
        }
    }

    private YearPeriodDO transformByEntityIdAndDate(String entityId, int type, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        PeriodWrapper pw = PeriodType.fromType((int)type).fromCalendar(calendar);
        IPeriodAdapter adapter = this.getPeriodAdapterByEntityId(entityId);
        return this.createPeriod(pw, adapter);
    }

    private YearPeriodDO createPeriod(PeriodWrapper p, IPeriodAdapter adapter) {
        try {
            YearPeriodDO yp = new YearPeriodDO();
            Date[] periods = adapter == null ? GcPeriodUtils.getDateArr((PeriodWrapper)p) : adapter.getPeriodDateRegion(p);
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

    private String getEntityIdByFormSchemeKey(String formSchemeKey) {
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            return null;
        }
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        String dataTimeEntityId = runTimeViewController.getFormScheme(formSchemeKey).getDateTime();
        return dataTimeEntityId;
    }

    private String getEntityIdByDataSchemeKey(String dataSchemeKey) {
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            return null;
        }
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        List dataDimensions = dataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.PERIOD);
        String dataTimeEntityId = ((DataDimension)dataDimensions.get(0)).getDimKey();
        return dataTimeEntityId;
    }

    private IPeriodAdapter getPeriodAdapterByEntityId(String entityId) {
        if (StringUtils.isEmpty((String)entityId)) {
            return new DefaultPeriodAdapter();
        }
        IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)SpringContextUtils.getBean(IPeriodEntityAdapter.class);
        IPeriodProvider periodAdapter = periodEntityAdapter.getPeriodProvider(entityId);
        return periodAdapter;
    }
}

