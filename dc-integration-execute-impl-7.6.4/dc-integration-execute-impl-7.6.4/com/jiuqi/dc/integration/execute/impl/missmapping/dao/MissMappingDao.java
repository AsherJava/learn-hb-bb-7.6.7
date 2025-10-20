/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Table
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.dc.integration.execute.impl.missmapping.dao;

import com.google.common.collect.Table;
import com.jiuqi.dc.integration.execute.impl.data.DataConvertDim;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.util.Set;

public interface MissMappingDao {
    public void insertMissMappingData(Table<String, String, Set<String>> var1, DataConvertDim var2, DataSchemeDTO var3, String var4);
}

