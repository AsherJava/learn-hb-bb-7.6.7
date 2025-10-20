/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.ddl.DDLException;
import com.jiuqi.bi.database.ddl.IDDLExecutor;
import com.jiuqi.bi.database.sql.loader.ILoadListener;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.util.OrderGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractTableLoader
implements ITableLoader {
    protected final Connection conn;
    protected final IDatabase database;
    protected ISQLTable sourceTable;
    protected SimpleTable destTable;
    protected List<LoadFieldMap> fieldMaps;
    protected int transactionSize;
    protected int option;
    private ILoadListener listener;
    private static final String NAME_PREFIX = "TMP_";

    public AbstractTableLoader(Connection conn, IDatabase database) {
        this.conn = conn;
        this.database = database;
        this.fieldMaps = new ArrayList<LoadFieldMap>();
        this.transactionSize = 100000;
    }

    protected ILoadListener listener() {
        return this.listener == null ? ILoadListener.EMPTY_LISTENER : this.listener;
    }

    @Override
    public ISQLTable getSourceTable() {
        return this.sourceTable;
    }

    @Override
    public void setSourceTable(ISQLTable srcTable) {
        this.sourceTable = srcTable;
    }

    @Override
    public SimpleTable getDestTable() {
        return this.destTable;
    }

    @Override
    public void setDestTable(SimpleTable destTable) {
        this.destTable = destTable;
    }

    @Override
    public List<LoadFieldMap> getFieldMaps() {
        return this.fieldMaps;
    }

    @Override
    public abstract int execute() throws TableLoaderException;

    @Override
    public void setListener(ILoadListener listener) {
        this.listener = listener;
    }

    @Override
    public void setTransactionSize(int transSize) {
        this.transactionSize = transSize;
    }

    @Override
    public void setOption(int option) {
        this.option = option;
    }

    protected void execSQL(String sql) throws SQLException {
        if (this.listener != null) {
            this.listener.executeSQL(sql);
        }
        try {
            IDDLExecutor ddl = this.database.createDDLExcecutor(this.conn);
            ddl.execute(sql);
        }
        catch (DDLException e) {
            throw new SQLException(e);
        }
    }

    protected void validate() throws TableLoaderException {
        if (this.sourceTable.fields().isEmpty()) {
            throw new TableLoaderException("\u672a\u5b9a\u4e49\u6765\u6e90\u8868\u5b57\u6bb5");
        }
        if (this.destTable.fields().isEmpty()) {
            throw new TableLoaderException("\u672a\u5b9a\u4e49\u76ee\u6807\u8868\u5b57\u6bb5");
        }
        if (this.fieldMaps.isEmpty()) {
            throw new TableLoaderException("\u672a\u6307\u5b9a\u5b57\u6bb5\u6620\u5c04\u5173\u7cfb");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected int execUpdateSQL(String sql) throws SQLException {
        if (this.listener != null) {
            this.listener.executeSQL(sql);
        }
        try (Statement stmt = this.conn.createStatement();){
            int n = stmt.executeUpdate(sql);
            return n;
        }
    }

    protected void resetSourceFields() throws TableLoaderException {
        ArrayList<ISQLField> sourceFields = new ArrayList<ISQLField>(this.sourceTable.fields());
        this.sourceTable.fields().clear();
        for (ISQLField destField : this.destTable.fields()) {
            ISQLField sourceField = this.extractSourceField(destField, sourceFields);
            if (sourceField == null) {
                throw new TableLoaderException("\u672a\u627e\u5230\u5b57\u6bb5\u7684\u6765\u6e90\u5b57\u6bb5\uff1a" + destField.fieldName());
            }
            this.sourceTable.fields().add(sourceField);
        }
        for (ISQLField sourceField : sourceFields) {
            sourceField.setVisible(false);
            this.sourceTable.fields().add(sourceField);
        }
    }

    private ISQLField extractSourceField(ISQLField destField, List<ISQLField> sourceFields) {
        for (LoadFieldMap map : this.fieldMaps) {
            if (map.getDestField() != destField) continue;
            if (sourceFields.remove(map.getSourceField())) {
                return map.getSourceField();
            }
            return null;
        }
        return null;
    }

    protected static String createName() {
        Random rand = new Random();
        int id = rand.nextInt(10000);
        return NAME_PREFIX + OrderGenerator.newOrder() + "_" + id;
    }
}

