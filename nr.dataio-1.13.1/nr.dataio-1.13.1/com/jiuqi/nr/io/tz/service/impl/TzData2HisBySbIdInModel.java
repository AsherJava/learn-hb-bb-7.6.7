/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.fields.EvalField
 *  com.jiuqi.bi.database.sql.model.filters.ExpressionFilter
 *  com.jiuqi.bi.database.sql.model.tables.AbstractTable
 *  com.jiuqi.bi.database.sql.model.tables.QueryTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.filters.ExpressionFilter;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.service.impl.TzData2HisBySbIdModel;
import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;

public class TzData2HisBySbIdInModel
extends TzData2HisBySbIdModel {
    private final int inSzie;

    public TzData2HisBySbIdInModel(JdbcTemplate jdbcTemplate, TzParams params, int inSzie) {
        super(jdbcTemplate, params);
        this.inSzie = inSzie;
    }

    @Override
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
        StringBuilder inSb = new StringBuilder();
        for (int i = 0; i < this.inSzie; ++i) {
            inSb.append("?,");
        }
        inSb.setLength(inSb.length() - 1);
        String sbIn = "T0.SBID in (" + inSb + ")";
        isqlFilters.add(new ExpressionFilter(sbIn));
        isqlFilters.add(new ExpressionFilter("T0.VALIDDATATIME < '" + this.params.getDatatime() + "'"));
        return t1;
    }
}

