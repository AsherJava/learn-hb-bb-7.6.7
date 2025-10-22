/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.plugin;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import java.util.Map;

public interface StandardizationLayerPenetrationPlugin {
    public PenetrationParamDTO convert(Map<String, String> var1, PenetrationContextInfo var2);
}

