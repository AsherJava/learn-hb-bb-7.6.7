/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelService;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="RuntimeDataTableRelServiceImpl-NO_CACHE")
public class DataTableRelServiceImpl
implements DataTableRelService {
    @Autowired
    private IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    private final Function<List<DataTableRelDO>, List<DataTableRel>> list2Dto = r -> r.stream().map(Convert::iDtr2Dto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public DataTableRel getBySrcTable(String srcTableKey) {
        return this.iDataTableRelDao.getBySrcTable(srcTableKey);
    }

    @Override
    public List<DataTableRel> getByDesTable(String desTableKey) {
        List<DataTableRelDO> byDesTable = this.iDataTableRelDao.getByDesTable(desTableKey);
        return this.list2Dto.apply(byDesTable);
    }
}

