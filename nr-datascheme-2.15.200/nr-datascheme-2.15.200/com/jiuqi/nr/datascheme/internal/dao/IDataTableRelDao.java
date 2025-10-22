/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao;

import com.jiuqi.nr.datascheme.api.DataTableRel;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDataTableRelDao<D extends DataTableRel> {
    public void insert(D var1) throws DataAccessException;

    public void batchInsert(List<D> var1) throws DataAccessException;

    public void update(D var1) throws DataAccessException;

    public void batchUpdate(List<D> var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void deleteBySrcTable(String var1) throws DataAccessException;

    public void deleteByDesTable(String var1) throws DataAccessException;

    public D getBySrcTable(String var1);

    public List<D> getByDesTable(String var1);

    public List<D> getAll();

    public List<D> getByDataScheme(String var1);
}

