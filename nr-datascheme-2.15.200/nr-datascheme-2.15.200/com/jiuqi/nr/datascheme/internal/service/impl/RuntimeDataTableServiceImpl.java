/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.service.DataTableService;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service(value="RuntimeDataTableServiceImpl-NO_CACHE")
public class RuntimeDataTableServiceImpl
implements DataTableService {
    @Autowired
    protected IDataTableDao<DataTableDO> dataTableDao;
    private final Function<DataTableDO, DataTableDTO> toDto = Convert::iDt2Dto;
    private final Function<List<DataTableDO>, List<DataTableDTO>> list2Dto = r -> r.stream().map(Convert::iDt2Dto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public DataTableDTO getDataTable(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DataTableDO dataTableDO = this.dataTableDao.get(key);
        return this.toDto.apply(dataTableDO);
    }

    @Override
    public DataTableDTO getDataTableByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        return this.toDto.apply(this.dataTableDao.getByCode(code));
    }

    @Override
    public List<DataTableDTO> getLatestDataTableByScheme(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        return this.list2Dto.apply(this.dataTableDao.getLatestDataTableByScheme(scheme));
    }

    @Override
    public Instant getLatestDataTableUpdateTime(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        return this.dataTableDao.getLatestDataTableUpdateTime(scheme);
    }

    @Override
    public List<DataTableDTO> getDataTables(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DataTableDO> dataTable = this.dataTableDao.batchGet(keys);
        return this.list2Dto.apply(dataTable);
    }

    @Override
    public List<DataTableDTO> getAllDataTable(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DataTableDO> byDataScheme = this.dataTableDao.getByDataScheme(scheme);
        return this.list2Dto.apply(byDataScheme);
    }

    @Override
    public List<DataTableDTO> getDataTableByGroup(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        List<DataTableDO> byGroup = this.dataTableDao.getByGroup(parentKey);
        return this.list2Dto.apply(byGroup);
    }

    @Override
    public List<DataTableDTO> getDataTableByScheme(String schemeKey) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        List<DataTableDO> byDataScheme = this.dataTableDao.getByCondition(schemeKey, null);
        return this.list2Dto.apply(byDataScheme);
    }

    @Override
    public List<DataTableDTO> getAllDataTableBySchemeAndTypes(String schemeKey, DataTableType ... types) {
        List<DataTableDTO> dataTables = this.getDataTableByScheme(schemeKey);
        if (null == types || 0 == types.length) {
            return dataTables;
        }
        List<DataTableDO> byDataSchemeAndTypes = this.dataTableDao.getByDataSchemeAndTypes(schemeKey, types);
        return this.list2Dto.apply(byDataSchemeAndTypes);
    }

    @Override
    public List<DataTableDTO> searchBy(String scheme, String keyword, int type) {
        Assert.notNull((Object)keyword, "keyword must not be null.");
        List<DataTableDO> r = this.dataTableDao.searchBy(scheme, keyword, type);
        return this.list2Dto.apply(r);
    }

    @Override
    public List<DataTableDTO> searchBy(List<String> schemes, String keyword, int type) {
        Assert.notNull((Object)keyword, "keyword must not be null.");
        List<DataTableDO> r = this.dataTableDao.searchBy(schemes, keyword, type);
        return this.list2Dto.apply(r);
    }
}

