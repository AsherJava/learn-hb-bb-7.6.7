/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.EnvDimParam;
import java.util.List;
import java.util.Map;

public interface EnvDimProvider {
    public Map<String, List<String>> getRelationDimValues(EnvDimParam var1);
}

