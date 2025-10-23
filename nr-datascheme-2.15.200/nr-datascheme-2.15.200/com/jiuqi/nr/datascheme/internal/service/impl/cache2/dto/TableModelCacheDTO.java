/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto;

import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.BasicCacheDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableModelCacheDTO
implements BasicCacheDTO {
    private final String key;
    private final String name;
    private final List<DataFieldDeployInfo> deployInfos;

    public TableModelCacheDTO(String key, String name) {
        this.key = key;
        this.name = name;
        this.deployInfos = new ArrayList<DataFieldDeployInfo>();
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getCode() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }

    public List<DataFieldDeployInfo> getDeployInfos() {
        return this.deployInfos;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        TableModelCacheDTO that = (TableModelCacheDTO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return Objects.hashCode(this.key);
    }
}

