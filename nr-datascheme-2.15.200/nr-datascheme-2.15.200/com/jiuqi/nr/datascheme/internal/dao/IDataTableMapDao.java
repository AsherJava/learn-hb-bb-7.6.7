/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao;

import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDataTableMapDao {
    public String insert(DataTableMapDO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void deleteBySrc(String var1) throws DataAccessException;

    public void deleteByScheme(String var1) throws DataAccessException;

    public DataTableMapDO get(String var1) throws DataAccessException;

    public String[] batchInsert(List<DataTableMapDO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public List<DataTableMapDO> getAll() throws DataAccessException;

    public List<DataTableMapDO> getBySrcType(String var1) throws DataAccessException;

    public List<DataTableMapDO> getBySrcKey(String var1) throws DataAccessException;

    public List<DataTableMapDO> getBySchemeKey(String var1) throws DataAccessException;

    public DataTableMapDO getByTableCode(String var1) throws DataAccessException;
}

