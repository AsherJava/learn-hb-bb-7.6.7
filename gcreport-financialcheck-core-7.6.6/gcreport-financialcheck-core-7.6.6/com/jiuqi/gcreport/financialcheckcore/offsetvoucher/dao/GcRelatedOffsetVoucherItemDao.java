/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.offsetvoucher.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import java.util.Collection;
import java.util.List;

public interface GcRelatedOffsetVoucherItemDao
extends IBaseSqlGenericDAO<GcRelatedOffsetVoucherItemEO> {
    public void deleteByCheckIdsAndOffsetPeriod(Collection<String> var1, Integer var2);

    public List<GcRelatedOffsetVoucherItemEO> queryEntityByCheckGroupId(String var1);

    public void updateRelatedOffsetVoucherItemInfo(List<GcRelatedOffsetVoucherItemEO> var1);

    public List<GcRelatedOffsetVoucherItemEO> queryByIds(Collection<String> var1);
}

