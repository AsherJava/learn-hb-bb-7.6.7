/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.internal.defintion.dao;

import java.util.List;
import nr.single.para.compare.internal.defintion.CompareMapFieldDO;
import org.springframework.dao.DataAccessException;

public interface ICompareMapFieldDao<DO extends CompareMapFieldDO> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public DO getByFieldKey(String var1) throws DataAccessException;

    public List<DO> getByDataSchemeKey(String var1) throws DataAccessException;

    public List<DO> getByTitleInDataScheme(String var1, String var2) throws DataAccessException;

    public void batchInsert(List<DO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void batchUpdate(List<DO> var1) throws DataAccessException;

    public List<DO> batchGet(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;
}

