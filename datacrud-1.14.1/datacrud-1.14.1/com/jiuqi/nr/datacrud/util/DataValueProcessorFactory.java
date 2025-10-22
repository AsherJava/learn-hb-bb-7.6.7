/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.util;

import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureDataValueProcessor;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.measure.MeasureView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataValueProcessorFactory {
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private MeasureService measureService;

    public MeasureDataValueProcessor initMeasureDataValueProcessor(String regionKey, Measure measure) {
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        return this.initMeasureDataValueProcessor(regionRelation, measure);
    }

    public MeasureDataValueProcessor initMeasureDataValueProcessor(RegionRelation regionRelation, Measure measure) {
        MeasureView measureView = regionRelation.getMeasureView();
        MeasureData measureData = regionRelation.getMeasureData();
        MeasureData selectMeasure = null;
        if (measureView != null && measureData != null) {
            selectMeasure = this.measureService.getByMeasure(measure.getKey(), measure.getCode());
        }
        MeasureDataValueProcessor processor = new MeasureDataValueProcessor(selectMeasure, measureView, measureData);
        processor.setMeasureService(this.measureService);
        return processor;
    }
}

