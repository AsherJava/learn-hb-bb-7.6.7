/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.InsertSQLModel
 *  com.jiuqi.bi.database.sql.model.SQLModelException
 *  com.jiuqi.bi.database.sql.model.fields.EvalField
 *  com.jiuqi.bi.database.sql.model.filters.ExpressionFilter
 *  com.jiuqi.bi.database.sql.model.tables.AbstractTable
 *  com.jiuqi.bi.database.sql.model.tables.FieldMap
 *  com.jiuqi.bi.database.sql.model.tables.JoinMode
 *  com.jiuqi.bi.database.sql.model.tables.JoinedTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.bi.database.sql.model.tables.SubTable
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.InsertSQLModel;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.filters.ExpressionFilter;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.JoinMode;
import com.jiuqi.bi.database.sql.model.tables.JoinedTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.database.sql.model.tables.SubTable;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import com.jiuqi.nr.io.tz.service.impl.Insert2StateTmpTable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.jdbc.core.JdbcTemplate;

public class TzData2HisModel
extends Insert2StateTmpTable {
    private boolean validDataTimeFilter;

    public TzData2HisModel(JdbcTemplate jdbcTemplate, TzParams params, byte optFilter) {
        super(jdbcTemplate, params, optFilter);
    }

    protected ISQLTable loadSrc() {
        String tableName = this.params.getStateTableName();
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        JoinedTable joinedTable = new JoinedTable();
        SimpleTable mainTable = new SimpleTable(tableName, "T1");
        joinedTable.setMainTable((ISQLTable)mainTable);
        SimpleTable t2 = new SimpleTable(tmpTable.getTzTableName(), "T2");
        SubTable subTable = new SubTable((ISQLTable)t2);
        joinedTable.subTables().add(subTable);
        subTable.setJoinMode(JoinMode.INNER);
        List fieldMaps = subTable.fieldMaps();
        this.addField((AbstractTable)t2, tmpTable);
        for (ISQLField field : t2.fields()) {
            joinedTable.addField(field);
        }
        fieldMaps.add(new FieldMap(mainTable.createField("SBID"), subTable.table().createField("SBID")));
        joinedTable.addField(t2.createField("MDCODE"));
        joinedTable.addField(t2.createField("FLOATORDER"));
        joinedTable.addField(t2.createField("VALIDDATATIME"));
        joinedTable.addField((ISQLField)new EvalField((ISQLTable)joinedTable, "'" + this.params.getDatatime() + "'", "INVALIDDATATIME"));
        String uuid = DataEngineUtil.buildcreateUUIDSql((IDatabase)this.database, (boolean)true);
        EvalField uuidField = new EvalField((ISQLTable)joinedTable, uuid, "BIZKEYORDER");
        joinedTable.addField((ISQLField)uuidField);
        if (this.validDataTimeFilter) {
            Collection isqlFilters = joinedTable.whereFilters();
            isqlFilters.add(new ExpressionFilter("T2.VALIDDATATIME < ?"));
        }
        return joinedTable;
    }

    private void addField(AbstractTable t2, DataSchemeTmpTable tmpTable) {
        t2.addField("SBID");
        Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys(), tmpTable.getTimePointDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).filter(Objects::nonNull).map(DataFieldDeployInfo::getFieldName).filter(Objects::nonNull).distinct().forEach(arg_0 -> ((AbstractTable)t2).addField(arg_0));
    }

    protected SimpleTable loadDest() {
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        SimpleTable sbHiTable = new SimpleTable(tmpTable.getTzTableName() + "_HIS", "T0");
        this.addField((AbstractTable)sbHiTable, tmpTable);
        sbHiTable.addField("MDCODE");
        sbHiTable.addField("FLOATORDER");
        sbHiTable.addField("VALIDDATATIME");
        sbHiTable.addField("INVALIDDATATIME");
        sbHiTable.addField("BIZKEYORDER");
        return sbHiTable;
    }

    public String buildInsertSql() {
        return super.buildSql();
    }

    @Override
    public String buildSql() {
        try {
            InsertSQLModel model = new InsertSQLModel(this.loadSrc(), (ISQLTable)this.loadDest());
            return model.toSQL(this.database, 0);
        }
        catch (SQLModelException e) {
            throw new TzImportException(e);
        }
    }

    public void validDataTimeFilter() {
        this.validDataTimeFilter = true;
    }
}

