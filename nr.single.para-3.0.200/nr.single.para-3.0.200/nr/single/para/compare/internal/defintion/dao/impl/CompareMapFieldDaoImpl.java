/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareMapFieldDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareMapFieldDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareMapFieldDaoImpl
extends AbstractCompareMapFieldDao<CompareMapFieldDO> {
    @Override
    public Class<CompareMapFieldDO> getClz() {
        return CompareMapFieldDO.class;
    }
}

