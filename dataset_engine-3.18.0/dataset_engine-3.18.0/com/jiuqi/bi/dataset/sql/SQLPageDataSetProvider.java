/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.sql.ConnectionWapper
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.DefaultPageDataSetProvider;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;
import com.jiuqi.bi.dataset.sql.FieldReader;
import com.jiuqi.bi.dataset.sql.JNDIAndSchemaQueryListener;
import com.jiuqi.bi.dataset.sql.SQLDataSetProvider;
import com.jiuqi.bi.dataset.sql.SQLDataSetUtils;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.dataset.sql.SQLQueryScriptExcutor;
import com.jiuqi.bi.sql.ConnectionWapper;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.bi.util.StringUtils;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SQLPageDataSetProvider
implements IPageDataSetProvider {
    private SQLModel model;
    private DefaultPageDataSetProvider defaultPageDataSetProvider;
    private int totalSize = -1;
    private int pageSize;

    public SQLPageDataSetProvider(SQLModel model) {
        this.model = model;
        this.defaultPageDataSetProvider = new DefaultPageDataSetProvider(model, new SQLDataSetProvider(model));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int open(MemoryDataSet<BIDataSetFieldInfo> memory, int pageNum, IDSContext context) throws BIDataSetException {
        int start;
        block36: {
            boolean isMultipleConnection;
            if (pageNum < 1 || pageNum > this.getPageCount(context)) {
                throw new IllegalArgumentException("pageNum : " + pageNum);
            }
            if (this.model.getSql() == null || this.model.getSql().length() == 0) {
                throw new BIDataSetException("\u6ca1\u6709\u8981\u6267\u884c\u7684SQL");
            }
            start = (pageNum - 1) * this.pageSize;
            int end = pageNum * this.pageSize;
            try {
                isMultipleConnection = SQLDataSetUtils.isMultipleConnection(this.model, context.getEnhancedParameterEnv());
            }
            catch (Exception e1) {
                throw new BIDataSetException(e1.getMessage(), e1);
            }
            if (isMultipleConnection) {
                throw new BIDataSetException("SQL\u6570\u636e\u96c6\u5206\u9875\u4e0d\u652f\u6301\u591a\u6570\u636e\u8fde\u63a5\u6a21\u5f0f");
            }
            try {
                ConnectionWapper[] conns = SQLDataSetUtils.getConnections(this.model, context.getEnhancedParameterEnv());
                if (conns.length == 0) {
                    throw new BIDataSetException("\u6ca1\u6709\u5408\u9002\u7684\u6570\u636e\u8fde\u63a5");
                }
                ConnectionWapper connWapper = conns[0];
                String jndi = connWapper.getJndi();
                String schema = connWapper.getSchema();
                try (Connection conn = connWapper.getConn();){
                    SQLQueryScriptExcutor executor;
                    SQLQueryExecutor sqlExecutor = SQLDataSetUtils.createSQLQueryExecutor(conn, context);
                    sqlExecutor.registerListener((ISQLQueryListener)new JNDIAndSchemaQueryListener(jndi, schema));
                    SQLDataSetProvider.makeOrderFields(sqlExecutor.getOrderFields(), context);
                    sqlExecutor.open(this.model.getSql());
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    String[] exprFilter = new String[1];
                    if (context != null) {
                        RangeValues timekeyRange = context.getTimekeyFiterRange();
                        FilterItem[] filters = context.getAllFilterItem();
                        SQLDataSetUtils.makeSQLExecutorFilter(this.model, timekeyRange, filters, map, exprFilter, context.getI18nLang());
                    }
                    if (StringUtils.isNotEmpty((String)this.model.getPreSqlScript())) {
                        executor = new SQLQueryScriptExcutor(new StringReader(this.model.getPreSqlScript()), conn, context);
                        try {
                            executor.parse();
                        }
                        catch (Exception e) {
                            throw new BIDataSetException(e.getMessage(), e);
                        }
                    }
                    try (ResultSet rs = !map.isEmpty() || exprFilter[0] != null ? sqlExecutor.executeQuery(start, end, map, exprFilter[0]) : sqlExecutor.executeQuery(start, end);){
                        List<FieldReader> readers = FieldReader.buildMemoryDataSet(this.model, rs.getMetaData());
                        while (rs.next()) {
                            DataRow row = memory.add();
                            for (FieldReader reader : readers) {
                                reader.read(rs, row);
                            }
                        }
                    }
                    finally {
                        sqlExecutor.close();
                    }
                    if (!StringUtils.isNotEmpty((String)this.model.getAfterSqlScript())) break block36;
                    executor = new SQLQueryScriptExcutor(new StringReader(this.model.getAfterSqlScript()), conn, context);
                    try {
                        executor.parse();
                    }
                    catch (Exception e) {
                        throw new BIDataSetException(e.getMessage(), e);
                    }
                }
            }
            catch (SQLException e) {
                throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25\uff0c" + e.getMessage(), e);
            }
            catch (Exception e) {
                throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25\uff0c" + e.getMessage(), e);
            }
        }
        context.markFiltered();
        context.markSorted();
        return start;
    }

    @Override
    public int getRecordCount(IDSContext context) throws BIDataSetException {
        return this.readRecordCount(context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    private int readRecordCount(IDSContext context) throws BIDataSetException {
        boolean isMultipleConnection;
        if (this.model.getSql() == null || this.model.getSql().length() == 0) {
            throw new BIDataSetException("\u6ca1\u6709\u8981\u6267\u884c\u7684SQL");
        }
        if (this.totalSize >= 0) {
            return this.totalSize;
        }
        try {
            isMultipleConnection = SQLDataSetUtils.isMultipleConnection(this.model, context.getEnhancedParameterEnv());
        }
        catch (Exception e1) {
            throw new BIDataSetException(e1.getMessage(), e1);
        }
        if (isMultipleConnection) {
            throw new BIDataSetException("SQL\u6570\u636e\u96c6\u5206\u9875\u4e0d\u652f\u6301\u591a\u6570\u636e\u8fde\u63a5\u6a21\u5f0f");
        }
        try {
            ConnectionWapper[] conns = SQLDataSetUtils.getConnections(this.model, context.getEnhancedParameterEnv());
            if (conns.length == 0) {
                throw new BIDataSetException("\u6ca1\u6709\u5408\u9002\u7684\u6570\u636e\u8fde\u63a5");
            }
            ConnectionWapper connWapper = conns[0];
            String jndi = connWapper.getJndi();
            String schema = connWapper.getSchema();
            try (Connection conn = connWapper.getConn();){
                int n;
                SQLQueryExecutor sqlExecutor = SQLDataSetUtils.createSQLQueryExecutor(conn, context);
                sqlExecutor.registerListener((ISQLQueryListener)new JNDIAndSchemaQueryListener(jndi, schema));
                SQLDataSetProvider.makeOrderFields(sqlExecutor.getOrderFields(), context);
                sqlExecutor.open(this.model.getSql());
                HashMap<String, Object> map = new HashMap<String, Object>();
                String[] exprFilter = new String[1];
                if (context != null) {
                    RangeValues timekeyRange = context.getTimekeyFiterRange();
                    FilterItem[] filters = context.getAllFilterItem();
                    SQLDataSetUtils.makeSQLExecutorFilter(this.model, timekeyRange, filters, map, exprFilter, context.getI18nLang());
                }
                try {
                    this.totalSize = !map.isEmpty() || exprFilter[0] != null ? sqlExecutor.getRecordCount(map, exprFilter[0]) : sqlExecutor.getRecordCount();
                    n = this.totalSize;
                }
                catch (Throwable throwable) {
                    sqlExecutor.close();
                    throw throwable;
                }
                sqlExecutor.close();
                return n;
            }
        }
        catch (SQLException e) {
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
        catch (Exception e) {
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
    }

    @Override
    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize : " + pageSize);
        }
        this.pageSize = pageSize;
    }

    @Override
    public int getPageCount(IDSContext context) throws BIDataSetException {
        int allSize = this.readRecordCount(context);
        return (allSize - 1) / this.pageSize + 1;
    }
}

