/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.subdatabase.update;

import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;

public class SubDataBaseSyncUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(SubDataBaseSyncUpdate.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Map<String, List<SubDataBase>> subDataBaseMap = this.querySubDataBase(connection);
            if (!subDataBaseMap.isEmpty()) {
                Map<String, List<DDLInfoObj>> ddlInfoMap = this.queryDDLInfos(connection);
                List<DDLInfoObj> newestDDLInfo = this.getNewestDDLInfo(subDataBaseMap, ddlInfoMap);
                this.doUpgrade(connection, newestDDLInfo);
            } else {
                this.logger.info("\u5f53\u524d\u7cfb\u7edf\u5185\u4e0d\u5b58\u5728\u5206\u5e93,\u65e0\u9700\u5347\u7ea7");
            }
        }
        catch (Exception e) {
            this.logger.error("\u5206\u5e93\u5347\u7ea7\u540c\u6b65\u5e8f\u53f7\u6807\u8bb0\u5931\u8d25", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }

    private Map<String, List<SubDataBase>> querySubDataBase(Connection connection) {
        HashMap<String, List<SubDataBase>> subDataBaseMap = new HashMap<String, List<SubDataBase>>();
        String SUB_DATABASE_DS = "SD_DS_KEY";
        String SUB_DATABASE_CODE = "SD_CODE";
        String SUB_DATABASE_TITLE = "SD_TITLE";
        String SUB_DATABASE_SYNC_TIME = "SD_SYNC_TIME";
        String SUB_DATABASE_QUERY_SQL = "SELECT " + SUB_DATABASE_DS + " , " + SUB_DATABASE_CODE + " , " + SUB_DATABASE_TITLE + " , " + SUB_DATABASE_SYNC_TIME + " FROM NR_SUBDATABASE_SERVICE";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SUB_DATABASE_QUERY_SQL);
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                String dataschemeKey = resultSet.getString(SUB_DATABASE_DS);
                String code = resultSet.getString(SUB_DATABASE_CODE);
                String title = resultSet.getString(SUB_DATABASE_TITLE);
                Timestamp syncTime = resultSet.getTimestamp(SUB_DATABASE_SYNC_TIME);
                SubDataBase subDataBase = new SubDataBase(dataschemeKey, code, title, syncTime);
                if (subDataBaseMap.get(dataschemeKey) == null) {
                    ArrayList<SubDataBase> subDataBaseList = new ArrayList<SubDataBase>();
                    subDataBaseList.add(subDataBase);
                    subDataBaseMap.put(dataschemeKey, subDataBaseList);
                    continue;
                }
                List subDataBases = (List)subDataBaseMap.get(dataschemeKey);
                subDataBases.add(subDataBase);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5206\u5e93\u5b9a\u4e49\u67e5\u8be2\u5931\u8d25", e);
        }
        return subDataBaseMap;
    }

    private Map<String, List<DDLInfoObj>> queryDDLInfos(Connection connection) {
        HashMap<String, List<DDLInfoObj>> DDLInfoMap = new HashMap<String, List<DDLInfoObj>>();
        String DDL_INFO_DS = "DS_KEY";
        String DDL_INFO_TM = "TM_KEY";
        String DDL_INFO_SYNC_TIME = "DDL_TIME";
        String DDL_INFO_QUERY_SQL = "SELECT " + DDL_INFO_DS + " , " + DDL_INFO_TM + "," + DDL_INFO_SYNC_TIME + " FROM NR_SD_DATATABLE_DEPLOY_INFO";
        try (PreparedStatement preparedStatement = connection.prepareStatement(DDL_INFO_QUERY_SQL);
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                List<DDLInfoObj> ddlInfoObjList;
                String dataschemeKey = resultSet.getString(DDL_INFO_DS);
                String tableModelKey = resultSet.getString(DDL_INFO_TM);
                Timestamp syncTime = resultSet.getTimestamp(DDL_INFO_SYNC_TIME);
                DDLInfoObj ddlInfoObj = new DDLInfoObj(dataschemeKey, tableModelKey, syncTime);
                if (DDLInfoMap.get(dataschemeKey) == null) {
                    ddlInfoObjList = new ArrayList<DDLInfoObj>();
                    ddlInfoObjList.add(ddlInfoObj);
                    DDLInfoMap.put(dataschemeKey, ddlInfoObjList);
                    continue;
                }
                ddlInfoObjList = (List)DDLInfoMap.get(dataschemeKey);
                ddlInfoObjList.add(ddlInfoObj);
            }
        }
        catch (Exception e) {
            this.logger.error("DDL\u53d1\u5e03\u4fe1\u606f\u67e5\u8be2\u5931\u8d25", e);
        }
        return DDLInfoMap;
    }

    private List<DDLInfoObj> getNewestDDLInfo(Map<String, List<SubDataBase>> subDataBaseMap, Map<String, List<DDLInfoObj>> ddlInfoMap) {
        ArrayList<DDLInfoObj> newestDDLInfo = new ArrayList<DDLInfoObj>();
        for (String dataschemeKey : subDataBaseMap.keySet()) {
            List<SubDataBase> subDataBases = subDataBaseMap.get(dataschemeKey);
            List<DDLInfoObj> ddlInfoObjs = ddlInfoMap.get(dataschemeKey);
            if (CollectionUtils.isEmpty(ddlInfoObjs)) continue;
            for (SubDataBase subDataBase : subDataBases) {
                Timestamp subDataBaseSyncTime = subDataBase.getSyncTime();
                for (DDLInfoObj ddlInfoObj : ddlInfoObjs) {
                    Timestamp ddlInfoObjSyncTime = ddlInfoObj.getSyncTime();
                    if (!ddlInfoObjSyncTime.after(subDataBaseSyncTime)) continue;
                    newestDDLInfo.add(ddlInfoObj);
                }
            }
        }
        return newestDDLInfo;
    }

    private void doUpgrade(Connection connection, List<DDLInfoObj> newestDDLInfo) {
        Throwable throwable;
        PreparedStatement preparedStatement;
        if (!CollectionUtils.isEmpty(newestDDLInfo)) {
            String DDL_INFO_UPDATE_SQL = "UPDATE NR_SD_DATATABLE_DEPLOY_INFO SET SYNC_ORDER = 1 WHERE DS_KEY = ? AND TM_KEY = ?";
            try {
                preparedStatement = connection.prepareStatement(DDL_INFO_UPDATE_SQL);
                throwable = null;
                try {
                    for (DDLInfoObj ddlInfo : newestDDLInfo) {
                        String dataSchemeKey = ddlInfo.getDataSchemeKey();
                        String tableModelKey = ddlInfo.getTableModelKey();
                        this.logger.info("DDL\u53d1\u5e03\u4fe1\u606f,\u6570\u636e\u65b9\u6848\u4e3a[{}],\u5973\u5a32\u8868\u6a21\u578bID\u4e3a[{}],\u8bc6\u522b\u4e3a\u9700\u8981\u540c\u6b65\u7684\u6a21\u578b", (Object)dataSchemeKey, (Object)tableModelKey);
                        preparedStatement.setString(1, dataSchemeKey);
                        preparedStatement.setString(2, tableModelKey);
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (preparedStatement != null) {
                        if (throwable != null) {
                            try {
                                preparedStatement.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                        } else {
                            preparedStatement.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                this.logger.error("\u6279\u91cf\u66f4\u65b0DDL\u53d1\u5e03\u4fe1\u606f\u4e2d\uff0c\u9700\u8981\u540c\u6b65\u7684\u6a21\u578b\u8bb0\u5f55\u5931\u8d25", e);
            }
        }
        String DDL_INFO_UPDATE_DEFAULT_SQL = "UPDATE NR_SD_DATATABLE_DEPLOY_INFO SET SYNC_ORDER = 0 WHERE SYNC_ORDER IS NULL";
        try {
            preparedStatement = connection.prepareStatement(DDL_INFO_UPDATE_DEFAULT_SQL);
            throwable = null;
            try {
                preparedStatement.execute();
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                if (preparedStatement != null) {
                    if (throwable != null) {
                        try {
                            preparedStatement.close();
                        }
                        catch (Throwable throwable5) {
                            throwable.addSuppressed(throwable5);
                        }
                    } else {
                        preparedStatement.close();
                    }
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u6279\u91cf\u66f4\u65b0DDL\u53d1\u5e03\u4fe1\u606f\u5931\u8d25", e);
        }
    }

    static class SubDataBase {
        private String dataSchemeKey;
        private String code;
        private String title;
        private Timestamp syncTime;

        public SubDataBase(String dataSchemeKey, String code, String title, Timestamp syncTime) {
            this.dataSchemeKey = dataSchemeKey;
            this.code = code;
            this.title = title;
            this.syncTime = syncTime;
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }

        public String getCode() {
            return this.code;
        }

        public String getTitle() {
            return this.title;
        }

        public Timestamp getSyncTime() {
            return this.syncTime;
        }
    }

    static class DDLInfoObj {
        private String dataSchemeKey;
        private String tableModelKey;
        private Timestamp syncTime;

        public DDLInfoObj(String dataSchemeKey, String tableModelKey, Timestamp syncTime) {
            this.dataSchemeKey = dataSchemeKey;
            this.tableModelKey = tableModelKey;
            this.syncTime = syncTime;
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }

        public String getTableModelKey() {
            return this.tableModelKey;
        }

        public Timestamp getSyncTime() {
            return this.syncTime;
        }
    }
}

