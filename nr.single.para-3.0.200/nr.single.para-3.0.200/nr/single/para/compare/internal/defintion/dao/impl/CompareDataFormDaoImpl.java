/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareDataFormDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareDataFormDaoImpl
extends AbstractCompareDataDao<CompareDataFormDO> {
    @Override
    public Class<CompareDataFormDO> getClz() {
        return CompareDataFormDO.class;
    }
}

