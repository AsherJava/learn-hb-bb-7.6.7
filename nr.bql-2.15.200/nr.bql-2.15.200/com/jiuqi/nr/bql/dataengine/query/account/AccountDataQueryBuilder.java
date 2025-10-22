/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.bql.dataengine.query.account;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.bql.dataengine.impl.CommonQueryImpl;
import com.jiuqi.nr.bql.dataengine.impl.ReadonlyTableImpl;
import com.jiuqi.nr.bql.dataengine.query.DataQueryBuilder;
import com.jiuqi.nr.bql.dataengine.query.account.AccountQuerySqlBuilder;
import com.jiuqi.nr.bql.datasource.QueryDataReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class AccountDataQueryBuilder
extends DataQueryBuilder {
    private static final long serialVersionUID = -7832234618989763806L;
    private static final String EMPTY_DIM = "-";
    private final GregorianCalendar fixCalendar = new GregorianCalendar(9999, 0, 1);
    protected AccountQuerySqlBuilder querySqlBuilder;
    protected final BiFunction<QueryContext, List<QueryField>, Boolean> isVersionFunc = (context, fields) -> fields.stream().anyMatch(e -> e != null && e.isNeedAccountVersion());
    protected final BiFunction<QueryContext, List<QueryField>, Boolean> isDataTimeFunc = (context, fields) -> fields.stream().anyMatch(e -> e != null && e.getTable().isAccountRptTable());

    public AccountDataQueryBuilder(QueryDataReader reader) {
        super(reader);
    }

    @Override
    protected ReadonlyTableImpl createDataTable(QueryContext qContext, CommonQueryImpl queryInfo) {
        return new ReadonlyTableImpl(qContext, queryInfo.getMasterKeys(), queryInfo.getColumns().size());
    }

    @Override
    public void buildQuery(QueryContext qContext, CommonQueryImpl queryInfo, boolean resultReadOnly) throws ExpressionException, SQLException, ParseException, InterpretException {
        this.queryVersionDate = queryInfo.getQueryVersionDate();
        this.table = resultReadOnly ? new ReadonlyTableImpl(qContext, queryInfo.getMasterKeys(), queryInfo.getColumns().size()) : this.createDataTable(qContext, queryInfo);
        this.doParseQuery(qContext, queryInfo);
        AccountQuerySqlBuilder sqlBuilder = this.getSqlBuilder(qContext);
        List fields = this.table.queryfields.stream().collect(Collectors.toList());
        sqlBuilder.setTrackHistory(this.isVersionFunc.apply(qContext, fields)).setContainTimeField(this.isDataTimeFunc.apply(qContext, fields));
        this.setTableParams(qContext.getExeContext().getCache(), queryInfo);
        this.buildQueryRegion(qContext);
        this.buildQuerySql(qContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void runQuery(QueryContext qContext, int rowCount, int rowIndex) throws Exception {
        this.currentIndex = 0;
        AccountQuerySqlBuilder sqlBuilder = this.getSqlBuilder(qContext);
        this.table.setContext(qContext);
        sqlBuilder.setResultTable(this.table);
        sqlBuilder.setRowFilterNode(this.rowFilterNode);
        sqlBuilder.setOrderByItems(this.orderByItems);
        sqlBuilder.setColValueFilters(this.colValueFilters);
        sqlBuilder.setQueryParam(this.queryParam);
        sqlBuilder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
        sqlBuilder.setQueryVersionDate(this.queryVersionDate);
        sqlBuilder.doInit(qContext);
        if (rowCount < 0) {
            qContext.setQueryRowStart(0);
        } else {
            qContext.setQueryRowStart(rowIndex);
            sqlBuilder.setDoPage(true);
        }
        boolean sqlSoftParse = !DataEngineConsts.DATA_ENGINE_DEBUG;
        sqlBuilder.setSqlSoftParse(sqlSoftParse);
        String mainQuerySql = sqlBuilder.buildSql(qContext);
        try {
            this.queryData(qContext, sqlBuilder, mainQuerySql, rowCount, rowIndex);
        }
        finally {
            this.queryParam.closeConnection();
            this.addExtQueryField();
        }
    }

    private void addExtQueryField() {
        List oldFields = this.table.queryfields.stream().collect(Collectors.toList());
        Map<QueryTable, QueryFields> fields = this.queryRegion.getAllTableFields();
        ArrayList newFields = new ArrayList();
        fields.forEach((queryTable, queryFields) -> {
            for (QueryField queryField : queryFields) {
                newFields.add(queryField);
            }
        });
        oldFields.addAll(newFields);
        List mergeFields = oldFields.stream().distinct().collect(Collectors.toList());
        this.table.queryfields = mergeFields;
    }

    @Override
    public AccountQuerySqlBuilder getSqlBuilder(QueryContext qContext) {
        if (this.querySqlBuilder == null) {
            this.querySqlBuilder = new AccountQuerySqlBuilder();
        }
        return this.querySqlBuilder;
    }

    @Override
    protected Object nodeEvaluate(QueryContext qContext, IASTNode node) {
        Object value = super.nodeEvaluate(qContext, node);
        if (value instanceof GregorianCalendar) {
            GregorianCalendar calendarValue = (GregorianCalendar)value;
            if (calendarValue.compareTo(this.fixCalendar) == 0) {
                value = null;
            }
        } else if (EMPTY_DIM.equals(value)) {
            value = " ";
        }
        return value;
    }
}

