/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 */
package com.jiuqi.dc.datamapping.impl.utils;

import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import com.jiuqi.dc.datamapping.impl.utils.DataRefChkResult;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;

public interface IDataRefChcker {
    public DataRefChkResult check(BaseDataMappingDefineDTO var1, DataRefSaveDTO var2);
}

