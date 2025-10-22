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
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.jdbc.core.JdbcTemplate;

public class TzData2HisBySbIdModel
extends BaseSqlModel {
    protected final TzParams params;

    public TzData2HisBySbIdModel(JdbcTemplate jdbcTemplate, TzParams params) {
        super(jdbcTemplate);
        this.params = params;
    }

    protected ISQLTable loadSrc() {
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        QueryTable t1 = new QueryTable((ISQLTable)new SimpleTable(tmpTable.getTzTableName(), "T0"), "T1");
        this.addField((AbstractTable)t1, tmpTable);
        t1.addField(t1.createField("MDCODE"));
        t1.addField(t1.createField("FLOATORDER"));
        t1.addField(t1.createField("VALIDDATATIME"));
        t1.addField((ISQLField)new EvalField((ISQLTable)t1, "'" + this.params.getDatatime() + "'", "INVALIDDATATIME"));
        String uuid = DataEngineUtil.buildcreateUUIDSql((IDatabase)this.database, (boolean)true);
        EvalField uuidField = new EvalField((ISQLTable)t1, uuid, "BIZKEYORDER");
        t1.addField((ISQLField)uuidField);
        Collection isqlFilters = t1.whereFilters();
        isqlFilters.add(new ExpressionFilter("T0.SBID = ?"));
        isqlFilters.add(new ExpressionFilter("T0.VALIDDATATIME < '" + this.params.getDatatime() + "'"));
        return t1;
    }

    protected void addField(AbstractTable t2, DataSchemeTmpTable tmpTable) {
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

