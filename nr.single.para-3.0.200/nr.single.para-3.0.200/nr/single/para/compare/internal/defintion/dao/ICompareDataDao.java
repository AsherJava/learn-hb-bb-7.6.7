/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.internal.defintion.dao;

import java.util.List;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import org.springframework.dao.DataAccessException;

public interface ICompareDataDao<DO extends CompareDataDO> {
    public String insert(DO var1) throws DataAccessException;

    public void delete(String var1) throws DataAccessException;

    public void deleteByInfoKey(String var1) throws DataAccessException;

    public void update(DO var1) throws DataAccessException;

    public DO get(String var1) throws DataAccessException;

    public List<DO> getByInfoKey(String var1) throws DataAccessException;

    public List<DO> getByDataTypeInInfo(String var1, CompareDataType var2) throws DataAccessException;

    public List<DO> getByParentInInfo(String var1, String var2) throws DataAccessException;

    public void batchInsert(List<DO> var1) throws DataAccessException;

    public void batchDelete(List<String> var1) throws DataAccessException;

    public void batchUpdate(List<DO> var1) throws DataAccessException;

    public List<DO> batchGet(List<String> var1) throws DataAccessException;

    public List<DO> getAll() throws DataAccessException;
}

