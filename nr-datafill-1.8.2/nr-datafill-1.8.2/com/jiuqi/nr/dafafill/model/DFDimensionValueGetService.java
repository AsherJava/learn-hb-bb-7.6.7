/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DFDimensionValueGetService {
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    public String getValues(DFDimensionValue dfDimensionValue, DataFillModel model) {
        if (!StringUtils.hasLength(dfDimensionValue.getValues())) {
            return dfDimensionValue.getValues();
        }
        if (StringUtils.hasLength(dfDimensionValue.get_values())) {
            return dfDimensionValue.get_values();
        }
        String[] values = dfDimensionValue.getValues().split(";");
        QueryField queryField = null;
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        queryFields.addAll(model.getQueryFields());
        queryFields.addAll(model.getAssistFields());
        for (QueryField field : queryFields) {
            if (!field.getCode().equals(dfDimensionValue.getName())) continue;
            queryField = field;
            break;
        }
        if (queryField == null) {
            return dfDimensionValue.getValues();
        }
        if (queryField.getFieldType() == FieldType.PERIOD) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId());
            StringBuilder stringBuilder = new StringBuilder();
            for (String value : values) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(";");
                }
                if (periodEntity.getPeriodType().equals((Object)PeriodType.WEEK) || periodEntity.getPeriodType().equals((Object)PeriodType.CUSTOM)) {
                    dfDimensionValue.set_values(dfDimensionValue.getValues());
                    return dfDimensionValue.getValues();
                }
                stringBuilder.append(TimeDimUtils.timeKeyToPeriod((String)value, (PeriodType)periodEntity.getPeriodType()));
            }
            dfDimensionValue.set_values(stringBuilder.toString());
            return stringBuilder.toString();
        }
        dfDimensionValue.set_values(dfDimensionValue.getValues());
        return dfDimensionValue.getValues();
    }

    public String getMaxValues(DFDimensionValue dfDimensionValue, DataFillModel model) {
        if (!StringUtils.hasLength(dfDimensionValue.getMaxValue())) {
            return dfDimensionValue.getMaxValue();
        }
        if (StringUtils.hasLength(dfDimensionValue.get_maxValues())) {
            return dfDimensionValue.get_maxValues();
        }
        String[] values = dfDimensionValue.getMaxValue().split(";");
        QueryField queryField = null;
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        queryFields.addAll(model.getQueryFields());
        queryFields.addAll(model.getAssistFields());
        for (QueryField field : queryFields) {
            if (!field.getCode().equals(dfDimensionValue.getName())) continue;
            queryField = field;
            break;
        }
        if (queryField == null) {
            return dfDimensionValue.getMaxValue();
        }
        if (queryField.getFieldType() == FieldType.PERIOD) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(queryField.getId());
            StringBuilder stringBuilder = new StringBuilder();
            for (String value : values) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(";");
                }
                if (periodEntity.getPeriodType().equals((Object)PeriodType.WEEK) || periodEntity.getPeriodType().equals((Object)PeriodType.CUSTOM)) {
                    dfDimensionValue.set_maxValues(dfDimensionValue.getMaxValue());
                    return dfDimensionValue.getMaxValue();
                }
                stringBuilder.append(TimeDimUtils.timeKeyToPeriod((String)value, (PeriodType)periodEntity.getPeriodType()));
            }
            dfDimensionValue.set_maxValues(stringBuilder.toString());
            return stringBuilder.toString();
        }
        dfDimensionValue.set_maxValues(dfDimensionValue.getMaxValue());
        return dfDimensionValue.getMaxValue();
    }
}

