/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.sql.command.SQLCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 *  com.jiuqi.bi.database.sql.parser.SQLParserException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.nodekeeper;

import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeState;
import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributionDAO {
    private static final long OVERTIME = 180000L;
    private static final long EXPIRE_OVERTIME = 604800000L;
    public static final String TABLE_NAME = "SF_DISTRIBUTED";
    public static final String FIELD_MACHINENAME = "DIS_MACHINENAME";
    public static final String FIELD_NODENAME = "DIS_NODENAME";
    public static final String FIELD_IP = "DIS_IP";
    public static final String FIELD_STATUS = "DIS_STATUS";
    public static final String FIELD_LASTTIME = "DIS_LASTTIME";
    public static final String FIELD_SERVICEADDRESS = "DIS_SERVICEADDRESS";
    public static final String FIELD_PORT = "DIS_PORT";
    public static final String FIELD_SNOWFLAKEID = "DIS_SNOWFLAKEID";
    public static final int NODE_STATUS_MASTER = 1;
    public static final int NODE_STATUS_WORKER = 0;
    public static final String FIELD_CONTEXTPATH = "DIS_CONTEXT";
    public static final String FIELD_HTTPS = "DIS_HTTPS";
    public static final String FIELD_GROUP = "DIS_GROUP";
    public static final String FIELD_APPLICATION_NAME = "DIS_APPLICATION_NAME";
    public static final String FIELD_MACHINECODE = "DIS_MACHINECODE";
    public static final String FIELD_APPLICATION_STATE = "DIS_APPLICATION_STATE";
    public static final String FIELD_TIME_DIFF = "DIS_TIME_DIFF";
    public static final String TABLE_SERVICES_NAME = "SF_SERVICES";
    public static final String FIELD_SERVICES_MACHINENAME = "DIS_MACHINENAME";
    public static final String FIELD_SERVICES_SERVICENAME = "DIS_SERVICE";
    private static Logger logger = LoggerFactory.getLogger(DistributionDAO.class);
    @Deprecated
    private static final String SQL_CREATETABLE = "CREATE TABLE SF_DISTRIBUTED (DIS_MACHINENAME VARCHAR(100) NOT NULL PRIMARY KEY, DIS_NODENAME VARCHAR(100), DIS_IP VARCHAR(100), DIS_STATUS NUMERIC(1,0), DIS_LASTTIME VARCHAR(20), DIS_PORT VARCHAR(10), DIS_SERVICEADDRESS VARCHAR(200) )";
    private static final String SQLX_CREATETABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS SF_DISTRIBUTED (DIS_MACHINENAME VARCHAR(100) NOT NULL, DIS_NODENAME VARCHAR(100), DIS_IP VARCHAR(100), DIS_STATUS NUMBER(1,0), DIS_LASTTIME VARCHAR(20), DIS_PORT VARCHAR(10), DIS_SERVICEADDRESS VARCHAR(200), DIS_SNOWFLAKEID NUMBER(4, 0), constraint PK_DISTRIBUTION primary key(DIS_MACHINENAME));";
    private static final String SQLX_IF_NOT_EXISTS_APPLICATION_NAME_INDEX = "CREATE INDEX I_SF_APPLICATION_N ON SF_DISTRIBUTED (DIS_APPLICATION_NAME);";
    private static final String SQLX_CREATE_SERVICETABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS SF_SERVICES (DIS_MACHINENAME VARCHAR(100) NOT NULL, DIS_SERVICE VARCHAR(100) )";
    private static final String SQLX_ADD_SNOWFLAKE_FIELD = "ALTER TABLE SF_DISTRIBUTED ADD DIS_SNOWFLAKEID NUMBER(4, 0);";
    private static final String SQLX_ADD_CONTEXTPATH_FIELD = "ALTER TABLE SF_DISTRIBUTED ADD DIS_CONTEXT VARCHAR(50);";
    private static final String SQLX_ADD_HTTPS_FIELD = "ALTER TABLE SF_DISTRIBUTED ADD DIS_HTTPS NUMBER(1, 0);";
    private static final String SQLX_ADD_GROUP_FIELD = "ALTER TABLE SF_DISTRIBUTED ADD DIS_GROUP VARCHAR(50);";
    private static final String SQLX_ADD_APPLICATION_NAME = "ALTER TABLE SF_DISTRIBUTED ADD DIS_APPLICATION_NAME VARCHAR(100);";
    private static final String SQLX_ADD_MACHINECODE = "ALTER TABLE SF_DISTRIBUTED ADD DIS_MACHINECODE VARCHAR(32);";
    private static final String SQLX_ADD_APPLICATION_STATE = "ALTER TABLE SF_DISTRIBUTED ADD DIS_APPLICATION_STATE VARCHAR(50);";
    private static final String SQLX_ADD_TIME_DIFF = "ALTER TABLE SF_DISTRIBUTED ADD DIS_TIME_DIFF VARCHAR(50);";
    private static final String SQL_INSERTNODE = "INSERT INTO SF_DISTRIBUTED (DIS_MACHINENAME, DIS_NODENAME, DIS_IP, DIS_STATUS, DIS_LASTTIME, DIS_SERVICEADDRESS, DIS_PORT, DIS_SNOWFLAKEID, DIS_CONTEXT, DIS_HTTPS, DIS_GROUP, DIS_APPLICATION_NAME, DIS_MACHINECODE, DIS_APPLICATION_STATE,DIS_TIME_DIFF) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETENODE = "DELETE FROM SF_DISTRIBUTED WHERE DIS_MACHINENAME=?";
    private static final String SQL_GETNODES = "SELECT * FROM SF_DISTRIBUTED";
    private static final String SQL_GETNODEBYNAME = "SELECT * FROM SF_DISTRIBUTED WHERE DIS_NODENAME=?";
    private static final String SQL_GETNODESBYPRENAME = "SELECT * FROM SF_DISTRIBUTED WHERE DIS_NODENAME LIKE ?";
    private static final String SQL_GETNODEBYMACHINENAME = "SELECT * FROM SF_DISTRIBUTED WHERE DIS_MACHINENAME=?";
    private static final String SQL_CHECK_SNOWFLAKE = "SELECT * FROM SF_DISTRIBUTED WHERE DIS_SNOWFLAKEID = ?";
    private static final String SQL_CANCELMASTER = "UPDATE SF_DISTRIBUTED SET DIS_STATUS=0 WHERE DIS_STATUS=1";
    private static final String SQL_SETMASTER = "UPDATE SF_DISTRIBUTED SET DIS_STATUS=1 WHERE DIS_MACHINENAME=?";
    private static final String SQL_UPDATETIMESTAMP = "UPDATE SF_DISTRIBUTED SET DIS_LASTTIME = ?, DIS_TIME_DIFF = ? WHERE DIS_MACHINENAME=?";
    private static final String SQL_UPDATEAPPLICATION_STATE = "UPDATE SF_DISTRIBUTED SET DIS_APPLICATION_STATE=? WHERE DIS_MACHINENAME=?";
    private static final String SQL_UPDATEIP = "UPDATE SF_DISTRIBUTED SET DIS_IP=? WHERE DIS_MACHINENAME=?";
    private static final String SQL_UPDATESNOWFLAKEID = "UPDATE SF_DISTRIBUTED SET DIS_SNOWFLAKEID = ? WHERE DIS_MACHINENAME=?";
    private static final String SQL_CLEAR_DEADSNOWFLAKEID = "UPDATE SF_DISTRIBUTED SET DIS_SNOWFLAKEID = NULL WHERE DIS_LASTTIME < ?";
    private static final String SQL_UPDATETIMESTAMP_ANDSETMASTER = "UPDATE SF_DISTRIBUTED SET DIS_LASTTIME = ? AND DIS_STATUS = ((select case when a > 0 then 1 else 0 end from (select sum(dis_status) a from SF_DISTRIBUTED)) WHERE DIS_MACHINENAME=?";
    private static final String SQL_UPDATENAME = "UPDATE SF_DISTRIBUTED SET DIS_NODENAME=? WHERE DIS_MACHINENAME=?";
    private static final String SQL_UPDATESERVERADDRESS = "UPDATE SF_DISTRIBUTED SET DIS_SERVICEADDRESS=? WHERE DIS_MACHINENAME=?";
    private static final String SQL_UPDATESERVERPORT = "UPDATE SF_DISTRIBUTED SET DIS_PORT=? WHERE DIS_MACHINENAME=?";
    private static final String SQL_CLEARDEADMASTER = "UPDATE SF_DISTRIBUTED SET DIS_STATUS = 0 WHERE DIS_STATUS = 1 AND DIS_LASTTIME < ?";
    private static final String SQLX_ALTERADDPORT = "ALTER TABLE SF_DISTRIBUTED ADD DIS_PORT VARCHAR(10)";
    private static final String SQL_UPDATE_NODE = "UPDATE SF_DISTRIBUTED SET DIS_NODENAME = ?, DIS_IP = ?, DIS_SERVICEADDRESS = ?, DIS_PORT = ?, DIS_CONTEXT = ?, DIS_HTTPS = ?, DIS_GROUP = ?, DIS_APPLICATION_NAME = ?, DIS_MACHINECODE = ?, DIS_APPLICATION_STATE = ?,DIS_TIME_DIFF = ?  WHERE DIS_MACHINENAME = ?";
    private static final String SQL_DELETE_NODE_SERVICES = "DELETE FROM SF_SERVICES WHERE DIS_MACHINENAME = ?";
    private static final String SQL_INSERT_NODE_SERVICES = "INSERT INTO SF_SERVICES(DIS_MACHINENAME, DIS_SERVICE) VALUES (?, ?)";
    private static final String SQL_SELECT_NODE_BY_SERVICE_AND_GROUP = "SELECT * FROM SF_DISTRIBUTED T1, SF_SERVICES T2 WHERE T1.DIS_MACHINENAME = T2.DIS_MACHINENAME AND T2.DIS_SERVICE = ? AND T1.DIS_GROUP = ?";
    private static final String SQL_SELECT_NODE_BY_APPLICATION_NAME = "SELECT * FROM SF_DISTRIBUTED WHERE DIS_APPLICATION_NAME =?";

    @Deprecated
    public static void createDistributionTable(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();){
            int status = stmt.executeUpdate(SQL_CREATETABLE);
            System.out.println("\u6267\u884c\u521b\u5efa\u8868SF_DISTRIBUTED\u7ed3\u675f\uff1a" + status);
        }
    }

    public static void createDistributionTableIfNotExists(Connection conn) throws SQLException {
        List commands;
        LogicTable table;
        SQLCommandParser parser = new SQLCommandParser();
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        ISQLMetadata metadata = database.createMetadata(conn);
        boolean addIndex = false;
        try {
            table = metadata.getTableByName(TABLE_NAME);
            if (table == null) {
                addIndex = true;
                commands = parser.parse(database, SQLX_CREATETABLE_IF_NOT_EXISTS);
                for (SQLCommand command : commands) {
                    logger.debug("\u6267\u884c\u5206\u5e03\u5f0f\u767b\u8bb0\u8868\u521b\u5efa\u64cd\u4f5c\uff1a{}", (Object)command.toString());
                    command.execute(conn);
                }
            }
        }
        catch (SQLMetadataException | SQLParserException e) {
            throw new SQLException(e);
        }
        try {
            List fields = metadata.getFieldsByTableName(TABLE_NAME);
            DistributionDAO.addField(fields, FIELD_PORT, SQLX_ALTERADDPORT, database, conn);
            DistributionDAO.addField(fields, FIELD_SNOWFLAKEID, SQLX_ADD_SNOWFLAKE_FIELD, database, conn);
            DistributionDAO.addField(fields, FIELD_CONTEXTPATH, SQLX_ADD_CONTEXTPATH_FIELD, database, conn);
            DistributionDAO.addField(fields, FIELD_HTTPS, SQLX_ADD_HTTPS_FIELD, database, conn);
            DistributionDAO.addField(fields, FIELD_GROUP, SQLX_ADD_GROUP_FIELD, database, conn);
            DistributionDAO.addField(fields, FIELD_APPLICATION_NAME, SQLX_ADD_APPLICATION_NAME, database, conn);
            DistributionDAO.addField(fields, FIELD_MACHINECODE, SQLX_ADD_MACHINECODE, database, conn);
            DistributionDAO.addField(fields, FIELD_APPLICATION_STATE, SQLX_ADD_APPLICATION_STATE, database, conn);
            DistributionDAO.addField(fields, FIELD_TIME_DIFF, SQLX_ADD_TIME_DIFF, database, conn);
            if (addIndex) {
                commands = parser.parse(database, SQLX_IF_NOT_EXISTS_APPLICATION_NAME_INDEX);
                for (SQLCommand command : commands) {
                    logger.debug("\u6267\u884c\u5206\u5e03\u5f0f\u767b\u8bb0\u8868\u521b\u5efa\u7d22\u5f15{}\u64cd\u4f5c\uff1a{}", (Object)FIELD_APPLICATION_NAME, (Object)command.toString());
                    command.execute(conn);
                }
            }
        }
        catch (SQLMetadataException | SQLParserException e) {
            throw new SQLException(e);
        }
        try {
            table = metadata.getTableByName(TABLE_SERVICES_NAME);
            if (table == null) {
                commands = parser.parse(database, SQLX_CREATE_SERVICETABLE_IF_NOT_EXISTS);
                for (SQLCommand command : commands) {
                    logger.debug("\u6267\u884c\u5206\u5e03\u5f0f\u767b\u8bb0\u8282\u70b9\u670d\u52a1\u8868\u521b\u5efa\u64cd\u4f5c\uff1a{}", (Object)command.toString());
                    command.execute(conn);
                }
            }
        }
        catch (SQLMetadataException | SQLParserException e) {
            throw new SQLException(e);
        }
    }

    private static void addField(List<LogicField> fields, String fieldName, String sqlx, IDatabase database, Connection conn) throws SQLMetadataException, SQLParserException, SQLException {
        boolean hasField = false;
        for (LogicField field : fields) {
            if (!field.getFieldName().equalsIgnoreCase(fieldName)) continue;
            hasField = true;
        }
        if (!hasField) {
            SQLCommandParser parser = new SQLCommandParser();
            List commands = parser.parse(database, sqlx);
            for (SQLCommand command : commands) {
                logger.debug("\u6267\u884c\u5206\u5e03\u5f0f\u767b\u8bb0\u8868\u521b\u5efa\u64cd\u4f5c\uff1a{}", (Object)command.toString());
                command.execute(conn);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int addNode(Connection conn, Node node) throws SQLException {
        int snowFlakeId = DistributionDAO.generateSnowFlakeId(node.getMachineName(), conn);
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERTNODE);){
            stmt.setString(1, node.getMachineName());
            stmt.setString(2, node.getName());
            stmt.setString(3, node.getIp());
            stmt.setInt(4, node.isMaster() ? 1 : 0);
            stmt.setString(5, DistributionDAO.formatDate(node.getLastTimeStamp()));
            stmt.setString(6, node.getServiceAddress());
            stmt.setString(7, node.getPort() + "");
            stmt.setInt(8, snowFlakeId);
            stmt.setString(9, node.getContextPath());
            stmt.setInt(10, node.isHttps() ? 1 : 0);
            stmt.setString(11, node.getGroup());
            stmt.setString(12, node.getApplicationName());
            stmt.setString(13, node.getMachineCode());
            stmt.setString(14, ServiceNodeStateHolder.getState().name());
            stmt.setString(15, node.getTimeDiff() == null ? "" : node.getTimeDiff() + "");
            stmt.executeUpdate();
        }
        return snowFlakeId;
    }

    public static void resetNodeServices(Connection conn, Node node, List<String> services) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_NODE_SERVICES);){
            stmt.setString(1, node.getMachineName());
            stmt.executeUpdate();
        }
        if (services == null || services.isEmpty()) {
            return;
        }
        stmt = conn.prepareStatement(SQL_INSERT_NODE_SERVICES);
        var4_4 = null;
        try {
            for (String service : services) {
                stmt.setString(1, node.getMachineName());
                stmt.setString(2, service);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
        catch (Throwable throwable) {
            var4_4 = throwable;
            throw throwable;
        }
        finally {
            if (stmt != null) {
                if (var4_4 != null) {
                    try {
                        stmt.close();
                    }
                    catch (Throwable throwable) {
                        var4_4.addSuppressed(throwable);
                    }
                } else {
                    stmt.close();
                }
            }
        }
    }

    private static int generateSnowFlakeId(String machineName, Connection conn) throws SQLException {
        int maxWorkers = 1024;
        int hashCode = machineName.hashCode();
        long unsigned = (long)hashCode & 0xFFFFFFFFL;
        int id = (int)(unsigned % (long)maxWorkers);
        int count = 0;
        boolean hasClearedDeadSnowFlake = false;
        while (DistributionDAO.checkExistsSnowFlakeId(conn, id)) {
            if (++id >= maxWorkers) {
                id = 0;
            }
            if (++count < maxWorkers) continue;
            if (hasClearedDeadSnowFlake) {
                throw new SQLException("\u65e0\u6cd5\u5206\u914dsnowflakeid\uff0c\u8fbe\u5230\u6700\u5927\u5bb9\u91cf");
            }
            DistributionDAO.clearDeadSnowFlake(conn);
            hasClearedDeadSnowFlake = true;
            count = 0;
        }
        return id;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean checkExistsSnowFlakeId(Connection conn, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_CHECK_SNOWFLAKE);){
            boolean bl;
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            try {
                bl = rs.next();
            }
            catch (Throwable throwable) {
                rs.close();
                throw throwable;
            }
            rs.close();
            return bl;
        }
    }

    public static void deleteNode(Connection conn, String machineName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETENODE);){
            stmt.setString(1, machineName);
            stmt.executeUpdate();
        }
    }

    public static void updateNode(Connection conn, Node node) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_NODE);){
            stmt.setString(1, node.getName());
            stmt.setString(2, node.getIp());
            stmt.setString(3, node.getServiceAddress());
            stmt.setString(4, node.getPort());
            stmt.setString(5, node.getContextPath());
            stmt.setInt(6, node.isHttps() ? 1 : 0);
            stmt.setString(7, node.getGroup());
            stmt.setString(8, node.getApplicationName());
            stmt.setString(9, node.getMachineCode());
            stmt.setString(10, ServiceNodeStateHolder.getState().name());
            stmt.setString(11, node.getTimeDiff() == null ? "" : node.getTimeDiff() + "");
            stmt.setString(12, node.getMachineName());
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int updateSnowFlakeId(Connection conn, String machineName) throws SQLException {
        int snowFlakeId = DistributionDAO.generateSnowFlakeId(machineName, conn);
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATESNOWFLAKEID);){
            stmt.setInt(1, snowFlakeId);
            stmt.setString(2, machineName);
            stmt.executeUpdate();
            int n = snowFlakeId;
            return n;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<Node> getNodes(Connection conn) throws SQLException {
        ArrayList<Node> list = new ArrayList<Node>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GETNODES);
             ResultSet rs = stmt.executeQuery();){
            while (rs.next()) {
                Node node = new Node();
                DistributionDAO.fillNode(rs, node);
                list.add(node);
            }
        }
        return list;
    }

    public static void deleteExpireNodes(Connection conn) throws SQLException {
        List<Node> nodes = DistributionDAO.getNodes(conn);
        long currentTime = System.currentTimeMillis();
        ArrayList<String> machineNames = new ArrayList<String>();
        for (Node node : nodes) {
            long lastTimeStamp = node.getLastTimeStamp();
            if (currentTime - lastTimeStamp < 604800000L) continue;
            machineNames.add(node.getMachineName());
        }
        if (!machineNames.isEmpty()) {
            for (String machineName : machineNames) {
                DistributionDAO.deleteNode(conn, machineName);
            }
            logger.info("nodekeeper\uff1a\u5220\u9664\u8fc7\u671f 7\u5929 \u7684\u8282\u70b9\u4fe1\u606f\uff0c\u6570\u91cf\uff1a{}", (Object)machineNames.size());
        }
    }

    public static List<Node> getNodesByFunction(Connection conn, String functionName, String group) throws SQLException {
        ArrayList<Node> list = new ArrayList<Node>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_NODE_BY_SERVICE_AND_GROUP);){
            stmt.setString(1, functionName);
            stmt.setString(2, group);
            try (ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    Node node = new Node();
                    DistributionDAO.fillNode(rs, node);
                    if (!node.isAlive()) continue;
                    list.add(node);
                }
            }
        }
        return list;
    }

    public static List<Node> getNodesByApplicationName(Connection conn, String applicationName) throws SQLException {
        ArrayList<Node> list = new ArrayList<Node>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_NODE_BY_APPLICATION_NAME);){
            stmt.setString(1, applicationName);
            try (ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    Node node = new Node();
                    DistributionDAO.fillNode(rs, node);
                    if (!node.isAlive()) continue;
                    list.add(node);
                }
            }
        }
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Node getNodeByName(Connection conn, String name) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GETNODEBYNAME);){
            ResultSet rs;
            block8: {
                Node node;
                stmt.setString(1, name);
                rs = stmt.executeQuery();
                try {
                    if (!rs.next()) break block8;
                    Node node2 = new Node();
                    DistributionDAO.fillNode(rs, node2);
                    node = node2;
                }
                catch (Throwable throwable) {
                    rs.close();
                    throw throwable;
                }
                rs.close();
                return node;
            }
            Node node = null;
            rs.close();
            return node;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<Node> getNodesByPreName(Connection conn, String prename) throws SQLException {
        ArrayList<Node> list = new ArrayList<Node>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GETNODESBYPRENAME);){
            stmt.setString(1, prename + "!_%");
            try (ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    Node node = new Node();
                    DistributionDAO.fillNode(rs, node);
                    list.add(node);
                }
            }
        }
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Node getNodeByMachine(Connection conn, String machineName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GETNODEBYMACHINENAME);){
            ResultSet rs;
            block8: {
                Node node;
                stmt.setString(1, machineName);
                rs = stmt.executeQuery();
                try {
                    if (!rs.next()) break block8;
                    Node node2 = new Node();
                    DistributionDAO.fillNode(rs, node2);
                    node = node2;
                }
                catch (Throwable throwable) {
                    rs.close();
                    throw throwable;
                }
                rs.close();
                return node;
            }
            Node node = null;
            rs.close();
            return node;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setMaster(Connection conn, String machineName) throws SQLException {
        boolean autoCommit = conn.getAutoCommit();
        if (autoCommit) {
            conn.setAutoCommit(false);
        }
        try (Statement stmt1 = conn.createStatement();){
            stmt1.executeUpdate(SQL_CANCELMASTER);
            try (PreparedStatement stmt2 = conn.prepareStatement(SQL_SETMASTER);){
                stmt2.setString(1, machineName);
                stmt2.executeUpdate();
                conn.commit();
            }
        }
        finally {
            if (autoCommit) {
                conn.setAutoCommit(autoCommit);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateTimestamp(Connection conn, String machineName, long timeDiff) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATETIMESTAMP);){
            stmt.setString(1, DistributionDAO.formatDate(System.currentTimeMillis()));
            stmt.setString(2, timeDiff + "");
            stmt.setString(3, machineName);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateApplicationState(Connection conn, ServiceNodeState state, String machineName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATEAPPLICATION_STATE);){
            stmt.setString(1, state.name());
            stmt.setString(2, machineName);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateIP(Connection conn, String machineName, String ip) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATEIP);){
            stmt.setString(1, ip);
            stmt.setString(2, machineName);
            stmt.executeUpdate();
        }
    }

    private static void clearDeadSnowFlake(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_CLEAR_DEADSNOWFLAKEID);){
            Calendar calendar = Calendar.getInstance();
            calendar.add(12, -3);
            stmt.setString(1, DistributionDAO.formatDate(calendar.getTimeInMillis()));
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void autoSetMaster(Connection conn, String machineName) throws SQLException {
        int i = -1;
        try (PreparedStatement stmt = conn.prepareStatement(SQL_CLEARDEADMASTER);){
            Calendar calendar = Calendar.getInstance();
            calendar.add(12, -3);
            stmt.setString(1, DistributionDAO.formatDate(calendar.getTimeInMillis()));
            i = stmt.executeUpdate();
        }
        if (i > 0) {
            try (PreparedStatement stmt2 = conn.prepareStatement(SQL_SETMASTER);){
                stmt2.setString(1, machineName);
                stmt2.executeUpdate();
                conn.commit();
            }
        }
    }

    public static void updateTimestampAndAutoSetMaster(Connection conn, String machineName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATETIMESTAMP_ANDSETMASTER);){
            stmt.setString(1, DistributionDAO.formatDate(System.currentTimeMillis()));
            stmt.setString(2, machineName);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateName(Connection conn, String machineName, String newName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATENAME);){
            stmt.setString(1, newName);
            stmt.setString(2, machineName);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateServerAddress(Connection conn, String machineName, String serverAddress) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATESERVERADDRESS);){
            stmt.setString(1, serverAddress);
            stmt.setString(2, machineName);
            stmt.executeUpdate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void updateServerPort(Connection conn, String machineName, String serverPort) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATESERVERPORT);){
            stmt.setString(1, serverPort);
            stmt.setString(2, machineName);
            stmt.executeUpdate();
        }
    }

    private static void fillNode(ResultSet rs, Node node) throws SQLException {
        String state;
        node.setName(rs.getString(FIELD_NODENAME));
        node.setMachineName(rs.getString("DIS_MACHINENAME"));
        node.setIp(rs.getString(FIELD_IP));
        node.setMaster(rs.getInt(FIELD_STATUS) == 1);
        String lasttime = rs.getString(FIELD_LASTTIME);
        node.setLastTimeStamp(DistributionDAO.toDate(lasttime));
        long currentTime = System.currentTimeMillis();
        node.setAlive(currentTime - node.getLastTimeStamp() < 180000L);
        node.setServiceAddress(rs.getString(FIELD_SERVICEADDRESS));
        String port = rs.getString(FIELD_PORT);
        if (port == null || port.length() == 0 || port.equals("null")) {
            port = "";
        }
        node.setPort(port);
        int snowFlakeId = rs.getInt(FIELD_SNOWFLAKEID);
        if (!rs.wasNull()) {
            node.setSnowflakeId(snowFlakeId);
        }
        node.setContextPath(rs.getString(FIELD_CONTEXTPATH));
        node.setHttps(rs.getInt(FIELD_HTTPS) == 1);
        node.setGroup(rs.getString(FIELD_GROUP));
        node.setApplicationName(rs.getString(FIELD_APPLICATION_NAME));
        node.setMachineCode(rs.getString(FIELD_MACHINECODE));
        String timeDiff = rs.getString(FIELD_TIME_DIFF);
        if (timeDiff != null && !timeDiff.isEmpty()) {
            node.setTimeDiff(Long.parseLong(timeDiff));
        }
        if (StringUtils.isNotEmpty((String)(state = rs.getString(FIELD_APPLICATION_STATE)))) {
            node.setApplicationState(ServiceNodeState.valueOf(state));
        }
        if (!node.isAlive()) {
            logger.debug("nodekeeper\uff1a\u8282\u70b9\u5931\u6d3b\uff0c\u8282\u70b9\u4fe1\u606f\uff1amachineName\uff1a{}\uff0capplicationName\uff1a{}\uff0cip\uff1a{}\uff0cport\uff1a{}\u3002\u5f53\u524d\u670d\u52a1\u5668\u65f6\u95f4\u6233\uff1a{},\u8282\u70b9\u6700\u540e\u6d3b\u8dc3\u65f6\u95f4\u6233\uff1a{}\uff0c\u76f8\u5dee\u5927\u4e8e\uff1a{}\u6beb\u79d2", node.getMachineName(), node.getApplicationName(), node.getIp(), node.getPort(), currentTime, node.getLastTimeStamp(), 180000L);
        }
    }

    private static String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date(timestamp));
    }

    private static long toDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return sdf.parse(date).getTime();
        }
        catch (ParseException e) {
            logger.error("\u8282\u70b9\u65f6\u95f4\u6233\u8f6c\u6362\u51fa\u73b0\u9519\u8bef", e);
            return -1L;
        }
    }
}

