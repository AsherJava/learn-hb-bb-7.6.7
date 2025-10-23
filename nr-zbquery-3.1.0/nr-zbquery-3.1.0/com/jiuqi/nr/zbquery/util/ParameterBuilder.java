/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.ApplicationContextProvider
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.bql.interpret.BiAdapTransResult
 *  com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser
 *  com.jiuqi.nr.bql.interpret.BiAdaptParam
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.DataBusinessType
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterRangeValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.ApplicationContextProvider;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.interpret.BiAdapTransResult;
import com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser;
import com.jiuqi.nr.bql.interpret.BiAdaptParam;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionType;
import com.jiuqi.nr.zbquery.model.DefaultValueMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.model.ZBQueryParameterValueConfig;
import com.jiuqi.nr.zbquery.rest.vo.ParameterInfoVO;
import com.jiuqi.nr.zbquery.util.DimensionValueUtils;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterRangeValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

public class ParameterBuilder {
    private InnerParameterBuilder innerBuilder;
    private BiAdaptFormulaParser biAdaptFormulaParser = (BiAdaptFormulaParser)SpringBeanUtils.getBean(BiAdaptFormulaParser.class);

    private ParameterBuilder(ParameterInfoVO paramInfo) {
        if (paramInfo.getQueryDimension() != null) {
            this.innerBuilder = new DimensionParameterBuilder(paramInfo);
        } else if (paramInfo.getQueryObject().getType() == QueryObjectType.ZB) {
            this.innerBuilder = new ZBParameterBuilder(paramInfo);
        } else {
            throw new RuntimeException(ZBQueryI18nUtils.getMessage("zbquery.exception.onlySupportDimZBParam", new Object[0]));
        }
    }

    public static ParameterModel build(ParameterInfoVO paramInfo) throws Exception {
        return new ParameterBuilder(paramInfo).build();
    }

    public static List<ParameterModel> build(List<ParameterInfoVO> paramInfos) throws Exception {
        ParameterInfoVO periodParam = null;
        ArrayList<ParameterInfoVO> periodChildParam = new ArrayList<ParameterInfoVO>();
        ParameterInfoVO orgParam = null;
        for (ParameterInfoVO paramInfo : paramInfos) {
            QueryDimension queryDim = paramInfo.getQueryDimension();
            if (queryDim == null) continue;
            if (periodParam == null && queryDim.getDimensionType() == QueryDimensionType.PERIOD) {
                periodParam = paramInfo;
                continue;
            }
            if (orgParam == null && queryDim.getDimensionType() == QueryDimensionType.MASTER && queryDim.isOrgDimension()) {
                orgParam = paramInfo;
                continue;
            }
            if (!DimensionValueUtils.isPeriodChildDim(queryDim)) continue;
            periodChildParam.add(paramInfo);
        }
        ArrayList<ParameterModel> paramModels = new ArrayList<ParameterModel>();
        for (ParameterInfoVO paramInfo : paramInfos) {
            ParameterModel paramModel = ParameterBuilder.build(paramInfo);
            paramModel.setHidden(!paramInfo.getConditionField().isVisible());
            QueryDimension queryDim = paramInfo.getQueryDimension();
            if (queryDim != null && queryDim.getDimensionType() != QueryDimensionType.PERIOD && !paramInfo.getConditionField().isCalibreCondition()) {
                boolean enableVersion = queryDim.isEnableVersion();
                if (!enableVersion && queryDim.isOrgDimension()) {
                    if (queryDim.getDimensionType() == QueryDimensionType.MASTER) {
                        enableVersion = true;
                    } else {
                        IEntityMetaService entityMetaService = (IEntityMetaService)ApplicationContextProvider.getBean(IEntityMetaService.class);
                        IEntityDefine entity = entityMetaService.queryEntityByCode(queryDim.getName());
                        if (entity != null) {
                            boolean bl = enableVersion = entity.getVersion() == 1;
                        }
                    }
                }
                if (enableVersion) {
                    if (periodParam != null) {
                        paramModel.getValueConfig().getDepends().add(new ParameterDependMember(periodParam.getConditionField().getFullName(), null));
                    }
                    if (!CollectionUtils.isEmpty(periodChildParam)) {
                        periodChildParam.forEach(childParamInfo -> paramModel.getValueConfig().getDepends().add(new ParameterDependMember(childParamInfo.getConditionField().getFullName(), null)));
                    }
                }
                if (orgParam != null && !queryDim.isOrgDimension() && queryDim.getIsolation() != 0) {
                    paramModel.getValueConfig().getDepends().add(new ParameterDependMember(orgParam.getConditionField().getFullName(), null));
                }
                paramModel.setOnlyLeafSelectable(paramInfo.getConditionField().isOnlyLeafSelectable());
            }
            paramModels.add(paramModel);
        }
        return paramModels;
    }

