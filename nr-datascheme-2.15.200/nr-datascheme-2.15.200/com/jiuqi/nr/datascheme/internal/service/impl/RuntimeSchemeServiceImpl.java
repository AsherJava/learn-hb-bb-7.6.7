/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service(value="RuntimeSchemeServiceImpl-NO_CACHE")
public class RuntimeSchemeServiceImpl
implements DataSchemeService {
    @Autowired
    private IDataSchemeDao<DataSchemeDO> dataSchemeDao;
    @Autowired
    private IDataDimDao<DataDimDO> dataDimDao;
    private final Function<DataSchemeDO, DataSchemeDTO> toDto = Convert::iDs2Dto;
    private final Function<List<DataSchemeDO>, List<DataSchemeDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public DataSchemeDTO getDataScheme(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DataSchemeDO dataSchemeDO = this.dataSchemeDao.get(key);
        return this.toDto.apply(dataSchemeDO);
    }

    @Override
    public DataSchemeDTO getDataSchemeByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        return this.toDto.apply(this.dataSchemeDao.getByCode(code));
    }

    @Override
    public List<DataSchemeDTO> getDataSchemeByParent(String parent) {
        if (parent == null) {
            parent = "00000000-0000-0000-0000-000000000000";
        }
        List<DataSchemeDO> byParent = this.dataSchemeDao.getByParent(parent);
        return this.list2Dto.apply(byParent);
    }

    @Override
    public List<DataSchemeDTO> getDataSchemes(List<String> keys) {
        if (null == keys || keys.isEmpty()) {
            return Collections.emptyList();
        }
        List<DataSchemeDO> list = this.dataSchemeDao.batchGet(keys);
        return this.list2Dto.apply(list);
    }

    @Override
    public List<DataSchemeDTO> getAllDataScheme() {
        List<DataSchemeDO> all = this.dataSchemeDao.getAll();
        return this.list2Dto.apply(all);
    }

    @Override
    public List<DataSchemeDTO> searchByKeyword(String keyword) {
        Assert.notNull((Object)keyword, "keyword must not be null.");
        List<DataSchemeDO> dataSchemes = this.dataSchemeDao.searchBy(keyword);
        return this.list2Dto.apply(dataSchemes);
    }

    @Override
    public List<DataDimDTO> getDataSchemeDimension(String dataSchemeKey) {
        List<DataDimDO> byDataScheme = this.dataDimDao.getByDataScheme(dataSchemeKey);
        if (byDataScheme.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<DataDimDTO> dims = new ArrayList<DataDimDTO>(byDataScheme.size());
        for (DataDimDO dataDimDO : byDataScheme) {
            DataDimDTO dim = Convert.iDm2Dto(dataDimDO);
            dims.add(dim);
        }
        return dims;
    }
}

