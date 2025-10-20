/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.command.StatementCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Propagation
 */
package com.jiuqi.dc.base.impl.definition;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.command.StatementCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class DcTempTableDefinitionService {
    @Value(value="classpath:/template/tempTableDefine.json")
    private Resource tempTableDefine;

    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void initTableDefine(DefinitionTableV tableDefine) throws Exception {
        String dbType = OuterDataSourceUtils.getJdbcTemplate().getDbType();
        if (!CollectionUtils.newArrayList((Object[])new String[]{"HANA", "ORACLE", "DAMENG", "MYSQL", "KINGBASE"}).contains(dbType.toUpperCase())) {
            return;
        }
        if ("MYSQL".equalsIgnoreCase(dbType)) {
            OuterDataSourceUtils.getJdbcTemplate().execute("DROP TABLE IF EXISTS " + tableDefine.getTableName());
            return;
        }
        IDbSqlHandler dbSqlHandler = SqlHandlerUtil.getDbSqlHandler((String)dbType);
        Integer tempTableCount = (Integer)OuterDataSourceUtils.getJdbcTemplate().queryForObject(dbSqlHandler.getTempTableJudgeSql(tableDefine.getTableName()), Integer.class);
        if (tempTableCount == 0) {
            String createTableSql = this.getCreateTableSql(tableDefine);
            StringBuilder sql = new StringBuilder(createTableSql.replace(");", ""));
            List<DefinitionFieldV> extendFieldList = this.getExtendFieldList(tableDefine);
            if (!CollectionUtils.isEmpty(extendFieldList)) {
                StringJoiner joiner = new StringJoiner(",\n");
                for (DefinitionFieldV dimension : extendFieldList) {
                    if (dimension.getCode().equals("EXPIREDATE")) continue;
                    joiner.add(this.getDimFieldSql(dimension));
                }
                sql.append(",\n").append(joiner);
            }
            sql.append(")");
            String createTempTableSql = this.getCreateTempTableSql(sql.toString(), tableDefine.getTempTableType());
            OuterDataSourceUtils.getJdbcTemplate().update("DROP TABLE " + tableDefine.getTableName());
            OuterDataSourceUtils.getJdbcTemplate().execute(createTempTableSql);
        }
    }

    @NotNull
    private List<DefinitionFieldV> getExtendFieldList(DefinitionTableV tableDefine) {
        BaseEntity sourceEntity;
        BaseEntity entity;
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        DBTable dbTableByType = entityTableCollector.getDbTableByType((entity = entityTableCollector.getEntityByName(tableDefine.getTableName())).getClass());
        if (!StringUtils.isEmpty((String)dbTableByType.sourceTable()) && (sourceEntity = entityTableCollector.getEntityByName(dbTableByType.sourceTable())) instanceof ITableExtend) {
            return ((ITableExtend)sourceEntity).getExtendFieldList(null);
        }
        if (entity instanceof ITableExtend) {
            return ((ITableExtend)entity).getExtendFieldList(null);
        }
        return CollectionUtils.newArrayList();
    }

    private String getCreateTableSql(DefinitionTableV tableDefine) {
        if (!CollectionUtils.isEmpty((Collection)tableDefine.getFields())) {
            StringJoiner joiner = new StringJoiner("\n\t");
            joiner.add("CREATE TABLE " + tableDefine.getTableName() + "(");
            tableDefine.getFields().sort((o1, o2) -> (int)(o1.getOrder() - o2.getOrder()));
            for (int i = 0; i < tableDefine.getFields().size(); ++i) {
                DefinitionFieldV field = (DefinitionFieldV)tableDefine.getFields().get(i);
                if (i == tableDefine.getFields().size() - 1) {
                    joiner.add(String.format("%1$s %2$s %3$s", field.getFieldName(), this.getDbTypeSql(field), this.getDbConstraintSql(field)));
                    continue;
                }
                joiner.add(String.format("%1$s %2$s %3$s,", field.getFieldName(), this.getDbTypeSql(field), this.getDbConstraintSql(field)));
            }
            joiner.add(");");
            return joiner.toString();
        }
        return "";
    }

    private String getDbTypeSql(DefinitionFieldV field) {
        StringBuilder builder = new StringBuilder("");
        switch (field.getDbType()) {
            case NVarchar: {
                builder.append("NVARCHAR(").append(field.getSize()).append(")");
                break;
            }
            case Varchar: {
                builder.append("VARCHAR(").append(field.getSize()).append(")");
                break;
            }
            case Date: {
                builder.append("DATE");
                break;
            }
            case DateTime: {
                builder.append("TIMESTAMP");
                break;
            }
            case Text: {
                builder.append("CLOB");
                break;
            }
            case Blob: {
                builder.append("BLOB");
                break;
            }
            case Boolean: 
            case Int: {
                builder.append("NUMBER(").append(field.getSize()).append(",0)");
                break;
            }
            case Long: 
            case Double: 
            case Numeric: {
                builder.append("NUMBER(").append(field.getSize()).append(",").append(field.getFractionDigits()).append(")");
                break;
            }
            default: {
                builder.append("NVARCHAR(").append(field.getSize()).append(")");
            }
        }
        return builder.toString();
    }

    private String getDbConstraintSql(DefinitionFieldV field) {
        StringBuilder builder = new StringBuilder("");
        if (!StringUtils.isEmpty((String)field.getDefaultValue())) {
            builder.append("DEFAULT ").append(field.getDefaultValue());
        }
        if (!field.isNullable()) {
            builder.append(" NOT");
        }
        builder.append(" NULL");
        return builder.toString();
    }

    private String getDimFieldSql(DefinitionFieldV dimension) {
        if (Arrays.asList(DBColumn.DBType.Varchar, DBColumn.DBType.NVarchar).contains(dimension.getDbType())) {
            return String.format("%1$s NVARCHAR(%2$d) DEFAULT %3$s", dimension.getCode(), dimension.getSize(), "'#'");
        }
        if (Arrays.asList(DBColumn.DBType.Int, DBColumn.DBType.Long, DBColumn.DBType.Double, DBColumn.DBType.Numeric).contains(dimension.getDbType())) {
            return String.format("%1$s NUMBER(%2$d,%3$d) DEFAULT %4$s", dimension.getCode(), dimension.getSize(), dimension.getFractionDigits(), "0.00");
        }
        if (Arrays.asList(DBColumn.DBType.DateTime, DBColumn.DBType.Date).contains(dimension.getDbType())) {
            return String.format("%1$s NVARCHAR(60) DEFAULT '%2$s'", dimension.getCode(), "1970-01-01");
        }
        return "";
    }

    public List<String> initTableScope() {
        return CollectionUtils.newArrayList((Object[])new String[]{"DC_TEMP_VCHRITEMASS"});
    }

    public void createTemporaryTable(String tableName) {
        String dbType = OuterDataSourceUtils.getJdbcTemplate().getDbType();
        String createSql = this.getCreateTableSql(tableName);
        if (Objects.equals(dbType.toUpperCase(), "MYSQL") && !StringUtils.isEmpty((String)createSql)) {
            OuterDataSourceUtils.getJdbcTemplate().update(createSql);
        }
    }

    private String getCreateTableSql(String tableName) {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        BaseEntity baseEntity = entityTableCollector.getEntitys().stream().filter(e -> e.getClass().getName().startsWith("com.jiuqi.dc")).filter(entity -> Objects.equals(entity.getTableName(), tableName) && !Objects.equals(entityTableCollector.getDbTableByType(entity.getClass()).tempTableType(), TemporaryTableTypeEnum.PHYSICAL)).findFirst().orElse(null);
        if (Objects.nonNull(baseEntity)) {
            TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)baseEntity, (DBTable)entityTableCollector.getDbTableByType(baseEntity.getClass()));
            DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
            String baseSql = this.getCreateTableSql(tableDefine);
            StringBuilder sql = new StringBuilder(baseSql.replace(");", ""));
            List<DefinitionFieldV> extendFieldList = this.getExtendFieldList(tableDefine);
            if (!CollectionUtils.isEmpty(extendFieldList)) {
                StringJoiner joiner = new StringJoiner(",\n");
                for (DefinitionFieldV dimension : extendFieldList) {
                    if (dimension.getCode().equals("EXPIREDATE")) continue;
                    joiner.add(this.getDimFieldSql(dimension));
                }
                sql.append(",\n").append(joiner);
            }
            sql.append(")");
            return this.getCreateTempTableSql(sql.toString(), tableDefine.getTempTableType());
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getCreateTempTableSql(String baseSql, TemporaryTableTypeEnum tempTableType) {
        String dbType = OuterDataSourceUtils.getJdbcTemplate().getDbType();
        try (Connection connection = OuterDataSourceUtils.getDataSource().getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            if (database == null) {
                throw new BusinessRuntimeException("\u83b7\u53d6IDatabase\u5931\u8d25");
            }
            List sqlCommands = new SQLCommandParser().parse(database, baseSql);
            StatementCommand statementCommand = (StatementCommand)sqlCommands.get(0);
            ISQLInterpretor sqlInterpretor = database.createSQLInterpretor(connection);
            List createSqls = sqlInterpretor.createTable((CreateTableStatement)statementCommand.getStatement());
            String excuteSql = (String)createSqls.get(0);
            if (Objects.equals(dbType.toUpperCase(), "MYSQL")) {
                String string = excuteSql = excuteSql.replaceFirst("(?i)create", "CREATE TEMPORARY ");
                return string;
            }
            if (Objects.equals(dbType.toUpperCase(), "KINGBASE")) {
                String string = excuteSql = excuteSql.replaceFirst("(?i)create", "CREATE GLOBAL TEMP ");
                return string;
            }
            if (TemporaryTableTypeEnum.SESSION.equals((Object)tempTableType)) {
                excuteSql = excuteSql.replaceFirst("(?i)create", "CREATE GLOBAL TEMPORARY ");
                excuteSql = excuteSql + " ON COMMIT PRESERVE ROWS";
            }
            if (TemporaryTableTypeEnum.TRANSACTION.equals((Object)tempTableType)) {
                excuteSql = excuteSql.replaceFirst("(?i)create", "CREATE GLOBAL TEMPORARY ");
                excuteSql = excuteSql + " ON COMMIT DELETE ROWS";
            }
            String string = excuteSql;
            return string;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u521d\u59cb\u5316\u4e34\u65f6\u8868\u7ffb\u8bd1\u5931\u8d25\uff0c\u8bf7\u5c1d\u8bd5\u624b\u52a8\u521b\u5efa\u4e34\u65f6\u8868\uff1a\n" + baseSql, (Throwable)e);
        }
    }

    public void dropTemporaryTable(String tableName) {
        String dbType = OuterDataSourceUtils.getJdbcTemplate().getDbType();
        if (!Objects.equals(dbType.toUpperCase(), "MYSQL")) {
            return;
        }
        String dropSql = this.getDropTableSql(tableName);
        if (!StringUtils.isEmpty((String)dropSql)) {
            OuterDataSourceUtils.getJdbcTemplate().update(dropSql);
        }
    }

    private String getDropTableSql(String tableName) {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        BaseEntity baseEntity = entityTableCollector.getEntitys().stream().filter(e -> e.getClass().getName().startsWith("com.jiuqi.dc")).filter(entity -> Objects.equals(entity.getTableName(), tableName) && !Objects.equals(entityTableCollector.getDbTableByType(entity.getClass()).tempTableType(), TemporaryTableTypeEnum.PHYSICAL)).findFirst().orElse(null);
        if (Objects.nonNull(baseEntity)) {
            return "DROP TABLE " + tableName;
        }
        return "";
    }

    public boolean cleanLastBatch(String tableName) {
        String dbType = OuterDataSourceUtils.getJdbcTemplate().getDbType();
        if (!CollectionUtils.newArrayList((Object[])new String[]{"HANA", "ORACLE", "DAMENG", "MYSQL", "KINGBASE"}).contains(dbType.toUpperCase())) {
            return Boolean.TRUE;
        }
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        BaseEntity baseEntity = entityTableCollector.getEntitys().stream().filter(e -> e.getClass().getName().startsWith("com.jiuqi.dc")).filter(entity -> Objects.equals(entity.getTableName(), tableName) && !Objects.equals(entityTableCollector.getDbTableByType(entity.getClass()).tempTableType(), TemporaryTableTypeEnum.PHYSICAL)).findFirst().orElse(null);
        return !Objects.nonNull(baseEntity);
    }
}

