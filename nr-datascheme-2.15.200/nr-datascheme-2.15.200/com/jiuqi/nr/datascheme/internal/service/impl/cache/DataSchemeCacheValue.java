/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.Basic
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

class DataSchemeCacheValue
implements Basic,
Serializable {
    private static final long serialVersionUID = 772753143587634881L;
    private DataSchemeDTO dataScheme;
    private List<DataDimDTO> dims;

    public DataSchemeCacheValue(DataSchemeDTO dataScheme, List<DataDimDTO> dims) {
        this.dataScheme = dataScheme;
        this.dims = dims;
    }

    public DataSchemeCacheValue() {
    }

    public DataSchemeDTO getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DataSchemeDTO dataScheme) {
        this.dataScheme = dataScheme;
    }

    public List<DataDimDTO> getDims() {
        return this.dims;
    }

    public void setDims(List<DataDimDTO> dims) {
        this.dims = dims;
    }

    public String getKey() {
        return this.dataScheme.getKey();
    }

    public String getCode() {
        return this.dataScheme.getCode();
    }

    public String getTitle() {
        return this.dataScheme.getTitle();
    }

    public String getDesc() {
        return this.dataScheme.getTitle();
    }

    public Instant getUpdateTime() {
        return this.dataScheme.getUpdateTime();
    }
}

