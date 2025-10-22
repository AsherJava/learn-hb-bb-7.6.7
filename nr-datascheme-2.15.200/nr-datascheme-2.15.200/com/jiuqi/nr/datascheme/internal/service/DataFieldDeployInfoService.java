/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import java.util.Collections;
import java.util.List;

public interface DataFieldDeployInfoService {
    public List<DataFieldDeployInfo> getByDataSchemeKey(String var1);

    public List<DataFieldDeployInfo> getByDataTableKey(String var1);

    public List<DataFieldDeployInfo> getByTableModelKey(String var1);

    public List<DataFieldDeployInfo> getByTableName(String var1);

    public List<DataFieldDeployInfo> getByDataFieldKeys(String ... var1);

    public List<DataFieldDeployInfo> getByColumnModelKeys(List<String> var1);

    public DataFieldDeployInfo getByColumnModelKey(String var1);

    public List<String> getTableNames(String ... var1);

    public String getDataTableByTableModel(String var1);

    default public List<DataFieldDeployInfo> getByDataFieldKey(String dataSchemeKey, String dataFieldKey) {
        return this.getByDataFieldKeys(dataFieldKey);
    }

    default public List<DataFieldDeployInfo> getByDataFieldKeys(String dataSchemeKey, List<String> dataFieldKeys) {
        if (null == dataFieldKeys) {
            return Collections.emptyList();
        }
        return this.getByDataFieldKeys(dataFieldKeys.toArray(new String[0]));
    }

    default public DataFieldDeployInfo getByColumnModelKey(String dataSchemeKey, String columnKey) {
        return this.getByColumnModelKey(columnKey);
    }

    default public List<DataFieldDeployInfo> getByColumnModelKeys(String dataSchemeKey, List<String> columnKeys) {
        if (null == columnKeys) {
            return Collections.emptyList();
        }
        return this.getByColumnModelKeys(columnKeys);
    }

    public List<DataFieldDeployInfo> getByTableCode(String var1);
}

