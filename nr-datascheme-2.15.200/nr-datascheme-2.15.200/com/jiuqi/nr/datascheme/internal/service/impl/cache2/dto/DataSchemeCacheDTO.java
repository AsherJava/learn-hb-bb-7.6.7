/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto;

import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.BasicCacheDTO;
import java.util.List;

public class DataSchemeCacheDTO
implements BasicCacheDTO {
    private final DataSchemeDTO dataScheme;
    private final List<DataDimDTO> dataDimensions;

    public DataSchemeCacheDTO(DataSchemeDTO dataScheme, List<DataDimDTO> dataDimensions) {
        this.dataScheme = dataScheme;
        this.dataDimensions = dataDimensions;
    }

    public DataSchemeDTO getDataScheme() {
        return this.dataScheme;
    }

    public List<DataDimDTO> getDataDimensions() {
        return this.dataDimensions;
    }

    @Override
    public String getKey() {
        return this.dataScheme.getKey();
    }

    @Override
    public String getCode() {
        return this.dataScheme.getCode();
    }
}

