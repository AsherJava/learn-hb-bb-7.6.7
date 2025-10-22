/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.internal.defintion.dao;

import java.util.List;
import nr.single.para.compare.internal.defintion.CompareInfoDO;
import org.springframework.dao.DataAccessException;

public interface ICompareInfoDao<DO extends CompareInfoDO> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public DO getByCode(String var1) throws DataAccessException;

    public List<DO> getByTask(String var1) throws DataAccessException;

    public List<DO> getByformScheme(String var1) throws DataAccessException;

    public void batchInsert(List<DO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void batchUpdate(List<DO> var1) throws DataAccessException;

    public List<DO> batchGet(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;
}

