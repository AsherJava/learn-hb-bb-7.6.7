/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.plugin;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.enums.UnitType;

public interface TransformLayerPenetrationPlugin {
    public UnitType getType();

    public String convert(PenetrationParamDTO var1, PenetrationContextInfo var2);
}

