/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto;
import java.util.List;

public interface IDataSourceInfoFilter {
    public List<NvwaDataSourceInfoDto> doFilter(List<NvwaDataSourceInfoDto> var1) throws Exception;

    public boolean supports(String var1);
}

