/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.IDataRowFilter
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.DataBusinessType
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.util;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.publicparam.datasource.caliberdim.NrCaliberDimDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.timegranularity.TimegranularityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.timegranularity.TimegranularityDataSourceProvider;
import com.jiuqi.bi.publicparam.datasource.timegranularity.filter.MemoryRowFilter;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamUtils {
    public static final Logger log = LoggerFactory.getLogger(ParamUtils.class);

    public static String getPeriod(ParameterDataSourceContext context) {
        String period = null;
        ParameterCalculator calculator = context.getCalculator();
        boolean hasTimegranularityDataSource = false;
        for (ParameterModel parameterModel : calculator.getParameterModels()) {
            try {
                AbstractParameterDataSourceModel datasource = parameterModel.getDatasource();
                if (datasource == null) continue;
                if (datasource instanceof NrPeriodDataSourceModel) {
                    ParameterResultset value = calculator.getValue(parameterModel.getName());
                    IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
                    List periods = value.getValueAsString(valueFormat);
                    NrPeriodDataSourceModel nrPeriodDataSourceModel = (NrPeriodDataSourceModel)datasource;
                    for (String p : periods) {
                        if (p == null) continue;
                        p = TimeDimUtils.timeKeyToPeriod((String)p, (PeriodType)PeriodType.fromType((int)nrPeriodDataSourceModel.getPeriodType()));
                        if (period != null && period.compareTo(p) >= 0) continue;
                        period = p;
                    }
                    continue;
                }
                if (!(datasource instanceof TimegranularityDataSourceModel)) continue;
                hasTimegranularityDataSource = true;
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        if (period == null && hasTimegranularityDataSource) {
            try {
                TimegranularityDataSourceModel dayDataSourceModel = new TimegranularityDataSourceModel();
                dayDataSourceModel.setTimegranularity(TimeGranularity.DAY.value());
                JSONObject json = new JSONObject();
                dayDataSourceModel.toJson(json);
                dayDataSourceModel.fromJson(json);
                MemoryRowFilter filter = dayDataSourceModel.getRowFilterByDepends(context, context.getModel().getValueConfig().getDepends());
                PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanProvider.getBean(PeriodEngineService.class);
                TimegranularityDataSourceProvider provider = new TimegranularityDataSourceProvider(dayDataSourceModel, periodEngineService);
                String timeKey = null;
                MemoryDataSet<TimeDimField> periodDataSet = provider.getPeriodDataSet("R");
                DataSet filteredDataSet = periodDataSet.filter((IDataRowFilter)filter);
                for (DataRow row : filteredDataSet) {
                    String value = row.getString(0);
                    if (timeKey != null && timeKey.compareTo(value) >= 0) continue;
                    timeKey = value;
                }
                if (timeKey != null) {
                    period = TimeDimUtils.timeKeyToPeriod(timeKey, (PeriodType)PeriodType.DAY);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return period;
    }

    public static String getPeriodEntityId(ParameterDataSourceContext context) {
        String periodEntityId = null;
        ParameterCalculator calculator = context.getCalculator();
        for (ParameterModel parameterModel : calculator.getParameterModels()) {
            try {
                NrPeriodDataSourceModel nrPeriodDataSourceModel;
                AbstractParameterDataSourceModel datasource = parameterModel.getDatasource();
                if (datasource == null || !(datasource instanceof NrPeriodDataSourceModel) || (periodEntityId = (nrPeriodDataSourceModel = (NrPeriodDataSourceModel)datasource).getEntityViewId()) == null || !periodEntityId.startsWith("NR_PERIOD_")) continue;
                periodEntityId = periodEntityId.substring("NR_PERIOD_".length());
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return periodEntityId;
    }

    public static ParameterModel createPeriodParamByDimension(TaskDefine taskDefine, String paramName, PeriodEngineService periodEngineService, DataDimension dim, boolean defaultCurrentPeriod) {
        ParameterModel parameterModel = new ParameterModel();
        DimensionType dimensionType = dim.getDimensionType();
        ParameterValueConfig valueConfig = new ParameterValueConfig();
        if (dimensionType == DimensionType.PERIOD) {
            IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dim.getDimKey());
            parameterModel.setGuid(periodEntity.getKey());
            parameterModel.setName(paramName);
            parameterModel.setTitle("\u65f6\u671f");
            if (defaultCurrentPeriod) {
                valueConfig.setDefaultValueMode("expr");
                ExpressionParameterValue expValue = new ExpressionParameterValue("-0N");
                valueConfig.setDefaultValue((AbstractParameterValue)expValue);
            } else {
                valueConfig.setDefaultValueMode("none");
            }
            parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
            NrPeriodDataSourceModel dataSourceModel = new NrPeriodDataSourceModel();
            dataSourceModel.setEntityViewId(periodEntity.getKey());
            dataSourceModel.setDataType(6);
            dataSourceModel.setPeriodType(periodEntity.getPeriodType().type());
            TimeGranularity timeGranularity = TimeDimUtils.periodTypeToTimeGranularity((PeriodType)periodEntity.getPeriodType());
            if (timeGranularity != null) {
                dataSourceModel.setTimegranularity(timeGranularity.value());
                if (timeGranularity == TimeGranularity.MONTH && !"Y".equals(periodEntity.getKey())) {
                    dataSourceModel.setMinFiscalMonth(periodEntity.getMinFiscalMonth());
                    dataSourceModel.setMaxFiscalMonth(periodEntity.getMaxFiscalMonth());
                }
            }
            dataSourceModel.setTimekey(true);
            dataSourceModel.setRemote(false);
            dataSourceModel.setBusinessType(DataBusinessType.TIME_DIM);
            parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
            ParamUtils.setPeriodStartEnd(taskDefine, dataSourceModel);
        }
        parameterModel.setSelectMode(ParameterSelectMode.MUTIPLE);
        return parameterModel;
    }

    public static void setPeriodStartEnd(TaskDefine taskDefine, NrPeriodDataSourceModel dataSourceModel) {
        String fromPeriod = taskDefine.getFromPeriod();
        String toPeriod = taskDefine.getToPeriod();
        StringBuilder periodStartEnd = new StringBuilder();
        if (StringUtils.isNotEmpty((String)fromPeriod)) {
            periodStartEnd.append(fromPeriod);
        }
        if (StringUtils.isNotEmpty((String)toPeriod)) {
            if (periodStartEnd.length() > 0) {
                periodStartEnd.append("-");
            }
            periodStartEnd.append(toPeriod);
        }
        if (periodStartEnd.length() > 0) {
            dataSourceModel.setPeriodStartEnd(periodStartEnd.toString());
        }
    }

    public static ParameterModel createEntityParamByDimension(TaskDefine taskDefine, String paramName, IEntityMetaService entityMetaService, DataDimension dim) {
        ParameterModel parameterModel = new ParameterModel();
        ParameterValueConfig valueConfig = new ParameterValueConfig();
        IEntityDefine entityDefine = entityMetaService.queryEntity(dim.getDimKey());
        parameterModel.setGuid(entityDefine.getId());
        parameterModel.setName(entityDefine.getDimensionName());
        parameterModel.setTitle(entityDefine.getTitle());
        valueConfig.setDefaultValueMode("first");
        parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
        NrEntityDataSourceModel dataSourceModel = new NrEntityDataSourceModel();
        dataSourceModel.setEntityViewId(entityDefine.getId());
        dataSourceModel.setDataType(6);
        dataSourceModel.setBusinessType(DataBusinessType.GENERAL_DIM);
        parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
        parameterModel.setSelectMode(ParameterSelectMode.MUTIPLE);
        return parameterModel;
    }

    public static ParameterModel createUnitParamByDimension(ReportDSModel dsModel, String paramName, IEntityMetaService entityMetaService, DataDimension dim) {
        ParameterModel parameterModel = new ParameterModel();
        ParameterValueConfig valueConfig = new ParameterValueConfig();
        if (dsModel.getGaterDimType() == 2) {
            parameterModel.setGuid(dsModel.getGaterDimName());
            parameterModel.setName(paramName);
            parameterModel.setTitle(dsModel.getUnitEntityDefnie().getTitle());
            valueConfig.setDefaultValueMode("first");
            parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
            NrCaliberDimDataSourceModel dataSourceModel = new NrCaliberDimDataSourceModel();
            dataSourceModel.setEntityViewId(dsModel.getGaterDimName());
            dataSourceModel.setDataType(6);
            dataSourceModel.setBusinessType(DataBusinessType.UNIT_DIM);
            parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
        } else {
            String entityId = dsModel.getGaterDimName() == null ? dim.getDimKey() : dsModel.getGaterDimName();
            IEntityDefine entityDefine = entityMetaService.queryEntity(entityId);
            parameterModel.setGuid(entityDefine.getId());
            parameterModel.setName(paramName);
            parameterModel.setTitle(entityDefine.getTitle());
            valueConfig.setDefaultValueMode("first");
            parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
            NrEntityDataSourceModel dataSourceModel = new NrEntityDataSourceModel();
            dataSourceModel.setEntityViewId(entityDefine.getId());
            dataSourceModel.setDataType(6);
            dataSourceModel.setBusinessType(DataBusinessType.UNIT_DIM);
            parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
        }
        parameterModel.setSelectMode(ParameterSelectMode.MUTIPLE);
        return parameterModel;
    }
}

