/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareInfoDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareInfoDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareInfoDaoImpl
extends AbstractCompareInfoDao<CompareInfoDO> {
    @Override
    public Class<CompareInfoDO> getClz() {
        return CompareInfoDO.class;
    }
}

