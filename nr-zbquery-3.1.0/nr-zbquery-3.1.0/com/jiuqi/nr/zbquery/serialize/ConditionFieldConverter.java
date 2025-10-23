/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.util.StdConverter
 */
package com.jiuqi.nr.zbquery.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionType;
import com.jiuqi.nr.zbquery.model.DefaultValueMode;

public class ConditionFieldConverter
extends StdConverter<ConditionField, ConditionField> {
    public ConditionField convert(ConditionField conditionField) {
        if (conditionField.getConditionType() == ConditionType.RANGE && conditionField.getDefaultMaxValueMode() == null) {
            String[] defaultValues;
            DefaultValueMode defaultValueMode = conditionField.getDefaultValueMode();
            conditionField.setDefaultMaxValueMode(defaultValueMode);
            if (defaultValueMode == DefaultValueMode.APPOINT && (defaultValues = conditionField.getDefaultValues()) != null && defaultValues.length > 0) {
                if (defaultValues.length == 1) {
                    conditionField.setDefaultMaxValue(defaultValues[0]);
                } else {
                    conditionField.setDefaultMaxValue(defaultValues[1]);
                    conditionField.setDefaultValues(new String[]{defaultValues[0]});
                }
            }
        } else if (conditionField.getConditionType() == ConditionType.SINGLE && conditionField.getDefaultValueMode() == DefaultValueMode.NONE) {
            if (conditionField.getFullName().startsWith("NR_PERIOD_")) {
                conditionField.setDefaultValueMode(DefaultValueMode.CURRENT);
            } else {
                conditionField.setDefaultValueMode(DefaultValueMode.FIRST);
            }
        }
        return conditionField;
    }
}

