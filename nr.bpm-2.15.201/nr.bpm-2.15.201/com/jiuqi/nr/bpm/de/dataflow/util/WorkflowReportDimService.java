/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.service.ITaskService
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.service.ITaskService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowReportDimService {
    @Autowired
    private ITaskService taskService;

    public List<DataDimension> getDataDimension(String taskKey) {
        ArrayList<DataDimension> dataDimensions = new ArrayList<DataDimension>();
        List dataDimension = this.taskService.getDataDimension(taskKey);
        List reportDimension = this.taskService.getReportDimension(taskKey);
        for (DataDimension dimension : dataDimension) {
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dataDimensions.add(dimension);
            }
            if (DimensionType.PERIOD != dimension.getDimensionType()) continue;
            dataDimensions.add(dimension);
        }
        dataDimensions.addAll(reportDimension);
        return dataDimensions;
    }

    public List<DataDimension> getReportDimension(String taskKey) {
        return this.taskService.getReportDimension(taskKey);
    }

    public List<DataDimension> getDataDimensionOfUnitScope(String taskKey) {
        ArrayList<DataDimension> dataDimensions = new ArrayList<DataDimension>();
        List dataDimension = this.taskService.getDataDimension(taskKey);
        for (DataDimension dimension : dataDimension) {
            if (DimensionType.UNIT_SCOPE != dimension.getDimensionType()) continue;
            dataDimensions.add(dimension);
        }
        return dataDimensions;
    }
}

