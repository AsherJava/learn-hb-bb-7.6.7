/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckGroupInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckResultSaveInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumTableResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.QueryEnumResultInfo;
import java.sql.SQLException;
import java.util.List;

public interface IEnumDataCheckService {
    public EnumDataCheckResultInfo enumDataCheckResult(String var1);

    public EnumDataCheckResultInfo enumDataCheck(EnumDataCheckInfo var1, AsyncTaskMonitor var2);

    public EnumTableResultInfo queryAllEnumTables(EnumDataCheckInfo var1, AsyncTaskMonitor var2);

    public EnumCheckResultSaveInfo saveEnumCheckResults(QueryEnumResultInfo var1) throws SQLException;

    public String queryEnumCheckResults(QueryEnumResultInfo var1) throws Exception;

    public EnumCheckGroupInfo queryEnumCheckResultGroup(QueryEnumResultInfo var1) throws Exception;

    public List<EnumDataCheckResultItem> queryEnumCheckResultExport(QueryEnumResultInfo var1) throws Exception;
}

