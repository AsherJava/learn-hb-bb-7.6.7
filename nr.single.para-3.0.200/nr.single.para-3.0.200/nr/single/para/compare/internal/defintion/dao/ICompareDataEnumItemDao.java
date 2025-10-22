/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.internal.defintion.dao;

import java.util.List;
import nr.single.para.compare.internal.defintion.CompareDataEnumItemDO;
import org.springframework.dao.DataAccessException;

public interface ICompareDataEnumItemDao {
    public List<CompareDataEnumItemDO> getByEnumId(String var1, String var2) throws DataAccessException;

    public void deleteByEnumId(String var1, String var2) throws DataAccessException;
}

