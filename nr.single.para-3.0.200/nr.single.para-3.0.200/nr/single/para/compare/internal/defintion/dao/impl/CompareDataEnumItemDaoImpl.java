/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import java.util.List;
import nr.single.para.compare.internal.defintion.CompareDataEnumItemDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataEnumItemDao;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class CompareDataEnumItemDaoImpl
extends AbstractCompareDataDao<CompareDataEnumItemDO>
implements ICompareDataEnumItemDao {
    @Override
    public Class<CompareDataEnumItemDO> getClz() {
        return CompareDataEnumItemDO.class;
    }

    @Override
    public List<CompareDataEnumItemDO> getByEnumId(String infoKey, String enumId) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)enumId, "enumId must not be null.");
        return super.list(new String[]{"CD_INFOKEY", "CD_ENUM_COMPAREKEY"}, new Object[]{infoKey, enumId}, this.getClz());
    }

    @Override
    public List<CompareDataEnumItemDO> getByParentInInfo(String infoKey, String parentKey) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        return super.list(new String[]{"CD_INFOKEY", "CD_ENUM_COMPAREKEY"}, new Object[]{infoKey, parentKey}, this.getClz());
    }

    @Override
    public void deleteByEnumId(String infoKey, String enumId) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)enumId, "enumId enumId not be null.");
        super.deleteBy(new String[]{"CD_INFOKEY", "CD_ENUM_COMPAREKEY"}, new Object[]{infoKey, enumId});
    }
}

