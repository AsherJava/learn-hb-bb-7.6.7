/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.InsertSQLModel
 *  com.jiuqi.bi.database.sql.model.SQLModelException
 *  com.jiuqi.bi.database.sql.model.fields.EvalField
 *  com.jiuqi.bi.database.sql.model.filters.ExpressionFilter
 *  com.jiuqi.bi.database.sql.model.tables.AbstractTable
 *  com.jiuqi.bi.database.sql.model.tables.QueryTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.InsertSQLModel;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.filters.ExpressionFilter;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import com.jiuqi.nr.io.tz.service.impl.BaseSqlModel;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.jdbc.core.JdbcTemplate;

public class AddData2SbTableModel
extends BaseSqlModel {
    private final TzParams params;

    public AddData2SbTableModel(JdbcTemplate jdbcTemplate, TzParams params) {
        super(jdbcTemplate);
        this.params = params;
    }

    public ISQLTable loadSrc() {
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        String mdCodeTable = this.params.getMdCodeTable();
        String sql = String.format("SELECT MAX(%s) FROM %s WHERE %s IN (SELECT %s FROM %s)", "FLOATORDER", tmpTable.getTzTableName(), "MDCODE", "MDCODE", mdCodeTable);
        Optional<Object> max = Optional.ofNullable(this.jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0.0;
        }));
        QueryTable queryTable = new QueryTable((ISQLTable)new SimpleTable(this.params.getTempTableName(), "T0"), "T1");
        this.addTzField(tmpTable, (AbstractTable)queryTable);
        String expression = "T0.OPT = 4";
        queryTable.whereFilters().add(new ExpressionFilter(expression));
        queryTable.addField("MDCODE");
        EvalField order = new EvalField((ISQLTable)queryTable, max.orElse(0.0) + " + T0." + "MD_ORDINAL" + " * 100 ", "FLOATORDER");
        queryTable.addField((ISQLField)order);
        EvalField evalField = new EvalField((ISQLTable)queryTable, "'" + this.params.getDatatime() + "'", "VALIDDATATIME");
        queryTable.addField((ISQLField)evalField);
        queryTable.addField("SBID", "BIZKEYORDER");
        queryTable.addField("MODIFYTIME");
        return queryTable;
    }

    public SimpleTable loadDest() {
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        SimpleTable sb = new SimpleTable(tmpTable.getTzTableName(), "T2");
        this.addTzField(tmpTable, (AbstractTable)sb);
        sb.addField("MDCODE");
        sb.addField("FLOATORDER");
        sb.addField("VALIDDATATIME");
        sb.addField("BIZKEYORDER");
        sb.addField("MODIFYTIME");
        return sb;
    }

    private void addTzField(DataSchemeTmpTable tmpTable, AbstractTable table) {
        table.addField("SBID");
        Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys(), tmpTable.getTimePointDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).filter(Objects::nonNull).map(DataFieldDeployInfo::getFieldName).filter(Objects::nonNull).forEach(arg_0 -> ((AbstractTable)table).addField(arg_0));
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
}

