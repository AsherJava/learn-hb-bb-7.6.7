/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataEngineUtil {
    public static final Logger logger = LoggerFactory.getLogger(DataEngineUtil.class);
    public static final int MAXINPARAM = 1000;
    public static final int FETCHSIZE = 100;

    public static int getMaxInSize(IDatabase database) {
        if (database.isDatabase("OSCAR")) {
            return 2000;
        }
        if (database.isDatabase("Informix")) {
            return 50;
        }
        return 1000;
    }

    public static boolean needJoinTempTable(IDatabase database) {
        return database.isDatabase("DM") || database.isDatabase("Informix");
    }

    public static String getDimensionFieldName(TableModelRunInfo tableInfo, String dimension) {
        String keyfieldName = dimension;
        ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimension);
        if (dimensionField != null) {
            keyfieldName = dimensionField.getName();
        }
        return keyfieldName;
    }

    public static SqlQueryHelper createSqlQueryHelper() {
        return new SqlQueryHelper();
    }

    @Deprecated
    public static ResultSet executeQuery(Connection conn, String sql) throws SQLException {
        return DataEngineUtil.executeQuery(conn, sql, null, null);
    }

    @Deprecated
    public static ResultSet executeQuery(Connection conn, String sql, IMonitor monitor) throws SQLException {
        return DataEngineUtil.executeQuery(conn, sql, null, monitor);
    }

    @Deprecated
    public static ResultSet executeQuery(Connection conn, String sql, Object[] args) throws SQLException {
        return DataEngineUtil.executeQuery(conn, sql, args, null);
    }

    @Deprecated
    public static ResultSet executeQuery(Connection conn, String sql, Object[] args, IMonitor monitor) throws SQLException {
        return null;
    }

    public static MemoryDataSet<QueryField> queryMemoryDataSet(Connection conn, String sql, Object[] args, IMonitor monitor) throws SQLException {
        return DataEngineUtil.queryMemoryDataSet(conn, null, sql, args, monitor);
    }

    /*
     * Exception decompiling
     */
    public static MemoryDataSet<QueryField> queryMemoryDataSet(Connection conn, String tableName, String sql, Object[] args, IMonitor monitor) throws SQLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static void sqlToLogger(String sql, IMonitor monitor) {
        if (monitor != null) {
            monitor.debug(sql, DataEngineConsts.DebugLogType.SQL);
        } else {
            logger.debug(sql);
        }
    }

    private static void setValue(Object value, PreparedStatement prep, int i) throws SQLException {
        if (value instanceof UUID) {
            prep.setBytes(i + 1, Convert.toBytes((UUID)((UUID)value)));
        } else if (value instanceof Date) {
            Timestamp timestamp = new Timestamp(((Date)value).getTime());
            prep.setTimestamp(i + 1, timestamp);
        } else {
            prep.setObject(i + 1, value);
        }
    }

    public static int executeUpdate(Connection conn, String sql, Object[] args) throws SQLException {
        return DataEngineUtil.executeUpdate(conn, sql, args, null);
    }

    public static int executeUpdate(Connection conn, String sql, Object[] args, IMonitor monitor) throws SQLException {
        int result = -1;
        try (PreparedStatement prep = conn.prepareStatement(sql);){
            DataEngineUtil.sqlToLogger(sql, monitor);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    DataEngineUtil.setValue(args[i], prep, i);
                }
            }
            result = prep.executeUpdate();
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder();
            msg.append("sql: ").append(sql).append("\n");
            msg.append("args: ").append(Arrays.toString(args)).append("\n");
            msg.append("operation: ").append("update").append("\n");
            msg.append(e.getMessage());
            logger.error(msg.toString(), e);
            throw new SQLException("\u6570\u636e\u66f4\u65b0\u51fa\u9519", e);
        }
        return result;
    }

    public static int executeUpdate(QueryParam queryParam, String sql, Object[] args, IMonitor monitor) throws SQLException {
        Connection connection = queryParam.getConnection();
        int result = DataEngineUtil.executeUpdate(connection, sql, args, monitor);
        queryParam.closeConnection();
        return result;
    }

    @Deprecated
    public static ResultSet executeQueryByScroll(Connection conn, String sql, Object[] args) throws SQLException {
        return DataEngineUtil.executeQueryByScroll(conn, sql, args, null);
    }

    @Deprecated
    public static ResultSet executeQueryByScroll(Connection conn, String sql, Object[] args, IMonitor monitor) throws SQLException {
        return null;
    }

    public static void batchUpdate(Connection conn, String sql, List<Object[]> batchValues) throws SQLException {
        DataEngineUtil.batchUpdate(conn, sql, batchValues, null);
    }

    public static void batchUpdate(Connection conn, String sql, List<Object[]> batchValues, IMonitor monitor) throws SQLException {
        try (PreparedStatement prep = conn.prepareStatement(sql);){
            DataEngineUtil.sqlToLogger(sql, monitor);
            int batchSize = 1000;
            int count = 0;
            for (Object[] batchValue : batchValues) {
                for (int i = 0; i < batchValue.length; ++i) {
                    DataEngineUtil.setValue(batchValue[i], prep, i);
                }
                prep.addBatch();
                if (++count % 1000 != 0) continue;
                prep.executeBatch();
            }
            prep.executeBatch();
        }
        catch (Exception e) {
            StringBuilder msg = new StringBuilder();
            msg.append("sql: ").append(sql).append("\n");
            msg.append("batchCount: ").append(batchValues.size()).append("\n");
            if (logger.isDebugEnabled()) {
                StringBuilder args = new StringBuilder();
                for (Object[] batchValue : batchValues) {
                    for (Object o : batchValue) {
                        args.append(o).append(",");
                    }
                    args.deleteCharAt(args.length() - 1);
                    args.append("\n");
                }
                msg.append("args: ").append((CharSequence)args);
            }
            msg.append("operation: ").append("batchUpdate").append("\n");
            msg.append(e.getMessage());
            logger.error(msg.toString(), e);
            throw new SQLException("\u6570\u636e\u66f4\u65b0\u51fa\u9519", e);
        }
    }

    public static void execute(Connection conn, String sql, IMonitor monitor) throws SQLException {
        try (PreparedStatement prep = conn.prepareStatement(sql);){
            DataEngineUtil.sqlToLogger(sql, monitor);
            prep.execute();
        }
    }

    public static String buildGUIDValueSql(IDatabase database, UUID uuid) {
        StringBuilder buff = new StringBuilder();
        if (database.isDatabase("ORACLE")) {
            buff.append("hextoraw('");
            DataEngineUtil.uuidToBuffer(buff, uuid);
            buff.append("')");
        } else if (database.isDatabase("DM") || database.isDatabase("DB2")) {
            buff.append("'x");
            DataEngineUtil.uuidToBuffer(buff, uuid);
            buff.append("'");
        } else if (database.isDatabase("MYSQL")) {
            buff.append("unhex('");
            DataEngineUtil.uuidToBuffer(buff, uuid);
            buff.append("')");
        } else if (database.isDatabase("MSSQL")) {
            buff.append("0x");
            DataEngineUtil.uuidToBuffer(buff, uuid);
        } else if (database.isDatabase("HANA")) {
            buff.append("hextobin('");
            DataEngineUtil.uuidToBuffer(buff, uuid);
            buff.append("')");
        } else {
            buff.append("'");
            DataEngineUtil.uuidToBuffer(buff, uuid);
            buff.append("'");
        }
        return buff.toString();
    }

    private static void uuidToBuffer(StringBuilder buff, UUID value) {
        int i;
        if (value == null) {
            return;
        }
        byte[] bytes = new byte[16];
        long sb = value.getLeastSignificantBits();
        for (i = 15; i >= 8; --i) {
            bytes[i] = (byte)sb;
            sb >>>= 8;
        }
        sb = value.getMostSignificantBits();
        for (i = 7; i >= 0; --i) {
            bytes[i] = (byte)sb;
            sb >>>= 8;
        }
        for (byte b : bytes) {
            int j = b >>> 4 & 0xF;
            j = j > 9 ? j - 10 + 65 : j + 48;
            buff.append((char)j);
            j = b & 0xF;
            j = j > 9 ? j - 10 + 65 : j + 48;
            buff.append((char)j);
        }
    }

    public static String buildcreateUUIDSql(IDatabase database, boolean isStr) {
        if (isStr) {
            if (database.isDatabase("ORACLE") || database.isDatabase("dbcp")) {
                return "RAWTOHEX(SYS_GUID())";
            }
            if (database.isDatabase("MYSQL") || database.isDatabase("TDSQL") || database.isDatabase("H2")) {
                return "UUID()";
            }
            if (database.isDatabase("MSSQL")) {
                return "cast(NEWID() as varchar(36))";
            }
            if (database.isDatabase("DB2")) {
                return "CHAR(concat(hex(RAND()), hex(RAND())))";
            }
            if (database.isDatabase("Informix") || database.isDatabase("GBase8s") || database.isDatabase("OSCAR") || database.isDatabase("GAUSSDB100") || database.isDatabase("DM") || database.isDatabase("VastBase")) {
                return "SYS_GUID()";
            }
            if (database.isDatabase("HANA")) {
                return "cast(SYSUUID AS varchar)";
            }
            if (database.isDatabase("KINGBASE") || database.isDatabase("KINGBASE8")) {
                return "SYS_GUID_NAME()";
            }
            if (database.isDatabase("DERBY")) {
                return "'abdfbbvbg-' ||cast(cast(floor(1 + random()*1000007) as bigint) as char(7)) || '-ghccfa-kadd' || cast(cast(floor(1 + random()*10008) as bigint) as char(5)) ";
            }
            if (database.isDatabase("POSTGRESQL") || database.isDatabase("GaussDB") || database.isDatabase("OpenGaussDB") || database.isDatabase("polardb")) {
                return "md5(random()::text || clock_timestamp()::text)::uuid::text";
            }
        } else {
            if (database.isDatabase("ORACLE") || database.isDatabase("Informix") || database.isDatabase("GBase8s") || database.isDatabase("OSCAR") || database.isDatabase("GAUSSDB100") || database.isDatabase("DM") || database.isDatabase("VastBase") || database.isDatabase("dbcp")) {
                return "SYS_GUID()";
            }
            if (database.isDatabase("MYSQL") || database.isDatabase("TDSQL") || database.isDatabase("H2")) {
                return "UUID()";
            }
            if (database.isDatabase("MSSQL")) {
                return "NEWID()";
            }
            if (database.isDatabase("DB2")) {
                return "concat(hex(RAND()), hex(RAND()))";
            }
            if (database.isDatabase("HANA")) {
                return "SYSUUID";
            }
            if (database.isDatabase("KINGBASE") || database.isDatabase("KINGBASE8")) {
                return "SYS_GUID_NAME()";
            }
            if (database.isDatabase("DERBY")) {
                return "'abdfbbvbg-' ||cast(cast(floor(1 + random()*1000007) as bigint) as char(7)) || '-ghccfa-kadd' || cast(cast(floor(1 + random()*10008) as bigint) as char(5)) ";
            }
            if (database.isDatabase("POSTGRESQL") || database.isDatabase("GaussDB") || database.isDatabase("OpenGaussDB") || database.isDatabase("polardb")) {
                return "uuid_in(md5(random()::text || now()::text)::cstring)";
            }
        }
        return null;
    }

    public static String buildDateValueSql(IDatabase database, Object value, Connection connection) {
        try {
            Date date = new Date(Convert.toDate((Object)value));
            return database.createSQLInterpretor(connection).formatSQLDate(date);
        }
        catch (SQLInterpretException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static boolean isDataBase(Connection conn, String databaseName) throws SQLException {
        IDatabase database = DatabaseInstance.getDatabase();
        return database.isDatabase(databaseName);
    }

    public static Object processLinkedUnit(QueryContext qContext, HashMap<String, Object> unitKeyMap, QueryTable queryTable, ExecutorContext exeContext, String keyName, Object keyValue) {
        List list;
        IDataModelLinkFinder dataLinkFinder;
        String linkAlias = qContext.getTableLinkAliaMap().get(queryTable);
        IDataModelLinkFinder iDataModelLinkFinder = dataLinkFinder = exeContext.getEnv() == null ? null : exeContext.getEnv().getDataModelLinkFinder();
        if (linkAlias != null && dataLinkFinder != null) {
            if (keyValue instanceof List) {
                ArrayList<Object> unitKeys = new ArrayList<Object>();
                List valueList = keyValue;
                Map<Object, List<Object>> keyValueMap = dataLinkFinder.findRelatedUnitKeyMap(exeContext, linkAlias, keyName, valueList);
                if (keyValueMap != null) {
                    for (Object v : valueList) {
                        List<Object> mapedValues = keyValueMap.get(v);
                        if (mapedValues != null && mapedValues.size() > 0) {
                            for (Object mv : mapedValues) {
                                unitKeyMap.put(mv.toString(), v);
                                unitKeys.add(mv);
                            }
                            continue;
                        }
                        qContext.getUnKnownLinkUnitSet(linkAlias).add(v.toString());
                    }
                    keyValue = unitKeys;
                }
            } else {
                List<Object> mapedKeyValues = dataLinkFinder.findRelatedUnitKey(exeContext, linkAlias, keyName, keyValue);
                if (mapedKeyValues != null && mapedKeyValues.size() > 0) {
                    for (Object mv : mapedKeyValues) {
                        unitKeyMap.put(mv.toString(), keyValue);
                    }
                    keyValue = mapedKeyValues;
                } else {
                    qContext.getUnKnownLinkUnitSet(linkAlias).add(keyValue.toString());
                    keyValue = null;
                }
            }
        }
        if (keyValue instanceof List && (list = (List)keyValue).size() == 1) {
            keyValue = list.get(0);
        }
        return keyValue;
    }

    public static Object getPeriodValue(QueryContext qContext, TableModelRunInfo tableInfo, QueryTable primaryTable, IFmlExecEnvironment env, String linkAlias, Object keyValue) {
        String periodStr = null;
        ExecutorContext exeContext = qContext.getExeContext();
        if (keyValue instanceof String) {
            int periodType;
            periodStr = (String)keyValue;
            PeriodModifier periodModifier = primaryTable.getPeriodModifier();
            int tablePeriodTpye = periodType = qContext.getExeContext().getPeriodType();
            if (tableInfo.getTablePeriodType() != null) {
                tablePeriodTpye = tableInfo.getTablePeriodType().type();
            }
            if (periodModifier == null && periodType > 0 && tablePeriodTpye != periodType) {
                periodModifier = PeriodModifier.parse((String)"-0N");
            }
            if (periodModifier != null) {
                String modifiedPeriod = null;
                if (!(linkAlias == null || exeContext.isStandardPeriod(periodType) && exeContext.isStandardPeriod(tablePeriodTpye))) {
                    modifiedPeriod = env.getPeriodAdapter(exeContext).modify(periodStr, periodModifier, env.getPeriodAdapter(exeContext, linkAlias));
                } else {
                    PeriodWrapper pw = new PeriodWrapper(periodStr);
                    if (tablePeriodTpye != periodType) {
                        periodModifier.tryCorrectType(pw, tablePeriodTpye);
                    }
                    periodStr = pw.toString();
                    modifiedPeriod = exeContext.getPeriodAdapter().modify(periodStr, periodModifier);
                }
                periodStr = StringUtils.isNotEmpty((String)modifiedPeriod) ? modifiedPeriod : "1900N0001";
            }
        }
        if (periodStr != null) {
            keyValue = periodStr;
        }
        return keyValue;
    }
}

