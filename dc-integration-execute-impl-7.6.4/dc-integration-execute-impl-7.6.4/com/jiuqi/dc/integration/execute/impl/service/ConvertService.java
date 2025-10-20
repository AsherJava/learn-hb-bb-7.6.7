/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 */
package com.jiuqi.dc.integration.execute.impl.service;

import com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.Map;

public interface ConvertService {
    public Map<String, String> convert(ConvertExecuteDTO var1, Boolean var2);

    public String getSettingTemplate(DataMappingDefineDTO var1);
}

