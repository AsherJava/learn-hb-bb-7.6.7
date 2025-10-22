/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareDataEnumDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareDataEnumDaoImpl
extends AbstractCompareDataDao<CompareDataEnumDO> {
    @Override
    public Class<CompareDataEnumDO> getClz() {
        return CompareDataEnumDO.class;
    }
}

