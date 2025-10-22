/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.IDataTableMapDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class DataTableMapDaoImpl
extends BaseDao
implements IDataTableMapDao {
    public Class<DataTableMapDO> getClz() {
        return DataTableMapDO.class;
    }

    @Override
    public String insert(DataTableMapDO dataTableMapDO) throws DataAccessException {
        Assert.notNull((Object)dataTableMapDO, "dataTableMapDO must not be null.");
        super.insert(dataTableMapDO);
        return dataTableMapDO.getTableKey();
    }

    @Override
    public void delete(String tableKey) throws DataAccessException {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        super.delete(tableKey);
    }

    @Override
    public void deleteBySrc(String srcKey) throws DataAccessException {
        Assert.notNull((Object)srcKey, "srcKey must not be null.");
        super.deleteBy(new String[]{"MAP_SRC_KEY"}, new Object[]{srcKey});
    }

    @Override
    public void deleteByScheme(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        super.deleteBy(new String[]{"MAP_SCHEME_KEY"}, new Object[]{schemeKey});
    }

    @Override
    public DataTableMapDO get(String tableKey) throws DataAccessException {
        Assert.notNull((Object)tableKey, "key must not be null.");
        return super.getByKey(tableKey, this.getClz());
    }

    @Override
    public String[] batchInsert(List<DataTableMapDO> dataTableMapDOs) throws DataAccessException {
        Assert.notNull(dataTableMapDOs, "dataTableMapDOs must not be null.");
        if (dataTableMapDOs.isEmpty()) {
            return new String[0];
        }
        String[] keys = new String[dataTableMapDOs.size()];
        for (int i = 0; i < dataTableMapDOs.size(); ++i) {
            DataTableMapDO dataTableMapDO = dataTableMapDOs.get(i);
            keys[i] = dataTableMapDO.getTableKey();
        }
        if (dataTableMapDOs.isEmpty()) {
            return new String[0];
        }
        super.insert(dataTableMapDOs.toArray());
        return keys;
    }

    @Override
    public void batchDelete(List<String> tableKeys) throws DataAccessException {
        Assert.notNull(tableKeys, "tableCodes must not be null.");
        for (String key : tableKeys) {
            Assert.notNull((Object)key, "Collection should not contain null.");
        }
        super.delete(tableKeys.toArray());
    }

    @Override
    public List<DataTableMapDO> getAll() throws DataAccessException {
        return super.list(this.getClz());
    }

    @Override
    public List<DataTableMapDO> getBySrcType(String srcType) throws DataAccessException {
        Assert.notNull((Object)srcType, "srcType must not be null.");
        return super.list(new String[]{"MAP_SRC_TYPE"}, (Object[])new String[]{srcType}, this.getClz());
    }

    @Override
    public List<DataTableMapDO> getBySrcKey(String srcKey) throws DataAccessException {
        Assert.notNull((Object)srcKey, "srcKey must not be null.");
        return super.list(new String[]{"MAP_SRC_KEY"}, (Object[])new String[]{srcKey}, this.getClz());
    }

    @Override
    public List<DataTableMapDO> getBySchemeKey(String schemeKey) throws DataAccessException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        return super.list(new String[]{"MAP_SCHEME_KEY"}, (Object[])new String[]{schemeKey}, this.getClz());
    }

    @Override
    public DataTableMapDO getByTableCode(String tableCode) throws DataAccessException {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        List<DataTableMapDO> list = super.list(new String[]{"MAP_TABLE_CODE"}, (Object[])new String[]{tableCode}, this.getClz());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}

