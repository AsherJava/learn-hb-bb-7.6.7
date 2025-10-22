/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.nr.fielddatacrud.ImpMode;
import java.util.Map;

public interface ActuatorConfig {
    public int getActuatorType();

    public String getDestTable();

    public String getDestPeriod();

    public ImpMode getMode();

    public Map<String, String> getField2DimMap();

    public boolean isSpecifySbId();

    default public boolean isRowByDw() {
        return false;
    }

    default public boolean isBatchByUnit() {
        return false;
    }
}

