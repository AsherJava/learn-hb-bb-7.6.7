/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.option.core.ZeroShowValue
 */
package com.jiuqi.nr.datacrud.impl.format.strategy;

import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.format.dto.NumberOptions;
import com.jiuqi.nr.datacrud.impl.format.strategy.NumberTypeStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.option.core.ZeroShowValue;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.springframework.util.StringUtils;

public class SysNumberTypeStrategy
extends NumberTypeStrategy {
    private ITaskOptionController taskOptionController;
    private String default0;
    private Integer globalNumDecimalPlaces;
    private Integer numDecimalPlaces;
    private Boolean thousands;

    public SysNumberTypeStrategy() {
    }

    public SysNumberTypeStrategy(ITaskOptionController taskOptionController, RegionRelationFactory factory) {
        this.taskOptionController = taskOptionController;
        this.regionRelationFactory = factory;
    }

    public void setTaskOptionController(ITaskOptionController taskOptionController) {
        this.taskOptionController = taskOptionController;
    }

    @Override
    public Integer getNumDecimalPlaces() {
        return this.numDecimalPlaces;
    }

    public void setNumDecimalPlaces(Integer numDecimalPlaces) {
        this.numDecimalPlaces = numDecimalPlaces;
    }

    public Integer getGlobalNumDecimalPlaces() {
        return this.globalNumDecimalPlaces;
    }

    public void setGlobalNumDecimalPlaces(Integer globalNumDecimalPlaces) {
        this.globalNumDecimalPlaces = globalNumDecimalPlaces;
    }

    public Boolean getThousands() {
        return this.thousands;
    }

    public void setThousands(Boolean thousands) {
        this.thousands = thousands;
    }

    @Override
    protected String default0(DataLinkDefine link, DataField field) {
        if (this.taskOptionController == null || this.regionRelationFactory == null) {
            return super.default0(link, field);
        }
        if (this.default0 != null) {
            if (ZeroShowValue.ORIGINAL_VALUE.getValue().equals(this.default0)) {
                return super.default0(link, field);
            }
            if (ZeroShowValue.NULL_VALUE.getValue().equals(this.default0)) {
                return "";
            }
            return this.default0;
        }
        String regionKey = link.getRegionKey();
        RegionRelation regionRelation = this.relationKeyMap.computeIfAbsent(regionKey, k -> this.regionRelationFactory.getRegionRelation((String)k));
        TaskDefine taskDefine = regionRelation.getTaskDefine();
        String key = taskDefine.getKey();
        String value = this.taskOptionController.getValue(key, "NUMBER_ZERO_SHOW");
        if (!StringUtils.hasLength(value)) {
            return super.default0(link, field);
        }
        if (ZeroShowValue.ORIGINAL_VALUE.getValue().equals(value)) {
            this.default0 = value;
            return super.default0(link, field);
        }
        if (ZeroShowValue.NULL_VALUE.getValue().equals(value)) {
            this.default0 = value;
            return "";
        }
        this.default0 = value;
        return this.default0;
    }

    @Override
    protected DecimalFormat getDecimalFormat(DataLinkDefine link, DataField field, BigDecimal value, NumberOptions options) {
        String measureUnit;
        DecimalFormat numberFormat = super.getDecimalFormat(link, field, value, options);
        if (this.thousands != null) {
            numberFormat.setGroupingUsed(this.thousands);
            if (this.thousands.booleanValue() && numberFormat.getGroupingSize() <= 0) {
                numberFormat.setGroupingSize(3);
            }
        }
        if (DataFieldType.BIGDECIMAL == options.getDataFieldType() && this.globalNumDecimalPlaces != null) {
            numberFormat.setMinimumFractionDigits(this.globalNumDecimalPlaces);
            numberFormat.setMaximumFractionDigits(this.globalNumDecimalPlaces);
        }
        if ((measureUnit = link.getMeasureUnit()) == null && field != null) {
            measureUnit = field.getMeasureUnit();
        }
        if (StringUtils.hasLength(measureUnit) && measureUnit.endsWith("NotDimession")) {
            return numberFormat;
        }
        if (DataFieldType.BIGDECIMAL == options.getDataFieldType() && this.numDecimalPlaces != null) {
            numberFormat.setMinimumFractionDigits(this.numDecimalPlaces);
            numberFormat.setMaximumFractionDigits(this.numDecimalPlaces);
        }
        return numberFormat;
    }
}

