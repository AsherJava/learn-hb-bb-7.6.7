/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.GcPeriodUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.organization.impl.bean.PeriodDO
 *  com.jiuqi.gcreport.organization.impl.service.impl.PeriodProvider
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.gcreport.nr.impl.period;

import com.jiuqi.common.base.util.GcPeriodUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.organization.impl.bean.PeriodDO;
import com.jiuqi.gcreport.organization.impl.service.impl.PeriodProvider;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class UnitTreePeriodProvider
implements PeriodProvider {
    public PeriodDO transformByPeriodStr(String periodString) {
        return this.transformByPeriodStr(null, periodString);
    }

    public PeriodDO transformByPeriodStr(String formSchemeKey, String periodString) {
        String entityId = this.getEntityIdByFormSchemeKey(formSchemeKey);
        return this.transformByEntityId(entityId, periodString);
    }

    private PeriodDO transformByEntityId(String entityId, String periodString) {
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

    private PeriodDO transformByEntityIdAndDate(String entityId, int type, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        PeriodWrapper pw = PeriodType.fromType((int)type).fromCalendar(calendar);
        IPeriodAdapter adapter = this.getPeriodAdapterByEntityId(entityId);
        return this.createPeriod(pw, adapter);
    }

    private PeriodDO createPeriod(PeriodWrapper p, IPeriodAdapter adapter) {
        try {
            PeriodDO yp = new PeriodDO();
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

    private IPeriodAdapter getPeriodAdapterByEntityId(String entityId) {
        if (StringUtils.isEmpty((String)entityId)) {
            return new DefaultPeriodAdapter();
        }
        IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)SpringContextUtils.getBean(IPeriodEntityAdapter.class);
        IPeriodProvider periodAdapter = periodEntityAdapter.getPeriodProvider(entityId);
        return periodAdapter;
    }
}

