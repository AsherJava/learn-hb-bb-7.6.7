/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.dataentry.internal.service.util.DateTimeUtil;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IStartFillDateService;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartFillDateServiceImpl
implements IStartFillDateService {
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Resource
    private ITemplateConfigService templateConfigService;
    @Resource
    private IRunTimeViewController runtimeView;
    private final Logger logger = LoggerFactory.getLogger(StartFillDateServiceImpl.class);

    @Override
    public Map<String, String> fillDateMap(String taskKey) {
        FormSchemeDefine formScheme;
        Object beginPeriodModify;
        FillDateType fillingDateType;
        List schemePeriodLinkDefineList;
        HashMap<String, String> periodSchemeMap;
        TaskDefine taskDefine;
        block15: {
            Date dateAfterFormat;
            IPeriodProvider periodProvider;
            block16: {
                Object periodOfFormate;
                taskDefine = this.runtimeView.queryTaskDefine(taskKey);
                periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
                periodSchemeMap = new HashMap<String, String>();
                schemePeriodLinkDefineList = null;
                try {
                    schemePeriodLinkDefineList = this.iRunTimeViewController.querySchemePeriodLinkByTask(taskKey);
                }
                catch (Exception e) {
                    this.logger.error("");
                }
                Date date = DateTimeUtil.getDay();
                fillingDateType = taskDefine.getFillingDateType();
                beginPeriodModify = null;
                dateAfterFormat = date;
                if (fillingDateType.equals((Object)FillDateType.NONE) || taskDefine.getFillingDateDays() <= 0) break block15;
                if (fillingDateType.equals((Object)FillDateType.NATURAL_DAY)) {
                    dateAfterFormat = DateTimeUtil.getDateOfBeforeDay(date, taskDefine.getFillingDateDays() - 1);
                } else if (fillingDateType.equals((Object)FillDateType.WORK_DAY)) {
                    dateAfterFormat = DateTimeUtil.getDateOfBeforeWorkDay(date, taskDefine.getFillingDateDays() - 1);
                }
                if (taskDefine.getPeriodType() == PeriodType.CUSTOM) break block16;
                beginPeriodModify = periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskDefine.getPeriodType().type(), (Date)dateAfterFormat);
                if (taskDefine.getTaskPeriodOffset() == 0) break block15;
                PeriodModifier periodModifier = new PeriodModifier();
                periodModifier.setPeriodModifier(taskDefine.getTaskPeriodOffset());
                beginPeriodModify = periodProvider.modify((String)beginPeriodModify, periodModifier);
                break block15;
            }
            if (taskDefine.getTaskPeriodOffset() != 0) {
                for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                    Date endDate;
                    Date beginDate;
                    String modify = schemePeriodLinkDefine.getPeriodKey();
                    if (taskDefine.getTaskPeriodOffset() != 0) {
                        PeriodModifier periodModifier = new PeriodModifier();
                        periodModifier.setPeriodModifier(~taskDefine.getTaskPeriodOffset());
                        modify = periodProvider.modify(modify, periodModifier);
                    }
                    formScheme = this.runtimeView.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                    Date[] periodRegion = new Date[]{};
                    try {
                        periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion(modify);
                    }
                    catch (ParseException e) {
                        this.logger.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                    }
                    if (!DateTimeUtil.isEffectiveDate(dateAfterFormat, beginDate = periodRegion[0], endDate = periodRegion[1])) continue;
                    beginPeriodModify = schemePeriodLinkDefine.getPeriodKey();
                    break;
                }
            }
        }
        List schemePeriodLinkDefineListAfterFormat = schemePeriodLinkDefineList.stream().sorted((periodOne, periodTwo) -> PeriodUtils.comparePeriod((String)periodOne.getPeriodKey(), (String)periodTwo.getPeriodKey())).collect(Collectors.toList());
        for (SchemePeriodLinkDefine schemePeriodLinkDefineInfo : schemePeriodLinkDefineListAfterFormat) {
            if (beginPeriodModify != null && !fillingDateType.equals((Object)FillDateType.NONE) && taskDefine.getFillingDateDays() > 0) {
                if (!StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getPeriodKey()) || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getSchemeKey())) continue;
                formScheme = this.runtimeView.getFormScheme(schemePeriodLinkDefineInfo.getSchemeKey());
                IPeriodEntity iPeriodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(formScheme.getDateTime());
                if (PeriodUtils.isPeriod13((String)iPeriodEntity.getCode(), (PeriodType)iPeriodEntity.getPeriodType()) && PeriodUtils.isPeriod13Data((PeriodType)iPeriodEntity.getPeriodType(), (String)schemePeriodLinkDefineInfo.getPeriodKey())) {
                    Date[] periodRegion = new Date[]{};
                    try {
                        periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion(schemePeriodLinkDefineInfo.getPeriodKey());
                    }
                    catch (ParseException e) {
                        this.logger.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                    }
                    Date beginDate = periodRegion[0];
                    String periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskDefine.getTaskPeriodOffset(), (Date)beginDate);
                    if (PeriodUtils.comparePeriod((String)beginPeriodModify, (String)periodOfFormate) < 0) continue;
                    periodSchemeMap.put(schemePeriodLinkDefineInfo.getPeriodKey(), schemePeriodLinkDefineInfo.getSchemeKey());
                    continue;
                }
                if (PeriodUtils.comparePeriod((String)beginPeriodModify, (String)schemePeriodLinkDefineInfo.getPeriodKey()) < 0) continue;
                periodSchemeMap.put(schemePeriodLinkDefineInfo.getPeriodKey(), schemePeriodLinkDefineInfo.getSchemeKey());
                continue;
            }
            if (!StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getPeriodKey()) || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getSchemeKey())) continue;
            periodSchemeMap.put(schemePeriodLinkDefineInfo.getPeriodKey(), schemePeriodLinkDefineInfo.getSchemeKey());
        }
        return periodSchemeMap;
    }

    @Override
    public boolean canFill(String taskKey, String formSchemeKey, String period) {
        IPeriodEntity iPeriodEntity;
        Date beginDate;
        Date[] periodRegion;
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
        Date date = DateTimeUtil.getDay();
        FillDateType fillingDateType = taskDefine.getFillingDateType();
        String beginPeriodModify = null;
        Date dateAfterFormat = date;
        if (!fillingDateType.equals((Object)FillDateType.NONE) && taskDefine.getFillingDateDays() > 0) {
            PeriodModifier periodModifier;
            if (fillingDateType.equals((Object)FillDateType.NATURAL_DAY)) {
                dateAfterFormat = DateTimeUtil.getDateOfBeforeDay(date, taskDefine.getFillingDateDays() - 1);
            } else if (fillingDateType.equals((Object)FillDateType.WORK_DAY)) {
                dateAfterFormat = DateTimeUtil.getDateOfBeforeWorkDay(date, taskDefine.getFillingDateDays() - 1);
            }
            if (taskDefine.getPeriodType() != PeriodType.CUSTOM) {
                String periodOfFormate;
                beginPeriodModify = periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskDefine.getPeriodType().type(), (Date)dateAfterFormat);
                if (taskDefine.getTaskPeriodOffset() != 0) {
                    periodModifier = new PeriodModifier();
                    periodModifier.setPeriodModifier(taskDefine.getTaskPeriodOffset());
                    beginPeriodModify = periodProvider.modify(beginPeriodModify, periodModifier);
                }
            } else if (taskDefine.getTaskPeriodOffset() != 0) {
                String modify = period;
                if (taskDefine.getTaskPeriodOffset() != 0) {
                    periodModifier = new PeriodModifier();
                    periodModifier.setPeriodModifier(~taskDefine.getTaskPeriodOffset());
                    modify = periodProvider.modify(modify, periodModifier);
                }
                periodRegion = new Date[]{};
                try {
                    periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodDateRegion(modify);
                }
                catch (ParseException e) {
                    this.logger.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                }
                beginDate = periodRegion[0];
                Date endDate = periodRegion[1];
                if (DateTimeUtil.isEffectiveDate(dateAfterFormat, beginDate, endDate)) {
                    beginPeriodModify = period;
                }
            }
        }
        if (PeriodUtils.isPeriod13((String)(iPeriodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskDefine.getDateTime())).getCode(), (PeriodType)iPeriodEntity.getPeriodType()) && PeriodUtils.isPeriod13Data((PeriodType)iPeriodEntity.getPeriodType(), (String)period)) {
            periodRegion = new Date[]{};
            try {
                periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodDateRegion(period);
            }
            catch (ParseException e) {
                this.logger.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
            }
            beginDate = periodRegion[0];
            String periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskDefine.getTaskPeriodOffset(), (Date)beginDate);
            if (PeriodUtils.comparePeriod((String)beginPeriodModify, (String)periodOfFormate) >= 0) {
                return true;
            }
        } else if (PeriodUtils.comparePeriod(beginPeriodModify, (String)period) >= 0) {
            return true;
        }
        return false;
    }
}

