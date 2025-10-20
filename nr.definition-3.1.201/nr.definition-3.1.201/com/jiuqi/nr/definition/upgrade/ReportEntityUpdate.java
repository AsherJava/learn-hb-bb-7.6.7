/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.StringUtils;

public class ReportEntityUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(ReportEntityUpdate.class);
    private final String updateSqlDes = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ? AND %s = ?", "NR_DATASCHEME_DIM_DES", "DD_REPORT_DIM", "DD_DS_KEY", "DD_DIM_KEY", "DD_TYPE");
    private final String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ? AND %s = ?", "NR_DATASCHEME_DIM", "DD_REPORT_DIM", "DD_DS_KEY", "DD_DIM_KEY", "DD_TYPE");
    private final int orgType = DimensionType.UNIT.getValue();
    private final int dimType = DimensionType.DIMENSION.getValue();

    public void execute(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)dataSource);
            connection.setAutoCommit(false);
            List<Task> designTaskList = this.getAllTask(connection, "NR_PARAM_TASK_DES");
            List<Task> taskList = this.getAllTask(connection, "NR_PARAM_TASK");
            Map<String, DesignTaskFlowsDefine> designFlowsDefineMap = this.getAllTaskFlows(connection, designTaskList, "NR_PARAM_BIGDATATABLE_DES");
            Map<String, DesignTaskFlowsDefine> flowsDefineMap = this.getAllTaskFlows(connection, designTaskList, "NR_PARAM_BIGDATATABLE");
            List<Dim> dimListDes = this.getReportDimList(designTaskList, designFlowsDefineMap);
            List<Dim> dimList = this.getReportDimList(taskList, flowsDefineMap);
            this.update(this.updateSqlDes, dimListDes, connection);
            this.update(this.updateSql, dimList, connection);
            this.logger.info("\u66f4\u65b0\u4e0a\u62a5\u4e3b\u4f53\u4e3a\u62a5\u9001\u60c5\u666f\u6210\u529f\uff01");
        }
        catch (SQLException e) {
            if (!connection.isClosed()) {
                connection.rollback();
            }
            this.logger.error("\u66f4\u65b0\u4e0a\u62a5\u4e3b\u4f53\u4e3a\u62a5\u9001\u60c5\u666f\u5931\u8d25\uff01", e);
            throw e;
        }
        finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }

    private Map<String, DesignTaskFlowsDefine> getAllTaskFlows(Connection connection, List<Task> taskList, String tableName) throws SQLException {
        HashMap<String, DesignTaskFlowsDefine> map = new HashMap<String, DesignTaskFlowsDefine>();
        StringBuilder sb = new StringBuilder("SELECT BD_DATA FROM ").append(tableName).append(" WHERE BD_KEY = ? AND BD_CODE = 'FLOWSETTING' AND BD_LANG = ?");
        for (Task task : taskList) {
            try {
                PreparedStatement statement = connection.prepareStatement(sb.toString());
                Throwable throwable = null;
                try {
                    statement.setString(1, task.getKey());
                    statement.setInt(2, LanguageType.DEFAULT.getValue());
                    ResultSet resultSet = statement.executeQuery();
                    Throwable throwable2 = null;
                    try {
                        while (resultSet.next()) {
                            DesignTaskFlowsDefine define = DesignTaskFlowsDefine.bytesToTaskFlowsData(resultSet.getBytes(1));
                            map.put(task.getKey(), define);
                        }
                    }
                    catch (Throwable throwable3) {
                        throwable2 = throwable3;
                        throw throwable3;
                    }
                    finally {
                        if (resultSet == null) continue;
                        if (throwable2 != null) {
                            try {
                                resultSet.close();
                            }
                            catch (Throwable throwable4) {
                                throwable2.addSuppressed(throwable4);
                            }
                            continue;
                        }
                        resultSet.close();
                    }
                }
                catch (Throwable throwable5) {
                    throwable = throwable5;
                    throw throwable5;
                }
                finally {
                    if (statement == null) continue;
                    if (throwable != null) {
                        try {
                            statement.close();
                        }
                        catch (Throwable throwable6) {
                            throwable.addSuppressed(throwable6);
                        }
                        continue;
                    }
                    statement.close();
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<Task> getAllTask(Connection connection, String param) {
        ArrayList<Task> task = new ArrayList<Task>();
        try (Statement statement = connection.createStatement();){
            try (ResultSet resultSet = statement.executeQuery(String.format("SELECT TK_KEY,TK_DATASCHEME FROM %s", param));){
                while (resultSet.next()) {
                    task.add(new Task(resultSet.getString(1), resultSet.getString(2)));
                }
            }
            ArrayList<Task> arrayList = task;
            return arrayList;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Dim> getReportDimList(List<Task> taskList, Map<String, DesignTaskFlowsDefine> flowsDefineMap) {
        ArrayList<Dim> dimList = new ArrayList<Dim>(taskList.size());
        IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)SpringBeanUtils.getBean(IPeriodEntityAdapter.class);
        for (Task task : taskList) {
            String[] split;
            DesignTaskFlowsDefine flowsDefine = flowsDefineMap.get(task.getKey());
            if (flowsDefine == null) continue;
            String designTableDefines = flowsDefine.getDesignTableDefines();
            for (String s : split = designTableDefines.split(";")) {
                if (!StringUtils.hasLength(s) || "null".equalsIgnoreCase(s) || periodEntityAdapter.isPeriodEntity(s) || OrgAdapterUtil.isOrg((String)s)) continue;
                dimList.add(new Dim(task.getDataSchemeKey(), s));
            }
        }
        return dimList;
    }

    private void update(String querySql, List<Dim> dimList, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(querySql);){
            int[] arr;
            for (Dim dim : dimList) {
                statement.setInt(1, 1);
                statement.setString(2, dim.getDsKey());
                statement.setString(3, dim.getDimKey());
                statement.setInt(4, this.dimType);
                statement.addBatch();
            }
            for (int i : arr = statement.executeBatch()) {
                if (i >= 0) continue;
                throw new SQLException("\u66f4\u65b0\u4e0a\u62a5\u4e3b\u4f53\u4e3a\u62a5\u9001\u60c5\u666f\u5931\u8d25\uff01");
            }
        }
    }

    private static class Task {
        private String key;
        private String dataSchemeKey;

        public Task(String key, String dataSchemeKey) {
            this.key = key;
            this.dataSchemeKey = dataSchemeKey;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }

        public void setDataSchemeKey(String dataSchemeKey) {
            this.dataSchemeKey = dataSchemeKey;
        }
    }

    private static class Dim {
        private String dsKey;
        private String dimKey;

        public Dim(String dsKey, String dimKey) {
            this.dsKey = dsKey;
            this.dimKey = dimKey;
        }

        public String getDsKey() {
            return this.dsKey;
        }

        public void setDsKey(String dsKey) {
            this.dsKey = dsKey;
        }

        public String getDimKey() {
            return this.dimKey;
        }

        public void setDimKey(String dimKey) {
            this.dimKey = dimKey;
        }
    }
}

