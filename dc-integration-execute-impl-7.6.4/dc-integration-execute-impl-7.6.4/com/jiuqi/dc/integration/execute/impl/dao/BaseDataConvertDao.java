/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.dc.integration.execute.impl.dao;

import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.util.List;
import java.util.Map;

public interface BaseDataConvertDao {
    public int countUnConvertData(BaseDataMappingDefineDTO var1, String var2);

    public List<Map<String, Object>> selectUnConvertData(BaseDataMappingDefineDTO var1, String var2);

    public Map<String, Map<String, Object>> selectConvertDataByIsolate(DataSchemeDTO var1, BaseDataMappingDefineDTO var2, Map<String, Object> var3, String var4);

    public List<Map<String, Object>> selectIsolationCodes(DataSchemeDTO var1, BaseDataMappingDefineDTO var2, String var3);

    public List<Map<String, Object>> selectOrgData(DataSchemeDTO var1, BaseDataMappingDefineDTO var2);
}

