/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.service.impl;

import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.common.AdjustUtil;
import com.jiuqi.nr.period.select.common.PeriodRangeUtil;
import com.jiuqi.nr.period.select.common.PeriodSelectDateUtils;
import com.jiuqi.nr.period.select.service.IDesignPeriodModuleService;
import com.jiuqi.nr.period.select.vo.AdjustData;
import com.jiuqi.nr.period.select.vo.CompreData;
import com.jiuqi.nr.period.select.vo.ModuleObj;
import com.jiuqi.nr.period.select.vo.PeriodData;
import com.jiuqi.nr.period.select.vo.PeriodRangeData;
import com.jiuqi.nr.period.select.vo.TaskData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignPeriodModuleServiceImpl
implements IDesignPeriodModuleService {
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private AdjustPeriodDesignService adjustPeriodDesignService;

    @Override
    public String queryPeriodType(ModuleObj moduleObj) throws RuntimeException {
        IPeriodEntity entity = null;
        switch (moduleObj.getKeyType()) {
            case TASK: {
                DesignTaskDefine task = this.iDesignTimeViewController.getTask(moduleObj.getKey());
                if (null == task) break;
                entity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(task.getDateTime());
                break;
            }
            case DATASCHEME: {
                List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(moduleObj.getKey(), DimensionType.PERIOD);
                if (null == dataSchemeDimension || dataSchemeDimension.size() != 1) break;
                entity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(((DesignDataDimension)dataSchemeDimension.get(0)).getDimKey());
                break;
            }
            case FORMSCHEME: {
                DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.getFormScheme(moduleObj.getKey());
                if (null == formScheme) break;
                DesignTaskDefine task1 = this.iDesignTimeViewController.getTask(formScheme.getTaskKey());
                entity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(task1.getDateTime());
                break;
            }
        }
        return null != entity ? entity.getCode() : null;
    }

    @Override
    public boolean queryAdjustStatus(ModuleObj moduleObj) throws RuntimeException {
        boolean adjust = false;
        switch (moduleObj.getKeyType()) {
            case TASK: {
                DesignTaskDefine task = this.iDesignTimeViewController.getTask(moduleObj.getKey());
                List dimensionsByTask = this.iDesignDataSchemeService.getDataSchemeDimension(task.getDataScheme());
                adjust = dimensionsByTask.stream().anyMatch(x -> "ADJUST".equals(x.getDimKey()));
                break;
            }
            case DATASCHEME: {
                adjust = this.iDesignDataSchemeService.enableAdjustPeriod(moduleObj.getKey());
                break;
            }
            case FORMSCHEME: {
                DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.getFormScheme(moduleObj.getKey());
                DesignTaskDefine task1 = this.iDesignTimeViewController.getTask(formScheme.getTaskKey());
                adjust = this.iDesignDataSchemeService.enableAdjustPeriod(task1.getDataScheme());
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
                DesignTaskDefine task = this.iDesignTimeViewController.getTask(moduleObj.getKey());
                adjust = AdjustUtil.desTo(this.adjustPeriodDesignService.query(task.getDataScheme()));
                break;
            }
            case DATASCHEME: {
                adjust = AdjustUtil.desTo(this.adjustPeriodDesignService.query(moduleObj.getKey()));
                break;
            }
            case FORMSCHEME: {
                DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.getFormScheme(moduleObj.getKey());
                DesignTaskDefine task1 = this.iDesignTimeViewController.getTask(formScheme.getTaskKey());
                adjust = AdjustUtil.desTo(this.adjustPeriodDesignService.query(task1.getDataScheme()));
                break;
            }
        }
        return adjust;
    }

    @Override
    public CompreData queryData(ModuleObj moduleObj) throws RuntimeException {
        try {
            CompreData compreData = new CompreData();
            DesignTaskDefine task = null;
            switch (moduleObj.getKeyType()) {
                case TASK: {
                    task = this.iDesignTimeViewController.getTask(moduleObj.getKey());
                    List<PeriodRangeData> periodRangeByTask = this.queryLinksByScheme(task.getKey(), task.getDateTime());
                    compreData.setSchemePeriod(periodRangeByTask);
                    break;
                }
                case FORMSCHEME: {
                    DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.getFormScheme(moduleObj.getKey());
                    task = this.iDesignTimeViewController.getTask(formScheme.getTaskKey());
                    List<PeriodRangeData> periodRangeByFormScheme = this.queryLinksByScheme(task.getKey(), task.getDateTime());
                    compreData.setSchemePeriod(periodRangeByFormScheme);
                    break;
                }
            }
            TaskData taskData = new TaskData();
            if (null == task) {
                List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(moduleObj.getKey(), DimensionType.PERIOD);
                if (null != dataSchemeDimension && dataSchemeDimension.size() == 1) {
                    IPeriodProvider periodDataSchemeProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(((DesignDataDimension)dataSchemeDimension.get(0)).getDimKey());
                    String[] periodCodeRegion = periodDataSchemeProvider.getPeriodCodeRegion();
                    Date[] periodDateRegion = periodDataSchemeProvider.getPeriodDateRegion();
                    taskData.setStartData(periodCodeRegion[0]);
                    taskData.setStartTime(null != periodDateRegion[0] ? Long.valueOf(periodDateRegion[0].getTime()) : null);
                    taskData.setStartData(periodCodeRegion[1]);
                    taskData.setEndTime(null != periodDateRegion[1] ? Long.valueOf(periodDateRegion[1].getTime()) : null);
                }
            } else {
                Date[] periodDateRegionByTask;
                IPeriodProvider periodTaskProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(task.getDateTime());
                String[] periodCodeRegion = periodTaskProvider.getPeriodCodeRegion();
                Date[] periodDateRegion = periodTaskProvider.getPeriodDateRegion();
                if (StringUtils.isEmpty((String)task.getFromPeriod())) {
                    taskData.setStartData(periodCodeRegion[0]);
                    taskData.setStartTime(null != periodDateRegion[0] ? Long.valueOf(periodDateRegion[0].getTime()) : null);
                } else {
                    taskData.setStartData(task.getFromPeriod());
                    periodDateRegionByTask = periodTaskProvider.getPeriodDateRegion(task.getFromPeriod());
                    taskData.setStartTime(null != periodDateRegionByTask[0] ? Long.valueOf(periodDateRegionByTask[0].getTime()) : null);
                }
                if (StringUtils.isEmpty((String)task.getToPeriod())) {
                    taskData.setEndData(periodCodeRegion[1]);
                    taskData.setEndTime(null != periodDateRegion[1] ? Long.valueOf(periodDateRegion[1].getTime()) : null);
                } else {
                    taskData.setEndData(task.getToPeriod());
                    periodDateRegionByTask = periodTaskProvider.getPeriodDateRegion(task.getFromPeriod());
                    taskData.setEndTime(null != periodDateRegionByTask[0] ? Long.valueOf(periodDateRegionByTask[0].getTime()) : null);
                }
            }
            compreData.setTaskData(taskData);
            return compreData;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PeriodData> queryPeriodDataByPage(ModuleObj moduleObj) {
        List<Object> periodDatas = new ArrayList();
        try {
            String periodEntity = moduleObj.getPeriodType();
            if (null == periodEntity) {
                switch (moduleObj.getKeyType()) {
                    case TASK: {
                        periodEntity = this.iDesignTimeViewController.getTask(moduleObj.getKey()).getDateTime();
                        break;
                    }
                    case DATASCHEME: {
                        List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(moduleObj.getKey(), DimensionType.PERIOD);
                        periodEntity = ((DesignDataDimension)dataSchemeDimension.get(0)).getDimKey();
                        break;
                    }
                    case FORMSCHEME: {
                        DesignFormSchemeDefine formScheme = this.iDesignTimeViewController.getFormScheme(moduleObj.getKey());
                        periodEntity = this.iDesignTimeViewController.getTask(formScheme.getTaskKey()).getDateTime();
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

    private List<PeriodRangeData> queryLinksByScheme(String task, String dataTime) throws RuntimeException {
        ArrayList<PeriodRangeData> objs = new ArrayList<PeriodRangeData>();
        List formSchemeDefines = this.iDesignTimeViewController.listFormSchemeByTask(task);
        List periodItems = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dataTime).getPeriodItems();
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            List schemePeriodLinkDefines = this.iDesignTimeViewController.listSchemePeriodLinkByFormScheme(formSchemeDefine.getKey());
            if (null == schemePeriodLinkDefines) continue;
            List<PeriodRangeData> periodRangeData = new PeriodRangeUtil().unSplitPeriod(schemePeriodLinkDefines, periodItems);
            objs.addAll(periodRangeData);
        }
        return objs;
    }
}

