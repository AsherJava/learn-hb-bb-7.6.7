/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.paging.OrderField
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.logging.DummyLogger
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.sql.ConnectionWapper
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.model.IDataSetWithDistinctProvider;
import com.jiuqi.bi.dataset.sql.FieldReader;
import com.jiuqi.bi.dataset.sql.JNDIAndSchemaQueryListener;
import com.jiuqi.bi.dataset.sql.SQLDataSetUtils;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.dataset.sql.SQLQueryScriptExcutor;
import com.jiuqi.bi.logging.DummyLogger;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.sql.ConnectionWapper;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLDataSetProvider
implements IDataSetWithDistinctProvider {
    private SQLModel model;

    public SQLDataSetProvider(SQLModel model) {
        this.model = model;
    }

    @Override
    public void open(MemoryDataSet<BIDataSetFieldInfo> memory, IDSContext context) throws BIDataSetException {
        if (this.model.getSql() == null || this.model.getSql().length() == 0) {
            throw new BIDataSetException("\u6ca1\u6709\u8981\u6267\u884c\u7684SQL");
        }
        try {
            ConnectionWapper[] conns = SQLDataSetUtils.getConnections(this.model, context.getEnhancedParameterEnv());
            boolean isMulti = conns.length > 1;
            for (ConnectionWapper connWapper : conns) {
                String jndi = connWapper.getJndi();
                String schema = connWapper.getSchema();
                try (Connection conn = connWapper.getConn();){
                    SQLQueryScriptExcutor executor;
                    if (!isMulti && StringUtils.isNotEmpty((String)this.model.getPreSqlScript())) {
                        executor = new SQLQueryScriptExcutor(new StringReader(this.model.getPreSqlScript()), conn, context);
                        try {
                            executor.parse();
                        }
                        catch (Exception e) {
                            throw new BIDataSetException(e.getMessage(), e);
                        }
                    }
                    this.fillDataset(conn, memory, context, jndi, schema);
                    if (isMulti || !StringUtils.isNotEmpty((String)this.model.getAfterSqlScript())) continue;
                    executor = new SQLQueryScriptExcutor(new StringReader(this.model.getAfterSqlScript()), conn, context);
                    try {
                        executor.parse();
                    }
                    catch (Exception e) {
                        throw new BIDataSetException(e.getMessage(), e);
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        catch (ParameterException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        context.markFiltered();
        context.markSorted();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void fillDataset(Connection conn, MemoryDataSet<BIDataSetFieldInfo> memory, IDSContext context, String jndi, String schema) throws BIDataSetException {
        try {
            SQLQueryExecutor sqlExecutor = SQLDataSetUtils.createSQLQueryExecutor(conn, context);
            sqlExecutor.setLogger((ILogger)new SQLLogger(context));
            sqlExecutor.registerListener((ISQLQueryListener)new JNDIAndSchemaQueryListener(jndi, schema));
            SQLDataSetProvider.makeOrderFields(sqlExecutor.getOrderFields(), context);
            sqlExecutor.open(this.model.getSql());
            try {
                HashMap<String, Object> map = new HashMap<String, Object>();
                String[] exprFilter = new String[1];
                if (context != null) {
                    RangeValues timekeyRange = context.getTimekeyFiterRange();
                    FilterItem[] filters = context.getAllFilterItem();
                    SQLDataSetUtils.makeSQLExecutorFilter(this.model, timekeyRange, filters, map, exprFilter, context.getI18nLang());
                }
                try (ResultSet rs = !map.isEmpty() || exprFilter[0] != null ? sqlExecutor.executeQuery(map, exprFilter[0]) : sqlExecutor.executeQuery();){
                    List<FieldReader> readers = FieldReader.buildMemoryDataSet(this.model, rs.getMetaData());
                    while (rs.next()) {
                        DataRow row = memory.add();
                        for (FieldReader reader : readers) {
                            reader.read(rs, row);
                        }
                    }
                }
            }
            finally {
                sqlExecutor.close();
            }
        }
        catch (SQLException e) {
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
        catch (Exception e) {
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
    }

    static void makeOrderFields(List<OrderField> orderFields, IDSContext context) {
        SQLDataSetProvider.makeOrderFields(orderFields, context, null);
    }

    static void makeOrderFields(List<OrderField> orderFields, IDSContext context, String firstOrderField) {
        SortItem[] sortItems = context.getSortItems();
        if (sortItems == null || sortItems.length == 0) {
            return;
        }
        ArrayList<OrderField> list = new ArrayList<OrderField>();
        for (SortItem sortItem : sortItems) {
            OrderField orderField = new OrderField(sortItem.getFieldName());
            orderField.setOrderMode(sortItem.getSortType() > 0 ? "asc" : "desc");
            list.add(orderField);
        }
        if (firstOrderField == null || firstOrderField.length() == 0) {
            orderFields.addAll(list);
            return;
        }
        boolean hasFind = false;
        for (int i = 0; i < list.size(); ++i) {
            OrderField orderField = (OrderField)list.get(i);
            if (!orderField.getFieldName().equalsIgnoreCase(firstOrderField)) continue;
            hasFind = true;
            list.remove(i);
            list.add(0, orderField);
            break;
        }
        if (!hasFind) {
            OrderField orderField = new OrderField(firstOrderField);
            list.add(0, orderField);
        }
        orderFields.addAll(list);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void distinct(MemoryDataSet<BIDataSetFieldInfo> memory, IDSContext context) throws BIDataSetException {
        if (this.model.getSql() == null || this.model.getSql().length() == 0) {
            throw new BIDataSetException("\u6ca1\u6709\u8981\u6267\u884c\u7684SQL");
        }
        try {
            ConnectionWapper[] conns;
            for (ConnectionWapper connWapper : conns = SQLDataSetUtils.getConnections(this.model, context.getEnhancedParameterEnv())) {
                String jndi = connWapper.getJndi();
                String schema = connWapper.getSchema();
                try (Connection conn = connWapper.getConn();){
                    this.fillDistinctDataset(conn, memory, context, jndi, schema);
                }
            }
        }
        catch (SQLException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        catch (ParameterException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        context.markFiltered();
        context.markSorted();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void fillDistinctDataset(Connection conn, MemoryDataSet<BIDataSetFieldInfo> memory, IDSContext context, String jndi, String schema) throws BIDataSetException {
        try {
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
            try (ResultSet rs = sqlExecutor.distinct(this.getFields(memory), map, exprFilter[0]);){
                List<FieldReader> readers = FieldReader.buildMemoryDataSet((Metadata<BIDataSetFieldInfo>)memory.getMetadata(), rs.getMetaData());
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
        }
        catch (Exception e) {
            throw new BIDataSetException("\u6253\u5f00\u6570\u636e\u96c6\u5931\u8d25\uff0c" + e.getMessage(), e);
        }
    }

    private List<String> getFields(MemoryDataSet<BIDataSetFieldInfo> memory) {
        Metadata metadata = memory.getMetadata();
        ArrayList<String> fieldNames = new ArrayList<String>(metadata.size());
        for (Column column : metadata) {
            fieldNames.add(column.getName());
        }
        return fieldNames;
    }

    private class SQLLogger
    extends DummyLogger {
        private IDSContext context;

        public SQLLogger(IDSContext context) {
            this.context = context;
        }

        public void warn(String arg0, Throwable arg1) {
            this.context.getLogger().warn(arg0);
        }

        public void warn(String arg0) {
            this.context.getLogger().warn(arg0);
        }

        public boolean isWarnEnabled() {
            return true;
        }

        public boolean isInfoEnabled() {
            return true;
        }

        public boolean isErrorEnabled() {
            return true;
        }

        public boolean isDebugEnabled() {
            return true;
        }

        public void info(String arg0, Throwable arg1) {
            this.context.getLogger().info(arg0);
        }

        public void info(String arg0) {
            this.context.getLogger().info(arg0);
        }

        public String getName() {
            return "com.jiuqi.bi.dataset";
        }

        public void error(String arg0, Throwable arg1) {
            this.context.getLogger().error(arg0);
        }

        public void error(String arg0) {
            this.context.getLogger().error(arg0);
        }

        public void debug(String arg0, Throwable arg1) {
            this.context.getLogger().debug(arg0);
        }

        public void debug(String arg0) {
            this.context.getLogger().debug(arg0);
        }
    }
}

