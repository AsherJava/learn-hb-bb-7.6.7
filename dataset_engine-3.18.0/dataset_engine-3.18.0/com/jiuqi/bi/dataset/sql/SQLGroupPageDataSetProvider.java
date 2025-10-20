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
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor$GroupInfo
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;
import com.jiuqi.bi.dataset.model.field.DSField;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLGroupPageDataSetProvider
implements IPageDataSetProvider {
    private SQLModel model;
    private String groupFieldName;
    private int groupFieldIndex;
    private int pageSize;

    public SQLGroupPageDataSetProvider(DSModel model, String groupFieldName) {
        this.model = (SQLModel)model;
        this.groupFieldName = groupFieldName;
        List<DSField> fields = model.getCommonFields();
        for (int i = 0; i < fields.size(); ++i) {
            DSField field = fields.get(i);
            if (!field.getName().equalsIgnoreCase(groupFieldName)) continue;
            this.groupFieldIndex = i;
            break;
        }
        if (this.groupFieldIndex == -1) {
            throw new IllegalArgumentException("\u65e0\u6cd5\u627e\u5230\u7ef4\u5ea6\u5b57\u6bb5" + groupFieldName);
        }
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    @Override
    public int open(MemoryDataSet<BIDataSetFieldInfo> memory, int pageNum, IDSContext context) throws BIDataSetException {
        boolean isMultipleConnection;
        if (pageNum < 1) {
            throw new IllegalArgumentException("pageNum : " + pageNum);
        }
        if (this.model.getSql() == null || this.model.getSql().length() == 0) {
            throw new BIDataSetException("\u6ca1\u6709\u8981\u6267\u884c\u7684SQL");
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
            String schema = connWapper.getSchema();
            String jndi = connWapper.getJndi();
            Throwable throwable = null;
            try (Connection conn = connWapper.getConn();){
                int n;
                List<PageInfo> pageKeys;
                SQLQueryExecutor sqlExecutor;
                block50: {
                    sqlExecutor = SQLDataSetUtils.createSQLQueryExecutor(conn, context);
                    sqlExecutor.registerListener((ISQLQueryListener)new JNDIAndSchemaQueryListener(jndi, schema));
                    SQLDataSetProvider.makeOrderFields(sqlExecutor.getOrderFields(), context, this.groupFieldName);
                    sqlExecutor.open(this.model.getSql());
                    if (StringUtils.isNotEmpty((String)this.model.getPreSqlScript())) {
                        SQLQueryScriptExcutor executor = new SQLQueryScriptExcutor(new StringReader(this.model.getPreSqlScript()), conn, context);
                        try {
                            executor.parse();
                        }
                        catch (Exception e) {
                            throw new BIDataSetException(e.getMessage(), e);
                        }
                    }
                    pageKeys = this.buildPageKeys(sqlExecutor, context);
                    if (pageNum <= pageKeys.size()) break block50;
                    int e = this.getRecordCount(context);
                    sqlExecutor.close();
                    return e;
                }
                try {
                    Object filters;
                    PageInfo info = pageKeys.get(pageNum - 1);
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    String[] exprFilter = new String[1];
                    if (context != null) {
                        RangeValues timekeyRange = context.getTimekeyFiterRange();
                        filters = context.getAllFilterItem();
                        SQLDataSetUtils.makeSQLExecutorFilter(this.model, timekeyRange, (FilterItem[])filters, map, exprFilter, context.getI18nLang());
                    }
                    map.put(this.groupFieldName, info.pageKeyList);
                    ResultSet rs = sqlExecutor.executeQuery(map, exprFilter[0]);
                    filters = null;
                    try {
                        List<FieldReader> readers = FieldReader.buildMemoryDataSet(this.model, rs.getMetaData());
                        while (rs.next()) {
                            DataRow row = memory.add();
                            for (FieldReader reader : readers) {
                                reader.read(rs, row);
                            }
                        }
                    }
                    catch (Throwable readers) {
                        filters = readers;
                        throw readers;
                    }
                    finally {
                        if (rs != null) {
                            if (filters != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable readers) {
                                    ((Throwable)filters).addSuppressed(readers);
                                }
                            } else {
                                rs.close();
                            }
                        }
                    }
                    if (StringUtils.isNotEmpty((String)this.model.getAfterSqlScript())) {
                        SQLQueryScriptExcutor executor = new SQLQueryScriptExcutor(new StringReader(this.model.getAfterSqlScript()), conn, context);
                        try {
                            executor.parse();
                        }
                        catch (Exception e) {
                            throw new BIDataSetException(e.getMessage(), e);
                        }
                    }
                    context.markFiltered();
                    context.markSorted();
                    int start = 0;
                    for (int i = 1; i < pageNum; ++i) {
                        PageInfo pi = pageKeys.get(i);
                        start += pi.pageSize;
                    }
                    n = start;
                }
                catch (Throwable throwable2) {
                    try {
                        sqlExecutor.close();
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        throwable = throwable3;
                        throw throwable3;
                    }
                }
                sqlExecutor.close();
                return n;
            }
        }
        catch (SQLException e) {
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25", e);
        }
        catch (Exception e) {
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getRecordCount(IDSContext context) throws BIDataSetException {
        int n;
        boolean isMultipleConnection;
        if (this.model.getSql() == null || this.model.getSql().length() == 0) {
            throw new BIDataSetException("\u6ca1\u6709\u8981\u6267\u884c\u7684SQL");
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
        ConnectionWapper[] conns = SQLDataSetUtils.getConnections(this.model, context.getEnhancedParameterEnv());
        if (conns.length == 0) {
            throw new BIDataSetException("\u6ca1\u6709\u5408\u9002\u7684\u6570\u636e\u8fde\u63a5");
        }
        ConnectionWapper connWapper = conns[0];
        String jndi = connWapper.getJndi();
        String schema = connWapper.getSchema();
        Connection conn = connWapper.getConn();
        try {
            SQLQueryExecutor sqlExecutor = SQLDataSetUtils.createSQLQueryExecutor(conn, context);
            sqlExecutor.registerListener((ISQLQueryListener)new JNDIAndSchemaQueryListener(jndi, schema));
            SQLDataSetProvider.makeOrderFields(sqlExecutor.getOrderFields(), context, this.groupFieldName);
            sqlExecutor.open(this.model.getSql());
            HashMap<String, Object> map = new HashMap<String, Object>();
            String[] exprFilter = new String[1];
            if (context != null) {
                RangeValues timekeyRange = context.getTimekeyFiterRange();
                FilterItem[] filters = context.getAllFilterItem();
                SQLDataSetUtils.makeSQLExecutorFilter(this.model, timekeyRange, filters, map, exprFilter, context.getI18nLang());
            }
            List groupInfos = map.isEmpty() && exprFilter[0] == null ? sqlExecutor.getGroupInfos(this.groupFieldName) : sqlExecutor.getGroupInfos(this.groupFieldName, map, exprFilter[0]);
            int recordCount = 0;
            for (SQLQueryExecutor.GroupInfo info : groupInfos) {
                recordCount += info.count;
            }
            n = recordCount;
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25", e);
            }
            catch (Exception e) {
                throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25", e);
            }
        }
        conn.close();
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getPageCount(IDSContext context) throws BIDataSetException {
        boolean isMultipleConnection;
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
                SQLDataSetProvider.makeOrderFields(sqlExecutor.getOrderFields(), context, this.groupFieldName);
                sqlExecutor.open(this.model.getSql());
                try {
                    List<PageInfo> pageKeys = this.buildPageKeys(sqlExecutor, context);
                    n = pageKeys.size();
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
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25\uff0cSQL\u6267\u884c\u51fa\u9519", e);
        }
        catch (Exception e) {
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6[" + this.model.getName() + "]\u5931\u8d25", e);
        }
    }

    private List<PageInfo> buildPageKeys(SQLQueryExecutor sqlExecutor, IDSContext context) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String[] exprFilter = new String[1];
        if (context != null) {
            RangeValues timekeyRange = context.getTimekeyFiterRange();
            FilterItem[] filters = context.getAllFilterItem();
            SQLDataSetUtils.makeSQLExecutorFilter(this.model, timekeyRange, filters, map, exprFilter, context.getI18nLang());
        }
        List groupInfos = map.isEmpty() && exprFilter[0] == null ? sqlExecutor.getGroupInfos(this.groupFieldName) : sqlExecutor.getGroupInfos(this.groupFieldName, map, exprFilter[0]);
        ArrayList<PageInfo> pageKeys = new ArrayList<PageInfo>();
        PageInfo curPageInfo = new PageInfo();
        int curPageSize = 0;
        for (SQLQueryExecutor.GroupInfo info : groupInfos) {
            curPageInfo.pageKeyList.add(info.value);
            if ((curPageSize += info.count) < this.pageSize) continue;
            curPageInfo.pageSize = curPageSize;
            curPageSize = 0;
            pageKeys.add(curPageInfo);
            curPageInfo = new PageInfo();
        }
        if (curPageInfo.pageKeyList.size() != 0) {
            pageKeys.add(curPageInfo);
        }
        return pageKeys;
    }

    private static class PageInfo {
        private List<Object> pageKeyList = new ArrayList<Object>();
        private int pageSize = 0;

        private PageInfo() {
        }
    }
}

