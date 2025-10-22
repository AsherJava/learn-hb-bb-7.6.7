/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.nr.datacrud.impl.format.strategy;

import com.jiuqi.nr.datacrud.impl.format.dto.NumberOptions;
import com.jiuqi.nr.datacrud.impl.format.strategy.NumberTypeStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.math.BigDecimal;
import org.springframework.util.StringUtils;

public class MeasureNumberTypeStrategy
extends NumberTypeStrategy {
    private Integer numDecimalPlaces;

    @Override
    protected String buildPatternByNumDecimalPlaces(Integer numDecimalPlaces) {
        StringBuilder patternBuilder = new StringBuilder("#0");
        if (numDecimalPlaces != null && numDecimalPlaces > 0) {
            patternBuilder.append(".");
            for (int i = 0; i < numDecimalPlaces; ++i) {
                patternBuilder.append("0");
            }
        }
        return patternBuilder.toString();
    }

    @Override
    protected String getPattern(DataLinkDefine link, DataField field, BigDecimal value, NumberOptions options) {
        if (DataFieldType.INTEGER == options.getDataFieldType()) {
            return "#0";
        }
        Integer numDecimalPlaces = this.getNumDecimalPlaces(link, field, value);
        return this.buildPatternByNumDecimalPlaces(numDecimalPlaces);
    }

    @Override
    protected Integer getNumDecimalPlaces(DataLinkDefine link, DataField field, BigDecimal value) {
        String measureUnit = link.getMeasureUnit();
        if (measureUnit == null && field != null) {
            measureUnit = field.getMeasureUnit();
        }
        if (StringUtils.hasLength(measureUnit) && measureUnit.endsWith("NotDimession")) {
            return super.getNumDecimalPlaces(link, field, value);
        }
        if (this.numDecimalPlaces != null && this.numDecimalPlaces >= 0) {
            return this.numDecimalPlaces;
        }
        return super.getNumDecimalPlaces(link, field, value);
    }

    @Override
    public Integer getNumDecimalPlaces() {
        return this.numDecimalPlaces;
    }

    public void setNumDecimalPlaces(Integer numDecimalPlaces) {
        this.numDecimalPlaces = numDecimalPlaces;
    }
}

