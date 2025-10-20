/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 */
package com.jiuqi.dc.datamapping.impl.dao;

import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefMappingCacheDTO;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IsolateFieldMappingDao {
    public List<DataRefMappingCacheDTO> loadShareMapping(String var1, String var2);

    public List<DataRefDTO> loadIsolateAllMapping(IsolationStrategy var1, String var2, String var3);

    public Map<String, String> getOdsUnit(String var1, Set<Object> var2);

    public Map<String, String> getOdsUnitByScheme(String var1);

    public List<DataRefMappingCacheDTO> loadOrgMapping(String var1, String var2);

    public List<DataRefMappingCacheDTO> loadAllOrgMapping(String var1);
}

