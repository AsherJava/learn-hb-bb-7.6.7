/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.nr.datacrud.spi.IDwClearChangeDataListener;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDwDataClearLis
implements IDwClearChangeDataListener {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected IRuntimeDataSchemeService runtimeDataSchemeService;

    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    @Override
    public String name() {
        return "\u6570\u636e\u8868\u6570\u636e";
    }

    @Override
    public void dataClear(String dataSchemeKey, String entityKeyData, String startPeriod, String endPeriod) {
        this.dataClear(dataSchemeKey, Collections.singletonList(entityKeyData), startPeriod, endPeriod);
    }

    @Override
    public void dataClear(String dataSchemeKey, List<String> entityKeyDataList, String startPeriod, String endPeriod) {
        String entityKeyStr = String.join((CharSequence)",", entityKeyDataList);
        this.LOGGER.info("\u5f00\u59cb\u6e05\u9664\u5355\u4f4d{}\u7684\u62a5\u8868\u6570\u636e", (Object)entityKeyStr);
        Map<String, Set<String>> tableKey2TableName = this.getTableKey2TableName(dataSchemeKey);
        this.LOGGER.info("\u627e\u5230\u5355\u4f4d{}{}\u62a5\u8868\u6570\u636e", (Object)entityKeyStr, (Object)tableKey2TableName.size());
        for (Map.Entry<String, Set<String>> tableEntry : tableKey2TableName.entrySet()) {
            DataTable dataTable;
            String tableKey = tableEntry.getKey();
            Set<String> tableNames = tableEntry.getValue();
            boolean accountTable = false;
            if (tableNames.size() > 1 && (dataTable = this.runtimeDataSchemeService.getDataTable(tableKey)) != null) {
                DataTableType dataTableType = dataTable.getDataTableType();
                boolean bl = accountTable = DataTableType.ACCOUNT == dataTableType;
            }
            if (accountTable) {
                this.LOGGER.info("\u6e05\u9664\u5355\u4f4d{}\u7684\u62a5\u8868\u6570\u636e,\u53f0\u8d26\u8868\u6682\u4e0d\u652f\u6301\u5220\u9664", (Object)entityKeyStr);
                continue;
            }
            this.doClear(tableNames, entityKeyDataList, startPeriod, endPeriod);
        }
        this.LOGGER.info("\u6e05\u9664\u5355\u4f4d{}\u7684\u62a5\u8868\u6570\u636e\u7ed3\u675f", (Object)entityKeyStr);
    }

    protected abstract void doClear(Set<String> var1, List<String> var2, String var3, String var4);

    protected Map<String, Set<String>> getTableKey2TableName(String dataSchemeKey) {
        List deployInfoBySchemeKey = this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey);
        HashMap<String, Set<String>> tableKey2TableName = new HashMap<String, Set<String>>();
        for (DataFieldDeployInfo dataFieldDeployInfo : deployInfoBySchemeKey) {
            Set tableNames = tableKey2TableName.computeIfAbsent(dataFieldDeployInfo.getDataTableKey(), key -> new HashSet());
            tableNames.add(dataFieldDeployInfo.getTableName());
        }
        return tableKey2TableName;
    }
}

