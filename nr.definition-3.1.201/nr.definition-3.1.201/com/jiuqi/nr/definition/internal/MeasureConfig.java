/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal;

import com.jiuqi.nr.definition.facade.IMeasureConfig;

public class MeasureConfig
implements IMeasureConfig {
    private String measureKey;
    private String measureUnit;
    private String entityKey;
    private String mappingRelation;
    private String measureUnitList;

    @Override
    public String getMeasureKey() {
        return this.measureKey;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public String getEntityKey() {
        return this.entityKey;
    }

    @Override
    public String getMappingRelation() {
        return this.mappingRelation;
    }

    @Override
    public String getMeasureUnitList() {
        return this.measureUnitList;
    }

    public void setMeasureKey(String measureKey) {
        this.measureKey = measureKey;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public void setMappingRelation(String mappingRelation) {
        this.mappingRelation = mappingRelation;
    }

    public void setMeasureUnitList(String measureUnitList) {
        this.measureUnitList = measureUnitList;
    }
}

