/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 */
package com.jiuqi.va.query.datasource.dao;

import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import java.util.List;

public interface DataSourceInfoDao {
    public DataSourceInfoVO getDataSourceInfoByCode(String var1);

    public List<DataSourceInfoVO> getDataSources();

    public DataSourceInfoVO getDataSourceInfoByCodeAndNotId(String var1, String var2);

    public void deleteDataSourceByCode(List<String> var1);

    public void save(DataSourceInfoVO var1);

    public void updateById(DataSourceInfoVO var1);
}

