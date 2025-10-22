/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.INumberData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.nr.datacrud.impl.measure;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.INumberData;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.measure.MeasureView;
import com.jiuqi.nr.datacrud.spi.IDataValueProcessor;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MeasureDataValueProcessor
implements IDataValueProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MeasureDataValueProcessor.class);
    protected MeasureService measureService;
    protected MeasureData selectMeasureData;
    protected MeasureView formMeasureView;
    protected MeasureData formMeasureData;
    protected final Map<String, MeasureData> measureDataCache = new HashMap<String, MeasureData>();
    protected BigDecimal measureRate;

    public MeasureDataValueProcessor(MeasureData selectMeasureData, MeasureView formMeasureView, MeasureData formMeasureData) {
        this.selectMeasureData = selectMeasureData;
        this.formMeasureView = formMeasureView;
        this.formMeasureData = formMeasureData;
        if (this.selectMeasureData == null || this.formMeasureData == null || this.formMeasureView == null) {
            this.measureRate = BigDecimal.ONE;
        } else {
            BigDecimal selectRate = this.selectMeasureData.getRateValue();
            this.measureRate = selectRate.divide(this.formMeasureData.getRateValue(), 20, RoundingMode.HALF_UP);
        }
    }

    @Override
    public AbstractData processValue(IMetaData metaData, AbstractData value) {
        if (!this.isProcessValue(metaData, value)) {
            return value;
        }
        DataLinkDefine dataLinkDefine = metaData.getDataLinkDefine();
        if (dataLinkDefine == null) {
            return this.processValue(metaData.getDataField(), value);
        }
        return this.processValue(metaData.getDataLinkDefine(), metaData.getDataField(), value);
    }

    private boolean isProcessValue(IMetaData metaData, AbstractData value) {
        if (this.measureService == null || this.measureRate == null) {
            return false;
        }
        if (BigDecimal.ONE.compareTo(this.measureRate) == 0) {
            return false;
        }
        if (value == null || value.isNull) {
            return false;
        }
        if (metaData.notProcessValue()) {
            return false;
        }
        int dataType = metaData.getDataType();
        switch (dataType) {
            case 3: 
            case 4: 
            case 10: {
                break;
            }
            case -2: {
                return value instanceof INumberData;
            }
            default: {
                return false;
            }
        }
        return true;
    }

    @Override
    public AbstractData processValue(DataField dataField, AbstractData value) {
        if (this.measureService == null || this.measureRate == null) {
            return value;
        }
        if (BigDecimal.ONE.compareTo(this.measureRate) == 0) {
            return value;
        }
        if (value == null || value.isNull) {
            return value;
        }
        if (dataField == null) {
            return value;
        }
        if (dataField.getDataFieldKind() == DataFieldKind.BUILT_IN_FIELD) {
            return value;
        }
        int dataType = value.dataType;
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
        String fieldMeasureUnit = dataField.getMeasureUnit();
        if (StringUtils.hasLength(fieldMeasureUnit) && fieldMeasureUnit.endsWith("NotDimession")) {
            return value;
        }
        MeasureData fieldMeasureData = this.getFiledMeasureData(fieldMeasureUnit);
        return this.convertData(value, dataField, fieldMeasureData);
    }

    public AbstractData processValue(DataLinkDefine dataLinkDefine, DataField dataField, AbstractData value) {
        String measureUnit = dataLinkDefine.getMeasureUnit();
        if (dataField != null && !StringUtils.hasLength(measureUnit)) {
            measureUnit = dataField.getMeasureUnit();
        }
        MeasureData measureData = this.getFiledMeasureData(measureUnit);
        return this.convertData(value, dataField, measureData);
    }

    public void setMeasureService(MeasureService measureService) {
        this.measureService = measureService;
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

    protected AbstractData convertData(AbstractData value, DataField dataField, MeasureData fieldMeasureData) {
        try {
            BigDecimal cy;
            Integer fractionDigits;
            if (value == null || value.isNull) {
                return value;
            }
            BigDecimal currency = value.getAsCurrency();
            boolean addDigits = true;
            if (dataField != null) {
                fractionDigits = dataField.getDecimal();
            } else {
                fractionDigits = currency.scale();
                addDigits = false;
            }
            if (fractionDigits == null) {
                fractionDigits = 0;
            }
            if (fieldMeasureData == null) {
                cy = this.measureRate;
            } else {
                BigDecimal selectRate = this.selectMeasureData.getRateValue();
                cy = selectRate.divide(fieldMeasureData.getRateValue(), 20, RoundingMode.HALF_UP);
            }
            int scale = addDigits ? fractionDigits + (cy.precision() - cy.scale() - 1) : fractionDigits;
            BigDecimal divide = currency.divide(cy, scale, RoundingMode.HALF_UP);
            return AbstractData.valueOf((Object)divide, (int)value.dataType);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return value;
        }
    }
}