    public static List<ParameterModel> build(ZBQueryModel zbQueryModel) throws Exception {
        ArrayList<ParameterInfoVO> paramInfos = new ArrayList<ParameterInfoVO>();
        QueryModelFinder finder = new QueryModelFinder(zbQueryModel);
        for (ConditionField condition : zbQueryModel.getConditions()) {
            ParameterInfoVO paramInfo = new ParameterInfoVO();
            paramInfo.setConditionField(condition);
            if (condition.getObjectType() == QueryObjectType.DIMENSION) {
                paramInfo.setQueryDimension(finder.getQueryDimension(condition.getFullName()));
                paramInfo.setQueryObject(paramInfo.getQueryDimension());
            } else {
                paramInfo.setQueryObject(finder.getQueryObject(condition.getFullName()));
            }
            paramInfos.add(paramInfo);
        }
        return ParameterBuilder.build(paramInfos);
    }

    private ParameterModel build() throws Exception {
        return this.innerBuilder.build();
    }

    private class ZBParameterBuilder
    extends InnerParameterBuilder {
        public ZBParameterBuilder(ParameterInfoVO paramInfo) {
            super(paramInfo);
        }

        @Override
        public ParameterModel build() throws Exception {
            this.paramModel = this.hasPublicParam() ? this.createPublicParamModel() : new ParameterModel();
            this.paramModel.setName(this.conditionField.getFullName());
            this.paramModel.setTitle(this.queryObject.getDisplayTitle());
            this.paramModel.setMessageAlias(this.queryObject.getMessageAlias());
            this.buildDataSource();
            this.buildDefaultValue();
            this.buildOther();
            return this.paramModel;
        }

        private void buildDataSource() throws Exception {
            if (this.hasPublicParam()) {
                return;
            }
            QueryField queryField = (QueryField)this.queryObject;
            NonDataSourceModel dataSourceModel = new NonDataSourceModel();
            if (StringUtils.isNotEmpty((String)queryField.getRelatedDimension())) {
                dataSourceModel = ParameterDataSourceManager.getInstance().getFactory("com.jiuqi.publicparam.datasource.dimension").newInstance();
                JSONObject modelJson = new JSONObject();
                dataSourceModel.toJson(modelJson);
                modelJson.put("entityViewId", (Object)queryField.getRelatedDimension());
                dataSourceModel.fromJson(modelJson);
                dataSourceModel.setBusinessType(DataBusinessType.GENERAL_DIM);
            }
            dataSourceModel.setDataType(queryField.getDataType());
            this.paramModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
        }

        private void buildDefaultValue() {
            switch (this.conditionField.getConditionType()) {
                case MULTIPLE: {
                    this.paramModel.setSelectMode(ParameterSelectMode.MUTIPLE);
                    this.paramModel.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
                    break;
                }
                case RANGE: {
                    this.paramModel.setSelectMode(ParameterSelectMode.RANGE);
                    this.paramModel.setValueConfig((AbstractParameterValueConfig)new ParameterRangeValueConfig());
                    break;
                }
                default: {
                    this.paramModel.setSelectMode(ParameterSelectMode.SINGLE);
                    this.paramModel.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
                    if (this.paramModel.getDatasource() instanceof NonDataSourceModel) break;
                    this.paramModel.setNullable(false);
                }
            }
            this.buildDefaultValue(false);
            if (this.conditionField.getConditionType() == ConditionType.RANGE) {
                this.buildDefaultValue(true);
            }
        }

        private void buildDefaultValue(boolean isMaxDefalutValue) {
            DefaultValueMode valueMode = this.conditionField.getDefaultValueMode();
            String[] values = this.conditionField.getDefaultValues();
            if (isMaxDefalutValue) {
                valueMode = this.conditionField.getDefaultMaxValueMode();
                values = this.conditionField.getDefaultMaxValue() != null ? new String[]{this.conditionField.getDefaultMaxValue()} : new String[]{};
            }
            if (StringUtils.isNotEmpty((String)((QueryField)this.queryObject).getRelatedDimension()) && valueMode == DefaultValueMode.NONE) {
                values = new String[]{};
            }
            FixedMemberParameterValue _value = null;
            _value = values != null && values.length > 0 ? new FixedMemberParameterValue(Arrays.asList(values)) : new FixedMemberParameterValue();
            if (!isMaxDefalutValue) {
                this.paramModel.getValueConfig().setDefaultValueMode("appoint");
                this.paramModel.getValueConfig().setDefaultValue((AbstractParameterValue)_value);
            } else {
                ((ParameterRangeValueConfig)this.paramModel.getValueConfig()).setDefaultMaxValueMode("appoint");
                ((ParameterRangeValueConfig)this.paramModel.getValueConfig()).setDefaultMaxValue((AbstractParameterValue)_value);
            }
        }

        private void buildOther() {
            this.setShowCode();
            if (this.hasPublicParam()) {
                return;
            }
            this.setParameterValueConfig();
            if (this.conditionField.getConditionType() == ConditionType.MULTIPLE) {
                this.paramModel.setWidgetType(ParameterWidgetType.POPUP.value());
            }
        }

        private void setShowCode() {
            if (StringUtils.isNotEmpty((String)((QueryField)this.queryObject).getRelatedDimension())) {
                this.paramModel.setShowCode(false);
                this.paramModel.setSwitchShowCode(true);
            }
        }
    }

