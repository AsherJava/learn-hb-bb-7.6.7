/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.holiday.manager.bean.HolidayDefine
 *  com.jiuqi.nr.holiday.manager.service.IHolidayManagerService
 *  com.jiuqi.nr.period.common.rest.PeriodDataSelectObject
 *  com.jiuqi.nr.period.common.utils.PeriodPropertyGroup
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.time.setting.de.HolidayRange
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.period.select.service.impl;

import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.holiday.manager.bean.HolidayDefine;
import com.jiuqi.nr.holiday.manager.service.IHolidayManagerService;
import com.jiuqi.nr.period.common.rest.PeriodDataSelectObject;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.common.AdjustUtil;
import com.jiuqi.nr.period.select.common.FlipPage;
import com.jiuqi.nr.period.select.common.Mode;
import com.jiuqi.nr.period.select.common.PeriodRangeUtil;
import com.jiuqi.nr.period.select.common.PeriodSelectDateUtils;
import com.jiuqi.nr.period.select.common.RunType;
import com.jiuqi.nr.period.select.common.Utils;
import com.jiuqi.nr.period.select.page.Page;
import com.jiuqi.nr.period.select.service.IRuntimePeriodModuleService;
import com.jiuqi.nr.period.select.service.data.DataContext;
import com.jiuqi.nr.period.select.service.data.RootVistor;
import com.jiuqi.nr.period.select.vo.AdjustData;
import com.jiuqi.nr.period.select.vo.CompreData;
import com.jiuqi.nr.period.select.vo.ModuleObj;
import com.jiuqi.nr.period.select.vo.PageData;
import com.jiuqi.nr.period.select.vo.ParamObj;
import com.jiuqi.nr.period.select.vo.PeriodData;
import com.jiuqi.nr.period.select.vo.PeriodRangeData;
import com.jiuqi.nr.period.select.vo.RangeData;
import com.jiuqi.nr.period.select.vo.SelectAdjustData;
import com.jiuqi.nr.period.select.vo.SelectData;
import com.jiuqi.nr.period.select.vo.SelectPeriodData;
import com.jiuqi.nr.period.select.vo.TaskData;
import com.jiuqi.nr.period.select.web.param.LoadPageData;
import com.jiuqi.nr.period.select.web.param.LoadYearData;
import com.jiuqi.nr.period.select.web.param.LoadYearsData;
import com.jiuqi.nr.period.select.web.param.ModeSelectData;
import com.jiuqi.nr.time.setting.de.HolidayRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimePeriodModuleServiceImpl
implements IRuntimePeriodModuleService {
    Logger logger = LoggerFactory.getLogger(IRuntimePeriodModuleService.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IAdjustPeriodService iAdjustPeriodService;
    @Autowired
    private IHolidayManagerService holidayManagerService;

    @Override
    public String queryPeriodType(ModuleObj moduleObj) throws RuntimeException {
        IPeriodEntity entity = null;
        switch (moduleObj.getKeyType()) {
            case TASK: {
                TaskDefine task = this.iRunTimeViewController.getTask(moduleObj.getKey());
                if (null == task) break;
                entity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(task.getDateTime());
                break;
            }
            case DATASCHEME: {
                List dataSchemeDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(moduleObj.getKey(), DimensionType.PERIOD);
                if (null == dataSchemeDimension || dataSchemeDimension.size() != 1) break;
                entity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(((DataDimension)dataSchemeDimension.get(0)).getDimKey());
                break;
            }
            case FORMSCHEME: {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(moduleObj.getKey());
                if (null == formScheme) break;
                entity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(formScheme.getDateTime());
                break;
            }
        }
        return null != entity ? entity.getCode() : null;
    }

    @Override
    public List<PeriodDataSelectObject> queryPeriodData(String period, boolean allData) throws RuntimeException {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(period);
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(period);
        PeriodPropertyGroup periodPropertyGroup = periodEntity.getPeriodPropertyGroup();
        List periodItems = periodProvider.getPeriodItems();
        if (allData) {
            return this.periodRowToPeriodDataSelectObject(periodPropertyGroup, periodItems);
        }
        if (periodEntity.getPeriodType().equals((Object)PeriodType.CUSTOM)) {
            return this.periodRowToPeriodDataSelectObject(periodPropertyGroup, periodItems);
        }
        ArrayList<IPeriodRow> titleModifyList = new ArrayList<IPeriodRow>();
        for (IPeriodRow periodItem : periodItems) {
            if (periodItem.getTitle().equals(PeriodUtils.getDateStrFromPeriod((String)periodItem.getCode()))) continue;
            titleModifyList.add(periodItem);
        }
        return titleModifyList.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
    }

    private List<PeriodDataSelectObject> periodRowToPeriodDataSelectObject(PeriodPropertyGroup periodPropertyGroup, List<IPeriodRow> periodItems) {
        List<Object> periodDataSelectObjects = new ArrayList();
        if (periodPropertyGroup == null) {
            periodDataSelectObjects = periodItems.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
        } else {
            switch (periodPropertyGroup) {
                case PERIOD_GROUP_BY_YEAR: {
                    periodDataSelectObjects = periodItems.stream().map(a -> {
                        PeriodDataSelectObject periodDataSelectObject = PeriodDataSelectObject.defineToObject((IPeriodRow)a);
                        periodDataSelectObject.setGroup(a.getYear() + PeriodPropertyGroup.PERIOD_GROUP_BY_YEAR.getGroupName());
                        return periodDataSelectObject;
                    }).collect(Collectors.toList());
                    break;
                }
                default: {
                    periodDataSelectObjects = periodItems.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
                }
            }
        }
        return periodDataSelectObjects;
    }

    @Override
    public boolean queryAdjustStatus(ModuleObj moduleObj) throws RuntimeException {
        boolean adjust = false;
        switch (moduleObj.getKeyType()) {
            case TASK: {
                TaskDefine task = this.iRunTimeViewController.getTask(moduleObj.getKey());
                List dimensionsByTask = this.iRuntimeDataSchemeService.getDataSchemeDimension(task.getDataScheme());
                adjust = dimensionsByTask.stream().anyMatch(x -> "ADJUST".equals(x.getDimKey()));
                break;
            }
            case DATASCHEME: {
                adjust = this.iRuntimeDataSchemeService.enableAdjustPeriod(moduleObj.getKey());
                break;
            }
            case FORMSCHEME: {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(moduleObj.getKey());
                TaskDefine task1 = this.iRunTimeViewController.getTask(formScheme.getTaskKey());
                adjust = this.iRuntimeDataSchemeService.enableAdjustPeriod(task1.getDataScheme());
                break;
            }
        }
        return adjust;
    }

    @Override
    public List<AdjustData> queryAdjustData(ModuleObj moduleObj) throws RuntimeException {
        List<AdjustData> adjust = new ArrayList<AdjustData>();
        switch (moduleObj.getKeyType()) {
            case TASK: {
                TaskDefine task = this.iRunTimeViewController.getTask(moduleObj.getKey());
                adjust = AdjustUtil.to(this.iAdjustPeriodService.queryAdjustPeriods(task.getDataScheme()));
                break;
            }
            case DATASCHEME: {
                adjust = AdjustUtil.to(this.iAdjustPeriodService.queryAdjustPeriods(moduleObj.getKey()));
                break;
            }
            case FORMSCHEME: {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(moduleObj.getKey());
                TaskDefine task1 = this.iRunTimeViewController.getTask(formScheme.getTaskKey());
                adjust = AdjustUtil.to(this.iAdjustPeriodService.queryAdjustPeriods(task1.getDataScheme()));
                break;
            }
        }
        return adjust;
    }

    @Override
    public CompreData queryData(ModuleObj moduleObj) throws RuntimeException {
        CompreData compreData = new CompreData();
        TaskDefine task = null;
        switch (moduleObj.getKeyType()) {
            case TASK: {
                task = this.iRunTimeViewController.getTask(moduleObj.getKey());
                List<PeriodRangeData> periodRangeData = this.queryLinksByScheme(task.getKey(), task.getDateTime());
                compreData.setSchemePeriod(periodRangeData);
                break;
            }
            case FORMSCHEME: {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(moduleObj.getKey());
                task = this.iRunTimeViewController.getTask(formScheme.getTaskKey());
                List<PeriodRangeData> periodRangeData2 = this.queryLinksByScheme(task.getKey(), task.getDateTime());
                compreData.setSchemePeriod(periodRangeData2);
                break;
            }
        }
        TaskData taskData = new TaskData();
        if (null == task) {
            List dataSchemeDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(moduleObj.getKey(), DimensionType.PERIOD);
            if (null != dataSchemeDimension && dataSchemeDimension.size() == 1) {
                String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(((DataDimension)dataSchemeDimension.get(0)).getDimKey()).getPeriodCodeRegion();
                taskData.setStartData(periodCodeRegion[0]);
                taskData.setStartData(periodCodeRegion[1]);
            }
        } else {
            String[] periodCodeRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(task.getDateTime()).getPeriodCodeRegion();
            if (StringUtils.isEmpty((String)task.getFromPeriod())) {
                taskData.setStartData(periodCodeRegion[0]);
            } else {
                taskData.setStartData(task.getFromPeriod());
            }
            if (StringUtils.isEmpty((String)task.getToPeriod())) {
                taskData.setEndData(periodCodeRegion[1]);
            } else {
                taskData.setEndData(task.getToPeriod());
            }
        }
        if (moduleObj.isFillingDue() && null != task) {
            String period = this.queryOffsetPeriod(task.getKey(), RunType.RUNTIME);
            compreData.setPeriod(period);
        }
        compreData.setTaskData(taskData);
        return compreData;
    }

    @Override
    public String queryOffsetPeriod(String taskKey, RunType runType) {
        TaskDefine task = this.iRunTimeViewController.getTask(taskKey);
        if (null == task) {
            throw new RuntimeException(NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122.getMessage());
        }
        String modifyPeriod = null;
        try {
            String endPeriod;
            String startPeriod;
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(task.getDateTime());
            IPeriodRow currPeriod = periodProvider.getCurPeriod();
            modifyPeriod = currPeriod.getCode();
            block1 : switch (task.getFillingDateType()) {
                case NATURAL_DAY: {
                    if (task.getFillingDateDays() < 0) break;
                    Calendar startCalendar = PeriodSelectDateUtils.getCurrentDate();
                    startCalendar.add(5, -task.getFillingDateDays());
                    for (IPeriodRow periodItem : periodProvider.getPeriodItems()) {
                        if (!PeriodSelectDateUtils.isEffectiveDate(startCalendar.getTime(), periodItem.getStartDate(), periodItem.getEndDate())) continue;
                        modifyPeriod = periodItem.getCode();
                        break block1;
                    }
                    break;
                }
                case WORK_DAY: {
                    if (task.getFillingDateDays() < 0) break;
                    Calendar startCalendar = PeriodSelectDateUtils.getCurrentDate();
                    startCalendar.set(13, 1);
                    this.offsetWorkdays(startCalendar, task.getFillingDateDays(), currPeriod.getYear());
                    startCalendar.set(13, 0);
                    for (IPeriodRow periodItem : periodProvider.getPeriodItems()) {
                        if (!PeriodSelectDateUtils.isEffectiveDate(startCalendar.getTime(), periodItem.getStartDate(), periodItem.getEndDate())) continue;
                        modifyPeriod = periodItem.getCode();
                        break block1;
                    }
                    break;
                }
            }
            if (0 != task.getTaskPeriodOffset()) {
                PeriodModifier periodModifier = new PeriodModifier();
                periodModifier.setPeriodModifier(task.getTaskPeriodOffset());
                modifyPeriod = periodProvider.modify(modifyPeriod, periodModifier);
            }
            if (StringUtils.isEmpty((String)(startPeriod = task.getFromPeriod()))) {
                startPeriod = periodProvider.getPeriodCodeRegion()[0];
            }
            if (StringUtils.isEmpty((String)(endPeriod = task.getToPeriod()))) {
                endPeriod = periodProvider.getPeriodCodeRegion()[1];
            }
            List periodItems = periodProvider.getPeriodItems();
            int startIndex = -1;
            int endIndex = -1;
            int periodIndex = -1;
            for (int i = 0; i < periodItems.size(); ++i) {
                if (((IPeriodRow)periodItems.get(i)).getCode().equals(startPeriod)) {
                    startIndex = i;
                }
                if (((IPeriodRow)periodItems.get(i)).getCode().equals(endPeriod)) {
                    endIndex = i;
                }
                if (!((IPeriodRow)periodItems.get(i)).getCode().equals(modifyPeriod)) continue;
                periodIndex = i;
            }
            if (periodIndex < startIndex) {
                modifyPeriod = ((IPeriodRow)periodItems.get(startIndex)).getCode();
            } else if (periodIndex > endIndex) {
                modifyPeriod = ((IPeriodRow)periodItems.get(endIndex)).getCode();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122.getMessage());
        }
        return modifyPeriod;
    }

    @Override
    public List<PeriodData> queryPeriodDataByPage(ModuleObj moduleObj) {
        List<Object> periodDatas = new ArrayList();
        try {
            String periodEntity = moduleObj.getPeriodType();
            if (null == periodEntity) {
                switch (moduleObj.getKeyType()) {
                    case TASK: {
                        periodEntity = this.iRunTimeViewController.getTask(moduleObj.getKey()).getDateTime();
                        break;
                    }
                    case DATASCHEME: {
                        List dataSchemeDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(moduleObj.getKey(), DimensionType.PERIOD);
                        periodEntity = ((DataDimension)dataSchemeDimension.get(0)).getDimKey();
                        break;
                    }
                    case FORMSCHEME: {
                        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(moduleObj.getKey());
                        periodEntity = this.iRunTimeViewController.getTask(formScheme.getTaskKey()).getDateTime();
                        break;
                    }
                }
            }
            List periodItems = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity).getPeriodItems();
            if (StringUtils.isNotEmpty((String)moduleObj.getYear())) {
                if (StringUtils.isEmpty((String)moduleObj.getMonth())) {
                    int year = Integer.parseInt(moduleObj.getYear());
                    List<IPeriodRow> dataByYear = periodItems.stream().filter(e -> e.getYear() == year).collect(Collectors.toList());
                    periodDatas = PeriodSelectDateUtils.toPeriodDatas(dataByYear);
                } else {
                    int year = Integer.parseInt(moduleObj.getYear());
                    int month = Integer.parseInt(moduleObj.getMonth());
                    List<IPeriodRow> dataByYearAndMonth = periodItems.stream().filter(e -> e.getYear() == year && e.getMonth() == month).collect(Collectors.toList());
                    periodDatas = PeriodSelectDateUtils.toPeriodDatas(dataByYearAndMonth);
                }
            } else {
                periodDatas = PeriodSelectDateUtils.toPeriodDatas(periodItems);
            }
        }
        catch (Exception e2) {
            throw new RuntimeException(NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_0122.getMessage(), e2);
        }
        return periodDatas;
    }

    @Override
    public PageData initData(ModeSelectData modeSelectData) {
        Result result = this.getResult(modeSelectData.getParamObj());
        DataContext dataContext = this.createDataContent(modeSelectData.getParamObj(), result, FlipPage.NONE, modeSelectData.getMode());
        if (dataContext.getSelectedPeriod().isEmpty()) {
            Integer finalFirstYear = result.firstYear;
            IPeriodRow iPeriodRow = result.periodProvider.getCurPeriod();
            dataContext.getSelectedPeriod().add(iPeriodRow.getCode());
            if (Mode.R.equals((Object)dataContext.getMode())) {
                dataContext.setRangeStart(iPeriodRow.getCode());
                dataContext.setRangeEnd(iPeriodRow.getCode());
            }
        }
        List dbYears = dataContext.getiPeriodRowList().stream().map(e -> e.getYear()).distinct().collect(Collectors.toList());
        Integer max = (Integer)Collections.max(dbYears);
        int endYear = result.firstYear + 11;
        if (endYear > max) {
            endYear = max;
        }
        RootVistor rootVistor = new RootVistor(dataContext);
        rootVistor.setStart(result.firstYear);
        rootVistor.setEnd(endYear);
        rootVistor.setLoadYear(result.firstYear);
        rootVistor.build();
        PageData pageData = new PageData();
        ArrayList<Page> datas = new ArrayList<Page>();
        datas.add(rootVistor.writeData());
        pageData.setPageData(datas);
        ArrayList<Integer> yearRange = new ArrayList<Integer>();
        int maxyear = result.periodProvider.getPeriodItems().stream().max(Comparator.comparing(IPeriodRow::getYear)).get().getYear();
        int minyear = result.periodProvider.getPeriodItems().stream().min(Comparator.comparing(IPeriodRow::getYear)).get().getYear();
        yearRange.add(minyear);
        yearRange.add(maxyear);
        pageData.setYearRange(yearRange);
        pageData.setSelectPeriod(dataContext.getSelectedPeriod());
        pageData.setAdjust(dataContext.getAdjust());
        pageData.setSelectedTitle(dataContext.getTitle());
        return pageData;
    }

    @Override
    public Page loadDataByYear(LoadYearData loadYearData) {
        Result result = this.getResult(loadYearData.getParamObj());
        DataContext dataContext = this.createDataContent(loadYearData.getParamObj(), result, FlipPage.NONE, loadYearData.getMode());
        RootVistor rootVistor = new RootVistor(dataContext);
        rootVistor.setStart(loadYearData.getYear());
        rootVistor.setEnd(loadYearData.getYear());
        rootVistor.setLoadYear(loadYearData.getYear());
        rootVistor.build();
        Page data = rootVistor.writeData().getData().get(0);
        return data;
    }

    @Override
    public List<Page> loadDataByYears(LoadYearsData loadYearsData) {
        Result result = this.getResult(loadYearsData.getParamObj());
        DataContext dataContext = this.createDataContent(loadYearsData.getParamObj(), result, FlipPage.NONE, loadYearsData.getMode());
        List periodItems = this.periodEngineService.getPeriodAdapter().getPeriodProvider(result.periodEntity.getCode()).getPeriodItems();
        HashSet<Integer> loadYears = new HashSet<Integer>();
        for (IPeriodRow periodItem : periodItems) {
            if (!loadYearsData.getYears().contains(periodItem.getCode())) continue;
            loadYears.add(periodItem.getYear());
        }
        ArrayList<Page> pageList = new ArrayList<Page>();
        ArrayList collect = new ArrayList(loadYears);
        for (Integer year : collect) {
            RootVistor rootVistor = new RootVistor(dataContext);
            rootVistor.setStart(year);
            rootVistor.setEnd(year);
            rootVistor.setLoadYear(year);
            rootVistor.build();
            Page data = rootVistor.writeData().getData().get(0);
            pageList.add(data);
        }
        return pageList;
    }

    @Override
    public RangeData loadDataByRange(ModeSelectData modeSelectData) {
        Result result = this.getResult(modeSelectData.getParamObj());
        DataContext dataContext = this.createDataContent(modeSelectData.getParamObj(), result, FlipPage.NONE, modeSelectData.getMode());
        List<IPeriodRow> periodItems = dataContext.getiPeriodRowList();
        ArrayList<String> selectData = new ArrayList();
        RangeData rangeData = new RangeData();
        if (modeSelectData.getRange() != null && !modeSelectData.getRange().isEmpty()) {
            String start = modeSelectData.getRange().get(0);
            String end = modeSelectData.getRange().get(modeSelectData.getRange().size() - 1);
            selectData = this.getDataByRange(periodItems, start, end);
        }
        ArrayList<Page> pageData = new ArrayList<Page>();
        HashSet<Integer> years = new HashSet<Integer>();
        for (IPeriodRow periodItem : periodItems) {
            if (!selectData.contains(periodItem.getCode())) continue;
            years.add(periodItem.getYear());
        }
        List<Object> loadYears = new ArrayList();
        for (Integer n : years) {
            if (Objects.equals(Collections.max(years), n) || Objects.equals(Collections.min(years), n)) continue;
            loadYears.add(n);
        }
        loadYears = loadYears.stream().sorted().collect(Collectors.toList());
        dataContext.setSelectedPeriod(selectData);
        for (Integer n : loadYears) {
            RootVistor rootVistor = new RootVistor(dataContext);
            rootVistor.setStart(n);
            rootVistor.setEnd(n);
            rootVistor.setLoadYear(n);
            rootVistor.build();
            Page data = rootVistor.writeData().getData().get(0);
            pageData.add(data);
        }
        rangeData.setSelectList(selectData);
        rangeData.setData(pageData);
        return rangeData;
    }

    @Override
    public List<Page> loadDataByOtherPage(LoadPageData loadPageData) {
        Result result = this.getResult(loadPageData.getParamObj());
        DataContext dataContext = this.createDataContent(loadPageData.getParamObj(), result, loadPageData.getFlip(), loadPageData.getMode());
        List dbYears = dataContext.getiPeriodRowList().stream().map(e -> e.getYear()).distinct().collect(Collectors.toList());
        Integer max = (Integer)Collections.max(dbYears);
        Integer min = (Integer)Collections.min(dbYears);
        ArrayList<Integer> years = new ArrayList<Integer>();
        switch (loadPageData.getFlip()) {
            case PRE: {
                int y;
                for (y = loadPageData.getYear() - 1 - 11; y <= loadPageData.getYear() - 1; ++y) {
                    if (y < min || y > max) continue;
                    years.add(y);
                }
                break;
            }
            case NEXT: {
                int y;
                for (y = loadPageData.getYear() + 1 + 11; y <= loadPageData.getYear() + 1 + 11 + 11; ++y) {
                    if (y < min || y > max) continue;
                    years.add(y);
                }
                break;
            }
        }
        if (years.isEmpty()) {
            return new ArrayList<Page>();
        }
        int start = (Integer)Collections.min(years);
        int end = (Integer)Collections.max(years);
        RootVistor rootVistor = new RootVistor(dataContext);
        rootVistor.setStart(start);
        rootVistor.setEnd(end);
        rootVistor.setLoadYear(start);
        rootVistor.build();
        ArrayList<Page> datas = new ArrayList<Page>();
        datas.add(rootVistor.writeData());
        return datas;
    }

    @Override
    public SelectData getSelectData(ModeSelectData loadSelectData) {
        String dataScheme = "";
        switch (Utils.getKeyType(loadSelectData.getParamObj())) {
            case DATASCHEME: {
                dataScheme = loadSelectData.getParamObj().getDataScheme();
                break;
            }
            case TASK: {
                Object task = null;
                task = RunType.DESIGNER.equals((Object)loadSelectData.getParamObj().getRunType()) ? this.iDesignTimeViewController.getTask(loadSelectData.getParamObj().getTaskId()) : this.iRunTimeViewController.getTask(loadSelectData.getParamObj().getTaskId());
                if (null == task) break;
                dataScheme = task.getDataScheme();
                break;
            }
            case FORMSCHEME: {
                DesignFormSchemeDefine formScheme;
                if (RunType.DESIGNER.equals((Object)loadSelectData.getParamObj().getRunType())) {
                    formScheme = this.iDesignTimeViewController.getFormScheme(loadSelectData.getParamObj().getFormScheme());
                    if (null == formScheme) break;
                    dataScheme = this.iDesignTimeViewController.getTask(formScheme.getTaskKey()).getDataScheme();
                    break;
                }
                formScheme = this.iRunTimeViewController.getFormScheme(loadSelectData.getParamObj().getFormScheme());
                if (null == formScheme) break;
                dataScheme = this.iRunTimeViewController.getTask(formScheme.getTaskKey()).getDataScheme();
                break;
            }
            default: {
                this.logger.error("\u65f6\u671f\u7ec4\u4ef6\u521d\u59cb\u5316\u53c2\u6570\u9519\u8bef:".concat(loadSelectData.getParamObj().toString()));
                throw new RuntimeException("\u521d\u59cb\u5316\u53c2\u6570\u9519\u8bef");
            }
        }
        if (StringUtils.isNotEmpty((String)dataScheme)) {
            List dataSchemeDimension;
            String period = "";
            if (RunType.DESIGNER.equals((Object)loadSelectData.getParamObj().getRunType())) {
                dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(dataScheme, DimensionType.PERIOD);
                if (null != dataSchemeDimension && dataSchemeDimension.size() == 1) {
                    period = ((DesignDataDimension)dataSchemeDimension.get(0)).getDimKey();
                }
            } else {
                dataSchemeDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataScheme, DimensionType.PERIOD);
                if (null != dataSchemeDimension && dataSchemeDimension.size() == 1) {
                    period = ((DataDimension)dataSchemeDimension.get(0)).getDimKey();
                }
            }
            if (StringUtils.isNotEmpty((String)period)) {
                IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(period);
                List periodItems = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getCode()).getPeriodItems();
                List<String> selectedPeriod = loadSelectData.getParamObj().getSelectedPeriod();
                SelectData selectData = new SelectData();
                switch (loadSelectData.getMode()) {
                    case S: 
                    case M: {
                        for (IPeriodRow periodItem : periodItems) {
                            if (!selectedPeriod.contains(periodItem.getCode())) continue;
                            selectData.addPeriodData(new SelectPeriodData(periodItem.getCode(), periodItem.getTitle()));
                        }
                        break;
                    }
                    case R: {
                        if (selectedPeriod.isEmpty()) break;
                        ArrayList<String> selectDataM = new ArrayList<String>();
                        String start = selectedPeriod.get(0);
                        String end = selectedPeriod.get(selectedPeriod.size() - 1);
                        List<String> dataByRange = this.getDataByRange(periodItems, start, end);
                        for (IPeriodRow periodItem : periodItems) {
                            if (!dataByRange.contains(periodItem.getCode())) continue;
                            selectDataM.add(periodItem.getCode());
                        }
                        selectData.setPeriodDataList(this.toSelectPeriodData(periodItems, selectDataM));
                    }
                }
                List adjustPeriodList = this.iAdjustPeriodService.queryAdjustPeriods(dataScheme);
                for (AdjustPeriod adjustPeriod : adjustPeriodList) {
                    if (!adjustPeriod.getCode().equals(loadSelectData.getParamObj().getAdjust()) || "0".equals(adjustPeriod.getCode())) continue;
                    selectData.setAdjustData(new SelectAdjustData(adjustPeriod.getCode(), adjustPeriod.getTitle()));
                }
                return selectData;
            }
        }
        return null;
    }

    private List<String> getDataByRange(List<IPeriodRow> datas, String start, String end) {
        ArrayList<String> selectData = new ArrayList<String>();
        boolean isadd = false;
        for (IPeriodRow periodData : datas) {
            if (start.equals(periodData.getCode())) {
                isadd = true;
            }
            if (isadd) {
                selectData.add(periodData.getCode());
            }
            if (!end.equals(periodData.getCode())) continue;
            isadd = false;
        }
        return selectData;
    }

    private List<SelectPeriodData> toSelectPeriodData(List<IPeriodRow> datas, List<String> select) {
        HashSet<String> distinct = new HashSet<String>(select);
        ArrayList<SelectPeriodData> selectData = new ArrayList<SelectPeriodData>();
        for (IPeriodRow data : datas) {
            if (!distinct.contains(data.getCode())) continue;
            selectData.add(new SelectPeriodData(data.getCode(), data.getTitle()));
        }
        return selectData;
    }

    @NotNull
    private DataContext createDataContent(ParamObj paramObj, Result result, FlipPage flipPage, Mode mode) {
        DataContext dataContext = new DataContext(this.periodEngineService, this.iAdjustPeriodService, this.iRuntimeDataSchemeService);
        dataContext.setMode(mode);
        dataContext.setDataScheme(result.dataScheme);
        dataContext.setPeriodEntity(result.periodEntity);
        this.initAdjust(dataContext, paramObj);
        dataContext.setSelectRegions(this.getSelectionRange(paramObj, result));
        RuntimePeriodModuleServiceImpl.initSelectedPeriod(paramObj, result, dataContext, flipPage);
        return dataContext;
    }

    private void initAdjust(DataContext dataContext, ParamObj paramObj) {
        List<Object> adjustPeriodList = new ArrayList();
        if (RunType.RUNTIME.equals((Object)paramObj.getRunType())) {
            adjustPeriodList = dataContext.getAdjustPeriodList();
        }
        boolean successAdjust = false;
        for (AdjustPeriod adjustPeriod : adjustPeriodList) {
            if (!adjustPeriod.getCode().equals(paramObj.getAdjust())) continue;
            successAdjust = true;
        }
        if (successAdjust) {
            dataContext.setAdjust(paramObj.getAdjust());
        } else {
            dataContext.setAdjust("0");
        }
    }

    private static void initSelectedPeriod(ParamObj paramObj, Result result, DataContext dataContext, FlipPage flipPage) {
        ArrayList<String> selectedPeriod = new ArrayList<String>();
        if (null != paramObj.getSelectedPeriod() && !paramObj.getSelectedPeriod().isEmpty()) {
            if (Mode.R.equals((Object)dataContext.getMode())) {
                List dataCodeList = dataContext.getiPeriodRowList().stream().map(e -> e.getCode()).collect(Collectors.toList());
                List filterData = paramObj.getSelectedPeriod().stream().filter(e -> dataCodeList.contains(e)).collect(Collectors.toList());
                if (!filterData.isEmpty()) {
                    dataContext.setRangeStart((String)filterData.get(0));
                    dataContext.setRangeEnd((String)filterData.get(filterData.size() - 1));
                    boolean add = false;
                    for (IPeriodRow periodData : result.periodDatas) {
                        if (dataContext.getRangeStart().equals(periodData.getCode())) {
                            add = true;
                        }
                        if (add) {
                            selectedPeriod.add(periodData.getCode());
                        }
                        if (!dataContext.getRangeEnd().equals(periodData.getCode())) continue;
                        add = false;
                    }
                }
            } else {
                for (IPeriodRow periodData : result.periodDatas) {
                    if (!paramObj.getSelectedPeriod().contains(periodData.getCode())) continue;
                    selectedPeriod.add(periodData.getCode());
                }
            }
        }
        dataContext.setSelectedPeriod(selectedPeriod);
    }

    @NotNull
    private List<String> getSelectionRange(ParamObj paramObj, Result result) {
        List<String> selectionRange = new ArrayList<String>();
        switch (Utils.getKeyType(paramObj)) {
            case DATASCHEME: {
                selectionRange = result.periodDatas.stream().map(e -> e.getCode()).collect(Collectors.toList());
                break;
            }
            case TASK: {
                String end;
                Object task = null;
                task = RunType.DESIGNER.equals((Object)paramObj.getRunType()) ? this.iDesignTimeViewController.getTask(paramObj.getTaskId()) : this.iRunTimeViewController.getTask(paramObj.getTaskId());
                String start = task.getFromPeriod();
                if (StringUtils.isEmpty((String)start)) {
                    start = result.periodProvider.getPeriodCodeRegion()[0];
                }
                if (StringUtils.isEmpty((String)(end = task.getToPeriod()))) {
                    end = result.periodProvider.getPeriodCodeRegion()[1];
                }
                boolean add = false;
                for (IPeriodRow periodData : result.periodDatas) {
                    if (start.equals(periodData.getCode())) {
                        add = true;
                    }
                    if (add) {
                        selectionRange.add(periodData.getCode());
                    }
                    if (!end.equals(periodData.getCode())) continue;
                    add = false;
                }
                break;
            }
            case FORMSCHEME: {
                if (RunType.DESIGNER.equals((Object)paramObj.getRunType())) {
                    List schemePeriodLinkDefines = this.iDesignTimeViewController.listSchemePeriodLinkByFormScheme(paramObj.getFormScheme());
                    List schemeLink = schemePeriodLinkDefines.stream().map(e -> e.getPeriodKey()).collect(Collectors.toList());
                    for (IPeriodRow periodData : result.periodDatas) {
                        if (!schemeLink.contains(periodData.getCode())) continue;
                        selectionRange.add(periodData.getCode());
                    }
                } else {
                    List schemePeriodLinkDefines = this.iRunTimeViewController.listSchemePeriodLinkByFormScheme(paramObj.getFormScheme());
                    List schemeLink = schemePeriodLinkDefines.stream().map(e -> e.getPeriodKey()).collect(Collectors.toList());
                    for (IPeriodRow periodData : result.periodDatas) {
                        if (!schemeLink.contains(periodData.getCode())) continue;
                        selectionRange.add(periodData.getCode());
                    }
                }
                break;
            }
        }
        return selectionRange;
    }

    @NotNull
    private Result getResult(ParamObj paramObj) {
        IPeriodEntity periodEntity = null;
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String dataScheme = "";
        switch (Utils.getKeyType(paramObj)) {
            case DATASCHEME: {
                List dataSchemeDimension;
                if (RunType.DESIGNER.equals((Object)paramObj.getRunType())) {
                    dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(paramObj.getDataScheme(), DimensionType.PERIOD);
                    if (null != dataSchemeDimension && dataSchemeDimension.size() == 1) {
                        periodEntity = periodAdapter.getPeriodEntity(((DesignDataDimension)dataSchemeDimension.get(0)).getDimKey());
                    }
                    dataScheme = paramObj.getDataScheme();
                    break;
                }
                dataSchemeDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(paramObj.getDataScheme(), DimensionType.PERIOD);
                if (null != dataSchemeDimension && dataSchemeDimension.size() == 1) {
                    periodEntity = periodAdapter.getPeriodEntity(((DataDimension)dataSchemeDimension.get(0)).getDimKey());
                }
                dataScheme = paramObj.getDataScheme();
                break;
            }
            case TASK: {
                Object task = null;
                task = RunType.DESIGNER.equals((Object)paramObj.getRunType()) ? this.iDesignTimeViewController.getTask(paramObj.getTaskId()) : this.iRunTimeViewController.getTask(paramObj.getTaskId());
                if (null == task) break;
                periodEntity = periodAdapter.getPeriodEntity(task.getDateTime());
                dataScheme = task.getDataScheme();
                if (null != paramObj.getSelectedPeriod() && !paramObj.getSelectedPeriod().isEmpty()) break;
                String expectPeriod = this.queryOffsetPeriod(task.getKey(), paramObj.getRunType());
                paramObj.setSelectedPeriod(Arrays.asList(expectPeriod));
                break;
            }
            case FORMSCHEME: {
                DesignFormSchemeDefine formScheme;
                if (RunType.DESIGNER.equals((Object)paramObj.getRunType())) {
                    formScheme = this.iDesignTimeViewController.getFormScheme(paramObj.getFormScheme());
                    if (null == formScheme) break;
                    DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.getTask(formScheme.getTaskKey());
                    dataScheme = designTaskDefine.getDataScheme();
                    periodEntity = periodAdapter.getPeriodEntity(designTaskDefine.getDateTime());
                    if (null != paramObj.getSelectedPeriod() && !paramObj.getSelectedPeriod().isEmpty()) break;
                    String expectPeriod = this.queryOffsetPeriod(formScheme.getTaskKey(), paramObj.getRunType());
                    paramObj.setSelectedPeriod(Arrays.asList(expectPeriod));
                    break;
                }
                formScheme = this.iRunTimeViewController.getFormScheme(paramObj.getFormScheme());
                if (null == formScheme) break;
                periodEntity = periodAdapter.getPeriodEntity(formScheme.getDateTime());
                dataScheme = this.iRunTimeViewController.getTask(formScheme.getTaskKey()).getDataScheme();
                if (null != paramObj.getSelectedPeriod() && !paramObj.getSelectedPeriod().isEmpty()) break;
                String expectPeriod = this.queryOffsetPeriod(formScheme.getTaskKey(), paramObj.getRunType());
                paramObj.setSelectedPeriod(Arrays.asList(expectPeriod));
                break;
            }
            default: {
                this.logger.error("\u65f6\u671f\u7ec4\u4ef6\u521d\u59cb\u5316\u53c2\u6570\u9519\u8bef:".concat(paramObj.toString()));
                throw new RuntimeException("\u521d\u59cb\u5316\u53c2\u6570\u9519\u8bef");
            }
        }
        if (null == periodEntity) {
            this.logger.error("\u65f6\u671f\u7ec4\u4ef6\u521d\u59cb\u5316\u53c2\u6570\u9519\u8bef\uff0c\u65e0\u6cd5\u627e\u5230\u5468\u671f\u7c7b\u578b:".concat(paramObj.toString()));
            throw new RuntimeException("\u65f6\u671f\u7ec4\u4ef6\u521d\u59cb\u5316\u53c2\u6570\u9519\u8bef\uff0c\u65e0\u6cd5\u627e\u5230\u5468\u671f\u7c7b\u578b");
        }
        IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(periodEntity.getCode());
        Integer firstYear = null;
        List periodDatas = periodProvider.getPeriodItems();
        if (null != paramObj.getSelectedPeriod() && !paramObj.getSelectedPeriod().isEmpty()) {
            ArrayList<Integer> initYears = new ArrayList<Integer>();
            for (IPeriodRow periodItem : periodDatas) {
                if (!paramObj.getSelectedPeriod().contains(periodItem.getCode())) continue;
                initYears.add(periodItem.getYear());
            }
            firstYear = initYears.isEmpty() ? Integer.valueOf(periodProvider.getCurPeriod().getYear()) : (Integer)Collections.min(initYears);
        } else {
            firstYear = periodProvider.getCurPeriod().getYear();
        }
        if (null == firstYear) {
            this.logger.error("\u65f6\u671f\u7ec4\u4ef6\u521d\u59cb\u5316\u53c2\u6570\u9519\u8bef\uff0c\u521d\u59cb\u5e74\u4efd\u5bfb\u627e\u5931\u8d25:".concat(paramObj.toString()));
            throw new RuntimeException("\u65f6\u671f\u7ec4\u4ef6\u521d\u59cb\u5316\u53c2\u6570\u9519\u8bef\uff0c\u521d\u59cb\u5e74\u4efd\u5bfb\u627e\u5931\u8d25");
        }
        Result result = new Result(periodEntity, dataScheme, periodProvider, firstYear, periodDatas);
        return result;
    }

    private Integer getParamFirstYear(ParamObj paramObj, IPeriodProvider periodProvider) {
        List collect;
        Integer year = null;
        List periodItems = periodProvider.getPeriodItems();
        switch (Utils.getKeyType(paramObj)) {
            case DATASCHEME: {
                break;
            }
            case TASK: {
                List from;
                TaskDefine task = this.iRunTimeViewController.getTask(paramObj.getTaskId());
                if (!StringUtils.isNotEmpty((String)task.getFromPeriod()) || (from = periodItems.stream().filter(e -> e.getCode().equals(task.getFromPeriod())).collect(Collectors.toList())).size() == 0) break;
                year = ((IPeriodRow)from.get(0)).getYear();
                break;
            }
            case FORMSCHEME: {
                List schemePeriodLinkDefines = this.iRunTimeViewController.listSchemePeriodLinkByFormScheme(paramObj.getFormScheme());
                List periodCodes = schemePeriodLinkDefines.stream().map(e -> e.getPeriodKey()).collect(Collectors.toList());
                List years = periodItems.stream().filter(e -> periodCodes.contains(e.getCode())).map(e -> e.getYear()).filter(e -> e != null).collect(Collectors.toList());
                if (years.isEmpty()) break;
                year = (Integer)Collections.min(years);
            }
        }
        if (null == year && !(collect = periodItems.stream().map(e -> e.getYear()).filter(e -> e != null).collect(Collectors.toList())).isEmpty()) {
            year = (Integer)Collections.min(collect);
        }
        return year;
    }

    private void offsetWorkdays(Calendar calendar, int offset, int year) {
        int sign = offset >= 0 ? -1 : 1;
        List<HolidayRange> list = this.getHolidayData(year);
        while (offset != 0) {
            calendar.add(5, sign);
            if (RuntimePeriodModuleServiceImpl.checkHoliday(calendar, list)) continue;
            --offset;
        }
    }

    private List<HolidayRange> getHolidayData(int year) {
        ArrayList<HolidayRange> list = new ArrayList<HolidayRange>();
        List holidayDefines = this.holidayManagerService.doQueryHolidayDefine(year + "");
        if (holidayDefines != null && holidayDefines.size() > 0) {
            for (HolidayDefine holidayDefine : holidayDefines) {
                list.add(new HolidayRange(holidayDefine));
            }
        }
        return list;
    }

    public static boolean checkHoliday(Calendar calendar, List<HolidayRange> list) {
        for (HolidayRange holidayRange : list) {
            boolean a = holidayRange.isHolidayRange(calendar.getTime());
            if (a) {
                return true;
            }
            boolean b = holidayRange.isWorkRange(calendar.getTime());
            if (!b) continue;
            return false;
        }
        return calendar.get(7) == 1 || calendar.get(7) == 7;
    }

    private List<PeriodRangeData> queryLinksByScheme(String task, String dataTime) throws RuntimeException {
        ArrayList<PeriodRangeData> objs = new ArrayList<PeriodRangeData>();
        List formSchemeDefines = this.iRunTimeViewController.listFormSchemeByTask(task);
        List periodItems = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dataTime).getPeriodItems();
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            List schemePeriodLinkDefines = this.iRunTimeViewController.listSchemePeriodLinkByFormScheme(formSchemeDefine.getKey());
            if (null == schemePeriodLinkDefines) continue;
            objs.addAll(new PeriodRangeUtil().unSplitPeriod(schemePeriodLinkDefines, periodItems));
        }
        return objs;
    }

    private static class Result {
        public final IPeriodEntity periodEntity;
        public final String dataScheme;
        public final IPeriodProvider periodProvider;
        public final Integer firstYear;
        public final List<IPeriodRow> periodDatas;

        public Result(IPeriodEntity periodEntity, String dataScheme, IPeriodProvider periodProvider, Integer firstYear, List<IPeriodRow> periodDatas) {
            this.periodEntity = periodEntity;
            this.dataScheme = dataScheme;
            this.periodProvider = periodProvider;
            this.firstYear = firstYear;
            this.periodDatas = periodDatas;
        }
    }
}

