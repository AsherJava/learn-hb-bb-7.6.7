/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.manager.TimeKeyHelper
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 */
package com.jiuqi.nr.summary.executor.query.test.case1;

import com.jiuqi.bi.dataset.manager.TimeKeyHelper;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSField;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;

public class TestDSModel1 {
    public SummaryDSModel getTestDsModel() {
        SummaryDSModel summaryDSModel = new SummaryDSModel();
        this.addDimFields(summaryDSModel);
        this.addMeasureFields(summaryDSModel);
        return summaryDSModel;
    }

    private void addDimFields(SummaryDSModel dsModel) {
        SummaryDSField field = this.getDimField("MD_ORG_CODE", "\u7ec4\u7ec7\u4ee3\u7801", 6, FieldType.GENERAL_DIM, "MD_ORG.CODE", "MD_ORG_CODE", "MD_ORG[CODE]");
        dsModel.addSummaryField(field.getName(), field);
        dsModel.getPublicDimFields().add(field.getName());
        field = this.getDimField("MD_ORG_ORGCODE", "\u7ec4\u7ec7ORG\u7801", 6, FieldType.GENERAL_DIM, "MD_ORG.ORGCODE", "MD_ORG_CODE", "MD_ORG[ORGCODE]");
        dsModel.addSummaryField(field.getName(), field);
        field = this.getDimField("MD_ORG_NAME", "\u7ec4\u7ec7\u6807\u9898", 6, FieldType.GENERAL_DIM, "MD_ORG.NAME", "MD_ORG_CODE", "MD_ORG[NAME]");
        dsModel.addSummaryField(field.getName(), field);
    }

    private void addMeasureFields(SummaryDSModel dsModel) {
        SummaryDSField field = this.getMeasureField("ZB2", "\u5b57\u7b26", 6, FieldType.DESCRIPTION, "FMXX_GD1.ZB2", null, "4f425859-bebc-4ba1-870b-5e78db1edadc");
        dsModel.addSummaryField(field.getName(), field);
        dsModel.getQueryMeasureFields().add(field.getName());
        field = this.getMeasureField("ZB1", "\u6570\u503c", 3, FieldType.MEASURE, "FMXX_GD1.ZB1", "#,##.00", "65c5ac1c-b2e5-4d28-b9c0-a4159481ac6e");
        dsModel.addSummaryField(field.getName(), field);
        dsModel.getQueryMeasureFields().add(field.getName());
    }

    private SummaryDSField getPeriodDim(String dataTimeKey, boolean isCustom, PeriodType periodType) {
        String code = NrPeriodConst.PREFIX_CODE + dataTimeKey + "_" + PeriodTableColumn.TIMEKEY.getCode();
        String title = "\u65f6\u671f";
        SummaryDSField dsField = new SummaryDSField();
        dsField.setName(code);
        dsField.setTitle(title);
        dsField.setMessageAlias(PeriodTableColumn.TIMEKEY.getCode());
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setKeyField(code);
        if (isCustom) {
            dsField.setFieldType(FieldType.GENERAL_DIM);
        } else {
            dsField.setFieldType(FieldType.TIME_DIM);
        }
        dsField.setTimekey(true);
        dsField.setTimegranularity(TimeDimUtils.periodTypeToTimeGranularity((PeriodType)periodType));
        dsField.setDataPattern("yyyyMMdd");
        if (dsField.getTimegranularity() != null) {
            dsField.setShowPattern(TimeKeyHelper.getDefaultShowDatePattern((int)dsField.getTimegranularity().value()));
        }
        dsField.setValType(6);
        return dsField;
    }

    private SummaryDSField getDimField(String code, String title, int dataType, FieldType fieldType, String alias, String keyField, String exp) {
        SummaryDSField dsField = new SummaryDSField();
        dsField.setName(code);
        dsField.setTitle(title);
        dsField.setMessageAlias(alias);
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setKeyField(keyField);
        dsField.setFieldType(fieldType);
        dsField.setValType(dataType);
        dsField.setExpression(exp);
        return dsField;
    }

    private SummaryDSField getMeasureField(String code, String title, int dataType, FieldType fieldType, String alias, String showPattern, String zbKey) {
        SummaryDSField dsField = new SummaryDSField();
        dsField.setName(code);
        dsField.setKeyField(code);
        dsField.setNameField(code);
        dsField.setTitle(title);
        dsField.setValType(dataType);
        dsField.setFieldType(fieldType);
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setMessageAlias(alias);
        dsField.setShowPattern(showPattern);
        dsField.setZbKey(zbKey);
        return dsField;
    }
}

