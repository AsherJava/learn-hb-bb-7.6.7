/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import java.util.List;
import nr.single.para.compare.internal.defintion.CompareDataFormulaFormDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class CompareDataFormulaFormDaoImpl
extends AbstractCompareDataDao<CompareDataFormulaFormDO> {
    @Override
    public Class<CompareDataFormulaFormDO> getClz() {
        return CompareDataFormulaFormDO.class;
    }

    @Override
    public List<CompareDataFormulaFormDO> getByParentInInfo(String infoKey, String parentKey) throws DataAccessException {
        Assert.notNull((Object)infoKey, "infoKey must not be null.");
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        return super.list(new String[]{"CD_INFOKEY", "CD_SCHEME_COMPAREKEY"}, new Object[]{infoKey, parentKey}, this.getClz());
    }
}

