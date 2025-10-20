/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.onekeymerge.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MergeTaskDao
extends IBaseSqlGenericDAO<MergeTaskEO> {
    public List<MergeTaskEO> listExecutableTask(String var1);

    public int countByGroupId(String var1);

    public int updateTaskStateByIds(List<String> var1, String var2);

    public List<MergeTaskEO> listByIds(Set<String> var1);

    public List<MergeTaskEO> listExecuting5ErrorByGroupId(String var1);

    public List<Map<String, Object>> countState(String var1);

    public int updateStateForWaiting(String var1);
}

