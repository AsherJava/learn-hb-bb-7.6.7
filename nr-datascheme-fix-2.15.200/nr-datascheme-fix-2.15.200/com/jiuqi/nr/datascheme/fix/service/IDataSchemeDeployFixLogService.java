/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.service;

import com.jiuqi.nr.datascheme.fix.entity.DeployFailFixLogDO;
import java.time.Instant;
import java.util.List;

public interface IDataSchemeDeployFixLogService {
    public int insertFixLog(DeployFailFixLogDO var1);

    public List<DeployFailFixLogDO> getFixLogByTable(String var1);

    public DeployFailFixLogDO getNewestLogByTable(String var1);

    public List<DeployFailFixLogDO> getFixLogByScheme(String var1);

    public List<DeployFailFixLogDO> getFixLogByDsAndSelectedTime(Instant var1, String var2);

    public DeployFailFixLogDO getFixLogByDtAndSelectedTime(Instant var1, String var2);

    public int updateFixLog(DeployFailFixLogDO var1);

    public int deleteFixLog(DeployFailFixLogDO var1);

    public void deleteAllBackUpTablesAndLogs() throws Exception;

    public void deleteAllBackUpTables() throws Exception;

    public int deleteAllLogs();

    public void deleteAllBackUpTablesByScheme(String var1) throws Exception;

    public void deleteAllBackUpTablesAndLogsByScheme(String var1) throws Exception;

    public void deleteAllLogsByScheme(String var1);

    public void deleteBackUpTablesBySchemeAndTime(Instant var1, String var2) throws Exception;

    public void deleteBackUpTablesAndLogsBySchemeAndTime(Instant var1, String var2) throws Exception;

    public void deleteLogsBySchemeAndTime(Instant var1, String var2);

    public void deleteBackUpTablesByTableAndTime(Instant var1, String var2) throws Exception;

    public void deleteBackUpTablesAndLogsByTableAndTime(Instant var1, String var2) throws Exception;

    public void deleteLogsByTableAndTime(Instant var1, String var2);

    public List<DeployFailFixLogDO> getAllFixLogs();
}

