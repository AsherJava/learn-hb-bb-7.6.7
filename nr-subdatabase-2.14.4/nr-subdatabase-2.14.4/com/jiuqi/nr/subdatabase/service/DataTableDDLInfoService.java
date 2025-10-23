/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.subdatabase.service;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.subdatabase.dao.DataTableDDLInfoDao;
import com.jiuqi.nr.subdatabase.facade.DataTableDDLInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataTableDDLInfoService {
    @Autowired
    private DataTableDDLInfoDao dataTableDDLInfoDao;

    public void insert(DataTableDDLInfo dataTableDDLInfo) throws DBParaException {
        this.dataTableDDLInfoDao.insert(dataTableDDLInfo);
    }

    public void update(DataTableDDLInfo dataTableDDLInfo) throws DBParaException {
        this.dataTableDDLInfoDao.update(dataTableDDLInfo);
    }

    public DataTableDDLInfo queryInfoBySKAndTMK(String dataSchemeKey, String tableModelKey) {
        return this.dataTableDDLInfoDao.queryInfoBySKAndTMK(dataSchemeKey, tableModelKey);
    }

    public List<DataTableDDLInfo> queryInfoBySchemeKey(String dataSchemeKey) {
        return this.dataTableDDLInfoDao.queryInfoBySchemeKey(dataSchemeKey);
    }
}

