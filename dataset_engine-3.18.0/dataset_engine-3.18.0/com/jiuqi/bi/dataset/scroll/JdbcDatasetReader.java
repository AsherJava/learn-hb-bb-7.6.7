/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.OrderField
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.sql.ConnectionWapper
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 */
package com.jiuqi.bi.dataset.scroll;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.scroll.DatasetReader;
import com.jiuqi.bi.dataset.scroll.IDataProcessor;
import com.jiuqi.bi.dataset.sql.FieldReader;
import com.jiuqi.bi.dataset.sql.JNDIAndSchemaQueryListener;
import com.jiuqi.bi.dataset.sql.SQLDataSetUtils;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.sql.ConnectionWapper;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JdbcDatasetReader
extends DatasetReader {
    public JdbcDatasetReader(DSModel model) {
        super(model);
    }

    @Override
    public void read(IDSContext context, IDataProcessor processor) throws BIDataSetException {
        SQLModel sqlModel = (SQLModel)this.model;
        String sql = sqlModel.getSql();
        if (sql == null || sql.length() == 0) {
            throw new BIDataSetException("\u6ca1\u6709\u8981\u6267\u884c\u7684SQL");
        }
        try {
            ConnectionWapper[] conns;
            for (ConnectionWapper connWapper : conns = SQLDataSetUtils.getConnections(sqlModel, context.getEnhancedParameterEnv())) {
                String jndi = connWapper.getJndi();
                String schema = connWapper.getSchema();
                Connection conn = connWapper.getConn();
                IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                if (db.isDatabase("POSTGRESQL") || db.isDatabase("GaussDB")) {
                    conn.setAutoCommit(false);
                }
                try {
                    this.process(context, processor, conn, jndi, schema);
                }
                catch (Exception e) {
                    processor.complete(false);
                    throw e;
                }
                finally {
                    if (db.isDatabase("POSTGRESQL") || db.isDatabase("GaussDB")) {
                        conn.setAutoCommit(true);
                    }
                    conn.close();
                }
            }
            processor.complete(true);
        }
        catch (Exception e) {
            if (e instanceof BIDataSetException) {
                throw (BIDataSetException)e;
            }
            throw new BIDataSetException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void process(IDSContext context, IDataProcessor processor, Connection conn, String jndi, String schema) throws Exception {
        SQLModel sqlModel = (SQLModel)this.model;
        SQLQueryExecutor sqlExecutor = SQLDataSetUtils.createSQLQueryExecutor(conn, context);
        sqlExecutor.registerListener((ISQLQueryListener)new JNDIAndSchemaQueryListener(jndi, schema));
        JdbcDatasetReader.makeOrderFields(sqlExecutor.getOrderFields(), context);
        sqlExecutor.open(sqlModel.getSql());
        try {
            HashMap<String, Object> map = new HashMap<String, Object>();
            String[] exprFilter = new String[1];
            if (context != null) {
                RangeValues timekeyRange = context.getTimekeyFiterRange();
                FilterItem[] filters = context.getAllFilterItem();
                SQLDataSetUtils.makeSQLExecutorFilter((SQLModel)this.model, timekeyRange, filters, map, exprFilter, context.getI18nLang());
            }
            try (ResultSet rs = !map.isEmpty() || exprFilter[0] != null ? sqlExecutor.executeQuery(map, exprFilter[0]) : sqlExecutor.executeQuery();){
                List<FieldReader> readers = FieldReader.buildMemoryDataSet(sqlModel, rs.getMetaData());
                while (rs.next()) {
                    MemoryDataRow row = new MemoryDataRow();
                    row._setBuffer(new Object[this.model.getFields().size()]);
                    for (FieldReader reader : readers) {
                        reader.read(rs, (DataRow)row);
                    }
                    processor.process((DataRow)row);
                }
            }
        }
        finally {
            sqlExecutor.close();
        }
    }

    static void makeOrderFields(List<OrderField> orderFields, IDSContext context) {
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
        orderFields.addAll(list);
    }
}

