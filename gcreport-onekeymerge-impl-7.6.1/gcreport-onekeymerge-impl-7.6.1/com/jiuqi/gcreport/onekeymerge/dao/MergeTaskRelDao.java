/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.onekeymerge.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskRelEO;
import java.util.List;
import java.util.Map;

public interface MergeTaskRelDao
extends IBaseSqlGenericDAO<MergeTaskRelEO> {
    public List<MergeTaskRelEO> listByGroupId(String var1);

    public Map<String, Integer> getAsyncTaskId2StateByNP(String var1);

    public Map<String, Integer> getAsyncTaskId2StateByBI(String var1);

    public int updateTaskStateByIds(List<String> var1, String var2);
}

