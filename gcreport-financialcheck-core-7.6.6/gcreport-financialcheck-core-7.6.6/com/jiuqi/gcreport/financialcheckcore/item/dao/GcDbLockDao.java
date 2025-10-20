/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckcore.item.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcDbLockEO;
import java.util.Collection;
import java.util.List;

public interface GcDbLockDao
extends IBaseSqlGenericDAO<GcDbLockEO> {
    public List<String> queryExpiredData(long var1);

    public int deleteByLockId(String var1);

    public void deleteExpiredDataByLockId(String var1);

    public List<String> queryUserNameByInputItemId(Collection<String> var1, String var2);

    public List<GcDbLockEO> listByItems(Collection<String> var1, String var2);

    public void updateReentrantCountByLockId(String var1, boolean var2);
}

