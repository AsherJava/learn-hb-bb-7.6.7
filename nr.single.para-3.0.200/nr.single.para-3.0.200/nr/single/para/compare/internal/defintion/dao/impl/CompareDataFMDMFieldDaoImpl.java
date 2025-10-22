/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareDataFMDMFieldDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareDataFMDMFieldDaoImpl
extends AbstractCompareDataDao<CompareDataFMDMFieldDO> {
    @Override
    public Class<CompareDataFMDMFieldDO> getClz() {
        return CompareDataFMDMFieldDO.class;
    }
}

