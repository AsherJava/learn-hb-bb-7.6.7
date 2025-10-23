/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.summary.internal.dao;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.summary.internal.entity.SummaryDataCellDO;
import java.util.List;

public interface ISummaryDataCellDao<DO extends SummaryDataCellDO> {
    public String insert(DO var1) throws DBParaException;

    public void batchInsert(List<DO> var1);

    public void delete(String var1) throws DBParaException;

    public void deleteByReport(String var1) throws DBParaException;

    public void deleteByReports(List<String> var1);

    public void update(DO var1) throws DBParaException;

    public void batchUpdate(List<DO> var1);

    public DO getByKey(String var1);

    public List<DO> listByReport(String var1);
}

