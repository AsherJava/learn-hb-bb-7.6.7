/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.plugin;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.enums.PenetrationType;
import java.util.Map;

public interface ParseLayerPenetrationPlugin {
    public PenetrationType getType();

    public Map<String, String> handle(String var1, PenetrationContextInfo var2);
}