    private class DimensionParameterBuilder
    extends InnerParameterBuilder {
        public DimensionParameterBuilder(ParameterInfoVO paramInfo) {
            super(paramInfo);
        }

        @Override
        public ParameterModel build() throws Exception {
            this.paramModel = this.hasPublicParam() ? this.createPublicParamModel() : new ParameterModel();
            this.paramModel.setName(this.conditionField.isCalibreCondition() ? this.conditionField.getCalibreName() : this.conditionField.getFullName());
            this.paramModel.setTitle(this.conditionField.isCalibreCondition() ? this.conditionField.getCalibreTitle() : this.queryObject.getDisplayTitle());
            this.buildAlias();
            this.buildDataSource();
            this.buildDefaultValue();
            this.buildOther();
            return this.paramModel;
        }

        private void buildAlias() {
            if (this.conditionField.isCalibreCondition()) {
                this.paramModel.setMessageAlias(FullNameWrapper.getMessageAlias_Calibre(this.conditionField.getCalibreName()));
            } else {
                this.paramModel.setMessageAlias(StringUtils.isEmpty((String)this.queryDimension.getMessageAlias()) ? FullNameWrapper.getMessageAlias(this.queryDimension) : this.queryDimension.getMessageAlias());
            }
        }

        private void buildDataSource() throws Exception {
            if (this.hasPublicParam()) {
                return;
            }
            String dsType = null;
            DataBusinessType businessType = DataBusinessType.NONE;
            boolean isTimekey = false;
            if (this.queryDimension.getDimensionType() == QueryDimensionType.PERIOD) {
                dsType = this.queryDimension.isEnableAdjust() && this.conditionField.getConditionType() == ConditionType.SINGLE ? "com.jiuqi.publicparam.datasource.adjustDate" : "com.jiuqi.publicparam.datasource.date";
                businessType = DataBusinessType.TIME_DIM;
                isTimekey = true;
            } else if (DimensionValueUtils.isPeriodChildDim(this.queryDimension)) {
                dsType = "com.jiuqi.publicparam.datasource.timegranularity";
                businessType = DataBusinessType.TIME_DIM;
            } else if (!this.queryDimension.isVirtualDimension()) {
                dsType = "com.jiuqi.publicparam.datasource.dimension";
                if (this.queryDimension.isCalibreDimension() || this.conditionField.isCalibreCondition()) {
                    dsType = "com.jiuqi.publicparam.datasource.caliber";
                } else if (this.queryDimension.isEnableStandardCurrency()) {
                    dsType = "com.jiuqi.publicparam.datasource.currency";
                }
                businessType = DataBusinessType.GENERAL_DIM;
                if (this.queryDimension.getDimensionType() == QueryDimensionType.MASTER && this.queryDimension.isOrgDimension()) {
                    businessType = DataBusinessType.UNIT_DIM;
                }
            }
            NonDataSourceModel dataSourceModel = new NonDataSourceModel();
            if (dsType != null) {
                dataSourceModel = ParameterDataSourceManager.getInstance().getFactory(dsType).newInstance();
                JSONObject modelJson = new JSONObject();
                dataSourceModel.toJson(modelJson);
                if (this.queryDimension.getDimensionType() == QueryDimensionType.PERIOD) {
                    if ("com.jiuqi.publicparam.datasource.adjustDate".equals(dsType)) {
                        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)ApplicationContextProvider.getBean(IRuntimeDataSchemeService.class);
                        modelJson.put("dataScheme", (Object)runtimeDataSchemeService.getDataSchemeByCode(this.queryDimension.getSchemeName()).getKey());
                    }
                    modelJson.put("entityViewId", (Object)BqlTimeDimUtils.getPeriodEntityId((String)this.paramModel.getName()));
                    modelJson.put("periodType", this.queryDimension.getPeriodType().type());
                    if (StringUtils.isNotEmpty((String)this.conditionField.getMinValue()) || StringUtils.isNotEmpty((String)this.conditionField.getMaxValue())) {
                        modelJson.put("periodStartEnd", (Object)(StringUtils.trim((String)this.conditionField.getMinValue()) + "-" + StringUtils.trim((String)this.conditionField.getMaxValue())));
                    }
                    if (PeriodUtil.is13Period(this.queryDimension)) {
                        modelJson.put("minFiscalMonth", this.queryDimension.getMinFiscalMonth());
                        modelJson.put("maxFiscalMonth", this.queryDimension.getMaxFiscalMonth());
                    }
                    this.paramModel.setOnlyLeafSelectable(true);
                } else if (DimensionValueUtils.isPeriodChildDim(this.queryDimension)) {
                    modelJson.put("timekeyEntityViewId", (Object)BqlTimeDimUtils.getPeriodEntityId((String)this.queryDimension.getParent()));
                    modelJson.put("timekeyPeriodType", PeriodUtil.getPeriodType(this.queryDimension.getParent(), this.queryDimension.getPeriodType()).type());
                    modelJson.put("timegranularity", PeriodUtil.toTimeGranularity(this.queryDimension.getPeriodType()).value());
                    if (this.queryDimension.getPeriodType() == PeriodType.YEAR) {
                        modelJson.put("dataPattern", (Object)"yyyy");
                    } else if (this.queryDimension.getPeriodType() == PeriodType.MONTH) {
                        modelJson.put("minFiscalMonth", this.queryDimension.getMinFiscalMonth());
                        modelJson.put("maxFiscalMonth", this.queryDimension.getMaxFiscalMonth());
                    }
                } else if (this.queryDimension.isCalibreDimension() || this.conditionField.isCalibreCondition()) {
                    modelJson.put("entityViewId", (Object)this.paramModel.getName());
                } else {
                    modelJson.put("entityViewId", (Object)FullNameWrapper.getEntityId(this.queryDimension));
                    if (StringUtils.isNotEmpty((String)this.reportScheme)) {
                        modelJson.put("formSchemeKey", (Object)this.reportScheme);
                    }
                    if (StringUtils.isNotEmpty((String)this.conditionField.getProperties())) {
                        modelJson.put("properties", (Object)this.conditionField.getProperties());
                    }
                }
                dataSourceModel.fromJson(modelJson);
            }
            dataSourceModel.setDataType(DataType.STRING.value());
            dataSourceModel.setBusinessType(businessType);
            dataSourceModel.setTimekey(isTimekey);
            this.paramModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
        }

