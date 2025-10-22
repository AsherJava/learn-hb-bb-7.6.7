/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareDataDaoImpl
extends AbstractCompareDataDao<CompareDataDO> {
    @Override
    public Class<CompareDataDO> getClz() {
        return CompareDataDO.class;
    }
}

