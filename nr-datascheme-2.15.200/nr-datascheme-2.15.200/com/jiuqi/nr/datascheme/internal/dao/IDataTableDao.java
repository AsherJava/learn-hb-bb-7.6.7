/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import java.time.Instant;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDataTableDao<DO extends DataTableDO> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void deleteByDataScheme(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public DO getByCode(String var1);

    public String[] batchInsert(List<DO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void batchUpdate(List<DO> var1) throws DataAccessException;

    public List<DO> batchGet(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;

    public List<DO> getByGroup(String var1);

    public List<DO> getByDataScheme(String var1) throws DataAccessException;

    public List<DO> getByDataSchemeAndTypes(String var1, DataTableType ... var2) throws DataAccessException;

    public List<DO> getByCondition(String var1, String var2) throws DataAccessException;

    public List<DO> searchBy(String var1, String var2, int var3);

    public List<DO> searchBy(List<String> var1, String var2, int var3);

    public List<DO> getBy(String var1, String var2, String var3);

    public void deleteByDataSchemeAndType(String var1, int var2);

    public List<DO> getLatestDataTableByScheme(String var1);

    public Instant getLatestDataTableUpdateTime(String var1);

    public void refreshUpdateTime(String var1);
}

