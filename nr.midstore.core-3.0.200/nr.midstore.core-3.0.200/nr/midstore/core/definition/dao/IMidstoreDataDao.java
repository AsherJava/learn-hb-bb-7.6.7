/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.midstore.core.definition.dao;

import java.util.List;
import nr.midstore.core.internal.definition.MidstoreDataDO;
import org.springframework.dao.DataAccessException;

public interface IMidstoreDataDao<DO extends MidstoreDataDO> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public void batchInsert(List<DO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void batchUpdate(List<DO> var1) throws DataAccessException;

    public List<DO> batchGet(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;

    public List<DO> getByParentKey(String var1, String var2) throws DataAccessException;

    public void deleteByParentKey(String var1, String var2) throws DataAccessException;

    public List<DO> getByParentKey(String var1) throws DataAccessException;

    public void deleteByParentKey(String var1) throws DataAccessException;

    public List<DO> getBySchemeKey(String var1) throws DataAccessException;

    public void deleteBySchemeKey(String var1) throws DataAccessException;

    public List<DO> getByCode(String var1) throws DataAccessException;

    public List<DO> getByField(String var1, String var2) throws DataAccessException;
}

