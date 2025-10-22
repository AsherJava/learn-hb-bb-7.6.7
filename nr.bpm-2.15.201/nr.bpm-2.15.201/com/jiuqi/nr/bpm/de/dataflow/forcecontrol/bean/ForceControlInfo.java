/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.util.RejectStateEnum;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class ForceControlInfo {
    private DimensionCombination dimensionCombination;
    private String currentCorporateValue;
    private String formKey;
    private String groupKey;
    private String parentKey;
    private String parentKeyWithCorporateValue;
    private DimensionValueSet parentDimensionValue;
    private int level;
    private int current_state = RejectStateEnum.INIT.getValue();
    private String unitCode;
    private String unitName;
    private IEntityRow parentEntityRow;

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrent_state() {
        return this.current_state;
    }

    public void setCurrent_state(int current_state) {
        this.current_state = current_state;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public IEntityRow getParentEntityRow() {
        return this.parentEntityRow;
    }

    public void setParentEntityRow(IEntityRow parentEntityRow) {
        this.parentEntityRow = parentEntityRow;
    }

    public String getParentKeyWithCorporateValue() {
        return this.parentKeyWithCorporateValue;
    }

    public void setParentKeyWithCorporateValue(String parentKeyWithCorporateValue) {
        this.parentKeyWithCorporateValue = parentKeyWithCorporateValue;
    }

    public DimensionValueSet getParentDimensionValue() {
        return this.parentDimensionValue;
    }

    public void setParentDimensionValue(DimensionValueSet parentDimensionValue) {
        this.parentDimensionValue = parentDimensionValue;
    }

    public String getCurrentCorporateValue() {
        return this.currentCorporateValue;
    }

    public void setCurrentCorporateValue(String currentCorporateValue) {
        this.currentCorporateValue = currentCorporateValue;
    }
}

