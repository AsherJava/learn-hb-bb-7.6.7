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
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import com.jiuqi.nr.io.tz.service.impl.BaseSqlModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

public class Insert2StateTmpTable
extends BaseSqlModel {
    protected final TzParams params;
    protected final byte optFilter;

    public Insert2StateTmpTable(JdbcTemplate jdbcTemplate, TzParams params, byte optFilter) {
        super(jdbcTemplate);
        this.params = params;
        this.optFilter = optFilter;
    }

    @Override
    public String buildSql() {
        try {
            ISQLTable src = this.buildQueryTable();
            SimpleTable de = this.buildSimpleTable(this.params.getStateTableName());
            InsertSQLModel model = new InsertSQLModel(src, (ISQLTable)de);
            Assert.isTrue(src.fields().size() == de.fields().size(), "\u6e90\u8868\u548c\u4e34\u65f6\u8868\u5339\u914d\u9519\u8bef");
            return model.toSQL(this.database, 0);
        }
        catch (SQLModelException e) {
            throw new TzImportException(e);
        }
    }

    protected ISQLTable buildQueryTable() {
        QueryTable src = new QueryTable((ISQLTable)new SimpleTable(this.params.getTempTableName(), "T0"), "T1");
        src.addField("SBID");
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        for (DataFieldDeployInfo field : tmpTable.getTimePointDeploys()) {
            src.addField(field.getFieldName());
        }
        String expression = "T0.OPT = " + this.optFilter;
        src.whereFilters().add(new ExpressionFilter(expression));
        if (this.optFilter == 2) {
            DataFieldDeployInfo field;
            field = new EvalField((ISQLTable)src, "'" + this.params.getDatatime() + "'", "VALIDDATATIME");
            src.addField((ISQLField)field);
        }
        if (this.optFilter == 2 || this.optFilter == 3) {
            src.addField("MODIFYTIME");
        }
        return src;
    }

    protected SimpleTable buildSimpleTable(String tableName) {
        return this.buildSimpleTable(tableName, "T2");
    }

    protected SimpleTable buildSimpleTable(String tableName, String flag) {
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        SimpleTable sb = new SimpleTable(tableName, flag);
        sb.addField("SBID");
        for (DataFieldDeployInfo field : tmpTable.getTimePointDeploys()) {
            sb.addField(field.getFieldName());
        }
        if (this.optFilter == 2) {
            sb.addField("VALIDDATATIME");
        }
        if (this.optFilter == 2 || this.optFilter == 3) {
            sb.addField("MODIFYTIME");
        }
        return sb;
    }
}

