/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.SQLModelException
 *  com.jiuqi.bi.database.sql.model.UpdateSQLModel
 *  com.jiuqi.bi.database.sql.model.tables.FieldMap
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.UpdateSQLModel;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.exception.TzImportException;
import com.jiuqi.nr.io.tz.service.impl.Insert2StateTmpTable;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

public class UpdateTzData
extends Insert2StateTmpTable {
    public UpdateTzData(JdbcTemplate jdbcTemplate, TzParams params, byte optFilter) {
        super(jdbcTemplate, params, optFilter);
    }

    public String buildInsertSql() {
        return super.buildSql();
    }

    @Override
    public String buildSql() {
        try {
            SimpleTable src = super.buildSimpleTable(this.params.getStateTableName(), "T0");
            SimpleTable de = super.buildSimpleTable(this.params.getTmpTable().getTzTableName(), "T1");
            UpdateSQLModel model = new UpdateSQLModel((ISQLTable)src, de);
            List fieldMaps = model.keyMaps();
            Assert.isTrue(src.fields().size() == de.fields().size(), "\u6e90\u8868\u548c\u4e34\u65f6\u8868\u5339\u914d\u9519\u8bef");
            fieldMaps.add(new FieldMap((ISQLField)src.fields().get(0), (ISQLField)de.fields().get(0)));
            return model.toSQL(this.database, 0);
        }
        catch (SQLModelException e) {
            throw new TzImportException(e);
        }
    }
}

