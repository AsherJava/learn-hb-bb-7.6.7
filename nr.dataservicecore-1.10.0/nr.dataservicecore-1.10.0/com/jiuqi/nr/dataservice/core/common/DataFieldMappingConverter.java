/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.common;

import com.jiuqi.nr.dataservice.core.common.DataFieldMap;
import java.util.List;
import java.util.Map;

public interface DataFieldMappingConverter {
    public Map<String, DataFieldMap> getDataFieldMapByTable(String var1, List<String> var2);
}

