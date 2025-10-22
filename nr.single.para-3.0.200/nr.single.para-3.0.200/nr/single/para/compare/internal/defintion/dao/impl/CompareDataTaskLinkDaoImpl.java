/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.internal.defintion.dao.impl;

import nr.single.para.compare.internal.defintion.CompareDataTaskLinkDO;
import nr.single.para.compare.internal.defintion.dao.impl.AbstractCompareDataDao;
import org.springframework.stereotype.Repository;

@Repository
public class CompareDataTaskLinkDaoImpl
extends AbstractCompareDataDao<CompareDataTaskLinkDO> {
    @Override
    public Class<CompareDataTaskLinkDO> getClz() {
        return CompareDataTaskLinkDO.class;
    }
}

