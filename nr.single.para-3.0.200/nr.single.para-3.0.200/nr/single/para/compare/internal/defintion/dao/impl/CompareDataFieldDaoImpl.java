/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import java.util.List;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.CompareDataFieldDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataFieldDao;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class CompareDataFieldDaoImpl
extends AbstractCompareDataDao<CompareDataFieldDO>
implements ICompareDataFieldDao {
    @Override
    public Class<CompareDataFieldDO> getClz() {
        return CompareDataFieldDO.class;
    }

    @Override
    public List<CompareDataFieldDO> getByParentInInfo(String infoKey, String parentKey) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        return super.list(new String[]{"CD_INFOKEY", "CD_FORM_COMPAREKEY"}, new Object[]{infoKey, parentKey}, this.getClz());
    }

    @Override
    public List<CompareDataFieldDO> getByFloatingId(String infoKey, String formCompareId, Integer floatingId) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)floatingId, "parentKey must not be null.");
        return super.list(new String[]{"CD_INFOKEY", "CD_FORM_COMPAREKEY", "CD_SINGLE_FLOATINGID"}, new Object[]{infoKey, formCompareId, floatingId}, this.getClz());
    }

    @Override
    public List<CompareDataFieldDO> getByUpdateType(String infoKey, CompareUpdateType updateType) {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)updateType, "updateType must not be null.");
        return super.list(new String[]{"CD_INFOKEY", "CD_UPDATETYPE"}, new Object[]{infoKey, updateType}, this.getClz());
    }

    @Override
    public List<CompareDataFieldDO> getByNetCode(String infoKey, String netCode) {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)netCode, "netCode must not be null.");
        return super.list(new String[]{"CD_INFOKEY", "CD_NETCODE"}, new Object[]{infoKey, netCode}, this.getClz());
    }
}

