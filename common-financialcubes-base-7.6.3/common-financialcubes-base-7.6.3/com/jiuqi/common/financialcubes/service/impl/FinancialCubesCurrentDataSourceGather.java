/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.service.impl;

import com.jiuqi.common.financialcubes.service.FinancialCubesCurrentDataSourceInfo;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesCurrentDataSourceGather
implements InitializingBean {
    private final Map<String, FinancialCubesCurrentDataSourceInfo> currentDataSourceInfoMap = new ConcurrentHashMap<String, FinancialCubesCurrentDataSourceInfo>();
    @Autowired(required=false)
    private List<FinancialCubesCurrentDataSourceInfo> currentDataSourceCodeList;

    @Override
    public void afterPropertiesSet() {
        if (this.currentDataSourceCodeList == null || this.currentDataSourceCodeList.size() == 0) {
            return;
        }
        this.currentDataSourceInfoMap.clear();
        for (FinancialCubesCurrentDataSourceInfo service : this.currentDataSourceCodeList) {
            this.currentDataSourceInfoMap.put(service.dataSourceCode(), service);
        }
    }

    public boolean containsDataSourceCode(String dataSourceCode) {
        return this.currentDataSourceInfoMap.keySet().contains(dataSourceCode);
    }
}

