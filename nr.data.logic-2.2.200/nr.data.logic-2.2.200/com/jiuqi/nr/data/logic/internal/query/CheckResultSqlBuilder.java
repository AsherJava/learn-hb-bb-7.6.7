/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.data.logic.internal.query;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConSql;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.query.SqlModel;
import com.jiuqi.nr.data.logic.internal.query.UnionModel;
import com.jiuqi.nr.data.logic.internal.query.parse.CKRNode;
import com.jiuqi.nr.data.logic.internal.query.parse.CKRNodeFinder;
import com.jiuqi.nr.data.logic.internal.query.parse.CKRQueryContext;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
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

public class CheckResultSqlBuilder
implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(CheckResultSqlBuilder.class);
    private static final String MAIN_TABLE_NAME = "t0";
    private static final String JOIN_TABLE_ALIA = "t1";
    private static final String COUNT_ALIA = "RR";
    private static final String GROUP_QUERY_ALIA = "TT";
    private static final String UNION_ALIA = "UU";
    private final FormSchemeDefine formSchemeDefine;
    private final SqlModel sqlModel;
    private final DataSource dataSource;
    private IDatabase database;
    private ReportFormulaParser parser;
    private IASTNode node;
    private CKRQueryContext queryContext;
    private String dwTableName;
    private final List<String> allQueryColumns;
    private String fmlSql;
    private final Map<String, ITempTable> tempAssistantTables = new HashMap<String, ITempTable>();
    private List<Object> sqlArgs;
    private final EntityUtil entityUtil;
    private final DataModelService dataModelService;
    private ITempTableManager tempTableManager;

    public CheckResultSqlBuilder(FormSchemeDefine formSchemeDefine, SqlModel sqlModel, DataSource dataSource) {
        this.formSchemeDefine = formSchemeDefine;
        this.sqlModel = sqlModel;
        this.dataSource = dataSource;
        ArrayList<String> columns1 = new ArrayList<String>(sqlModel.getMainColumns());
        ArrayList<String> columns2 = new ArrayList<String>(sqlModel.getJoinColumns());
        columns1.addAll(columns2);
        this.allQueryColumns = columns1;
        this.entityUtil = (EntityUtil)BeanUtil.getBean(EntityUtil.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        try {
            this.initNode();
            this.parseNode();
        }
        catch (ParseException e) {
            String msg = "\u5ba1\u6838\u6761\u4ef6\u89e3\u6790\u5f02\u5e38" + e.getMessage();
            logger.error(msg, e);
        }
        this.createTempTableIfNeed();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean exist() {
        this.sqlArgs = new ArrayList<Object>();
        String sql = this.buildExistSql().toString();
        logger.debug("\u5ba1\u6838\u662f\u5426\u5b58\u5728\u5ba1\u6838\u7ed3\u679c\u8bb0\u5f55sql:{}\n{}", (Object)sql, (Object)this.sqlArgs);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
            boolean bl = this.executeQueryExist(sql, connection);
            return bl;
        }
        catch (Exception e) {
            String msg = "\u662f\u5426\u5b58\u5728\u5ba1\u6838\u7ed3\u679c\u8bb0\u5f55\u67e5\u8be2\u5f02\u5e38:" + e.getMessage();
            logger.error(msg, e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
            }
        }
        return false;
    }

    public List<Map<String, Object>> items() {
        this.sqlArgs = new ArrayList<Object>();
        StringBuilder sql = this.buildItemSql(true);
        String sqlStr = this.appendPageIfNeed(sql);
        logger.debug("\u5ba1\u6838\u7ed3\u679c\u660e\u7ec6sql:{}\n{}", (Object)sqlStr, (Object)this.sqlArgs);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
            List<Map<String, Object>> list = this.executeQuery(sqlStr, connection);
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException("\u5ba1\u6838\u7ed3\u679c\u660e\u7ec6\u67e5\u8be2\u5f02\u5e38", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
            }
        }
    }

    public int itemsCount() {
        this.sqlArgs = new ArrayList<Object>();
        StringBuilder sql = this.buildItemCountSql();
        String sqlStr = sql.toString();
        logger.debug("\u5ba1\u6838\u7ed3\u679c\u660e\u7ec6\u6570\u76eesql:{}\n{}", (Object)sqlStr, (Object)this.sqlArgs);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
            int n = this.executeQueryCount(sqlStr, connection);
            return n;
        }
        catch (Exception e) {
            throw new RuntimeException("\u5ba1\u6838\u7ed3\u679c\u660e\u7ec6\u6570\u76ee\u67e5\u8be2\u5f02\u5e38", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
            }
        }
    }

    public List<Map<String, Object>> groups() {
        this.sqlArgs = new ArrayList<Object>();
        StringBuilder sql = this.buildGroupSql(true);
        String sqlStr = this.appendPageIfNeed(sql);
        logger.debug("\u5ba1\u6838\u7ed3\u679c\u5206\u7ec4sql:{}\n{}", (Object)sqlStr, (Object)this.sqlArgs);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
            List<Map<String, Object>> list = this.executeQuery(sqlStr, connection);
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException("\u5ba1\u6838\u7ed3\u679c\u5206\u7ec4\u67e5\u8be2\u5f02\u5e38", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
            }
        }
    }

    public int groupsCount() {
        this.sqlArgs = new ArrayList<Object>();
        StringBuilder sql = this.buildGroupCountSql();
        String sqlStr = sql.toString();
        logger.debug("\u5ba1\u6838\u7ed3\u679c\u5206\u7ec4\u6570\u76eesql:{}\n{}", (Object)sqlStr, (Object)this.sqlArgs);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
            int n = this.executeQueryCount(sqlStr, connection);
            return n;
        }
        catch (Exception e) {
            throw new RuntimeException("\u5ba1\u6838\u7ed3\u679c\u5206\u7ec4\u6570\u76ee\u67e5\u8be2\u5f02\u5e38", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
            }
        }
    }

    private void deleteTempTable() {
        for (ITempTable table : this.tempAssistantTables.values()) {
            try {
                table.close();
            }
            catch (Exception e) {
                String msg = "\u5ba1\u6838\u7ed3\u679c\u67e5\u8be2\u5220\u9664\u4e34\u65f6\u8868\u5f02\u5e38:" + e.getMessage();
                logger.error(msg, e);
            }
        }
    }

    /*
     * Exception decompiling
     */
    private List<Map<String, Object>> executeQuery(String sqlStr, Connection connection) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    /*
     * Exception decompiling
     */
    private int executeQueryCount(String sqlStr, Connection connection) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    /*
     * Exception decompiling
     */
    private boolean executeQueryExist(String sql, Connection connection) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    private StringBuilder buildItemSql(boolean order) {
        ArrayList<StringBuilder> sqlUnions = new ArrayList<StringBuilder>();
        sqlUnions.add(this.buildItemSql(order, this.sqlModel.getCheckTypes(), this.sqlModel.getDimFilters(), 0));
        if (!CollectionUtils.isEmpty(this.sqlModel.getUnionModels())) {
            for (int i = 0; i < this.sqlModel.getUnionModels().size(); ++i) {
                UnionModel unionModel = this.sqlModel.getUnionModels().get(i);
                sqlUnions.add(this.buildItemSql(order, unionModel.getCheckTypes(), unionModel.getDimFilters(), i + 1));
            }
        }
        return this.orderUnionSql(sqlUnions, order);
    }

    private StringBuilder buildItemSql(boolean order, Map<Integer, Boolean> checkType, Map<String, List<String>> dimFilters, int index) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        for (String mainColumn : this.sqlModel.getMainColumns()) {
            sql.append(MAIN_TABLE_NAME).append(".").append(mainColumn).append(",");
        }
        for (String joinColumn : this.sqlModel.getJoinColumns()) {
            sql.append(JOIN_TABLE_ALIA).append(".").append(joinColumn).append(",");
        }
        if (order) {
            for (String orderByColumn : this.sqlModel.getOrderByColumns()) {
                if (this.allQueryColumns.contains(orderByColumn)) continue;
                sql.append(MAIN_TABLE_NAME).append(".").append(orderByColumn).append(",");
            }
        }
        sql.setLength(sql.length() - 1);
        this.appendJoinTable(sql);
        this.appendFmlJoinTable(sql);
        this.appendWhereSql(sql, dimFilters, index, checkType);
        return sql;
    }

    private StringBuilder buildExistSql() {
        ArrayList<StringBuilder> sqlUnions = new ArrayList<StringBuilder>();
        sqlUnions.add(this.buildExistSql(this.sqlModel.getCheckTypes(), this.sqlModel.getDimFilters(), 0));
        if (!CollectionUtils.isEmpty(this.sqlModel.getUnionModels())) {
            for (int i = 0; i < this.sqlModel.getUnionModels().size(); ++i) {
                UnionModel unionModel = this.sqlModel.getUnionModels().get(i);
                sqlUnions.add(this.buildExistSql(unionModel.getCheckTypes(), unionModel.getDimFilters(), i + 1));
            }
        }
        return this.unionSql(sqlUnions);
    }

    private StringBuilder buildExistSql(Map<Integer, Boolean> checkType, Map<String, List<String>> dimFilters, int index) {
        StringBuilder sql = new StringBuilder();
        sql.append("select 1 ");
        this.appendJoinTable(sql);
        this.appendFmlJoinTable(sql);
        this.appendWhereSql(sql, dimFilters, index, checkType);
        return sql;
    }

    private void appendWhereSql(StringBuilder sql, Map<String, List<String>> dimFilters, int index, Map<Integer, Boolean> checkType) {
        sql.append(" where ").append(MAIN_TABLE_NAME).append(".").append("CKR_BATCH_ID").append(" =? ");
        this.sqlArgs.add(this.sqlModel.getBatchId());
        this.appendDimFilter(sql, dimFilters, index);
        this.appendNormalFilter(sql);
        this.appendCheckType(sql, checkType);
        this.appendQueryCondition(sql, this.sqlModel.getQueryCondition());
        this.appendFmlCondition(sql);
        this.appendQueryDate(sql);
    }

    private void appendQueryCondition(StringBuilder sql, QueryCondition queryCondition) {
        if (queryCondition != null) {
            QueryContext sqlConContext = new QueryContext();
            sqlConContext.setCkrTableAlia(MAIN_TABLE_NAME);
            sqlConContext.setCkdTableAlia(JOIN_TABLE_ALIA);
            QueryConSql queryConSql = queryCondition.buildSql(sqlConContext);
            String conSql = queryConSql.getConSql();
            if (StringUtils.isNotEmpty((String)conSql)) {
                sql.append(" and ");
                sql.append("(");
                sql.append(conSql);
                sql.append(")");
                this.sqlArgs.addAll(queryConSql.getConArgs());
            }
        }
    }

    private void appendQueryDate(StringBuilder sql) {
        if (this.queryContext != null && this.queryContext.getTableAliaMap().containsKey(this.dwTableName) && this.sqlModel.getQueryDate() != null) {
            String dwTableAlia = this.queryContext.getTableAliaMap().get(this.dwTableName);
            sql.append(" and (");
            sql.append(dwTableAlia).append(".").append("VALIDTIME").append("<=?");
            this.sqlArgs.add(this.sqlModel.getQueryDate());
            sql.append(" and ");
            sql.append(dwTableAlia).append(".").append("INVALIDTIME").append(">?");
            this.sqlArgs.add(this.sqlModel.getQueryDate());
            sql.append(")");
        }
    }

    private void appendNormalFilter(StringBuilder sql) {
        for (Map.Entry<String, List<String>> entry : this.sqlModel.getColumnFilters().entrySet()) {
            List<String> value;
            String colName = entry.getKey();
            if ("MDCODE".equals(colName) || (value = entry.getValue()).size() <= 0) continue;
            this.appendWhereStr(sql, colName, value);
        }
    }

    private void appendWhereStr(StringBuilder sql, String colName, List<String> colValue) {
        sql.append(" and ");
        if (this.tempAssistantTables.containsKey(colName)) {
            ITempTable tempTable = this.tempAssistantTables.get(colName);
            sql.append(" exists ").append(CommonUtils.getExistsSelectSql(tempTable, "t0." + colName));
        } else if (colValue.size() > 0) {
            sql.append(MAIN_TABLE_NAME).append(".").append(colName).append(" in(");
            for (String o : colValue) {
                sql.append("?");
                this.sqlArgs.add(o);
                sql.append(",");
            }
            sql.setLength(sql.length() - 1);
            sql.append(") ");
        }
    }

    private void appendCheckType(StringBuilder sql, Map<Integer, Boolean> checkType) {
        if (checkType.size() > 0) {
            sql.append(" and ");
            sql.append("(");
            boolean addOr = false;
            for (Map.Entry<Integer, Boolean> entry : checkType.entrySet()) {
                if (addOr) {
                    sql.append(" or ");
                }
                Integer key = entry.getKey();
                Boolean value = entry.getValue();
                sql.append("(");
                sql.append(MAIN_TABLE_NAME).append(".").append("CKR_FORMULACHECKTYPE").append("=").append("?");
                this.sqlArgs.add(key);
                if (value != null) {
                    sql.append(" and ").append(JOIN_TABLE_ALIA).append(".").append("CKD_DESCRIPTION");
                    if (value.booleanValue()) {
                        sql.append(" is not null");
                    } else {
                        sql.append(" is null");
                    }
                }
                sql.append(")");
                addOr = true;
            }
            sql.append(")");
        }
    }

    private void appendDimFilter(StringBuilder sql, Map<String, List<String>> dimFilters, int index) {
        String tempKey = Integer.toString(index);
        for (Map.Entry<String, List<String>> entry : dimFilters.entrySet()) {
            String colName = entry.getKey();
            List<String> value = entry.getValue();
            if ("MDCODE".equals(colName) && this.tempAssistantTables.containsKey(tempKey)) {
                sql.append(" and ");
                ITempTable tempTable = this.tempAssistantTables.get(tempKey);
                sql.append(" exists ").append(CommonUtils.getExistsSelectSql(tempTable, "t0.MDCODE"));
                continue;
            }
            if (value.size() <= 0) continue;
            sql.append(" and ");
            sql.append(MAIN_TABLE_NAME).append(".").append(colName).append(" in (");
            for (String code : value) {
                sql.append("?");
                this.sqlArgs.add(code);
                sql.append(",");
            }
            sql.setLength(sql.length() - 1);
            sql.append(") ");
        }
    }

    private void appendJoinTable(StringBuilder sql) {
        sql.append(" from ").append(this.sqlModel.getMainTableName()).append(" ").append(MAIN_TABLE_NAME).append(" ").append(" left join ").append(this.sqlModel.getJoinTableName()).append(" ").append(JOIN_TABLE_ALIA).append(" ").append(" on ").append(MAIN_TABLE_NAME).append(".").append("CKR_RECID").append("=").append(JOIN_TABLE_ALIA).append(".").append("CKD_RECID").append(" ");
    }

    private void appendOrderBy(StringBuilder sql, String orderAlia) {
        boolean addOrder = true;
        for (String orderByColumn : this.sqlModel.getOrderByColumns()) {
            if (addOrder) {
                addOrder = false;
                sql.append(" order by ");
            }
            sql.append(orderAlia).append(".").append(orderByColumn).append(",");
        }
        if (!addOrder) {
            sql.setLength(sql.length() - 1);
        }
    }

    private void appendFmlJoinTable(StringBuilder sql) {
        if (this.queryContext != null && this.queryContext.getTableAliaMap().size() > 0) {
            for (Map.Entry<String, String> entry : this.queryContext.getTableAliaMap().entrySet()) {
                String tableName = entry.getKey();
                if (this.sqlModel.getMainTableName().equals(tableName) || this.sqlModel.getJoinTableName().equals(tableName)) continue;
                String tableAlia = entry.getValue();
                sql.append(" left join ").append(tableName).append(" ").append(tableAlia).append(" ").append("on ").append(MAIN_TABLE_NAME).append(".");
                if (tableName.equals(this.dwTableName)) {
                    sql.append("MDCODE");
                } else {
                    sql.append(tableName);
                }
                sql.append("=").append(tableAlia).append(".CODE");
            }
        }
    }

    private StringBuilder buildItemCountSql() {
        StringBuilder sql = new StringBuilder();
        StringBuilder itemSql = this.buildItemSql(false);
        sql.append("select count(1) AS RECORD_COUNT from (").append((CharSequence)itemSql).append(") ").append(COUNT_ALIA);
        return sql;
    }

    private StringBuilder buildGroupSql(boolean order) {
        StringBuilder child = this.buildItemSql(order);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        for (String colName : this.allQueryColumns) {
            if (this.sqlModel.getGroupByColumns().containsKey(colName)) {
                AggrType aggrType = this.sqlModel.getGroupByColumns().get(colName);
                if (null == aggrType || AggrType.NONE == aggrType || AggrType.MIN == aggrType) {
                    sql.append("min");
                } else if (AggrType.COUNT == aggrType) {
                    sql.append("count");
                } else if (AggrType.SUM == aggrType) {
                    sql.append("SUM");
                } else {
                    sql.append("min");
                }
            } else if ("CKR_RECID".equals(colName)) {
                sql.append("count");
            } else {
                sql.append("min");
            }
            sql.append("(").append(GROUP_QUERY_ALIA).append(".").append(colName).append(") ").append(colName.substring(1));
            sql.append(",");
        }
        if (order) {
            for (String orderByColumn : this.sqlModel.getOrderByColumns()) {
                if (this.allQueryColumns.contains(orderByColumn)) continue;
                sql.append("min(").append(GROUP_QUERY_ALIA).append(".").append(orderByColumn).append(") ").append(orderByColumn.substring(1));
                sql.append(",");
            }
        }
        sql.setLength(sql.length() - 1);
        sql.append(" from ").append("(").append((CharSequence)child).append(") ").append(GROUP_QUERY_ALIA);
        sql.append(" group by ");
        for (String groupByColumn : this.sqlModel.getGroupByColumns().keySet()) {
            sql.append(GROUP_QUERY_ALIA).append(".").append(groupByColumn).append(",");
        }
        sql.setLength(sql.length() - 1);
        if (order) {
            sql.append(" order by ");
            for (String orderByColumn : this.sqlModel.getOrderByColumns()) {
                sql.append(orderByColumn.substring(1)).append(",");
            }
            sql.setLength(sql.length() - 1);
        }
        return sql;
    }

    private StringBuilder buildGroupCountSql() {
        StringBuilder sql = new StringBuilder();
        StringBuilder groupSql = this.buildGroupSql(false);
        sql.append("select count(1) AS RECORD_COUNT from (").append((CharSequence)groupSql).append(") ").append(COUNT_ALIA);
        return sql;
    }

    private void createTempTableIfNeed() {
        try {
            int maxInSize = DataEngineUtil.getMaxInSize((IDatabase)this.getDatabase());
            for (Map.Entry<String, List<String>> entry : this.sqlModel.getColumnFilters().entrySet()) {
                String colName = entry.getKey();
                if ("MDCODE".equals(colName)) continue;
                List<String> valueList = entry.getValue();
                this.initTemp(maxInSize, colName, valueList);
            }
            this.initTemp(maxInSize, Integer.toString(0), this.sqlModel.getDimFilters().get("MDCODE"));
            if (!CollectionUtils.isEmpty(this.sqlModel.getUnionModels())) {
                for (int i = 0; i < this.sqlModel.getUnionModels().size(); ++i) {
                    UnionModel unionModel = this.sqlModel.getUnionModels().get(i);
                    this.initTemp(maxInSize, Integer.toString(i + 1), unionModel.getDimFilters().get("MDCODE"));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void initTemp(int maxInSize, String colName, List<String> colValue) {
        if (colValue != null && colValue.size() >= maxInSize) {
            try {
                ITempTable tempTable = this.getTempTableManager().getOneKeyTempTable();
                ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
                for (String s : colValue) {
                    Object[] batchArr = new Object[]{s};
                    batchValues.add(batchArr);
                }
                tempTable.insertRecords(batchValues);
                this.tempAssistantTables.put(colName, tempTable);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private String appendPageIfNeed(StringBuilder sql) {
        if (this.sqlModel.getPageStart() == -1 || this.sqlModel.getPageEnd() == -1) {
            return sql.toString();
        }
        try {
            IPagingSQLBuilder sqlBuilder = this.getDatabase().createPagingSQLBuilder();
            sqlBuilder.setRawSQL(sql.toString());
            return sqlBuilder.buildSQL(this.sqlModel.getPageStart(), this.sqlModel.getPageEnd());
        }
        catch (Exception e) {
            throw new RuntimeException("\u5206\u9875\u5904\u7406\u5f02\u5e38", e);
        }
    }

    private void appendFmlCondition(StringBuilder sql) {
        if (StringUtils.isEmpty((String)this.fmlSql)) {
            return;
        }
        sql.append(" and (");
        sql.append(this.fmlSql);
        sql.append(")");
    }

    private void initNode() throws ParseException {
        if (StringUtils.isNotEmpty((String)this.sqlModel.getFilterCondition())) {
            logger.debug("{}\u5ba1\u6838\u6761\u4ef6:{}", (Object)this.getClass(), (Object)this.sqlModel.getFilterCondition());
            this.queryContext = new CKRQueryContext();
            this.queryContext.setDatabase(this.getDatabase());
            this.queryContext.setDataDefinitionsCache(new DataDefinitionsCache(this.dataModelService));
            this.queryContext.setMainTableName(this.sqlModel.getMainTableName());
            EntityData dwEntity = this.entityUtil.getEntity(this.entityUtil.getContextMainDimId(this.formSchemeDefine.getDw()));
            this.dwTableName = dwEntity.getTableName();
            this.queryContext.setDwGroupName(this.dwTableName);
            this.queryContext.getTableAliaMap().put(this.sqlModel.getMainTableName(), MAIN_TABLE_NAME);
            this.queryContext.getTableAliaMap().put(this.sqlModel.getJoinTableName(), JOIN_TABLE_ALIA);
            this.node = this.getParser().parseCond(this.sqlModel.getFilterCondition(), (IContext)this.queryContext);
        }
    }

    private void parseNode() {
        if (this.node == null) {
            return;
        }
        try {
            int index = 2;
            for (IASTNode node : this.node) {
                if (!(node instanceof CKRNode)) continue;
                ColumnModelDefine columnModel = ((CKRNode)node).getQueryColumn();
                String tableID = columnModel.getTableID();
                DataModelService bean = (DataModelService)BeanUtil.getBean(DataModelService.class);
                String tableName = bean.getTableModelDefineById(tableID).getName();
                if (this.queryContext == null || this.queryContext.getTableAliaMap().containsKey(tableName)) continue;
                this.queryContext.getTableAliaMap().put(tableName, "t" + index);
                ++index;
            }
            if (this.queryContext != null && this.queryContext.getDatabase() != null) {
                this.fmlSql = this.node.interpret((IContext)this.queryContext, Language.SQL, (Object)new SQLInfoDescr(this.queryContext.getDatabase(), true));
            }
        }
        catch (InterpretException e) {
            String msg = "\u5ba1\u6838\u6761\u4ef6\u89e3\u6790\u5f02\u5e38" + e.getMessage();
            logger.error(msg, e);
        }
    }

    private ReportFormulaParser getParser() {
        if (this.parser == null) {
            this.parser = ReportFormulaParser.getInstance();
            this.parser.setJQReportMode(true);
            this.parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new CKRNodeFinder());
        }
        return this.parser;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private String bs2String(InputStream is) {
        if (is == null) {
            return "";
        }
        try {
            String string;
            Throwable throwable;
            ByteArrayOutputStream outStream;
            block25: {
                block26: {
                    int ch;
                    outStream = new ByteArrayOutputStream();
                    throwable = null;
                    byte[] buffer = new byte[1024];
                    while ((ch = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, ch);
                    }
                    string = outStream.toString();
                    if (outStream == null) break block25;
                    if (throwable == null) break block26;
                    try {
                        outStream.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    break block25;
                }
                outStream.close();
            }
            return string;
            catch (Throwable throwable3) {
                try {
                    try {
                        throwable = throwable3;
                        throw throwable3;
                    }
                    catch (Throwable throwable4) {
                        if (outStream != null) {
                            if (throwable != null) {
                                try {
                                    outStream.close();
                                }
                                catch (Throwable throwable5) {
                                    throwable.addSuppressed(throwable5);
                                }
                            } else {
                                outStream.close();
                            }
                        }
                        throw throwable4;
                    }
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return "";
    }

    private String clob2String(Clob clob) {
        String re = "";
        try (Reader reader = clob.getCharacterStream();
             BufferedReader br = new BufferedReader(reader);){
            String str = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (str != null) {
                sb.append(str);
                str = br.readLine();
            }
            re = sb.toString();
        }
        catch (Exception e) {
            String msg = "Clob\u6570\u636e\u8f6c\u6362\u5f02\u5e38:" + e.getMessage();
            logger.error(msg, e);
        }
        return re;
    }

    private boolean clobReadByStream() {
        IDatabase db = this.getDatabase();
        return db != null && (db.isDatabase("polardb") || db.isDatabase("POSTGRESQL"));
    }

    private IDatabase getDatabase() {
        if (this.database == null) {
            try (Connection conn = DataSourceUtils.getConnection((DataSource)this.dataSource);){
                this.database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            }
            catch (SQLException e) {
                String msg = "\u83b7\u53d6\u6570\u636e\u5e93\u4fe1\u606f\u5f02\u5e38:" + e.getMessage();
                logger.error(msg, e);
            }
        }
        return this.database;
    }

    public List<Map<String, Object>> listFmlParsedExpressionKey() {
        this.sqlArgs = new ArrayList<Object>();
        StringBuilder sql = this.buildFmlSql();
        String sqlStr = sql.toString();
        logger.debug("\u67e5\u8be2\u5ba1\u6838\u7ed3\u679c\u516c\u5f0f\u4fe1\u606fsql:{}\n{}", (Object)sqlStr, (Object)this.sqlArgs);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
            List<Map<String, Object>> list = this.executeQuery(sqlStr, connection);
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException("\u5ba1\u6838\u7ed3\u679c\u516c\u5f0f\u4fe1\u606f\u67e5\u8be2\u5f02\u5e38", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
            }
        }
    }

    private StringBuilder buildFmlSql() {
        ArrayList<StringBuilder> sqlUnions = new ArrayList<StringBuilder>();
        sqlUnions.add(this.buildFmlSql(this.sqlModel.getCheckTypes(), this.sqlModel.getDimFilters(), 0));
        if (!CollectionUtils.isEmpty(this.sqlModel.getUnionModels())) {
            for (int i = 0; i < this.sqlModel.getUnionModels().size(); ++i) {
                UnionModel unionModel = this.sqlModel.getUnionModels().get(i);
                sqlUnions.add(this.buildFmlSql(unionModel.getCheckTypes(), unionModel.getDimFilters(), i + 1));
            }
        }
        return this.orderUnionSql(sqlUnions, true);
    }

    private StringBuilder unionSql(List<StringBuilder> sqlUnions) {
        StringBuilder unionSql;
        block6: {
            unionSql = new StringBuilder();
            if (CollectionUtils.isEmpty(sqlUnions)) break block6;
            if (this.getDatabase().isDatabase("DERBY")) {
                StringBuilder firstSql = sqlUnions.get(0);
                int conStartIndex = firstSql.indexOf("where") + "where ".length();
                String selectSql = firstSql.substring(0, conStartIndex);
                unionSql.append(selectSql);
                boolean addOr = false;
                for (StringBuilder sqlUnion : sqlUnions) {
                    if (addOr) {
                        unionSql.append(" or ");
                    }
                    String whereSql = sqlUnion.substring(conStartIndex);
                    unionSql.append("(").append(whereSql).append(")");
                    addOr = true;
                }
            } else {
                boolean addUnion = false;
                for (StringBuilder sqlUnion : sqlUnions) {
                    if (addUnion) {
                        unionSql.append(" union all ");
                    }
                    unionSql.append("(").append((CharSequence)sqlUnion).append(")");
                    addUnion = true;
                }
            }
        }
        return unionSql;
    }

    private StringBuilder orderUnionSql(List<StringBuilder> sqlUnions, boolean order) {
        StringBuilder unionSql = this.unionSql(sqlUnions);
        if (order) {
            StringBuilder result = new StringBuilder();
            result.append("select * from ").append("(").append((CharSequence)unionSql).append(") ").append(UNION_ALIA);
            this.appendOrderBy(result, UNION_ALIA);
            return result;
        }
        return unionSql;
    }

    private StringBuilder buildFmlSql(Map<Integer, Boolean> checkTypes, Map<String, List<String>> dimFilters, int index) {
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct ");
        for (String mainColumn : this.sqlModel.getMainColumns()) {
            sql.append(MAIN_TABLE_NAME).append(".").append(mainColumn).append(",");
        }
        for (String orderByColumn : this.sqlModel.getOrderByColumns()) {
            if (this.allQueryColumns.contains(orderByColumn)) continue;
            sql.append(MAIN_TABLE_NAME).append(".").append(orderByColumn).append(",");
        }
        sql.setLength(sql.length() - 1);
        sql.append(" from ").append(this.sqlModel.getMainTableName()).append(" ").append(MAIN_TABLE_NAME).append(" ");
        this.appendWhereSql(sql, dimFilters, index, checkTypes);
        return sql;
    }

    @Override
    public void close() {
        this.deleteTempTable();
    }

    private ITempTableManager getTempTableManager() {
        if (this.tempTableManager == null) {
            this.tempTableManager = (ITempTableManager)BeanUtil.getBean(ITempTableManager.class);
        }
        return this.tempTableManager;
    }
}

