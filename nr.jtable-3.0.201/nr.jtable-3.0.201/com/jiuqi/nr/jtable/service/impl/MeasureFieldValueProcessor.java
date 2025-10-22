/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.dataset.AbstractRegionRelationEvn;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.MeasureViewData;
import com.jiuqi.nr.jtable.params.input.MeasureQueryInfo;
import com.jiuqi.nr.jtable.params.output.MeasureData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasureFieldValueProcessor
implements IFieldValueProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MeasureFieldValueProcessor.class);
    private final AbstractRegionRelationEvn regionRelationEvn;
    private double measureRate = 1.0;
    private MeasureViewData measureViewData;
    private String formMeasureCode;
    private String selectMeasureCode;
    private MeasureData selectMeasureData;
    private final IColumnModelFinder columnModelFinder;

    public MeasureFieldValueProcessor(AbstractRegionRelationEvn regionRelationEvn, JtableContext jtableContext) {
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.regionRelationEvn = regionRelationEvn;
        FormData form = jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        List<MeasureViewData> measures = form.getMeasures();
        if (measures != null && measures.size() > 0) {
            this.measureViewData = measures.get(0);
            this.formMeasureCode = form.getMeasureValues().get(this.measureViewData.getKey());
            this.selectMeasureCode = jtableContext.getMeasureMap().get(this.measureViewData.getKey());
            if (StringUtils.isNotEmpty((String)this.formMeasureCode) && StringUtils.isNotEmpty((String)this.selectMeasureCode) && !this.selectMeasureCode.equals(this.formMeasureCode)) {
                IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
                MeasureQueryInfo measureQueryInfo = new MeasureQueryInfo();
                measureQueryInfo.setMeasureViewKey(this.measureViewData.getKey());
                measureQueryInfo.setMeasureValue(this.formMeasureCode);
                MeasureData formMeasureData = jtableEntityService.queryMeasureDataByCode(measureQueryInfo);
                measureQueryInfo.setMeasureValue(this.selectMeasureCode);
                MeasureData selectMeasureDataInfo = jtableEntityService.queryMeasureDataByCode(measureQueryInfo);
                this.measureRate = selectMeasureDataInfo.getRate() / formMeasureData.getRate();
                this.selectMeasureData = selectMeasureDataInfo;
            }
        }
        this.columnModelFinder = (IColumnModelFinder)BeanUtil.getBean(IColumnModelFinder.class);
    }

    public Object processValue(QueryField queryField, Object value) {
        FieldType type;
        boolean numberField;
        if (this.measureRate == 1.0) {
            return value;
        }
        String columnId = queryField.getUID();
        FieldDefine field = this.columnModelFinder.findFieldDefineByColumnId(columnId);
        LinkData linkData = this.regionRelationEvn.getDataLinkByFiled(field.getKey());
        if (linkData == null) {
            return value;
        }
        FieldData fieldData = this.regionRelationEvn.getFieldByDataLink(linkData.getKey());
        MeasureData fieldMeasure = null;
        if (StringUtils.isNotEmpty((String)fieldData.getMeasureUnit())) {
            String fieldMeasureCode;
            String fieldMeasureUnit = fieldData.getMeasureUnit();
            if (fieldMeasureUnit.contains("NotDimession")) {
                return value;
            }
            String[] measures = fieldMeasureUnit.split(";");
            if (measures.length == 2 && measures[0].equals(this.measureViewData.getKey()) && !this.formMeasureCode.equals(fieldMeasureCode = measures[1])) {
                MeasureQueryInfo measureQueryInfo = new MeasureQueryInfo();
                measureQueryInfo.setMeasureViewKey(this.measureViewData.getKey());
                measureQueryInfo.setMeasureValue(fieldMeasureCode);
                IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
                fieldMeasure = jtableEntityService.queryMeasureDataByCode(measureQueryInfo);
            }
        }
        boolean bl = numberField = (type = FieldType.forValue((int)fieldData.getFieldType())) == FieldType.FIELD_TYPE_FLOAT || type == FieldType.FIELD_TYPE_INTEGER || type == FieldType.FIELD_TYPE_DECIMAL;
        if (numberField) {
            int fractionDigits = fieldData.getFractionDigits();
            try {
                BigDecimal bigDecimal;
                String tempValue = value.toString();
                if (tempValue.contains(",")) {
                    Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(tempValue);
                    bigDecimal = new BigDecimal(number.doubleValue());
                } else {
                    bigDecimal = new BigDecimal(tempValue);
                }
                bigDecimal = new BigDecimal(value.toString());
                if (fieldMeasure == null) {
                    bigDecimal = bigDecimal.divide(BigDecimal.valueOf(this.measureRate), fractionDigits, 4);
                } else {
                    double measureRateNew = this.selectMeasureData.getRate() / fieldMeasure.getRate();
                    bigDecimal = bigDecimal.divide(BigDecimal.valueOf(measureRateNew), fractionDigits, 4);
                }
                if (queryField.getDataType() == 10) {
                    return bigDecimal;
                }
                return bigDecimal.doubleValue();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return value;
            }
        }
        return value;
    }

    public double getMultiplier(QueryField queryField) {
        return this.measureRate;
    }
}

