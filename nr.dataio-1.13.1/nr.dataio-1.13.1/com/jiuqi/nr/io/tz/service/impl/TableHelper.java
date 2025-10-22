/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.SortField
 *  com.jiuqi.bi.database.sql.model.fields.RankField
 *  com.jiuqi.bi.database.sql.model.filters.InFilter
 *  com.jiuqi.bi.database.sql.model.tables.QueryTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.database.sql.model.fields.RankField;
import com.jiuqi.bi.database.sql.model.filters.InFilter;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import java.util.Collection;
import java.util.stream.Stream;

public class TableHelper {
    public static SimpleTable buildTempSimpleTable(DataSchemeTmpTable tmpTable, String tableName) {
        SimpleTable destTable = new SimpleTable(tableName, "TS0");
        destTable.addField("MDCODE");
        Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys()).flatMap(Collection::stream).map(DataFieldDeployInfo::getFieldName).forEach(arg_0 -> ((SimpleTable)destTable).addField(arg_0));
        destTable.addField("ORDINAL");
        destTable.addField("SBID");
        return destTable;
    }

    public static ISQLTable buildRankSbTable(TzParams params) {
        DataSchemeTmpTable tmpTable = params.getTmpTable();
        QueryTable queryTable = new QueryTable((ISQLTable)new SimpleTable(tmpTable.getTzTableName(), "T0"), "T1");
        ISQLField mdCode = queryTable.addField("MDCODE");
        RankField rankField = new RankField((ISQLTable)queryTable, "ORDINAL");
        rankField.partitionFields().add(mdCode);
        for (DataFieldDeployInfo dimDeploy : tmpTable.getDimDeploys()) {
            ISQLField pm = queryTable.addField(dimDeploy.getFieldName());
            rankField.partitionFields().add(pm);
        }
        for (DataFieldDeployInfo tableDimField : tmpTable.getTableDimDeploys()) {
            ISQLField tm = queryTable.addField(tableDimField.getFieldName());
            rankField.partitionFields().add(tm);
        }
        ISQLField floatOrder = queryTable.createField("FLOATORDER");
        rankField.orderFields().add(new SortField((ISQLTable)queryTable, floatOrder));
        queryTable.addField((ISQLField)rankField);
        ISQLField sbId = queryTable.addField("SBID");
        rankField.orderFields().add(new SortField((ISQLTable)queryTable, sbId));
        InFilter inFilter = new InFilter();
        inFilter.setFilterField(mdCode);
        QueryTable innerTable = new QueryTable((ISQLTable)new SimpleTable(params.getMdCodeTable(), "M0"), "M1");
        innerTable.addField("MDCODE");
        inFilter.setFilterTable((ISQLTable)innerTable);
        queryTable.whereFilters().add(inFilter);
        return queryTable;
    }
}

