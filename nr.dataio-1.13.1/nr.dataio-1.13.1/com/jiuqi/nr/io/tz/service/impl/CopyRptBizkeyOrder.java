/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.loader.LoadFieldMap
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.filters.ExpressionFilter
 *  com.jiuqi.bi.database.sql.model.tables.QueryTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.filters.ExpressionFilter;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.service.impl.BaseMergeLoader;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

public class CopyRptBizkeyOrder
extends BaseMergeLoader {
    private final TzParams params;
    private final Integer transactionSize;

    public CopyRptBizkeyOrder(JdbcTemplate jdbcTemplate, TzParams params, Integer transactionSize) {
        super(jdbcTemplate);
        this.params = params;
        this.transactionSize = transactionSize != null && transactionSize <= 0 ? null : transactionSize;
    }

    @Override
    protected void loadSrc() {
        DataSchemeTmpTable tmpTable = this.params.getTmpTable();
        QueryTable src = new QueryTable((ISQLTable)new SimpleTable(tmpTable.getTzTableName() + "_RPT", "T0"), "T1");
        src.addField("MDCODE");
        for (DataFieldDeployInfo dimDeploy : tmpTable.getDimDeploys()) {
            src.addField(dimDeploy.getFieldName());
        }
        src.addField("SBID");
        src.addField("BIZKEYORDER");
        String expression = "T0.DATATIME = '" + this.params.getDatatime() + "'";
        src.whereFilters().add(new ExpressionFilter(expression));
        this.loader.setSourceTable((ISQLTable)src);
    }

    @Override
    protected void loadDest() {
        SimpleTable dest = new SimpleTable(this.params.getTempTableName(), "TS0");
        dest.addField("MDCODE");
        this.params.getTmpTable().getDimDeploys().stream().map(DataFieldDeployInfo::getFieldName).forEach(arg_0 -> ((SimpleTable)dest).addField(arg_0));
        dest.addField("SBID");
        dest.addField("BIZKEYORDER");
        this.loader.setDestTable(dest);
    }

    @Override
    protected void loadFieldMaps() {
        List fieldMaps = this.loader.getFieldMaps();
        ISQLTable src = this.loader.getSourceTable();
        SimpleTable dest = this.loader.getDestTable();
        List fields = src.fields();
        List destFields = dest.fields();
        Assert.isTrue(fields.size() == destFields.size(), "\u6e90\u8868\u548c\u4e34\u65f6\u8868\u5339\u914d\u9519\u8bef");
        for (int i = 0; i < destFields.size() - 1; ++i) {
            ISQLField isqlField = (ISQLField)destFields.get(i);
            fieldMaps.add(new LoadFieldMap((ISQLField)fields.get(i), isqlField, true, false));
        }
        fieldMaps.add(new LoadFieldMap((ISQLField)fields.get(fields.size() - 1), (ISQLField)destFields.get(destFields.size() - 1), false, true));
    }

    @Override
    protected Integer getTransactionSize() {
        return this.transactionSize;
    }
}

