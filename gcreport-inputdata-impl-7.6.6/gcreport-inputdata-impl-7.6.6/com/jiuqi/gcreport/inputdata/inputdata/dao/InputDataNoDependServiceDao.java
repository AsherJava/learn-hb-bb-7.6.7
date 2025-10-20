/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import java.util.Collection;
import java.util.List;

public interface InputDataNoDependServiceDao
extends IDbSqlGenericDAO<InputDataEO, String> {
    public List<InputDataEO> queryCheckOffsetGroupId(Collection<String> var1, InputWriteNecLimitCondition var2);

    public List<InputDataEO> queryCheckOffsetGroupIdByLockId(String var1, String var2);
}

