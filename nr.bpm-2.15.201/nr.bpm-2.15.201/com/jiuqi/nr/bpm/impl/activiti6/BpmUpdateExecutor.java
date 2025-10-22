/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.activiti.engine.ActivitiException
 *  org.activiti.engine.impl.util.IoUtil
 *  org.activiti.engine.impl.util.ReflectUtil
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.impl.util.ReflectUtil;

public class BpmUpdateExecutor
implements CustomClassExecutor {
    private static final Logger logger = LogFactory.getLogger(BpmUpdateExecutor.class);
    public static final boolean IGNOREERROR = true;
    public static final String POSTGRESQL = "postgres";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        try (Connection conn = dataSource.getConnection();){
            String operation = "create";
            boolean isPostSql = BpmUpdateExecutor.isPostGresSQL(conn);
            if (!isPostSql) {
                return;
            }
            String component = "engine";
            String resourceName = this.getResourceForDbOperation("create", component, POSTGRESQL);
            this.executeSchemaResource("create", component, resourceName, conn, false);
            component = "history";
            resourceName = this.getResourceForDbOperation("create", component, POSTGRESQL);
            this.executeSchemaResource("create", component, resourceName, conn, false);
            component = "identity";
            resourceName = this.getResourceForDbOperation("create", component, POSTGRESQL);
            this.executeSchemaResource("create", component, resourceName, conn, true);
        }
    }

    public String getResourceForDbOperation(String operation, String component, String databaseType) {
        if (databaseType.equals("informix") || databaseType.equals("gaussdb100") || databaseType.equals("hana")) {
            return String.format("config/db/activiti.%s.%s.%s.sql", databaseType, operation, component);
        }
        return String.format("org/activiti/db/create/activiti.%s.%s.%s.sql", databaseType, operation, component);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void executeSchemaResource(String operation, String component, String resourceName, Connection conn, boolean isOptional) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = ReflectUtil.getResourceAsStream((String)resourceName);
            if (inputStream == null) {
                if (!isOptional) throw new ActivitiException("resource '" + resourceName + "' is not available");
                logger.info(String.format("no schema resource {%s} for {%s}", resourceName, operation));
                return;
            } else {
                this.execResource(inputStream, resourceName, conn);
            }
            return;
        }
        finally {
            IoUtil.closeSilently((InputStream)inputStream);
        }
    }

    private void execResource(InputStream inputStream, String resourceName, Connection conn) throws Exception {
        byte[] bytes = IoUtil.readInputStream((InputStream)inputStream, (String)resourceName);
        String ddlStatements = new String(bytes);
        BufferedReader reader = new BufferedReader(new StringReader(ddlStatements));
        String line = this.readNextTrimmedLine(reader);
        StringBuilder sqlBuilder = new StringBuilder();
        while (line != null) {
            if (line.startsWith("# ") || line.startsWith("-- ")) continue;
            if (line.startsWith("execute java ")) {
                throw new ActivitiException("no surport script: execute java.");
            }
            if (line.length() > 0) {
                if (line.endsWith(";")) {
                    sqlBuilder = this.addSqlStatementPiece(sqlBuilder, line.substring(0, line.length() - 1));
                    this.execSql(conn, sqlBuilder.toString());
                    sqlBuilder.setLength(0);
                } else {
                    sqlBuilder = this.addSqlStatementPiece(sqlBuilder, line);
                }
            }
            line = this.readNextTrimmedLine(reader);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void execSql(Connection conn, String statement) throws SQLException {
        try (Statement jdbcStatement = conn.createStatement();){
            jdbcStatement.execute(statement);
        }
    }

    private String readNextTrimmedLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line != null) {
            line = line.trim();
        }
        return line;
    }

    private StringBuilder addSqlStatementPiece(StringBuilder builder, String line) {
        if (builder.length() == 0) {
            builder.append(line);
        } else {
            builder.append(" \n" + line);
        }
        return builder;
    }

    private static String getDataBaseType(Connection conn) throws SQLException {
        logger.info(conn.getMetaData().getDatabaseProductName());
        if (DataEngineUtil.isDataBase((Connection)conn, (String)"ORACLE")) {
            return "oracle";
        }
        if (DataEngineUtil.isDataBase((Connection)conn, (String)"MYSQL")) {
            return "mysql";
        }
        if (DataEngineUtil.isDataBase((Connection)conn, (String)"GAUSSDB100")) {
            return "gaussdb100";
        }
        if (DataEngineUtil.isDataBase((Connection)conn, (String)"DM")) {
            return "oracle";
        }
        if (DataEngineUtil.isDataBase((Connection)conn, (String)"Informix")) {
            return "informix";
        }
        if (DataEngineUtil.isDataBase((Connection)conn, (String)"OSCAR")) {
            return "oracle";
        }
        if (DataEngineUtil.isDataBase((Connection)conn, (String)"HANA")) {
            return "hana";
        }
        if (DataEngineUtil.isDataBase((Connection)conn, (String)"TDSQL")) {
            return "mysql";
        }
        throw new ActivitiException("activiti db upgrade error, unknow dbtype ");
    }

    private static boolean isPostGresSQL(Connection conn) throws SQLException {
        return DataEngineUtil.isDataBase((Connection)conn, (String)"POSTGRESQL") || DataEngineUtil.isDataBase((Connection)conn, (String)"polardb");
    }
}

