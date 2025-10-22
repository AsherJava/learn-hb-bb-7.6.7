/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

public abstract class AbstractDataTableRelDao<D extends DataTableRel>
extends BaseDao
implements IDataTableRelDao<D> {
    public abstract Class<D> getClz();

    @Override
    public void insert(D dataTableRel) throws DataAccessException {
        Assert.notNull(dataTableRel, "dataTableRel must not be null.");
        super.insert(dataTableRel);
    }

    @Override
    public void batchInsert(List<D> dataTableRels) throws DataAccessException {
        Assert.notNull(dataTableRels, "dataTableRels must not be null.");
        super.insert(dataTableRels.toArray());
    }

    @Override
    public void update(D dataTableRel) throws DataAccessException {
        Assert.notNull(dataTableRel, "dataTableRel must not be null.");
        super.update(dataTableRel);
    }

    @Override
    public void batchUpdate(List<D> dataTableRels) throws DataAccessException {
        Assert.notNull(dataTableRels, "dataTableRels must not be null.");
        super.update(dataTableRels.toArray());
    }

    @Override
    public void delete(String key) throws DataAccessException {
        Assert.notNull((Object)key, "key must not be null.");
        super.delete(key);
    }

    @Override
    public void batchDelete(List<String> keys) throws DataAccessException {
        Assert.notNull(keys, "keys must not be null.");
        super.delete(keys.toArray());
    }

    @Override
    public void deleteBySrcTable(String srcTableKey) throws DataAccessException {
        Assert.notNull((Object)srcTableKey, "srcTableKey must not be null.");
        super.deleteBy(new String[]{"DTR_SRCTABLE_KEY"}, new Object[]{srcTableKey});
    }

    @Override
    public void deleteByDesTable(String desTableKey) throws DataAccessException {
        Assert.notNull((Object)desTableKey, "desTableKey must not be null.");
        super.deleteBy(new String[]{"DTR_DESTTABLE_KEY"}, new Object[]{desTableKey});
    }

    @Override
    public D getBySrcTable(String srcTableKey) {
        Assert.notNull((Object)srcTableKey, "srcTableKey must not be null.");
        return (D)((DataTableRel)super.getBy(String.format(" %s=? ", "DTR_SRCTABLE_KEY"), new Object[]{srcTableKey}, this.getClz()));
    }

    @Override
    public List<D> getByDesTable(String desTableKey) {
        Assert.notNull((Object)desTableKey, "desTableKey must not be null.");
        return super.list(new String[]{"DTR_DESTTABLE_KEY"}, new Object[]{desTableKey}, this.getClz());
    }

    @Override
    public List<D> getAll() {
        return super.list(this.getClz());
    }

    @Override
    public List<D> getByDataScheme(String dataSchemeKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        return super.list(new String[]{"DTR_DS_KEY"}, new Object[]{dataSchemeKey}, this.getClz());
    }
}

