/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.enumcheck.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.enumcheck.common.EnumCheckGroupInfo;
import com.jiuqi.nr.enumcheck.common.EnumCheckResInfo;
import com.jiuqi.nr.enumcheck.common.EnumCheckResultSaveInfo;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckParam;
import com.jiuqi.nr.enumcheck.common.EnumTableResultInfo;
import com.jiuqi.nr.enumcheck.common.ExportEnumCheckResParam;
import com.jiuqi.nr.enumcheck.common.ExportEnumCheckResult;
import com.jiuqi.nr.enumcheck.common.QueryEnumCheckResGroupParam;
import com.jiuqi.nr.enumcheck.common.QueryEnumCheckResParam;
import com.jiuqi.nr.enumcheck.common.SaveEnumCheckResParam;
import java.sql.SQLException;
import java.util.List;

public interface IEnumCheckService {
    public EnumCheckResInfo enumDataCheck(EnumDataCheckParam var1, AsyncTaskMonitor var2);

    public EnumTableResultInfo queryAllEnumTables(EnumDataCheckParam var1);

    public EnumCheckResultSaveInfo saveEnumCheckResults(SaveEnumCheckResParam var1) throws SQLException;

    public EnumCheckGroupInfo queryEnumCheckResultGroup(QueryEnumCheckResGroupParam var1) throws Exception;

    public String queryEnumCheckResults(QueryEnumCheckResParam var1) throws Exception;

    public List<ExportEnumCheckResult> exportEnumCheckResult(ExportEnumCheckResParam var1) throws Exception;
}

