/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.jetbrains.annotations.Nullable
 */
package com.jiuqi.nr.entity.ext.version.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.entity.common.Utils;
import com.jiuqi.nr.entity.ext.version.IVersionQueryService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class VersionQueryServiceImpl
implements IVersionQueryService {
    private static final Logger log = LoggerFactory.getLogger(VersionQueryServiceImpl.class);
    @Autowired
    private PeriodEngineService periodEngineService;

    @Override
    public Date getVersionDate(DimensionValueSet masterKeys, String periodViewKey, String entityId) {
        return this.getVersionDate(masterKeys, periodViewKey);
    }

    @Override
    public Date getVersionDate(DimensionValueSet masterKeys, String periodViewKey) {
        if (masterKeys == null) {
            return null;
        }
        Object value = this.getPeriodDimensionValue(masterKeys);
        Date endTime = null;
        if (value != null) {
            String periodCode = String.valueOf(value);
            if (StringUtils.hasText(periodViewKey)) {
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodViewKey);
                if (periodProvider == null) {
                    throw new RuntimeException(String.format("\u65f6\u671f\u7c7b\u578b%s\u65e0\u6cd5\u8bc6\u522b", periodViewKey));
                }
                IPeriodEntity periodEntity = periodProvider.getPeriodEntity();
                PeriodType periodType = periodEntity.getPeriodType();
                if (PeriodUtils.isPeriod13((String)periodCode, (PeriodType)periodType)) {
                    try {
                        endTime = PeriodUtils.transPeriod13Data((String)periodCode)[1];
                    }
                    catch (Exception e) {
                        log.error(String.format("\u89e3\u6790\u4e1a\u52a1\u65f6\u671f%s\u65f6\u671f\u7c7b\u578b%s\u7684\u65f6\u95f4\u503c\u65f6\u53d1\u751f\u9519\u8bef\uff1a%s", periodCode, periodViewKey, e.getMessage()), e);
                    }
                } else {
                    try {
                        endTime = periodProvider.getPeriodDateRegion(periodCode)[1];
                    }
                    catch (Exception e) {
                        log.error(String.format("\u89e3\u6790\u4e1a\u52a1\u65f6\u671f%s\u65f6\u671f\u7c7b\u578b%s\u7684\u65f6\u95f4\u503c\u65f6\u53d1\u751f\u9519\u8bef\uff1a%s", periodCode, periodViewKey, e.getMessage()), e);
                    }
                }
                if (endTime == null) {
                    endTime = this.getEndTimeByPeriodCode(periodCode);
                }
            } else {
                endTime = this.getEndTimeByPeriodCode(periodCode);
            }
        }
        return endTime;
    }

    @Nullable
    private Date getEndTimeByPeriodCode(String periodCode) {
        String[] timesArr = null;
        try {
            timesArr = PeriodUtil.getTimesArr((String)periodCode);
        }
        catch (Exception e) {
            log.error(String.format("\u4e1a\u52a1\u65f6\u671f%s\u683c\u5f0f\u5316\u4e3a\u65e5\u671f\u533a\u95f4\u9519\u8bef", periodCode), e);
        }
        if (timesArr == null) {
            return null;
        }
        if (timesArr.length != 2) {
            GregorianCalendar gregorianCalendar = null;
            try {
                gregorianCalendar = PeriodUtil.period2Calendar((String)periodCode);
            }
            catch (Exception e) {
                log.error(String.format("\u4e1a\u52a1\u65f6\u671f%s\u683c\u5f0f\u8f6c\u6362\u4e3a\u65e5\u5386\u5bf9\u8c61\u9519\u8bef", periodCode), e);
            }
            if (gregorianCalendar == null) {
                return null;
            }
            return new Date(gregorianCalendar.getTimeInMillis());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date entTime = new Date();
        try {
            entTime = simpleDateFormat.parse(timesArr[1]);
        }
        catch (ParseException e) {
            log.error(String.format("\u4e1a\u52a1\u65f6\u671f%s\u683c\u5f0f\u5316\u4e3a\u65f6\u95f4\u9519\u8bef\uff0c\u9519\u8bef\u65f6\u95f4\u5b57\u7b26\u4e32\u4e3a:%s", periodCode, timesArr[1]), e);
        }
        return Utils.getEndTimeOfDay(entTime);
    }

    protected Object getPeriodDimensionValue(DimensionValueSet masterKeys) {
        return masterKeys.getValue("DATATIME");
    }
}

