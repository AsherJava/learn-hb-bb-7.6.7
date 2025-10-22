/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.fielddatacrud.ActuatorConfig
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 */
package com.jiuqi.nr.io.sb;

import com.jiuqi.nr.fielddatacrud.ActuatorConfig;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.io.sb.ImportMode;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import java.util.Map;

public interface SBImportActuatorConfig
extends ActuatorConfig {
    public Map<String, Object> configItems();

    public SBImportActuatorType getImportActuatorType();

    public ImportMode getImportMode();

    default public int getActuatorType() {
        return this.getImportActuatorType().getValue();
    }

    default public Map<String, String> getField2DimMap() {
        return (Map)this.configItems().get("ZB_DIM_MAPPING");
    }

    default public ImpMode getMode() {
        return ImportMode.toImpMode(this.getImportMode());
    }
}

