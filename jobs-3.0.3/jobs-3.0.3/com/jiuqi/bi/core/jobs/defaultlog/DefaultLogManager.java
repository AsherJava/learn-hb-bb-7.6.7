/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.defaultlog.DefaultLogDAO;
import com.jiuqi.bi.core.jobs.defaultlog.LogItem;
import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.bi.core.jobs.extension.LogType;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DefaultLogManager {
    public List<ILogGenerator.LogItem> getLastLogs(List<String> instanceIds, int count) throws JobsException {
        return this.getLastLogs(instanceIds, null, count);
    }

    public List<ILogGenerator.LogItem> getLastLogs(List<String> instanceIds, LogType logType, int count) throws JobsException {
        List<LogItem> logItems;
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            logItems = DefaultLogDAO.getLastLogs(conn, instanceIds, logType, count);
        }
        catch (SQLException e) {
            throw new JobsException(e);
        }
        return LogItem.toJobLogItems(logItems);
    }

    public List<ILogGenerator.LogItem> getLogsBefore(List<String> instanceIds, long beforeTimeStamp, int count) throws JobsException {
        return this.getLogsBefore(instanceIds, beforeTimeStamp, null, count);
    }

    public List<ILogGenerator.LogItem> getLogsBefore(List<String> instanceIds, long beforeTimeStamp, LogType logType, int count) throws JobsException {
        List<LogItem> logItems;
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            logItems = DefaultLogDAO.getLogsBefore(conn, instanceIds, beforeTimeStamp, logType, count);
        }
        catch (SQLException e) {
            throw new JobsException(e);
        }
        return LogItem.toJobLogItems(logItems);
    }

    public List<ILogGenerator.LogItem> getLastLogsAfter(List<String> instanceIds, long afterTimeStamp) throws JobsException {
        return this.getLastLogsAfter(instanceIds, null, afterTimeStamp);
    }

    public List<ILogGenerator.LogItem> getLastLogsAfter(List<String> instanceIds, LogType logType, long afterTimeStamp) throws JobsException {
        List<LogItem> logItems;
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            logItems = DefaultLogDAO.getLastLogsAfter(conn, instanceIds, logType, afterTimeStamp);
        }
        catch (SQLException e) {
            throw new JobsException(e);
        }
        return LogItem.toJobLogItems(logItems);
    }

    public ILogGenerator.LogItemDetail getLogDetail(long logId) throws JobsException {
        LogItem logItem = null;
        ILogGenerator.LogItemDetail resultLogItem = null;
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            logItem = DefaultLogDAO.getLogDetail(conn, logId);
        }
        catch (SQLException e) {
            throw new JobsException(e);
        }
        if (logItem != null) {
            return logItem.getLogDetail();
        }
        return resultLogItem;
    }

    public void iterateAllLogs(List<String> instanceIds, ILogGenerator.LogItemHandle handler) throws JobsException {
        this.iterateAllLogs(instanceIds, null, handler);
    }

    public void iterateAllLogs(List<String> instanceIds, LogType logType, ILogGenerator.LogItemHandle handler) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            DefaultLogDAO.iterateAllLogs(conn, instanceIds, logType, handler);
        }
        catch (SQLException e) {
            throw new JobsException(e);
        }
    }
}

