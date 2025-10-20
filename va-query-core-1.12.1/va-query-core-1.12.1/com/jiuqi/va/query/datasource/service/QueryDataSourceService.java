/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 */
package com.jiuqi.va.query.datasource.service;

import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import java.util.List;

public interface QueryDataSourceService {
    public List<DataSourceInfoVO> getAllDataSources();

    public DataSourceInfoVO getDataSourceInfoByCode(String var1);

    public void updateDataSource(DataSourceInfoVO var1);

    public void deleteDataSourceByCode(List<String> var1);

    public void testDataSource(DataSourceInfoVO var1);

    public String enableTempTable(String var1);
}

