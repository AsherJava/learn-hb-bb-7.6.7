/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.exception.DefinitonException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ConditionStyleUpdate
implements CustomClassExecutor {
    private final Logger log = LoggerFactory.getLogger(ConditionStyleUpdate.class);
    private static final String NR_PARAM_CONDITIONAL_STYLE = "NR_PARAM_CONDITIONAL_STYLE";
    private static final String NR_PARAM_CONDITIONAL_STYLE_DES = "NR_PARAM_CONDITIONAL_STYLE_DES";
    private static final String NR_PARAM_DATALINK = "NR_PARAM_DATALINK";
    private static final String NR_PARAM_DATALINK_DES = "NR_PARAM_DATALINK_DES";
    private static final String NR_PARAM_DATAREGION = "NR_PARAM_DATAREGION";
    private static final String NR_PARAM_DATAREGION_DES = "NR_PARAM_DATAREGION_DES";
    private static final String CS_LINK_KEY = "CS_LINK_KEY";
    public static final String CS_KEY = "CS_KEY";
    public static final String CS_FORM_KEY = "CS_FORM_KEY";
    public static final String CS_POS_X = "CS_POS_X";
    public static final String CS_POS_Y = "CS_POS_Y";
    public static final String QUERY_CS_FIELDS = "CS_KEY,CS_FORM_KEY,CS_POS_X,CS_POS_Y";
    public static final String DL_KEY = "DL_KEY";

    public void execute(DataSource dataSource) throws Exception {
        this.log.info("\u5f00\u59cb\u5347\u7ea7\u6761\u4ef6\u6837\u5f0f\uff01");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            this.log.info("\u5f00\u59cb\u5347\u7ea7\u8bbe\u8ba1\u671f\u6761\u4ef6\u6837\u5f0f");
            this.addLinkColumn(connection, NR_PARAM_CONDITIONAL_STYLE_DES);
            this.updateLinkKey(connection, "des");
            this.log.info("\u8bbe\u8ba1\u671f\u6761\u4ef6\u6837\u5f0f\u5347\u7ea7\u7ed3\u675f");
            this.log.info("\u5f00\u59cb\u5347\u7ea7\u8fd0\u884c\u671f\u6761\u4ef6\u6837\u5f0f");
            this.addLinkColumn(connection, NR_PARAM_CONDITIONAL_STYLE);
            this.updateLinkKey(connection, "run");
            this.log.info("\u8fd0\u884c\u671f\u6761\u4ef6\u6837\u5f0f\u5347\u7ea7\u7ed3\u675f");
            this.log.info("\u6761\u4ef6\u6837\u5f0f\u5347\u7ea7\u5b8c\u6210\uff01");
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("\u5347\u7ea7\u6761\u4ef6\u6837\u5f0f\u5931\u8d25\uff1a", e.getMessage(), e));
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }

    private void addLinkColumn(Connection connection, String tableName) throws Exception {
        this.log.info("\u5f00\u59cb\u6dfb\u52a0\u94fe\u63a5\u5217");
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
        AlterColumnStatement addStatement = new AlterColumnStatement(tableName, AlterType.ADD);
        addStatement.setColumnName(CS_LINK_KEY);
        LogicField newField = new LogicField();
        newField.setFieldName(CS_LINK_KEY);
        newField.setDataType(6);
        newField.setSize(40);
        newField.setNullable(true);
        addStatement.setNewColumn(newField);
        this.doAdd(sqlInterpreter, connection, addStatement);
    }

    private void doAdd(ISQLInterpretor sqlInterpreter, Connection connection, AlterColumnStatement addStatement) {
        try {
            List addSql = sqlInterpreter.alterColumn(addStatement);
            for (String sql : addSql) {
                this.executeSql(connection, sql);
            }
            this.log.info("\u6dfb\u52a0\u94fe\u63a5\u5217\u6210\u529f");
        }
        catch (Exception e) {
            throw new DefinitonException(String.format("\u6dfb\u52a0\u94fe\u63a5\u5217\u5931\u8d25\uff01", new Object[0]), e);
        }
    }

    private void updateLinkKey(Connection connection, String type) {
        String drTableName;
        String dlTableName;
        String csTableName;
        this.log.info("\u5f00\u59cb\u8bbe\u7f6e\u94fe\u63a5ID");
        if (type == "des") {
            csTableName = NR_PARAM_CONDITIONAL_STYLE_DES;
            dlTableName = NR_PARAM_DATALINK_DES;
            drTableName = NR_PARAM_DATAREGION_DES;
        } else {
            csTableName = NR_PARAM_CONDITIONAL_STYLE;
            dlTableName = NR_PARAM_DATALINK;
            drTableName = NR_PARAM_DATAREGION;
        }
        List<ConditionalStyleMessage> allCs = this.getAllCS(connection, csTableName);
        this.log.info(String.format("\u5171\u6709%s\u4e2a\u6761\u4ef6\u6837\u5f0f", allCs.size()));
        HashMap<String, String> updateCsLinkMap = new HashMap<String, String>();
        HashMap<String, String> deleteCsLinkMap = new HashMap<String, String>();
        if (!CollectionUtils.isEmpty(allCs)) {
            for (ConditionalStyleMessage cs : allCs) {
                String dl_key = this.getDataLinkKey(connection, cs, drTableName, dlTableName, csTableName);
                if (StringUtils.hasText(dl_key)) {
                    updateCsLinkMap.put(cs.getKey(), dl_key);
                    continue;
                }
                deleteCsLinkMap.put(cs.getKey(), dl_key);
            }
            this.updateCsTableData(connection, csTableName, updateCsLinkMap, deleteCsLinkMap);
        }
    }

    private String getDataLinkKey(Connection connection, ConditionalStyleMessage csMessage, String drTableNmae, String dlTableName, String csTableName) {
        StringBuilder queryDataLinkSql = new StringBuilder();
        queryDataLinkSql.append("SELECT DL.DL_KEY FROM ").append(csTableName + " CS ");
        queryDataLinkSql.append("LEFT JOIN ").append(drTableNmae + " DR ").append("ON DR.DR_FORM_KEY = CS.CS_FORM_KEY ");
        queryDataLinkSql.append("LEFT JOIN ").append(dlTableName + " DL ").append("ON DL.DL_REGION_KEY = DR.DR_KEY ");
        queryDataLinkSql.append("WHERE CS.CS_POS_X = ? AND CS.CS_POS_Y = ? AND CS.CS_FORM_KEY = ? AND CS.CS_POS_X = DL.DL_POSX AND CS.CS_POS_Y = DL.DL_POSY ORDER BY DL.DL_KEY");
        String dl_key = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryDataLinkSql.toString());){
            preparedStatement.setInt(1, csMessage.getX());
            preparedStatement.setInt(2, csMessage.getY());
            preparedStatement.setString(3, csMessage.getFormKey());
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()) {
                    dl_key = resultSet.getString(DL_KEY);
                }
            }
        }
        catch (Exception e) {
            this.log.error(String.format("\u672a\u67e5\u8be2\u5230\u6761\u4ef6\u6837\u5f0f[%s]\u5bf9\u5e94\u7684\u94fe\u63a5", csMessage.getKey()));
        }
        return dl_key;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateCsTableData(Connection connection, String tableName, Map<String, String> updateCsLinkMap, Map<String, String> deleteCsLinkMap) {
        try {
            connection.setAutoCommit(false);
            if (!updateCsLinkMap.isEmpty()) {
                this.updateCsLinkKey(connection, tableName, updateCsLinkMap);
            }
            if (!deleteCsLinkMap.isEmpty()) {
                this.deleteCs(connection, tableName, deleteCsLinkMap);
            }
            connection.commit();
        }
        catch (Exception e) {
            this.rollBack(connection);
            this.log.error(String.format("\u66f4\u65b0\u8868[%s]\u6761\u4ef6\u6837\u5f0f\u5931\u8d25", tableName));
        }
        finally {
            this.openAutoCommit(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateCsLinkKey(Connection connection, String tableName, Map<String, String> updateCsLinkMap) {
        PreparedStatement preparedStatement = null;
        try {
            StringBuilder updateCsSql = new StringBuilder();
            updateCsSql.append("UPDATE ").append(tableName).append(" SET ").append(CS_LINK_KEY).append("= ? WHERE ");
            updateCsSql.append(CS_KEY).append(" = ?");
            preparedStatement = connection.prepareStatement(updateCsSql.toString());
            for (Map.Entry<String, String> map : updateCsLinkMap.entrySet()) {
                preparedStatement.setString(1, map.getValue());
                preparedStatement.setString(2, map.getKey());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            this.closePrep(preparedStatement);
        }
        catch (Exception e) {
            this.log.error("\u8bbe\u7f6e\u8868[%s]\u94fe\u63a5\u6570\u636e\u5931\u8d25", (Object)tableName);
        }
        finally {
            this.closePrep(preparedStatement);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void deleteCs(Connection connection, String tableName, Map<String, String> deleteCsLinkMap) {
        PreparedStatement preparedStatement = null;
        try {
            StringBuilder deleteCsSql = new StringBuilder();
            deleteCsSql.append("DELETE FROM ").append(tableName);
            deleteCsSql.append(" WHERE CS_KEY = ? ");
            preparedStatement = connection.prepareStatement(deleteCsSql.toString());
            for (Map.Entry<String, String> map : deleteCsLinkMap.entrySet()) {
                preparedStatement.setString(1, map.getKey());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            this.closePrep(preparedStatement);
        }
        catch (Exception e) {
            this.log.error("\u5220\u9664\u8868[%s]\u5931\u6548\u6761\u4ef6\u6837\u5f0f\u5931\u8d25", (Object)tableName);
        }
        finally {
            this.closePrep(preparedStatement);
        }
    }

    private List<ConditionalStyleMessage> getAllCS(Connection connection, String tableName) {
        ArrayList<ConditionalStyleMessage> csMessageList = new ArrayList<ConditionalStyleMessage>();
        StringBuilder queryAllCS = new StringBuilder().append("SELECT ").append(QUERY_CS_FIELDS).append(" FROM ").append(tableName);
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryAllCS.toString());
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                String key = resultSet.getString(CS_KEY);
                int posX = resultSet.getInt(CS_POS_X);
                int posY = resultSet.getInt(CS_POS_Y);
                String formKey = resultSet.getString(CS_FORM_KEY);
                csMessageList.add(new ConditionalStyleMessage(key, posX, posY, formKey));
            }
        }
        catch (Exception e) {
            this.log.error(String.format("\u83b7\u53d6\u8868[%s]\u4e2d\u6570\u636e\u5931\u8d25", tableName));
        }
        return csMessageList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeSql(Connection conn, String sql) {
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            prep.execute();
        }
        catch (Exception e) {
            this.log.error(String.format("sql[%s]\u6267\u884c\u5931\u8d25", sql));
        }
        finally {
            this.closePrep(prep);
        }
    }

    private void closePrep(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void rollBack(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void openAutoCommit(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class ConditionalStyleMessage {
        private String key;
        private int x;
        private int y;
        private String formKey;

        ConditionalStyleMessage(String csKey, int posX, int posY, String formkey) {
            this.key = csKey;
            this.x = posX;
            this.y = posY;
            this.formKey = formkey;
        }

        private String getKey() {
            return this.key;
        }

        private int getX() {
            return this.x;
        }

        private int getY() {
            return this.y;
        }

        private String getFormKey() {
            return this.formKey;
        }
    }
}