        private void buildDefaultValue() {
            switch (this.conditionField.getConditionType()) {
                case MULTIPLE: {
                    this.paramModel.setSelectMode(ParameterSelectMode.MUTIPLE);
                    this.paramModel.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
                    break;
                }
                case RANGE: {
                    this.paramModel.setSelectMode(ParameterSelectMode.RANGE);
                    this.paramModel.setValueConfig((AbstractParameterValueConfig)new ParameterRangeValueConfig());
                    break;
                }
                default: {
                    this.paramModel.setSelectMode(ParameterSelectMode.SINGLE);
                    this.paramModel.setValueConfig((AbstractParameterValueConfig)new ParameterValueConfig());
                    this.paramModel.setNullable(false);
                }
            }
            this.buildDefaultValue(false);
            if (this.conditionField.getConditionType() == ConditionType.RANGE) {
                this.buildDefaultValue(true);
            }
        }

        private void buildDefaultValue(boolean isMaxDefalutValue) {
            DefaultValueMode valueMode = this.conditionField.getDefaultValueMode();
            String[] values = this.conditionField.getDefaultValues();
            if (isMaxDefalutValue) {
                valueMode = this.conditionField.getDefaultMaxValueMode();
                values = this.conditionField.getDefaultMaxValue() != null ? new String[]{this.conditionField.getDefaultMaxValue()} : new String[]{};
            }
            String _valueMode = null;
            FixedMemberParameterValue _value = null;
            switch (valueMode) {
                case FIRST: {
                    _valueMode = "first";
                    break;
                }
                case FIRST_CHILD: {
                    _valueMode = "firstChild";
                    break;
                }
                case FIRST_ALLCHILD: {
                    _valueMode = "firstAllChild";
                    break;
                }
                case CURRENT: {
                    _valueMode = "currPeriod";
                    break;
                }
                case PREVIOUS: {
                    _valueMode = "priorPeriod";
                    break;
                }
                case PREVIOUS_N: {
                    int offset = isMaxDefalutValue ? this.conditionField.getDefaultMaxPreviousN() : this.conditionField.getDefaultPreviousN();
                    PeriodType periodType = this.queryDimension.getPeriodType();
                    if (this.queryDimension.getDimensionType() == QueryDimensionType.CHILD) {
                        try {
                            String value = DimensionValueUtils.getCurrentPeriod(this.queryDimension, -1 * offset);
                            _valueMode = "appoint";
                            if (StringUtils.isNotEmpty((String)value)) {
                                _value = new FixedMemberParameterValue((Object)value);
                                _value.setBindingData(this.conditionField.getDefaultBinding());
                                break;
                            }
                            _value = new FixedMemberParameterValue();
                            break;
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    _valueMode = "expr";
                    _value = new ExpressionParameterValue(-offset + String.valueOf((char)periodType.code()));
                    break;
                }
                case APPOINT: {
                    _valueMode = "appoint";
                    if (values != null && values.length > 0) {
                        _value = new FixedMemberParameterValue(Arrays.asList(values));
                        _value.setBindingData(this.conditionField.getDefaultBinding());
                        break;
                    }
                    _value = new FixedMemberParameterValue();
                    break;
                }
                default: {
                    _valueMode = "none";
                }
            }
            if (!isMaxDefalutValue) {
                this.paramModel.getValueConfig().setDefaultValueMode(_valueMode);
                this.paramModel.getValueConfig().setDefaultValue((AbstractParameterValue)_value);
            } else {
                ((ParameterRangeValueConfig)this.paramModel.getValueConfig()).setDefaultMaxValueMode(_valueMode);
                ((ParameterRangeValueConfig)this.paramModel.getValueConfig()).setDefaultMaxValue((AbstractParameterValue)_value);
            }
        }

        private void buildOther() {
            if (this.hasPublicParam()) {
                this.setShowCode();
                return;
            }
            this.setParameterValueConfig();
            if (this.queryDimension.getDimensionType() == QueryDimensionType.PERIOD && this.queryDimension.getPeriodType() != PeriodType.CUSTOM) {
                if (this.queryDimension.isEnableAdjust() && this.conditionField.getConditionType() == ConditionType.SINGLE) {
                    this.paramModel.setWidgetType(101);
                } else {
                    this.paramModel.setWidgetType(ParameterWidgetType.DATEPICKER.value());
                }
            } else if (DimensionValueUtils.isPeriodChildDim(this.queryDimension) && this.queryDimension.getPeriodType() == PeriodType.YEAR) {
                this.paramModel.setWidgetType(ParameterWidgetType.DATEPICKER.value());
            } else if (this.paramModel.getSelectMode() == ParameterSelectMode.SINGLE) {
                this.paramModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
                this.setShowCode();
            } else if (this.paramModel.getSelectMode() == ParameterSelectMode.MUTIPLE) {
                if (this.queryDimension.getDimensionType() == QueryDimensionType.MASTER && !this.queryDimension.isCalibreDimension() && !this.conditionField.isCalibreCondition()) {
                    this.paramModel.setWidgetType(ParameterWidgetType.UNITSELECTOR.value());
                } else {
                    this.paramModel.setWidgetType(ParameterWidgetType.POPUP.value());
                    this.setShowCode();
                }
            }
        }

        private void setShowCode() {
            if (!DimensionValueUtils.isPeriodChildDim(this.queryDimension)) {
                this.paramModel.setShowCode(false);
                this.paramModel.setSwitchShowCode(true);
            }
        }
    }

    private abstract class InnerParameterBuilder {
        protected ConditionField conditionField;
        protected QueryObject queryObject;
        protected QueryDimension queryDimension;
        protected String reportScheme;
        private Boolean hasPublicParam;
        protected ParameterModel paramModel;

        public InnerParameterBuilder(ParameterInfoVO paramInfo) {
            this.conditionField = paramInfo.getConditionField();
            this.queryObject = paramInfo.getQueryObject();
            this.queryDimension = paramInfo.getQueryDimension();
            this.reportScheme = paramInfo.getReportScheme();
        }

        public abstract ParameterModel build() throws Exception;

        protected boolean hasPublicParam() {
            if (this.hasPublicParam == null) {
                this.hasPublicParam = false;
                if (this.queryDimension != null) {
                    if (this.queryDimension.getDimensionType() == QueryDimensionType.INNER) {
                        this.hasPublicParam = StringUtils.isNotEmpty((String)this.queryDimension.getRelatedPublicParameter());
                    }
                } else if (StringUtils.isNotEmpty((String)((QueryField)this.queryObject).getRelatedDimension())) {
                    this.hasPublicParam = StringUtils.isNotEmpty((String)this.queryObject.getRelatedPublicParameter());
                }
            }
            return this.hasPublicParam;
        }

        protected ParameterModel createPublicParamModel() throws ParameterStorageException {
            String publicParamName = null;
            publicParamName = this.queryDimension != null ? this.queryDimension.getRelatedPublicParameter() : this.queryObject.getRelatedPublicParameter();
            ParameterModel pModel = ParameterStorageManager.getInstance().findModel(publicParamName);
            if (pModel == null) {
                throw new ParameterStorageException("\u65e0\u6cd5\u83b7\u53d6\u516c\u5171\u53c2\u6570\u7684\u6a21\u578b\u5bf9\u8c61\u3010" + publicParamName + "\u3011");
            }
            return pModel;
        }

        protected void setParameterValueConfig() {
            String candidateValueFilter;
            String biFx = candidateValueFilter = this.conditionField.getCandidateValueFilter();
            if (StringUtils.isNotEmpty((String)candidateValueFilter)) {
                BiAdaptParam biAdaptParam = new BiAdaptParam();
                BiAdapTransResult res = null;
                try {
                    res = ParameterBuilder.this.biAdaptFormulaParser.transFormulaToBiSyntax(biAdaptParam, candidateValueFilter);
                    biFx = res.getBiFormula();
                }
                catch (InterpretException | ParseException throwable) {
                    // empty catch block
                }
                ZBQueryParameterValueConfig valueConfig = new ZBQueryParameterValueConfig();
                valueConfig.setCandidateMode(ParameterCandidateValueMode.EXPRESSION);
                valueConfig.setCandidateValue(Collections.singletonList(biFx));
                this.paramModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
            }
        }
    }
}

