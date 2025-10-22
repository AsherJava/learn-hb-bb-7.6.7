/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import java.util.List;

public interface SchemeTargetDim {
    public TargetDimType getTargetDimType();

    public String getDimValue();

    public List<CustomCalibreRow> getCustomCalibreRows();

    public boolean isValidTargetDim();
}

