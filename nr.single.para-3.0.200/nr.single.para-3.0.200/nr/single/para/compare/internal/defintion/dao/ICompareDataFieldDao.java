/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.internal.defintion.dao;

import java.util.List;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.CompareDataFieldDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataDao;
import org.springframework.dao.DataAccessException;

public interface ICompareDataFieldDao
extends ICompareDataDao<CompareDataFieldDO> {
    public List<CompareDataFieldDO> getByFloatingId(String var1, String var2, Integer var3) throws DataAccessException;

    public List<CompareDataFieldDO> getByUpdateType(String var1, CompareUpdateType var2);

    public List<CompareDataFieldDO> getByNetCode(String var1, String var2);
}

