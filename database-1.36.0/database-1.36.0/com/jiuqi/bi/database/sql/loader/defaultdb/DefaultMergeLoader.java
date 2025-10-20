/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader.defaultdb;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.AbstractMergeLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.InsertSQLModel;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.UpdateSQLModel;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.filters.NotExistsFilter;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import java.sql.Connection;
import java.sql.SQLException;

public final class DefaultMergeLoader
extends AbstractMergeLoader {
    private boolean needInsert;

    public DefaultMergeLoader(Connection conn, IDatabase database, boolean needInsert) {
        super(conn, database);
        this.needInsert = needInsert;
    }

    @Override
    protected int mergeData(String tmpTableName, int tableSize, boolean isTemp) throws SQLException, TableLoaderException {
        this.resetSourceFields();
        int recSize = this.updateData(tmpTableName);
        if (this.needInsert) {
            recSize += this.insertData(tmpTableName);
        }
        return recSize;
    }

    private int updateData(String tmpTableName) throws SQLException, TableLoaderException {
        String sql = this.createUpdateSQL(tmpTableName);
        return this.execUpdateSQL(sql);
    }

    private String createUpdateSQL(String tmpTableName) throws TableLoaderException {
        SimpleTable tmpTable = this.createTmpTable(tmpTableName);
        UpdateSQLModel updateModel = new UpdateSQLModel(tmpTable, this.destTable);
        for (LoadFieldMap loadMap : this.fieldMaps) {
            if (!loadMap.isKey()) continue;
            ISQLField srcField = tmpTable.findByFieldName(loadMap.getSourceField().fieldName());
            FieldMap map = new FieldMap(srcField, loadMap.getDestField(), loadMap.isNullable());
            updateModel.keyMaps().add(map);
        }
        try {
            return updateModel.toSQL(this.database, 0);
        }
        catch (SQLModelException e) {
            throw new TableLoaderException(e);
        }
    }

    private SimpleTable createTmpTable(String tmpTableName) {
        SimpleTable tmpTable = new SimpleTable(tmpTableName, "S");
        for (ISQLField field : this.sourceTable.fields()) {
            if (!field.isVisible()) continue;
            tmpTable.addField(field.fieldName(), field.alias());
        }
        return tmpTable;
    }

    private int insertData(String tmpTableName) throws SQLException, TableLoaderException {
        String sql = this.createInsertSQL(tmpTableName);
        return this.execUpdateSQL(sql);
    }

    private String createInsertSQL(String tmpTableName) throws TableLoaderException {
        SimpleTable tmpTable = this.createTmpTable(tmpTableName);
        QueryTable tmpQuery = QueryTable.wrapperTable(tmpTable, "Q");
        SimpleTable target = new SimpleTable(this.destTable.name(), "T");
        NotExistsFilter filter = new NotExistsFilter(target);
        for (LoadFieldMap loadMap : this.fieldMaps) {
            if (!loadMap.isKey()) continue;
            ISQLField srcField = tmpTable.addField(loadMap.getSourceField().fieldName());
            ISQLField destField = target.addField(loadMap.getDestField().name());
            FieldMap map = new FieldMap(srcField, destField, loadMap.isNullable());
            filter.fieldMaps().add(map);
        }
        tmpQuery.whereFilters().add(filter);
        InsertSQLModel insertModel = new InsertSQLModel(tmpQuery, this.destTable);
        try {
            return insertModel.toSQL(this.database, 0);
        }
        catch (SQLModelException e) {
            throw new TableLoaderException(e);
        }
    }

    @Override
    protected ISQLField createRowIDField(ISQLTable qryTable) throws TableLoaderException {
        if (this.database.isDatabase("ORACLE") || this.database.isDatabase("DM") || this.database.isDatabase("KINGBASE")) {
            return new EvalField(qryTable, "ROWNUM", "TMP_ROWID");
        }
        if (this.database.isDatabase("MSSQL")) {
            return null;
        }
        if (this.database.isDatabase("HANA")) {
            StringBuilder sql = new StringBuilder("ROW_NUMBER() OVER (");
            boolean started = false;
            for (LoadFieldMap map : this.fieldMaps) {
                ISQLField field;
                if (!map.isKey() || (field = qryTable.findField(map.getSourceField().fieldName())) == null) continue;
                if (started) {
                    sql.append(',');
                } else {
                    started = true;
                    sql.append("ORDER BY ");
                }
                try {
                    field.toSQL(sql, this.database, 1);
                }
                catch (SQLModelException e) {
                    throw new TableLoaderException(e);
                }
            }
            sql.append(')');
            return new EvalField(qryTable, sql.toString(), "TMP_ROWID");
        }
        if (this.database.isDatabase("MYSQL") || this.database.isDatabase("GBASE")) {
            return null;
        }
        if (this.database.isDatabase("POSTGRESQL") || this.database.isDatabase("GaussDB")) {
            return new EvalField(qryTable, "ROW_NUMBER() OVER()", "TMP_ROWID");
        }
        return super.createRowIDField(qryTable);
    }
}

