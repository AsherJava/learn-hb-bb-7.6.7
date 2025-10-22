/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.measure;

import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureView;

public interface MeasureService {
    public MeasureView getByMeasure(String var1);

    public MeasureData getByMeasure(String var1, String var2);
}

