/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 */
package com.jiuqi.dc.datamapping.impl.service;

import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FieldMappingBizDataCacheProvider {
    public IsolationStrategy getIsolateType();

    public boolean hasRef(IsolationParamContext var1, String var2);

    public Map<String, DataRefMappingCacheDTO> getCache(IsolationParamContext var1, String var2);

    public List<DataRefDTO> getBaseMappingCache(IsolationParamContext var1, String var2);

    public void loadCache(String var1, String var2);

    public void syncCache(List<IsolationParamContext> var1, String var2, String var3);

    public Set<String> getUnitCodeRange(IsolationParamContext var1, String var2, List<DataRefDTO> var3);
}

