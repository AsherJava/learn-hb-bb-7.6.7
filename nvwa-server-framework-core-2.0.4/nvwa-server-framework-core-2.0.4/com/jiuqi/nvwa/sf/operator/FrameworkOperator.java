/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.LicenceException
 *  com.jiuqi.bi.authz.licence.FuncPointInfo
 *  com.jiuqi.bi.authz.licence.LicenceInfo
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.bi.authz.licence.MachineCodeGenerator
 *  com.jiuqi.bi.authz.licence.MachineCodeGenerator$MachineInfo
 *  com.jiuqi.bi.authz.licence.ModuleInfo
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.DefaultDatabase
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.sql.command.SQLCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.sql.parser.SQLParserException
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.util.Version
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.bi.authz.LicenceException;
import com.jiuqi.bi.authz.licence.FuncPointInfo;
import com.jiuqi.bi.authz.licence.LicenceInfo;
import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.bi.authz.licence.MachineCodeGenerator;
import com.jiuqi.bi.authz.licence.ModuleInfo;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.DefaultDatabase;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.util.Version;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.authz.ClientSocketService;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.sf.operator.ModuleUpgradeLockOperator;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameworkOperator {
    private static Logger logger = LoggerFactory.getLogger(FrameworkOperator.class);
    public static final String TABLE_FIELD_ID = "MODULEID";
    public static final String TABLE_FIELD_VERSION = "MODULEVERSION";
    public static final String TABLE_FIELD_UPDATE = "UPDATETIME";
    public static final String TABLE_FIELD_FROMVERSION = "FROMVERSION";
    public static final String TABLE_FIELD_GUID = "GUID";
    public static final String TABLE_FIELD_TAG = "MODULETAG";
    public static final String TABLE_FIELD_MESSEGE = "SQL_MESSAGE";
    public static final String TABLE_FIELD_SQL = "SQL_TEXT";
    public static final String TABLE_FIELD_ORDER = "SQL_ORDER";
    public static final String TABLE_LICENCE_PRODUCTID = "L_PRODUCTID";
    public static final String TABLE_LICENCE_BYTES = "L_DATA";
    public static final String MODULE_TABLE_NAME = "SF_VERSION";
    public static final String LOG_TABLE_NAME = "SF_LOG";
    public static final String LICENCE_TABLE_NAME = "SF_LICENCE";
    public static final String LOCK_TABLE_NAME = "SF_LOCK";
    public static final String LOCK_TABLE_FIELD_KEY = "SL_ID";
    public static final String LOCK_TABLE_FIELD_MODULE = "SL_MODULE_ID";
    public static final String LOCK_TABLE_FIELD_MACHINE = "SL_MACHINE_NAME";
    public static final String LOCK_TABLE_FIELD_TIMESTAMP = "SL_TIMESTAMP";
    public static final String LOCK_TABLE_GLOBAL_VALUE = "GLOBAL";
    public static final String UPGRADE_LOG_TABLE_NAME = "SF_UPGRADE_LOG";
    public static final String UPGRADE_LOG_TABLE_FIELD_SQL_LOG = "LOG_SQL";
    public static final String UPGRADE_LOG_TABLE_FIELD_MACHINE = "LOG_MACHINE_NAME";
    public static final String UPGRADE_LOG_TABLE_FIELD_LOG_TYPE = "LOG_TYPE";
    public static final String UPGRADE_LOG_TABLE_FIELD_TIMESTAMP = "LOG_TIMESTAMP";
    public static final String LEGACY_MODULE_TABLE_NAME = "NP_DB_VERSION";
    public static final String LEGACY_TABLE_FIELD_ID = "MODULEID";
    public static final String LEGACY_TABLE_FIELD_VERSION = "MODULEVERSION";

    public static boolean lowerThan(String jdkVersion) {
        if (jdkVersion == null) {
            throw new IllegalArgumentException();
        }
        int i = jdkVersion.indexOf(95);
        if (i > 0) {
            jdkVersion = jdkVersion.substring(0, i);
        }
        return Framework.getJDKVersion().compareTo(new Version(jdkVersion)) < 0;
    }

    public static MachineCodeGenerator.MachineInfo getMachineCode(String productId) throws Exception {
        MachineCodeGenerator.MachineInfo info = MachineCodeGenerator.getMachineInfo((String)productId);
        if (info == null) {
            MachineCodeGenerator.createMachineInfo((String)productId, (String)"");
            return MachineCodeGenerator.getMachineInfo((String)productId);
        }
        return info;
    }

    public static IDatabase checkSelfVersion(Connection conn) throws SQLException {
        logger.info("\u68c0\u6d4b\u670d\u52a1\u5668\u6846\u67b6\u7248\u672c\u4fe1\u606f");
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        if (db instanceof DefaultDatabase) {
            String url = "";
            try {
                DatabaseMetaData dbMetaData = conn.getMetaData();
                url = dbMetaData.getURL();
            }
            catch (SQLException e) {
                url = "Unknown URL";
            }
            logger.error("\u672a\u8bc6\u522b\u7684\u6570\u636e\u5e93\u7c7b\u578b: {}", (Object)url);
            throw new SQLException("\u672a\u8bc6\u522b\u7684\u6570\u636e\u5e93\u7c7b\u578b: " + url);
        }
        ISQLMetadata metadata = db.createMetadata(conn);
        try {
            LogicTable legacyModuleTable;
            LogicTable lockTable;
            LogicTable licenceTable;
            LogicTable logTable;
            LogicTable moduleTable = metadata.getTableByName(MODULE_TABLE_NAME);
            if (moduleTable == null) {
                logger.info("\u6b63\u5728\u521b\u5efa\u6a21\u5757\u4fe1\u606f\u8868");
                FrameworkOperator.createModuleTable(conn, db);
            }
            if ((logTable = metadata.getTableByName(LOG_TABLE_NAME)) == null) {
                logger.info("\u6b63\u5728\u521b\u5efa\u6a21\u5757\u5347\u7ea7\u8bb0\u5f55\u8868");
                FrameworkOperator.createLogTable(conn, db);
            }
            if ((licenceTable = metadata.getTableByName(LICENCE_TABLE_NAME)) == null) {
                logger.info("\u6b63\u5728\u521b\u5efa\u6388\u6743\u4fe1\u606f\u8868");
                FrameworkOperator.createLicenceTable(conn, db);
            }
            if ((lockTable = metadata.getTableByName(LOCK_TABLE_NAME)) == null) {
                logger.info("\u6b63\u5728\u521b\u5efa\u6a21\u5757\u5347\u7ea7\u4e92\u65a5\u9501\u8868");
                FrameworkOperator.createLockTable(conn, db);
            }
            ModuleUpgradeLockOperator.releaseLock();
            LogicTable upgradeLogTable = metadata.getTableByName(UPGRADE_LOG_TABLE_NAME);
            if (upgradeLogTable == null) {
                logger.info("\u6b63\u5728\u521b\u5efa\u6a21\u5757\u5347\u7ea7\u65e5\u5fd7\u8868");
                FrameworkOperator.createUpgradeLogTable(conn, db);
            }
            if ((legacyModuleTable = metadata.getTableByName(LEGACY_MODULE_TABLE_NAME)) == null) {
                logger.info("\u6b63\u5728\u521b\u5efa\u65e7\u7248\u672c\u6a21\u5757\u4fe1\u606f\u8868");
                FrameworkOperator.createLegacyModuleTable(conn, db);
            }
        }
        catch (SQLMetadataException e) {
            logger.error("\u68c0\u6d4b\u670d\u52a1\u5668\u6846\u67b6\u7248\u672c\u51fa\u73b0\u9519\u8bef", e);
            throw new SQLException(e);
        }
        logger.info("\u670d\u52a1\u5668\u6846\u67b6\u7248\u672c\u4fe1\u606f\u68c0\u6d4b\u5b8c\u6bd5");
        return db;
    }

    private static void createLegacyModuleTable(Connection conn, IDatabase db) throws SQLException {
        StringBuilder buff = new StringBuilder();
        buff.append("CREATE TABLE ").append(LEGACY_MODULE_TABLE_NAME).append(" (");
        buff.append("MODULEID").append(" varchar(50) not null, ");
        buff.append("MODULEVERSION").append(" varchar(50) not null, ");
        buff.append("CONSTRAINT PK_").append(LEGACY_MODULE_TABLE_NAME).append(" PRIMARY KEY (").append("MODULEID").append(")");
        buff.append(");");
        SQLCommandParser sqlParser = new SQLCommandParser();
        try {
            List commands = sqlParser.parse(db, buff.toString());
            for (SQLCommand command : commands) {
                logger.debug("\t\u6b63\u5728\u6267\u884cSQL:{}", (Object)command.toString(db.getName()));
                command.execute(conn);
            }
        }
        catch (SQLParserException e) {
            throw new SQLException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void createModuleTable(Connection conn, IDatabase db) throws SQLException {
        CreateTableStatement statement = new CreateTableStatement("", MODULE_TABLE_NAME);
        LogicField lf = FrameworkOperator.createLogicField("MODULEID", "", 6, false, 50, -1, -1);
        statement.addColumn(lf);
        lf = FrameworkOperator.createLogicField("MODULEVERSION", "", 6, false, 50, -1, -1);
        statement.addColumn(lf);
        lf = FrameworkOperator.createLogicField(TABLE_FIELD_UPDATE, "", 3, true, 18, 18, 0);
        statement.addColumn(lf);
        lf = FrameworkOperator.createLogicField(TABLE_FIELD_FROMVERSION, "", 6, true, 50, -1, -1);
        statement.addColumn(lf);
        lf = FrameworkOperator.createLogicField(TABLE_FIELD_GUID, "", 6, false, 32, -1, -1);
        statement.addColumn(lf);
        lf = FrameworkOperator.createLogicField(TABLE_FIELD_TAG, "", 5, true, 1, 1, 0);
        statement.addColumn(lf);
        statement.getPrimaryKeys().add(TABLE_FIELD_GUID);
        try {
            List sqls = statement.interpret(db, conn);
            try (Statement stmt = conn.createStatement();){
                for (String sql : sqls) {
                    logger.debug("\t\u6b63\u5728\u6267\u884cSQL:{}", (Object)sql);
                    stmt.execute(sql);
                }
            }
        }
        catch (SQLInterpretException e) {
            throw new SQLException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void createLogTable(Connection conn, IDatabase db) throws SQLException {
        CreateTableStatement statement = new CreateTableStatement("", LOG_TABLE_NAME);
        LogicField lf = FrameworkOperator.createLogicField(TABLE_FIELD_GUID, "", 6, false, 32, -1, -1);
        statement.addColumn(lf);
        lf = FrameworkOperator.createLogicField(TABLE_FIELD_SQL, "", 6, true, 1000, -1, -1);
        statement.addColumn(lf);
        lf = FrameworkOperator.createLogicField(TABLE_FIELD_MESSEGE, "", 6, true, 4000, -1, -1);
        statement.addColumn(lf);
        lf = FrameworkOperator.createLogicField(TABLE_FIELD_ORDER, "", 6, true, 8, -1, -1);
        statement.addColumn(lf);
        statement.getPrimaryKeys().add(TABLE_FIELD_GUID);
        try {
            List sqls = statement.interpret(db, conn);
            try (Statement stmt = conn.createStatement();){
                for (String sql : sqls) {
                    logger.debug("\t\u6b63\u5728\u6267\u884cSQL:{}", (Object)sql);
                    stmt.execute(sql);
                }
            }
        }
        catch (SQLInterpretException e) {
            SQLException sqle = new SQLException(e.getMessage());
            sqle.setStackTrace(e.getStackTrace());
            throw sqle;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void createLicenceTable(Connection conn, IDatabase db) throws SQLException {
        try {
            CreateTableStatement statement = new CreateTableStatement("", LICENCE_TABLE_NAME);
            LogicField productField = new LogicField();
            productField.setFieldName(TABLE_LICENCE_PRODUCTID);
            productField.setFieldTitle("\u4ea7\u54c1ID");
            productField.setDataType(6);
            productField.setNullable(false);
            productField.setSize(100);
            statement.addColumn(productField);
            LogicField bytesField = new LogicField();
            bytesField.setFieldName(TABLE_LICENCE_BYTES);
            bytesField.setFieldTitle("\u6388\u6743\u6587\u4ef6");
            bytesField.setDataType(9);
            bytesField.setNullable(true);
            statement.addColumn(bytesField);
            List sqls = statement.interpret(db, conn);
            try (Statement stmt = conn.createStatement();){
                for (String sql : sqls) {
                    logger.debug("\t\u6b63\u5728\u6267\u884cSQL:{}", (Object)sql);
                    stmt.execute(sql);
                }
            }
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void createLockTable(Connection conn, IDatabase db) throws SQLException {
        try {
            CreateTableStatement statement = new CreateTableStatement("", LOCK_TABLE_NAME);
            LogicField keyField = new LogicField();
            keyField.setFieldName(LOCK_TABLE_FIELD_KEY);
            keyField.setDataType(6);
            keyField.setNullable(false);
            keyField.setSize(100);
            statement.addColumn(keyField);
            LogicField moduleField = new LogicField();
            moduleField.setFieldName(LOCK_TABLE_FIELD_MODULE);
            moduleField.setFieldTitle("\u6a21\u5757ID");
            moduleField.setNullable(true);
            moduleField.setDataType(6);
            moduleField.setSize(200);
            statement.addColumn(moduleField);
            LogicField machineField = new LogicField();
            machineField.setFieldName(LOCK_TABLE_FIELD_MACHINE);
            machineField.setFieldTitle("\u673a\u5668\u540d");
            machineField.setNullable(true);
            machineField.setDataType(6);
            machineField.setSize(200);
            statement.addColumn(machineField);
            LogicField timestampField = new LogicField();
            timestampField.setFieldName(LOCK_TABLE_FIELD_TIMESTAMP);
            timestampField.setFieldTitle("\u65f6\u95f4\u6233");
            timestampField.setNullable(true);
            timestampField.setDataType(3);
            timestampField.setSize(20);
            timestampField.setPrecision(20);
            timestampField.setScale(0);
            statement.addColumn(timestampField);
            List sqls = statement.interpret(db, conn);
            sqls.add(String.format("INSERT INTO %s (%s) VALUES ('%s')", LOCK_TABLE_NAME, LOCK_TABLE_FIELD_KEY, LOCK_TABLE_GLOBAL_VALUE));
            try (Statement stmt = conn.createStatement();){
                for (String sql : sqls) {
                    logger.debug("\t\u6b63\u5728\u6267\u884cSQL:{}", (Object)sql);
                    stmt.execute(sql);
                }
            }
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void createUpgradeLogTable(Connection conn, IDatabase db) throws SQLException {
        try {
            CreateTableStatement statement = new CreateTableStatement("", UPGRADE_LOG_TABLE_NAME);
            LogicField moduleField = new LogicField();
            moduleField.setFieldName(UPGRADE_LOG_TABLE_FIELD_SQL_LOG);
            moduleField.setFieldTitle("\u5347\u7ea7SQL");
            moduleField.setNullable(true);
            moduleField.setDataType(6);
            moduleField.setSize(2000);
            statement.addColumn(moduleField);
            LogicField machineField = new LogicField();
            machineField.setFieldName(UPGRADE_LOG_TABLE_FIELD_MACHINE);
            machineField.setFieldTitle("\u673a\u5668\u540d");
            machineField.setNullable(true);
            machineField.setDataType(6);
            machineField.setSize(200);
            statement.addColumn(machineField);
            LogicField logTypeField = new LogicField();
            logTypeField.setFieldName(UPGRADE_LOG_TABLE_FIELD_LOG_TYPE);
            logTypeField.setFieldTitle("\u65e5\u5fd7\u7c7b\u578b");
            logTypeField.setNullable(true);
            logTypeField.setDataType(5);
            logTypeField.setSize(1);
            statement.addColumn(logTypeField);
            LogicField timestampField = new LogicField();
            timestampField.setFieldName(UPGRADE_LOG_TABLE_FIELD_TIMESTAMP);
            timestampField.setFieldTitle("\u65f6\u95f4\u6233");
            timestampField.setNullable(true);
            timestampField.setDataType(3);
            timestampField.setSize(20);
            timestampField.setPrecision(20);
            timestampField.setScale(0);
            statement.addColumn(timestampField);
            List sqls = statement.interpret(db, conn);
            try (Statement stmt = conn.createStatement();){
                for (String sql : sqls) {
                    logger.debug("\t\u6b63\u5728\u6267\u884cSQL:{}", (Object)sql);
                    stmt.execute(sql);
                }
            }
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private static LogicField createLogicField(String fieldName, String fieldTitle, int type, boolean nullable, int size, int precision, int scale) {
        LogicField f = new LogicField();
        f.setFieldName(fieldName);
        f.setFieldTitle(fieldTitle);
        f.setDataType(type);
        f.setNullable(nullable);
        if (size != -1) {
            f.setSize(size);
        }
        if (precision != -1) {
            f.setPrecision(precision);
        }
        if (scale != -1) {
            f.setScale(scale);
        }
        return f;
    }

    public static void generateProductInfo(Framework framework, JSONObject json) throws LicenceException {
        json.put("productid", (Object)framework.getProductId());
        json.put("version", (Object)framework.getProductVersion());
        MachineCodeGenerator.MachineInfo machineInfo = framework.getLicenceManager().getMachineInfo(framework.getProductId());
        if (machineInfo == null) {
            try {
                MachineCodeGenerator.createMachineInfo((String)framework.getProductId(), (String)"");
                machineInfo = framework.getLicenceManager().getMachineInfo(framework.getProductId());
            }
            catch (Exception e) {
                logger.error("\u673a\u5668\u7801\u751f\u6210\u5931\u8d25", e);
            }
        }
        json.put("machineInfo", (Object)machineInfo.getMachineCode());
        boolean useCenterMode = framework.useAuthzCenterMode();
        json.put("useCenterMode", useCenterMode);
        JSONObject licenceJson = new JSONObject();
        LicenceManager licenceManager = framework.getLicenceManager();
        try {
            String productId = framework.getProductId();
            LicenceInfo info = licenceManager.getProductLicence(productId);
            String productLicenceInfo = FrameworkOperator.getProductLicenceInfo(info);
            licenceJson.put("data", (Object)productLicenceInfo);
            licenceJson.put("productid", (Object)productId);
        }
        catch (Exception e) {
            logger.error("\u751f\u6210\u6388\u6743\u4fe1\u606f\u5931\u8d25", e);
        }
        json.put("licences", (Object)licenceJson);
    }

    private static String getProductLicenceInfo(LicenceInfo info) {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append("\u6388\u6743\u7ed9\u3010").append(info.getUseFor()).append("\u3011\n");
        Framework.getInstance();
        if (Framework.useAuthzCenterMode() && Framework.getInstance().getClientSocketService() != null) {
            ClientSocketService clientSocketService = Framework.getInstance().getClientSocketService();
            JSONObject serviceParam = clientSocketService.getServiceParam();
            String authzCenterAddress = serviceParam.optString("authzCenterAddress");
            String serverPort = serviceParam.optString("serverPort");
            sb.append("\u96c6\u4e2d\u6388\u6743\u5730\u5740\uff1a" + authzCenterAddress + "\n");
        }
        sb.append("------------------------------------------------------------\n");
        sb.append("\u6388\u6743\u751f\u6210\u65f6\u95f4\uff1a").append(sdf.format(new Date(info.getTimeStamp()))).append("\n");
        sb.append("\u670d\u52a1\u622a\u6b62\u65f6\u95f4\uff1a").append(sdf.format(new Date(info.getExpiryTime()))).append("\n");
        sb.append("\u4f7f\u7528\u5230\u671f\u65f6\u95f4\uff1a").append(sdf.format(new Date(info.getUseTime()))).append("\n");
        sb.append("------------------------------------------------------------\n");
        sb.append("\u6388\u6743\u6a21\u5757\uff1a");
        if (info.getModules().isEmpty()) {
            sb.append("\u65e0\u66f4\u591a\u4fe1\u606f");
        } else {
            sb.append("\n");
        }
        for (ModuleInfo mi : info.getModules()) {
            sb.append("\t").append(mi.getTitle()).append("(").append(mi.getId()).append(")\n");
            for (FuncPointInfo fpi : mi.getFuncPoints()) {
                sb.append("\t\t").append(fpi.getTitle()).append("(").append(fpi.getId()).append(") : ").append(fpi.getValue()).append("\n");
            }
        }
        return sb.toString();
    }

    public static void generateModuleInfo(Framework framework, JSONObject json) throws Exception {
        json.put("version", (Object)framework.getProductVersion());
        json.put("productId", (Object)framework.getProductId());
        JSONArray array = new JSONArray();
        List<ModuleDescriptor> modules = framework.getModules();
        for (ModuleDescriptor module : modules) {
            JSONObject moduleJson = new JSONObject();
            moduleJson.put("moduleId", (Object)module.getId());
            moduleJson.put("name", (Object)module.getName());
            moduleJson.put("version", (Object)module.getVersion());
            ModuleWrapper wrapper = framework.getModuleWrappers().get(module.getId());
            moduleJson.put("status", (Object)wrapper.getStatus());
            array.put((Object)moduleJson);
        }
        json.put("modules", (Object)array);
    }
}

