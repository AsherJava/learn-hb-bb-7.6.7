/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao;

import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDataSchemeDao<DO extends DataSchemeDO> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public DO getByCode(String var1) throws DataAccessException;

    public void batchInsert(List<DO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void batchUpdate(List<DO> var1) throws DataAccessException;

    public List<DO> batchGet(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;

    public List<DO> getByParent(String var1) throws DataAccessException;

    public DO getByPrefix(String var1) throws DataAccessException;

    public List<DO> getBy0(String var1, String var2) throws DataAccessException;

    public List<DO> getBy(String var1, String var2) throws DataAccessException;

    public DO getByBizCode(String var1) throws DataAccessException;

    public List<DO> searchBy(String var1);
}

