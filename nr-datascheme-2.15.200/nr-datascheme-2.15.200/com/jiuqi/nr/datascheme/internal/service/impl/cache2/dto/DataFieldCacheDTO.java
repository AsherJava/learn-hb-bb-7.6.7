/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto;

import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.BasicCacheDTO;
import java.util.ArrayList;
import java.util.List;

public class DataFieldCacheDTO
implements BasicCacheDTO {
    private final DataFieldDTO dataField;
    private final List<DataFieldDeployInfo> deployInfos;

    public DataFieldCacheDTO(DataFieldDTO dataField) {
        this.dataField = dataField;
        this.deployInfos = new ArrayList<DataFieldDeployInfo>();
    }

    public DataFieldDTO getDataField() {
        return this.dataField;
    }

    public List<DataFieldDeployInfo> getDeployInfos() {
        return this.deployInfos;
    }

    @Override
    public String getKey() {
        return this.dataField.getKey();
    }

    @Override
    public String getCode() {
        return this.dataField.getCode();
    }

    public int hashCode() {
        return this.dataField.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        DataFieldCacheDTO that = (DataFieldCacheDTO)obj;
        return this.dataField.equals(that.dataField);
    }
}

