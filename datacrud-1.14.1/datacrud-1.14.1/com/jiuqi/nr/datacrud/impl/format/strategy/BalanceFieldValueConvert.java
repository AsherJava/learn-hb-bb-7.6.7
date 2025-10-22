/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.datacrud.impl.format.strategy;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureFieldValueConvert;
import com.jiuqi.nr.datacrud.impl.measure.MeasureView;
import com.jiuqi.nr.datascheme.api.DataField;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BalanceFieldValueConvert
extends MeasureFieldValueConvert {
    private static final Logger logger = LoggerFactory.getLogger(BalanceFieldValueConvert.class);
    private Integer numDecimalPlaces;
    private Set<String> noConvertCols;

    public BalanceFieldValueConvert(MeasureData selectMeasureData, MeasureView formMeasureView, MeasureData formMeasureData) {
        super(selectMeasureData, formMeasureView, formMeasureData);
    }

    public void setNoConvertCols(Set<String> noConvertCols) {
        this.noConvertCols = noConvertCols;
    }

    @Override
    public Object processValue(QueryField queryField, Object value) {
        if (this.noConvertCols != null) {
            if (this.noConvertCols.contains(queryField.getFieldName())) {
                return value;
            }
            if (this.noConvertCols.contains(queryField.getFieldCode())) {
                return value;
            }
        }
        return super.processValue(queryField, value);
    }

    @Override
    protected Object convertData(Object value, DataField dataField, MeasureData fieldMeasureData) {
        try {
            BigDecimal valueBigDecimal;
            Integer fractionDigits = dataField.getDecimal();
            if (fractionDigits == null) {
                fractionDigits = 0;
            }
            if (this.numDecimalPlaces != null) {
                fractionDigits = this.numDecimalPlaces;
            }
            if ((valueBigDecimal = this.getBigDecimal(value)) == null) {
                return value;
            }
            BigDecimal cy = fieldMeasureData == null ? this.measureRate : this.selectMeasureData.getRateValue().divide(fieldMeasureData.getRateValue(), 20, RoundingMode.HALF_UP);
            valueBigDecimal = valueBigDecimal.divide(cy, (int)fractionDigits, RoundingMode.HALF_UP);
            return valueBigDecimal;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return value;
        }
    }

    public void setNumDecimalPlaces(Integer numDecimalPlaces) {
        this.numDecimalPlaces = numDecimalPlaces;
    }
}

