/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDimensionHelper
implements IReportDimensionHelper {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IProcessRuntimeParamHelper processRuntimeParamHelper;

    @Override
    public String getUnitDimensionName(String taskKey) {
        DataDimension unitDimension = this.getReportUnitDimension(taskKey);
        return this.getDimensionName(unitDimension);
    }

    @Override
    public String getDimensionName(DataDimension dataDimension) {
        String dimKey = dataDimension.getDimKey();
        if (this.periodEntityAdapter.isPeriodEntity(dimKey)) {
            return this.periodEntityAdapter.getPeriodEntity(dimKey).getDimensionName();
        }
        if ("ADJUST".equals(dimKey)) {
            return "ADJUST";
        }
        return this.entityMetaService.queryEntity(dimKey).getDimensionName();
    }

    @Override
    public DataDimension getReportUnitDimension(String taskKey) {
        List dataDimension = this.taskService.getDataDimension(taskKey);
        return dataDimension.stream().filter(e -> e.getDimensionType() == DimensionType.UNIT).findFirst().orElse(null);
    }

    @Override
    public DataDimension getReportPeriodDimension(String taskKey) {
        List dataDimension = this.taskService.getDataDimension(taskKey);
        return dataDimension.stream().filter(e -> e.getDimensionType() == DimensionType.PERIOD).findFirst().orElse(null);
    }

    @Override
    public DataDimension getCurrencyDataDimension(String taskKey) {
        TaskDefine taskDefine = this.processRuntimeParamHelper.getTaskDefine(taskKey);
        List<DataDimension> allDataDimension = this.getAllDataDimension(taskDefine.getKey());
        for (DataDimension dimension : allDataDimension) {
            if (!this.isMdCurrencyDimension(taskDefine, dimension)) continue;
            return dimension;
        }
        return null;
    }

    @Override
    public DataDimension findDataDimensionByName(String taskKey, String dimensionName) {
        List<DataDimension> allDataDimension = this.getAllDataDimension(taskKey);
        return allDataDimension.stream().filter(dm -> dimensionName.equalsIgnoreCase(this.getDimensionName((DataDimension)dm))).findFirst().orElse(null);
    }

    @Override
    public List<DataDimension> getReportDimensionsExceptUnitAndPeriod(String taskKey) {
        return this.taskService.getReportDimension(taskKey);
    }

    @Override
    public List<DataDimension> getAllReportDimensions(String taskKey) {
        ArrayList<DataDimension> rptDimensionDefines = new ArrayList<DataDimension>();
        rptDimensionDefines.add(this.getReportUnitDimension(taskKey));
        rptDimensionDefines.add(this.getReportPeriodDimension(taskKey));
        rptDimensionDefines.addAll(this.taskService.getReportDimension(taskKey));
        return rptDimensionDefines;
    }

    @Override
    public List<DataDimension> getAllDataDimension(String taskKey) {
        return this.taskService.getDataDimension(taskKey);
    }

    @Override
    public boolean isCorporateDimension(TaskDefine taskDefine, DataDimension dimension) {
        IEntityDefine dwEntityDefine = this.processRuntimeParamHelper.getProcessEntityDefinition(taskDefine.getKey());
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(dwEntityDefine.getId());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimension.getDimAttribute());
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival();
    }

    @Override
    public boolean isMdCurrencyDimension(TaskDefine taskDefine, DataDimension dimension) {
        String dimensionName = this.getDimensionName(dimension);
        return "MD_CURRENCY".equalsIgnoreCase(dimensionName);
    }

    @Override
    public boolean isMdCurrencyReferEntity(String taskKey, String period) {
        DataDimension currencyDm = this.getCurrencyDataDimension(taskKey);
        if (currencyDm != null) {
            FormSchemeDefine formScheme = this.processRuntimeParamHelper.getFormScheme(taskKey, period);
            String currencyReferEntityId = this.formSchemeService.getDimAttributeByReportDim(formScheme.getKey(), currencyDm.getDimKey());
            return StringUtils.isNotEmpty((String)currencyReferEntityId);
        }
        return false;
    }
}

