/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.util.type.GUID
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nvwa.datav.dashboard.monitor.table;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.nvwa.datav.dashboard.monitor.table.DashboardCacheStatInfo;
import com.jiuqi.nvwa.datav.dashboard.monitor.table.DashboardExecTraceInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorTableDao {
    private static final String T_NVWA_B_DASHBOARD_CACHEINFO = "NVWA_B_DASHBOARD_CACHEINFO";
    private static final String T_NVWA_B_DASHBOARD_EXECTRACE = "NVWA_B_DASHBOARD_EXECTRACE";
    private static final String F_NDC_TASKID = "NDC_TASKID";
    private static final String F_NDC_NODENAME = "NDC_NODENAME";
    private static final String F_NDC_NODEIP = "NDC_NODEIP";
    private static final String F_NDC_CREATETIME = "NDC_CREATETIME";
    private static final String F_NDC_SESSIONID = "NDC_SESSIONID";
    private static final String F_NDC_TYPE = "NDC_TYPE";
    private static final String F_NDC_DS_NAME = "NDC_DS_NAME";
    private static final String F_NDC_DS_TITLE = "NDC_DS_TITLE";
    private static final String F_NDC_DS_COUNT = "NDC_DS_COUNT";
    private static final String F_NDC_DS_PVALUES = "NDC_DS_PVALUES";
    private static final String F_NDC_CHARTTITLE = "NDC_CHARTTITLE";
    private static final String F_NDC_CHARTTYPE = "NDC_CHARTTYPE";
    private static final String F_NDC_ESTIMATETIME = "NDC_ESTIMATETIME";
    private static final String F_NDE_GUID = "NDE_GUID";
    private static final String F_NDE_STAGE = "NDE_STAGE";
    private static final String F_NDE_MSG = "NDE_MSG";
    private static final String F_NDE_TIME = "NDE_TIME";
    private static final String INSERT_CACHEINFO = "INSERT INTO NVWA_B_DASHBOARD_CACHEINFO(NDC_TASKID, NDC_NODENAME, NDC_NODEIP, NDC_CREATETIME, NDC_SESSIONID, NDC_TYPE, NDC_DS_NAME, NDC_DS_TITLE, NDC_DS_COUNT, NDC_DS_PVALUES, NDC_CHARTTITLE, NDC_CHARTTYPE, NDC_ESTIMATETIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_EXECTRACE = "INSERT INTO NVWA_B_DASHBOARD_EXECTRACE(NDE_GUID, NDC_TASKID, NDE_STAGE, NDE_MSG, NDE_TIME) VALUES(?,?,?,?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void clearHistory() throws SQLException {
        this.jdbcTemplate.update("truncate table NVWA_B_DASHBOARD_CACHEINFO");
        this.jdbcTemplate.update("truncate table NVWA_B_DASHBOARD_EXECTRACE");
    }

    public void insertCacheInfo(DashboardCacheStatInfo info) throws SQLException {
        this.jdbcTemplate.update(INSERT_CACHEINFO, new Object[]{info.getTaskId(), info.getNodeName(), info.getNodeIp(), info.getCreatetime(), info.getSessionId(), info.getType(), info.getDsName(), info.getDsTitle(), info.getDsCount(), info.getDsPvalues(), info.getChartTitle(), info.getChartType(), info.getEstimateTime()});
    }

    public void insertTraceInfo(DashboardExecTraceInfo info) throws SQLException {
        this.jdbcTemplate.update(INSERT_EXECTRACE, new Object[]{GUID.newFullGUID(), info.getTaskId(), info.getStage(), info.getMsg(), info.getTime()});
    }

    public void createStatTableIfNotExists() throws SQLException, SQLInterpretException, SQLMetadataException {
        block35: {
            CreateTableStatement cts = new CreateTableStatement("", T_NVWA_B_DASHBOARD_CACHEINFO);
            cts.addColumn(MonitorTableDao.newField(F_NDC_TASKID, 6, "\u7f13\u5b58\u4efb\u52a1ID", false, 50, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_NODENAME, 6, "\u8282\u70b9IP", true, 50, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_NODEIP, 6, "\u8282\u70b9\u540d\u79f0", true, 20, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_CREATETIME, 6, "\u4efb\u52a1\u521b\u5efa\u65f6\u95f4", true, 20, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_SESSIONID, 6, "\u7528\u6237\u8bf7\u6c42\u4f1a\u8bddID", true, 50, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_TYPE, 6, "\u7f13\u5b58\u5bf9\u8c61\u7c7b\u578b", true, 20, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_DS_NAME, 6, "\u6570\u636e\u96c6\u540d\u79f0", true, 50, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_DS_TITLE, 6, "\u6570\u636e\u96c6\u6807\u9898", true, 50, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_DS_COUNT, 5, "\u6570\u636e\u96c6\u8bb0\u5f55\u6570", true, 10, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_DS_PVALUES, 6, "\u6570\u636e\u96c6\u53c2\u6570\u53d6\u503c", true, 500, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_CHARTTITLE, 6, "\u56fe\u8868\u6807\u9898", true, 200, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_CHARTTYPE, 6, "\u56fe\u8868\u7c7b\u578b", true, 20, null));
            cts.addColumn(MonitorTableDao.newField(F_NDC_ESTIMATETIME, 5, "\u6267\u884c\u6d88\u8017\u7684\u5927\u6982\u65f6\u95f4", true, 10, null));
            cts.getPrimaryKeys().add(F_NDC_TASKID);
            CreateTableStatement cts2 = new CreateTableStatement("", T_NVWA_B_DASHBOARD_EXECTRACE);
            cts2.addColumn(MonitorTableDao.newField(F_NDE_GUID, 6, "\u7269\u7406ID", false, 50, null));
            cts2.addColumn(MonitorTableDao.newField(F_NDC_TASKID, 6, "\u7f13\u5b58\u4efb\u52a1ID", false, 50, null));
            cts2.addColumn(MonitorTableDao.newField(F_NDE_STAGE, 6, "\u9636\u6bb5\u540d\u79f0", true, 50, null));
            cts2.addColumn(MonitorTableDao.newField(F_NDE_MSG, 6, "\u65e5\u5fd7\u5185\u5bb9", true, 500, null));
            cts2.addColumn(MonitorTableDao.newField(F_NDE_TIME, 6, "\u65e5\u5fd7\u751f\u6210\u65f6\u95f4", true, 20, null));
            cts2.getPrimaryKeys().add(F_NDE_GUID);
            try (Connection conn = this.jdbcTemplate.getDataSource().getConnection();){
                Throwable throwable;
                PreparedStatement ps;
                List sqls;
                IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                ISQLMetadata meta = db.createMetadata(conn);
                if (!meta.existsTable(T_NVWA_B_DASHBOARD_CACHEINFO, null)) {
                    sqls = cts.interpret(db, conn);
                    for (String sql : sqls) {
                        ps = conn.prepareStatement(sql);
                        throwable = null;
                        try {
                            ps.executeUpdate();
                        }
                        catch (Throwable throwable2) {
                            throwable = throwable2;
                            throw throwable2;
                        }
                        finally {
                            if (ps == null) continue;
                            if (throwable != null) {
                                try {
                                    ps.close();
                                }
                                catch (Throwable throwable3) {
                                    throwable.addSuppressed(throwable3);
                                }
                                continue;
                            }
                            ps.close();
                        }
                    }
                }
                if (meta.existsTable(T_NVWA_B_DASHBOARD_EXECTRACE, null)) break block35;
                sqls = cts2.interpret(db, conn);
                for (String sql : sqls) {
                    ps = conn.prepareStatement(sql);
                    throwable = null;
                    try {
                        ps.executeUpdate();
                    }
                    catch (Throwable throwable4) {
                        throwable = throwable4;
                        throw throwable4;
                    }
                    finally {
                        if (ps == null) continue;
                        if (throwable != null) {
                            try {
                                ps.close();
                            }
                            catch (Throwable throwable5) {
                                throwable.addSuppressed(throwable5);
                            }
                            continue;
                        }
                        ps.close();
                    }
                }
            }
        }
    }

    private static LogicField newField(String name, int dataType, String title, boolean nullable, int size, String defaultValue) {
        LogicField f = new LogicField();
        f.setFieldName(name);
        f.setDataType(dataType);
        f.setFieldTitle(title);
        f.setNullable(nullable);
        f.setSize(size);
        f.setDefaultValue(defaultValue);
        return f;
    }
}

