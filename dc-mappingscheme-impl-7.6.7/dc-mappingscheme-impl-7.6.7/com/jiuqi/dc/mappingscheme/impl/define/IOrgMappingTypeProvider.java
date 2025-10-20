/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO
 */
package com.jiuqi.dc.mappingscheme.impl.define;

import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.OrgMappingTypeDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import java.util.List;

public interface IOrgMappingTypeProvider {
    default public IPluginType getPluginType() {
        return null;
    }

    public List<OrgMappingTypeDTO> listMappingType(DataSchemeDTO var1);
}

