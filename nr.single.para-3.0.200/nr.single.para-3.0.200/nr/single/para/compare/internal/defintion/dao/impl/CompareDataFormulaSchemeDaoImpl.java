/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareDataFormulaSchemeDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareDataFormulaSchemeDaoImpl
extends AbstractCompareDataDao<CompareDataFormulaSchemeDO> {
    @Override
    public Class<CompareDataFormulaSchemeDO> getClz() {
        return CompareDataFormulaSchemeDO.class;
    }
}

