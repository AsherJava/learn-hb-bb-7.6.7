/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareDataPrintSchemeDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareDataPrintSchemeDaoImpl
extends AbstractCompareDataDao<CompareDataPrintSchemeDO> {
    @Override
    public Class<CompareDataPrintSchemeDO> getClz() {
        return CompareDataPrintSchemeDO.class;
    }
}

