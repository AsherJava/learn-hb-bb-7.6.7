/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataTableRel
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DesignDataTableRel;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.dto.DataTableRelDesignDTO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableRelDO;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelDesignService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataTableRelDesignServiceImpl
implements DataTableRelDesignService {
    @Autowired
    private IDataTableRelDao<DesignDataTableRelDO> iDataTableRelDao;
    private final Function<List<DataTableRelDesignDTO>, List<DesignDataTableRelDO>> list2Do = r -> r.stream().map(Convert::iDtr2Do).filter(Objects::nonNull).collect(Collectors.toList());
    private final Function<List<DesignDataTableRelDO>, List<DesignDataTableRel>> list2Dto = r -> r.stream().map(Convert::iDtr2Dto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public String insertDataTableRel(DataTableRelDesignDTO dataTableRel) {
        DesignDataTableRelDO iDtr2Do = Convert.iDtr2Do(dataTableRel);
        this.iDataTableRelDao.insert(iDtr2Do);
        return iDtr2Do.getKey();
    }

    @Override
    public List<String> insertDataTableRels(List<DataTableRelDesignDTO> dataTableRels) {
        if (null == dataTableRels || dataTableRels.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<DesignDataTableRelDO> dos = new ArrayList<DesignDataTableRelDO>();
        for (int i = 0; i < dataTableRels.size(); ++i) {
            DataTableRelDesignDTO dataTableRel = dataTableRels.get(i);
            dos.add(Convert.iDtr2Do(dataTableRel));
            keys.add(dataTableRel.getKey());
        }
        return keys;
    }

    @Override
    public void updateDataTableRel(DataTableRelDesignDTO dataTableRel) {
        DesignDataTableRelDO iDtr2Do = Convert.iDtr2Do(dataTableRel);
        this.iDataTableRelDao.update(iDtr2Do);
    }

    @Override
    public void updateDataTableRels(List<DataTableRelDesignDTO> dataTableRels) {
        if (null == dataTableRels || dataTableRels.isEmpty()) {
            return;
        }
        this.iDataTableRelDao.batchUpdate(this.list2Do.apply(dataTableRels));
    }

    @Override
    public void deleteDataTableRel(String key) {
        this.iDataTableRelDao.delete(key);
    }

    @Override
    public void deleteDataTableRels(List<String> keys) {
        if (null == keys || keys.isEmpty()) {
            return;
        }
        this.iDataTableRelDao.batchDelete(keys);
    }

    @Override
    public void deleteBySrcTable(String srcTableKey) {
        this.iDataTableRelDao.deleteBySrcTable(srcTableKey);
    }

    @Override
    public void deleteByDesTable(String desTableKey) {
        this.iDataTableRelDao.deleteByDesTable(desTableKey);
    }

    @Override
    public DesignDataTableRel getBySrcTable(String srcTableKey) {
        return this.iDataTableRelDao.getBySrcTable(srcTableKey);
    }

    @Override
    public List<DesignDataTableRel> getByDesTable(String desTableKey) {
        List<DesignDataTableRelDO> byDesTable = this.iDataTableRelDao.getByDesTable(desTableKey);
        return this.list2Dto.apply(byDesTable);
    }
}

