/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.impl;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DynamicDataSourceException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDataSourceInfoFilter;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDataSourceInfoFilterService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class DataSourceInfoInfoFilterServiceImpl
implements IDataSourceInfoFilterService {
    private final List<IDataSourceInfoFilter> dataSourceInfoFilterList;

    public DataSourceInfoInfoFilterServiceImpl(@Autowired List<IDataSourceInfoFilter> dataSourceInfoFilterList) {
        this.dataSourceInfoFilterList = dataSourceInfoFilterList;
    }

    @Override
    public List<NvwaDataSourceInfoDto> doFilter(List<NvwaDataSourceInfoDto> dataSourceInfos) throws DynamicDataSourceException {
        if (ObjectUtils.isEmpty(dataSourceInfos)) {
            return new ArrayList<NvwaDataSourceInfoDto>();
        }
        for (NvwaDataSourceInfoDto dtaSourceInfoDto : dataSourceInfos) {
            if (StringUtils.hasLength(dtaSourceInfoDto.getCategory())) continue;
            dtaSourceInfoDto.setCategory("");
        }
        Map<String, List<NvwaDataSourceInfoDto>> groupedMap = dataSourceInfos.stream().collect(Collectors.groupingBy(NvwaDataSourceInfoDto::getCategory));
        ArrayList<NvwaDataSourceInfoDto> filteredInfoList = new ArrayList<NvwaDataSourceInfoDto>();
        for (Map.Entry<String, List<NvwaDataSourceInfoDto>> entry : groupedMap.entrySet()) {
            if (!StringUtils.hasLength(entry.getKey())) continue;
            boolean matched = false;
            for (IDataSourceInfoFilter filter : this.dataSourceInfoFilterList) {
                if (!filter.supports(entry.getKey())) continue;
                try {
                    matched = true;
                    filteredInfoList.addAll(filter.doFilter(entry.getValue()));
                    break;
                }
                catch (Exception e) {
                    throw new DynamicDataSourceException("\u6570\u636e\u6e90\u8fc7\u6ee4\u5931\u8d25", e);
                }
            }
            if (matched) continue;
            filteredInfoList.addAll((Collection<NvwaDataSourceInfoDto>)entry.getValue());
        }
        return filteredInfoList;
    }
}

