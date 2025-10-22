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
 *  com.jiuqi.nr.bpm.exception.BpmException
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtils
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.todo.internal;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtils;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class TodoExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(TodoExecutor.class);
    private static final String TODOPARAM = "TODOPARAM";
    private static final String MSGBODY = "MSGBODY";
    private static final String MSG_TODO = "MSG_TODO";
    private static final String MSG_PACKET = "MSG_PACKET";
    private static final String MSGID = "MSGID";
    private static final String ID = "ID";
    private IDesignTimeViewController designTimeViewController;
    final String tempFieldName = "TODOPARAM_";

    private void init() {
        this.designTimeViewController = (IDesignTimeViewController)BeanUtil.getBean(IDesignTimeViewController.class);
    }

    private List<String> getMsgPacketPrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(ID);
        return primaryKeyNames;
    }

    private List<String> getMsgTodoPrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(MSGID);
        return primaryKeyNames;
    }

    public void execute(DataSource dataSource) throws Exception {
        this.init();
        this.deleteData(dataSource);
        this.updateMsgTodo(dataSource);
        this.updateMsgPacket(dataSource);
    }

    private void updateMsgTodo(DataSource dataSource) {
        if (!StringUtils.hasText((String)TODOPARAM) || !StringUtils.hasText((String)TODOPARAM)) {
            return;
        }
        this.logger.info("======\u5347\u7ea7\u5f00\u59cb\uff01==========================");
        try {
            List allTaskDefines = this.designTimeViewController.getAllTaskDefines();
            for (DesignTaskDefine designTaskDefine : allTaskDefines) {
                List formSchemes = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
                for (DesignFormSchemeDefine formScheme : formSchemes) {
                    this.executorUpdateMsgTodo(dataSource, formScheme);
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u5931\u8d25!\u8868\u4e3aMSG_TODO", e);
            throw new BpmException(e.getMessage(), (Throwable)e);
        }
    }

    private void executorUpdateMsgTodo(DataSource dataSource, DesignFormSchemeDefine formScheme) {
        Throwable throwable;
        Connection connection;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        List<ColumnData> data = null;
        try {
            connection = dataSource.getConnection();
            throwable = null;
            try {
                StringBuilder querySql = new StringBuilder();
                querySql.append("SELECT ").append(TODOPARAM).append(",");
                for (String primaryKey : this.getMsgTodoPrimaryKeys()) {
                    querySql.append(primaryKey).append(",");
                }
                querySql.deleteCharAt(querySql.length() - 1);
                querySql.append(" FROM ").append(MSG_TODO);
                querySql.append(" WHERE FORMSCHEMEKEY = '").append(formScheme.getKey()).append("'");
                if (this.logger.isDebugEnabled()) {
                    this.logger.error("\u67e5\u8be2\u62a5\u8868\u65b9\u6848{}[{}]\u4e0b\u7684\u6570\u636e\uff0c\u6267\u884cSQL\u4e3a\uff1a{}", formScheme.getTitle(), formScheme.getFormSchemeCode(), querySql.toString());
                }
                preparedStatement = connection.prepareStatement(querySql.toString());
                resultSet = preparedStatement.executeQuery();
                data = this.getData(resultSet, this.getMsgTodoPrimaryKeys(), TODOPARAM);
            }
            catch (Throwable querySql) {
                throwable = querySql;
                throw querySql;
            }
            finally {
                if (connection != null) {
                    if (throwable != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable querySql) {
                            throwable.addSuppressed(querySql);
                        }
                    } else {
                        connection.close();
                    }
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u62a5\u8868\u65b9\u6848{}[{}]\u4e0b\u7684\u6570\u636e\u5931\u8d25:{}", formScheme.getTitle(), formScheme.getFormSchemeCode(), e);
            throw new BpmException(e.getMessage(), (Throwable)e);
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.error("\u67e5\u8be2\u62a5\u8868\u65b9\u6848{}[{}]\u4e0b\u7684\u6570\u636e\uff0c\u5171:{}\u6761", formScheme.getTitle(), formScheme.getFormSchemeCode(), data == null ? 0 : data.size());
        }
        try {
            connection = dataSource.getConnection();
            throwable = null;
            try {
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                this.addColumn(MSG_TODO, TODOPARAM, connection, sqlInterpreter);
                if (!CollectionUtils.isEmpty(data)) {
                    StringBuilder updateSQL = new StringBuilder();
                    updateSQL.append("UPDATE ").append(MSG_TODO).append(" SET ").append("TODOPARAM_").append("= ? WHERE ");
                    for (int i = 0; i < this.getMsgTodoPrimaryKeys().size(); ++i) {
                        if (i != this.getMsgTodoPrimaryKeys().size() - 1) {
                            updateSQL.append(this.getMsgTodoPrimaryKeys().get(i)).append("=? AND ");
                            continue;
                        }
                        updateSQL.append(this.getMsgTodoPrimaryKeys().get(i)).append("=?");
                    }
                    preparedStatement = connection.prepareStatement(updateSQL.toString());
                    this.setParams((PreparedStatement)preparedStatement, data);
                    preparedStatement.executeBatch();
                    preparedStatement.close();
                }
                this.dropColumn(MSG_TODO, TODOPARAM, connection, sqlInterpreter);
                this.renameColumn(MSG_TODO, TODOPARAM, connection, sqlInterpreter);
                this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01\u8868\u4e3aMSG_TODO,\u66f4\u65b0\u6570\u636e\u4e2a\u6570:" + data.size());
                data = null;
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (connection != null) {
                    if (throwable != null) {
                        try {
                            connection.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        connection.close();
                    }
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u5347\u7ea7\u62a5\u8868\u65b9\u6848{}[{}]\u7684\u5f85\u529e\u8868\u5931\u8d25:{}", formScheme.getTitle(), formScheme.getFormSchemeCode(), e);
            throw new BpmException(e.getMessage(), (Throwable)e);
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }

    private void updateMsgPacket(DataSource dataSource) {
        if (!StringUtils.hasText((String)MSGBODY) || !StringUtils.hasText((String)MSGBODY)) {
            return;
        }
        this.logger.info("======\u5347\u7ea7\u5f00\u59cb\uff01==========================");
        try {
            String tempFieldName = "MSGBODY_";
            Connection connection = dataSource.getConnection();
            Statement preparedStatement = null;
            ResultSet resultSet = null;
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            try {
                StringBuilder querySql = new StringBuilder();
                querySql.append("SELECT ").append(MSGBODY).append(",");
                for (String primaryKey : this.getMsgPacketPrimaryKeys()) {
                    querySql.append(primaryKey).append(",");
                }
                querySql.deleteCharAt(querySql.length() - 1);
                querySql.append(" FROM ").append(MSG_PACKET);
                preparedStatement = connection.prepareStatement(querySql.toString());
                resultSet = preparedStatement.executeQuery();
                List<ColumnData> data = this.getData(resultSet, this.getMsgPacketPrimaryKeys(), MSGBODY);
                resultSet.close();
                preparedStatement.close();
                ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                this.addColumn(MSG_PACKET, MSGBODY, connection, sqlInterpreter);
                if (!CollectionUtils.isEmpty(data)) {
                    StringBuilder updateSQL = new StringBuilder();
                    updateSQL.append("UPDATE ").append(MSG_PACKET).append(" SET ").append(tempFieldName).append("= ? WHERE ");
                    for (int i = 0; i < this.getMsgPacketPrimaryKeys().size(); ++i) {
                        if (i != this.getMsgPacketPrimaryKeys().size() - 1) {
                            updateSQL.append(this.getMsgPacketPrimaryKeys().get(i)).append("=? AND ");
                            continue;
                        }
                        updateSQL.append(this.getMsgPacketPrimaryKeys().get(i)).append("=?");
                    }
                    preparedStatement = connection.prepareStatement(updateSQL.toString());
                    this.setParams((PreparedStatement)preparedStatement, data);
                    preparedStatement.executeBatch();
                    preparedStatement.close();
                }
                this.dropColumn(MSG_PACKET, MSGBODY, connection, sqlInterpreter);
                this.renameColumn(MSG_PACKET, MSGBODY, connection, sqlInterpreter);
                this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01\u8868\u4e3aMSG_PACKET,\u66f4\u65b0\u6570\u636e\u4e2a\u6570:" + data.size());
            }
            catch (Exception e) {
                this.logger.error("\u5347\u7ea7\u5931\u8d25!\u8868\u4e3aMSG_PACKET", e);
                throw new BpmException("\u5347\u7ea7\u5931\u8d25!\u8868\u4e3aMSG_PACKET", (Throwable)e);
            }
            finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    }
                    catch (Exception e) {
                        this.logger.error(e.getMessage(), e);
                    }
                }
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
                if (connection != null) {
                    try {
                        connection.close();
                    }
                    catch (Exception e2) {
                        this.logger.error(e2.getMessage(), e2);
                    }
                }
                this.logger.info("==========BLOB\u5b57\u6bb5\u5347\u7ea7\u5b8c\u6210\uff01=========");
            }
        }
        catch (Exception e) {
            this.logger.info("\u5347\u7ea7\u5931\u8d25!");
            throw new BpmException(e.getMessage(), (Throwable)e);
        }
    }

    private String getString(ResultSet rs, String filed) throws SQLException {
        Object object = rs.getObject(filed);
        if (object != null && object instanceof Blob) {
            byte[] bytes = rs.getBytes(filed);
            String s = null;
            if (bytes != null && bytes.length > 0) {
                s = new String(bytes, StandardCharsets.UTF_8);
            }
            return s;
        }
        return null;
    }

    private List<ColumnData> getData(ResultSet resultSet, List<String> primaryKeys, String filed) throws SQLException {
        ArrayList<ColumnData> result = new ArrayList<ColumnData>();
        while (resultSet.next()) {
            ColumnData r = new ColumnData();
            Object[] values = new Object[primaryKeys.size()];
            for (int i = 0; i < primaryKeys.size(); ++i) {
                values[i] = resultSet.getObject(primaryKeys.get(i));
            }
            r.setValue(values);
            String blob = this.getString(resultSet, filed);
            if (!StringUtils.hasLength((String)blob)) continue;
            r.setData(blob);
            result.add(r);
        }
        return result;
    }

    private void setParams(PreparedStatement preparedStatement, List<ColumnData> data) throws SQLException {
        for (ColumnData columnData : data) {
            preparedStatement.setString(1, columnData.getData());
            for (int i = 0; i < columnData.getValue().length; ++i) {
                preparedStatement.setObject(i + 2, columnData.getValue()[i]);
            }
            preparedStatement.addBatch();
        }
    }

    private void addColumn(String tableName, String filedCode, Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        String tempFieldName = filedCode + "_";
        AlterColumnStatement addStatement = new AlterColumnStatement(tableName, AlterType.ADD);
        addStatement.setColumnName(filedCode);
        LogicField newField = new LogicField();
        newField.setFieldName(tempFieldName);
        newField.setDataType(12);
        newField.setNullable(true);
        newField.setSize(8000);
        addStatement.setNewColumn(newField);
        List addSql = sqlInterpreter.alterColumn(addStatement);
        for (String sql : addSql) {
            this.executeSql(conn, sql);
        }
    }

    private void dropColumn(String tableName, String filed, Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        AlterColumnStatement delStatement = new AlterColumnStatement(tableName, AlterType.DROP);
        delStatement.setColumnName(filed);
        List delSql = sqlInterpreter.alterColumn(delStatement);
        for (String sql : delSql) {
            this.executeSql(conn, sql);
        }
    }

    private void renameColumn(String tableCode, String filed, Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        String tempFieldName = filed + "_";
        AlterColumnStatement renameStatement = new AlterColumnStatement(tableCode, AlterType.RENAME);
        LogicField newField = new LogicField();
        newField.setFieldName(filed);
        newField.setDataType(12);
        newField.setNullable(true);
        renameStatement.setColumnName(tempFieldName);
        renameStatement.setReColumnName(filed);
        renameStatement.setNewColumn(newField);
        List renameSql = sqlInterpreter.alterColumn(renameStatement);
        for (String sql : renameSql) {
            this.executeSql(conn, sql);
        }
    }

    private void executeSql(Connection conn, String sql) throws SQLException {
        Statement prep = null;
        try {
            this.logger.debug(sql);
            prep = conn.prepareStatement(sql);
            prep.execute();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new BpmException(e.getMessage(), (Throwable)e);
        }
        finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }

    private void deleteData(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            this.delete(connection);
            this.deteteMsgTodo(connection);
        }
        catch (Exception e) {
            this.logger.debug("\u5220\u9664msg\u8868\u6570\u636e\u5f02\u5e38", e);
            throw new BpmException(e.getMessage(), (Throwable)e);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }

    private void delete(Connection conn) {
        PreparedStatement prep = null;
        try {
            String sql = "TRUNCATE TABLE MSG_PACKET";
            prep = conn.prepareStatement(sql);
            prep.execute();
        }
        catch (Exception e) {
            this.logger.debug("TRUNCATE TABLE MSG_PACKET\u5f02\u5e38", e);
            throw new BpmException(e.getMessage(), (Throwable)e);
        }
        finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }

    private void deteteMsgTodo(Connection conn) {
        PreparedStatement prep = null;
        try {
            String sql = "delete from MSG_TODO WHERE COMPLETETIME IS NOT NULL";
            prep = conn.prepareStatement(sql);
            prep.execute();
        }
        catch (Exception e) {
            this.logger.debug("TRUNCATE TABLE MSG_PACKET\u5f02\u5e38", e);
            throw new BpmException(e.getMessage(), (Throwable)e);
        }
        finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }

    static class ColumnData {
        private Object[] value;
        private String data;

        ColumnData() {
        }

        public Object[] getValue() {
            return this.value;
        }

        public void setValue(Object[] value) {
            this.value = value;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}

