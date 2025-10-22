/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.np.definition.internal.update;

import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateViewField
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(UpdateViewField.class);
    private static final String SQL_QUERY = "select e.EV_TABLE_KEY, t.TD_KIND, e.EV_KEY from %s e, %s t where e.EV_TABLE_KEY = t.TD_KEY";
    private static final String SQL_UPDATE = "UPDATE %s set EV_ENTITY_ID = ? WHERE EV_KEY = ?";
    private static final String SQL_VERSION = "select * from NP_DB_VERSION where MODULEID = 'npdefinition'";
    private static final String DESIGN_VIEW = "DES_SYS_ENTITYVIEWDEFINE";
    private static final String DESIGN_TABLE = "DES_SYS_TABLEDEFINE";
    private static final String RUNTIME_VIEW = "SYS_ENTITYVIEWDEFINE";
    private static final String RUNTIME_TABLE = "SYS_TABLEDEFINE";
    private boolean updateVersion = false;

    public void execute(DataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection();){
            try {
                this.updateVersion = this.adjustUpdateVersion(connection);
                Map<String, String> design = this.getAllTableMap(connection, DESIGN_TABLE, DESIGN_VIEW);
                Map<String, String> runtime = this.getAllTableMap(connection, RUNTIME_TABLE, RUNTIME_VIEW);
                this.updateViewEntityId(connection, design, DESIGN_VIEW);
                this.updateViewEntityId(connection, runtime, RUNTIME_VIEW);
            }
            catch (SQLException e) {
                connection.rollback();
            }
        }
    }

    private void updateViewEntityId(Connection connection, Map<String, String> viewToEntityId, String tableName) throws SQLException {
        String sql = String.format(SQL_UPDATE, tableName);
        try (PreparedStatement pstm = connection.prepareStatement(sql);){
            connection.setAutoCommit(false);
            for (String viewKey : viewToEntityId.keySet()) {
                int i = 1;
                pstm.setString(i++, viewToEntityId.get(viewKey));
                pstm.setString(i++, viewKey);
                pstm.addBatch();
            }
            pstm.executeBatch();
            connection.commit();
        }
    }

    private Map<String, String> getAllTableMap(Connection connection, String table, String view) throws SQLException {
        HashMap<String, String> viewToEntityId = new HashMap<String, String>();
        String sql = String.format(SQL_QUERY, view, table);
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery();){
            while (rs.next()) {
                int i = 1;
                String tableKey = rs.getString(i++);
                int tableKind = rs.getInt(i++);
                String viewKey = rs.getString(i++);
                if (this.updateVersion) {
                    if (TableKind.TABLE_KIND_ENTITY.getValue() != tableKind && TableKind.TABLE_KIND_ENTITY_PERIOD.getValue() != tableKind && TableKind.TABLE_KIND_DICTIONARY.getValue() != tableKind) continue;
                    if (TableKind.TABLE_KIND_ENTITY_PERIOD.getValue() == tableKind) {
                        tableKind = TableKind.TABLE_KIND_ENTITY.getValue();
                    }
                    tableKey = new StringBuffer(tableKey).append("@").append((Object)TableKind.forValue(tableKind)).toString();
                }
                viewToEntityId.put(viewKey, tableKey);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return viewToEntityId;
    }

    private boolean adjustUpdateVersion(Connection connection) {
        String moduleVersion = "MODULEVERSION";
        String specialVersion = "1.0.32";
        boolean flag = false;
        try (PreparedStatement ps = connection.prepareStatement(SQL_VERSION);
             ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
                String version = rs.getString(moduleVersion);
                if (!specialVersion.equals(version)) continue;
                flag = true;
            }
        }
        catch (SQLException throwables) {
            this.logger.error(throwables.getMessage(), throwables);
        }
        return flag;
    }
}

