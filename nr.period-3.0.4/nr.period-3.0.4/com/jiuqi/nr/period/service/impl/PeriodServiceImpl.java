/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.period.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.language.LanguageType;
import com.jiuqi.nr.period.common.rest.PeriodY13Obj;
import com.jiuqi.nr.period.common.utils.EntityInfo;
import com.jiuqi.nr.period.common.utils.PeriodException;
import com.jiuqi.nr.period.common.utils.PeriodSqlUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.SearchParam;
import com.jiuqi.nr.period.dao.PeriodDao;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.dao.PeriodLanguageDao;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodLanguage;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.I18nPeriodEntity;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodLanguageImpl;
import com.jiuqi.nr.period.service.PeriodService;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodServiceImpl
implements PeriodService {
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    private PeriodDataDao periodDataDao;
    @Autowired
    private PeriodLanguageDao periodLanguageDao;

    @Override
    public List<IPeriodEntity> getPeriodList() throws Exception {
        ArrayList<IPeriodEntity> list = new ArrayList<IPeriodEntity>();
        for (PeriodDefineImpl periodDefine : this.periodDao.getPeriodList()) {
            list.add(periodDefine);
        }
        return list;
    }

    @Override
    public IPeriodEntity queryPeriodByKey(String key) throws Exception {
        IPeriodEntity periodDefine = this.periodDao.queryPeriodByCode(key);
        return periodDefine;
    }

    private PeriodDataDefineImpl extensionYear(String code, int year) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        Calendar instance = Calendar.getInstance();
        instance.set(year, 0, 1);
        periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.YEAR.type(), instance.getTime()));
        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
        periodDataDefine.setYear(year);
        periodDataDefine.setDays(LocalDate.of(year, Month.DECEMBER, Month.DECEMBER.maxLength()).getDayOfYear());
        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        return periodDataDefine;
    }

    private PeriodDataDefineImpl extensionHalfyear(Date localDate) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        if (localDate.getMonth() < 6) {
            periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.HALFYEAR.type(), localDate));
        } else {
            periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.HALFYEAR.type(), localDate));
        }
        Calendar calendar = PeriodUtils.getCalendar(localDate);
        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
        periodDataDefine.setYear(calendar.get(1));
        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        periodDataDefine.setDays(PeriodUtils.getNumberOfDays(periodDataDefine));
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        return periodDataDefine;
    }

    private PeriodDataDefineImpl extensionSeason(Calendar calendar) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        switch (PeriodUtils.getMonthOfQuarter(calendar.get(2) + 1)) {
            case 1: {
                periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.SEASON.type(), calendar.getTime()));
                periodDataDefine.setQuarter(1);
                break;
            }
            case 2: {
                periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.SEASON.type(), calendar.getTime()));
                periodDataDefine.setQuarter(2);
                break;
            }
            case 3: {
                periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.SEASON.type(), calendar.getTime()));
                periodDataDefine.setQuarter(3);
                break;
            }
            case 4: {
                periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.SEASON.type(), calendar.getTime()));
                periodDataDefine.setQuarter(4);
                break;
            }
        }
        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
        periodDataDefine.setYear(calendar.get(1));
        periodDataDefine.setDays(PeriodUtils.getNumberOfDays(periodDataDefine));
        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        return periodDataDefine;
    }

    private PeriodDataDefineImpl extensionMonth(Calendar calendar) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.MONTH.type(), calendar.getTime()));
        periodDataDefine.setQuarter(calendar.get(2) / 3 + 1);
        periodDataDefine.setMonth(calendar.get(2) + 1);
        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
        periodDataDefine.setYear(calendar.get(1));
        periodDataDefine.setDays(PeriodUtils.getNumberOfDays(periodDataDefine));
        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setSimpleTitle(PeriodUtils.autoMonthSimpleTitle(PeriodType.MONTH, periodDataDefine.getMonth()));
        return periodDataDefine;
    }

    private PeriodDataDefineImpl extensionTenday(Calendar calendar) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.TENDAY.type(), calendar.getTime()));
        periodDataDefine.setQuarter(calendar.get(2) / 3 + 1);
        periodDataDefine.setMonth(calendar.get(2) + 1);
        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
        periodDataDefine.setYear(calendar.get(1));
        periodDataDefine.setDays(PeriodUtils.getNumberOfDays(periodDataDefine));
        periodDataDefine.setDay(calendar.get(5));
        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        return periodDataDefine;
    }

    private PeriodDataDefineImpl extensionWeek(Calendar calendar) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.WEEK.type(), calendar.getTime()));
        periodDataDefine.setQuarter(calendar.get(2) / 3 + 1);
        periodDataDefine.setMonth(calendar.get(2) + 1);
        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
        periodDataDefine.setYear(calendar.get(1));
        periodDataDefine.setDays(7);
        periodDataDefine.setDay(calendar.get(5));
        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        return periodDataDefine;
    }

    private PeriodDataDefineImpl extensionDay(Calendar calendar) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        periodDataDefine.setCode(PeriodUtils.getPeriodFromDate(PeriodType.DAY.type(), calendar.getTime()));
        periodDataDefine.setQuarter(calendar.get(2) / 3 + 1);
        periodDataDefine.setMonth(calendar.get(2) + 1);
        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
        periodDataDefine.setStartDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), true));
        periodDataDefine.setEndDate(PeriodUtils.getStartDateOfPeriod(periodDataDefine.getCode(), false));
        periodDataDefine.setYear(calendar.get(1));
        periodDataDefine.setDays(1);
        periodDataDefine.setDay(calendar.get(5));
        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        return periodDataDefine;
    }

    @Override
    public void extensionDefaultPeriod(IPeriodEntity periodObject, String startDate, String endDate) throws Exception {
        IPeriodEntity periodDefine = this.periodDao.queryPeriodByCode(periodObject.getCode());
        List<PeriodDataDefineImpl> dataList = this.periodDataDao.getDataListByCode(periodDefine.getCode());
        IPeriodRow maxDefine = PeriodUtils.maxRow(dataList);
        IPeriodRow minDefine = PeriodUtils.minRow(dataList);
        if (maxDefine == null || minDefine == null) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_100);
        }
        Calendar startCalendar = PeriodUtils.getCalendar(minDefine.getStartDate());
        Calendar endCalendar = PeriodUtils.getCalendar(maxDefine.getEndDate());
        Calendar extendStartCalendar = PeriodUtils.getCalendar(PeriodUtils.getStartDateOfPeriod(startDate, true));
        Calendar extendEndCalendar = PeriodUtils.getCalendar(PeriodUtils.getStartDateOfPeriod(endDate, false));
        ArrayList<PeriodDataDefineImpl> insertList = new ArrayList<PeriodDataDefineImpl>();
        switch (periodObject.getType()) {
            case YEAR: {
                int x;
                if (extendStartCalendar.before(startCalendar)) {
                    for (x = extendStartCalendar.get(1); x < startCalendar.get(1); ++x) {
                        insertList.add(this.extensionYear(periodObject.getCode(), x));
                    }
                }
                if (!extendEndCalendar.after(endCalendar)) break;
                for (x = endCalendar.get(1) + 1; x <= extendEndCalendar.get(1); ++x) {
                    insertList.add(this.extensionYear(periodObject.getCode(), x));
                }
                break;
            }
            case HALFYEAR: {
                if (extendStartCalendar.before(startCalendar)) {
                    while (extendStartCalendar.before(startCalendar)) {
                        insertList.add(this.extensionHalfyear(extendStartCalendar.getTime()));
                        extendStartCalendar.add(2, 6);
                    }
                }
                if (!extendEndCalendar.after(endCalendar)) break;
                Calendar calendarH = PeriodUtils.getCalendar(maxDefine.getStartDate());
                calendarH.add(2, 6);
                while (!calendarH.after(extendEndCalendar)) {
                    insertList.add(this.extensionHalfyear(calendarH.getTime()));
                    calendarH.add(2, 6);
                }
                break;
            }
            case SEASON: {
                if (extendStartCalendar.before(startCalendar)) {
                    while (extendStartCalendar.before(startCalendar)) {
                        insertList.add(this.extensionSeason(extendStartCalendar));
                        extendStartCalendar.add(2, 3);
                    }
                }
                if (!extendEndCalendar.after(endCalendar)) break;
                Calendar calendarS = PeriodUtils.getCalendar(maxDefine.getStartDate());
                calendarS.add(2, 3);
                while (!calendarS.after(extendEndCalendar)) {
                    insertList.add(this.extensionSeason(calendarS));
                    calendarS.add(2, 3);
                }
                break;
            }
            case MONTH: {
                if (extendStartCalendar.before(startCalendar)) {
                    while (extendStartCalendar.before(startCalendar)) {
                        insertList.add(this.extensionMonth(extendStartCalendar));
                        extendStartCalendar.add(2, 1);
                    }
                }
                if (!extendEndCalendar.after(endCalendar)) break;
                Calendar calendarM = PeriodUtils.getCalendar(maxDefine.getStartDate());
                calendarM.add(2, 1);
                while (!calendarM.after(extendEndCalendar)) {
                    insertList.add(this.extensionMonth(calendarM));
                    calendarM.add(2, 1);
                }
                break;
            }
            case TENDAY: {
                if (extendStartCalendar.before(startCalendar)) {
                    while (extendStartCalendar.before(startCalendar)) {
                        insertList.add(this.extensionTenday(extendStartCalendar));
                        if (extendStartCalendar.get(5) <= 20) {
                            extendStartCalendar.set(5, extendStartCalendar.get(5) + 10);
                            continue;
                        }
                        extendStartCalendar.add(5, extendStartCalendar.getActualMaximum(5) - extendStartCalendar.get(5) + 1);
                    }
                }
                if (!extendEndCalendar.after(endCalendar)) break;
                Calendar calendarT = PeriodUtils.getCalendar(maxDefine.getStartDate());
                if (calendarT.get(5) <= 20) {
                    calendarT.set(5, calendarT.get(5) + 10);
                } else {
                    calendarT.add(5, calendarT.getActualMaximum(5) - calendarT.get(5) + 1);
                }
                while (!calendarT.after(extendEndCalendar)) {
                    insertList.add(this.extensionTenday(calendarT));
                    if (calendarT.get(5) <= 20) {
                        calendarT.set(5, calendarT.get(5) + 10);
                        continue;
                    }
                    calendarT.add(5, calendarT.getActualMaximum(5) - calendarT.get(5) + 1);
                }
                break;
            }
            case WEEK: {
                Calendar calendar = PeriodUtils.getCalendar(extendStartCalendar.get(1), 0, 1);
                calendar.setFirstDayOfWeek(2);
                calendar.set(7, 2);
                if (calendar.get(1) != extendStartCalendar.get(1)) {
                    calendar.set(5, calendar.get(5) + 7);
                }
                if ((extendStartCalendar = calendar).before(startCalendar)) {
                    while (extendStartCalendar.before(startCalendar)) {
                        insertList.add(this.extensionWeek(extendStartCalendar));
                        extendStartCalendar.set(5, extendStartCalendar.get(5) + 7);
                    }
                }
                if (!extendEndCalendar.after(endCalendar)) break;
                Calendar calendarW = PeriodUtils.getCalendar(maxDefine.getStartDate());
                calendarW.set(5, calendarW.get(5) + 7);
                while (!calendarW.after(extendEndCalendar)) {
                    insertList.add(this.extensionWeek(calendarW));
                    calendarW.set(5, calendarW.get(5) + 7);
                }
                break;
            }
            case DAY: {
                if (extendStartCalendar.before(startCalendar)) {
                    while (extendStartCalendar.before(startCalendar)) {
                        insertList.add(this.extensionDay(extendStartCalendar));
                        extendStartCalendar.set(5, extendStartCalendar.get(5) + 1);
                    }
                }
                if (!extendEndCalendar.after(endCalendar)) break;
                Calendar calendarD = PeriodUtils.getCalendar(maxDefine.getStartDate());
                calendarD.set(5, calendarD.get(5) + 1);
                while (!calendarD.after(extendEndCalendar)) {
                    insertList.add(this.extensionDay(calendarD));
                    calendarD.set(5, calendarD.get(5) + 1);
                }
                break;
            }
            default: {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_103);
            }
        }
        this.periodDataDao.insertDataList(periodObject.getCode(), insertList);
    }

    @Override
    public void insertCustomPeriod(IPeriodEntity periodObject) throws Exception {
        if (periodObject.getType().type() != PeriodType.CUSTOM.type()) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_102);
        }
        PeriodDefineImpl periodDefine = PeriodSqlUtil.createPeriodDefine();
        periodDefine.setCode(PeriodUtils.removePerfix(periodObject.getCode().toUpperCase()));
        periodDefine.setKey(PeriodUtils.removePerfix(periodObject.getCode().toUpperCase()));
        periodDefine.setTitle(periodObject.getTitle());
        periodDefine.setType(periodObject.getType());
        periodDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDefine.setPeriodPropertyGroup(periodObject.getPeriodPropertyGroup());
        this.periodDao.insertDate(periodDefine);
        this.periodDataDao.createAndDeployTable(PeriodUtils.addPrefix(periodDefine.getCode()), periodDefine.getTitle(), "\u62a5\u8868" + periodDefine.getTitle() + "\u62a5\u65f6\u671f\u8868", periodDefine.getCode());
    }

    @Override
    public void updateCustomPeriod(IPeriodEntity periodObject) throws Exception {
        PeriodDefineImpl periodDefine = new PeriodDefineImpl();
        periodDefine.setKey(periodObject.getKey());
        periodDefine.setTitle(periodObject.getTitle());
        periodDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDefine.setPeriodPropertyGroup(periodObject.getPeriodPropertyGroup());
        this.periodDao.updateDate(periodDefine);
    }

    @Override
    public void updatePeriodDate(IPeriodEntity periodEntity) throws Exception {
        IPeriodEntity noI18n = periodEntity;
        if (periodEntity instanceof I18nPeriodEntity) {
            noI18n = ((I18nPeriodEntity)periodEntity).getiPeriodEntity();
        }
        PeriodDefineImpl periodDefine = new PeriodDefineImpl();
        periodDefine.setKey(noI18n.getKey());
        periodDefine.setTitle(noI18n.getTitle());
        periodDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDefine.setPeriodPropertyGroup(noI18n.getPeriodPropertyGroup());
        periodDefine.setUpdateTime(new Date());
        this.periodDao.updateDate(periodDefine);
    }

    @Override
    public void deleteCustomPeriod(String key) throws Exception {
        IPeriodEntity pd = this.periodDao.queryPeriodByKey(key);
        List<PeriodDataDefineImpl> datas = this.periodDataDao.getDataListByCode(pd.getCode());
        ArrayList<String> ids = new ArrayList<String>();
        for (PeriodDataDefineImpl p : datas) {
            ids.add(p.getKey());
        }
        this.periodDataDao.deleteCustomPeriodDatas(pd.getCode(), ids);
        this.periodDataDao.deteteTable(pd);
        this.periodDao.deleteData(key);
        this.periodLanguageDao.deleteByEntity(key);
    }

    @Override
    public List<IPeriodEntity> queryDefaultPeriodList() throws Exception {
        return this.periodDao.getDefaultPeriodList();
    }

    @Override
    public List<IPeriodEntity> queryCustomPeriodList() throws Exception {
        return this.periodDao.getCustomPeriodList();
    }

    @Override
    public List<IPeriodEntity> queryPeriodList(SearchParam searchParam) throws Exception {
        List<IPeriodEntity> list = new ArrayList<IPeriodEntity>();
        switch (searchParam.getSearchType()) {
            case DEFAULT: {
                for (IPeriodEntity periodEntity : this.queryDefaultPeriodList()) {
                    if (PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) continue;
                    list.add(this.setLanguage(periodEntity, searchParam.getLanguage()));
                }
                list = this.sort(list);
                break;
            }
            case CUSTOM: {
                for (IPeriodEntity periodEntity : this.queryCustomPeriodList()) {
                    list.add(this.setLanguage(periodEntity, searchParam.getLanguage()));
                }
                break;
            }
            case PERIOD13: {
                List<IPeriodEntity> iPeriodEntities = this.queryDefaultPeriodList();
                List collect = iPeriodEntities.stream().sorted((a, b) -> a.getCreateTime().compareTo(b.getCreateTime())).collect(Collectors.toList());
                for (IPeriodEntity periodEntity : collect) {
                    if (!PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) continue;
                    list.add(this.setLanguage(periodEntity, searchParam.getLanguage()));
                }
                break;
            }
            default: {
                for (IPeriodEntity periodEntity : this.getPeriodList()) {
                    list.add(this.setLanguage(periodEntity, searchParam.getLanguage()));
                }
            }
        }
        return list;
    }

    private List<IPeriodEntity> sort(List<IPeriodEntity> iPeriodEntities) {
        ArrayList<IPeriodEntity> sortList = new ArrayList<IPeriodEntity>();
        IPeriodEntity n = null;
        IPeriodEntity h = null;
        IPeriodEntity j = null;
        IPeriodEntity y = null;
        IPeriodEntity x = null;
        IPeriodEntity z = null;
        IPeriodEntity r = null;
        ArrayList<IPeriodEntity> cw = new ArrayList<IPeriodEntity>();
        ArrayList<IPeriodEntity> cus = new ArrayList<IPeriodEntity>();
        for (IPeriodEntity iPeriodEntity : iPeriodEntities) {
            switch (iPeriodEntity.getPeriodType()) {
                case YEAR: {
                    n = iPeriodEntity;
                    break;
                }
                case HALFYEAR: {
                    h = iPeriodEntity;
                    break;
                }
                case SEASON: {
                    j = iPeriodEntity;
                    break;
                }
                case MONTH: {
                    if (PeriodUtils.isPeriod13(iPeriodEntity.getCode(), iPeriodEntity.getPeriodType())) {
                        cw.add(iPeriodEntity);
                        break;
                    }
                    y = iPeriodEntity;
                    break;
                }
                case TENDAY: {
                    x = iPeriodEntity;
                    break;
                }
                case WEEK: {
                    z = iPeriodEntity;
                    break;
                }
                case DAY: {
                    r = iPeriodEntity;
                    break;
                }
                case CUSTOM: {
                    cus.add(iPeriodEntity);
                    break;
                }
            }
        }
        if (null != n) {
            sortList.add(n);
        }
        if (null != h) {
            sortList.add(h);
        }
        if (null != j) {
            sortList.add(j);
        }
        if (null != y) {
            sortList.add(y);
        }
        if (!cw.isEmpty()) {
            List collect = cw.stream().sorted((a, b) -> a.getCreateTime().compareTo(b.getCreateTime())).collect(Collectors.toList());
            sortList.addAll(collect);
        }
        if (null != x) {
            sortList.add(x);
        }
        if (null != z) {
            sortList.add(z);
        }
        if (null != r) {
            sortList.add(r);
        }
        if (!cus.isEmpty()) {
            sortList.addAll(cus);
        }
        return sortList;
    }

    @Override
    public IPeriodEntity queryPeriodByKeyLanguage(String periodKey, String language) throws Exception {
        return this.setLanguage(this.queryPeriodByKey(periodKey), language);
    }

    @Override
    public void insertCustomPeriodLanguage(IPeriodEntity periodEntity, String language) throws Exception {
        this.insertCustomPeriod(periodEntity);
        if (!this.isCN(language)) {
            PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
            periodLanguage.setEntity(periodEntity.getCode());
            periodLanguage.setCode(null);
            periodLanguage.setTitle(periodEntity.getTitle());
            periodLanguage.setType(language);
            this.periodLanguageDao.insertOrUpdate(periodLanguage);
        }
    }

    @Override
    public void updateCustomPeriodLanguage(IPeriodEntity periodEntity, String language) throws Exception {
        if (this.isCN(language)) {
            this.updateCustomPeriod(periodEntity);
        } else {
            PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
            periodLanguage.setEntity(periodEntity.getCode());
            periodLanguage.setCode(null);
            periodLanguage.setTitle(periodEntity.getTitle());
            periodLanguage.setType(language);
            this.periodLanguageDao.insertOrUpdate(periodLanguage);
        }
    }

    private boolean isCN(String language) {
        return LanguageType.Chinese.getCode().equals(language);
    }

    private IPeriodEntity setLanguage(IPeriodEntity iPeriodEntity, String language) throws Exception {
        if (!this.isCN(language)) {
            I18nPeriodEntity i18nPeriodEntity = new I18nPeriodEntity(iPeriodEntity);
            IPeriodLanguage iPeriodLanguage = this.periodLanguageDao.queryLanguageByEntityAndCode(iPeriodEntity.getCode(), null, language);
            if (null != iPeriodLanguage) {
                i18nPeriodEntity.setTitle(iPeriodLanguage.getTitle());
            }
            return i18nPeriodEntity;
        }
        return iPeriodEntity;
    }

    @Override
    public void insertPeriodY13(IPeriodEntity periodObject, String language) throws Exception {
        PeriodDefineImpl periodDefine = PeriodSqlUtil.createPeriodDefine();
        periodDefine.setCode(PeriodUtils.removePerfix(periodObject.getCode().toUpperCase()));
        periodDefine.setKey(PeriodUtils.removePerfix(periodObject.getCode().toUpperCase()));
        periodDefine.setTitle(periodObject.getTitle());
        periodDefine.setType(periodObject.getType());
        periodDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDefine.setPeriodPropertyGroup(periodObject.getPeriodPropertyGroup());
        periodDefine.setDataType(periodObject.getDataType());
        this.periodDao.insertDate(periodDefine);
        this.periodDataDao.createAndDeployTable(PeriodUtils.addPrefix(periodDefine.getCode()), periodDefine.getTitle(), "\u62a5\u8868" + periodDefine.getTitle() + "\u62a5\u65f6\u671f\u8868", periodDefine.getCode());
        if (!this.isCN(language)) {
            PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
            periodLanguage.setEntity(periodDefine.getCode());
            periodLanguage.setCode(null);
            periodLanguage.setTitle(periodDefine.getTitle());
            periodLanguage.setType(language);
            this.periodLanguageDao.insertOrUpdate(periodLanguage);
        }
    }

    @Override
    public void updatePeriodY13(IPeriodEntity periodEntity, String language) throws Exception {
        if (this.isCN(language)) {
            PeriodDefineImpl periodDefine = new PeriodDefineImpl();
            periodDefine.setKey(periodEntity.getKey());
            periodDefine.setTitle(periodEntity.getTitle());
            periodDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
            this.periodDao.updateDate(periodDefine);
        } else {
            PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
            periodLanguage.setEntity(periodEntity.getCode());
            periodLanguage.setCode(null);
            periodLanguage.setTitle(periodEntity.getTitle());
            periodLanguage.setType(language);
            this.periodLanguageDao.insertOrUpdate(periodLanguage);
        }
    }

    @Override
    public List<PeriodDataDefineImpl> createPeriodY13Data(IPeriodEntity periodEntity, PeriodY13Obj periodY13Obj, boolean isNew) throws Exception {
        ArrayList<PeriodDataDefineImpl> datas = new ArrayList<PeriodDataDefineImpl>();
        int yearStart = Integer.parseInt(periodY13Obj.getYearStart());
        int yearEnd = Integer.parseInt(periodY13Obj.getYearEnd());
        List<PeriodDataDefineImpl> yDatas = this.periodDataDao.getDataListByCode(PeriodUtils.getPeriodType(periodEntity.getPeriodType()));
        if (isNew) {
            for (int year = yearStart; year <= yearEnd; ++year) {
                this.createPeriod13DataRowByYear(year, periodEntity, periodY13Obj, datas, yDatas);
            }
        } else {
            for (int year = yearStart; year <= yearEnd; ++year) {
                this.createPeriod13DataRowByYear(year, periodEntity, periodY13Obj, datas, yDatas);
            }
            List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(periodEntity.getCode());
            Map<String, PeriodDataDefineImpl> collect = dataListByCode.stream().collect(Collectors.toMap(PeriodDataDefineImpl::getCode, e -> e));
            for (PeriodDataDefineImpl data : datas) {
                if (null == collect.get(data.getCode())) continue;
                PeriodDataDefineImpl dbData = collect.get(data.getCode());
                BeanUtils.copyProperties(dbData, data);
            }
        }
        return datas;
    }

    @Override
    public List<PeriodDataDefineImpl> extendPeriodY13Data(IPeriodEntity periodEntity, PeriodY13Obj periodY13Obj, boolean isNew) throws Exception {
        ArrayList<PeriodDataDefineImpl> datas;
        block10: {
            List<PeriodDataDefineImpl> yDatas;
            int yearEnd;
            block9: {
                datas = new ArrayList<PeriodDataDefineImpl>();
                int yearStart = Integer.parseInt(periodY13Obj.getYearStart());
                yearEnd = Integer.parseInt(periodY13Obj.getYearEnd());
                yDatas = this.periodDataDao.getDataListByCode(PeriodUtils.getPeriodType(periodEntity.getPeriodType()));
                if (!isNew) break block9;
                for (int year = yearStart; year <= yearEnd; ++year) {
                    this.createPeriod13DataRowByYear(year, periodEntity, periodY13Obj, datas, yDatas);
                }
                break block10;
            }
            List<PeriodDataDefineImpl> currDatas = this.periodDataDao.getDataListByCode(periodEntity.getCode());
            int maxDataYear = currDatas.stream().max(Comparator.comparing(PeriodDataDefineImpl::getYear)).get().getYear();
            int minDataYear = currDatas.stream().min(Comparator.comparing(PeriodDataDefineImpl::getYear)).get().getYear();
            for (int i = yearStart; i < minDataYear; ++i) {
                this.createPeriod13DataRowByYear(i, periodEntity, periodY13Obj, datas, yDatas);
            }
            for (int j = maxDataYear + 1; j <= yearEnd; ++j) {
                this.createPeriod13DataRowByYear(j, periodEntity, periodY13Obj, datas, yDatas);
            }
            List minYearCodes = currDatas.stream().filter(e -> e.getYear() == minDataYear).map(q -> q.getCode()).collect(Collectors.toList());
            boolean has0 = false;
            for (Object minYearCode : minYearCodes) {
                int i = Integer.parseInt(((String)minYearCode).substring(5));
                if (i != 0) continue;
                has0 = true;
            }
            if (!has0 && periodY13Obj.isPeriod0()) {
                for (int i = minDataYear; i <= maxDataYear; ++i) {
                    datas.add(this.createPeriod13DataRow(i, PeriodUtils.getPeriodType(periodEntity.getPeriodType()), 0));
                }
            }
            int maxQ = 12;
            for (String minYearCode : minYearCodes) {
                int i = Integer.parseInt(minYearCode.substring(5));
                maxQ = Integer.max(maxQ, i);
            }
            if (!periodY13Obj.isPeriod13() || maxQ >= periodY13Obj.getPeriodNum()) break block10;
            for (int i = minDataYear; i <= maxDataYear; ++i) {
                for (int j = maxQ + 1; j <= periodY13Obj.getPeriodNum(); ++j) {
                    datas.add(this.createPeriod13DataRow(i, PeriodUtils.getPeriodType(periodEntity.getPeriodType()), j));
                }
            }
        }
        return datas;
    }

    @Override
    public void insertPeriodY13Datas(String code, List<PeriodDataDefineImpl> datas) throws Exception {
        this.periodDataDao.insertDataList(code, datas);
    }

    @Override
    public EntityInfo createEntityInfo(String code) throws Exception {
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByCode(code);
        List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(periodEntity.getCode());
        if (dataListByCode.isEmpty()) {
            return new EntityInfo(-1, -1, -1, -1);
        }
        int maxDataYear = dataListByCode.stream().max(Comparator.comparing(PeriodDataDefineImpl::getYear)).get().getYear();
        int minDataYear = dataListByCode.stream().min(Comparator.comparing(PeriodDataDefineImpl::getYear)).get().getYear();
        if (!(PeriodType.CUSTOM.equals((Object)periodEntity.getPeriodType()) || PeriodType.DAY.equals((Object)periodEntity.getPeriodType()) || PeriodType.WEEK.equals((Object)periodEntity.getPeriodType()))) {
            List minYearCodes = dataListByCode.stream().filter(e -> e.getYear() == minDataYear).map(q -> q.getCode()).collect(Collectors.toList());
            int minPeriod = 1;
            for (String minYearCode : minYearCodes) {
                int i = Integer.parseInt(minYearCode.substring(5));
                if (i != 0) continue;
                minPeriod = 0;
            }
            int maxPeriod = 1;
            for (String minYearCode : minYearCodes) {
                int i = Integer.parseInt(minYearCode.substring(5));
                maxPeriod = Integer.max(maxPeriod, i);
            }
            return new EntityInfo(maxPeriod, minPeriod, minDataYear, maxDataYear);
        }
        return new EntityInfo(-1, -1, minDataYear, maxDataYear);
    }

    @Override
    public void updateEntityInfo(String code) throws Exception {
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByCode(code);
        if (null == periodEntity) {
            return;
        }
        PeriodDefineImpl def = (PeriodDefineImpl)periodEntity;
        EntityInfo entityInfo = this.createEntityInfo(def.getCode());
        def.setMaxFiscalMonth(entityInfo.getMaxFiscalMonth());
        def.setMinFiscalMonth(entityInfo.getMinFiscalMonth());
        def.setMaxYear(entityInfo.getMaxYear());
        def.setMinYear(entityInfo.getMinYear());
        this.periodDao.updateEntityInfo(def, entityInfo);
    }

    @Override
    public void updateDataType(IPeriodEntity periodEntity, int dataType) throws Exception {
        this.periodDao.updateDataType(periodEntity, dataType);
    }

    private void createPeriod13DataRowByYear(int year, IPeriodEntity periodEntity, PeriodY13Obj periodY13Obj, List<PeriodDataDefineImpl> datas, List<PeriodDataDefineImpl> yDatas) {
        if (periodY13Obj.isPeriod0()) {
            datas.add(this.createPeriod13DataRow(year, PeriodUtils.getPeriodType(periodEntity.getPeriodType()), 0));
        }
        int finalYear = year;
        List collect = yDatas.stream().filter(e -> e.getYear() == finalYear).collect(Collectors.toList());
        for (PeriodDataDefineImpl define : collect) {
            define.setSimpleTitle(PeriodUtils.autoMonthSimpleTitle(periodEntity.getPeriodType(), define.getMonth()));
            define.setTitle(PeriodUtils.getDateStrFromPeriod(define.getCode()));
        }
        datas.addAll(collect);
        if (periodY13Obj.isPeriod13()) {
            for (int j = 13; j <= periodY13Obj.getPeriodNum(); ++j) {
                datas.add(this.createPeriod13DataRow(year, PeriodUtils.getPeriodType(periodEntity.getPeriodType()), j));
            }
        }
    }

    private PeriodDataDefineImpl createPeriod13DataRow(int year, String type, int num) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        Calendar instance = Calendar.getInstance();
        instance.set(year, 0, 1);
        periodDataDefine.setCode(PeriodUtils.completionCode(year, type, num));
        periodDataDefine.setTitle(PeriodUtils.getDateStrFromPeriod(periodDataDefine.getCode()));
        periodDataDefine.setStartDate(PeriodUtils.completionDate(year, num, true));
        periodDataDefine.setEndDate(PeriodUtils.completionDate(year, num, false));
        periodDataDefine.setYear(year);
        if (num == 0) {
            periodDataDefine.setQuarter(1);
            periodDataDefine.setMonth(0);
        } else if (num >= 13) {
            periodDataDefine.setQuarter(4);
            periodDataDefine.setMonth(num);
        }
        periodDataDefine.setDay(0);
        periodDataDefine.setTimeKey(PeriodUtils.completionTimeKey(year, num));
        periodDataDefine.setDays(PeriodUtils.getNumberOfDays(periodDataDefine));
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setSimpleTitle(PeriodUtils.autoMonthSimpleTitle(PeriodType.MONTH, periodDataDefine.getMonth()));
        return periodDataDefine;
    }
}

