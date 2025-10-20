/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDTO
 */
package com.jiuqi.dc.mappingscheme.impl.define;

import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDTO;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import java.util.Collections;
import java.util.List;

public interface IFieldMappingProvider {
    default public IPluginType getPluginType() {
        return null;
    }

    public String getCode();

    public String getName();

    public String getEffectTable();

    @Deprecated
    default public List<FieldMappingDTO> listFixedFieldMapping(String dataSchemeCode) {
        return Collections.emptyList();
    }

    public List<FieldDTO> listOdsField(DataSchemeDTO var1);

    public Integer showOrder();
}

