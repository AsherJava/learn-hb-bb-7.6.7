/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DimMappingDTO
 */
package com.jiuqi.dc.mappingscheme.impl.define;

import com.jiuqi.dc.mappingscheme.client.dto.DimMappingDTO;
import com.jiuqi.dc.mappingscheme.impl.enums.StorageType;

public interface IStorageFiledMappingSqlBuilder {
    public StorageType getStorageType();

    public String buildSelectSql(String var1, DimMappingDTO var2);

    public String buildJoinSql(String var1, DimMappingDTO var2);

    public String buildGroupSql(String var1, DimMappingDTO var2);
}

