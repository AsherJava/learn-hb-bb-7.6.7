/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
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
package com.jiuqi.nr.summary.utils;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.vo.ParamConfig;
import com.jiuqi.nr.summary.vo.ParameterBuildInfo;
import com.jiuqi.nr.summary.vo.ParameterModelItem;
import com.jiuqi.nr.summary.vo.ParameterModelWrapper;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ParameterBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ParameterBuilder.class);
    @Autowired
    private IDesignSummarySolutionService designSumSolutionService;
    @Autowired
    private IRuntimeSummarySolutionService runtimeSumSolutionService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private SummaryParamService summaryParamService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;

    public ParameterModelWrapper buildParameter(ParameterBuildInfo buildInfo) throws SummaryCommonException {
        ParameterModel periodParamModel;
        ParameterModelWrapper paramWrapper = new ParameterModelWrapper();
        boolean useSoluKey = StringUtils.hasLength(buildInfo.getSolutionKey());
        boolean useTaskKey = StringUtils.hasLength(buildInfo.getTaskKey());
        boolean useReportKey = StringUtils.hasLength(buildInfo.getReportKey());
        if (!(useSoluKey || useTaskKey || useReportKey)) {
            logger.error("\u6784\u9020xfform\u53c2\u6570\u5931\u8d25\uff0c\u81f3\u5c11\u9700\u8981solutionKey\u6216\u8005taskKey\u6216\u8005reportKey");
            throw new SummaryCommonException(SummaryErrorEnum.XFFORM_LOAD_FAILED);
        }
        String taskKey = buildInfo.getTaskKey();
        if (!StringUtils.hasLength(taskKey)) {
            String soluKey = buildInfo.getSolutionKey();
            if (useReportKey) {
                SummaryReport sumReport = this.runtimeSumSolutionService.getSummaryReportDefine(buildInfo.getReportKey());
                soluKey = sumReport.getSolution();
            }
            SummarySolution sumSolution = this.designSumSolutionService.getSummarySolutionDefine(soluKey);
            taskKey = sumSolution.getMainTask();
        }
        TaskDefine taskDefine = this.summaryParamService.getTaskDefine(taskKey);
        ParamConfig periodParam = buildInfo.getPeriodParam();
        if (buildInfo.isWithPeriod()) {
            if (periodParam == null || !StringUtils.hasLength(periodParam.getName())) {
                periodParam = this.buildDefaultPeriodParamConfig(periodParam);
            }
            periodParamModel = this.buildPeriodParameter(taskDefine, periodParam);
            ParameterModelItem periodPramItem = new ParameterModelItem();
            periodPramItem.setName(periodParamModel.getName());
            periodPramItem.setParam(periodParamModel);
            paramWrapper.setPeriodParam(periodPramItem);
        } else {
            periodParamModel = null;
        }
        ParamConfig masterParam = buildInfo.getMasterDimParam();
        if (buildInfo.isWithMaster()) {
            if (masterParam == null || !StringUtils.hasLength(masterParam.getName())) {
                masterParam = this.buildDefaultMasterParamConfig(taskDefine, masterParam);
            }
            ParameterModel masterParamModel = this.buildMasterParameter(masterParam);
            if (periodParamModel != null) {
                masterParamModel.getValueConfig().getDepends().add(new ParameterDependMember(periodParamModel.getName(), null));
            }
            ParameterModelItem masterParamItem = new ParameterModelItem();
            masterParamItem.setName(masterParamModel.getName());
            masterParamItem.setParam(masterParamModel);
            paramWrapper.setMasterParam(masterParamItem);
        }
        List<ParamConfig> sceneDimParams = buildInfo.getSceneDimParams();
        if (buildInfo.isWithScene()) {
            if (CollectionUtils.isEmpty(sceneDimParams)) {
                sceneDimParams = this.buildDefaultSceneParamConfig(taskDefine, buildInfo);
            }
            List<ParameterModel> sceneParamModels = this.buildSceneDimParameters(taskDefine, sceneDimParams);
            ArrayList<ParameterModelItem> sceneParamItems = new ArrayList<ParameterModelItem>();
            sceneParamModels.forEach(paramModel -> {
                if (periodParamModel != null) {
                    paramModel.getValueConfig().getDepends().add(new ParameterDependMember(periodParamModel.getName(), null));
                }
                ParameterModelItem sceneParamItem = new ParameterModelItem();
                sceneParamItem.setName(paramModel.getName());
                sceneParamItem.setParam((ParameterModel)paramModel);
                sceneParamItems.add(sceneParamItem);
            });
            paramWrapper.setSceneParams(sceneParamItems);
        }
        return paramWrapper;
    }

    private ParamConfig buildDefaultPeriodParamConfig(ParamConfig periodParam) {
        ParamConfig paramConfig = new ParamConfig();
        paramConfig.setDefaultValueMode("currPeriod");
        if (periodParam != null && StringUtils.hasLength(periodParam.getDefaultValueMode())) {
            paramConfig.setDefaultValueMode(periodParam.getDefaultValueMode());
        }
        return paramConfig;
    }

    private ParamConfig buildDefaultMasterParamConfig(TaskDefine taskDefine, ParamConfig masterParam) {
        String dw = taskDefine.getDw();
        masterParam.setName(dw);
        return masterParam;
    }

    private List<ParamConfig> buildDefaultSceneParamConfig(TaskDefine taskDefine, ParameterBuildInfo buildInfo) {
        String dims = taskDefine.getDims();
        if (StringUtils.hasLength(dims)) {
            return Arrays.stream(dims.split(";")).map(dimKey -> {
                ParamConfig sceneParam = new ParamConfig();
                sceneParam.setName((String)dimKey);
                sceneParam.setSelectMode(buildInfo.getSceneSelectMode());
                sceneParam.setDefaultValueMode(buildInfo.getSceneDefaultValueMode());
                return sceneParam;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private ParameterModel buildPeriodParameter(TaskDefine taskDefine, ParamConfig periodParam) {
        ParameterModel parameterModel = new ParameterModel();
        String dateTimeKey = taskDefine.getDateTime();
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dateTimeKey);
        String periodParamName = periodParam.getName();
        if (!StringUtils.hasLength(periodParamName)) {
            periodParamName = NrPeriodConst.PREFIX_CODE + dateTimeKey;
        }
        parameterModel.setName(periodParamName);
        parameterModel.setTitle(periodEntity.getTitle());
        parameterModel.setMessageAlias("P_TIMEKEY");
        this.setPeriodDataSource(taskDefine, parameterModel);
        this.setDataType(parameterModel);
        this.setPeriodDefaultValue(parameterModel, periodParam);
        this.setPeriodOther(parameterModel);
        return parameterModel;
    }

    private ParameterModel buildMasterParameter(ParamConfig masterParam) {
        ParameterModel parameterModel = new ParameterModel();
        String dw = masterParam.getName();
        this.setName(parameterModel, dw);
        this.setTitle(parameterModel, dw);
        this.setEntityMessageAlias(parameterModel, dw);
        this.setEntityDataSource(parameterModel, dw);
        this.setDataType(parameterModel);
        this.setEntityDefaultValue(parameterModel, masterParam);
        this.setEntityOther(parameterModel, masterParam);
        return parameterModel;
    }

    private List<ParameterModel> buildSceneDimParameters(TaskDefine taskDefine, List<ParamConfig> sceneDimParams) {
        String dims;
        HashMap sceneDimMap = new HashMap();
        sceneDimParams.forEach(sceneDim -> sceneDimMap.put(sceneDim.getName(), sceneDim));
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        HashMap dimensionMap = new HashMap();
        if (!CollectionUtils.isEmpty(dimensions)) {
            dimensions.forEach(dim -> dimensionMap.put(dim.getDimKey(), dim));
        }
        if (StringUtils.hasLength(dims = taskDefine.getDims())) {
            return Arrays.stream(dims.split(";")).filter(dimKey -> !this.isMergeUnitScene(taskDefine, (String)dimKey, dimensionMap)).map(dimKey -> {
                ParamConfig paramConfig = (ParamConfig)sceneDimMap.get(dimKey);
                if (paramConfig == null) {
                    dimKey = dimKey.split("@")[0];
                    paramConfig = (ParamConfig)sceneDimMap.get(dimKey);
                }
                return this.buildSceneDimParameter((String)dimKey, paramConfig);
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private boolean isMergeUnitScene(TaskDefine taskDefine, String dimKey, Map<String, DataDimension> dimensionMap) {
        String masterDim = taskDefine.getDw();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(masterDim);
        DataDimension dataDimension = dimensionMap.get(dimKey);
        Iterator attributes = entityModel.getAttributes();
        while (attributes.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributes.next();
            if (dataDimension.getDimAttribute() == null || !dataDimension.getDimAttribute().equals(attribute.getCode()) || attribute.isMultival()) continue;
            return true;
        }
        return false;
    }

    private ParameterModel buildSceneDimParameter(String dimKey, ParamConfig paramConfig) {
        ParameterModel parameterModel = new ParameterModel();
        this.setName(parameterModel, dimKey);
        this.setTitle(parameterModel, dimKey);
        this.setEntityMessageAlias(parameterModel, dimKey);
        this.setEntityDataSource(parameterModel, dimKey);
        this.setDataType(parameterModel);
        this.setEntityDefaultValue(parameterModel, paramConfig);
        this.setEntityOther(parameterModel, paramConfig);
        return parameterModel;
    }

    private void setName(ParameterModel parameterModel, String dimKey) {
        String dimName = dimKey;
        if (dimKey.contains("@")) {
            dimName = dimKey.split("@")[0];
        }
        parameterModel.setName(dimName);
    }

    private void setTitle(ParameterModel parameterModel, String dimKey) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimKey);
        parameterModel.setTitle(entityDefine.getTitle());
    }

    private void setEntityMessageAlias(ParameterModel parameterModel, String dimKey) {
        if (dimKey.endsWith("@ORG")) {
            parameterModel.setMessageAlias("MD_ORG.CODE");
        } else if (dimKey.endsWith("@BASE")) {
            String dimCode = dimKey.split("@")[0];
            parameterModel.setMessageAlias(dimCode + "." + "OBJECTCODE");
        }
    }

    private void setPeriodDataSource(TaskDefine taskDefine, ParameterModel parameterModel) {
        String dsType = "com.jiuqi.publicparam.datasource.date";
        AbstractParameterDataSourceModel dataSourceModel = ParameterDataSourceManager.getInstance().getFactory(dsType).newInstance();
        JSONObject modelJson = new JSONObject();
        dataSourceModel.toJson(modelJson);
        String periodEntityId = BqlTimeDimUtils.getPeriodEntityId((String)parameterModel.getName());
        modelJson.put("entityViewId", (Object)periodEntityId);
        modelJson.put("periodType", taskDefine.getPeriodType().type());
        dataSourceModel.fromJson(modelJson);
        parameterModel.setDatasource(dataSourceModel);
    }

    private void setEntityDataSource(ParameterModel parameterModel, String dimKey) {
        String dsType = "com.jiuqi.publicparam.datasource.dimension";
        AbstractParameterDataSourceModel dataSourceModel = ParameterDataSourceManager.getInstance().getFactory(dsType).newInstance();
        JSONObject modelJson = new JSONObject();
        dataSourceModel.toJson(modelJson);
        modelJson.put("entityViewId", (Object)dimKey);
        if (dimKey.contains("@")) {
            modelJson.put("entityViewId", (Object)dimKey.split("@")[0]);
        }
        dataSourceModel.fromJson(modelJson);
        parameterModel.setDatasource(dataSourceModel);
    }

    private void setDataType(ParameterModel parameterModel) {
        parameterModel.getDatasource().setDataType(6);
    }

    private void setPeriodDefaultValue(ParameterModel parameterModel, ParamConfig paramConfig) {
        parameterModel.setSelectMode(paramConfig.getSelectMode());
        ParameterValueConfig valueConfig = new ParameterValueConfig();
        String defaultValueMode = paramConfig.getDefaultValueMode();
        if (!StringUtils.hasLength(defaultValueMode)) {
            defaultValueMode = "currPeriod";
        }
        valueConfig.setDefaultValueMode(defaultValueMode);
        FixedMemberParameterValue parameterValue = new FixedMemberParameterValue();
        if (!CollectionUtils.isEmpty(paramConfig.getValues())) {
            valueConfig.setDefaultValueMode("appoint");
            parameterValue.getItems().addAll(paramConfig.getValues());
        }
        valueConfig.setDefaultValue((AbstractParameterValue)parameterValue);
        parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
    }

    private void setEntityDefaultValue(ParameterModel parameterModel, ParamConfig paramConfig) {
        parameterModel.setSelectMode(paramConfig.getSelectMode());
        ParameterValueConfig valueConfig = new ParameterValueConfig();
        String defaultValueMode = paramConfig.getDefaultValueMode();
        if (!StringUtils.hasLength(defaultValueMode)) {
            defaultValueMode = "none";
        }
        valueConfig.setDefaultValueMode(defaultValueMode);
        FixedMemberParameterValue parameterValue = new FixedMemberParameterValue();
        if (!CollectionUtils.isEmpty(paramConfig.getValues())) {
            valueConfig.setDefaultValueMode("appoint");
            parameterValue.getItems().addAll(paramConfig.getValues());
        }
        valueConfig.setCandidateMode(paramConfig.getCandidateValueMode());
        List candidateValue = valueConfig.getCandidateValue();
        if (!CollectionUtils.isEmpty(paramConfig.getCandidateValue())) {
            candidateValue.addAll(paramConfig.getCandidateValue());
        }
        valueConfig.setDefaultValue((AbstractParameterValue)parameterValue);
        parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
    }

    private void setPeriodOther(ParameterModel parameterModel) {
        parameterModel.setWidgetType(ParameterWidgetType.DATEPICKER.value());
    }

    private void setEntityOther(ParameterModel parameterModel, ParamConfig paramConfig) {
        if (paramConfig.getSelectMode().equals((Object)ParameterSelectMode.SINGLE)) {
            parameterModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
        } else {
            parameterModel.setWidgetType(ParameterWidgetType.POPUP.value());
        }
    }
}

