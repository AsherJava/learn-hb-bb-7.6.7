/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionGroupDO;
import java.util.List;

public interface ISummarySolutionGroupDao {
    public String insert(SummarySolutionGroupDO var1) throws DBParaException;

    public void delete(String var1) throws DBParaException;

    public void batchDelete(List<String> var1) throws DBParaException;

    public void update(SummarySolutionGroupDO var1) throws DBParaException;

    public SummarySolutionGroupDO getByKey(String var1);

    public SummarySolutionGroupDO getByGroupAndTitle(String var1, String var2);

    public List<SummarySolutionGroupDO> listByGroup(String var1);

    public List<SummarySolutionGroupDO> fuzzyQuery(String var1);
}

