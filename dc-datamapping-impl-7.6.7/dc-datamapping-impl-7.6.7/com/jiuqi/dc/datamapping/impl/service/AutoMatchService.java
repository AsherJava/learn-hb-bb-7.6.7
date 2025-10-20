/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 */
package com.jiuqi.dc.datamapping.impl.service;

import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import java.util.List;
import java.util.Map;

public interface AutoMatchService {
    public String getCode();

    public String getName();

    public List<DataRefDTO> autoMatch(BaseDataMappingDefineDTO var1, List<DataRefDTO> var2, List<Map<String, Object>> var3, DataRefAutoMatchDTO var4);
}

