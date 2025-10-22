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
 *  com.jiuqi.bi.database.sql.model.tables.QueryTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
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
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import com.jiuqi.nr.io.tz.service.impl.BaseSqlModel;
import org.springframework.jdbc.core.JdbcTemplate;

public class TempData2RtpTable
extends BaseSqlModel {
    private final TzParams params;
    private final byte flag;
    private final String opt;
    private final String operator;
    private final String expression;

    public TempData2RtpTable(JdbcTemplate jdbcTemplate, TzParams params) {
        super(jdbcTemplate);
        this.params = params;
        this.flag = 0;
        this.opt = "OPT";
        this.operator = " >= ";
        this.expression = null;
    }

    public TempData2RtpTable(JdbcTemplate jdbcTemplate, TzParams params, String opt, String operator, byte flag) {
        super(jdbcTemplate);
        this.params = params;
        this.flag = flag;
        this.opt = opt;
        this.operator = operator;
        this.expression = null;
    }

    public TempData2RtpTable(JdbcTemplate jdbcTemplate, TzParams params, String expression) {
        super(jdbcTemplate);
        this.params = params;
        this.expression = expression;
        this.flag = 0;
        this.opt = null;
        this.operator = null;
    }

    protected QueryTable loadSrc() {
        QueryTable src = new QueryTable((ISQLTable)new SimpleTable(this.params.getTempTableName(), "T0"), "T1");
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        String uuid = DataEngineUtil.buildcreateUUIDSql((IDatabase)this.database, (boolean)true);
        EvalField uuidField = new EvalField((ISQLTable)src, uuid, "BIZKEYORDER");
        src.addField((ISQLField)uuidField);
        this.addFields((AbstractTable)src, tmpTable);
        src.addField((ISQLField)new EvalField((ISQLTable)src, "'" + this.params.getDatatime() + "'", "DATATIME"));
        String expression = this.expression != null ? "T0." + this.expression : "T0." + this.opt + this.operator + this.flag;
        src.whereFilters().add(new ExpressionFilter(expression));
        return src;
    }

    private void addFields(AbstractTable table, DataSchemeTmpTable tmpTable) {
        table.addField("SBID");
        table.addField("MDCODE");
        for (DataFieldDeployInfo dimDeploy : tmpTable.getDimDeploys()) {
            table.addField(dimDeploy.getFieldName());
        }
        for (DataFieldDeployInfo field : tmpTable.getPeriodicDeploys()) {
            table.addField(field.getFieldName());
        }
    }

    protected SimpleTable loadDest() {
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        SimpleTable t0 = new SimpleTable(tmpTable.getTzTableName() + "_RPT", "T2");
        t0.addField("BIZKEYORDER");
        this.addFields((AbstractTable)t0, tmpTable);
        t0.addField("DATATIME");
        return t0;
    }

    @Override
    public String buildSql() {
        try {
            InsertSQLModel model = new InsertSQLModel((ISQLTable)this.loadSrc(), (ISQLTable)this.loadDest());
            return model.toSQL(this.database, 0);
        }
        catch (SQLModelException e) {
            throw new TzImportException(e);
        }
    }
}

