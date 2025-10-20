/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.BudI18nUtil
 *  com.jiuqi.budget.common.utils.DateTimeCenter
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.budget.controller;

import com.jiuqi.budget.common.utils.BudI18nUtil;
import com.jiuqi.budget.common.utils.DateTimeCenter;
import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodFactory;
import com.jiuqi.budget.dataperiod.IDataPeriodType;
import com.jiuqi.budget.dataperiod.PeriodTypeGroup;
import com.jiuqi.budget.dataperiod.format.BaseDataPeriodFormat;
import com.jiuqi.budget.dataperiod.format.DayFormat3;
import com.jiuqi.budget.dataperiod.format.HalfYearFormat2;
import com.jiuqi.budget.dataperiod.format.MonthFormat2;
import com.jiuqi.budget.dataperiod.format.SeasonFormat3;
import com.jiuqi.budget.dataperiod.format.TendayFormat3;
import com.jiuqi.budget.dataperiod.format.WeekFormat2;
import com.jiuqi.budget.dataperiod.format.YearFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/budget/biz/dataPeriod"})
public class BudDataPeriodController {
    @GetMapping(value={"/getNextPeriodBySysTime/{periodType}"})
    public DataPeriod getNextPeriodBySysTime(@PathVariable IDataPeriodType periodType) {
        return DataPeriodFactory.valueOf(LocalDateTime.now(), periodType).offSet("+1" + periodType.toString());
    }

    @GetMapping(value={"/getTitle/{dataPeriod}"})
    public String getTitle(@PathVariable String dataPeriod) {
        BaseDataPeriodFormat baseDataPeriodFormat;
        Locale locale = BudI18nUtil.getLocale();
        DataPeriod period = DataPeriodFactory.valueOf(dataPeriod);
        IDataPeriodType type = period.getType();
        if (type.getGroup() == PeriodTypeGroup.CUSTOMIZE) {
            return period.getTitle();
        }
        switch (type.getType()) {
            case "N": {
                baseDataPeriodFormat = new YearFormat();
                break;
            }
            case "H": {
                baseDataPeriodFormat = new HalfYearFormat2();
                break;
            }
            case "J": {
                baseDataPeriodFormat = new SeasonFormat3();
                break;
            }
            case "Y": {
                baseDataPeriodFormat = new MonthFormat2();
                break;
            }
            case "X": {
                baseDataPeriodFormat = new TendayFormat3();
                break;
            }
            case "Z": {
                baseDataPeriodFormat = new WeekFormat2();
                break;
            }
            case "R": {
                baseDataPeriodFormat = new DayFormat3();
                break;
            }
            default: {
                return period.getTitle();
            }
        }
        return baseDataPeriodFormat.format(locale, period);
    }

    @GetMapping(value={"/getStartDay/{dataPeriod}"})
    public String getStartDay(@PathVariable String dataPeriod) {
        DataPeriod period = DataPeriodFactory.valueOf(dataPeriod);
        LocalDateTime startDay = period.getStartDay();
        return DateTimeCenter.formatDate((LocalDate)startDay.toLocalDate());
    }
}

