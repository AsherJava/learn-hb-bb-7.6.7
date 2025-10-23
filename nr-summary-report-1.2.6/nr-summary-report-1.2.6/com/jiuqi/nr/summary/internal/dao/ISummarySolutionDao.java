/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionDO;
import java.util.List;

public interface ISummarySolutionDao {
    public String insert(SummarySolutionDO var1) throws DBParaException;

    public void delete(String var1) throws DBParaException;

    public void update(SummarySolutionDO var1) throws DBParaException;

    public SummarySolutionDO getByKey(String var1, boolean var2);

    public SummarySolutionDO getByName(String var1);

    public SummarySolutionDO getByGroupAndTitle(String var1, String var2);

    public List<SummarySolutionDO> listByGroup(String var1, boolean var2);

    public List<SummarySolutionDO> listByGroups(List<String> var1, boolean var2);

    public List<SummarySolutionDO> fuzzyQuery(String var1);
}

