/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.helper.PeriodHelper
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimHelper
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.parameter;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.helper.PeriodHelper;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimHelper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.singlequeryimport.parameter.BuildParam;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ParameterBuilder {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private PeriodHelper periodHelper;

    public List<ParameterModel> buildParameterModel(BuildParam buildParam) throws Exception {
        ArrayList<ParameterModel> parameterModels = new ArrayList<ParameterModel>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(buildParam.getTaskKey());
        ParameterModel periodParameterModel = this.buildPeriodParameterModel(taskDefine, buildParam);
        parameterModels.add(periodParameterModel);
        if (buildParam.isShowDw()) {
            ParameterModel dwParameterModel = this.buildDwParameterModel(buildParam, taskDefine, buildParam.getReportSchemeKey(), periodParameterModel.getName());
            parameterModels.add(dwParameterModel);
        }
        List<ParameterModel> sceneParameterModel = this.buildSceneParameterModel(taskDefine, buildParam.getReportSchemeKey());
        parameterModels.addAll(sceneParameterModel);
        return parameterModels;
    }

    private ParameterModel buildPeriodParameterModel(TaskDefine taskDefine, BuildParam buildParam) throws Exception {
        ParameterModel parameterModel = new ParameterModel();
        PeriodType periodType = taskDefine.getPeriodType();
        String dateTimeKey = taskDefine.getDateTime();
        parameterModel.setName(NrPeriodConst.PREFIX_CODE + dateTimeKey);
        parameterModel.setTitle(PeriodConsts.typeToTitle((int)periodType.type()));
        AbstractParameterDataSourceModel dataSourceModel = ParameterDataSourceManager.getInstance().getFactory("com.jiuqi.publicparam.datasource.date").newInstance();
        JSONObject modelJson = new JSONObject();
        dataSourceModel.toJson(modelJson);
        String periodEntityId = BqlTimeDimUtils.getPeriodEntityId((String)parameterModel.getName());
        modelJson.put("entityViewId", (Object)periodEntityId);
        if (BqlTimeDimUtils.isCustomPeriod((String)periodEntityId)) {
            modelJson.put("periodType", PeriodType.CUSTOM.type());
        } else {
            if (taskDefine.getToPeriod() == null) {
                modelJson.put("periodStartEnd", (Object)String.format("%s-", taskDefine.getFromPeriod()));
            } else {
                modelJson.put("periodStartEnd", (Object)String.format("%s-%s", taskDefine.getFromPeriod(), taskDefine.getToPeriod()));
            }
            modelJson.put("periodType", periodType.type());
        }
        dataSourceModel.fromJson(modelJson);
        dataSourceModel.setDataType(DataType.STRING.value());
        parameterModel.setDatasource(dataSourceModel);
        parameterModel.setSelectMode(ParameterSelectMode.SINGLE);
        ParameterValueConfig valueConfig = new ParameterValueConfig();
        valueConfig.setDefaultValueMode("appoint");
        String currentPeriod = buildParam.getCurrPeriod();
        IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dateTimeKey);
        TimeDimHelper helper = new TimeDimHelper();
        if (!StringUtils.hasLength(currentPeriod)) {
            currentPeriod = this.periodHelper.getCurrentPeriod(taskDefine, periodEntity);
        }
        FixedMemberParameterValue defaultValue = new FixedMemberParameterValue(Collections.singletonList(helper.periodToTimeKey(currentPeriod)));
        valueConfig.setDefaultValue((AbstractParameterValue)defaultValue);
        parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
        parameterModel.setWidgetType(ParameterWidgetType.DATEPICKER.value());
        parameterModel.setMessageAlias("");
        return parameterModel;
    }

    private ParameterModel buildDwParameterModel(BuildParam buildParam, TaskDefine taskDefine, String reportScheme, String periodParameterName) {
        ParameterModel parameterModel = new ParameterModel();
        String dw = taskDefine.getDw();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(dw);
        parameterModel.setName(entityDefine.getCode());
        parameterModel.setTitle(entityDefine.getTitle());
        AbstractParameterDataSourceModel dataSourceModel = ParameterDataSourceManager.getInstance().getFactory("com.jiuqi.publicparam.datasource.dimension").newInstance();
        JSONObject modelJson = new JSONObject();
        dataSourceModel.toJson(modelJson);
        modelJson.put("entityViewId", (Object)dw);
        modelJson.put("formSchemeKey", (Object)reportScheme);
        dataSourceModel.fromJson(modelJson);
        dataSourceModel.setDataType(DataType.STRING.value());
        parameterModel.setDatasource(dataSourceModel);
        parameterModel.setSelectMode(ParameterSelectMode.MUTIPLE);
        ParameterValueConfig valueConfig = new ParameterValueConfig();
        if (CollectionUtils.isEmpty(buildParam.getCurrUnits())) {
            valueConfig.setDefaultValueMode("none");
        } else {
            FixedMemberParameterValue parameterValue = new FixedMemberParameterValue(buildParam.getCurrUnits());
            valueConfig.setDefaultValue((AbstractParameterValue)parameterValue);
        }
        valueConfig.getDepends().add(new ParameterDependMember(periodParameterName, null));
        parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
        parameterModel.setWidgetType(ParameterWidgetType.UNITSELECTOR.value());
        parameterModel.setMessageAlias("");
        return parameterModel;
    }

    private List<ParameterModel> buildSceneParameterModel(TaskDefine taskDefine, String reportScheme) {
        ArrayList<ParameterModel> parameterModels = new ArrayList<ParameterModel>();
        String dims = taskDefine.getDims();
        if (StringUtils.hasLength(dims)) {
            String[] dimKeyArr;
            HashMap<String, DataDimension> dataDimensionMap = new HashMap();
            List dimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
            if (!CollectionUtils.isEmpty(dimensions)) {
                dataDimensionMap = dimensions.stream().collect(Collectors.toMap(DataDimension::getDimKey, Function.identity()));
            }
            for (String dimKey : dimKeyArr = dims.split(";")) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimKey);
                if (this.isMergeUnitScene(taskDefine, entityDefine, dataDimensionMap)) continue;
                ParameterModel parameterModel = new ParameterModel();
                parameterModel.setName(entityDefine.getCode());
                parameterModel.setTitle(entityDefine.getTitle());
                AbstractParameterDataSourceModel dataSourceModel = ParameterDataSourceManager.getInstance().getFactory("com.jiuqi.publicparam.datasource.dimension").newInstance();
                JSONObject modelJson = new JSONObject();
                dataSourceModel.toJson(modelJson);
                modelJson.put("entityViewId", (Object)dimKey);
                modelJson.put("formSchemeKey", (Object)reportScheme);
                dataSourceModel.fromJson(modelJson);
                dataSourceModel.setDataType(DataType.STRING.value());
                parameterModel.setDatasource(dataSourceModel);
                parameterModel.setSelectMode(ParameterSelectMode.SINGLE);
                ParameterValueConfig valueConfig = new ParameterValueConfig();
                valueConfig.setDefaultValueMode("first");
                parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
                parameterModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
                parameterModel.setMessageAlias("");
                if (!this.isStandardCurrency(taskDefine, entityDefine)) continue;
                parameterModel.setHidden(true);
            }
        }
        return parameterModels;
    }

    private void buildDataSource_period(ParameterModel parameterModel, PeriodType periodType) {
        AbstractParameterDataSourceModel dataSourceModel = ParameterDataSourceManager.getInstance().getFactory("com.jiuqi.publicparam.datasource.date").newInstance();
        JSONObject modelJson = new JSONObject();
        dataSourceModel.toJson(modelJson);
        String periodEntityId = BqlTimeDimUtils.getPeriodEntityId((String)parameterModel.getName());
        modelJson.put("entityViewId", (Object)periodEntityId);
        if (BqlTimeDimUtils.isCustomPeriod((String)periodEntityId)) {
            modelJson.put("periodType", PeriodType.CUSTOM.type());
        } else {
            modelJson.put("periodType", periodType.type());
        }
        dataSourceModel.fromJson(modelJson);
        dataSourceModel.setDataType(DataType.STRING.value());
        parameterModel.setDatasource(dataSourceModel);
    }

    private boolean isMergeUnitScene(TaskDefine taskDefine, IEntityDefine entityDefine, Map<String, DataDimension> dimensionMap) {
        String masterDim = taskDefine.getDw();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(masterDim);
        DataDimension dataDimension = dimensionMap.get(entityDefine.getId());
        Iterator attributes = entityModel.getAttributes();
        while (attributes.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributes.next();
            String dimAttribute = dataDimension.getDimAttribute();
            if (!StringUtils.hasLength(dimAttribute) || !dimAttribute.equals(attribute.getCode()) || attribute.isMultival()) continue;
            return true;
        }
        return false;
    }

    private boolean isStandardCurrency(TaskDefine taskDefine, IEntityDefine entityDefine) {
        if ("MD_CURRENCY".equals(entityDefine.getCode())) {
            String dw = taskDefine.getDw();
            IEntityModel entityModel = this.entityMetaService.getEntityModel(dw);
            Iterator attributes = entityModel.getAttributes();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                if (!"CURRENCYID".equals(attribute.getCode())) continue;
                return true;
            }
        }
        return false;
    }
}

