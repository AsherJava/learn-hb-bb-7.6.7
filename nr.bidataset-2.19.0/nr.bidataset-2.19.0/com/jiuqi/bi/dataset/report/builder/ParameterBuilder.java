/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.ApplicationContextProvider
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.report.builder;

import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ParameterInfoVo;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.ApplicationContextProvider;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeTargetDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import java.util.Arrays;
import org.json.JSONObject;

public class ParameterBuilder {
    private static final String SUFFIX_ORG = "@ORG";
    private static final String SUFFIX_BASE = "@BASE";
    private final ReportDsParameter parameter;
    private final String taskId;
    private final String summarySchemeCode;
    private final boolean useMaxValue;
    private boolean isCalibre;

    public ParameterBuilder(ParameterInfoVo parameterInfoVo) {
        this.parameter = parameterInfoVo.getParameter();
        this.taskId = parameterInfoVo.getTaskId();
        this.summarySchemeCode = parameterInfoVo.getSummarySchemeCode();
        this.useMaxValue = parameterInfoVo.isUseMaxValue();
    }

    public ParameterModel build() {
        ParameterModel parameterModel = new ParameterModel();
        String modelName = this.parameter.getName();
        if (!this.isPeriod() && this.summarySchemeCode != null) {
            BSSchemeService schemeService = (BSSchemeService)ApplicationContextProvider.getBean(BSSchemeService.class);
            SummaryScheme summaryScheme = schemeService.findScheme(this.taskId, this.summarySchemeCode);
            SchemeTargetDim targetDim = summaryScheme.getTargetDim();
            if (TargetDimType.BASE_DATA.equals((Object)targetDim.getTargetDimType())) {
                modelName = targetDim.getDimValue();
            } else if (TargetDimType.CALIBRE.equals((Object)targetDim.getTargetDimType())) {
                modelName = targetDim.getDimValue().split("@")[0];
                this.isCalibre = true;
            }
        }
        parameterModel.setName(modelName);
        parameterModel.setTitle(this.parameter.getTitle());
        parameterModel.setMessageAlias(this.parameter.getMessageAlias());
        this.buildDataSource(parameterModel);
        this.buildDataType(parameterModel);
        this.buildDefaultValue(parameterModel);
        this.buildOther(parameterModel);
        return parameterModel;
    }

    private void buildDataSource(ParameterModel parameterModel) {
        String dsType = null;
        if (this.isPeriod()) {
            dsType = "com.jiuqi.publicparam.datasource.date";
        } else {
            dsType = "com.jiuqi.publicparam.datasource.dimension";
            if (this.isCalibre) {
                dsType = "com.jiuqi.publicparam.datasource.caliber";
            }
        }
        AbstractParameterDataSourceModel dataSourceModel = ParameterDataSourceManager.getInstance().getFactory(dsType).newInstance();
        JSONObject modelJson = new JSONObject();
        dataSourceModel.toJson(modelJson);
        if (this.isPeriod()) {
            String periodEntityId = BqlTimeDimUtils.getPeriodEntityId((String)parameterModel.getName());
            modelJson.put("entityViewId", (Object)periodEntityId);
            if (this.isCustomPeriod()) {
                modelJson.put("periodType", PeriodType.CUSTOM.type());
            } else {
                PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
                IPeriodEntity periodEntity = periodEngineService.getPeriodAdapter().getPeriodEntity(BqlTimeDimUtils.getPeriodEntityId((String)parameterModel.getName()));
                PeriodType periodType = periodEntity.getPeriodType();
                modelJson.put("periodType", this.getPeriodType().type());
                if (this.is13Period(periodType, parameterModel.getName())) {
                    modelJson.put("minFiscalMonth", periodEntity.getMinFiscalMonth());
                    modelJson.put("maxFiscalMonth", periodEntity.getMaxFiscalMonth());
                }
            }
        } else if (this.isOrg()) {
            modelJson.put("entityViewId", (Object)(parameterModel.getName() + SUFFIX_ORG));
        } else {
            modelJson.put("entityViewId", (Object)(parameterModel.getName() + SUFFIX_BASE));
            if (this.isCalibre) {
                modelJson.put("entityViewId", (Object)parameterModel.getName());
            }
        }
        dataSourceModel.fromJson(modelJson);
        parameterModel.setDatasource(dataSourceModel);
    }

    private void buildDataType(ParameterModel parameterModel) {
        parameterModel.getDatasource().setDataType(this.parameter.getDataType());
    }

    private void buildDefaultValue(ParameterModel parameterModel) {
        parameterModel.setSelectMode(this.parameter.getSelectMode());
        if (this.parameter.getSelectMode().equals((Object)ParameterSelectMode.RANGE)) {
            parameterModel.setSelectMode(ParameterSelectMode.SINGLE);
        }
        ParameterValueConfig valueConfig = new ParameterValueConfig();
        String[] values = this.parameter.getDefaultValues();
        FixedMemberParameterValue _value = null;
        if (this.useMaxValue) {
            String maxValue = this.parameter.getDefaultMaxValue();
            values = maxValue != null ? new String[]{maxValue} : new String[]{};
        }
        _value = values != null && values.length > 0 ? new FixedMemberParameterValue(Arrays.asList(values)) : new FixedMemberParameterValue();
        valueConfig.setDefaultValueMode("appoint");
        valueConfig.setDefaultValue((AbstractParameterValue)_value);
        parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
    }

    private void buildOther(ParameterModel parameterModel) {
        if (this.isPeriod() && !this.isCustomPeriod()) {
            parameterModel.setWidgetType(ParameterWidgetType.DATEPICKER.value());
        } else if (this.parameter.getSelectMode() == ParameterSelectMode.SINGLE) {
            parameterModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
        } else if (this.isOrg()) {
            parameterModel.setWidgetType(ParameterWidgetType.UNITSELECTOR.value());
        } else {
            parameterModel.setWidgetType(ParameterWidgetType.POPUP.value());
        }
    }

    private boolean isOrg() {
        return this.parameter.getName().startsWith("MD_ORG") && !this.isCalibre;
    }

    private boolean isPeriod() {
        return this.parameter.getName().startsWith("NR_PERIOD_") && !this.isCalibre;
    }

    private boolean isCustomPeriod() {
        String periodEntityId = BqlTimeDimUtils.getPeriodEntityId((String)this.parameter.getName());
        return BqlTimeDimUtils.isCustomPeriod((String)periodEntityId);
    }

    private boolean is13Period(PeriodType period, String periodName) {
        return period.equals((Object)PeriodType.MONTH) && !"NR_PERIOD_Y".equalsIgnoreCase(periodName);
    }

    private PeriodType getPeriodType() {
        return BqlTimeDimUtils.getPeriodType((String)BqlTimeDimUtils.getPeriodEntityId((String)this.parameter.getName()));
    }
}

