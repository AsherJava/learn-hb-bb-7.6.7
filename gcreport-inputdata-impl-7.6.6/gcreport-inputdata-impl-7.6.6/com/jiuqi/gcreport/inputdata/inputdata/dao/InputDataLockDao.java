/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataLockEO;
import java.util.Collection;
import java.util.List;

public interface InputDataLockDao
extends IDbSqlGenericDAO<InputDataLockEO, String> {
    public void deleteByLockId(String var1);

    public List<InputDataLockEO> queryExpiredData(long var1);

    public List<InputDataLockEO> queryUserNameByInputItemId(Collection<String> var1);

    public List<InputDataLockEO> queryByLockId(String var1);

    public void updateGroupId(List<InputDataLockEO> var1);
}

