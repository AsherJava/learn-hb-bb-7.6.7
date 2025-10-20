/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.definition.service.impl;

import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="taskDefineServiceImpl")
public class ITaskServiceImpl
implements ITaskService {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Override
    public List<DataDimension> getDataDimension(String taskKey) {
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(task.getDataScheme());
        if (dimensions == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(dimensions);
    }

    @Override
    public List<DataDimension> getReportDimension(String taskKey) {
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(task.getDataScheme());
        if (dimensions == null) {
            return Collections.emptyList();
        }
        List list = dimensions.stream().filter(DataDimension::getReportDim).collect(Collectors.toList());
        return Collections.unmodifiableList(list);
    }

    @Override
    public String getCurrentPeriod(String taskKey) {
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
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
}

