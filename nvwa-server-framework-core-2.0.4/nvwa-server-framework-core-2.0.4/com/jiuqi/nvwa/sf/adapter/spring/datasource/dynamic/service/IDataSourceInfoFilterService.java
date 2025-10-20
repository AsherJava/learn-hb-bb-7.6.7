/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DynamicDataSourceException;
import java.util.List;

public interface IDataSourceInfoFilterService {
    public List<NvwaDataSourceInfoDto> doFilter(List<NvwaDataSourceInfoDto> var1) throws DynamicDataSourceException;
}

