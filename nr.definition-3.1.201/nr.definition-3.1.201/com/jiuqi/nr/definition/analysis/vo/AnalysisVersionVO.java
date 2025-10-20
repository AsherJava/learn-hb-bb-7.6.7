/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver
 *  com.fasterxml.jackson.databind.module.SimpleModule
 */
package com.jiuqi.nr.definition.analysis.vo;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionAttributeImpl;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionInfoImpl;
import java.io.Serializable;
import java.util.List;

public class AnalysisVersionVO
implements Serializable {
    private static final long serialVersionUID = 7553725157948040602L;
    private String key;
    private String tableKey;
    private String title;
    private List<DimensionInfo> srcDimension;
    private String measureUnit;
    private int roundDivisor;
    private String customPeriodRange;
    private long timeStamp;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableCode) {
        this.tableKey = tableCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DimensionInfo> getSrcDimension() {
        return this.srcDimension;
    }

    public void setSrcDimension(List<DimensionInfo> srcDimension) {
        this.srcDimension = srcDimension;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getRoundDivisor() {
        return this.roundDivisor;
    }

    public void setRoundDivisor(int roundDivisor) {
        this.roundDivisor = roundDivisor;
    }

    public String getCustomPeriodRange() {
        return this.customPeriodRange;
    }

    public void setCustomPeriodRange(String customPeriodRange) {
        this.customPeriodRange = customPeriodRange;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static Module getDeserializedModule() {
        SimpleModule module = new SimpleModule();
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(DimensionInfo.class, DimensionInfoImpl.class);
        resolver.addMapping(DimensionAttribute.class, DimensionAttributeImpl.class);
        module.setAbstractTypes(resolver);
        return module;
    }
}

