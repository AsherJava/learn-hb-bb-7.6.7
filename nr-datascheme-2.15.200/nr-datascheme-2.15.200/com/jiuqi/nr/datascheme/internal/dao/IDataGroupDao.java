/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.internal.dao;

import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface IDataGroupDao<DO extends DataGroupDO> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void deleteByDataScheme(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public String[] batchInsert(List<DO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void batchUpdate(List<DO> var1) throws DataAccessException;

    public List<DO> batchGet(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;

    public List<DO> getByKind(int var1) throws DataAccessException;

    public List<DO> getByParent(String var1) throws DataAccessException;

    public List<DO> getByScheme(String var1) throws DataAccessException;

    public List<DO> getByCondition(String var1, String var2) throws DataAccessException;

    public List<DO> getBy(String var1, String var2, String var3) throws DataAccessException;

    public List<DO> searchBy(String var1, String var2, int var3);

    public List<DO> searchBy(List<String> var1, String var2, int var3);
}

