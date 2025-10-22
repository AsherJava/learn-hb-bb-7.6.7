/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.datacrud.impl.measure;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.measure.MeasureView;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MeasureFieldValueConvert
implements IFieldValueProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MeasureFieldValueConvert.class);
    protected MeasureService measureService;
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    protected MeasureData selectMeasureData;
    protected MeasureView formMeasureView;
    protected MeasureData formMeasureData;
    protected final Map<String, MeasureData> measureDataCache = new HashMap<String, MeasureData>();
    protected BigDecimal measureRate;

    public void setMeasureService(MeasureService measureService) {
        this.measureService = measureService;
    }

    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public MeasureFieldValueConvert(MeasureData selectMeasureData, MeasureView formMeasureView, MeasureData formMeasureData) {
        this.selectMeasureData = selectMeasureData;
        this.formMeasureData = formMeasureData;
        this.formMeasureView = formMeasureView;
        this.measureRate = this.selectMeasureData == null || this.formMeasureData == null || this.formMeasureView == null ? BigDecimal.ONE : this.selectMeasureData.getRateValue().divide(this.formMeasureData.getRateValue(), 20, RoundingMode.HALF_UP);
    }

    public Object processValue(QueryField queryField, Object value) {
        if (this.runtimeDataSchemeService == null || this.measureService == null) {
            return value;
        }
        if (this.measureRate.compareTo(BigDecimal.ONE) == 0) {
            return value;
        }
        String columnId = queryField.getUID();
        if (columnId == null) {
            return value;
        }
        int dataType = queryField.getDataType();
        switch (dataType) {
            case 3: 
            case 4: 
            case 10: {
                break;
            }
            default: {
                return value;
            }
        }
        DataField dataField = this.runtimeDataSchemeService.getDataFieldByColumnKey(columnId);
        if (dataField == null && null == (dataField = this.runtimeDataSchemeService.getDataField(columnId))) {
            return value;
        }
        if (dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) {
            return value;
        }
        String fieldMeasureUnit = dataField.getMeasureUnit();
        if (StringUtils.hasLength(fieldMeasureUnit) && fieldMeasureUnit.endsWith("NotDimession")) {
            return value;
        }
        MeasureData fieldMeasureData = this.getFiledMeasureData(fieldMeasureUnit);
        return this.convertData(value, dataField, fieldMeasureData);
    }

    protected Object convertData(Object value, DataField dataField, MeasureData fieldMeasureData) {
        try {
            BigDecimal valueBigDecimal;
            Integer fractionDigits = dataField.getDecimal();
            if (fractionDigits == null) {
                fractionDigits = 0;
            }
            if ((valueBigDecimal = this.getBigDecimal(value)) == null) {
                return value;
            }
            if (fieldMeasureData == null) {
                BigDecimal cy = this.measureRate;
                int scale = fractionDigits + (cy.precision() - cy.scale() - 1);
                valueBigDecimal = valueBigDecimal.divide(cy, scale, RoundingMode.HALF_UP);
            } else {
                BigDecimal cy = this.selectMeasureData.getRateValue().divide(fieldMeasureData.getRateValue(), 20, RoundingMode.HALF_UP);
                int scale = fractionDigits + (cy.precision() - cy.scale() - 1);
                valueBigDecimal = valueBigDecimal.divide(cy, scale, RoundingMode.HALF_UP);
            }
            return valueBigDecimal;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return value;
        }
    }

    protected MeasureData getFiledMeasureData(String fieldMeasureUnit) {
        MeasureData fieldMeasureData = null;
        if (StringUtils.hasLength(fieldMeasureUnit) && (fieldMeasureData = this.measureDataCache.get(fieldMeasureUnit)) == null && !this.measureDataCache.containsKey(fieldMeasureUnit)) {
            String fieldMeasureCode;
            String[] measures = fieldMeasureUnit.split(";");
            MeasureView measureView = this.formMeasureView;
            MeasureData measureData = this.formMeasureData;
            if (measures.length == 2 && measures[0].equals(measureView.getKey()) && !(fieldMeasureCode = measures[1]).equals(measureData.getCode())) {
                fieldMeasureData = this.measureService.getByMeasure(measures[0], fieldMeasureCode);
                this.measureDataCache.put(fieldMeasureUnit, fieldMeasureData);
            }
        }
        return fieldMeasureData;
    }

    public double getMultiplier(QueryField queryField) {
        return this.measureRate.doubleValue();
    }

    protected BigDecimal getBigDecimal(Object value) {
        BigDecimal res = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                res = (BigDecimal)value;
            } else if (value instanceof String) {
                res = new BigDecimal((String)value);
            } else if (value instanceof BigInteger) {
                res = new BigDecimal((BigInteger)value);
            } else if (value instanceof Number) {
                res = BigDecimal.valueOf(((Number)value).doubleValue());
            } else {
                return null;
            }
        }
        return res;
    }
}

