/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto;

import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.DataFieldCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.TableModelCacheDTO;
import java.time.Instant;
import java.util.Objects;

public class ColumnModelCacheDTO
implements DataFieldDeployInfo {
    private final String key;
    private final String name;
    private final TableModelCacheDTO tableModel;
    private final DataFieldCacheDTO dataField;

    public ColumnModelCacheDTO(TableModelCacheDTO tableModel, DataFieldCacheDTO dataField, DataFieldDeployInfo deployInfo) {
        this.key = deployInfo.getColumnModelKey();
        this.name = deployInfo.getFieldName();
        this.tableModel = tableModel;
        this.dataField = dataField;
        this.tableModel.getDeployInfos().add(this);
        this.dataField.getDeployInfos().add(this);
    }

    public String getDataSchemeKey() {
        return this.dataField.getDataField().getDataSchemeKey();
    }

    public String getDataTableKey() {
        return this.dataField.getDataField().getDataTableKey();
    }

    public String getSourceTableKey() {
        return this.getDataTableKey();
    }

    public String getDataFieldKey() {
        return this.dataField.getKey();
    }

    public String getTableModelKey() {
        return this.tableModel.getKey();
    }

    public String getColumnModelKey() {
        return this.key;
    }

    public String getFieldName() {
        return this.name;
    }

    public String getTableName() {
        return this.tableModel.getName();
    }

    public String getVersion() {
        return this.dataField.getDataField().getVersion();
    }

    public Instant getUpdateTime() {
        return this.dataField.getDataField().getUpdateTime();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ColumnModelCacheDTO that = (ColumnModelCacheDTO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return Objects.hashCode(this.key);
    }
}

