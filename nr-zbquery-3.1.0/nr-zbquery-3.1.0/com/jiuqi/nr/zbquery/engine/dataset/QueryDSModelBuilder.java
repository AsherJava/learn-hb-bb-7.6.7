/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType
 *  com.jiuqi.bi.query.model.DimFieldDescriptor
 *  com.jiuqi.bi.query.model.MeasureDescriptor
 *  com.jiuqi.bi.query.model.QueryModel
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 */
package com.jiuqi.nr.zbquery.engine.dataset;

import com.jiuqi.bi.adhoc.model.TimeGranularity;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.query.model.DimFieldDescriptor;
import com.jiuqi.bi.query.model.MeasureDescriptor;
import com.jiuqi.bi.query.model.QueryModel;
import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSField;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModel;
import com.jiuqi.nr.zbquery.engine.executor.QueryModelBuilder;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.HeaderMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.EnumTransfer;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.ParameterBuilder;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class QueryDSModelBuilder {
    public static final String DS_NAME = "DS1";
    public static final String DS_TITLE = "\u6570\u636e\u96c61";
    private ZBQueryModel zbQueryModel;
    private ConditionValues conditionValues;
    private QueryDSModel dsModel;
    private QueryModelBuilder _builder;
    private QueryModel _queryModel;

    public QueryDSModelBuilder(ZBQueryModel zbQueryModel) {
        this.zbQueryModel = zbQueryModel;
    }

    public QueryDSModelBuilder(ZBQueryModel zbQueryModel, ConditionValues conditionValues) {
        this.zbQueryModel = zbQueryModel;
        this.conditionValues = conditionValues;
    }

    public void build() throws Exception {
        this.build(false);
    }

    public void build(boolean buildParam) throws Exception {
        this.init();
        this.buildQueryModel();
        this.dsModel._setGuid(Guid.newGuid());
        this.dsModel.setName(DS_NAME);
        this.dsModel.setTitle(DS_TITLE);
        if (this.zbQueryModel.getQueryObjects().isEmpty()) {
            return;
        }
        QueryDimension periodDim = this._builder.getModelFinder().getPeriodDimension();
        if (periodDim != null) {
            this.dsModel.setMinFiscalMonth(periodDim.getMinFiscalMonth());
            this.dsModel.setMaxFiscalMonth(periodDim.getMaxFiscalMonth());
        }
        this.buildDimensions();
        this.buildMeasures();
        this.buildRowNum();
        this.reorderDsField();
        if (buildParam) {
            this.buildParam();
        }
    }

    public QueryDSModel getDSModel() {
        return this.dsModel;
    }

    public QueryModelBuilder getQueryModelBuilder() {
        return this._builder;
    }

    public ZBQueryModel getZbQueryModel() {
        return this.zbQueryModel;
    }

    private void init() {
        this.dsModel = new QueryDSModel();
        this._builder = null;
        this._queryModel = null;
    }

    private void buildQueryModel() throws Exception {
        this._builder = new QueryModelBuilder(this.zbQueryModel, this.conditionValues);
        this._builder.build();
        this._queryModel = this._builder.getQueryModel();
    }

    private void buildDimensions() throws Exception {
        List _dimFields = this._queryModel.getDimensions();
        for (DimFieldDescriptor _dimField : _dimFields) {
            if (!_dimField.isVisible()) continue;
            this.buildDimension(_dimField.getAlias());
        }
        Map<String, List<String>> childDimFields = this._builder.getChildDimFields();
        for (String dimFullName : childDimFields.keySet()) {
            for (String filedFullName : childDimFields.get(dimFullName)) {
                String fieldAlias = this._builder.getFullNameAliasMapper().get(filedFullName);
                this.buildDimension(fieldAlias);
            }
        }
    }

    private void buildDimension(String fieldAlias) throws Exception {
        String fullName = this._builder.getAliasFullNameMapper().get(fieldAlias);
        QueryDSField dsField = new QueryDSField();
        dsField.setName(fieldAlias);
        dsField.setKeyField(this._builder.getKeyColMapper().get(fieldAlias));
        dsField.setNameField(fieldAlias);
        DimensionAttributeField dimAttrField = (DimensionAttributeField)this._builder.getModelFinder().getQueryObject(fullName);
        if (dimAttrField == null) {
            return;
        }
        QueryDimension queryDim = this._builder.getModelFinder().getQueryDimension(dimAttrField.getParent());
        dsField.setTitle(dimAttrField.getDisplayTitle());
        dsField.setMessageAlias(this.getMessageAlias(dimAttrField, queryDim));
        dsField.setFieldType(queryDim != null ? EnumTransfer.toFieldType(this.getRealDimType(queryDim)) : FieldType.GENERAL_DIM);
        dsField.setValType(dimAttrField.getDataType());
        dsField.setShowPattern(dimAttrField.getShowFormat());
        if (queryDim != null && this.getRealDimType(queryDim) == QueryDimensionType.PERIOD) {
            if (!queryDim.isSpecialPeriodType()) {
                dsField.setTimegranularity(EnumTransfer.toTimeGranularity(dimAttrField.getPeriodType()));
                dsField.setTimekey(dimAttrField.isTimekey());
                dsField.setShowPattern(PeriodUtil.getShowFormat(dimAttrField.getPeriodType(), dimAttrField.isTimekey()));
                if (dsField.isTimekey()) {
                    dsField.setDataPattern("yyyyMMdd");
                    String showPattern = BqlTimeDimUtils.getTimeKeyFieldShowPattern((TimeGranularity)BqlTimeDimUtils.adaptTimeGranularity((PeriodType)dimAttrField.getPeriodType()), (String)queryDim.getName());
                    if (showPattern.contains("nr.period.format")) {
                        dsField.setShowPattern(showPattern);
                    }
                }
            } else {
                dsField.setFieldType(FieldType.GENERAL_DIM);
            }
        }
        this.dsModel.getCommonFields().add(dsField);
        if (this._builder.getParentColMapper().containsKey(fieldAlias)) {
            String parentAlias = this._builder.getParentColMapper().get(fieldAlias);
            DSHierarchy hierarchy = new DSHierarchy();
            hierarchy.setName(parentAlias);
            hierarchy.setTitle(parentAlias);
            hierarchy.setType(DSHierarchyType.PARENT_HIERARCHY);
            hierarchy.getLevels().add(fieldAlias);
            hierarchy.setParentFieldName(parentAlias);
            this.dsModel.getHiers().add(hierarchy);
        }
    }

    private QueryDimensionType getRealDimType(QueryDimension queryDim) {
        QueryDimension parentDim;
        if (queryDim.getDimensionType() == QueryDimensionType.CHILD && (parentDim = this._builder.getModelFinder().getQueryDimension(queryDim.getParent())).getDimensionType() == QueryDimensionType.PERIOD) {
            return QueryDimensionType.PERIOD;
        }
        return queryDim.getDimensionType();
    }

    private String getMessageAlias(DimensionAttributeField dimAttrField, QueryDimension queryDim) {
        DimensionAttributeField _dimAttrField;
        if (queryDim == null) {
            return dimAttrField.getMessageAlias();
        }
        if (queryDim.getDimensionType() != QueryDimensionType.PERIOD && this._builder.getModelFinder().getLayoutQueryObject(dimAttrField.getFullName()) == null && dimAttrField.getFullName().equals(FullNameWrapper.getKeyFullName(queryDim))) {
            return StringUtils.isEmpty((String)queryDim.getMessageAlias()) ? FullNameWrapper.getMessageAlias(queryDim) : queryDim.getMessageAlias();
        }
        QueryDimension childDim = this._builder.getModelFinder().getChildDimension(dimAttrField.getFullName());
        if (childDim != null) {
            return StringUtils.isEmpty((String)childDim.getMessageAlias()) ? FullNameWrapper.getMessageAlias(childDim) : childDim.getMessageAlias();
        }
        if (queryDim.getDimensionType() == QueryDimensionType.PERIOD && queryDim.isSpecialPeriodType() && dimAttrField.getName().equals("P_CODE") && (_dimAttrField = (DimensionAttributeField)this._builder.getModelFinder().getQueryObject(queryDim.getFullName() + "." + "P_TITLE")) != null) {
            return StringUtils.isEmpty((String)_dimAttrField.getMessageAlias()) ? FullNameWrapper.getMessageAlias(queryDim, _dimAttrField) : _dimAttrField.getMessageAlias();
        }
        return StringUtils.isEmpty((String)dimAttrField.getMessageAlias()) ? FullNameWrapper.getMessageAlias(queryDim, dimAttrField) : dimAttrField.getMessageAlias();
    }

    private void buildMeasures() {
        List _measures = this._queryModel.getMeasures();
        for (MeasureDescriptor _measure : _measures) {
            String alias = _measure.getAlias();
            String fullName = this._builder.getAliasFullNameMapper().get(alias);
            QueryDSField dsField = new QueryDSField();
            dsField.setName(alias);
            dsField.setKeyField(alias);
            dsField.setNameField(alias);
            QueryField queryField = (QueryField)this._builder.getModelFinder().getQueryObject(fullName);
            dsField.setTitle(queryField.getDisplayTitle());
            dsField.setValType(this.getValType(queryField));
            if (DataTypes.isNumber((int)queryField.getDataType())) {
                dsField.setFieldType(FieldType.MEASURE);
            } else {
                dsField.setFieldType(FieldType.DESCRIPTION);
            }
            dsField.setShowPattern(this.getShowFormat(queryField));
            dsField.setAggregation(AggregationType.SUM);
            dsField.setApplyType(EnumTransfer.toApplyType(queryField.getApplyType()));
            dsField.setMessageAlias(queryField.getMessageAlias());
            this.dsModel.getCommonFields().add(dsField);
        }
    }

    private int getValType(QueryField queryField) {
        int valType = queryField.getDataType();
        if (EnumTransfer.isFileDataType(queryField)) {
            valType = 6;
        } else if (valType == DataFieldType.DATE_TIME.getValue()) {
            valType = 2;
        }
        return valType;
    }

    private String getShowFormat(QueryField queryField) {
        String showFormat = queryField.getShowFormat();
        if (queryField.getDataType() == DataFieldType.DATE_TIME.getValue() && StringUtils.isEmpty((String)showFormat)) {
            showFormat = "yyyy-MM-dd HH:mm:ss";
        }
        return showFormat;
    }

    private void buildRowNum() {
        if (this.zbQueryModel.getOption().getRowHeaderMode() == HeaderMode.LIST) {
            QueryDSField dsField = new QueryDSField();
            dsField.setName("SYS_ROWNUM");
            dsField.setValType(5);
            dsField.setFieldType(FieldType.GENERAL_DIM);
            dsField.setAggregation(AggregationType.SUM);
            dsField.setApplyType(ApplyType.PERIOD);
            dsField.setKeyField("SYS_ROWNUM");
            dsField.setNameField("SYS_ROWNUM");
            this.dsModel.getCommonFields().add(dsField);
        }
    }

    private void reorderDsField() {
        this.dsModel.getCommonFields().sort(new Comparator<DSField>(){

            @Override
            public int compare(DSField o1, DSField o2) {
                int cv = QueryDSModelBuilder.this._builder.getFinalQueryFieldAliases().indexOf(o1.getName()) - QueryDSModelBuilder.this._builder.getFinalQueryFieldAliases().indexOf(o2.getName());
                if (cv > 0) {
                    return 1;
                }
                if (cv < 0) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private void buildParam() throws Exception {
        this.dsModel.getParameterModels().addAll(ParameterBuilder.build(this.zbQueryModel));
    }
}

