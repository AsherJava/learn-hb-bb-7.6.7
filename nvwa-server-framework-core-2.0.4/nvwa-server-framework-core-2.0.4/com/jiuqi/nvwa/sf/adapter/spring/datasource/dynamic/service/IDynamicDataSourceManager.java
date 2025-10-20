/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceBasicInfo;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceGroup;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaExtDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceCreationException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException;
import java.util.List;
import java.util.Set;

public interface IDynamicDataSourceManager {
    public void addDataSource(String var1, NvwaExtDataSourceProperties var2) throws DataSourceCreationException;

    public void updateDataSource(String var1, NvwaExtDataSourceProperties var2) throws DataSourceCreationException;

    public void deleteDataSource(String var1);

    public Set<String> getDatasourceKeys();

    @Deprecated
    public NvwaDataSourceBasicInfo getDatasourceBasicInfo(String var1) throws DataSourceNotFoundException;

    public boolean containsDataSource(String var1);

    @Deprecated
    public List<NvwaDataSourceBasicInfo> getAllDataSourceBasicInfos();

    public void updateDataSourceTitle(String var1, String var2);

    public void updateDataSourceGroup(String var1, NvwaDataSourceGroup var2);

    public List<NvwaDataSourceInfoDto> listAllDataSourceInfos() throws Exception;

    public NvwaDataSourceInfoDto getDataSourceInfo(String var1) throws DataSourceNotFoundException;
}

